import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
		while(true) {
			List<String> dnaSamples = new LinkedList<String>(Arrays.asList(sc.nextLine().split(" ")));
			for(int i = 0; i < dnaSamples.size(); i++){
				
				List<String> aux = new LinkedList<>(dnaSamples);
				aux.remove(i);
								
				List<String> posiblescadenas = permute(aux.toArray(new String[0]));
				final String cadenatolookfor = dnaSamples.get(i);
				if(posiblescadenas.stream().anyMatch(cadena -> cadena.contains(cadenatolookfor))) {
					//esto ya las printea, ahora tengo q saber como conectarme al daemon y enviarle las pruebas
					System.out.println(String.valueOf(i + 1));
				}
			}
		}

    }
    //pasarle el array pero sin el elemento q qeremos q no este en la permutacion
    public static List<String> permute(String[] arr){
		List<String> posiblescadenas = new ArrayList<>();
        permuteHelper(arr, 0, posiblescadenas);
        return posiblescadenas;
    }

    private static String permuteHelper(String[] arr, int index, List<String> posiblescadenas){
        if(index >= arr.length - 1){ //If we are at the last element - nothing left to permute
//pushear en posibles cadenas
			String cadenajunta = "";
            for(int i = 0; i < arr.length; i++){
            		cadenajunta += arr[i];
            }
            return cadenajunta;
        }

        for(int i = index; i < arr.length; i++){ //For each index in the sub array arr[index...end]

            //Swap the elements at indices index and i
            String t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;

            //Recurse on the sub array arr[index+1...end]
            posiblescadenas.add(permuteHelper(arr, index+1, posiblescadenas));

            //Swap the elements back
            t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;
        }
        return "";
    }
    
}