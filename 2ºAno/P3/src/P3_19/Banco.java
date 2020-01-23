package P3_19;


import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

public class Banco {

	protected Set<Conta> contas;
	private String nome;
	
	//Construtor
	public Banco(Set<Conta> contas, String nome)
	{
		this.contas = contas;
		this.nome = nome;
	}
	public Banco(String nome)
	{
		this.contas = new HashSet<Conta>();
		this.nome = nome;
	}
	
	public Set<Conta> getContas() { return contas; }
	public String getNome() { return nome; }
	
	//Método add para adicionar contas ao banco
	public boolean add(Conta c) {
		return contas.add(c);
	}
	
	
	Iterator<Conta> iterator() {
		return (this).new BancoIterator();
	}
	
	private class BancoIterator implements Iterator<Conta>{
		
		Iterator<Conta> i = contas.iterator();

		
		@Override
		public boolean hasNext() {
			return i.hasNext();
		}

		@Override
		public Conta next() {
			return i.next();
		}
		
	}
}


