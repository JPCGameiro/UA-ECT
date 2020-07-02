import java.util.ArrayList;
import java.util.HashMap;

public class Teste {
    public static void main(String[] args){
        // um possível problema futuro as aspas que a String pode conter!!!
        String g = "antlr4-5[\"o pedro é fixe?\"]{ \"Obvio que não\" - 100; \"Não\" - 100;}";
        QuestionsReaderMain qrm = new QuestionsReaderMain();
        HashMap<String, ArrayList<Question>> questions = qrm.parseKuest(g);
        Util.printMemory(questions);
    }
}