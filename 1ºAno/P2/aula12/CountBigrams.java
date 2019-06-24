import static java.lang.System.*;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import p2utils.HashTable;

public class CountBigrams
{

  public static void main(String[] args) {
    HashTable<Integer> counts = new HashTable<>(100000); // Usamos hash table!

    for (int a = 0; a < args.length; a++) { // Processa cada ficheiro
      File fin = new File(args[a]);
      analyseText(fin, counts); // atualiza contagens de bigramas
    }
    
    printCounts(counts);
    
    mostFrequent(counts);
  }

  // Read input text file word by word and count occurences of bigrams
  static void analyseText(File fin, HashTable<Integer> counts) {
    try {
      Scanner scf = new Scanner(fin);
      scf.useDelimiter("[^\\p{IsAlphabetic}]+");
      // ^ Isto serve para especificar que o separador de "tokens" no scanner
      // será qualquer sequência de 1 ou mais carateres não alfabéticos.
      // Assim, cada token devolvido por scf.next() é uma palavra no sentido
      // mais convencional: uma sequência composta apenas de letras!

      String prevWord = null; // serve para guardar a palavra anterior

      while (scf.hasNext()) { // Processa cada palavra
        // palavra atual: é lida do scanner e normalizada:
        String currWord = scf.next().toLowerCase();

        // Completar...

      }
      scf.close();
    }
    catch (IOException e) {
      err.printf("ERROR: %s\n", e.getMessage());
      exit(1);
    }
  }

  // Print each key (bigram) and its count
  static void printCounts(HashTable<Integer> counts) {
    // Completar...

  }
    
  // Find and print the key with most occurrences
  // and its relative frequency.
  static void mostFrequent(HashTable<Integer> counts) {
    // Completar...

  }
}
