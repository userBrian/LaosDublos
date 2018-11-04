import java.util.ArrayList;

public class MethIte
{
	private CPLEX cplex;
	
	/**
	 * résolution d'un problème PVC par itération avec CPLEX et l'ajout de sous tours 
	 * @param pb
	 * @return
	 */
	public SolutionPVC resolutionProbleme(PLPVC pb)
	{
		//Instancier CPLEX
		cplex = new CPLEX();
		
		SolutionPVC sol = cplex.solvePVC(pb);

		//Lancer la résolution par CPLEX du PLPVC en rajoutant les contraintes de sous tours
		while(!sol.contrainteSousToursSatisfaite())
		{
			ajoutContraintesSousTours(sol);
			sol =  cplex.resoudre();
		}
		return sol;
	}
	
	/**
	 * Ajoute les contraintes de sous tours au modele CPLEX en fonction des sous tours présents dans la solution s
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
