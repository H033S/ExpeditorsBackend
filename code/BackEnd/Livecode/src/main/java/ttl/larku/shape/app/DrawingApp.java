package ttl.larku.shape.app;

import java.util.ArrayList;
import java.util.List;
import ttl.larku.shape.domain.Circle;
import ttl.larku.shape.domain.Rectangle;

public class DrawingApp {

   public static void main(String[] args) {
      Circle c = new Circle(2, 2, 10, "RED", 1);

      Circle withDefaults = new Circle(10, 10, 20);

//      System.out.println(STR."area: \{c.getArea()}");

//      c.draw();

      List<Circle> circles = new ArrayList<>();
      circles.add(c);
      circles.add(withDefaults);

      for (Circle circle : circles) {
         circle.draw();
      }


      Rectangle r0 = new Rectangle(10, 10, 30);
      Rectangle r1 = new Rectangle(10, 10, 30, 5);
      Rectangle r2 = new Rectangle(40, 40, 60, 3, "Green", 2);
      List<Rectangle> rects = new ArrayList<>();
      rects.add(r0);
      rects.add(r1);
      rects.add(r2);

      for (Rectangle rectangle : rects) {
         rectangle.draw();
      }

   }

}
