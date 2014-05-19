package com.github.kochab.vsys.netparkingsim.server;

/**
 * Predefinied request observers.
 *
 * @author Eugen Kinder
 *
 */

public enum RequestObservers implements RequestObserver {
    /**
     * No-Op request observer.
     */
    NULL {
        @Override
        public void onFree() {}
        
        @Override
        public void onIn() {}
        
        @Override
        public void onOut() {}
        
        @Override
        public void onQuit() {}
        
        @Override
        public void onUnknown(String data) {}
    };
}
