import java.util.*;
import java.util.stream.Collectors;
import types.Question;
import java.io.*;

public class Output {
   public static void main(String[] args) throws IOException {
      // get Question objects from a given path to a directory and subdirectory
      QuestionsReaderMain qrm = new QuestionsReaderMain();
      HashMap<String, ArrayList<Question>> mem = qrm.getKuestsFromDirectory(".");

      // put them in a List of Question
      List<Question> memory = new ArrayList<>();
      for(String key : mem.keySet()){
         ArrayList<Question> aux = mem.get(key);
         for(Question q : aux){
            memory.add(q);
         }
      }

      // object for kuest display
      KuestInterface ki;
      List<Question> aux;

      //

      Question v1;
      v1 = QuestionsReaderMain.parseSingleKuest("antlr-P1-1[\"É possivel separar o parser do lexer em ficheiros diferentes?\"]{    \"Não\" - 0;    \"Sim\" - 100;}");

      Question v2;
      v2 = QuestionsReaderMain.parseSingleKuest("antlr-P2-2[\"Qual a linguagem destino de antlr?\"]{    \"C\" - 0;    \"Java\" - 100;    \"Python\" - 0;    \"C++\" - 0;}");

      Question v3;
      v3 = QuestionsReaderMain.parseSingleKuest("antlr-P3-3[\"O que significa o operador * em antlr?\"]{    \"0 ou mais vezes\" - 100;    \"1 ou mais vezes\" - 0;    \"0 ou 1 vez\" - 0;}");

      List<Question> v4 = new ArrayList<>();
      v4 = memory.stream().filter(q -> (q.getTema().equals("matematica"))).limit(3).collect(Collectors.toList());
      v4.add(v1);
      v4.add(v2);
      v4.add(v3);

      Collections.shuffle(v4);

      int v5;
      v5 = v4.size()-1;
      while((v5 != 0)){
         Question v6;
         v6 = v4.get(v5);
         if ((v6.getId() .equals( "P3"))) {
            ki = new KuestInterface(v6, "mc"  , 50 );
            while(!ki.respondeu())System.out.print("");
         }
         else {
            ki = new KuestInterface(v6, "vf"  , 50 );
            while(!ki.respondeu())System.out.print("");
         }
         v5--;
      }
   }
}
