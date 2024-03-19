package ttl.larku.shape.domain;

//Allow users to draw Circles of various dimensions
//Allow users to draw Rectangles of various dimensions

public class Circle {

   int x1, y1;
   int x2, y2;
   private int radius;

   private String color;
   private int lineThickness;

   public Circle(int x1, int y1, int radius, String color, int lineThickness) {
      this.x1 = x1;
      this.y1 = y1;
      this.radius = radius;
      this.color = color;
      this.lineThickness = lineThickness;
   }

   public Circle(int x1, int y1, int radius) {
      this(x1, y1, radius, "Black", 1);
   }

   public String getColor() {
      return color;
   }

   public void setColor(String color) {
      this.color = color;
   }

   public int getLineThickness() {
      return lineThickness;
   }

   public void setLineThickness(int lineThickness) {
      this.lineThickness = lineThickness;
   }

   public int getX1() {
      return x1;
   }

   public void setX1(int x1) {
      this.x1 = x1;
   }

   public int getY1() {
      return y1;
   }

   public void setY1(int y1) {
      this.y1 = y1;
   }

   public int getX2() {
      return x2;
   }

   public void setX2(int x2) {
      this.x2 = x2;
   }

   public int getY2() {
      return y2;
   }

   public void setY2(int y2) {
      this.y2 = y2;
   }

   public int getRadius() {
      return radius;
   }

   public void setRadius(int radius) {
      this.radius = radius;
   }


   public void draw() {
      System.out.println(STR."Circle.draw at \{x1}, \{y1}, radius: \{radius}");
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
}
