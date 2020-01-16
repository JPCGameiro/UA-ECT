#include <iostream>
using namespace std;

class Figures {
	public:
		Figures(void);
		Figures(Point centro);
		Figures(int x, int y);
		void dysplayFigures();
		Point getCentro();
		
	private:
		Point centro;
};	
