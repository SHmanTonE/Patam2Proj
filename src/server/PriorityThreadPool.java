package server;

import java.util.concurrent.*;

public class PriorityThreadPool extends ThreadPoolExecutor {

    public PriorityThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    private class Helper<V> extends FutureTask<V> implements Runnable,Comparable<Helper<V>> {

        private int priority;

        public Helper(Callable<V> callable,int priority) {
            super(callable);
            this.priority = priority;
        }
        public Helper(Runnable runnable,V result ,int priority) {
            super(runnable,result);
            this.priority = priority;
        }

        @Override
        public int compareTo(Helper<V> o) {
            return this.priority - o.priority ;
        }
    }



    public <V> void execute(Runnable r,V result,int priority){
        super.execute(newTaskFor(r,result,priority));
    }

    public <V> Future<V> submit(Callable<V> c, int priority){
        RunnableFuture<V> newTask = newTaskFor(c,priority);
        execute(newTask);
        return newTask;
    }

    protected <V> RunnableFuture<V> newTaskFor (Runnable runnable,V result , int priority){
        return new Helper<V>(runnable,result,priority);
    }
    protected <V> RunnableFuture<V> newTaskFor(Callable callable , int priority){
        return new Helper<V>(callable,priority);
    }

}
