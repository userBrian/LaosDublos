/*
 * Cette classe contient, en plus de solution, une representation matricielle de la soltuion qui 
 * facilite les calcls et operations dessus, ainsi que des fonctions vérifiants les cotraintes
 * du PVC sur la solution
 */
public class SolutionPVC extends Solution {

	/**
	 * Representation matricielle de la solution. Dans cette matrice, la case ij represente
	 * l'arc allant de la ville i a la ville j. Si la case vaut true alors l'arc a ete choisi dans la solution, sinon
	 * la case vaudra false.
	 */
	private boolean matriceSolution[][];
	
	public SolutionPVC(int taille) {
		super(taille);
		matriceSolution = new boolean[taille][taille];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++)
				matriceSolution[i][j] = false;
		}
	}
	
	public SolutionPVC(Solution sol)
	{
		super(sol.getTaille());
		remplirMatriceSolution(sol.getResultat());
	}
	
	/*
	 * Celui la sert pour les tests
	 */
	public SolutionPVC(boolean matriceSolution[][])
	{
		super(matriceSolution.length);
		this.matriceSolution = matriceSolution;
	}
	
	public void remplirMatriceSolution(int resultat[])
	{
		int tailleMatrice = getTaille();
		matriceSolution = new boolean[tailleMatrice][tailleMatrice];
		
		for (int i = 0; i < resultat.length; i++)
		{
			matriceSolution[i%tailleMatrice][i/tailleMatrice] = resultat[i] == 1 ? true : false;
		}
	}

	public boolean[][] getMatriceSolution() {
		return matriceSolution;
	}
	
	/*
	 * Verifie qu'il y a bien un et un seul arc entrant et un seul arc sortant pour chaque ville
	 * dans la solution proposée
	 * 
	 * retourne vrai si la contrainte est satisfaite, faux sinon
	 */
	boolean contrainte1satisfaite()
	{
		int count;
		
		for (int i = 0; i < getTaille(); i++) 
		{
			count = 0;
			for (int j = 0; j < getTaille(); j++) {
				if (matriceSolution[i][j]) count++;
			}
			if (count != 1) return false;
		}
		
		
		for (int j = 0; j < getTaille(); j++) {
			count = 0;
			for (int i = 0; i < getTaille(); i++) {
				if (matriceSolution[i][j]) count++;
			}
			if (count != 1) return false;
		}
		
		return true;
		
	}
	
	/*
	 * Vérifie que les valeurs choisies des ariables sont valides, ie qu'elle sont
	 * comprises dans {0, 1}
	 * 
	 * retourne true si les variables sont valides, false sinon
	 */
	boolean valeursVariablesValides()
	{
		for (int variable : getResultat()) {
			if (variable != 0 && variable != 1) return false;
		}
		return true;
	}
	
	/*
	 * Verifie qu'il n'y a pas de sous tours dans la solution proposée
	 * 
	 * Renvoie vrai si la contrainte est satisfaite, faux sinon
	 */
	boolean contrainteSousToursSatisfaite()
	{
		int villeActuelle = 0;
		int compte = 0;
		
		do
		{
			for (int i = 0; i < getTaille(); i++) {
				if (matriceSolution[villeActuelle][i])
				{
					villeActuelle = i; 
					break;
				}
			}
			compte++;
		}
		while(villeActuelle != 0);
		
		return compte == getTaille();
	}
	
	/*
	 * Verfie si la solution satisfait toutes les contraitnes du PVC
	 * 
	 * retourne vrai si la solution est valide, faux sionon
	 */
	boolean solutionValide()
	{
		return valeursVariablesValides() && contrainte1satisfaite() && contrainteSousToursSatisfaite();
	}
	
	public void setTrue(int x, int y){
		matriceSolution[x][y] = true;
	}
}
