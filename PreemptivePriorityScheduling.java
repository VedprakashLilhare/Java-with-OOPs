import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PreemptivePriorityScheduling {

    public static void main(String[] args) {
        // Create a list of processes
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", 10, 1));
        processes.add(new Process("P2", 15, 2));
        processes.add(new Process("P3", 20, 3));

        // Sort the processes by priority
        Collections.sort(processes, (p1, p2) -> p1.priority - p2.priority);

        // Print the processes
        for (Process process : processes) {
            System.out.println(process);
        }

        // Run the preemptive priority-scheduling algorithm
        runPreemptivePriorityScheduling(processes);
    }

    private static void runPreemptivePriorityScheduling(List<Process> processes) {
        // Create a ready queue
        List<Process> readyQueue = new ArrayList<>();

        // Initialize the time
        int time = 0;

        // While there are still processes to be scheduled
        while (!processes.isEmpty()) {
            // Get the process with the highest priority from the ready queue
            Process currentProcess = readyQueue.remove(0);

            // Execute the process for one time unit
            currentProcess.burstTime--;

            // If the process has finished executing
            if (currentProcess.burstTime == 0) {
                // Remove the process from the ready queue
                readyQueue.remove(currentProcess);

                // Print the process's completion time
                System.out.println(currentProcess + " completed at " + time);
            } else {
                // Add the process back to the ready queue
                readyQueue.add(currentProcess);
            }

            // Increment the time
            time++;
        }
    }
}

class Process {

    String name;
    int burstTime;
    int priority;

    public Process(String name, int burstTime, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", burstTime=" + burstTime +
                ", priority=" + priority +
                '}';
    }
}