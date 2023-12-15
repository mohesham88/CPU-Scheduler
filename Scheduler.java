import java.util.ArrayList;
import java.util.Queue;

public abstract class Scheduler {

    ArrayList<Process> processes;
    Queue<Process> readyQueue;


    ArrayList<Process> dieList;


    public Scheduler(ArrayList<Process> processes){
        dieList = new ArrayList<>();
        this.processes = processes;
    }

    public abstract void runScheduler();

}
