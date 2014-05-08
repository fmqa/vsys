#ifndef ATOMICPARKINGLOT_H
#define ATOMICPARKINGLOT_H

#include <atomic>

namespace vsys {
    class atomic_parking_lot {
    public:
        atomic_parking_lot(int);
        bool park();
        bool unpark();
        int remaining();
    private:
        std::atomic<int> count;
        int capacity;
    };
}

#endif /* #ifndef ATOMICPARKINGLOT_H */