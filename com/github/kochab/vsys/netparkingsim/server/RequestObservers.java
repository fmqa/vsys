package com.github.kochab.vsys.netparkingsim.server;

public enum RequestObservers implements RequestObserver {
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
