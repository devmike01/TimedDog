package devmike.leviapps.co.timeddogx.services;// Created by Gbenga Oladipupo(Devmike01) on 9/13/20.


public interface TimeOutBinderImpl {
    Runnable onStartCounting(long timeoutMillis, String logoutClassName);
    //void onTouch();
    boolean isCancelled();
}
