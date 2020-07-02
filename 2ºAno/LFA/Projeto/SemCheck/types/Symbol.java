package types;

public abstract class Symbol {
	
	private Type type;
	private String name;
	private Value value;
	private boolean valueDefined;
	private String varName;
	private Type subType;	

	public Symbol(Type type, String name) {
		assert type!=null && name!=null;
		this.type = type;
		this.name = name;
	}
	
	public void setValue(Value value) {
		this.value = value;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	public void setValueDefined() {
		this.valueDefined = true;
	}
	public void setsubType(Type subType){
		this.subType = subType;
	}
	
	public String getName() { return name; }
	public String getVarName() { return varName; }
	public Value getValue() { return value; }
	public Type getType() { return type; }
	public boolean isDefined() { return valueDefined; }
	public Type getsubType() { return subType; }

}
