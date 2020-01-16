#include <iostream>
using namespace std;

class Circles: public Figures{
	public:
		Circles(void);
		Circles(Point p, int r);
		Circles(int x, int y, int r);
		Circles(int r);
		void dysplayCircle();
		double CircleArea();
		double CirclePerimeter();
	private:
		int raio;
};
