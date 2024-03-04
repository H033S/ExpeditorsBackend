package expeditors.backend;

import java.util.concurrent.ThreadLocalRandom;

public class RandomStudent {

   public void main(String[] args) {

      String[] name = {
            "Alan Aguillon Juarez",
            "Antonio Nazco",
            "Antony Alfaro",
            "Arjun Panikar",
            "Carla Cairns",
            "Edwin Soto",
            "Jesus Cortez Valdez",
            "Juan De Dios Hernandez",
            "Julio Cesar Rodriguez",
            "Komal Patel",
            "Lokesh Gopi",
            "Lucas Maesaka",
            "Mainor Lobo",
            "Marcus Silva",
            "Raul Gomez",
            "Rohit Aherwadkar",
            "Tetyana Alvarado",
      };


      for(int i = 0; i < 10; i++) {
         String randomStudent = getRandomStudent(name);
         System.out.println("Random Student: " + randomStudent);
      }
   }


   public String getRandomStudent(String [] arr) {

      int rNum = ThreadLocalRandom.current().nextInt(arr.length);
      String s = arr[rNum];
      return s;
   }
}


