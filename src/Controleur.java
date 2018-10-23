import java.io.File;

public class Controleur {
	
	private static PL probleme;
	private Affichage fenetre;
	private MethIte iterative;
	private Recuit recuit;

	public static void main(String[] args) {
		Parseur p = new Parseur();
		probleme = p.parserFichier(new File("a280.tsp"));
	}

}
