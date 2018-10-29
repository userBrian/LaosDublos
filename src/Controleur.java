import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class Controleur {
	
	private static PL probleme;
	private static Affichage fenetre;
	private MethIte iterative;
	private Recuit recuit;

	public static void main(String[] args) {
		//fenetre = new Affichage();

		List<double[][]> infos = new ArrayList<double[][]>();
		infos = Parseur.parserXML(new File("a280.xml"));
		probleme = new PLPVC(infos.get(1));
		RecuitPVC r = new RecuitPVC((PLPVC)probleme);
		System.out.println("Go !");
		SolutionPVC sol = r.solutionInitiale();
		for(int i = 0; i < 280; i++){
			for(int j = 0; j < 280; j++)
				System.out.println(sol.getMatriceSolution()[i][j]);
			System.out.println("\n");
		}
		//System.out.println(infos.get(0)[1][0]);

		//fenetre.affichageVilles(infos.get(0));
		
	}

}
