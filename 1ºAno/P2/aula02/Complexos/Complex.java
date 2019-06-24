
public class Complex 
{
  private double re; // stores the real part
  private double im; // stores the imaginary part

  // Constructor
  // Example usage: "Complex n1 = new Complex(2,3);"
  public Complex(double real, double imag) 
  {
    re = real;
    im = imag;
  }

  public double real() {   // real part
    return re;
  } 

  public double imag() {   // imaginary part
    return im;
  } 

  public double abs() {    // absolute value (modulus)
    return Math.sqrt(re * re + im * im);
  } 

  public double arg() {  // argument (aka angle or phase) between -pi and pi
    return Math.atan2(im, re);
  } 

}

