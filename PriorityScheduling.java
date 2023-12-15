import java.util.*;

public class PriorityScheduling extends Scheduler {

    public PriorityScheduling(ArrayList<Process> processes) {
        super(processes);
        readyQueue = new PriorityQueue<>(Comparator.comparingInt(Process::getPriority));
    }

    @Override
    public void runScheduler() {
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // Adding processes that have arrived to the ready queue
            while (!processes.isEmpty() && processes.get(0).getArrivalTime() <= currentTime) {
                readyQueue.add(processes.remove(0));
            }

            if (!readyQueue.isEmpty()) {

                // implement Aging to solve the starvation problem
                for(Process p : readyQueue){
                    if(p.priority > 0){
                        p.priority++;
                    }
                }

                Process currentProcess = readyQueue.poll();
                int waitingTime = currentTime - currentProcess.getArrivalTime();
                totalWaitingTime += waitingTime;
                totalTurnaroundTime += waitingTime + currentProcess.getBurstTime();

                currentTime += currentProcess.getBurstTime();
                currentProcess.setTurnAroundTime(waitingTime + currentProcess.getBurstTime());
                currentProcess.setWaitingTime(waitingTime);



                dieList.add(currentProcess);
            } else {
                currentTime++;
            }
        }

        double avgWaitingTime = (double) totalWaitingTime / dieList.size();
        double avgTurnaroundTime = (double) totalTurnaroundTime / dieList.size();

        // Print Processes execution order
        for (Process p : dieList) {
            System.out.println("Process " + p.getProcessName() + " executed.");
        }

        // Print Waiting Time for each process
        for (Process p : dieList) {
            System.out.println("Waiting Time for Process " + p.getProcessName() + ": " + p.getWaitingTime());
        }

        // Print Turnaround Time for each process
        for (Process p : dieList) {
            System.out.println("Turnaround Time for Process " + p.getProcessName() + ": " + p.getTurnAroundTime());
        }

        // Print Average Waiting Time and Average Turnaround Time
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }

    // Additional methods or modifications can be added as needed
}

