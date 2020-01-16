//João Gameiro  Nº93097
//P3-ECT-UA

package aula9.Ex2;

public class GeladoSimples implements Gelado{
	
	String ingredient;
	
	//Construtor 
	public GeladoSimples(String ingredient)
	{
		this.ingredient = ingredient;
	}

	@Override
	public void base(int i) {
		if(i==1)
			System.out.print(i+" bola de gelado de "+ingredient);
		else
			System.out.print(i+" bolas de gelado de "+ingredient);
		
	}

}
