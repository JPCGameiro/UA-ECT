#include <iostream>
using namespace std;

class Squares: public Figures {
	public:
		Squares();
		Squares(Point p, int l);
		Squares(int l);
		Squares(int x, int y, int l);
		void dysplaySquares();
		double SquareArea();
		double SquarePerimeter();
		int getL();
	private:
		int l;
};
