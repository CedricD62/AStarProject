package pathFinding;

import java.util.Scanner;

public class CreationPersonnage 
{
	// initialisation des variables 
	private static final String GUERRIER 	= "guerrier";
	private static final String NINJA 		= "ninja";
	private static final String BARDE 		= "barde";
	private static final String MAGEBLANC	= "mage blanc";
	private static final String MAGENOIR	= "mage noir";
	private static final String PALADIN		= "paladin";
	private static final String ULDHA 		= "uldha";
	private static final String LIMSA 		= "limsa";
	private static final String GRIDANIA 	= "gridania";
	
	static Personnage joueur = new Personnage();
	static Scanner sc = new Scanner(System.in);
	
	public static void initialisationPersonnage() { 
		// r�cup�ration des saisies utilisateur pour cr�ation du personnages
		// akpit des information � joueur de type personnage initialis� plus haut 
		int choix = 0;
		
		System.out.println("********************************");
		System.out.println("* CREATION DE VOTRE PERSONNAGE *");
		System.out.println("********************************");
		System.out.println();
		System.out.print("Veuillez indiquer le nom de votre personnage : ");
			joueur.setNom(sc.next());
		System.out.println();	
		System.out.println("Votre personnage sera de sexe : ");
		System.out.println();
		System.out.println(" - 1 Masculin");
		System.out.println(" - 2 Feminin");
		System.out.println();
		System.out.print("Indiquez votre choix : ");
			choix = Exeption.numericExceptions(choix);
			while(choix != 1 && choix != 2) {
				System.out.println();
				System.out.println("Veuillez s�lectionner une option valide :");
				System.out.println(" - 1 Masculin");
				System.out.println(" - 2 Feminin");
				System.out.println();
				System.out.print("Votre choix : ");
					choix = Exeption.numericExceptions(choix);
			}
			
			if(choix == 1) {
				joueur.setMasculin(true); 
			}else {
				joueur.setFeminin(true);
			}
		System.out.println();
		System.out.println("DEFINITION DU ROLE DE VOTRE PERSONNAGE :");
		System.out.println();
		System.out.println("Choisissez votre r�le : ");
		System.out.println();
		System.out.println(" - 1 Combatant au corps � corps");
		System.out.println(" - 2 Combatant � distance");
		System.out.println(" - 3 Soigneur");
		System.out.println();
		System.out.print("Indiquez votre choix : ");
			choix = Exeption.numericExceptions(choix);
				while(choix < 1 && choix > 3) {
					System.out.println();
					System.out.println("Veuillez s�lectionner une option valide :");
					System.out.println(" - 1 Combatant au corps � corps");
					System.out.println(" - 2 Combatant � distance");
					System.out.println(" - 3 Soigneur");
					System.out.println();
					System.out.print("Votre choix : ");
						choix = Exeption.numericExceptions(choix);
				}
			
			switch (choix) {
			case 1:
				System.out.println();
				System.out.println("vous avez choisit Combatant au corps � corps ");
				System.out.println("Voici les m�tiers disponibles : ");
				System.out.println(" - 1 Guerrier");
				System.out.println(" - 2 Paladin");
				System.out.println(" - 3 Ninja");
				System.out.println();
				System.out.print("Indiquez votre choix : ");
					choix = Exeption.numericExceptions(choix);
						while(choix < 1 && choix > 3) {
							System.out.println();
							System.out.println("Veuillez s�lectionner une option valide :");
							System.out.println(" - 1 Guerrier");
							System.out.println(" - 2 Paladin");
							System.out.println(" - 3 Ninja");
							System.out.println();
							System.out.print("Votre choix : ");
								choix = Exeption.numericExceptions(choix);
						}
						
					if(choix == 1) { // les m�tiers choisit determinent la ville de d�part 
						joueur.setMetier(GUERRIER);
						joueur.setLimsa(true); 
						joueur.setVille(LIMSA);
					}else if (choix == 2) {
						joueur.setMetier(PALADIN);
						joueur.setUldha(true);
						joueur.setVille(ULDHA);
					}else {
						joueur.setMetier(NINJA);
						joueur.setLimsa(true);
						joueur.setVille(LIMSA);
					}
					
			break;
			case 2:
				System.out.println();
				System.out.println("vous avez choisit Combatant � distance ");
				System.out.println("Voici les m�tiers disponibles : ");
				System.out.println(" - 1 Mage noir");
				System.out.println(" - 2 Barde");
				System.out.println();
				System.out.print("Indiquez votre choix : ");
					choix = Exeption.numericExceptions(choix);
						while(choix != 1 && choix != 2) {
							System.out.println();
							System.out.println("Veuillez s�lectionner une option valide :");
							System.out.println(" - 1 Mage noir");
							System.out.println(" - 2 Barde");
							System.out.println();
							System.out.print("Votre choix : ");
								choix = Exeption.numericExceptions(choix);
						}
				
					if(choix == 1) {
						joueur.setMetier(MAGENOIR);
						joueur.setUldha(true);
						joueur.setVille(ULDHA);
					}else {
						joueur.setMetier(BARDE);
						joueur.setGridania(true);
						joueur.setVille(GRIDANIA);
					}
			break;
			default:
				System.out.println();
				System.out.println("vous avez choisit Soigneur ");
				System.out.println("Voici le m�tier disponible : ");
				System.out.println(" - Mage blanc");
				
					joueur.setMetier(MAGEBLANC);
					joueur.setGridania(true);	
					joueur.setVille(GRIDANIA);
			break;
			}
	}
	
	public static void affichagePersonnage() { // r�capitulation des informations du personnage cr�� et information de la suite du programme
		System.out.println();
		System.out.println("****************************************");
		System.out.println("* CARACTERISTIQUES DE VOTRE PERSONNAGE *");
		System.out.println("****************************************");
		System.out.println();
		System.out.println("Vos caract�ristiques : ");
		System.out.println();
		System.out.println("Nom : " + joueur.getNom());
		System.out.println("R�le : " +joueur.getMetier());
		System.out.println("Ville de d�part : " +joueur.getVille());
		System.out.println("Niveau : " +joueur.niveau);
		System.out.println();
		System.out.println("Vous trouverez ci-apr�s :");
		System.out.println();
		System.out.println(" - l'affichage de la carte sous forme matricielle");
		System.out.println(" - l'affichage des co�ts de d�placements calcul� par le simulateur");
		System.out.println(" - l'itin�raire choisi par le simulateur");
		System.out.println(" - l'affichage du parcours sous sa forme matricielle");
		System.out.println();
	}
	
	public static void LancementAlgo() { // lancement de l'algorithme en fonction de la ville de d�part 
		
		if(joueur.isUldha() == true) {
			Uldha.lancementUldha();
		}else if(joueur.isGridania() == true) {
			Gridania.lancementGridania();
		}else {
			Limsa.lancementLimsa();
		}
		
		
	}
}
