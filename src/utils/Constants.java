package utils;


 final public class Constants {
    public static final String COMPLETED ="A";
    public static final String FULL_COMPLETED ="ACCOMPLISHED";
    public static final String NEW ="N";
    public static final String FULL_NEW ="NEW";
    public static final String PERFORMING ="P";
    public static final String FULL_PERFORMING ="IN PROGRESS";
    public static final String CANCELLED ="C";
    public static final String FULL_CANCELLED ="CANCELLED";

     public static final int LOW = 0;
     public static final int MEDIUM = 1;
     public static final int HIGH = 2;
     public static final String NEWTASK_ACTION="newtask";
     public static final String NEWSUBTASK_ACTION="newsubtask";
     public static final String DELETETASK_ACTION="deletetask";
     public static final String DELETESUBTASK_ACTION="deletesubtask";
     public  String getCANCELLED() {
         return CANCELLED;
     }

     public  String getCOMPLETED() {
         return COMPLETED;
     }

     public  String getNEW() {
         return NEW;
     }

     public String getPERFORMING() {
         return PERFORMING;
     }

     public  int getLOW() {
         return LOW;
     }

     public  int getMEDIUM() {
         return MEDIUM;
     }

     public  int getHIGH() {
         return HIGH;
     }
 }
