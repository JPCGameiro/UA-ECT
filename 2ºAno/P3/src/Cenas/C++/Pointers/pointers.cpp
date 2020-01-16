#include <iostream>
using namespace std;

int main(int argc, char* argv[])
{
	int a = 55;
	int* p;
	p = &a;
	*p = 8888;
	cout << "Endereço: " << p << " Conteúdo: " << *p << endl;
}
