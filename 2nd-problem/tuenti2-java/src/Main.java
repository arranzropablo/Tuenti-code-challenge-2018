import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);
	
	public static BigInteger convertToBase (ArrayList<Integer> num, BigInteger base){
		BigInteger result = BigInteger.valueOf(0);
	    for (int i = 0; i < num.size(); i++){
		    	BigInteger test = base.pow((base.intValue() - i) - 1);
	
	        result = result.add(test.multiply(BigInteger.valueOf(num.get(i))));
	    }
	    return result;
	}
	
	public static void main(String[] args) {

		int nCasos;
		String cadena;
		
		nCasos = Integer.parseInt(sc.nextLine());
		
			for (int i = 0; i < nCasos; i++){
				
				cadena = sc.nextLine();

		        int base = cadena.length();

		        ArrayList<Integer> digits = new ArrayList<>();

		        for (int j = 0; j < base; j++){

		            digits.add(j);
		        }
		        
		        Collections.sort(digits);
		        Collections.reverse(digits);
		        digits.toString();
		        
		        BigInteger maxnum = convertToBase(digits, BigInteger.valueOf(base));
		        
		        
		        //quito un 1 y lo añado al final y le doy la vuelta
		        digits.remove(new Integer(1));
		        digits.add(1);
		        Collections.reverse(digits);
		        
		        BigInteger minnum = convertToBase(digits, BigInteger.valueOf(base));

				System.out.println("Case #" + String.valueOf(i + 1) + ": " + String.valueOf(maxnum.subtract(minnum)));
			}

	}

}
