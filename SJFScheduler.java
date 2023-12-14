import java.util.ArrayList;
import java.util.LinkedList;

public class SJFScheduler extends Scheduler {

    public SJFScheduler(ArrayList<Process> processes) {
        super(processes);
    }

    @Override
    public void runScheduler() {
        dieList = new ArrayList<>();
        readyQueue = new LinkedList<>();
        int currentTime = 0;

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

                System.out.println("Executing process " + shortest.name +" from time " + currentTime + " to " + (currentTime + shortest.burstTime));
                currentTime += shortest.burstTime;
                readyQueue.remove(shortest);
                dieList.add(shortest);
            } else {
                currentTime++; // No process available yet, move to the next time unit
            }
        }
    }
}
