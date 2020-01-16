#define _USE_MATH_DEFINES
#include "Circles.h"
#include <math.h>

using namespace std;

Circles::Circles() {
	Figures(Point(0,0));
	raio = 1;
}
Circles::Circles(Point ponto, int r):
	Figures(ponto),	raio(r) {}

Circles::Circles(int x, int y, int r):
	Figures(Point(x,y)), raio(r) {}
	
Circles::Circles(int r):
	Figures(Point(0,0)), raio(r) {}

void Circles::dysplayCircle() {
	cout << "Círculo de raio " << raio << " -> ";
	dysplayFigures();
}
double Circles::CircleArea() {
	return M_PI*(raio^2);
}
double Circles::CirclePerimeter() {
	return 2*M_PI*(raio);
}
