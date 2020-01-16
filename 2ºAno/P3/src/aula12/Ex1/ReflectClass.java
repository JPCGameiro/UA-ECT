//João Gameiro  Nºº93097
//p3-ECT-UA

package aula12.Ex1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Scanner;

public class ReflectClass {


	public static void main(String[] args) throws ClassNotFoundException {
		
		Scanner ler = new Scanner(System.in);
		Circulo c1 = new Circulo(0,0,2);
		
		System.out.println("Nome da classe: "+c1.getClass().getName());
		System.out.println("Nome da superclasse: "+c1.getClass().getSuperclass().getName());
		Class<?>[] cls = c1.getClass().getInterfaces();
		if(cls.length!=0) {
			System.out.print("Interfaces: ");
			for(Class<?> cl : cls)
				System.out.print(cl.getName());
		}
		
		System.out.println("\n---------------------Construtores-------------------\n");
		printConstructors(c1);
		
		System.out.println("\n-----------------------Atributos--------------------\n");
		Field[] flist = c1.getClass().getDeclaredFields();
		for(Field f : flist)
			System.out.println(f.getType()+" "+f.getName());
		
		
		Method[] mlist = c1.getClass().getDeclaredMethods();
		System.out.println("\n-----------------------Métodos----------------------\n");
		for(Method m : mlist) {
			Class<?>[] clist = m.getParameterTypes();
			if(clist.length!=0) {
				System.out.print(m.getName()+ "(");
				for(int i=0;i<clist.length;i++) {
					if(i==clist.length-1)
						System.out.print(clist[i].getName() + " param"+i+")\n");
					else
						System.out.print(clist[i].getName() + " param"+i+", ");
				}	
			}
			else
				System.out.println(m.getName()+ "()");
		}
		
		
		System.out.println("\n\nCriar um objeto\nEscolha um construtor: ");
		Constructor<?>[] constlist = printConstructors(c1);
		int op = -1;
		do {
			System.out.print("\nOption-> ");
			op = ler.nextInt();
		}
		while(op<0 || op>constlist.length-1);
		
		ConstParam(constlist[op]);
		
		ler.close();

	}

	//Função para imprimir os construtores e os seus respectivos parâmetros
	public static Constructor<?>[] printConstructors(Object c1)
	{
		int cnt=0;
		Constructor<?>[] cl = c1.getClass().getDeclaredConstructors();
		for(Constructor<?> c : cl) {
			Class<?>[] cargs = c.getParameterTypes();
			if(cargs.length==0)
				System.out.print(c.getName()+"()");
			else {
				System.out.print(cnt + " -> "+c.getName()+"(");
				cnt++;
				for(int i=0;i<cargs.length;i++)
				{
					if(i==cargs.length-1)
						System.out.print(cargs[i].getName()+" param"+i+")\n");
					else
						System.out.print(cargs[i].getName()+" param"+i+", ");
				}
			}
		}
		return cl;
	}
	
	//Inserir valores nos parâmetros de um construtor
	public static  void ConstParam(Constructor<?> c)
	{
		Class<?>[] cargs = c.getParameterTypes();
		for(Class<?> cs : cargs)
			System.out.println(cs.getName());
	}

}
