
public abstract class Recuit {

	private int nbIterations;
    private double meilleurCout;
    private Solution meilleureSolution;
    private Solution solutionActuelle;
    protected double temperature;
    protected PL probleme;
	
	public Recuit(PL prob) {
		probleme = prob;
		temperature = 10000;
	}
	
	protected void initialiserTemp(){
		
		System.out.println("Initialisation temperature ...");
		setSolutionActuelle(solutionInitiale());
		
		boolean ok = false;
		int nbAcceptations = 0;
		
		while(!ok)
		{
			for (int i = 0; i < 100; i++) {
				if (accepterSolution(SolutionPVC.genererSolutionAleatoire(solutionActuelle.getTaille())))
				{
					nbAcceptations++;
				}
			}
			System.out.println(nbAcceptations + " acceptees");
			if ( nbAcceptations  > 80)
			{
				ok = true;
			}
			else {
				System.out.println("Nombre d'acceptations trop faible, température : " + temperature + " -> " + (temperature*2));
				temperature *= 2;
			}
		}
		
		System.out.println("Temperature initialisée à " + temperature);
		
	}
	
	protected boolean accepterSolution(Solution solConsideree)
	{
		int deltaCout;
		
		deltaCout = solConsideree.getCout() - solutionActuelle.getCout();
		
		if(deltaCout < 0) return true;
		else return (Math.random() < Math.exp(deltaCout/temperature));
	}
	
	protected abstract void mainLoop();
	
	protected abstract Solution selectionMouvement(SolutionPVC solInit);
	
	protected abstract Solution solutionInitiale();

	public double getMeilleurCout() {
		return meilleurCout;
	}

	public void setMeilleurCout(double meilleurCout) {
		this.meilleurCout = meilleurCout;
	}

	public Solution getMeilleureSolution() {
		return meilleureSolution;
	}

	public void setMeilleureSolution(Solution meilleureSolution) {
		this.meilleureSolution = meilleureSolution;
	}

	public Solution getSolutionActuelle() {
		return solutionActuelle;
	}

	public void setSolutionActuelle(Solution solutionActuelle) {
		this.solutionActuelle = solutionActuelle;
	}
	
	
	
}
