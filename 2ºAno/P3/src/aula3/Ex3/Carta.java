//João Gameiro  Nº93097
//P3 - ECT - UA

package aula3.Ex3;

public class Carta {
	
	private final String tipo;
	
	//Construtor
	public Carta(String tipo)
	{
		if(!validType(tipo)) { throw new IllegalArgumentException(); }
		this.tipo = tipo;
	}
	
	public String tipo() { return tipo; }
	
	//Função para verificar se o tipo inserido é válido
	public boolean validType(String tipo)
	{
		return tipo.equals("A") || tipo.equals("B") || tipo.equals("C") || tipo.equals("D");
	}
	//Função para verificar se dois tipos são iguais
	public boolean equals(String t)
	{
		return t.equals(tipo);
	}

}
