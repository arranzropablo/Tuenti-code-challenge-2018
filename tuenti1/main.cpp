#include <iostream>
#include <vector>
#include <fstream>

using namespace std;

int main(){

	int nCasos, columns, rows;

	cin >> nCasos;

	for (int i = 0; i < nCasos; i++){
		cin >> columns;
		cin >> rows;
		cout << "Case #" << i + 1 << ": " << ((columns - 1) * (rows - 1)) << "\n";
	}

	return 0;
}