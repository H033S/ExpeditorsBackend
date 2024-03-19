package ttl.larku.shape.app;

import java.util.ArrayList;
import java.util.List;
import ttl.larku.shape.domain.Circle;
import ttl.larku.shape.domain.Rectangle;
import ttl.larku.shape.domain.AbstractShape;
import ttl.larku.shape.domain.Shape;

public class DrawingAppInheritance {

   public static void main(String[] args) {
      Circle c = new Circle(2, 2, 10, "RED", 1);

      AbstractShape shape = c;


      Object obj = c;

      System.out.println(STR."c.x1: \{c.getX1()}, c.radius: \{c.getRadius()}");


      Rectangle rect = new Rectangle(10, 10, 20);
      AbstractShape s2 = rect;

      System.out.println(STR."rect.x1: \{rect.getX1()}, rect.width: \{rect.getWidth()}");

      List<Shape> shapes = new ArrayList<>();
      shapes.add(c);
      shapes.add(rect);

      for(Shape s : shapes) {
//         s.draw();
         System.out.println("s: " + s.toString());
//         s.setLineThickness(20);
      }
   }

}
