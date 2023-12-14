import java.util.*;

public class AG_Scheduler extends Scheduler{

    HashMap<Process, Integer> qunatumTime;
    public HashMap<Process, Integer> AG_Factor;

    public AG_Scheduler(ArrayList<Process> processes, int defualtQuantumTime) {
        super(processes);
        this.qunatumTime = new HashMap<Process, Integer>();
        for(Process p : processes){
            qunatumTime.put(p, defualtQuantumTime);
        }
        AG_Factor = new HashMap<>();
        readyQueue = new PriorityQueue<>(new AG_Factor_Comperator());
    }


    public int calculateAGFactor(Process p){
        Random random = new Random();
        // random integer value from 0 to 20
        int RF = random.nextInt(21);
        int AG_Factor = 0;

        if (RF < 10) {
            AG_Factor = RF + p.arrivalTime + p.burstTime;
        } else if (RF > 10) {
            AG_Factor = 10 + p.arrivalTime + p.burstTime;
        } else {
            AG_Factor = p.priority + p.arrivalTime + p.burstTime;
        }
        return AG_Factor;
    }

    @Override
    public void runScheduler() {
        int currentTime = 0;

        while (!processes.isEmpty()){
            // add the process to the waiting queue if its arrival time matches
            for(Process p : processes){
                if(p.arrivalTime == currentTime){
                    processes.remove(p);
                    readyQueue.add(p);
                    int agFactor = calculateAGFactor(p);
                    AG_Factor.put(p, agFactor);
                }
            }

            // get the process with the least ag factor
            Process processWithLeastAGFactor = readyQueue.peek();

            int processQuantumTime = qunatumTime.get(processWithLeastAGFactor);


            if(processWithLeastAGFactor.burstTime <= Math.ceil(processQuantumTime / 2.0)){
                //The running process finished its job (set its quantum time to zero
                //and remove it from ready queue and add it to the die list).
                readyQueue.poll();
                dieList.add(processWithLeastAGFactor);
                currentTime += processWithLeastAGFactor.burstTime;
                qunatumTime.put(processWithLeastAGFactor, 0);
            }else{
                // The running process didn’t use all its quantum time based on another
                //process converted from ready to running (add this process to the end
                //of the queue, and then increase its Quantum time by the remaining
                //unused Quantum time of this process).
                currentTime += Math.ceil(processQuantumTime / 2.0);
                processWithLeastAGFactor.burstTime -= Math.ceil(processQuantumTime / 2.0);
                int remainingUnusedQuantumTime = (int) (processQuantumTime - Math.ceil(processQuantumTime / 2.0));
                qunatumTime.put(processWithLeastAGFactor, qunatumTime.get(processWithLeastAGFactor) + remainingUnusedQuantumTime);
            }

        }

    }








    public class AG_Factor_Comperator implements Comparator<Process> {

        @Override
        public int compare(Process p1, Process p2) {
            int value = AG_Factor.get(p1) - AG_Factor.get(p2);
            if (value > 0) {
                return 1;
            }
            else if (value < 0) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

}
