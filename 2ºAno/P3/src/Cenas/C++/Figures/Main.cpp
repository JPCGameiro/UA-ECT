#include "Point.cpp"
#include "Figures.cpp"
#include "Circles.cpp"
#include "Squares.cpp"
#include <iostream>
using namespace std;

int main(int agrc, char* argv[])
{
	Point p0;
	Point p1(23, 12);
	Point p2(12, 1);
	Point p3 = p1+p2;
	Figures f0(p0);
	Figures f1(1, 3);
	Circles c0;
	Circles c1(4);
	Circles c2(2,2,5);
	Squares s0;
	Squares s1(p2, 5);
	Squares s2(1, 5, 87);
	
	cout << "\nTESTE DA CLASSE POINT \n"<< endl;
	cout << "p0 -> ";
	p0.dysplayPoint();
	cout << "p1 -> ";
	p1.dysplayPoint();
	cout << "p2 -> ";
	p2.dysplayPoint();
	cout << "p1+p2 -> ";
	p1.sumPoints(p2).dysplayPoint();
	cout << "p1+p2 -> ";
	p3.dysplayPoint();
	
	cout << "\nTESTE DA CLASSE FIGURES \n" << endl;
	f0.dysplayFigures();
	f1.dysplayFigures();
	
	cout << "\nTESTE DA CLASSE CIRCLES \n" << endl;
	c0.dysplayCircle();
	cout << "Àrea - " << c0.CircleArea() << " Perimetro - " << c0.CirclePerimeter() << endl;
	c1.dysplayCircle();
	cout << "Àrea - " << c1.CircleArea() << " Perimetro - " << c1.CirclePerimeter() << endl;
	c2.dysplayCircle();
	cout << "Àrea - " << c2.CircleArea() << " Perimetro - " << c2.CirclePerimeter() << endl;
	
	cout << "\nTESTE DA CLASSE SQUARES \n" << endl;
	s0.dysplaySquares();
	cout << "Àrea - " << s0.SquareArea() << " Perimetro - " << s0.SquarePerimeter() << endl;
	s1.dysplaySquares();
	cout << "Àrea - " << s1.SquareArea() << " Perimetro - " << s1.SquarePerimeter() << endl;
	s2.dysplaySquares();
	cout << "Àrea - " << s2.SquareArea() << " Perimetro - " << s2.SquarePerimeter() << endl;
}
