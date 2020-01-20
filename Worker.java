package master_worker;

import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Worker implements Runnable{
    private ConcurrentLinkedQueue<Task> queue;
    private ConcurrentHashMap<String,Object> result;

    public void setQueue(ConcurrentLinkedQueue<Task> queue) {
        this.queue = queue;
    }

    public void setResult(ConcurrentHashMap<String, Object> result) {
        this.result = result;
    }

    //真正业务逻辑
    public Object handle(Task input){
        Object output=null;
        try {
            Thread.sleep(500);
            output=input.getPrice();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return output;
    }

    @Override
    public void run() {
        while(true){
            Task input=this.queue.poll();
            if(input==null){
                break;
            }
            //开始执行业务处理
            Object output=handle(input);
            this.result.put(Integer.toString(input.getId()),output);
        }
    }
}
