
import java.util.HashMap;
import java.util.Set;
public class Question {
        private String Tema;
        private int dificuldade;
        private String id;
        private HashMap<String,Integer> respostas = new HashMap<>();
        private String pergunta;
        
        
        public Question(String Tema, String id, int dificuldade, HashMap<String,Integer> respostas,String pergunta) {
                
                this.Tema = Tema;
                this.dificuldade = dificuldade;
                this.id = id;
                this.respostas = respostas;
                this.pergunta = pergunta;
        }
        public Question(int dificuldade, HashMap<String,Integer> respostas,String pergunta) {
                //fazer assert para ver se questiontype pertence a escolhas multiplas ou true or false. Ou entao fazer enum.
                this.dificuldade = dificuldade;
                this.respostas = respostas;
                this.pergunta = pergunta;
        }
        public String getTema(){
                return Tema;
        }
        public int getDificuldade() {
                return dificuldade;
        }
        public String getId() {
                return id;
        }
        public int numRespostas(){
                return respostas.size();
        }
        
        public HashMap<String, Integer> getRespostas() {
                return respostas;
        }
        
        public String getRespostacerta() {
                for        (String key : respostas.keySet()) {
                        if(respostas.get(key) == 100) {
                                return key;
                        }
                }
                return null;
        }
        
        public String getPergunta() {
                return pergunta;
        }
        
        public String toString(){
                StringBuilder s = new StringBuilder();
                s.append(String.format("tema: %s\n id: %s\n", this.Tema, this.id));
                s.append(String.format("->%s\n", pergunta));
                Set<String> keys = respostas.keySet();
                for(String key : keys){
                        s.append(String.format("\t%s - %d\n", key, respostas.get(key)));
                }
                return s.toString();
        }
}