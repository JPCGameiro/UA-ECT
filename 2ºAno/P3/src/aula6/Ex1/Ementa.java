//João Gameiro  Nº93097
//P3-ECT-UA

package aula6.Ex1;
import java.util.Arrays;
import java.util.LinkedList;

public class Ementa {

		private String nome;
		private String local;
		@SuppressWarnings("unchecked")private LinkedList<Prato> [] pratos = new LinkedList [7];
		
		//Construtor
		public Ementa(String nome, String local)
		{
			this.nome=nome;
			this.local=local;
			for(int i=0;i<7;i++)
			{
				pratos[i] = new LinkedList<Prato>();
			}
		}
		
		
		public String nome() { return nome; }
		public String local() { return local; }
		
		//Adicionar um prato a um dia da semana
		public void addPrato(Prato prato, int d)
		{
			pratos[d].add(prato);
		}
		
		@Override
		public String toString()
		{
			String result="Ementa "+nome+ ", Local:  "+local+"\n";
			for(int i=0;i<pratos.length;i++) {
				for(int j=0;j<pratos[i].size();j++) {
					result = result+"Dia "+DiaSemana.dNum(i+1)+" "+pratos[i].get(j).toString()+"\n";					
				}
			}
			return result;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((local == null) ? 0 : local.hashCode());
			result = prime * result + ((nome == null) ? 0 : nome.hashCode());
			result = prime * result + Arrays.hashCode(pratos);
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Ementa other = (Ementa) obj;
			if (local == null) {
				if (other.local != null)
					return false;
			} else if (!local.equals(other.local))
				return false;
			if (nome == null) {
				if (other.nome != null)
					return false;
			} else if (!nome.equals(other.nome))
				return false;
			if (!Arrays.equals(pratos, other.pratos))
				return false;
			return true;
		}
		
		
}
