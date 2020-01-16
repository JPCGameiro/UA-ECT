//João Gameiro   Nº93097
//class Data

package aula10.Ex1;

import java.time.LocalDate;

public class Data {
	private int dia;
	private int mes;
	private int ano;
	
	//Construtor
	public Data(int dia, int mes, int ano)
	{
		if(!ValidDate(dia,mes,ano))
		{
			throw new IllegalArgumentException();
		}
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
	}
	
	public int dia() { return dia; }
	public int mes() { return mes; }
	public int ano() { return ano; }
	
	//Função para devolver uma String com a data no formato dd-mes-ano
	public String toString()
	{
		return String.format("%02d-%02d-%04d", dia, mes, ano);
	}
	
	//Função para devolver a data de hoje
	public static Data today()
	{
		LocalDate localDate = LocalDate.now();
		return new Data(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
	}
	//Função para verificar se uma data é válida ou não
	public static boolean ValidDate(int dia, int mes, int ano)
	{
		boolean verify = true;
		
		//Valores inválidos: ano negativo, mês<= 0 ou mês >12 e dia negativo; 
		if(ano<0 || mes<=0 || mes>12 || dia<=0)
			return false;
		
		int mes31[] = {1, 3, 5, 7, 8, 10, 12};
		int mes30[] = {2, 4, 6, 9, 11};
		
		//Verificação do ano bissexto
		boolean bi = false;
		if((ano%400==0) || (ano%4==0 && ano%100!=0))
			bi = true;
		
		//Se o ano for Bissexto
		if(bi == true)
		{
			if(mes == 2)                          //fevereiro
				if(dia>29)
					verify = false;
			else
				for(int i=0;i<mes31.length;i++)   //meses com 31 dias
					if(mes==mes31[i])
					{
						if(dia>31)
							verify = false;
					}
				for(int j=0;j<mes30.length;j++)   //meses com 30 dias
					if(mes==mes30[j])
					{
						if(dia>30)
							verify = false;
					}
		}
		//Se não for ano Bissexto
		else
		{
			if(mes == 2)
				if(dia>28)                            //fevereiro
					verify = false;
				else
					for(int i=0;i<mes31.length;i++)   //meses com 31 dias
						if(mes==mes31[i])
						{
							if(dia>31)
								verify = false;
						}
					for(int j=0;j<mes30.length;j++)   //meses com 30 dias
						if(mes==mes30[j])
						{
							if(dia>30)
								verify = false;
						}
		}
		
		return verify;
	}

}