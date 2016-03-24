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
     public static final int NORMAL = 1;
     public static final int HIGH = 2;

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
 }
