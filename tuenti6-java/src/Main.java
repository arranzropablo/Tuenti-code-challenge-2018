import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static Scanner sc = new Scanner(System.in);
    public static int maxScore;

	public static void maxScore(List<Nota> notasList, int score, int currentTime, int prevTime) {
		for(int i = 0; i < notasList.size(); i++) {
			if(isValid(notasList.get(i), currentTime)) {
				score += notasList.get(i).score;
				currentTime = (notasList.get(i).initposition / notasList.get(i).speed) + (notasList.get(i).length / notasList.get(i).speed);
				notasList.get(i).used = true;
				if(score > maxScore) {
					maxScore = score;
				}
				maxScore(notasList, score, currentTime, currentTime);
				//puedo sumarlo y pasarselo restando y luego restarselo
				notasList.get(i).used = false;
				score -= notasList.get(i).score;
				currentTime = prevTime;
			}
		}
	}
	
	public static boolean isValid(Nota nota, int currentTime) {
		
		return !nota.used && currentTime < nota.initposition / nota.speed;
	}
    
    //versión con pre-filtrado
//	public static void maxScore(List<Nota> notasList, int score, int currentTime, final int prevTime) {
//		notasList = notasList.stream().filter(nota -> {return !nota.used && prevTime < nota.initposition/nota.speed;} ).collect(Collectors.toList());
//		for(int i = 0; i < notasList.size(); i++) {
//			score += notasList.get(i).score;
//			currentTime = (notasList.get(i).initposition / notasList.get(i).speed)
//					+ (notasList.get(i).length / notasList.get(i).speed);
//			notasList.get(i).used = true;
//			if (score > maxScore) {
//				maxScore = score;
//			}
//			maxScore(notasList, score, currentTime, currentTime);
//			notasList.get(i).used = false;
//			score -= notasList.get(i).score;
//			currentTime = prevTime;
//		}
//	}
    
    public static void main(String[] args) {
		int nCasos = sc.nextInt();
		for (int i = 0; i < nCasos; i++) {
			int nNotas = sc.nextInt();
			List<Nota> listNotas = new ArrayList<>();
			for (int j = 0; j < nNotas; j++) {
				int X = sc.nextInt();
				int L = sc.nextInt();
				int S = sc.nextInt();
				int P = sc.nextInt();
				Optional<Nota> nota = listNotas.stream().filter(any -> any.initposition / any.speed == X / S && any.length / any.speed == L / S).findFirst();
				if(nota.isPresent()) {
					listNotas.get(listNotas.indexOf(nota.get())).score += P;
				} else {
					listNotas.add(new Nota(X, L, S, P, false));
				}				
			}
			maxScore = 0;
			maxScore(listNotas, 0, 0, 0);
			System.out.println("Case #" + String.valueOf(i + 1) + ": " + String.valueOf(maxScore));
		}
		
	}
    
}

	class Nota {

	  public final int speed;
	  public final int length;
	  public final int initposition;
	  public int score;
	  public boolean used;

	  public Nota(int initposition, int length, int speed, int score, boolean used) {
	    this.speed = speed;
	    this.length = length;
	    this.initposition = initposition;
	    this.score = score;
	    this.used = used;
	  }

	}
