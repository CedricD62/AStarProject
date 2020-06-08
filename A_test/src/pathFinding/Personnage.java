package pathFinding;

public class Personnage 
{
	// initialisation des variables locales
	Integer niveau = 1;
	private String nom;
	private String metier; 
	private String ville;
	private boolean masculin;
	private boolean feminin;
	private boolean uldha;
	private boolean gridania;
	private boolean limsa;
	
	// constucteur dlasse Personnage
	public Personnage () 
	{
		nom = "";
	}

	// informations getter / setter
	public String getNom() {
		return nom;
	}
	public void setNom(String pNom) {
		this.nom = pNom;
	}

	public String getMetier() {
		return metier;
	}
	public void setMetier(String metier) {
		this.metier = metier;
	}

	public boolean isMasculin() {
		return masculin;
	}
	public void setMasculin(boolean masculin) {
		this.masculin = masculin;
	}

	public boolean isFeminin() {
		return feminin;
	}
	public void setFeminin(boolean feminin) {
		this.feminin = feminin;
	}

	public boolean isUldha() {
		return uldha;
	}
	public void setUldha(boolean uldha) {
		this.uldha = uldha;
	}

	public boolean isGridania() {
		return gridania;
	}
	public void setGridania(boolean gridania) {
		this.gridania = gridania;
	}

	public boolean isLimsa() {
		return limsa;
	}
	public void setLimsa(boolean limsa) {
		this.limsa = limsa;
	}

	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
}






