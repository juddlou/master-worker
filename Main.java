package master_worker;

public class Main {
    public static void main(String[] args) {
        Master master=new Master(new Worker(),10);

        for(int i=1;i<=100;i++){
            Task task=new Task(i,"test "+i,1);
            master.submit(task);
        }

        master.execute();

        long start=System.currentTimeMillis();
        while(true){
            if(master.isCompleted()){
                long end=System.currentTimeMillis()-start;
                int result=master.getResult();
                System.out.println("total time consumed "+end+", result is "+result);
                break;
            }
        }


    }
}
