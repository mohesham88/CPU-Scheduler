import java.util.ArrayList;
import java.util.LinkedList;

public class SJFScheduler extends Scheduler {
    int contextSwitchTime; // Define your context switch time here

    public SJFScheduler(ArrayList<Process> processes,int contextSwitchTime) {
        super(processes);
        this.contextSwitchTime = contextSwitchTime;
    }

    @Override
    public void runScheduler() {
        readyQueue = new LinkedList<>();
        dieList = new ArrayList<>();
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        Process previousProcess = null; // Track the previous process for context switching

        System.out.println("Processes Execution Order:");
        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // Add arrived processes to the readyQueue
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));
            }

            if (!readyQueue.isEmpty()) {
                Process shortest = null;
                for (Process p : readyQueue) {
                    if (shortest == null || p.burstTime < shortest.burstTime) {
                        shortest = p;
                    }
                }

                // Account for context switch time if it's not the first process
                if (previousProcess != null) {
                    currentTime += contextSwitchTime;
                }

                System.out.println("Executing process " + shortest.name + " from time " + currentTime + " to " + (currentTime + shortest.burstTime));
                
                currentTime += shortest.burstTime;
                readyQueue.remove(shortest);
                dieList.add(shortest);

                shortest.waitingTime = currentTime - shortest.arrivalTime - shortest.burstTime;
                shortest.turnAroundTime = currentTime - shortest.arrivalTime;

                totalWaitingTime += shortest.waitingTime;
                totalTurnaroundTime += shortest.turnAroundTime;

                System.out.println("Waiting Time for " + shortest.name + ": " + shortest.waitingTime);
                System.out.println("Turnaround Time for " + shortest.name + ": " + shortest.turnAroundTime);

                previousProcess = shortest; // Update the previous process
            } else {
                currentTime++; // No process available yet, move to the next time unit
            }
        }

        double avgWaitingTime = (double) totalWaitingTime / dieList.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / dieList.size();

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
}
