public class TestContacto {

  public static void main(String[] args) {
    Contacto[] cl = new Contacto[4];
    cl[0] = new Contacto("Ana", "978676760");
    cl[1] = new Contacto("Rita", "867367834", "rita@gmail.com");
    cl[2] = new Contacto("Paulo", "897476388", "paulo@hotmail.com");
    cl[3] = new Contacto("Carlos", "674767867");
    for (int i = 0; i < cl.length; i++)
    {		
		if(cl[i].eMail()==null)
		{
			System.out.println(cl[i].Nome() + ": " + cl[i].Telefone());
		}
		else
		{
			System.out.println(cl[i].Nome() + ": " + cl[i].Telefone() + "; " + cl[i].eMail());
		}
    }
  }
}

