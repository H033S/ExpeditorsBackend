package ttl.larku.week2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArrayDemo {

   public void main() {
//      arrayDemo();
//      listDemo();
      doSomeWork(10);

   }

   public void doSomeWork(int amount) {

      int [] result = getInfoFromDataBase(amount);

      for(int i = 0; i < result.length; i++) {
         System.out.println("i: " + i);
      }

   }


   public int [] getInfoFromDataBase(int amount) {


      int [] result = new int[amount];

      //Go get data from DB and return result
      for(int i = 0; i < result.length; i++) {
         result[i] = i;
      }

      return result;
   }

   public void arrayDemo() {
      int i = 10;

      int [] iarr;


      iarr = new int[10];

      for(int index = 0; index < iarr.length; index++) {
         iarr[index] = 10;
      }

      int [] iarr2 = iarr;

      System.out.println("iarr[11]: " + iarr[10]);
   }

   public void problemWithArrays() {
      int i = 10;

      int [] iarr;


      iarr = new int[10];

      for(int index = 0; index < iarr.length; index++) {
         iarr[index] = 10;
      }

      int [] iarr2 = iarr;

      System.out.println("iarr[11]: " + iarr[10]);
   }

   public void listDemo() {
      Set<Integer> sints = new HashSet<>();
      List<Integer> lints = new ArrayList<>();
      Map<String, Integer> mints = new HashMap<>();

      lints.add(220);

      int i = lints.get(0);

      for(int index = 0; index < 25; index++) {
         lints.add(index);
      }

      lints.add(10);

      System.out.println(lints);

   }


}
