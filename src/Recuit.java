
public abstract class Recuit {

	private int nbAcceptations;
    private double meilleurCout;
    protected Solution meilleureSolution;
    protected Solution solutionActuelle;
    protected double temperature;
    protected PL probleme;
    protected double coutActuel;
    protected double deltaCout;
    protected int count;
    protected int nbIte;
    protected int limit;

   
	
	public Recuit(PL prob) {
		probleme = prob;
		temperature = 1000;
	}
	
	/**
	 * Fixe la valeur initiale de la temperature
	 */
	protected abstract void initialiserTemp();
	
	
	/*
	 * Fixe la solution de depart et le cout associ�
	 */
	protected abstract void initialiserSolutionActuelle();
	
	/*
	 * Renvoie true si la solution est acceptee
	 */
	protected boolean accepterSolution(Solution solConsideree)
	{
				
		deltaCout = solConsideree.getCout(probleme) - solutionActuelle.getCout(probleme);
		
		if(deltaCout < 0) return true;
		else return (Math.random() < Math.exp(-deltaCout/temperature));
	}
	
	/**
	 * Fonction principale du recuit, contient l'algorithme
	 */
	protected void mainLoop()
	{
		nbIte = 1000;
		count = 0;
		limit = 100;
		double rateMin = 0.05;
		double rate;
		
		initialiserSolutionActuelle();
		
		initialiserTemp();
		do
		{
			nbAcceptations = 0;
			for(int i = 1; i < nbIte; i++)
			{
				Solution x = selectionMouvement(getSolutionActuelle());
				
				if (accepterSolution(x))
				{
					coutActuel = solutionActuelle.getCout(probleme);
					setSolutionActuelle(x);
	//				System.out.println("meilleur cout : " + meilleurCout + "\tcout actuel : " + coutActuel);
					if (coutActuel < getMeilleurCout())
					{
						setMeilleurCout(coutActuel);
						System.out.println("Nouvelle meilleure solution trouv�e ! Champagne !");
						setMeilleureSolution(getSolutionActuelle());
						count = 0;
					}
					nbAcceptations++;
				}
			}
			
			rate = (double)nbAcceptations/(double)nbIte;
			if(rate < rateMin)
			{
				count++;
			}
			temperature *= 0.8;
			System.out.println("Je baisse le feu\nNouvelle temperature : "+ temperature + "degres garenheit");
			System.out.println("meilleur cout : " + meilleurCout + "\tcout actuel : " + coutActuel);
			System.out.println("\n\n");
		}
		while(count < limit);
		
		probleme.setSolution(getMeilleureSolution());
	}
	
	/*
	 * Retourne une solution voisine de la solution actuelle 
	 */
	protected abstract Solution selectionMouvement(Solution solInit);
	
	/*
	 * Decide d'une soltuion initaile pour debuter l'algorithme
	 */
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
