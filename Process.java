public class Process {
    String name;
    int arrivalTime;
    int burstTime;
    int priority;
    int turnAroundTime;
    int responseTiem;

    Process(String name, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public String getProcessName() {
        return name;
    }

    public void setProcessName(String processName)
    {
        this.name = processName;
    }

    public int getBurstTime()
    {
        return burstTime;
    }

    public void setBurstTime(int burstTime)
    {
        this.burstTime = burstTime;
    }

    public int getArrivalTime()
    {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

}
