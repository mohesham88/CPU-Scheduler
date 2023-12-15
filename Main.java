import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        // // Create an array list of processes
        // ArrayList<Process> processes = new ArrayList<>();

        // // Add processes with name, arrival time, burst time, and priority
        // processes.add(new Process("P1", 0, 6, 3));
        // processes.add(new Process("P2", 2, 8, 1));
        // processes.add(new Process("P3", 3, 7, 2));
        // processes.add(new Process("P4", 5, 3, 4));

        // // Create SJF scheduler instance
        // SJFScheduler sjfScheduler = new SJFScheduler(processes,2);

        // // Run the scheduler
        // sjfScheduler.runScheduler();

        ArrayList<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", 0, 7, 2));
        processes.add(new Process("P2", 2, 4, 3));
        processes.add(new Process("P3", 4, 1, 1));
        processes.add(new Process("P4", 5, 4, 4));

        PriorityScheduling scheduler = new PriorityScheduling(processes);
        scheduler.runScheduler();   


    }
}
