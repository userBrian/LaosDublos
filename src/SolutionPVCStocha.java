import java.util.ArrayList;

public class SolutionPVCStocha extends SolutionPVC {
	
	private double coutSolutionDeterministe;

	public SolutionPVCStocha(int nbVilles) {
		super(nbVilles);
	}

	public SolutionPVCStocha(Solution sol) {
		super(sol);
	}

	public SolutionPVCStocha(ArrayList<Integer> cycle) {
		super(cycle);
	}

	public SolutionPVCStocha(int[][] matriceSolution) {
		super(matriceSolution);
	}

	public boolean contrainteStochastiqueSatisfaite(PL pb)
	{
		return getCout(pb) < coutSolutionDeterministe*1.2;
	}
	
	@Override
	public boolean solutionValide()
	{
		return valeursVariablesValides() && contrainte1satisfaite() && contrainteSousToursSatisfaite() && contrainteStochastiqueSatisfaite();

	}
}
