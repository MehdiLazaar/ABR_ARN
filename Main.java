import java.util.ArrayList;
import java.util.random.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;
/**
 * @author Lazaar El Mahdi
 * @version 2023
 * */
public class Main {
	 public static void main(String[] args) {
			int taille = 10;
			int taille2 = 10;
	        int intervalle = 600;
	        int[] tableauCles = new int[taille];
	        ABR<Integer> abr = new ABR<>();
			ABR<Integer> abr2 = new ABR<>();
	        Random random = new Random();

	        // Génération aléatoire des clés et ajout dans le tableau
	        System.out.println("Clés générées aléatoirement : ");
	        for (int i = 0; i < taille; i++) {
	            int cle = random.nextInt(intervalle);
	            tableauCles[i] = cle;
	            System.out.print(cle + " ");
	        }
			System.out.println("\n");
	        // Mesure de temps de construction.
	        long debut1 = System.currentTimeMillis();

	        // Ajout des clés dans l'arbre binaire de recherche (ABR)
	        for (int i = 0; i < taille; i++) {
	            abr.add(tableauCles[i]);
	        }
			abr.add(2);

	        long fin1 = System.currentTimeMillis();

	        // Affichage de l'arbre binaire de recherche après l'ajout des clés
	        System.out.println("Arbre binaire de recherche après l'ajout des clés : ");
	        System.out.println(abr.toString());

	        long tempsExecution = fin1 - debut1;
	        System.out.println("Temps de construction : " + tempsExecution + " millisecondes." + "\n");
	        
	        //Mesure du temps de recherche :
	        
	        long debutRecherche = System.currentTimeMillis();

	        for (int i = 0; i < taille; i++) {
	            abr.contains(2);
	        }
	        long finRecherche = System.currentTimeMillis();

	        long tempsExecutionRecherche = finRecherche - debutRecherche;
	        System.out.println("Temps de recherche par clé : " + (float)tempsExecutionRecherche + " millisecondes.");
			System.out.println("-----------------------------------");
			System.out.println("Apres la suppression de la clé 2 \n");
			abr.supprimer(2);
			System.out.println(abr.toString());

			long tempsDefDeb = System.currentTimeMillis();
			for(int i = 0; i < taille2; i++){
				abr2.add(i);
			} 
			long tempsDefFin = System.currentTimeMillis();
			long tempsDefResu = tempsDefFin - tempsDefDeb;
			System.out.println(abr2.toString());
	        System.out.println("Temps de construction de l'arbre du cas defavorable " + tempsDefResu + "ms");
	        System.out.println("-----------------------------------------------------------------------");
	        
	        ARN<Integer> arn = new ARN<>();
	        Random rd = new Random();
	        int[] tabCles = new int[taille];

	        // Génération aléatoire des clés et ajout dans le tableau
	        for (int i = 0; i < taille; i++) {
	            int cle = rd.nextInt(intervalle);
	            tabCles[i] = cle;
	        }
	        long debut2 = System.currentTimeMillis();

	        // Ajout des clés dans l'arbre Rouge-Noir (ARN)
	        for (int i = 0; i < taille; i++) {
	            arn.add(tabCles[i]);
	        }
			arn.add(33);

	        long fin2 = System.currentTimeMillis();

	        // Affichage de l'arbre Rouge-Noir après l'ajout des clés
	        System.out.println("Arbre Rouge-Noir après l'ajout des clés : ");
	        System.out.println(arn.toString());

	        long tempsExec2 = fin2 - debut2;
	        System.out.println("Temps de construction : " + tempsExec2 + " millisecondes.");
	        // Mesure temps de recherche 
        long debutRecherche2 = System.currentTimeMillis();

        for (int i = 0; i < taille; i++) {
            arn.contains(33);
        }

        long finRecherche2 = System.currentTimeMillis();

        long tempsExecutionRecherche2 = finRecherche2 - debutRecherche2;
        System.out.println("Temps total de recherche pour " + tabCles.length + " clés : " + tempsExecutionRecherche2 + " millisecondes.");

		System.out.println("Suppression avec Iterator");
		Iterator<Integer> iterate = arn.iterator();
		System.out.println("Apres la suppresion de la clé 33");
        while (iterate.hasNext()) {
            Integer element = iterate.next();
            if (element.equals(33)) {
                iterate.remove();
                break;
            }
        }
		System.out.println(arn.toString());
	}
}
