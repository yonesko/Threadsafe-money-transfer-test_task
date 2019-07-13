package glebio.bank.processing;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Gleb Danichev
 */
public class TaskExecutor {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public void schedule(Runnable runnable) {
        executorService.execute(runnable);
    }
}
