import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static Scanner sc = new Scanner(System.in);
	
	public static HashMap<Integer, ArrayList<String>> saveNotas() {
		HashMap<Integer, ArrayList<String>> notas = new HashMap<>();
		notas.put(0, new ArrayList<String>() {{add("C"); add("B#");}});
		notas.put(1, new ArrayList<String>() {{add("C#"); add("Db");}});
		notas.put(2, new ArrayList<String>() {{add("D");}});
		notas.put(3, new ArrayList<String>() {{add("D#"); add("Eb");}});
		notas.put(4, new ArrayList<String>() {{add("E"); add("Fb");}});
		notas.put(5, new ArrayList<String>() {{add("F"); add("E#");}});
		notas.put(6, new ArrayList<String>() {{add("F#"); add("Gb");}});
		notas.put(7, new ArrayList<String>() {{add("G");}});
		notas.put(8, new ArrayList<String>() {{add("G#"); add("Ab");}});
		notas.put(9, new ArrayList<String>() {{add("A");}});
		notas.put(10, new ArrayList<String>() {{add("A#"); add("Bb");}});
		notas.put(11, new ArrayList<String>() {{add("B"); add("Cb");}});
		return notas;
	}
	
	public static HashMap<String, ArrayList<String>> crearAcordesMenores(HashMap<Integer, ArrayList<String>> notas){
		HashMap<String, ArrayList<String>> acordes = new HashMap<>();
		for(int nota : notas.keySet()) {
			//pattern WHWWHWW siendo W sumar 2 y H sumar 1
			//G minor : CDD#FGG#A#
			ArrayList<String> acorde = new ArrayList<>();
			acorde.addAll(notas.get(nota % 12));
			acorde.addAll(notas.get((nota + 2) % 12));
			acorde.addAll(notas.get((nota + 3) % 12));
			acorde.addAll(notas.get((nota + 5) % 12));
			acorde.addAll(notas.get((nota + 7) % 12));
			acorde.addAll(notas.get((nota + 8) % 12));
			acorde.addAll(notas.get((nota + 10) % 12));
			String nombre = "m" + notas.get(nota).get(0);
			acordes.put(nombre, acorde);
		}
		return acordes;
	}
	
	public static HashMap<String, ArrayList<String>> crearAcordesMayores(HashMap<Integer, ArrayList<String>> notas){
		HashMap<String, ArrayList<String>> acordes = new HashMap<>();
		for(int nota : notas.keySet()) {
			//pattern WWHWWWH siendo W sumar 2 y H sumar 1
			//C major : CDEFGAB
			ArrayList<String> acorde = new ArrayList<>();
			acorde.addAll(notas.get(nota % 12));
			acorde.addAll(notas.get((nota + 2) % 12));
			acorde.addAll(notas.get((nota + 4) % 12));
			acorde.addAll(notas.get((nota + 5) % 12));
			acorde.addAll(notas.get((nota + 7) % 12));
			acorde.addAll(notas.get((nota + 9) % 12));
			acorde.addAll(notas.get((nota + 11) % 12));
			String nombre = "M" + notas.get(nota).get(0);
			acordes.put(nombre, acorde);
		}
		return acordes;
	}
	
	public static void main(String[] args) {
		
		//puedo hacer que cree para cada semitono su escala y la guarde en una lista de listas, luego qe lea el caso y que se quede con las letras unicas y que haga un findall sobre cada lista y se qeude con las qe cuadren
		
		HashMap<Integer, ArrayList<String>> notas = saveNotas();
		HashMap<String, ArrayList<String>> acordes = new HashMap<>();
		acordes.putAll(crearAcordesMayores(notas));
		acordes.putAll(crearAcordesMenores(notas));
		
		int nCasos = sc.nextInt();
		for (int i = 0; i < nCasos; i++) {
			int nNotas = sc.nextInt();
		    Set<String> notasCancion = new HashSet<String>();
			for (int j = 0; j < nNotas; j++) {
				notasCancion.add(sc.next());
			}

			ArrayList<String> posiblesacordes = new ArrayList<>();
			for (String acorde : acordes.keySet()) {
				if(acordes.get(acorde).containsAll(notasCancion)) {
					posiblesacordes.add(acorde);
				}
			}
			if(posiblesacordes.isEmpty()) {
				System.out.println("Case #" + String.valueOf(i + 1) + ": None");
			} else {
				Collections.sort(posiblesacordes, new CompararAcordes());
				String posiblesacordestoprint = "";
				for (int j = 0; j < posiblesacordes.size(); j++) {
					posiblesacordestoprint += posiblesacordes.get(j) + " ";
				}
				posiblesacordestoprint = posiblesacordestoprint.substring(0, posiblesacordestoprint.length() - 1);
				System.out.println("Case #" + String.valueOf(i + 1) + ": " + posiblesacordestoprint);
			}
		}
		
	}
	

}

class CompararAcordes implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if (o1 == o2) {
            return 0;
        }

        if(o1.charAt(0) == 'M' && o2.charAt(0) == 'M' || o1.charAt(0) == 'm' && o2.charAt(0) == 'm') {
        		if(o1.charAt(1) < o2.charAt(1)) {
        			return -1;
        		} else if(o1.charAt(1) > o2.charAt(1)) {
        			return 1;
        		} else if(o1.charAt(1) == o2.charAt(1)) {
	        		if(o1.length() == 3 && o2.length() == 2) {
	        			return 1;
	        		} else if (o1.length() == 2 && o2.length() == 3) {
	        			return -1;
	        		} else {
	        			return 0;
	        		}
        		}
        } else if(o1.charAt(0) == 'M' && o2.charAt(0) == 'm') {
    			return -1;
        }
        return 0;
    }
}