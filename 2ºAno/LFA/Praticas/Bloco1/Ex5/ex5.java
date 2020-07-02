
import java.util.*;

public class ex5
{
	public static void main(String[] args)
	{
		HashMap<String, Double> map = new HashMap<String, Double>();
		LinkedList<String> list = new LinkedList<String>();
		Scanner sc0 = new Scanner(System.in);
		
		while(sc0.hasNextLine()){
			Scanner sc1 = new Scanner(sc0.nextLine());
			while(sc1.hasNext()){
				String s = sc1.next();
				if(s.equals("quit")){
					System.out.println("System shutting down...");
					System.exit(0);
				}
				else
					list.add(s);
				if(!sc1.hasNext())
					break;
			}
			
			if(list.size()==1){
				try{
					double d = Double.parseDouble(list.peek());
					System.out.println("result: "+d);
				}
				catch(NumberFormatException e){
					String var = list.peek();
					if(map.containsKey(var))
						System.out.println("result: "+var+"- "+map.get(var));
					else
						System.err.println("ERROR: variable was not initializes yet");
				}
			}
			else if(list.size()%2==0){
				System.err.println("ERROR: invalid sintax");
			}
			else{			//primeiro é um double
				if(isDouble(list.get(0))){
					if(isOperator(list.get(1)).equals("="))
						System.err.println("ERROR: invalid sintax");
					else{
						
						try{
								int cnt=0, max=list.size();
								double result;
								if(isDouble(list.get(cnt)))
									result = Double.parseDouble(list.get(cnt));
								else if(map.containsKey(list.get(cnt)))
									result = map.get(list.get(cnt));
								else 
									throw new IllegalAccessException();
									
								do{
									double aux;
									if(isDouble(list.get(cnt+2)))
										aux = Double.parseDouble(list.get(cnt+2));
									else if(map.containsKey(list.get(cnt+2)))
										aux = map.get(list.get(cnt+2));
									else
										throw new IllegalAccessException();
									result = doOperation(result, aux, list.get(cnt+1));
									cnt = cnt+2;
								}while(cnt<=list.size()-2);
								
								System.out.println("result: "+result);
							}
							catch(NumberFormatException e) { System.err.println("ERROR: invalid sintax"); }
							catch(IllegalAccessException e) { System.err.println("ERROR: variable was not initialized"); }
						
					}
				}			//primeiro é um operador
				else if(!isOperator(list.get(0)).equals("")){
					System.err.println("ERROR: invalid sintax");
				}
				else{		//primeiro é uma variável
					if(!isOperator(list.get(1)).equals("=")){		//uma conta 
						if(!map.containsKey(list.get(0)))
							System.err.println("ERROR: variable "+list.get(0)+" was not initialized yet");
						else{
							try{
								int cnt=0;
								double result;
								if(isDouble(list.get(cnt)))
									result = Double.parseDouble(list.get(cnt));
								else if(map.containsKey(list.get(cnt)))
									result = map.get(list.get(cnt));
								else 
									throw new IllegalAccessException();
									
								do{
									double aux;
									if(isDouble(list.get(cnt+2)))
										aux = Double.parseDouble(list.get(cnt+2));
									else if(map.containsKey(list.get(cnt+2)))
										aux = map.get(list.get(cnt+2));
									else
										throw new IllegalAccessException();
									result = doOperation(result, aux, list.get(cnt+1));
									cnt = cnt+2;
								}while(cnt<=list.size()-2);

								System.out.println("result: "+result);
							}
							catch(NumberFormatException e) { System.err.println("ERROR: invalid sintax"); }
							catch(IllegalAccessException e) { System.err.println("ERROR: variable was not initialized"); }
						}
					}
					else{											//uma atribuição//
						if(list.size()==3){							//atribuição simples//
							String var = list.get(0);
							if(map.containsKey(list.get(2))){
								map.put(var, map.get(list.get(2)));
								System.out.println("result: "+var+" = "+map.get(var));
							}
							else if(isDouble(list.get(2))){
								map.put(var, Double.parseDouble(list.get(2)));
								System.out.println("result: "+var+" = "+Double.parseDouble(list.get(2)));
							}
							else if(!isOperator(list.get(1)).equals("="))
								System.err.println("ERROR: invalid sintax");
							else
								System.err.println("ERROR: variable "+list.get(2)+" was not initialized");
						}
						else{										//atribuição que envolve contas//
							try{
								int cnt=2;
								double result;
								if(isDouble(list.get(cnt)))
									result = Double.parseDouble(list.get(cnt));
								else if(map.containsKey(list.get(cnt)))
									result = map.get(list.get(cnt));
								else 
									throw new IllegalAccessException();
									
								do{
									double aux;
									if(isDouble(list.get(cnt+2)))
										aux = Double.parseDouble(list.get(cnt+2));
									else if(map.containsKey(list.get(cnt+2)))
										aux = map.get(list.get(cnt+2));
									else
										throw new IllegalAccessException();
									result = doOperation(result, aux, list.get(cnt+1));
									cnt = cnt+2;
								}while(cnt<=list.size()-2);
								
								map.put(list.get(0), result);
								System.out.println("result: "+list.get(0)+" = "+map.get(list.get(0)));
							}
							catch(NumberFormatException e) { System.err.println("ERROR: invalid sintax"); }
							catch(IllegalAccessException e) { System.err.println("ERROR: variable was not initialized"); }
						}
					}						
				}
			}
			list.clear();
		}
	}
	
	//Função para imprimir o conteúdo da linkedlist
	public static String printList(LinkedList<String> list)
	{
		String result="";
		for(int i=0;i<list.size();i++)
			result = result+"<"+list.get(i)+">";
		return result;
	}
	
	//Função para verificar se é um operador ou não
	public static String isOperator(String op)
	{
		if(op.equals("+") | op.equals("-") | op.equals("/") | op.equals("*") | op.equals("="))
			return op;
		else 
			return "";
	}
	
	//Função para realizar a operação
	public static double doOperation(double op0, double op1, String op)
	{		
		double result = 0;
		switch(op){
			case "+":
				result=op0+op1;
				break;
			case "-":
				result=op0-op1;
				break;
			case "*":
				result=op0*op1;
				break;
			case "/":
				result=op0/op1;
				break;
		}
		return result;
	}
	
	//Função para verificar se uma string é um double ou não
	public static boolean isDouble(String s)
	{
		try{
			Double.parseDouble(s);
			return true;
		}
		catch(NumberFormatException | InputMismatchException e) {
			return false;
		}
	}
}
