package ttl.larku.shape.domain;

public class Rectangle {

   private int x1, y1;

   private int width;
   private int height;

   private String color;
   private int lineThickness;

   public Rectangle(int x1, int y1, int width, int height, String color, int lineThickness) {
      this.x1 = x1;
      this.y1 = y1;
      this.width = width;
      this.height = height;
      this.color = color;
      this.lineThickness = lineThickness;
   }

   public Rectangle(int x1, int y1, int width, int height) {
      this(x1, y1, width, height, "Black", 1);
   }

   public Rectangle(int x1, int y1, int sideLength) {
      this(x1, y1, sideLength, sideLength, "Black", 1);
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


   public void draw() {
      System.out.println(STR."Rectangle.draw at \{x1}, \{y1}, w, h: \{width}, \{height}");
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
