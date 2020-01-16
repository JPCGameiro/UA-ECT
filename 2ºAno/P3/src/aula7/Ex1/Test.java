//João Gameiro  Nº93097
//P3-ECT-UA

package aula7.Ex1;
import java.util.LinkedList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

public class Test {
	
	static LinkedList<Voo> vlist = new LinkedList<Voo>();
	static LinkedList<Companhia> clist = new LinkedList<Companhia>();
	static LinkedList<String> olist = new LinkedList<String>();

	public static void main(String[] args) throws IOException{
		
		String info = getFilesInfo();
		System.out.println(info);
		if(saveFileInfo(info))
			System.out.println("\nInformação guardada com sucesso no ficheiro info.txt");
		if(saveBinaryInfo(info))
			System.out.println("\nImformação binário guardada com sucesso no ficheiro info.bin");
		
	}
	
	
	//Função que retira a imformação dos ficheiros
	public static String getFilesInfo() throws IOException
	{
		List<String> voosInfo = Files.readAllLines(Paths.get("/home/joao/Documents/Universidade/2ºAno/P3/src/aula7/Ex1/aula7_material/voos.txt"));
		List<String> compInfo = Files.readAllLines(Paths.get("/home/joao/Documents/Universidade/2ºAno/P3/src/aula7/Ex1/aula7_material/companhias.txt"));
		
		//Retirar imformação do ficheiro das companhias
		for(int z=1;z<compInfo.size();z++)
		{
			Companhia c = new Companhia(compInfo.get(z).split("\t")[0], compInfo.get(z).split("\t")[1]);
			clist.add(c);
		}
		//Retirar imformação do ficheiro dos voos
		for(int i=1;i<voosInfo.size();i++)
		{
			Hora atraso;
			String Sigla="";
			Companhia comp = null;
			
			
			String[] arr = voosInfo.get(i).split("\t");
			//Hora
			Hora h = new Hora(Integer.parseInt(arr[0].split(":")[0]), Integer.parseInt(arr[0].split(":")[1]));
			//Nome e Sigla
			Sigla = arr[1].substring(0, 2);			
			for(Companhia c : clist) {
				if(c.sigla().equals(Sigla)) {
					comp = c;
					break;
				}
					
			}
			olist.add(arr[2]);
			//Atraso e adição do voo à lista de voos
			if(arr.length==4) {
				if(comp!=null) {
					atraso = new Hora(Integer.parseInt(arr[3].split(":")[0]), Integer.parseInt(arr[3].split(":")[1]));
					vlist.add(new Voo(h, comp, arr[2], atraso, arr[1]));
				}
			}
			else {
				if(comp!=null) {
					vlist.add(new Voo(h, comp, arr[2], arr[1]));
				}
			}
		}
		
		String result="Hora\tVoo\t\tCompanhia\t\tOrigem\t\t\tAtraso\n";
		for(Voo v : vlist)
			result = result+v.toString()+"\n";
		
		return result;
	}
	
	//Função para gravar imformações num novo ficheiro
	public static boolean saveFileInfo(String info)throws IOException
	{
		Files.write(Paths.get("/home/joao/Documents/Universidade/2ºAno/P3/src/aula7/Ex1/aula7_material/info.txt"), info.getBytes());
		return true;
	}
	//Função para gravar informações num ficheiro binário
	public static boolean saveBinaryInfo(String info) throws IOException {
		RandomAccessFile binFile = new RandomAccessFile("/home/joao/Documents/Universidade/2ºAno/P3/src/aula7/Ex1/aula7_material/BinaryInfo.bin", "rw");
		binFile.write(info.getBytes());
		binFile.close();
		return true;
	}

}
