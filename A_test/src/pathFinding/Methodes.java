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
	
	// r�cup�ration des informations de la carte dans le constructeur m�thode
	public Methodes(int ligne, int colonne, int iDepart, int jDepart, int iArrive, int jArrive, int[][] obstacles, int [] niveau) {
		// initialisation d'une matrice 2 dimensions de type Case
		matrice = new Case[ligne][colonne];
		// openList de type priorityQueue avec classement des cases par rapport au co�tTotal (�quivalent de F ( heuristique + co�t de deplacement)
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
		
		// calcul du co�t heursitique
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
		// coutTotal de la case de depart � 0
		matrice[debutI][debutJ].coutTotal = 0;
		// ajout des obstables dans la matrice via la m�thode ajoutObstacles
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
		// r�hinitialisation de la viriable origine de la case de d�part � 0
		matrice[checkPointI][checkPointJ].origine=null;
		// ajour de la case de d�part dans l'openList
		openList.add(matrice[checkPointI][checkPointJ]);
		// cr�ation d'un objet de type Case
		Case caseActuelle;
		
		while(openList != null ) { // entr� dans la boucle tant que openList n'est pas vide
		
			caseActuelle = openList.poll(); // caseActuelle r�cup�re le 1er �l�ment de l'openList
				
			if(caseActuelle == null) { // si caseActuelle est null on quitte la boucle
				break;
			}
			niveauPersonnage(personnage, caseActuelle); // passage dans la m�thode niveauPersonnage pour MAJ du niveau du personnage par rapport � la case actuelle
			
				closeList.add(caseActuelle); // ajout de la case actuelle dans la closeList
			
			if(caseActuelle.equals(matrice[finI][finJ])) {	// si la case actuelle correspond � la position de fin on quitte la boucle	
				break;
			}
			// cr�ation d'un objet Case nomm� voisin
			Case voisin;
			
			if(caseActuelle.i - 1 >= 0 ){ // on v�rifie que l'on est dans les limites de taille de la matrice
				voisin = matrice[caseActuelle.i-1][caseActuelle.j]; // voisin r�cup�re les informations de la case apr�s v�rification  
				if(voisin != null) { // on v�rifie si voisin est null 
					if(nouvelAlgo == true) { // si booleenne nouvelAlgo est sur true on l�ve des restrictions 
						// si le niveauMax de la case voisin est inf�rieur au niveau du personnage ou si le niveauMax de voisin est �gal au niveau du personnage +5 
						// lev� de restrictions obligatoire pour permettre � l'algorithme de se d�placer lors des 2eme passage et autres sur des cases de niveau inf�rieur
						if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
							// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement 
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
						}
					}
					// si niveauMin de voisin est �gal au niveauMax de caseActuelle
					if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
						// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
					// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
					else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
						// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
					calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
				}
				if(caseActuelle.j - 1 >= 0){	// on v�rifie que l'on est dans les limites de taille de la matrice
					voisin = matrice[caseActuelle.i-1][caseActuelle.j-1]; // voisin r�cup�re les informations de la case apr�s v�rification  
					if(voisin != null) {  // on v�rifie si voisin est null 
						if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on l�ve des restrictions 
							// si le niveauMax de la case voisin est inf�rieur au niveau du personnage ou si le niveauMax de voisin est �gal au niveau du personnage +5 
							if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
								// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
								calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
							}
						}
						// si niveauMin de voisin est �gal au niveauMax de caseActuelle
						if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
							// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
						// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
						else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)){
							// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
					}
				}
				if(caseActuelle.j + 1 < matrice[0].length){	// on v�rifie que l'on est dans les limites de taille de la matrice
					voisin = matrice[caseActuelle.i-1][caseActuelle.j+1]; // voisin r�cup�re les informations de la case apr�s v�rification  
					if(voisin != null) { // on v�rifie si voisin est null 
						if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on l�ve des restrictions 
							// si le niveauMax de la case voisin est inf�rieur au niveau du personnage ou si le niveauMax de voisin est �gal au niveau du personnage +5 
							if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
								// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
								calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
							}
						}
						// si niveauMin de voisin est �gal au niveauMax de caseActuelle
						if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
							// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
						// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
						else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
							// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
					}
				}
			}
			if(caseActuelle.i + 1 < matrice.length){ // on v�rifie que l'on est dans les limites de taille de la matrice		
				voisin = matrice[caseActuelle.i+1][caseActuelle.j];// voisin r�cup�re les informations de la case apr�s v�rification  
				if(voisin != null) { // on v�rifie si voisin est null 
					if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on l�ve des restrictions 
						// si le niveauMax de la case voisin est inf�rieur au niveau du personnage ou si le niveauMax de voisin est �gal au niveau du personnage +5 
						if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
							// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
						}
					}
					// si niveauMin de voisin est �gal au niveauMax de caseActuelle
					if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
						// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
					// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
					else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
						// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
					calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
				}		
				if(caseActuelle.j - 1 >= 0){	// on v�rifie que l'on est dans les limites de taille de la matrice
					voisin = matrice[caseActuelle.i+1][caseActuelle.j-1];// voisin r�cup�re les informations de la case apr�s v�rification  
					if(voisin != null) { // on v�rifie si voisin est null 
						if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on l�ve des restrictions 
							// si le niveauMax de la case voisin est inf�rieur au niveau du personnage ou si le niveauMax de voisin est �gal au niveau du personnage +5 
							if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
								// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
								calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
							}
						}
						// si niveauMin de voisin est �gal au niveauMax de caseActuelle
						if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
							// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
						// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
						else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
							// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
					}
					
				}
				if(caseActuelle.j + 1 < matrice[0].length){	// on v�rifie que l'on est dans les limites de taille de la matrice
					voisin = matrice[caseActuelle.i+1][caseActuelle.j+1];// voisin r�cup�re les informations de la case apr�s v�rification  
					if(voisin != null) { // on v�rifie si voisin est null 
						if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on l�ve des restrictions 
							// si le niveauMax de la case voisin est inf�rieur au niveau du personnage ou si le niveauMax de voisin est �gal au niveau du personnage +5 
							if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
								// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
								calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
							}
						}
						// si niveauMin de voisin est �gal au niveauMax de caseActuelle
						if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
							// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
						// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
						else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
							// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_DIAG);
						}
					}
				}
			}
			if (caseActuelle.j - 1 >= 0){	// on v�rifie que l'on est dans les limites de taille de la matrice
				voisin = matrice[caseActuelle.i][caseActuelle.j-1];// voisin r�cup�re les informations de la case apr�s v�rification  
				if(voisin != null) { // on v�rifie si voisin est null 
					if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on l�ve des restrictions 
						// si le niveauMax de la case voisin est inf�rieur au niveau du personnage ou si le niveauMax de voisin est �gal au niveau du personnage +5 
						if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
							// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
						}
					}
					// si niveauMin de voisin est �gal au niveauMax de caseActuelle
					if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
						// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
					// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
					else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
						// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
					calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
				}	
			}
			if (caseActuelle.j + 1 < matrice[0].length){	// on v�rifie que l'on est dans les limites de taille de la matrice	
				voisin = matrice[caseActuelle.i][caseActuelle.j+1];// voisin r�cup�re les informations de la case apr�s v�rification  
				if(voisin != null) { // on v�rifie si voisin est null 
					if(nouvelAlgo == true) {// si booleenne nouvelAlgo est sur true on l�ve des restrictions 
						// si le niveauMax de la case voisin est inf�rieur au niveau du personnage ou si le niveauMax de voisin est �gal au niveau du personnage +5 
						if(voisin.niveauMax < personnage.niveau || voisin.niveauMax.equals(personnage.niveau+5)) {
							// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
							calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
						}
					}
					// si niveauMin de voisin est �gal au niveauMax de caseActuelle
					if(voisin.niveauMin.equals(caseActuelle.niveauMax)) {
						// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
						calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
					// sinon si le niveau du personnage se situe entre les niveaux min et max de voisin
					else if((personnage.niveau >= voisin.niveauMin && personnage.niveau <= voisin.niveauMax)) {
						// entr�e dans m�thode calculCoutVoisin qui prend en parametre caseActuelle, voisin et le coutTotal de caeActuelle auquel on ajout le cout de d�placement
					calculCoutVoisin(caseActuelle, voisin, caseActuelle.coutTotal + COUT_DEP_H_OU_V);
					}
				}
			}
		}
		boolean caseFinPresent = closeList.contains(matrice[finI][finJ]); // on v�rifie si la closeList contient les coordonn�es de destinations
		if(caseFinPresent == false ) { // si non
			calculeItineraire(); // entr�e dans la m�thode calculItin�raire	
		}else { // sinon
			Case caseTampon = matrice[finI][finJ]; // cr�ation d'une variable tampon qui r�cup�res les informations de la case de tin
			trie(caseTampon); // entr�e dans m�thode trie
		}
	}
	
	public void niveauPersonnage(Personnage personnage, Case caseActuelle) {
		// si le niveau du personnage est �gal au niveau minimum de la caseActuelle
		if(personnage.niveau.equals(caseActuelle.niveauMin)) {
			personnage.niveau = caseActuelle.niveauMax; // le niveau du personnage prend la valeur du niveauMax de la case actuelle
		}else if (personnage.niveau > caseActuelle.niveauMin && personnage.niveau < caseActuelle.niveauMax) { // sinon si le niveau du personnage se situe entre les niveaux Min et Max de la case actuelle
			personnage.niveau = caseActuelle.niveauMax; // le niveau du personnage prend la valeur du niveauMax de la case actuelle
		}else if (personnage.niveau > caseActuelle.niveauMax || personnage.niveau < caseActuelle.niveauMin) { // sinon si le niveau du personnage est inf�rieur ou sup�rieur au niveau de la case actuelle
			return; // aucune modification et retour dans algorithme
		}
	}
	
	public void calculCoutVoisin(Case caseActuelle, Case voisin, int coutCaseActuelle) {
		// on v�rifie si le voisin est d�j� pr�sent dans la closeList
		boolean voisinPresent = closeList.contains(voisin); 
		// si oui
		if(voisinPresent == true) {
			return; // on retourne dans algorithme
		}
		// on stock la valeur heuristique de voisin + le coutCaseActuelle
		int voisinCoutTotal = voisin.heuristique + coutCaseActuelle;
		voisinPresent = openList.contains(voisin); // on v�rifie si voisin est pr�sent dans l'openList
		
		if(voisinPresent == false || voisin.coutTotal > voisinCoutTotal) { // si voisin n'est pas dans l'openList ou si le coutTotal de voisin est sup�rieur � variable voisinCoutotal
			voisin.coutTotal = voisinCoutTotal; // mise � jour de la valeur de voisin.coutTotal
			voisin.origine = caseActuelle; // on d�finie la caseActuelle comme coordonn�es d'origines de cette case
			
			if(voisinPresent == false) { // si voisin n'est pas pr�sent dans l'openList
				openList.add(voisin); // on ajout voisin � l'openList
			}
		}
	}
	
		
	public void calculeItineraire() {
		// on r�cup�res les informations de la case de d�part dans une variable locale caseTampon
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
		// on stock dans variables checkPoint les coordonn�es i/j de cette case
		checkPointI = caseTampon.i;
		checkPointJ = caseTampon.j;
		// on vide la closeList et l'openList
		openList.clear();
		closeList.clear();
		// on entre dans la m�thode trie
		trie(caseTampon);
		// on initialise nouvelAlgo sur true
		nouvelAlgo = true;
		// on relance la m�hode algorithme
		algorithme();
	}
	
	public void trie(Case c) {
		// obn ajout c dans la liste chemin
		chemin.add(c);
		while(c.origine != null) { // tant que la variable origine de c est diff�rent de null
			chemin.add(c); // on ajout c � la liste chemin
			c = matrice[c.origine.i][c.origine.j]; // c prend les valeur de sa case origine
		}
		// apr�s cette �tape le chemin de la case de d�part � case X est stock en sens inverse ( de case X vers case d�part)
		// pour les rang�es dans l'ordre :
		for(int i = chemin.size()-1; i > 0; i--) {
			// c rcup�re les valeurs de chemin � l'indice i
			c = chemin.get(i);
			// on ajout c � la liste checkPoint
			checkPoint.add(c);
		}
		// une fois termin� on vide la liste chemin 
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
				if(matrice[i][j] == matrice[debutI][debutJ]) { // si matrice[i][j] correspond aux coordonn�es de d�parts
					System.out.print("  [DEP]"); // affichage
				}else if (matrice[i][j] == matrice[finI][finJ]) { // si matrice[i][j] correspond aux coordonn�es de fin
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
		System.out.println("* AFFICHAGE DES CO�TS DE DEPLACEMENTS FINAUX *");
		System.out.println("**********************************************");
		System.out.println();
		for (int i = 0; i < matrice.length; i++) {
			System.out.println(" ");
			for (int j = 0; j < matrice[i].length; j++) {
				 if (matrice[i][j] != null) {// si matrice[i][j] est diff�rent de null
					 // sp�cification d'affichage en fonction des informations stoch�es dans la variable coutTotal pour avoir un affichage allign�
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
		if(closeList.contains(matrice[finI][finJ])) { // si la closeList contient les coordonn�es de fin
			System.out.println(" ");
			System.out.println("*********************");
			System.out.println("* ITINERAIRE CHOISI *");
			System.out.println("*********************");
			System.out.println();
			System.out.println();
			System.out.print(matrice[debutI][debutJ]); // affichage de la case de d�ppart
			cpt=0;
			for(Case chemin : checkPoint) { // it�ration sur la liste checkPoint
				System.out.print(chemin); // affichage des coordonn�es choisies par le programme
				cpt++;
				if(cpt == 15) { // lorsque CPT est �gal � 15
					System.out.println(" "); // retour � la ligne
					cpt = 0; // r�initialisation de CPT
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
					if(matrice[i][j] == matrice[debutI][debutJ]) { // si matrice[i][j] correspond aux coordonn�es de d�parts
						System.out.print("  [DEP]"); // affichage
					}else if (matrice[i][j] == matrice[finI][finJ]) {// si matrice[i][j] correspond aux coordonn�es de fin
						System.out.print("  [ARR]");// affichage
					}else if(matrice[i][j] == null) { // si matrice[i][j] est null
						System.out.print("  [OBS]");// affichage
					}else {//sinon
						if(checkPoint.contains(matrice[i][j])) // si liste checkPoint contient les coordonn�es matrice[i][j]		
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
				// dans notre cas on n'atteint pas cette information en cas de non r�cup�ration des coordonn�es de fin => boucle infinie
			System.out.println(" Pas de chemin possible jusqu'� l'arriv�e");
		}	

	}
}	
	