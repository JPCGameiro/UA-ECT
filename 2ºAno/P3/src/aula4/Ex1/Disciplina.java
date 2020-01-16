//João Gameiro  Nº93097
//P3-ECT-UA

package aula4.Ex1;
import java.util.LinkedList;

public class Disciplina {
	
	private String nome;
	private String areaCientifica;
	private int ECTS;
	private Professor Responsavel;
	private LinkedList<Estudante> Alunos;
	
	//Construtor
	public Disciplina(String nome, String areaCientifica, int ECTS, Professor Responsavel)
	{
		this.nome = nome;
		this.areaCientifica = areaCientifica;
		this.ECTS = ECTS;
		this.Responsavel = Responsavel;
		this.Alunos = new LinkedList<Estudante>();
		
	}
	
	public String nome() { return nome; }
	public String areaCientifica() { return areaCientifica; }
	public int ECTS() { return ECTS; }
	public Professor Responsavel() { return Responsavel; }
	public LinkedList<Estudante> Alunos() { return Alunos; }
	
	//FUNÇÕES AUXILIARES
	
	//adicionar um estudante
	public boolean addAluno(Estudante est)
	{
		if(Alunos.contains(est))
			return false;
		else {
			Alunos.add(est);
			return true;
		}
	}
	//remover um estudante
	public boolean delAluno(int nmec)
	{
		for(int i=0;i<Alunos.size();i++) {
			if(Alunos.get(i).nmec()==nmec) {
				Alunos.remove(Alunos.get(i));
				return true;

			}
		}
		return false;
	}
	//Verificar se um aluno está inscrito
	public boolean alunoInscrito(int nmec)
	{
		boolean Verify=false;
		
		for(int i=0;i<Alunos.size();i++) {
			if(Alunos.get(i).nmec()==nmec)
				Verify = true;
		}
		return Verify;
	}
	//Devolve o número de alunos inscritos
	public int numAlunos()
	{
		return Alunos.size();
	}
	//Devolver as caracteristicas da disciplina
	@Override public String toString()
	{
		return "Disciplina: "+nome+" | Àrea Científica: "+areaCientifica+" | ECTS: "+ECTS+" | Responsável: "+Responsavel;
	}
	//Devolve um array com os alunos
	public Estudante[] getAlunos()
	{
		return Alunos.toArray(new Estudante[0]);
	}
	//Devolve um array com os alunos de um tipo de disciplina
	public Estudante[] getAlunos(String tipo)
	{
		LinkedList<Estudante> e = new LinkedList<Estudante>();
		
		
		for(int i=0;i<Alunos.size();i++) {
			if(Alunos.get(i).getClass().toString().contains(tipo)) {
				e.add(Alunos.get(i));
			}
		}
		return e.toArray(new Estudante[0]);
		
	}
}
