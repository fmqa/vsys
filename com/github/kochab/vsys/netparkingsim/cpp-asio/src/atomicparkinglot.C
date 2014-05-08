#include "atomicparkinglot.H"

vsys::atomic_parking_lot::atomic_parking_lot(int cap) 
    : count(), capacity(cap) 
{
}

bool vsys::atomic_parking_lot::park() {
    if (count < capacity) {
        ++count;
        return true;
    }
    return false;
}

bool vsys::atomic_parking_lot::unpark() {
    if (count > 0) {
        --count;
        return true;
    }
    return false;
}

int vsys::atomic_parking_lot::remaining() {
    return capacity - count;
}
