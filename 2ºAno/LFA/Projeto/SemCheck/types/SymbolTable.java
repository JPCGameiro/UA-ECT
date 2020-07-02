package types;

import java.util.Map;
import java.util.HashMap;

public class SymbolTable{
    protected Map<String,Symbol> symbolTable;

    public SymbolTable(){
        this.symbolTable = new HashMap<String, Symbol>();
    }

    public boolean contains(String s){
        return symbolTable.containsKey(s);
    }
    public Symbol get(String s){
        return symbolTable.get(s);
    }
    public void put(String s, Symbol sym){
        symbolTable.put(s, sym);
    }
}