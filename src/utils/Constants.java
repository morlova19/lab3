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
     public static final String FAILED ="F";
     public static final String FULL_FAILED ="FAILED";

     public static final int LOW = 0;
     public static final String FULL_LOW = "LOW";
     public static final int NORMAL = 1;
     public static final String FULL_NORMAL = "NORMAL";
     public static final int HIGH = 2;
     public static final String FULL_HIGH = "HIGH";

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

     public  int getNORMAL() {
         return NORMAL;
     }

     public  int getHIGH() {
         return HIGH;
     }

     public  String getFullCompleted() {
         return FULL_COMPLETED;
     }

     public  String getFullNew() {
         return FULL_NEW;
     }

     public  String getFullPerforming() {
         return FULL_PERFORMING;
     }

     public  String getFullCancelled() {
         return FULL_CANCELLED;
     }

     public  String getFullFailed() {
         return FULL_FAILED;
     }

     public  String getFullLow() {
         return FULL_LOW;
     }

     public  String getFullNormal() {
         return FULL_NORMAL;
     }

     public  String getFullHigh() {
         return FULL_HIGH;
     }
 }
