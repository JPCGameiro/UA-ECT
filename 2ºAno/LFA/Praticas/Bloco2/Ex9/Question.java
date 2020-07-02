import java.util.HashMap;

public class Question
{
    private String id;
    private String question;
    private HashMap<String, Integer> answers;

    public Question(String id, String question)
    {
        this.id=id;
        this.question=question;
        this.answers = new HashMap<String, Integer>();
    }

    public String id() { return id; }
    public String question() { return question; }
    public HashMap<String, Integer> answers() { return answers; }

    //adicionar uma resposta
    public void addAnswer(String answer, int points)
    {
        if(!answers.containsKey(answer))
            answers.put(answer, points);
    }

    public String toString()
    {
        String result = "Question ID: "+id+"\n";
        result = result +"- "+question+"\n";
        for(String s : answers.keySet())
            result = result + "    "+s+" : "+answers.get(s)+"\n";
        return result;
    }

    public String printNAnswers(int n)
    {
        String result = "Question ID: "+id+"\n";
        result = result +"- "+question+"\n";
        for(String s : answers.keySet()){
            if(n!=0){
                result = result + "    "+s+";\n";
                n--;
            }
            else 
                break;
        }
        return result;

    }
}