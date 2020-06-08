package pathFinding;
public class Uldha {
	public static void lancementUldha() {
		
		// informations de dimention de la matrice
		 int nbrLigneGrille = 23;
		 int nbrColonnesGrille = 27;
		// coordonn�es du points de d�part
		 int numLigneDepart = 17;
		 int numColonneDepart = 11;
		// coordonn�es du point d'arriv�
		 int numLigneArrive = 1;
		 int numColonneArrive = 12;
		// tableau � 2 dimension regroupant les obstacles dans la zone
		 int [][] obstableZone = {{0,0},{0,1},{0,2},{0,3},{0,4},{0,5},{0,6},{0,7},{0,8},{0,9},{0,10},{0,13},{0,14},{0,15},{0,16},{0,17},{0,18},{0,19},{0,20},{0,21},{0,22},{0,23},{0,24},{0,25},{0,26},
								  {1,0},{1,1},{1,2},{1,3},{1,4},{1,5},{1,6},{1,7},{1,8},{1,9},{1,10},{1,13},{1,14},{1,15},{1,16},{1,17},{1,18},{1,19},{1,20},{1,21},{1,22},{1,23},{1,24},{1,25},{1,26},
								  {2,0},{2,1},{2,2},{2,3},{2,4},{2,5},{2,6},{2,7},{2,8},{2,9},{2,10},{2,11},{2,15},{2,16},{2,17},{2,18},{2,19},{2,20},{2,21},{2,22},{2,23},{2,24},{2,25},{2,26},
								  {3,0},{3,1},{3,2},{3,3},{3,4},{3,5},{3,6},{3,7},{3,8},{3,9},{3,10},{3,11},{3,12},{3,14},{3,15},{3,16},{3,17},{3,20},{3,21},{3,22},{3,23},{3,26},
								  {4,0},{4,1},{4,2},{4,3},{4,4},{4,5},{4,6},{4,7},{4,8},{4,9},{4,10},{4,11},{4,12},{4,14},{4,15},{4,16},{4,17},{4,21},{4,22},{4,26},
								  {5,0},{5,1},{5,2},{5,3},{5,4},{5,5},{5,6},{5,7},{5,8},{5,9},{5,10},{5,11},{5,12},{5,14},{5,15},{5,16},{5,17},{5,26},
								  {6,0},{6,1},{6,2},{6,3},{6,4},{6,5},{6,6},{6,7},{6,8},{6,9},{6,10},{6,11},{6,13},{6,14},{6,15},{6,16},{6,17},{6,18},{6,22},{6,23},
								  {7,0},{7,1},{7,2},{7,3},{7,4},{7,5},{7,6},{7,7},{7,8},{7,9},{7,10},{7,14},{7,15},{7,16},{7,17},{7,25},{7,26},
								  {8,0},{8,1},{8,2},{8,3},{8,4},{8,5},{8,6},{8,7},{8,8},{8,9},{8,10},{8,18},{8,19},{8,20},{8,22},{8,23},{8,24},{8,25},{8,26},
								  {9,0},{9,1},{9,2},{9,3},{9,4},{9,5},{9,6},{9,7},{9,8},{9,9},{9,10},{9,15},{9,16},{9,17},{9,18},{9,19},{9,20},{9,22},{9,23},{9,24},{9,25},{9,26},
								  {10,0},{10,1},{10,2},{10,3},{10,4},{10,5},{10,6},{10,7},{10,8},{10,9},{10,11},{10,14},{10,15},{10,16},{10,17},{10,18},{10,19},{10,20},{10,22},{10,23},{10,24},{10,25},{10,26},
								  {11,0},{11,1},{11,2},{11,3},{11,4},{11,5},{11,6},{11,7},{11,8},{11,10},{11,11},{11,12},{11,14},{11,15},{11,16},{11,17},{11,18},{11,19},{11,20},{11,22},{11,23},{11,24},{11,25},{11,26},
								  {12,0},{12,1},{12,2},{12,3},{12,4},{12,5},{12,6},{12,7},{12,9},{12,10},{12,11},{12,17},{12,18},{12,19},{12,20},{12,22},{12,23},{12,24},{12,25},{12,26},
								  {13,0},{13,1},{13,2},{13,3},{13,4},{13,5},{13,6},{13,8},{13,9},{13,10},{13,11},{13,13},{13,14},{13,15},{13,16},{13,18},{13,19},{13,20},{13,22},{13,23},{13,24},{13,25},{13,26},
								  {14,0},{14,1},{14,2},{14,3},{14,4},{14,5},{14,6},{14,8},{14,9},{14,10},{14,11},{14,12},{14,16},{14,17},{14,19},{14,20},{14,22},{14,23},{14,24},{14,25},{14,26},
								  {15,0},{15,3},{15,4},{15,5},{15,6},{15,8},{15,9},{15,10},{15,11},{15,12},{15,13},{15,14},{15,15},{15,17},{15,18},{15,20},{15,23},{15,24},{15,25},{15,26},
								  {16,0},{16,3},{16,4},{16,5},{16,7},{16,8},{16,9},{16,10},{16,11},{16,12},{16,15},{16,17},{16,18},{16,23},{16,24},{16,25},{16,26},
								  {17,0},{17,6},{17,7},{17,8},{17,15},{17,17},{17,18},{17,19},{17,23},{17,24},{17,25},{17,26},
								  {18,0},{18,1},{18,3},{18,6},{18,7},{18,9},{18,17},{18,18},{18,19},{18,26},
								  {19,0},{19,1},{19,2},{19,3},{19,8},{19,9},{19,10},{19,15},{19,16},{19,18},{19,19},{19,23},{19,24},{19,25},{19,26},
								  {20,0},{20,1},{20,2},{20,6},{20,7},{20,8},{20,9},{20,10},{20,14},{20,15},{20,16},{20,17},{20,19},{20,23},{20,24},{20,25},{20,26},
								  {21,0},{21,1},{21,5},{21,6},{21,7},{21,8},{21,9},{21,10},{21,11},{21,13},{21,14},{21,15},{21,16},{21,17},{21,18},{21,24},{21,25},{21,26},
								  {22,0},{22,1},{22,2},{22,3},{22,4},{22,5},{22,6},{22,7},{22,8},{22,9},{22,10},{22,11},{22,12},{22,13},{22,14},{22,15},{22,16},{22,17},{22,18},{22,19},{22,20},{22,21},{22,22},{22,23},{22,24},{22,25},{22,26}};
		 // tableau � 1 dimension renseignant les nibeai minimum et maximum par case ( block de 2 valeur pour 1 case en partant de la coordonn�e "0.0")
		 int [] niveauZone =  {   0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,45,50 ,45,50 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0,
								  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,45,50 ,45,50 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0,
								  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,45,50 ,45,50 ,45,50 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0,
								  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,40,45 ,0,0 ,0,0 ,0,0 ,0,0, 15,20 ,15,20 ,0,0 ,0,0 ,0,0, 0,0 ,25,30, 25,30, 0,0,
								  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,40,45 ,0,0 ,0,0 ,0,0 ,0,0, 15,20 ,15,20 ,15,20 ,0,0 ,0,0 ,25,30 ,25,30, 25,30, 0,0,
								  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,0,0 ,0,0, 15,20 ,15,20 ,15,20 ,15,20 ,15,20 ,25,30 ,25,30, 25,30, 0,0,
								  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0  ,0,0,  15,20 ,15,20 ,15,20  ,0,0  ,0,0 ,35,40, 35,40, 35,40,
								  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,5,10 ,5,10 ,5,10 , 0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,15,20 ,15,20 ,15,20 ,15,20 ,35,40 ,35,40, 0,0, 0,0,      
								  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,5,10 ,5,10 ,10,15 ,10,15 ,1,1 ,1,1 ,1,1, 0,0 ,0,0 ,0,0 ,15,20 ,0,0 ,0,0, 0,0, 0,0, 0,0,
							   	  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,5 ,1,5 ,10,15 ,10,15 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0, 0,0, 0,0, 0,0,
								  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,1,5 ,1,5 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0, 0,0, 0,0 ,0,0,
							      0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,0,0 ,1,5 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0, 0,0, 0,0 ,0,0,
							      0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,0,0 ,1,1 ,1,5 ,1,1 ,1,1 ,1,1 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0, 0,0, 0,0 ,0,0,
								  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0, 0,0, 0,0 ,0,0,
								  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,1,1 ,1,1 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0, 0,0, 0,0, 0,0,
								  0,0, 20,25 ,20,25 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,1,1 ,0,0 ,25,30 ,25,30 ,0,0 ,0,0, 0,0 ,0,0,
								  0,0, 20,25, 20,25 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,1,1 ,0,0 ,1,1 ,0,0 ,0,0 ,1,1 ,25,30 ,25,30 ,25,30 ,0,0, 0,0, 0,0, 0,0,
								  0,0, 10,15 ,10,15 ,10,15 ,10,15 ,1,1 ,0,0 ,0,0 ,0,0 ,1,1 ,1,1 ,1,1 ,1,1 ,1,1 ,1,1 ,0,0 ,1,1 ,0,0 ,0,0 ,0,0 ,25,30 ,25,30 ,25,30 ,0,0,0,0, 0,0, 0,0, 
								  0,0, 0,0 ,10,15 ,0,0 ,10,15 ,10,15 ,0,0 ,0,0 ,1,1 ,0,0 ,1,1 ,1,1 ,1,1 ,1,1 ,1,1 ,1,1 ,1,1 ,0,0 ,0,0 ,0,0 ,45,50 ,45,50 ,45,50 ,45,50, 45,50, 45,50, 0,0,
								  0,0, 0,0 ,0,0 ,0,0 ,5,10 ,1,5 ,1,5 ,1,1 ,0,0 ,0,0 ,0,0 ,1,1 ,1,1 ,1,1 ,1,1 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,45,50 ,45,50 ,45,50 ,0,0, 0,0, 0,0, 0,0,
								  0,0, 0,0 ,0,0 ,5,10 ,5,10 ,1,5 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,1,1 ,1,1 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,45,50 ,45,50 ,45,50 ,0,0 ,0,0 ,0,0 ,0,0,
								  0,0, 0,0 ,5,10 ,5,10 ,5,10 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,1,1 ,30,35 ,30,35 ,30,35 ,30,35 ,0,0 ,0,0 ,0,0,
								  0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0, 0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,0,0 ,30,35 ,30,35 ,30,35 ,30,35 ,0,0 ,0,0 ,0,0 };
		
		// les informations sont ins�r�es dans le constructeur Methode pour le lancement de d'A*
		Methodes aStar = new Methodes(nbrLigneGrille, nbrColonnesGrille, numLigneDepart, numColonneDepart, numLigneArrive, numColonneArrive, obstableZone,niveauZone);
		
		// lancement des diff�rentes m�thodes de traitement
		aStar.affichageMatrice();  // affichage de la matrice
		aStar.algorithme();        // application de l'algorithme
		aStar.affichageCoutCase(); // affichage des co�ts de d�placements 
		aStar.affichageChemin();   // affichage de la solution
	}
}
