package aula4.Ex1;

public class Test {

	public static void main(String[] args) {
		
		Estudante est1 = new Estudante("Andreia", 9855678, new Data(18, 7, 1974));
		Estudante est2 = new Estudante("Monica", 75266454, new Data(11, 8, 1978));
		Estudante est3 = new Estudante("Jose", 85265426, new Data(15, 2, 1976));
		Estudante est4 = new Estudante("Manuel", 85153442, new Data(1, 3, 1973));
		
		Bolseiro bls1 = new Bolseiro("Maria", 8976543, new Data(12, 5, 1976));
		Bolseiro bls2 = new Bolseiro("Xico", 872356522, new Data(21, 4, 1975));
		Bolseiro bls3 = new Bolseiro("Duarte", 32423512, new Data(6, 1, 1976));
		
		bls1.setBolsa(745);
		bls2.setBolsa(845);
		bls3.setBolsa(745);
		
		Professor pf1 = new Professor("Jose Manuel", 11223344, new Data(11, 9, 1969));
		Disciplina dis = new Disciplina("P5", "Informatica", 6, pf1);
		dis.addAluno(est1);
		dis.addAluno(est2);
		dis.addAluno(bls1);
		
		if (dis.alunoInscrito(est3.nmec()))
			System.out.println("\n" + est3 + " \n\t-> ESTÁ inscrito na Disciplina");
		else
			System.out.println("\n" + est3 + " \n\t-> NÃO ESTÁ inscrito na Disciplina");
		
		System.out.println("\nNo de Alunos Inscritos: " + dis.numAlunos());
		dis.addAluno(est3);
		dis.addAluno(bls2);
		dis.addAluno(est4);
		dis.addAluno(bls3);
			
		if (!dis.addAluno(bls3))
			System.out.println(bls3.nmec() + ", " + bls3.nome() + " já está inscrito nesta disciplina!");
		if (dis.delAluno(bls2.nmec()))
			System.out.println(bls2.nome() + " Removido!");
			
		System.out.println("\nNo de Alunos Inscritos: " + dis.numAlunos());
			
		System.out.println(dis + "\n");
			
		System.out.println("\n Listagem de Estudantes:");
		for (Estudante e : dis.getAlunos())
			System.out.println(e);

		System.out.println("\n Listagem de Bolseiros:");
		for (Estudante e : dis.getAlunos("Bolseiro"))
			System.out.println(e);
		

	}

}
