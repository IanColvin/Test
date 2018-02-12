import java.util.Date;
import java.io.*;
import java.util.*;

/**
* This program will log information for a task, including start time, stop time, description, and provide a summary for a single
* task or all tasks.
*
* Referenced https://docs.oracle.com/javase/7/docs/api/java/util/Date.html for date code
* Referenced https://www.tutorialspoint.com/java/io/index.htm for creating files
* Referenced https://stackoverflow.com/questions/18549704/java-filewriter-create-a-new-line for adding a new line
*
* @author Ian Colvin
* @version Project 1          
*/

public class TM {

   TaskLog t;
      
   private void appMain(String[] args) {
   
         t = new TaskLog("Log.txt");
         
         switch(args[0]) {
            case "start": cmdStartAndStop(args[0], args[1]);
                          break;
            case "stop": cmdStartAndStop(args[0], args[1]);
                          break;
            case "describe": cmdDescribe(args[1], args[2]);
                          break;
            case "summary": 
                          if(args.length == 1)
                          cmdSummary();
                          else
                          cmdSummary(args[1]);
                          break;
                          
            default: System.out.println("Error");
                     break;
         }
   }

   public static void main(String[] args){
       TM tm = new TM();
       tm.appMain(args);
   }
   
   //	Logs the start time of a task with name <task name>
   private void cmdStartAndStop(String command, String taskName) {
       t.recordStartStop(command,taskName);
   }
      
   //	Logs the description of the task with name <task name>
   private void cmdDescribe(String taskName, String taskDescription) {
      t.recordDescribe(taskName,taskDescription);
   }
   
   // Provides a report of the activity and total time spent 
   // working on task with name <task name>
   private void cmdSummary(String taskName) {
      t.printSummary(taskName);   
   }
   
   // Provies a report ofo the activity and total time spent 
   // working on ALL tasks
   private void cmdSummary() {
      t.printSummary();     
   }
}

class TaskLog {
   
   private String logName;   
   
   public TaskLog(String logName) {
      this.logName = logName;
   }
   
   // Takes a task and prints out the task name, description and time spend in seconds.
   public void printSummary(String taskName) {
    int startTime = 0, sumTime = 0 ;   
    String description = "";
    
      try {
         Scanner s = new Scanner(new File(logName));
         
         while(s.hasNext())
         {
            if(s.next().equals(taskName)) {
               String command = s.next();
               
               if(command.equals("start")){
                  startTime = s.nextInt();
               } else if(command.equals("stop")) {
                  sumTime += s.nextInt() - startTime;
               } else if(command.equals("description")) {
                 description += s.nextLine();
               }
               
            }
            if(s.hasNextLine())
               s.nextLine();
         }
          } catch (Exception e) {}          

      System.out.println("Task: " + taskName);
      System.out.println("Description: " + description);
      System.out.println("Time Spent: " + sumTime + " Seconds");
      System.out.println();
   }
   
   // Finds all the seperate tasks in the log file and passes each one to printSummary.
   public void printSummary() {
         Set<String> summarySet  = new TreeSet<String>();
      
       try {
         Scanner s = new Scanner(new File(logName));
         
         while(s.hasNextLine())
         {
            summarySet.add(s.next());
            s.nextLine();
         }
          } catch (Exception e) {}     
               
      for(String i : summarySet)
         printSummary(i); 
   }
   
   // Returns a time stamp in seconds.
   private String getTimeStamp(){
      Date timeStamp = new Date();
      
      return "" + timeStamp.getTime() / 1000;
   }
   
   // Records the Start/Stop command and task name in format: Task Command Time
   public void recordStartStop(String command, String taskName){
         try {
         FileWriter out = new FileWriter(logName,true);
         out.append(taskName + " " + command + " " + getTimeStamp()+ System.lineSeparator());
         out.close();
      } catch (IOException e) {};
   }

   // Records the Description command and task name in format: Task Command Description
   public void recordDescribe(String taskName, String taskDescription){
        try {
         FileWriter out = new FileWriter(logName,true);
         out.append(taskName + " description " + taskDescription + System.lineSeparator());
         out.close();
      } catch (IOException e) {};
   }
}