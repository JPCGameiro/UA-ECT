import static java.lang.System.*;
import java.util.Scanner;
public class TestAgenda
{
  public static void main(String[] args)
  {
    Agenda agenda = new Agenda();
    agenda.novaTarefa(new Tarefa(new Data(14,6,2012), new Data(27,6,2012), "Producao"));
    agenda.novaTarefa(new Tarefa(new Data(2,7,2012), new Data(13,7,2012), "Testes"));
    agenda.novaTarefa(new Tarefa(new Data(6,6,2012), new Data(6,6,2012), "Reuniao chefia"));
    agenda.novaTarefa(new Tarefa(new Data(9,5,2012), new Data(9,5,2012), "Reuniao equipa"));
    agenda.novaTarefa(new Tarefa(new Data(22,3,2012), new Data(27,3,2012), "Projeto"));
    agenda.escreve();
    out.println();
    Data d1 = new Data(05,5,2012);
    Data d2 = new Data(15,7,2012);
    Agenda todo = agenda.filtra(d1, d2);
    out.printf("Tarefas de %s a %s:\n", d1, d2);
    todo.escreve();
  }
}

