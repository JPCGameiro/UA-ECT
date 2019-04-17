import java.util.Scanner;
import java.io.*;
public class Teste2A {
public static void main(String[] args) throws IOException{
Cidade[] cidades;
cidades = lerFichTemp("temperaturas.txt");
procurarListar(cidades," "); // espaço lista tudo
int[] maxmin = maxMin(cidades);
System.out.printf("Máximo = %5d %3d %s%n", cidades[maxmin[0]].dia,
cidades[maxmin[0]].temperatura, cidades[maxmin[0]].nome);
System.out.printf("Mínimo = %5d %3d %s%n", cidades[maxmin[1]].dia,
cidades[maxmin[1]].temperatura, cidades[maxmin[1]].nome);
gravarCidade(cidades, "Aveiro");
int[] freq = freqTemp(cidades);
printFreq(freq);
}
