package org.ttl.ratingservice.app;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadDemo {
   public static void main(String[] args) {
      ThreadDemo td = new ThreadDemo();
      td.go();

   }

   public void go() {
      DataHolder dh = new DataHolder();
      Worker w1 = new Worker(dh);
      Thread th = new Thread(w1);

      Worker w2 = new Worker(dh);
      Thread th2 = new Thread(w2);

      th.start();
      th2.start();

      try {
         th.join();
         th2.join();
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }

      System.out.println("At end, value in dh: " + dh.getValue());
   }

   class Worker implements Runnable {

      private final DataHolder dh;

      public Worker(DataHolder dh) {
         this.dh = dh;
      }

      @Override
      public void run() {
         for (int i = 0; i < 1000; i++) {
//            System.out.println("Hello from " + Thread.currentThread().getName());
            int nextValue = dh.getValue();
            //Use it for something
         }
      }
   }
}

class DataHolder {
   private int value;
   private int otherValue;

   private AtomicInteger ai = new AtomicInteger(0);

   public int getValue() {
//      synchronized (this) {
//         value++;
//         otherValue--;
//         return value++;
//      }

      return ai.getAndIncrement();
   }
}
