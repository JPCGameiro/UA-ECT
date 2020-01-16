//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex1;
import java.util.LinkedList;

public class ColecaoFiguras {
	
	private LinkedList<Figura> colecao;
	private double maxArea;
	private double currentArea;
	
	//Construtor
	public ColecaoFiguras(double maxArea)
	{
		this.maxArea = maxArea;
		this.currentArea = 0;
		this.colecao = new LinkedList<Figura>();
	}
	
	public double maxArea() { return maxArea; }
	public double currentArea() { return currentArea; }
	public LinkedList<Figura> colecao() { return colecao; }
	
	
	//Adiciona uma figura à coleção
	public boolean addFigura(Figura f)
	{
		if(exists(f) || (f.area()+currentArea)>maxArea)
			return false;
		else {
			colecao.add(f);
			currentArea = currentArea + f.area();
			return true;
		}
	}
	//Remove um figura da coleção
	public boolean delFigura(Figura f)
	{
		if(!colecao.contains(f))
			return false;
		else {
			colecao.remove(f);
			currentArea = currentArea - f.area();
			return true;
		}
	}
	//Devolve a Área total das figuras
	public double areaTotal()
	{
		return currentArea;
	}
	//Verifica se uma figura existe
	public boolean exists(Figura f)
	{
		return colecao.contains(f);
	}
	//Devolve as características da coleção
	@Override public String toString()
	{
		return "NºFiguras: "+colecao.size()+" | Àrea Total: "+currentArea()+ " | Àrea Total Permitida: "+maxArea();
	}
	//Devolve um array de figuras
	public Figura[] getFiguras()
	{
		return colecao.toArray(new Figura[0]);
	}
	//Devolve um array com os centros das figuras
	public Ponto[] getCentros()
	{
		LinkedList<Ponto> p = new LinkedList<>();
		for(int i=0;i<colecao.size();i++) {
			p.add(colecao.get(i).centro());
		}
		return p.toArray(new Ponto[0]);
	}
}
