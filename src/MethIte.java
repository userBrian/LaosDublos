import java.util.ArrayList;

public class MethIte
{
	private CPLEX cplex;
	
	/**
	 * r�solution d'un probl�me PVC par it�ration avec CPLEX et l'ajout de sous tours 
	 * @param pb
	 * @return
	 */
	public SolutionPVC resolutionProbleme(PLPVC pb)
	{
		//Instancier CPLEX
		cplex = new CPLEX();
		
		SolutionPVC sol = cplex.solvePVC(pb);

		//Lancer la r�solution par CPLEX du PLPVC en rajoutant les contraintes de sous tours
		while(!sol.contrainteSousToursSatisfaite())
		{
			ajoutContraintesSousTours(sol);
			sol =  cplex.resoudre();
		}
		return sol;
	}
	
	/**
	 * Ajoute les contraintes de sous tours au modele CPLEX en fonction des sous tours pr�sents dans la solution s
	 * @param s
	 */
	public void ajoutContraintesSousTours(SolutionPVC s)
	{
		ArrayList<ArrayList<Integer>> sousTours = s.getSousTours();
		for(int i = 0; i < sousTours.size(); i++)
		{
			cplex.ajoutContrainteSousTours(sousTours.get(i));
		}
	}
	
}
