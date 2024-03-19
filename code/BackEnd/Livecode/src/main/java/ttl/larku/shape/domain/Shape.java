package ttl.larku.shape.domain;

public interface Shape {

   public String getColor();

   public void setColor(String color);

   public int getLineThickness() ;

   public void setLineThickness(int lineThickness);

   public int getX1();

   public void setX1(int x1);

   public int getY1();

   public void setY1(int y1);

   public void draw();
}
