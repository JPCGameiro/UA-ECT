
#include "Point.h"


Point::Point(void) {
	x=0; y=0;
}
Point::Point(int x0, int y0) {
	x=x0; y=y0;
}
int Point::getX() {
	return x;
}
int Point::getY() {
	return y;
}
void Point::dysplayPoint() {
	cout << "(" << getX() << "," << getY() << ")" << endl;
}
Point Point::sumPoints(Point p) {
	return Point(getX()+p.getX(), getY()+p.getY());
}
Point Point::operator +(Point rhs) {
	return Point(x+rhs.getX(), y+rhs.getY());
}
