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
    public static int minsaltos;

    //terreno de mxn
    //lava -> # (invalid)
    //tierra -> .
    //trampolin -> * (se mueve en L 4-2)
    //se mueven en L(2-1) pero no puede caer en invalid ni fuera
    //el caballero en S tiene qe rescatar a la princesa en P y llevarla a D
    	
    //Esta era la solución con backtracking pero es demasiado complejo y cuando hay más de 531 casillas le cuesta mucho
    
    public static void encontrarCamino(String[][] mapa, boolean[][] marcado, Coordinates<Integer, Integer> currentCell, int saltos, int nFilas, int nColumnas, Coordinates<Integer, Integer> objetivo) {
    		for (int i = 0; i < 8; i++) {
    			List<Boolean> marcadoList = new ArrayList<>();
    			for(boolean[] array : marcado) {
    				for(boolean elem : array) {
    					marcadoList.add(elem);
    				}
    			}
        		if (!marcadoList.contains(false)) {
        			return;
        		}
    			Coordinates<Integer, Integer> nextCell = daPaso(currentCell, i, mapa);
    			if(isValidCell(nextCell, nFilas, nColumnas, mapa) && !marcado[nextCell.getRow()][nextCell.getCol()]) {
    				saltos += 1;
    				marcado[nextCell.getRow()][nextCell.getCol()] = true;
    				if(nextCell.getRow() == objetivo.getRow() && nextCell.getCol() == objetivo.getCol()) {
    					//he llegado
    					if(saltos < minsaltos) {
    						minsaltos = saltos;
    					}
    				} 
    				//poda para que si ya lleva mas que el minimo no siga por ahí
    				else if(saltos < minsaltos) {
    					encontrarCamino(mapa, marcado, nextCell, saltos, nFilas, nColumnas, objetivo);
    				}
    				saltos -= 1;
    				marcado[nextCell.getRow()][nextCell.getCol()] = false;
    			}
    		}
    }
    
    public static Coordinates<Integer, Integer> daPaso(Coordinates<Integer, Integer> cell, int opt, String[][] mapa) {
    		switch(opt) {
    			case 0:
    				if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase("*")) {
    					return new Coordinates<>(cell.getRow() + 2, cell.getCol() + 4);
    				} else if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase(".")) {
    					return new Coordinates<>(cell.getRow() + 1, cell.getCol() + 2);
    				}
    				break;
    			case 1:
    				if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase("*")) {
    					return new Coordinates<>(cell.getRow() + 2, cell.getCol() - 4);
    				} else if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase(".")) {
    					return new Coordinates<>(cell.getRow() + 1, cell.getCol() - 2);
    				}
    				break;
    			case 2:
    				if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase("*")) {
    					return new Coordinates<>(cell.getRow() + 4, cell.getCol() + 2);
    				} else if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase(".")) {
    					return new Coordinates<>(cell.getRow() + 2, cell.getCol() + 1);
    				}
    				break;
    			case 3:
    				if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase("*")) {
    					return new Coordinates<>(cell.getRow() + 4, cell.getCol() - 2);
    				} else if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase(".")) {
    					return new Coordinates<>(cell.getRow() + 2, cell.getCol() - 1);
    				}
    				break;
    			case 4:
    				if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase("*")) {
    					return new Coordinates<>(cell.getRow() - 2, cell.getCol() - 4);
    				} else if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase(".")) {
    					return new Coordinates<>(cell.getRow() - 1, cell.getCol() - 2);
    				}
    				break;
    			case 5:
    				if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase("*")) {
    					return new Coordinates<>(cell.getRow() - 2, cell.getCol() + 4);
    				} else if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase(".")) {
    					return new Coordinates<>(cell.getRow() - 1, cell.getCol() + 2);
    				}
    				break;
    			case 6:
    				if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase("*")) {
    					return new Coordinates<>(cell.getRow() - 4, cell.getCol() - 2);
    				} else if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase(".")) {
    					return new Coordinates<>(cell.getRow() - 2, cell.getCol() - 1);
    				}
    				break;
    			case 7:
    				if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase("*")) {
    					return new Coordinates<>(cell.getRow() - 4, cell.getCol() + 2);
    				} else if(mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase(".")) {
    					return new Coordinates<>(cell.getRow() - 2, cell.getCol() + 1);
    				}
    				break;
    		}
    		//en principio unreachable
    		return new Coordinates<>(0,0);
    }
    
    public static void main(String[] args) {
					
		int nCasos = sc.nextInt();
		for (int i = 0; i < nCasos; i++) {
			String dimensiones = sc.nextLine();
			if (dimensiones.equalsIgnoreCase("")) {
				dimensiones = sc.nextLine();
			}
			int nFilas = Integer.parseInt(dimensiones.split(" ")[0]);
			int nColumnas = Integer.parseInt(dimensiones.split(" ")[1]);
			
			String[][] mapa = new String[nFilas][nColumnas];
			
			Coordinates<Integer, Integer> caballero = new Coordinates<>(0,0);
			Coordinates<Integer, Integer> princesa = new Coordinates<>(0,0);
			Coordinates<Integer, Integer> salida = new Coordinates<>(0,0);
			//quizas hay q hacer nextline y luego hacer split, porqe sino no se como consumir 1 char
			sc.useDelimiter("");
			for(int j = 0; j < nFilas; j++) {
				for(int k = 0; k < nColumnas; k++) {
					mapa[j][k] = sc.next();
					if(mapa[j][k].equalsIgnoreCase("S")) {
						caballero = new Coordinates<>(j, k);
						mapa[j][k] = ".";
					} else if (mapa[j][k].equalsIgnoreCase("P")) {
						princesa = new Coordinates<>(j, k);
						mapa[j][k] = ".";
					} else if (mapa[j][k].equalsIgnoreCase("D")) {
						salida = new Coordinates<>(j, k);
						mapa[j][k] = ".";
					}
				}
				sc.next();
			}
			
			if (isReachable(salida, nFilas, nColumnas, mapa) && isReachable(princesa, nFilas, nColumnas, mapa)) {
				minsaltos = Integer.MAX_VALUE;
				boolean[][] marcado = new boolean[nFilas][nColumnas];
				marcado[caballero.getRow()][caballero.getCol()] = true;
				encontrarCamino(mapa, marcado, caballero, 0, nFilas, nColumnas, princesa);
				int fromKnightToPrincess = minsaltos;
				
				minsaltos = Integer.MAX_VALUE;
				marcado = new boolean[nFilas][nColumnas];
				marcado[princesa.getRow()][princesa.getCol()] = true;
				encontrarCamino(mapa, marcado, princesa, 0, nFilas, nColumnas, salida);
				int fromPrincessToExit = minsaltos;

				System.out.println("Case #" + String.valueOf(i + 1) + ": " + String.valueOf(fromKnightToPrincess + fromPrincessToExit));
				//aqui llamada al vueltatras hasta la princesa contando los pasos y luego la llamada al vueltatras hasta la salida contando los pasos
				
			} else {
				System.out.println("Case #" + String.valueOf(i + 1) + ": IMPOSSIBLE");
			}
			

		}
		
	}
    
    public static boolean estaEnMapa(int i, int j, int filas, int columnas) {
    		return i < filas && i >= 0 && j < columnas && j >= 0;
    }
    
    public static boolean isValidCell(Coordinates<Integer, Integer> cell, int nFilas, int nColumnas, String[][] mapa) {
    		return estaEnMapa(cell.getRow(), cell.getCol(), nFilas, nColumnas) && !mapa[cell.getRow()][cell.getCol()].equalsIgnoreCase("#");
    }
    
    public static boolean isReachable(Coordinates<Integer, Integer> cell, int nFilas, int nColumnas, String[][] mapa) {
    		return (estaEnMapa(cell.getRow() + 2, cell.getCol() + 1, nFilas, nColumnas) && mapa[cell.getRow() + 2][cell.getCol() + 1].equalsIgnoreCase(".")) ||
    				(estaEnMapa(cell.getRow() + 2, cell.getCol() - 1, nFilas, nColumnas) && mapa[cell.getRow() + 2][cell.getCol() - 1].equalsIgnoreCase(".")) ||
    				(estaEnMapa(cell.getRow() + 1, cell.getCol() + 2, nFilas, nColumnas) && mapa[cell.getRow() + 1][cell.getCol() + 2].equalsIgnoreCase(".")) ||
    				(estaEnMapa(cell.getRow() + 1, cell.getCol() - 2, nFilas, nColumnas) && mapa[cell.getRow() + 1][cell.getCol() - 2].equalsIgnoreCase(".")) ||
    				(estaEnMapa(cell.getRow() - 2, cell.getCol() + 1, nFilas, nColumnas) && mapa[cell.getRow() - 2][cell.getCol() + 1].equalsIgnoreCase(".")) ||
    				(estaEnMapa(cell.getRow() - 2, cell.getCol() - 1, nFilas, nColumnas) && mapa[cell.getRow() - 2][cell.getCol() - 1].equalsIgnoreCase(".")) ||
    				(estaEnMapa(cell.getRow() - 1, cell.getCol() + 2, nFilas, nColumnas) && mapa[cell.getRow() - 1][cell.getCol() + 2].equalsIgnoreCase(".")) ||
    				(estaEnMapa(cell.getRow() - 1, cell.getCol() - 2, nFilas, nColumnas) && mapa[cell.getRow() - 1][cell.getCol() - 2].equalsIgnoreCase(".")) ||
    				
    				(estaEnMapa(cell.getRow() + 2, cell.getCol() + 4, nFilas, nColumnas) && mapa[cell.getRow() + 2][cell.getCol() + 4].equalsIgnoreCase("*")) ||
    				(estaEnMapa(cell.getRow() + 2, cell.getCol() - 4, nFilas, nColumnas) && mapa[cell.getRow() + 2][cell.getCol() - 4].equalsIgnoreCase("*")) ||
    				(estaEnMapa(cell.getRow() + 4, cell.getCol() + 2, nFilas, nColumnas) && mapa[cell.getRow() + 4][cell.getCol() + 2].equalsIgnoreCase("*")) ||
    				(estaEnMapa(cell.getRow() + 4, cell.getCol() - 2, nFilas, nColumnas) && mapa[cell.getRow() + 4][cell.getCol() - 2].equalsIgnoreCase("*")) ||
    				(estaEnMapa(cell.getRow() - 2, cell.getCol() + 4, nFilas, nColumnas) && mapa[cell.getRow() - 2][cell.getCol() + 4].equalsIgnoreCase("*")) ||
    				(estaEnMapa(cell.getRow() - 2, cell.getCol() - 4, nFilas, nColumnas) && mapa[cell.getRow() - 2][cell.getCol() - 4].equalsIgnoreCase("*")) ||
    				(estaEnMapa(cell.getRow() - 4, cell.getCol() + 2, nFilas, nColumnas) && mapa[cell.getRow() - 4][cell.getCol() + 2].equalsIgnoreCase("*")) ||
    				(estaEnMapa(cell.getRow() - 4, cell.getCol() - 2, nFilas, nColumnas) && mapa[cell.getRow() - 4][cell.getCol() - 2].equalsIgnoreCase("*"));
    }

}

	class Coordinates<L,R> {

	  private final L row;
	  private final R col;

	  public Coordinates(L row, R col) {
	    this.row = row;
	    this.col = col;
	  }

	  public L getRow() { return row; }
	  public R getCol() { return col; }
	}
