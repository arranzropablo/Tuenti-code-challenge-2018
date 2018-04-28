import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static Scanner sc = new Scanner(System.in);
    public static int maxScore;

    //terreno de mxn
    //lava -> # (invalid)
    //tierra -> .
    //trampolin -> * (se mueve en L 4-2)
    //se mueven en L(2-1) pero no puede caer en invalid ni fuera
    //el caballero en S tiene qe rescatar a la princesa en P y llevarla a D
    	
    //Esta era la solución con backtracking pero es demasiado complejo y cuando hay más de 531 casillas le cuesta mucho
    
//    public static void maxScore(List<Nota> notasList, boolean[][] marcado, Coordinates<Integer, Integer> currentCell, int saltos, int nFilas, int nColumnas, Coordinates<Integer, Integer> objetivo) {
//    		for (int i = 0; i < 8; i++) {
//    			List<Boolean> marcadoList = new ArrayList<>();
//    			for(boolean[] array : marcado) {
//    				for(boolean elem : array) {
//    					marcadoList.add(elem);
//    				}
//    			}
//        		if (!marcadoList.contains(false)) {
//        			return;
//        		}
//    			Coordinates<Integer, Integer> nextCell = daPaso(currentCell, i, mapa);
//    			if(isValidCell(nextCell, nFilas, nColumnas, mapa) && !marcado[nextCell.getRow()][nextCell.getCol()]) {
//    				saltos += 1;
//    				marcado[nextCell.getRow()][nextCell.getCol()] = true;
//    				if(nextCell.getRow() == objetivo.getRow() && nextCell.getCol() == objetivo.getCol()) {
//    					//he llegado
//    					if(saltos < minsaltos) {
//    						minsaltos = saltos;
//    					}
//    				} 
//    				//poda para que si ya lleva mas que el minimo no siga por ahí
//    				else if(saltos < minsaltos) {
//    					encontrarCamino(mapa, marcado, nextCell, saltos, nFilas, nColumnas, objetivo);
//    				}
//    				saltos -= 1;
//    				marcado[nextCell.getRow()][nextCell.getCol()] = false;
//    			}
//    		}
//    }
     
//  //Inicializamos marcas a false;
//    void mochila(int valor[], int peso[], int m, int max, int k, int sumaPeso, bool marca[], int &maxValor) {
//
//    	int i = k; //int i=0;
//    	while (i < n && !marca[i] && sumaPeso + peso[i] < m) {
//    		sumaPeso += peso[i];
//    		max += valor[i];
//    		marca[i] = true;
//    		if (max > maxValor || maxValor == -1)
//    			maxValor = max;
//    		mochila(valor, peso, m, max, k + 1, sumaPeso, marca, maxValor);
//    		sumaPeso -= peso[i];
//    		max -= valor[i];
//    		marca[i] = false;
//    	}
//    }
    
	public static void maxScore(List<Nota> notasList, int score, int currentTime, Nota prevNota) {
		for(int i = 0; i < notasList.size(); i++) {
			if(isValid(notasList.get(i), currentTime, prevNota)) {
				//si se pulsa a la vez no se actualiza el tiempo
				//mirar el timepo de salida de la anterior y el de esta y q no se actualice el tiempo
				score += notasList.get(i).score;
				//como puedo hacer sto?? le cuesta crear variables y se qeda un poco pillao..
				int auxCurrentTime = currentTime;
				if(prevNota == null || (notasList.get(i).initposition / notasList.get(i).speed) != (prevNota.initposition / prevNota.speed)) {
					//si son notas qe NO se pulsan a la vez actualizamos current time
					currentTime = (notasList.get(i).initposition / notasList.get(i).speed) + (notasList.get(i).length / notasList.get(i).speed);
				}
				notasList.get(i).used = true;
				if(score > maxScore) {
					maxScore = score;
				}
				maxScore(notasList, score, currentTime, notasList.get(i));
				//puedo sumarlo y pasarselo restando y luego restarselo
				notasList.get(i).used = false;
				score -= notasList.get(i).score;
				if(prevNota == null || (notasList.get(i).initposition / notasList.get(i).speed) != (prevNota.initposition / prevNota.speed)) {
					//si son notas qe NO se han pulsan a la vez actualizamos current time
					if(prevNota == null) {
						currentTime = 0;
					} else {
						currentTime = (prevNota.initposition / prevNota.speed) + (prevNota.length / prevNota.speed);
					}
				}
			}
		}
	}
	
	public static boolean isValid(Nota nota, int currentTime, Nota prevNota) {
		//puedo pulsar dos notas a la vez (que empiecen y terminen estrictamente a la vez
		
		return !nota.used && (currentTime < (nota.initposition / nota.speed) || ((nota.initposition / nota.speed) == (prevNota.initposition / prevNota.speed) && (nota.length / nota.speed) == (prevNota.length / prevNota.speed)));
	}
    
    public static void main(String[] args) {
					// i = k en el vuelta atras para q el primero sea uno diferente en cada intento y el for q recorra todo excepto él mismo
		int nCasos = sc.nextInt();
		for (int i = 0; i < nCasos; i++) {
			int nNotas = sc.nextInt();
			List<Nota> listNotas = new ArrayList<>();
			for (int j = 0; j < nNotas; j++) {
				listNotas.add(new Nota(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), false));
			}
			maxScore = 0;
			maxScore(listNotas, 0, 0, null);
			System.out.println("Case #" + String.valueOf(i + 1) + ": " + String.valueOf(maxScore));
		}
		
	}
    
}

	class Nota {

	  public final int speed;
	  public final int length;
	  public final int initposition;
	  public final int score;
	  public boolean used;

	  public Nota(int initposition, int length, int speed, int score, boolean used) {
	    this.speed = speed;
	    this.length = length;
	    this.initposition = initposition;
	    this.score = score;
	    this.used = used;
	  }

	}
