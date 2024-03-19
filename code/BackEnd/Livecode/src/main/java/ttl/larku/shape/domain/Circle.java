package ttl.larku.shape.domain;

//Allow users to draw Circles of various dimensions
//Allow users to draw Rectangles of various dimensions

public class Circle extends AbstractShape {

   private int radius;

   public Circle(int x1, int y1, int radius, String color, int lineThickness) {
      super(x1, y1, color, lineThickness);

      this.radius = radius;
   }

   public Circle(int x1, int y1, int radius) {
      this(x1, y1, radius, "Black", 10);
//      this.x1 = x1;
//      this.y1 = y1;
//      this.radius = radius;
   }


   public int getRadius() {
      return radius;
   }

   public void setRadius(int radius) {
      this.radius = radius;
   }

   @Override
   public void draw() {
      System.out.println(STR."Circle.draw at \{getX1()}, \{getY1()}, radius: \{radius}");
   }

   //perimeter
   public double getPerimeter() {
      //2 * pi * r
      return 2 *  Math.PI * radius;
   }

   //area
   public double getArea() {
      return Math.PI * radius * radius;
   }

   @Override
   public String toString() {
      return "Circle{" +
            "radius=" + radius +
            ", " + super.toString() +
            '}';
   }
}
