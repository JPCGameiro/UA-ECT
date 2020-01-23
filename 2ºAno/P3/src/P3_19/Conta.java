package P3_19;

import java.util.LinkedList;

public class Conta {
	
	private int num = 0;
	private Cliente cliente;
	private double saldo;
	private LinkedList<Movimento> movimentos;
	
	//Construtor
	public Conta(int num, Cliente cliente, double saldo, LinkedList<Movimento> movimentos)
	{
		this.num = num;
		this.cliente = cliente;
		this.saldo = saldo;
		this.movimentos = movimentos;
	}
	public Conta(Cliente cliente)
	{
		this.num = num+1;
		this.saldo = 0;
		this.cliente = cliente;
		this.movimentos = new LinkedList<Movimento>();
	}
	
	public int getNum() { return num; }
	public Cliente getCliente() { return cliente; }
	public double getSaldo() { return saldo; }
	public  LinkedList<Movimento> getMovimentos() { return movimentos; }
	
	//Método add para efectuar operações sobre o saldo
	public void add(Movimento m)  {
		movimentos.add(m);
		saldo = saldo+m.getMontante();
	}
	
	//Método toString()
	@Override
	public String toString() {
		return "N: "+num+", "+cliente.toString()+", Saldo: "+saldo+", Movimentos: "+returnMoves(movimentos);
	}
	
	//Método para devolver movimentos numa string
	public String returnMoves(LinkedList<Movimento> list)
	{
		String result = "";
		for(Movimento m : list)
			result = result+m.toString();
		return result;
	}

}
