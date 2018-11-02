import java.util.ArrayList;

public class MethIte
{
	private CPLEX cplex;
	
	public MethIte() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public SolutionPVC resolutionProbleme(PLPVC pb)
	{
		//Instancier CPLEX
		cplex = new CPLEX();
		
		SolutionPVC sol = cplex.solveBrian(pb);

		//Lancer la résolution par CPLEX du PLPVC en rajoutant les contrainets de sous tours
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
