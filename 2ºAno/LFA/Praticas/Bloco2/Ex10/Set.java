import java.util.LinkedList;

public class Set
{
    private LinkedList<String> set;

    //Construtor
    public Set()
    {
        this.set = new LinkedList<String>();
    }

    public LinkedList<String> set() { return set; }


    public String toString()
    {
        try{
            String result="{ "+set.get(0);
            for(int i=1;i<set.size();i++)
                result += ", "+set.get(i);
            return result + " }";
        }
        catch(IndexOutOfBoundsException e){
            return "{}";
        }
    }

    //Add element to set
    public boolean add(String s) {
        if(!set.contains(s))
            set.add(s);
        else
            return false;
        return true;
    }

    //check if an elements exists
    public boolean contains(String s) {
        return set.contains(s);
    }

    //set union
    public Set union(Set arg0) {
        Set result = new Set();
        for(String s : set)
            result.add(s);
        for(String s : arg0.set())
            result.add(s);
        return result;
    }

    //set intersection
    public Set intersection(Set arg0) {
        Set result = new Set();
        for(String s: set) {
            for(String s0 : arg0.set()){
                if(s0.equals(s))
                    result.add(s0);
            }
        }
        return result;
    }

    //set difference
    public Set difference(Set arg0) {
        Set result = new Set();
        for(String s : set) {
            for(String s0 : arg0.set()){
                if(!s0.equals(s) && set.contains(s0))
                    result.add(s);
            }
        }
        return result; 
    }
}