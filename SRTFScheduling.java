import java.util.ArrayList;
import java.util.LinkedList;

public class SRTFScheduling extends Scheduler {
    public SRTFScheduling(ArrayList<Process> processes) {
        super(processes);
    }

    @Override
    public void runScheduler() {
        readyQueue = new LinkedList<>();
        dieList = new ArrayList<>();
        int currentTime = 0;
        int totalProcesses = processes.size(); // Store the total number of processes

        System.out.println("Processes Execution Order:");
        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // Add arrived processes to the readyQueue
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));
            }

            if (!readyQueue.isEmpty()) {
                Process shortest = readyQueue.poll(); // Retrieve the shortest process
                System.out.println("Executing process " + shortest.name + " from time " + currentTime + " to " + (currentTime + 1));
                currentTime++;

                shortest.remainingTime--;

                if (shortest.remainingTime == 0) {
                    shortest.completed = true;
                    shortest.turnAroundTime = currentTime - shortest.arrivalTime;
                    shortest.waitingTime = shortest.turnAroundTime - shortest.burstTime;
                    dieList.add(shortest);
                }

                for (Process p : readyQueue) {
                    if (!p.completed && p != shortest) {
                        p.waitingTime++;
                    }
                }
            } else {
                currentTime++;
            }

            // Break the loop if all processes are completed
            if (dieList.size() >= totalProcesses) {
                break;
            }
        }

        // Calculate statistics
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        for (Process p : dieList) {
            totalWaitingTime += p.waitingTime;
            totalTurnaroundTime += p.turnAroundTime;
        }

        double avgWaitingTime = (double) totalWaitingTime / totalProcesses;
        double avgTurnaroundTime = (double) totalTurnaroundTime / totalProcesses;

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
}
