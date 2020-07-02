package types;
import java.util.Stack;

public class Context{

    private Stack<SymbolTable> stack;
    private boolean hasBreak = false;
    private boolean inCycle = false;


    public Context(){
        this.stack = new Stack<SymbolTable>();
    }

    //Adicionar uma SymbolTable ao contexto
    public void push(SymbolTable s){
        stack.push(s);
    }

    //Remover a tabela no topo da Stack (ser utilizado no compilador)
    public SymbolTable pop(){
        return stack.pop();
    }

    //Verificar se a tabela no topo da Stack contem um certo elemento
    public boolean contains(String varName){
        return stack.peek().contains(varName);
    }

    //Obter o símbolo especificado do topo da Stack
    public Symbol get(String varName){
        return stack.peek().get(varName);
    }

    //Adicionar um símbolo à tabela no topo da Stack
    public void put(String s, VariableSymbol v){
        stack.peek().put(s, v);
    }

    //Inverter a Stack para utilização no compilador
    public void reverse(){
        Stack<SymbolTable> aux = new Stack<SymbolTable>();
        while(!stack.empty()){
            aux.push(stack.pop());
        }
        stack = aux;
    }

    //indicar a existência ou não de um break no contexto
    public void setHasBreak(boolean b){
        hasBreak = b;
    }

    //Verificação se a tabela no topo da Stack do contexto tem break ou não
    public boolean hasBreak(){
        return hasBreak;
    }

    //Indicar a presença de um ciclo
    public void setInCycle(boolean b){
        setHasBreak(false);
        inCycle = b;
    }

    //Verificação se a tabela no topo da Stack aparece num contexto de ciclo ou não
    public boolean inCycle(){
        return inCycle;
    }
}