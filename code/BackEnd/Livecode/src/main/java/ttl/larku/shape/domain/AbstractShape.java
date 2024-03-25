package ttl.larku.shape.domain;

public abstract class AbstractShape implements Shape{
   private int x1, y1;
   private String color = "Black";
   private int lineThickness = 10;

   public AbstractShape(int x1, int y1, String color, int lineThickness) {
      this.x1 = x1;
      this.y1 = y1;
      this.color = color;
      this.lineThickness = lineThickness;
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

   @Override
   public String toString() {
      return "AbstractShape{" +
            "x1=" + x1 +
            ", y1=" + y1 +
            ", color='" + color + '\'' +
            ", lineThickness=" + lineThickness +
            '}';
   }
}
