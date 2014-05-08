/**
 * Simple ASIO Counter client example
 * 
 * g++ -s -std=c++11 -DASIO_STANDALONE -DASIO_DYN_LINK -I../asio-1.10.2/include -L../lib64 -o asio_parkinglot_server asio_parkinglot_server.C atomicparkinglot.C -pthread -lasio
 * 
 * @author Fadi Moukayed
 */

#include <cstdlib>
#include <cstring>
#include <iostream>
#include <array>
#include <asio.hpp>

using asio::ip::tcp;

static std::array<const char *, 4> commands = {
    "free", "in", "out", "quit"
};

static std::array<const char *, 4> commands_nl = {
    "free\n", "in\n", "out\n", "quit\n"
};
                     
static void dispatch(asio::io_service &ios,
                     int ci, 
                     tcp::resolver::iterator it) 
{
    tcp::socket s(ios);
    asio::connect(s, it);
    asio::write(s, asio::buffer(commands_nl[ci], strlen(commands_nl[ci])));
    
    if (ci == 3) {
        std::exit(EXIT_SUCCESS);
    }
    
    asio::streambuf buffer;
    asio::read_until(s, buffer, "\n");
    std::cout << "<[" << s.remote_endpoint().address()
                    << ':' 
                    << s.remote_endpoint().port()
                    << "] " 
                    << asio::buffer_cast<const char*>(buffer.data());
}

static void dispatch(asio::io_service &ios,
                     int ci, 
                     const std::vector<tcp::resolver::iterator> &eps) 
{
    // Broadcast quit signal to all servers
    if (ci == 3) {
        for (tcp::resolver::iterator it : eps) {
            tcp::socket s(ios);
            asio::connect(s, it);
            asio::write(s, asio::buffer(commands_nl[ci], strlen(commands_nl[ci])));
        }
        std::exit(EXIT_SUCCESS);
    }
    
    for (tcp::resolver::iterator it : eps) {
        tcp::socket s(ios);
        asio::connect(s, it);
        asio::write(s, asio::buffer(commands_nl[ci], strlen(commands_nl[ci])));
        
        asio::streambuf buffer;
        asio::read_until(s, buffer, "\n");
        std::cout << "<[" << s.remote_endpoint().address()
                        << ':' 
                        << s.remote_endpoint().port()
                        << "] " 
                        << asio::buffer_cast<const char *>(buffer.data());
        // Stop at first success
        if (std::memcmp(asio::buffer_cast<const char *>(buffer.data()), 
                                                        "ok", 2) == 0) 
        {
            break;
        }
    }
}

int main(int argc, char *argv[]) {
    try {
        if (argc < 2) {
            std::cerr << "Usage: " << argv[0] << " <IP-ADDRESS:PORT>  ... " << std::endl;
            return 1;
        }
        
        std::vector<tcp::resolver::query> servers;
        servers.reserve(argc - 1);
        
        // Parse IP:PORT pairs
        for (int i = 1; i < argc; ++i) {
            char *ip = std::strtok(argv[i], ":");
            while (ip) {
                char *port = std::strtok(NULL, ":");
                if (port) {
                    servers.emplace_back(ip, port);
                    ip = std::strtok(NULL, ":");
                } else {
                    std::cerr << "Argument number [" << i 
                              << "] - Invalid IP:PORT pair" << std::endl;
                    return 1;
                }
            }
        }
        
        asio::io_service io_service;
        tcp::resolver resolver(io_service);
        
        std::vector<tcp::resolver::iterator> endpoints;
        endpoints.reserve(argc - 1);
        
        // Resolve all addresses
        for (const tcp::resolver::query &q : servers) {
            asio::error_code ec;
            tcp::resolver::iterator ep = resolver.resolve(q, ec);
            if (!ec) {
                endpoints.push_back(std::move(ep));
            } else {
                std::cerr << "Couldn't resolve host: " << q.host_name()
                          << ':' << q.service_name() << std::endl;
                return 1;
            }
        }
        
        std::cout << "Commands: ";
        for (const char *c : commands) {
            std::cout << c << ' ';
        }
        std::cout << std::endl;
        
        std::cout << "Endpoints: ";
        {
            int i = 0;
            for (const tcp::resolver::query &q : servers) {
                std::cout << ++i << ". [" << q.host_name() << ':' 
                          << q.service_name() << "] ";
            }
        }
        std::cout << std::endl;
        
        std::array<char, 64> line;
        
        for (;;) {
            std::cout << "> ";
            std::cin.getline(&line[0], line.size());
            char *cmd = std::strtok(&line[0], " ");
            char *arg = std::strtok(NULL, " ");
            if (cmd) {
                int cindex = -1;
                for (int i = 0; i < commands.size(); ++i) {
                    if (strcmp(commands[i], cmd) == 0) {
                        cindex = i;
                    }
                }
                if (cindex == -1) {
                    std::cerr << "Invalid command: " << cmd << std::endl;
                } else {
                    if (arg) {
                        int epindex = std::atoi(arg);
                        if (epindex) {
                            // Unicast dispatcher
                            dispatch(io_service, cindex, 
                                     endpoints[epindex - 1]);
                        } else {
                            std::cerr << "Invalid endpoint index: " 
                                      << epindex << std::endl;
                        }
                    } else {
                        // Multicast dispatcher
                        dispatch(io_service, cindex, endpoints);
                    }
                }
            }
        }
        
    } catch (std::exception &e) {
        std::cerr << "Exception: " << e.what() << "\n";
    }
}
