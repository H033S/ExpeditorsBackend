package ttl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

public class CollectionDemo {

   @Test
   public void testMapOrder() {
      Map<Integer, Integer> mints = new HashMap<>();

      mints.put(10, 10);
      mints.put(20, 10);

      for(var entry: mints.entrySet()) {
         var key = entry.getKey();
         var value = entry.getValue();

         System.out.println(STR."key: \{key}, value: \{value}");
      }
   }

   @Test
   public void testTreeMapOrder() {
      Map<Integer, Integer> mints = new TreeMap<>();

      mints.put(10, 10);
      mints.put(20, 10);
      mints.put(15, 10);

      for(var entry: mints.entrySet()) {
         var key = entry.getKey();
         var value = entry.getValue();

         System.out.println(STR."key: \{key}, value: \{value}");
      }

      List<Integer> list = new ArrayList<>();
      list.add(10);
      list.add(5);

   }
}
