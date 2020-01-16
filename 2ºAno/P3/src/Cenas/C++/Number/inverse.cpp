#include <iostream>
using namespace std;

int main(int argc, char* argv[])
{
	float number = -1;
	
	cout << "(0 -> Termina)" << endl;
	do {
		cout << "Insira um número: ";
		cin  >> number;
		if(number!=0)
			cout << "O inverso de " << number << " é " << 1/number << endl;
		else
			cout << "Sistema a Encerrar" << endl;
	}
	while(number!=0);
}
