#include "Squares.h"

Squares::Squares() {
	Figures(Point(0,0));
	l = 1;
}
Squares::Squares(Point p, int side):
	Figures(p), l(side) {}

Squares::Squares(int x, int y, int side):
	Figures(Point(x, y)), l(side) {}

Squares::Squares(int sides):
	Figures(Point(0,0)), l(sides) {}
	
void Squares::dysplaySquares() {
	cout << "Quadrado de lado " << l << " -> ";
	dysplayFigures();
}

int Squares::getL() {
	return l;
}

double Squares::SquareArea() {
	return l*l;
}

double Squares::SquarePerimeter() {
	return l*4;
}

