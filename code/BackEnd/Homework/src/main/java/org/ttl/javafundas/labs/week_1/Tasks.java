package org.ttl.javafundas.labs.week_1;

public class Tasks {
    public static final char EOL = '\n';
    public enum Quality{
        A,B,C
    }

    public static String getVars(
            int id,
            String make_and_model,
            String color,
            boolean has_towing_package,
            float odometer_reading,
            float price,
            Quality quality_rating){

       return   STR."Vehicle ID: \{id}" + EOL +
                STR."Make / Model: \{make_and_model}" + EOL +
                STR."Color: \{color}" + EOL +
                STR."Has Towing Package: \{has_towing_package}" + EOL +
                STR."Odometer Reading: \{odometer_reading}" + EOL +
                STR."Price: \{price}" + EOL+
                STR."Quality: \{quality_rating.name()}";

    }

    public static void printNumberDivisibleBy3or7From_500to500(){
        for (int i = -500; i < 500; i++) {
            if(i%3==0 || i%7==0) System.out.println(i);
        }
    }

    public static int countNumberDivisibleBy3or7From_500to500(){
        int count = 0;
        for (int i = -500; i < 500; i++) {
            if(i%3==0 || i%7==0) count++;
        }

        return  count;
    }
}
