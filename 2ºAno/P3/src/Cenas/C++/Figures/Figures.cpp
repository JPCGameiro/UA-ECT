#include "Figures.h"

using namespace std;

Figures::Figures(void) {
	centro = Point(0, 0);
}
Figures::Figures(Point pt) {
	centro = pt;
}
Figures::Figures(int x, int y) {
	centro = Point(x,y);
}
Point Figures::getCentro() {
	return centro;
}
void Figures::dysplayFigures() {
	cout << "Figura de centro ";
	centro.dysplayPoint();
}

