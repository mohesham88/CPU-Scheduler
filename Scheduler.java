import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Scheduler {

    ArrayList<Process> processes;
    Queue<Process> readyQueue;


    ArrayList<Process> dieList;


    public Scheduler(ArrayList<Process> processes){
        this.processes = processes;
    }

    public abstract void runScheduler();

}
