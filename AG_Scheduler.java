import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AG_Scheduler extends Scheduler{

    HashMap<Process, Integer> qunatumTime;

    public AG_Scheduler(ArrayList<Process> processes, int defualtQuantumTime) {
        super(processes);
        this.qunatumTime = new HashMap<Process, Integer>();
        for(Process p : processes){
            qunatumTime.put(p, defualtQuantumTime);
        }
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
}
