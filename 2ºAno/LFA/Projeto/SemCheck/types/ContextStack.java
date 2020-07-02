package types;
import java.util.Stack;

public class ContextStack{

    private Stack<Context> CTStack;
    private int currentContext = 0; 

    public ContextStack(){
        CTStack = new Stack<Context>();
        CTStack.push(new Context());                //Criação do contexto global
        CTStack.peek().push(new SymbolTable());     //Criação da tabela global e sua adição ao contexto global
    }

    //Ir  para um contexto mais "restrito"  (APENAS A USAR NA ANÁLISE SEMÂNTICA)
    public void goUpContext(){
        if(CTStack.size() == currentContext+1){
            CTStack.push(new Context());         //Criar um novo contexto caso ele não exista
        }
        currentContext++;
        CTStack.get(currentContext).push(new SymbolTable());  //Criar uma tabela nova neste contexto
        if(previousIsCycle()) CTStack.get(currentContext).setInCycle(true);
    }

    //Ir para um contexto menos "restrito"    (APENAS A USAR NA ANÁLISE SEMÂNTICA)
    public void goDownContext(){
        currentContext--;
    } 
  
    //Ir  para um contexto mais "restrito" (APENAS A USAR NO COMPILADOR)
    public void UpContext(){
        currentContext++;
    }

    //Sair de um contexto (APENAS A USAR NO COMPILADOR)
    public void DownContext(){
        CTStack.get(currentContext).pop();
        currentContext--;
    }


    //verificar se o contexto atual e os que estão abaixo na Stack contêm um símbolo
    public boolean contains(String varName){
        Stack<Context> aux = new Stack<Context>();
        int i = CTStack.size()-1;
        boolean contains = false;

        while(i!=currentContext){
            aux.push(CTStack.pop());
            i--;
        }
        while(!CTStack.empty()){
            aux.push(CTStack.pop());
            if(aux.peek().contains(varName)){
                contains = true;
                break;
            }
        }
        while(!aux.empty())
            CTStack.push(aux.pop());
        return contains;
    }

    //Obter um símbolo do contexto atual ou dos inferiores
    public Symbol get(String varName){
        Stack<Context> aux = new Stack<Context>();
        int i = CTStack.size()-1;
        Symbol sym = null;

        while(i!=currentContext){
            aux.push(CTStack.pop());
            i--;
        }
        while(!CTStack.empty()){
            aux.push(CTStack.pop());
            if(aux.peek().contains(varName)){
                sym = aux.peek().get(varName);
                break;
            }
        }
        while(!aux.empty())
            CTStack.push(aux.pop());
        return sym;
    }

    //Inserir umm símbolo no current context
    public void put(String s, VariableSymbol v){
        CTStack.get(currentContext).put(s, v);
    }

    //Inverter as stacks de tabelas de símbolos de cada contexto (sem ser a global)
    public void reverse(){
        for(int i=1;i<CTStack.size();i++)
            CTStack.get(i).reverse();
    }       

    //Obter o contexto atual 
    public Context getCurrentContext(){
        return CTStack.get(currentContext);
    }

    //função adicional para verificar se algum dos contextos anteriores ao current é um ciclo
    private boolean previousIsCycle(){
        for(int i=currentContext;i>=0;i--){
            if(CTStack.get(i).inCycle())
                return true;
        }
        return false;
    }
}