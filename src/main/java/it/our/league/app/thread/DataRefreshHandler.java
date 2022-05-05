package it.our.league.app.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class DataRefreshHandler {

    @Autowired
    private ApplicationContext applicationContext;

    private Future<Integer> future;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public synchronized Future<Integer> startDataRefresh() {
        if (future == null || future.isDone()){
            DataRefreshCallable callable = new DataRefreshCallable();
            applicationContext.getAutowireCapableBeanFactory().autowireBean(callable);
            future = executor.submit(callable);
        }
        return future;
    }

}
