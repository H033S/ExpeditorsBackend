package ttl.larku.shape.domain;

import mypack.MyClass;

public class Rectangle extends AbstractShape implements Shape{

   private int width;
   private int height;


   public Rectangle(int x1, int y1, int width, int height, String color, int lineThickness) {
      super(x1, y1, color, lineThickness);
      this.width = width;
      this.height = height;
   }

   public Rectangle(int x1, int y1, int width, int height) {
      this(x1, y1, width, height, "Black", 1);
   }

   public Rectangle(int x1, int y1, int sideLength) {
      this(x1, y1, sideLength, sideLength, "Black", 1);
   }


   public int getLength() {
      return height;
   }

   public void setLength(int length) {
      this.height = length;
   }

   public int getWidth() {
      return width;
   }

   public void setWidth(int width) {
      this.width = width;
   }


   @Override
   public void draw() {
      System.out.println(STR."Rectangle.draw at \{getX1()}, \{getY1()}, w, h: \{width}, \{height}");
   }

   //perimeter
   public double getPerimeter() {
      //2 * pi * r
      return 2 * width + 2 * height;
   }

   //area
   public double getArea() {
      return width * height;
   }

}
