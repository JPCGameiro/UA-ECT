import static java.lang.System.*;

public class TestData3 {

  public static void main(String[] args) {
    
    out.println(Data.diasDoMes(1, 2000)); // should work
    out.println(Data.diasDoMes(12, 2000)); // should work
    
    // Descomente cada uma das instruções abaixo e volte a testar
    
    //assert Data.diasDoMes(0, 1900)!=-1 :"Mês Inválido"; // should fail!
    //assert Data.diasDoMes(13, 2000)!=-1: "Mês Inválido"; // should fail!
    
    Data d = new Data(29, 2, 2000); //shoudl work
    // check it:
    assert d.dia()==29 && d.mes()==2 && d.ano()==2000;
    
    // Descomente cada uma das instruções abaixo e volte a testar
    
    //d = new Data(29, 2, 1900); // should fail!
    //assert d.dataValida(d.dia(), d.mes(), d.ano()) == true : "Data Inválida";
    //d = new Data(29, 2, 2001); // should fail!
    //assert d.dataValida(d.dia(), d.mes(), d.ano()) : "Data Inválida";
    //d = new Data(31, 4, 2001); // should fail!
    //assert d.dataValida(d.dia(), d.mes(), d.ano()) : "Data Inválida";
    //d = new Data(0, 1, 2001); // should fail!
    //assert d.dataValida(d.dia(), d.mes(), d.ano()) : "Data Inválida";
    //d = new Data(1, 13, 2001); // should fail!
    //assert d.dataValida(d.dia(), d.mes(), d.ano()) : "Data Inválida";
    
    
    // Testar seguinte():
    Data today = new Data();
    d = new Data();
    d.seguinte();
    out.println(d);
    // avança 400 anos, dia-a-dia:
    do 
    {
      d.seguinte();
    }
    while(d.dia()!=today.dia() || d.mes()!=today.mes() || (d.ano()!=today.ano()+400));
    out.println(d);
    assert d.dia() == today.dia();
    assert d.mes() == today.mes();
    assert d.ano() == today.ano()+400;  // exactly 400 years from now!
    
    out.println("Class Data passed all tests.");
  }

  // Check if program is being run with -ea, exit if not.
  static {  // A static block. Cool!
    boolean ea = false;
    assert ea = true; // assert with a side-effect, on purpose!
    if (!ea) {
      err.println("This program must be run with -ea!");
      exit(1);
    }
  }


}
