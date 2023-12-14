import java.util.*;

public class AG_Scheduler extends Scheduler{

    HashMap<Process, Integer> qunatumTime;
    Queue<Process> processAGFactorَQueue;
    public HashMap<Process, Integer> AG_Factor;


    public AG_Scheduler(ArrayList<Process> processes, int defualtQuantumTime) {
        super(processes);
        this.qunatumTime = new HashMap<Process, Integer>();
        for(Process p : processes){
            qunatumTime.put(p, defualtQuantumTime);
        }
        AG_Factor = new HashMap<>();
        readyQueue = new LinkedList<>();
        processAGFactorَQueue = new PriorityQueue<>(new AG_Factor_Comperator());
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

        Process interruptedProcess = null;
        int runningTimeForLastProcess = 0;
        boolean takeFirstOfQueueNotLeastAg = false;
        while (!processes.isEmpty() || !readyQueue.isEmpty()){

            // add the process to the waiting queue if its arrival time matches
            for(int i = processes.size() - 1; i >= 0;i--){
                Process p = processes.get(i);
                if(p.arrivalTime <= currentTime){
                    processes.remove(i);
                    readyQueue.add(p);
                    int agFactor = calculateAGFactor(p);
                    AG_Factor.put(p, agFactor);
                    System.out.println("Process " + p.name + " ag factor is = " + agFactor);
                }
            }

            // if the ready queue is empty all process finished for now
            // waiting for more process from the process arraylist
            if(readyQueue.isEmpty()){
                currentTime++;
                continue;
            }

            // get the process with the least ag factor
            processAGFactorَQueue.clear();
            processAGFactorَQueue.addAll(readyQueue);
            // get process with least ag factor in the queue;
            Process processWithLeastAGFactor = processAGFactorَQueue.peek();
            // in case of first scenario take first in queue not least ag
            if(takeFirstOfQueueNotLeastAg){
                processWithLeastAGFactor = readyQueue.peek();
                takeFirstOfQueueNotLeastAg = false;
            }

            int processQuantumTime = qunatumTime.get(processWithLeastAGFactor);


            // same process is running
            if(interruptedProcess == processWithLeastAGFactor){
                //The running process used all its quantum time and it still have job to
                //do (add this process to the end of the queue, then increases its
                //Quantum time by (ceil(10% of the (mean of Quantum))) ).




                if(processWithLeastAGFactor.burstTime == 0){
                    // process finished
                    removeProccessFromReadyQueue(processWithLeastAGFactor);
                    dieList.add(processWithLeastAGFactor);
                    runningTimeForLastProcess = 0;
                    System.out.println( processWithLeastAGFactor.name + " Finished");
                    continue;
                }

                if(runningTimeForLastProcess == qunatumTime.get(processWithLeastAGFactor)){
                    System.out.println("Process " + processWithLeastAGFactor.name + " Finished it's quantum time and will be put in the queue");
                    // add the process to the end of the queue
                    removeProccessFromReadyQueue(processWithLeastAGFactor);
                    readyQueue.add(processWithLeastAGFactor);
                    // update quantum time
                    int updatedQuantumTime = (int)Math.ceil(0.1 * calculateMeanQuantamTime())+ qunatumTime.get(processWithLeastAGFactor);
                    qunatumTime.put(processWithLeastAGFactor, updatedQuantumTime);

                    takeFirstOfQueueNotLeastAg = true;
                    runningTimeForLastProcess = 0;

                }
            runningTimeForLastProcess++;
            processWithLeastAGFactor.burstTime--;
            currentTime++;
            System.out.println("time = " + currentTime + " process running is " + processWithLeastAGFactor.name);
            }else {

                if (processWithLeastAGFactor.burstTime <= Math.ceil(processQuantumTime / 2.0)) {
                    //The running process finished its job (set its quantum time to zero
                    //and remove it from ready queue and add it to the die list).
                    System.out.println("$ Executing (non-preemtive) Process " + processWithLeastAGFactor.name + " From time " + currentTime + " to " + (currentTime + processWithLeastAGFactor.burstTime));
                    System.out.println(processWithLeastAGFactor.name + " Finished");
                    // remove process from the ready Queue
                    removeProccessFromReadyQueue(processWithLeastAGFactor);
                    dieList.add(processWithLeastAGFactor);
                    currentTime += processWithLeastAGFactor.burstTime;
                    qunatumTime.put(processWithLeastAGFactor, 0);
                    processWithLeastAGFactor.burstTime -= processWithLeastAGFactor.burstTime;
                    interruptedProcess = null; // no interrupted process the process finished
                } else {
                    // The running process didn’t use all its quantum time based on another
                    //process converted from ready to running (add this process to the end
                    //of the queue, and then increase its Quantum time by the remaining
                    //unused Quantum time of this process).


                    System.out.println("$ Executing Process (non-preemtive) " + processWithLeastAGFactor.name + " From time " + currentTime + " to " + (currentTime + (int)Math.ceil(processQuantumTime / 2.0)));
                    currentTime += (int)Math.ceil(processQuantumTime / 2.0);
                    processWithLeastAGFactor.burstTime -= Math.ceil(processQuantumTime / 2.0);

                    // process is interrupted by another process
                    if(interruptedProcess != null){
                        // the interrupted process quantum time increases by the remaining quantum time
                        int interruptedProcessQuantumTime = qunatumTime.get(interruptedProcess);
                        int remainingUnusedQuantumTimeForInterruptedProcess = (int) (interruptedProcessQuantumTime - Math.ceil(interruptedProcessQuantumTime / 2.0));
                        qunatumTime.put(interruptedProcess, qunatumTime.get(interruptedProcess) + remainingUnusedQuantumTimeForInterruptedProcess);
                        // if the process is interrupted add it to the end of the queue
                        removeProccessFromReadyQueue(interruptedProcess);
                        readyQueue.add(interruptedProcess);
                    }
                    interruptedProcess = processWithLeastAGFactor; // for the new loop
                    runningTimeForLastProcess = (int)Math.ceil(processQuantumTime / 2.0);
                }
            }
        }

    }


    public int calculateMeanQuantamTime(){
        int mean = 0, queueSize = readyQueue.size();
        while (queueSize-- > 0){
            Process p = readyQueue.poll();
            mean += qunatumTime.get(p);
            readyQueue.add(p);
        }
        mean /= readyQueue.size();
        return mean;
    }


    public void removeProccessFromReadyQueue(Process p){
        int  queueSize = readyQueue.size();
        while (queueSize-- > 0){
            Process process = readyQueue.poll();
            // System.out.println("Desired p " + p.name);
            if(process != p){
                readyQueue.add(process);
                // System.out.println(process.name + ",");
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
