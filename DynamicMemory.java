 import java.io.*;
 import java.util.*;

 class Partition {
   private int size;
   private int startingAddress;
   private int endAddress;
   private String processId;
   private int internalFragmentation;
   private String status;
   public Partition(int size, int startingAddress,int endAddress) {
   this.size = size;
   this.startingAddress = startingAddress;
   this.endAddress=endAddress;
   this.processId = "Null";
   this.internalFragmentation = -1;
   this.status = "free"; // set default status to H (hole)
   }
   public Partition(){
   }
   
   public int getSize() {
   return size; 
   }
   public void setSize(int size) {
   this.size = size;
   }
   public int getStartingAddress() {
   return startingAddress;
   }
   
   public void setStartingAddress(int startingAddress) {
   this.startingAddress = startingAddress;
   }
   
   public int getEndingAddress() {
   return endAddress ;
   } 
   public void setEndingAddress(int endAddress) {
   this.endAddress = endAddress;
   }
   
   public String getProcessId() {
   return processId;
   }
   
   public void setProcessId(String processId) {
   this.processId = processId;
   }
   
   public int getInternalFragmentation() {
   return internalFragmentation;
   }
   
   public void setInternalFragmentation(int internalFragmentation) {
   this.internalFragmentation = internalFragmentation;
   }
   
   public String getStatus() {
   return status;
   }
   public void setStatus(String status) {
   this.status = status;
    }
   }


 public class DynamicMemory {
 static Scanner input = new Scanner(System.in);

 public static void main(String[] args) {
 
 try (Scanner input = new Scanner(System.in)) {
    int ST_addr,EN_addr;
     int M;
     System.out.println("Enter the number of memory partitions:");
     M = input.nextInt();
     Partition[] memory = new Partition[M];
     for (int i = 0; i < M; i++) {
     System.out.printf("Enter the size of partition %d in KB: ", i + 1);
     int partitionSize = input.nextInt();
     if (i == 0) {
     ST_addr = 0;
     } else {
     ST_addr = memory[i-1].getEndingAddress()+1; /// 1 update
     }
     EN_addr = ST_addr + partitionSize -1;
     
     memory[i] = new Partition(partitionSize, ST_addr, EN_addr);
     }
     
     System.out.print("Enter the allocation strategy (F = First-fit, B = Best-fit, W = Worst-fit): ");
     String strategy = input.next();
     
     boolean isRunning = true;
     while (isRunning) {
     System.out.println("\nEnter your choice:\n"
     + "1. Allocate memory\n"
     + "2. Deallocate memory\n"
     + "3. Display memory status\n"
     + "4. Exit");
     int choice = input.nextInt();
    
     int processSize=0;
     String processID="";
     switch (choice) {
     case 1:
     boolean dup=true;
     while ( dup==true){
     System.out.print("Enter the process ID and size (separated by a space): ");
     processID = input.next();
     processSize = input.nextInt();
     
     //check duplicates
     for (Partition m1 : memory) {
     if (!m1.getProcessId().equals(processID)) {
     } else {
     System.out.println("Duplicate Process name");
     dup=true;
     break;
     }
     dup=false ;
     }
     
     };
     
     allocateProcess(processID, processSize, strategy, memory);
     break;
     case 2:
     System.out.print("Enter the process ID to deallocate: ");
     String processIDToDeallocate = input.next();
     deallocateProcess(processIDToDeallocate,memory);
     break;
     case 3:
     printarray(memory);
     displayMemoryState(memory);
     writeToFile(memory);
     break;
     case 4:
     isRunning = false;
     break;
     default:
     System.out.println("Invalid choice");
     }
     }
}
 }
static void allocateProcess(String processID, int processSize, String strategy,Partition[] partitions) {
int partitionIndex = -1;
 int fragmentation ;
 
 // Determine the partition to allocate the process in based on the allocation strategy
 switch (strategy) {
 case "F":
 partitionIndex = findFirstFitPartition(partitions, processSize);
 break;
 case "B":
 partitionIndex = findBestFitPartition(partitions, processSize);
 break;
 case "W":
 partitionIndex = findWorstFitPartition(partitions, processSize);
 break;
 }
 
 // If a suitable partition is found, allocate the process
 if (partitionIndex != -1) {
 partitions[partitionIndex].setProcessId(processID);
 partitions[partitionIndex].setStatus("allocated");
 fragmentation = partitions[partitionIndex].getSize() - processSize;
 partitions[partitionIndex].setInternalFragmentation(fragmentation);
 System.out.println("Process " + processID + " of size " + processSize + " KB allocated in partition " + partitionIndex);
 } else {
 System.out.println("Unable to allocate process " + processID + " of size " +
processSize + " KB with " + strategy + " strategy.");
 }
 
 // Display the updated memory state
 displayMemoryState(partitions);
 
 }

 /**
 * Find the first free partition that can fit a process of the given size.
 * Returns the index of the partition, or -1 if no such partition exists.
 */
 private static int findFirstFitPartition(Partition[] partitions, int processSize) {
 for (int i = 0; i < partitions.length; i++) {
 Partition partition = partitions[i];
 if (partition.getStatus().equals("free") && partition.getSize() >= processSize
) {
 return i;
}
}
 return -1;
 }
 
 /**
 * Find the free partition that can fit a process of the given size and has the 
smallest
 * internal fragmentation.
 * Returns the index of the partition, or -1 if no such partition exists.
 */
 private static int findBestFitPartition(Partition[] partitions, int processSize) {
 int bestIndex = -1;
 int smallestFragmentation = Integer.MAX_VALUE;
 for (int i = 0; i < partitions.length; i++) {
 Partition partition = partitions[i];
 if (partition.getStatus().equals("free") && partition.getSize() >= processSize
) {
 int fragmentation = partition.getSize() - processSize;
 if (fragmentation < smallestFragmentation) {
 bestIndex = i;
 smallestFragmentation = fragmentation;
}
}
}
 return bestIndex;
 }
 
 /**
 * Find the free partition that can fit a process of the given size and has the 
largest
 * internal fragmentation.
 * Returns the index of the partition, or -1 if no such partition exists.
 */
 private static int findWorstFitPartition(Partition[] partitions, int processSize) {
 int worstIndex = -1;
 int largestFragmentation = -1;
 for (int i = 0; i < partitions.length; i++) {
 Partition partition = partitions[i];
 if (partition.getStatus().equals("free") && partition.getSize() >= processSize
) {
 int fragmentation = partition.getSize() - processSize;
 if (fragmentation > largestFragmentation) {
 worstIndex = i;
 largestFragmentation = fragmentation;
 }}}
 return worstIndex;
 }

 static void deallocateProcess(String processIDToDeallocate, Partition[] partitions) {
 boolean isFound = false;
 for (Partition partition : partitions) {
 if (partition.getProcessId().equals(processIDToDeallocate)) {
 partition.setProcessId("Null");
 partition.setStatus("free");
 partition.setInternalFragmentation(-1);
 isFound = true;
 }
 }
 if (!isFound) {
 System.out.println("Process " + processIDToDeallocate + " is not found in the memory.");
 }
 displayMemoryState(partitions);
 }

 // Display the memory state after allocation
 static void displayMemoryState(Partition[] partitions) {
 System.out.print("[");
 for (Partition partition : partitions) {
 if (partition.getStatus().equals("allocated")) {
 System.out.print(partition.getProcessId() + " | ");
 } else {
 System.out.print("H | ");
 }
 }
 System.out.println("]");
 }

 // Display detailed information about each partition and write it to the output file
 static void writeToFile(Partition[] partitions) {
 try {
 try (FileWriter writer = new FileWriter("C:\\ss\\Report.txt")) {
 writer.write("status\t"
 + " size\t"
 + "St_add\t"
 + "En_add\t"
 + "PID\t"
 + "fragmentation\n");
 for (Partition partition : partitions) {
 writer.write(partition.getStatus() + " " +
 partition.getSize() + "KB\t\t" +
 partition.getStartingAddress() + "\t\t" +
 partition.getEndingAddress() + "\t\t" +
 partition.getProcessId() + "\t\t" +
 partition.getInternalFragmentation() + "KB\n");
 }
 }
 System.out.println("Report written to file: Report.txt");
 } catch (IOException e) {
 System.out.println("An error occurred while writing to the file.");
 }
 }

 public static void printarray(Partition[] CMS) {
 System.out.printf("Part_no" + "\t" + "Part_sz" + "\t" + "ST_addr" + "\t" + "EN_addr"
 + "\t" + "P_nm " + "\t" + "P_fragm" + "\t" + " Part_st" + "\n");
 for (int i = 0; i < CMS.length; i++) {
 System.out.printf("%3d" + "\t" + "%3dKB" + "\t" + "%5dKB" + "\t" + "%5dKB" + "\t"
 + "%5s" + "\t" + "%3dKB" + "\t"
 + "%10s" + "\t" + "\n",i + 1, CMS[i].getSize(), CMS[i].getStartingAddress(), CMS[i].getEndingAddress(), CMS[i].getProcessId(),
 CMS[i].getInternalFragmentation(), CMS[i].getStatus());
 }
 }
 }