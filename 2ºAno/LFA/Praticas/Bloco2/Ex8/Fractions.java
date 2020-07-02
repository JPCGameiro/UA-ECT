import java.lang.Math;

public class Fractions{

    private int numerator;
    private int denominator;
    
    //Construtores
    public Fractions(int numerator, int denominator)
    {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    public Fractions(int n)
    {
        this.numerator = n;
        this.denominator = n;
    }

    public int numerator() { return numerator; }
    public int denominator() { return denominator; }
    public double FrationValue() { return numerator/denominator; }
    public String toString() { return numerator+"/"+denominator; }

    //Soma de frações
    public Fractions sum(Fractions a) {
        return new Fractions( (numerator*a.denominator() + denominator*a.numerator()), denominator()*a.denominator());
    }

    //Subtracção de frações 
    public Fractions sub(Fractions a) {
        return new Fractions( (numerator*a.denominator() - denominator*a.numerator()), denominator()*a.denominator());
    }

    //Multiplicação de frações
    public Fractions mult(Fractions a){
        return new Fractions( numerator*a.numerator(), (denominator*a.denominator()));
    }

    //Divisão de frações
    public Fractions div(Fractions a){
        return new Fractions( numerator*a.denominator(), (denominator*a.numerator()));
    }

    //Potência de frações
    public Fractions exp(int ex) {
        if(ex >= 0)
            return new Fractions( (int)Math.pow(numerator, ex), (int)Math.pow(denominator, ex));
        else
            return new Fractions( (int)Math.pow(denominator, ex*-1), (int)Math.pow(numerator, ex*-1));
    }

    //Reduzir frações
    public Fractions reduce(){
        int mdc = MDC(numerator, denominator);
        if(mdc>1) return new Fractions(numerator/mdc, denominator/mdc);
        else return new Fractions(numerator, denominator);
    }
    public int MDC(int a, int b)
    {
        if(b==0) return a;
        else return MDC(a, a%b);
    }

}