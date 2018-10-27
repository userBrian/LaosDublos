import java.io.File;
import java.util.List;



public class Controleur {
	
	private static PL probleme;
	private static Affichage fenetre;
	private MethIte iterative;
	private Recuit recuit;

	public static void main(String[] args) {
		/*Parseur p = new Parseur();
		int[][] pos = p.parserFichier(new File("a280.xml"));
		probleme = new PLPVC(pos);
		fenetre = new Affichage();
		fenetre.affichageVilles(pos);
		System.out.println(probleme.toString());*/
		File f = new File("a280.xml");
		List<double[][]> infos = Parseur.parserXML(f, probleme);
		//probleme = new PLPVC(infos.get(1));
		//System.out.println(probleme.toString());
		
	}

}
