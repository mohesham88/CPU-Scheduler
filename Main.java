import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        // Create an array list of processes
        ArrayList<Process> processes = new ArrayList<>();
        
        // Add processes with name, arrival time, burst time, and priority
        processes.add(new Process("P1", 0, 6, 4));
        processes.add(new Process("P2", 3, 4, 9));
        processes.add(new Process("P3", 4, 3, 3));
        processes.add(new Process("P4", 2, 5, 8));

        // Create SJF scheduler instance
        // SJFScheduler sjfScheduler = new SJFScheduler(processes);

        Scheduler agScheduler = new AG_Scheduler(processes, 4);
        // Run the scheduler
        agScheduler.runScheduler();

    }
}
