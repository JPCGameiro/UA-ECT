import static java.lang.System.*;
public class Agenda
{
	private Tarefa[] tarefas = new Tarefa[1000];
	
	public Agenda ()
	{
		this.tarefas=tarefas;
	}
	
	public Tarefa[] tarefas()
	{
		return tarefas;
	}
	
	public void novaTarefa(Tarefa tasks)
	{
		int i=0, j=0, k=0;
		Tarefa tmp;
		
		for(i=0;i<tarefas.length;i++)
		{
			if(tarefas[i]==null)
			{
				break;
			}
		}
		tarefas[i]=tasks;
		for(j=0;j<i+1;j++)
		{
			for(k=j+1;k<i+1;k++)
			{
				if(tarefas[j].inicio().compareTo(tarefas[k].inicio())==1)
				{
					tmp=tarefas[j];
					tarefas[j]=tarefas[k];
					tarefas[k]=tmp;
				}
			}
		}
	}
	
	public void escreve()
	{
		int i=0;
		
		for(i=0;i<tarefas.length;i++)
		{
			if(tarefas[i]!=null)
			{
				out.printf("%04d-%02d-%02d  ---  %04d-%02d-%02d :  %s\n", tarefas[i].inicio().ano(), tarefas[i].inicio().mes(), tarefas[i].inicio().dia(), tarefas[i].fim().ano(), tarefas[i].fim().mes(), tarefas[i].fim().dia(), tarefas[i].texto());
		    }
		    else if(tarefas[i]==null)
		    {
				break;
			}
		}
	}
	
	public Agenda filtra(Data d1, Data d2)
	{
		int i=0, j=0;
		Agenda a = new Agenda();
		
		for(i=0;i<tarefas.length;i++)
		{
			if(tarefas[i]!=null)
			{
				if(d1.compareTo(tarefas[i].inicio())==-1 && d2.compareTo(tarefas[i].inicio())==1)
				{
					a.tarefas[j]=tarefas[i];
					j++;
				}
			}
			else if(tarefas[i]==null)
			{
				break;
			}
			
		}
		return a;		
	}
}
