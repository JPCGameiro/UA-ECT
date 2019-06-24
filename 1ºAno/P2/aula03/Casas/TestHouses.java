import static java.lang.System.*;

public class TestHouses
{
  public static void main(String[] args)
  {
    House h = new House("apartment",7,4);

    Point p1 = new Point(0.0,1.0);
    Point p2 = new Point(11.0,2.5);
    Point p3 = new Point(4.0,0.0);
    Point p4 = new Point(4.0,2.5);
    Point p5 = new Point(0.0,4.0);
    Point p6 = new Point(11.0,6.0);
    Point p7 = new Point(5.5,7.0);
    Point p8 = new Point(4.0,4.0);
    Point p9 = new Point(5.5,4.0);
    Point p10 = new Point(11.0,7.0);
    Point p11 = new Point(11.0,0.0);
    Point p12 = new Point(14.0,2.5);
    Point p13 = new Point(14.0,7.0);
    Point p14 = new Point(14.0,10.0);

    out.println("\nAntes de introduzir divisões: ");
    out.println("- Divisões registadas: " + h.size());
    out.println("- Capacidade actual  : " + h.maxSize());

    h.addRoom(new Room(p1,p8, "hall"));
    h.addRoom(new Room(p3,p2, "sala"));
    h.addRoom(new Room(p4,p6, "corredor"));
    h.addRoom(new Room(p5,p7, "quarto"));
    h.addRoom(new Room(p9,p10, "quarto"));
    h.addRoom(new Room(p11,p12, "wc"));
    h.addRoom(new Room(p2,p13, "cozinha"));
    h.addRoom(new Room(p10,p14, "copa"));

    out.println("\nDepois de introduzir divisões: ");
    out.println("- Divisões registadas: " + h.size());
    out.println("- Capacidade actual  : " + h.maxSize());

    double avgDist = h.averageRoomDistance();
    out.println("\nDistância média entre divisões: "+avgDist);

    double area = h.area();
    out.println("\nArea da casa: "+area);

   out.println("\nÁreas por divisão: ");
    for(int i=0; i<h.size(); i++)
      out.println(i+1 + " - " + h.room(i).roomType() + ": " + h.room(i).area());

    out.println("\nNúmero de divisões de cada tipo");
    RoomTypeCount [] roomCounts = h.getRoomTypeCounts();
    for(int i=0; i<roomCounts.length; i++)
      out.println(roomCounts[i].roomType + ": " + roomCounts[i].count);
  }
}


