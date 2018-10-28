import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;



public class Controleur {
	
	private static PL probleme;
	private static Affichage fenetre;
	private MethIte iterative;
	private Recuit recuit;

	public static void main(String[] args) {
		fenetre = new Affichage();
		/*File f = new File("a280.tsp");
		try{
			List<double[][]> infos = Parseur.parserTSP(new FileReader(f));
			probleme = new PLPVC(infos.get(1));
			fenetre.affichageVilles(infos.get(0));
			System.out.println(probleme.toString());
		} catch(FileNotFoundException e){
			
		}*/
		List<double[][]> infos = Parseur.parserXML(new File("a280.xml"));
		//System.out.println(probleme.toString());
		
	}

}
