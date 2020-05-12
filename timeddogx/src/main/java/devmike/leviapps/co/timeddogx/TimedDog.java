package devmike.leviapps.co.timeddogx;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.lifecycle.Transformations;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.impl.model.PreferenceDao_Impl;
import devmike.leviapps.co.timeddogx.utils.TimeDogAppLifecycle;

/**
 * Created by Gbenga Oladipupo on 12/05/2020.
 */
public class TimedDog implements Observer<WorkInfo>, TimeDogAppLifecycle.OnTimeDogAppLifecycleListener {

    private OneTimeWorkRequest request;

    private static WorkManager workManager;

    private static TimedDog instance;

    private static final Object lock =new Object();

    static final String ARG_TIMEOUT =".ARG_TIMEOUT";

    private Data data;

    private MutableLiveData<TimedResult> timedResultLiveData;

    private LiveData<WorkInfo> workInfoLiveData;

    private SharedPreferences timedSharedPreference;


    @Deprecated
    public static void init(Application application){
        with(application);
    }

    public static TimedDog with(Application application){
        synchronized (lock){
            if(instance == null){
                instance = new TimedDog(application);
            }
            return instance;
        }

    }

    private TimedDog(Application application) {
        ProcessLifecycleOwner.get().getLifecycle().addObserver( new TimeDogAppLifecycle(this));
        timedSharedPreference =
                application.getSharedPreferences(TimedDogXWorker.BACKGROUND_TO_FOREGROUND,
                        Context.MODE_PRIVATE);
        workManager = WorkManager.getInstance(application);
        this.timedResultLiveData = new MutableLiveData<>();
    }

    public TimedDog duration(long timeInMillis){
        data = new Data.Builder()
                .putLong(ARG_TIMEOUT, timeInMillis)
                .build();
        return this;
    }

    public TimedDog start(){
        request = new OneTimeWorkRequest.Builder(TimedDogXWorker.class)
                .setInputData(data)
                .build();
        workManager.enqueue(request);
        if(workInfoLiveData ==null){
            workInfoLiveData =  workManager.getWorkInfoByIdLiveData(request.getId());
        }
        workInfoLiveData.observeForever(this);

        return this;
    }

    public LiveData<TimedResult> getTimedResultLiveData() {
        return timedResultLiveData;
    }

    @Override
    public void onChanged(WorkInfo workInfo) {
        if(workInfo != null){
            final boolean isForeground =
                    workInfo.getOutputData().getBoolean(TimedDogXWorker.OUTPUT_FOREGROUND, false);
            if(isForeground) {
                timedResultLiveData.postValue(TimedResult.set(false));
            }

        }
    }


    //public void done(){
     //   workInfoLiveData.removeObserver(this);
    //}

    @Override
    public void onResume() {
        boolean isLogout = timedSharedPreference.getBoolean(TimedDogXWorker.LOGOUT_CHECK, false);
        if (isLogout){
            timedResultLiveData.postValue(TimedResult.set(true));
            timedSharedPreference.edit().clear().apply();
        }
    }

    @Override
    public void onStop() {

    }
}
