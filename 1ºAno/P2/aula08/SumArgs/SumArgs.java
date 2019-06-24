import static java.lang.System.*;

public class SumArgs
{
  public static void main(String[] args) 
  {
    // Criar array com um elemento por cada argumento do programa:
    double[] arr = new double[args.length];
	
	for(int i=0;i<args.length;i++)
	{
		arr[i] = Double.parseDouble(args[i]);
	}
    // Preencher arr com os valores obtidos dos argumentos:
    //...

    // Calcular a soma com função recursiva:
    double total = sumRec(arr, 0, arr.length);
    out.printf("sum of arguments = %f\n", total);

    // Verificação de correção do resultado:
    assert total == sum(arr, 0, arr.length) : "ERROR";
  }

  // sum of subarray [start,end[ of arr:
  public static double sum(double[] arr, int start, int end) {
    assert arr != null;
    assert start >= 0 && start <= end && end <= arr.length;

    double res = 0;
    for (int i = start; i < end; i++)
      res += arr[i];
    return res;
  }

  // same thing, recursive:
  public static double sumRec(double[] arr, int start, int end)
  {
	  assert arr != null;
	  assert start >= 0 && start <= end && end <= arr.length;
	  
	  double res =  0;
	  if(start == end - 1)
	  {
		  return arr[end-1];
	  }
	  else
	  {
		  return arr[end-1] + sumRec(arr, start, end -1);
	  }
  }

}

