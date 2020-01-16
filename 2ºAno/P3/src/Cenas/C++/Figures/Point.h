#include <iostream>
using namespace std;

class Point {
	public:
		Point(void);					//default constructor
//		virtual ~Point(void);			//Destructor
//		Point(const Point& copy);		//Copy construtctor
//		Point& operator=(const Point);	//Assignement
		Point(int x, int y);			//Constructor(int, int) 
		void dysplayPoint();			//Aditional functions
		int getX();
		int getY();
		Point sumPoints(Point p);
		Point operator+(Point rhs);
	
	private:
		int x;
		int y;
};
