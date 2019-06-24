public class Point 
{

  Point(double x, double y) 
  {
    this.x = x;
    this.y = y;
  }

  Point() {
    this.x = 0;
    this.y = 0;
  }

  public Point halfWayTo(Point p) 
  {
    Point hw = new Point();
    hw.x = (this.x+p.x)/2.0;
    hw.y = (this.y+p.y)/2.0;
    return hw;
  }

  public double distTo(Point p) 
  {
    return Math.sqrt(Math.pow(this.x-p.x,2)+Math.pow(this.y-p.y,2));
  }

  public double x() 
  {
    return x;
  }

  public double y() 
  {
    return y;
  }

  private double x, y;
}
