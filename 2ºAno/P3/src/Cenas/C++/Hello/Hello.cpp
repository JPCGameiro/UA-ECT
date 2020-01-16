#include <iostream>
using namespace std;

namespace n1 {
	void iseven(int n)
	{
		if (n%2==0) {
			cout << "Número é Par" << endl;
		}
		else {
			cout << "Número é Impar" << endl;
		}
	}
}

namespace n2 {
	void iseven(int n)
	{
		if (n%2==0) {
			cout << "Par" << endl;
		}
		else {
			cout << "Impar" << endl;
		}
	}
}

using namespace n1;
//using namespace n2;
int main(int argc, char* argv[])
{

	int number = -1;
	while (number!=0) { 
		cout << "Insere um número (0 -> terminar): ";
		cin >> number;
		n1::iseven(number);
	}
	
	return 0;
}


