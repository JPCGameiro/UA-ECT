import java.util.Stack;
import java.util.Scanner;
import java.util.EmptyStackException;

public class ex2{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner(System.in);
		Stack<Double> stack = new Stack<Double>();
		
		while(ler.hasNextLine()) {
			if(ler.hasNextDouble())									//ler doubles
				stack.push(ler.nextDouble());
			else {
				String op = ler.next();								//ler as operações
				if(verifyOperator(op)){
					if(stack.isEmpty()){							//lidar com os erros da falta de operandos
						System.err.println("ERROR: two operands missing");
						break;
					}
					else if(stack.size()==1) {
						System.err.println("ERROR: one operand missing");
						break;
					}
					else{											//calcular a operação e guardar o resultado na stack
						double op2, op1;
						op1 = stack.pop();
						op2 = stack.pop();
						switch(op){
							case "+":
								stack.push(op1+op2);
								break;
									
							case "-":
								stack.push(op1-op2);
								break;
									
							case "/":
								stack.push(op1/op2);
								break;
									
							case "*":
								stack.push(op1*op2);
								break;
						}
					}
				}
				else {												//Operador inválido
					System.err.println("ERROR: invalid operator");
					break;
				}
			}
			System.out.println(toString(stack));					//imprimir o conteúdo da stack no final de cada iteração
		}
	}
	
	//Função para imprimir o conteúdo da Stack
	public static String toString(Stack<Double> stack)
	{
		String result = "Stack: ";
		return result+stack.toString();
	}
	
	//Função para verificar a validade do operador
	public static boolean verifyOperator(String op)
	{
		if(op.equals("+") | op.equals("-") | op.equals("*") | op.equals("/"))
			return true;
		else 
			return false;
	}
}
