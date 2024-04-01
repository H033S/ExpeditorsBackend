package ttl.larku.shape.app;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import ttl.larku.shape.domain.AbstractShape;
import ttl.larku.shape.domain.Circle;
import ttl.larku.shape.domain.Rectangle;
import ttl.larku.shape.domain.Shape;

public class StaticDemo {

   public static void main(String[] args) {
      //Get Shape count == 0

      System.out.println(AbstractShape.getCount());


      Circle c = new Circle(2, 2, 10, "RED", 1);
      Rectangle rect = new Rectangle(10, 10, 20);

      List<Shape> shapes = new ArrayList<>();
      shapes.add(c);
      shapes.add(rect);

      //Get Shape count == 2
      System.out.println(AbstractShape.getCount());
   }

}
