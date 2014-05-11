/**
 * Simple ASIO Counter server example
 * 
 * g++ -s -std=c++11 -DASIO_STANDALONE -DASIO_DYN_LINK -I../asio-1.10.2/include -L../lib64 -o asio_parkinglot_server asio_parkinglot_server.C atomicparkinglot.C -pthread -lasio
 * 
 * @author Fadi Moukayed
 */

#include <cstdlib>
#include <cstring>
#include <iostream>
#include <memory>
#include <array>
#include <asio.hpp>
#include "atomicparkinglot.H"

using asio::ip::tcp;

class session : public std::enable_shared_from_this<session> {
public:
    session(tcp::socket socket, vsys::atomic_parking_lot &p) 
    : socket(std::move(socket)),
      plot(&p) {}
    void start() { do_read(); }
private:
    void log_req(const char *r) {
        std::cout << "Received '" << r << "' request from: "
                  << socket.remote_endpoint().address()
                  << ":"
                  << socket.remote_endpoint().port()
                  << std::endl;
    }
    
    void do_read() {
        auto self = shared_from_this();

        asio::async_read_until(
            socket,
            request,
            "\n",
            [this, self](std::error_code ec, std::size_t length) {
                if (!ec) {
                    asio::streambuf response;
                    std::ostream osresponse(&response);
                    
                    if (std::strncmp(asio::buffer_cast<const char *>(request.data()), "free", 4) == 0) {
                        log_req("free");
                        osresponse << plot->remaining() << std::endl;
                    } else if (std::strncmp(asio::buffer_cast<const char *>(request.data()), "in", 2) == 0) {
                        log_req("in");
                        osresponse << (plot->park() ? "ok" : "fail") << std::endl;
                    } else if (std::strncmp(asio::buffer_cast<const char *>(request.data()), "out", 3) == 0) {
                        log_req("out");
                        osresponse << (plot->unpark() ? "ok" : "fail") << std::endl;
                    } else if (std::strncmp(asio::buffer_cast<const char *>(request.data()), "quit", 4) == 0) {
                        log_req("quit");
                        osresponse << "ok" << std::endl;
                        socket.send(response.data());
                        std::exit(EXIT_SUCCESS);
                    } else {
                        log_req("Unknown");
                        osresponse << "Unknown command: "
                                   << asio::buffer_cast<const char*>(request.data())
                                   << std::endl;
                    }
                    
                    request.commit(request.size());
                    socket.send(response.data());
                }
            }
        );
    }
    
    asio::streambuf request;
    tcp::socket socket;
    vsys::atomic_parking_lot *plot;
};

class server {
public:
    server(asio::io_service& io_service, short port, int n) 
    : acceptor(io_service, tcp::endpoint(tcp::v4(), port)),
      socket(io_service),
      plot(n)
    {
        do_accept();        
    }
private:
    void do_accept() {
        acceptor.async_accept(
            socket,
            [this](std::error_code ec) {
                if (!ec) {
                    std::make_shared<session>(std::move(socket), plot)->start();
                }
                do_accept();
            }
        );
    }
    
    vsys::atomic_parking_lot plot;
    tcp::acceptor acceptor;
    tcp::socket socket;
};

int main(int argc, char* argv[]) {
    try {
        int n = 5;
        
        if (argc < 2 || argc > 3) {
            std::cerr << "Usage: " << argv[0] << " <port> [N = 5]" << std::endl;
            return 1;
        }
        
        if (argc == 3) {
            n = std::atoi(argv[2]);
            if (n <= 0) {
                std::cerr << "Invalid parking space count: " << n << std::endl;
                return 1;
            }
        }

        asio::io_service io_service;

        server s(io_service, std::atoi(argv[1]), n);

        io_service.run();
    } catch (std::exception& e) {
        std::cerr << "Exception: " << e.what() << "\n";
    }
    return 0;
}
