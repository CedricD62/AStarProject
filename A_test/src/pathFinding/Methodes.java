package pathFinding;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Methodes {
	
	// initialisation des variables de la classe Methodes
	public static final int COUT_DEP_DIAG = 14; 
	public static final int COUT_DEP_H_OU_V = 10; 
	private Case [][] matrice;
	private PriorityQueue<Case> openList;
	private ArrayList<Case> closeList = new  ArrayList<Case>();
	private ArrayList<Case>checkPoint = new  ArrayList<Case>();
	private ArrayList<Case>chemin = new  ArrayList<Case>();
	private int debutI , debutJ;
	private int checkPointI, checkPointJ;
	private int finI, finJ;
	private static int cpt = 0;
	private boolean nouvelAlgo = false;
	Personnage personnage = CreationPersonnage.joueur;
	
	// récupération des informations de la carte dans le constructeur méthode
	public Methodes(int ligne, int colonne, int iDepart, int jDepart, int iArrive, int jArrive, int[][] obstacles, int [] niveau) {
		// initialisation d'une matrice 2 dimensions de type Case
		matrice = new Case[ligne][colonne];
		// openList de type priorityQueue avec classement des cases par rapport au coûtTotal (équivalent de F ( heuristique + coût de deplacement)
		openList = new PriorityQueue<Case>((Case c1, Case c2) -> {
			if( c1.coutTotal < c2.coutTotal) {
				return -1;
			}else if (c1.coutTotal > c2.coutTotal) {
				return 1;
			}else {
				return 0;
			}
		});
		// information des cases de departs et fin dans les methodes respectives
		caseDepart(iDepart, jDepart);
		caseFin(iArrive, jArrive);
		
		// calcul du coût heursitique
		for (int i = 0; i < matrice.length; i++) {
			for (int j = 0; j < matrice[i].length; j++) {
				matrice[i][j] = new Case(i,j);
				matrice[i][j].heuristique = Math.abs(i - iArrive) + Math.abs(j - jArrive);
				// ajout des niveaux min et max pour chacunes des cases
				matrice[i][j].niveauMin = niveau[cpt];
				cpt++;
				matrice[i][j].niveauMax = niveau[cpt];
				cpt++;
			}
		}
		// coutTotal de la case de depart à 0
		matrice[debutI][debutJ].coutTotal = 0;
		// ajout des obstables dans la matrice via la méthode ajoutObstacles
		for (int i = 0; i < obstacles.length; i++) {
				ajoutObstacles(obstacles[i][0], obstacles[i][1]);
		}
		
	}

	public void ajoutObstacles(int i, int j) {
		matrice[i][j] = null;
	}
	
	public void caseDepart(int i, int j) {
		debutI = i;
		debutJ = j;
		// variables checkPoint prennent les valeur de la case de depart 
		checkPointI = debutI;
		checkPointJ = debutJ;
	}
	
	public void caseFin(int i, int j) {
		finI = i;
		finJ = j;	
	}
	
	
	// algorithme est le coeur du traitement d'A*
	public void algorithme() {
		// réhinitialisation de la viriable origine de la case de départ à 0
		matrice[checkPointI][checkPointJ].origine=null;
		// ajour de la case de départ dans l'openList
		openList.add(matrice[checkPointI][checkPointJ]);
		// création d'un objet de type Case
		Case caseActuelle;
		
		while(openList != null ) { // entré dans la boucle tant que openList n'est pas vide
		
			caseActuelle = openList.poll(); // caseActuelle récupère le 1er élément de l'openList
				
			if(caseActuelle == null) { // si caseActuelle est null on quitte la boucle
				break;
			}
			niveauPersonnage(personnage, caseActuelle); // passage dans la méthode niveauPersonnage pour MAJ du niveau du personnage par rapport à la case actuelle
			
				closeList.add(caseActuelle); // ajout de la case actuelle dans la closeList
			
			if(caseActuelle.equals(matrice[finI][finJ])) {	// si la case actuelle correspond à la position de fin on quitte la boucle	
				break;
			}
			// création d'un objet Case nommé voisin
			Case voisin;
			
			if(caseActuelle.i - 1 >= 0 ){ // on vérifie que l'on est dans les limites de taille de la matrice
				voisin = matrice[caseActuelle.i-1][caseActuelle.j]; // voisin récupère les informations de la case après vérification  
				if(voisin != null) { // on vérifie si voisin est null 
					if(nouvelAlgo == true) { // si booleenne nouvelAlgo est sur true on lève des restrictions 
						// si le niveauMax de la case voisin est inférieur au niveau du personnage ou si le niveauMax de voisin est égal au niveau du personnage +5 
						// levé de restrictions obligatoire pour permettre à l'algorithme de se déplacer lors des 2eme passage et autres sur des cases de niveau inférieur
						if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
							// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement 
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
						}
					}
					// si niveauMin de voisin est égal au niveauMax de caseActuelle
					if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
						// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
					// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
					else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
						// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
					calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
				}
				if(caseActuelle.j - 1 >= 0){	// on vérifie que l'on est dans les limites de taille de la matrice
					voisin = matrice[caseActuelle.i-1][caseActuelle.j-1]; // voisin récupère les informations de la case après vérification  
					if(voisin != null) {  // on vérifie si voisin est null 
						if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on lève des restrictions 
							// si le niveauMax de la case voisin est inférieur au niveau du personnage ou si le niveauMax de voisin est égal au niveau du personnage +5 
							if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
								// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
								calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
							}
						}
						// si niveauMin de voisin est égal au niveauMax de caseActuelle
						if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
							// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
						// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
						else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)){
							// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
					}
				}
				if(caseActuelle.j + 1 < matrice[0].length){	// on vérifie que l'on est dans les limites de taille de la matrice
					voisin = matrice[caseActuelle.i-1][caseActuelle.j+1]; // voisin récupère les informations de la case après vérification  
					if(voisin != null) { // on vérifie si voisin est null 
						if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on lève des restrictions 
							// si le niveauMax de la case voisin est inférieur au niveau du personnage ou si le niveauMax de voisin est égal au niveau du personnage +5 
							if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
								// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
								calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
							}
						}
						// si niveauMin de voisin est égal au niveauMax de caseActuelle
						if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
							// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
						// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
						else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
							// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
					}
				}
			}
			if(caseActuelle.i + 1 < matrice.length){ // on vérifie que l'on est dans les limites de taille de la matrice		
				voisin = matrice[caseActuelle.i+1][caseActuelle.j];// voisin récupère les informations de la case après vérification  
				if(voisin != null) { // on vérifie si voisin est null 
					if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on lève des restrictions 
						// si le niveauMax de la case voisin est inférieur au niveau du personnage ou si le niveauMax de voisin est égal au niveau du personnage +5 
						if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
							// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
						}
					}
					// si niveauMin de voisin est égal au niveauMax de caseActuelle
					if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
						// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
					// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
					else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
						// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
					calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
				}		
				if(caseActuelle.j - 1 >= 0){	// on vérifie que l'on est dans les limites de taille de la matrice
					voisin = matrice[caseActuelle.i+1][caseActuelle.j-1];// voisin récupère les informations de la case après vérification  
					if(voisin != null) { // on vérifie si voisin est null 
						if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on lève des restrictions 
							// si le niveauMax de la case voisin est inférieur au niveau du personnage ou si le niveauMax de voisin est égal au niveau du personnage +5 
							if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
								// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
								calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
							}
						}
						// si niveauMin de voisin est égal au niveauMax de caseActuelle
						if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
							// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
						// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
						else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
							// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
					}
					
				}
				if(caseActuelle.j + 1 < matrice[0].length){	// on vérifie que l'on est dans les limites de taille de la matrice
					voisin = matrice[caseActuelle.i+1][caseActuelle.j+1];// voisin récupère les informations de la case après vérification  
					if(voisin != null) { // on vérifie si voisin est null 
						if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on lève des restrictions 
							// si le niveauMax de la case voisin est inférieur au niveau du personnage ou si le niveauMax de voisin est égal au niveau du personnage +5 
							if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
								// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
								calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
							}
						}
						// si niveauMin de voisin est égal au niveauMax de caseActuelle
						if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
							// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
						// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
						else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
							// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
					}
				}
			}
			if (caseActuelle.j - 1 >= 0){	// on vérifie que l'on est dans les limites de taille de la matrice
				voisin = matrice[caseActuelle.i][caseActuelle.j-1];// voisin récupère les informations de la case après vérification  
				if(voisin != null) { // on vérifie si voisin est null 
					if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on lève des restrictions 
						// si le niveauMax de la case voisin est inférieur au niveau du personnage ou si le niveauMax de voisin est égal au niveau du personnage +5 
						if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
							// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
						}
					}
					// si niveauMin de voisin est égal au niveauMax de caseActuelle
					if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
						// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
					// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
					else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
						// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
					calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
				}	
			}
			if (caseActuelle.j + 1 < matrice[0].length){	// on vérifie que l'on est dans les limites de taille de la matrice	
				voisin = matrice[caseActuelle.i][caseActuelle.j+1];// voisin récupère les informations de la case après vérification  
				if(voisin != null) { // on vérifie si voisin est null 
					if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on lève des restrictions 
						// si le niveauMax de la case voisin est inférieur au niveau du personnage ou si le niveauMax de voisin est égal au niveau du personnage +5 
						if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
							// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
						}
					}
					// si niveauMin de voisin est égal au niveauMax de caseActuelle
					if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
						// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
					// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
					else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
						// entrée dans méthode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de déplacement
					calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
				}
			}
		}
		boolean caseFinPresent = closeList.contains(matrice[finI][finJ]); // on vérifie si la closeList contient les coordonnées de destinations
		if(caseFinPresent == false ) { // si non
			calculeItineraire(); // entrée dans la méthode calculItinéraire	
		}else { // sinon
			Case caseTampon = matrice[finI][finJ]; // création d'une variable tampon qui récupères les informations de la case de tin
			trie(caseTampon); // entrée dans méthode trie
		}
	}
	
	public void niveauPersonnage(Personnage personnage, Case caseActuelle) {
		// si le niveau du personnage est égal au niveau minimum de la caseActuelle
		if(personnage.niveau.equals(caseActuelle.niveauMin)) {
			personnage.niveau = caseActuelle.niveauMax; // le niveau du personnage prend la valeur du niveauMax de la case actuelle
		}else if (personnage.niveau > caseActuelle.niveauMin && personnage.niveau < caseActuelle.niveauMax) { // sinon si le niveau du personnage se situe entre les niveaux Min et Max de la case actuelle
			personnage.niveau = caseActuelle.niveauMax; // le niveau du personnage prend la valeur du niveauMax de la case actuelle
		}else if (personnage.niveau > caseActuelle.niveauMax || personnage.niveau < caseActuelle.niveauMin) { // sinon si le niveau du personnage est inférieur ou supérieur au niveau de la case actuelle
			return; // aucune modification et retour dans algorithme
		}
	}
	
	public void calculCoutVoisin(Case caseActuelle, Case voisin, int coutCaseActuelle) {
		// on vérifie si le voisin est déjà présent dans la closeList
		boolean voisinPresent = closeList.contains(voisin); 
		// si oui
		if(voisinPresent == true) {
			return; // on retourne dans algorithme
		}
		// on stock la valeur heuristique de voisin + le coutCaseActuelle
		int voisinCoutTotal = voisin.heuristique + coutCaseActuelle;
		voisinPresent = openList.contains(voisin); // on vérifie si voisin est présent dans l'openList
		
		if(voisinPresent == false || voisin.coutTotal > voisinCoutTotal) { // si voisin n'est pas dans l'openList ou si le coutTotal de voisin est supérieur à variable voisinCoutotal
			voisin.coutTotal = voisinCoutTotal; // mise à jour de la valeur de voisin.coutTotal
			voisin.origine = caseActuelle; // on définie la caseActuelle comme coordonnées d'origines de cette case
			
			if(voisinPresent == false) { // si voisin n'est pas présent dans l'openList
				openList.add(voisin); // on ajout voisin à l'openList
			}
		}
	}
	
		
	public void calculeItineraire() {
		// on récupères les informations de la case de départ dans une variable locale caseTampon
		Case caseTampon = matrice[debutI][debutJ];
		Case caseTampon2;
		// on recherche dans la closeList la case de niveau maximum
			for(int i = 0; i <closeList.size(); i++) {
				caseTampon2= closeList.get(i);
				if(caseTampon.niveauMax < caseTampon2.niveauMax  ) {
					caseTampon = caseTampon2;
				}
			}
			// on recherche ensuite la case de niveau maximum ayant le coutTotal le plus faible
			for(int i = 0; i <closeList.size(); i++) {
				caseTampon2= closeList.get(i);
				if(caseTampon.niveauMax == caseTampon2.niveauMax) {
					if(caseTampon.coutTotal > caseTampon2.coutTotal ){
						caseTampon = caseTampon2;						
					}
				}
			}
		// on stock dans variables checkPoint les coordonnées i/j de cette case
		checkPointI = caseTampon.i;
		checkPointJ = caseTampon.j;
		// on vide la closeList et l'openList
		openList.clear();
		closeList.clear();
		// on entre dans la méthode trie
		trie(caseTampon);
		// on initialise nouvelAlgo sur true
		nouvelAlgo = true;
		// on relance la méhode algorithme
		algorithme();
	}
	
	public void trie(Case c) {
		// obn ajout c dans la liste chemin
		chemin.add(c);
		while(c.origine != null) { // tant que la variable origine de c est différent de null
			chemin.add(c); // on ajout c à la liste chemin
			c = matrice[c.origine.i][c.origine.j]; // c prend les valeur de sa case origine
		}
		// après cette étape le chemin de la case de départ à case X est stock en sens inverse ( de case X vers case départ)
		// pour les rangées dans l'ordre :
		for(int i = chemin.size()-1; i > 0; i--) {
			// c rcupère les valeurs de chemin à l'indice i
			c = chemin.get(i);
			// on ajout c à la liste checkPoint
			checkPoint.add(c);
		}
		// une fois terminé on vide la liste chemin 
		chemin.clear();
	}
	
	public void affichageMatrice() {
		System.out.println();
		System.out.println("************************************************");
		System.out.println("* AFFICHAGE DE LA CARTE SOUS FORME MATRICIELLE *");
		System.out.println("************************************************");
		System.out.println();
		// on affiche les informations de la matrice
		for (int i = 0; i < matrice.length; i++) {
			System.out.println();
			for (int j = 0; j < matrice[i].length; j++) {
				if(matrice[i][j] == matrice[debutI][debutJ]) { // si matrice[i][j] correspond aux coordonnées de départs
					System.out.print("  [DEP]"); // affichage
				}else if (matrice[i][j] == matrice[finI][finJ]) { // si matrice[i][j] correspond aux coordonnées de fin
					System.out.print("  [ARR]"); // affichage
				}else if(matrice[i][j] == null) { // si matrice[i][j] est null
					System.out.print("  [OBS]"); // affichage
				}else { // sinon 
					System.out.print("  [ 0 ]"); // affichage
				}
			}
			System.out.println(" ");
		}
		System.out.println(" ");
	}
	
	public void affichageCoutCase() {
		System.out.println();
		System.out.println("**********************************************");
		System.out.println("* AFFICHAGE DES COÛTS DE DEPLACEMENTS FINAUX *");
		System.out.println("**********************************************");
		System.out.println();
		for (int i = 0; i < matrice.length; i++) {
			System.out.println(" ");
			for (int j = 0; j < matrice[i].length; j++) {
				 if (matrice[i][j] != null) {// si matrice[i][j] est différent de null
					 // spécification d'affichage en fonction des informations stochées dans la variable coutTotal pour avoir un affichage alligné
					if(matrice[i][j].coutTotal == 0) {
						System.out.print("  [    "+matrice[i][j].coutTotal+" ]");
					}else if (matrice[i][j].coutTotal < 1000) {
						System.out.print("  [  "+matrice[i][j].coutTotal+" ]");
					
				 	}else {
						System.out.print("  [ "+matrice[i][j].coutTotal+" ]");
					}
				}else{
					System.out.print("  [  OBS ]");
				}
			}
			System.out.println(" ");
		}
		System.out.println(" ");
	}
	
	
	public void affichageChemin() {
		if(closeList.contains(matrice[finI][finJ])) { // si la closeList contient les coordonnées de fin
			System.out.println(" ");
			System.out.println("*********************");
			System.out.println("* ITINERAIRE CHOISI *");
			System.out.println("*********************");
			System.out.println();
			System.out.println();
			System.out.print(matrice[debutI][debutJ]); // affichage de la case de déppart
			cpt=0;
			for(Case chemin : checkPoint) { // itération sur la liste checkPoint
				System.out.print(chemin); // affichage des coordonnées choisies par le programme
				cpt++;
				if(cpt == 15) { // lorsque CPT est égal à 15
					System.out.println(" "); // retour à la ligne
					cpt = 0; // réinitialisation de CPT
				}
			}

			System.out.println(" ");
			System.out.println(" ");
			System.out.println("**********************************************");
			System.out.println("* AFFICHAGE DU CHEMIN SOUS FORME MATRICIELLE *");
			System.out.println("**********************************************");
			System.out.println();
			// on affiche les informations de la matrice
			for (int i = 0; i < matrice.length; i++) {
				System.out.println(" ");
				for (int j = 0; j < matrice[i].length; j++) {
					if(matrice[i][j] == matrice[debutI][debutJ]) { // si matrice[i][j] correspond aux coordonnées de départs
						System.out.print("  [DEP]"); // affichage
					}else if (matrice[i][j] == matrice[finI][finJ]) {// si matrice[i][j] correspond aux coordonnées de fin
						System.out.print("  [ARR]");// affichage
					}else if(matrice[i][j] == null) { // si matrice[i][j] est null
						System.out.print("  [OBS]");// affichage
					}else {//sinon
						if(checkPoint.contains(matrice[i][j])) // si liste checkPoint contient les coordonnées matrice[i][j]		
							System.out.print("  [ X ]");// affichage
						else { //sinon
							System.out.print("  [ 0 ]");// affichage
						}
					}
				}
				System.out.println(" ");
			}	
		System.out.println(" ");
		}else { // si la closedList ne contient pas les information de fin 
				// dans notre cas on n'atteint pas cette information en cas de non récupération des coordonnées de fin => boucle infinie
			System.out.println(" Pas de chemin possible jusqu'à l'arrivée");
		}	

	}
}	
	