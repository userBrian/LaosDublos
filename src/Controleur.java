import java.io.File;

public class Controleur {
	
	private static PL probleme;
	private static Affichage fenetre;
	private MethIte iterative;
	private Recuit recuit;

	public static void main(String[] args) {
		Parseur p = new Parseur();
		int[][] pos = p.parserFichier(new File("a280.tsp"));
		probleme = new PLPVC(pos);
		fenetre = new Affichage();
		fenetre.affichageVilles(pos);
		System.out.println(probleme.toString());
	}

}
