import static java.lang.System.*;

public class DatasPassadas 
{

  public static void main(String[] args) 
  {
    Data atual = new Data();
	
	Data natal = new Data (25, 12, atual.ano()-1);
	out.printf(natal.extenso()+"\n");
	do
	{
		natal.seguinte();
		out.printf(natal.extenso()+"\n");
	}
	while(natal.mes()!=atual.mes() || natal.dia()!=atual.dia() || natal.ano()!=atual.ano());
  }

}

