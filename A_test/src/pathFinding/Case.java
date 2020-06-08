package pathFinding;

public class Case 
{
	
	public int i, j; 		 // coordonnées dans la matrice
	public int heuristique;  // calculé avec l'algorithme de manathan (case[i] - caseFin[i]) + (case[j] - caseFin[j])
	public int coutTotal; 	 // cout heuristique + cout de déplacement
	public Case origine;	 // case avant déplacement
	public Integer niveauMin;//	niveauMin et niveauMax des zones sur la carte
	public Integer niveauMax;
	
	public Case(int i, int j) 
	{
		this.i = i;
		this.j = j;
	}
	
	@Override
	public String toString() {
		return "[" + i + "," + j + "]";
	}
}
 







