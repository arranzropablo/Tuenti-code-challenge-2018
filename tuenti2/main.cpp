#include <iostream>
#include <math.h>

using namespace std;

unsigned long long convertToBase(string num, int base){
    unsigned long long result = 0;
    for (int i = 0; i < num.size(); i++){
        unsigned long long test = pow(base,(base - i) - 1);
        result = result + ((num[i] - '0') * test);
    }
    return result;
}

//las bases se sacan con permutacions de 0 a n-1 pero la mayor va a ser ordenarlos de mayor a menor y la menor va a ser el menor (no 0) y luego 0 y de menos a mas

//con esa base es el numero qe caiga por 3^2 luego por 3^1 y asi
int main(){

	int nCasos;
    string cadena;

	cin >> nCasos;

	for (int i = 0; i < nCasos; i++){
		cin >> cadena;

        int base = cadena.size();

        string digits;

        for (int j = 0; j < base; j++){

            digits += to_string(j);
        }

        char digitschars[digits.size()];

        strcpy(digitschars, digits.c_str());
        size_t arraySize = sizeof(digitschars)/sizeof(*digitschars);
        sort(digitschars, digitschars+arraySize);

        reverse(digitschars, digitschars+arraySize);

        string maxnum = digitschars;

        //eliminamos un 1 y un 0 poniendolos a 0 y al ponerlo en orden menor a mayor empezamos por la pos 2
        bool done = false;
        int j = digits.size() - 1;
        while (done != true){
            if (digitschars[j] == '1'){
                done = true;
                digitschars[j] = '0';
            }
            j--;
        }

        reverse(digitschars, digitschars+arraySize);

        string minnum = "10";
        //para base > 9 no sería así porque el min sería 1001123456789
        //en vez de esto lo q hay qe hacer es quitar un 1 y un 0 y reordenar todos los chars de menor a mayor (si no es null) y meterlos detrás del string
        for (int j = 2; j < digits.size(); j++){
            minnum += digitschars[j];
        }

		cout << "Case #" << i + 1 << ": " << to_string(convertToBase(maxnum, base) - convertToBase(minnum, base)) << "\n";
	}

	return 0;
}