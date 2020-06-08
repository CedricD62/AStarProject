package pathFinding;

public class Case 
{
	
	public int i, j; 		 // coordonn�es dans la matrice
	public int heuristique;  // calcul� avec l'algorithme de manathan (case[i] - caseFin[i]) + (case[j] - caseFin[j])
	public int coutTotal; 	 // cout heuristique + cout de d�placement
	public Case origine;	 // case avant d�placement
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
 







