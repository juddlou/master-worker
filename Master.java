package master_worker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Master {
    private ConcurrentLinkedQueue<Task> queue=new ConcurrentLinkedQueue<>();

    private HashMap<String,Thread> workers=new HashMap<String,Thread>();

    private ConcurrentHashMap<String, Object> result=new ConcurrentHashMap<>();

    public Master(Worker worker,int workerCount){
        worker.setQueue(this.queue);
        worker.setResult(result);
        for(int i=0;i<workerCount;i++){
            workers.put(Integer.toString(i),new Thread(worker));
        }
    }

    //提交任务
    public void submit(Task task){
        this.queue.add(task);
    }

    //执行方法,启动应用程序，让所有worker工作
    public void execute(){
        for(Map.Entry<String,Thread> me:workers.entrySet()){
            me.getValue().start();
        }
    }

    //判断线程是否执行完毕
    public boolean isCompleted(){
        for(Map.Entry<String,Thread> me:workers.entrySet()){
            if(me.getValue().getState()!=Thread.State.TERMINATED){
                return false;
            }
        }
        return true;
    }

    //返回结果集数据
    public int getResult(){
        int i=0;
        for(Map.Entry<String,Object> resultMap:result.entrySet()){
            i+=(int)resultMap.getValue();
        }
        return i;
    }

}
