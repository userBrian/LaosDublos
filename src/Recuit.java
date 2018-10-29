
public abstract class Recuit {

	private int nbIterations;
    private double meilleurCout;
    protected double temperature;
    protected PL probleme;
	
	public Recuit(PL prob) {
		probleme = prob;
		temperature = 10000;
	}
	
	protected void initialiserTemp(){
		
	}
	
	protected abstract void mainLoop();
	
	protected abstract Solution selectionMouvement(SolutionPVC solInit);
	
	protected abstract Solution solutionInitiale();
	
}
