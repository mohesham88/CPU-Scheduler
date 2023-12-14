import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Scheduler {

    ArrayList<Process> processes;
    Queue<Process> runningQueue;
    Queue<Process> waitingQueue;

    ArrayList<Process> dieList;


    public Scheduler(ArrayList<Process> processes){
        this.processes = processes;
        runningQueue = new LinkedList<Process>();
        waitingQueue = new LinkedList<Process>();
        for(Process p : processes){
            waitingQueue.add(p);
        }
    }

    public abstract void runScheduler();

}
