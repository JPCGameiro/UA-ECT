//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex2;

public abstract class Veiculo {
	
		protected int ano;
		protected Cor cor;
		protected int numRodas;
		
		//Construtor Veiculo
		public Veiculo(int ano, Cor cor, int numRodas)
		{
			this.ano = ano;
			this.cor = cor;
			this.numRodas = numRodas;
		}
		
		public int ano() { return ano; }
		public Cor cor() { return cor; }
		public int numRodas() { return numRodas; }
		
		@Override public String toString()
		{
			return "VEICULO - Ano: "+ano+" Cor:"+cor+" NºRodas: "+numRodas;
		}

}
