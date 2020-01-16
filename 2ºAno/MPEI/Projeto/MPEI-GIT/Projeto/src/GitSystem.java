

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class GitSystem {
	
	private LinkedList<File> list = new LinkedList<File>(); 	//Lista de Ficheiros
	private BloomFilter bf;										//BloomFilter
	
	//Construtor
	public GitSystem()
	{
		this.list = new LinkedList<File>();
	}
	
	public LinkedList<File> list() { return list; }
	
	//------------------------------//
	//Remover todos os ficheiros	//
	//------------------------------//
	public void removeAllFiles()
	{
		list.clear();
	}
	
	//------------------------------//
	//Listar o nome dos ficheiros	//
	//------------------------------//
	public String listAllFiles()
	{
		String result = "Files Added:\n\n";
		
		for(int i=0;i<list.size();i++)
			result = result + list.get(i).getName()+" \n";
		return result;
	}
	
	//------------------------------//
	//Adicionar ficheiros	        //
	//------------------------------//
	public int  addFile(String filename) throws IOException
	{
		File f = new File("./src/Files/"+filename);
		
		if(f.exists()) {
			if(list.isEmpty()) {	//para o primeiro ficheiro a ser adicionado
				list.add(f);
				bf = new BloomFilter(list);
				return 0;			//sucesso
			}
			else {
				if(bf.membro(f)) 
					return 2;		//ficheiro já foi adicionado
				else {
					list.add(f);
					bf = new BloomFilter(list);
					if(similar(filename))
						return 3;
					return 0;
				}
			}
		}
		else
			return 1;				//ficheiro não existe
		
	}
	
	//------------------------------//
	//Verificar similaridade        //
	//------------------------------//
	public boolean similar(String filename) throws IOException
	{
		MinHash m;
		File fi = new File("./src/Files/"+filename);
		for(File file : list) {
			if(file.compareTo(fi)!=0) {
				m = new MinHash(fi);
				double sim = m.jaccard(new MinHash(file));
				if(sim > 0.50) {
					return true;				
				}
			}
		}
		return false;
	}
	
	//------------------------------//
	//Remover ficheiros 	        //
	//------------------------------//
	public boolean removeFile(String filename)
	{
		if(bf.membro(new File("./src/Files/"+filename))) {
			list.remove(new File("./src/Files/"+filename));
			bf = new BloomFilter(list);
			return true;
		}
		else
			return false;
	}
	
	
	//------------------------------//
	//Pesquisar ficheiros 	        //
	//------------------------------//
	public String searchFile(String filename)throws IOException
	{
		String result = "File Content:\n\n";
		if(bf.membro((new File("./src/Files/"+filename)))) {				//testar se o ficheiro já foi adicionado ao BloomFilter
			Scanner ler = new Scanner(new File("./src/Files/"+filename));
			while(ler.hasNextLine())
				result = result+ler.nextLine() + "\n";
			ler.close();
		}
		else
			result = "";
		return result;

		
	}
	
	//------------------------------//
	//Pesquisar ficheiros similares //
	//------------------------------//
	public String searchSimilarFiles(String filename) throws IOException
	{
		String result = "Similar Files:\n\n";
		File f = new File("./src/Files/"+filename);

		if(bf.membro(f)) {
			MinHash m = new MinHash(f);
			
			for(File file : list) {
				if(file.compareTo(f)!=0) {
					double sim = m.jaccard(new MinHash(file));
					result = result + file.getName() + " -> " + sim*100+"%\n";
				}
			}
			return result;
		}
		else
			return "";
	}
}
