package devmike.leviapps.co.timeddogx.services;// Created by Gbenga Oladipupo(Devmike01) on 4/2/21.


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimedDogExecutorService implements ExecutorServiceContract {


    private final ExecutorService mExecutorService;

    private static TimedDogExecutorService timeExecutor;

    public static TimedDogExecutorService getInstance(){
        if (timeExecutor ==null){
            timeExecutor =  new TimedDogExecutorService();
        }
        return timeExecutor;
    }


    public TimedDogExecutorService(){
        this.mExecutorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void onRun(Runnable runnable) {
        mExecutorService.execute(runnable);
    }

    @Override
    public void shutdown() {
        mExecutorService.shutdown();
    }


}
