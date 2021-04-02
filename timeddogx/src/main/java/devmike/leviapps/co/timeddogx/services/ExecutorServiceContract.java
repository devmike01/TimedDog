package devmike.leviapps.co.timeddogx.services;// Created by Gbenga Oladipupo(Devmike01) on 4/2/21.


public interface ExecutorServiceContract {
    void onRun(Runnable runnable);
    void shutdown();
}
