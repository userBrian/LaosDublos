import java.util.ArrayList;

public class MethIte
{
	private CPLEX cplex;
	
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

	public void ajoutContraintesSousTours(SolutionPVC s)
	{
		ArrayList<ArrayList<Integer>> sousTours = s.getSousTours();
		
		for(int i = 0; i < sousTours.size(); i++)
		{
			cplex.ajoutContrainteSousTours(sousTours.get(i));
		}
	}
	
}
