//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex3;
import java.util.LinkedList;
import java.io.IOException;

public interface TipoAgenda {
	
	public LinkedList<Pessoa> readFile(String file) throws IOException;
	public void writeFile(String file, LinkedList<Pessoa> agenda) throws IOException;
}
