import static java.lang.System.*;
public class House
{
	private String tipo;
	int inicial, reserva;
	private Room[] houser;
	
	House(String tipo, int inicial, int reserva)
	{
		this.tipo=tipo;
		this.inicial=inicial;
		this.reserva=reserva;
		houser = new Room[inicial+reserva];
	}
	
	public String tipo()
	{
		return tipo;
	}
	public int inicial()
	{
		return inicial;
	}
	public int reserva()
	{
		return reserva;
	}
	
	House(String tipo)
	{
		houser = new Room[8];
	}
	//Adiciona uma divisão
	public void addRoom(Room divisao)
	{
		int i = 0;
		
		for(i=0;i<houser.length;i++)
		{
			if(houser[i]==null)
			{
				break;
			}
		}
		houser[i]=divisao;
	}
	
	//Devolve o número de divisões da casa
	public int size()
	{
		int i=0;
		
		for(i=0;i<houser.length;i++)
		{
			if(houser[i]==null)
			{
				break;
			}
		}
		return i+1;
	}
	
	//Devolve o número máximo de divisões que é possível adicionar
	public int maxSize()
	{
		int i = 0, j;
		
		for(i=0;i<houser.length;i++)
		{
			if(houser[i]==null)
			{
				break;
			}
		}
		j=houser.length-i;
		return j;
	}
	
	//Devolve a divisão correspondente
	public Room room(int i)
	{
		Room div = houser[i];
		return div;
	}
	
	//Devolve a área total
	public double area()
	{
		int i = 0;
		double soma=0;
		
		for(i=0;i<houser.length;i++)
		{
			if(houser[i]!=null)
			{
				soma = soma + houser[i].area();
			}
			else if(houser[i]==null)
			{
				break;
			}
		}
		return soma;
	}
	
	//Devolve o tipo de divisões e o respetivo número
	public RoomTypeCount [] getRoomTypeCounts()
	{
		int i=0, j=0, k=0, s=0, w=0;
		RoomTypeCount a[] = new RoomTypeCount[1000];
		for(i=0;i<houser.length;i++)
		{
			if(houser[i]==null)
			{
				break;
			}
		}
		for(j=0;j<i;j++)
		{
				if(Pertence(a, houser[i].tipo())==0)
				{
					for(k=0;k<a.length;k++)
					{
						if(a[k]==null)
						{
							break;
						}
					}
					a[k].roomType=houser[i].tipo();
					a[k].count=1;
				}
				else if(Pertence(a, houser[i].tipo())!=0)
				{
					a[Pertence(a, houser[i].tipo())].count++;
				}
		}
		for(s=0;s<a.length;s++)
		{
			if(a[s]==null)
			{
				break;
			}
		}
		RoomTypeCount b[] = new RoomTypeCount[s-1];
		for(w=0;w<s;w++)
		{
			b[w]=a[w];
		}
		return b;
	}
	//Verifica se o tipo de divisão já existe
	public int Pertence(RoomTypeCount a[], String tipo)
	{
		int i=0, k=0;
		for(k=0;k<a.length;k++)
		{
			if(a[k]==null)
			{
				break;
			}
		}
		
		for(i=0;i<k;i++)
		{
			if(a[i].roomType==tipo)
			{
				break;
			}
		}
		
		if(a[0]==null)
		{
			i=0;
		}
		return i;
	}
	//Devolve a distância média entre divisões da sala
	public double averageRoomDistance()
	{
		
		int i=0;
		double soma=0, result;
		
		for(i=0;i<houser.length;i++)
		{
			if(houser[i]!=null)
			{
				soma+=houser[i].area();
			}
			else if(houser[i]==null)
			{
				break;
			}
		}
		soma=soma/i;
		return soma;		
	}
}
