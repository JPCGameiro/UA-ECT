import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import types.*;

public class Util {
    public static void printMemory(HashMap<String, ArrayList<Question>> k){
        // print memory!
        Set<String> keys = k.keySet();

        for(String key : keys){
            List<Question> q = k.get(key);

            for (Question kk : q){
                System.out.println(kk);
            }
        }
    }

    public static void printMemory(List<Question> k){
        for(Question q : k){
            System.out.println(q);
        }
    }
}