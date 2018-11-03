
public abstract class Recuit {

	private int nbIterations;
    private double meilleurCout;
    protected Solution meilleureSolution;
    protected Solution solutionActuelle;
    protected double temperature;
    protected PL probleme;
    protected int deltaCout;

	
	public Recuit(PL prob) {
		probleme = prob;
		temperature = 10000;
	}
	
	protected abstract void initialiserTemp();
	
	protected boolean accepterSolution(Solution solConsideree)
	{
				
		deltaCout = solConsideree.getCout() - solutionActuelle.getCout();
		
		if(deltaCout < 0) return true;
		else return (Math.random() < Math.exp(deltaCout/temperature));
	}
	
	protected void mainLoop(){
		nbIterations = 0;
		
		initialiserTemp();
		
		while(temperature > 10){	// TODO : D�finir un seuil
			while(nbIterations < probleme.getDimension()){
				Solution x = selectionMouvement(getSolutionActuelle());
				
				if (accepterSolution(x))
				{
					setSolutionActuelle(x);
					if (getSolutionActuelle().getCout() < getMeilleurCout())
					{
						setMeilleurCout(getSolutionActuelle().getCout());
						setMeilleureSolution(getSolutionActuelle());
					}
					nbIterations++;

				}
				
			}
			
			temperature *= 0.9;
			nbIterations = 0;
		}
		
		probleme.setSolution(getMeilleureSolution());
	}
	
	protected abstract Solution selectionMouvement(Solution solInit);
	
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
