package pathFinding;

import java.util.Scanner;

public class Exeption {
	 
		static Scanner sc = new Scanner(System.in);
		 
		public static int numericExceptions(int valeur) 
		{
			// variable bpoleenne erreur initialisée sur false;
			 boolean erreur = false;
			 String choix = " "; // initialisation d'une variable string quelconque
			 
			do {
				try 
				{
					// la variable String choix récupère la saisie utilisateur de la methode informationClient() de la classe Informations
					choix= sc.next();
					valeur = Integer.parseInt(choix); // variable convertie en int
				
					erreur = false;	// confirmation de la booleenne sur false afin de permettre une sortie de boucle en cas de saisie valide
				} catch(NumberFormatException e) // si erreur de saisie utilisateur => déclenchement de l'exception
				{
					//message envoyé au client
					System.out.println(choix+ " " +"n'est pas une option valide, recommencez : ");
					erreur = true;	//booleenne erreur passe sur true
				}
			}while(erreur == true); // on recommence la demande de saisie utilisateur tant que la variable booleenne erreur est sur true
			
			return valeur; // retour de la valeur testée
		}
}
