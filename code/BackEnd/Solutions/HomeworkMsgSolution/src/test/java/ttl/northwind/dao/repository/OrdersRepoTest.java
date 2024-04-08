package ttl.northwind.dao.repository;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ttl.northwind.domain.entity.Orders;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrdersRepoTest {

   @Autowired
   private OrdersRepository orderRepo;

   @Test
   @Transactional
   public void testGetOrderById() {
      Orders order = orderRepo.findById((short)10251).orElse(null);

      System.out.println(order);
      assertNotNull(order);
   }
}
