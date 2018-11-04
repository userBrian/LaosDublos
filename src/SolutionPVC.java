import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
	
	/*
	 * Representation de la solution sous forme d'une liste d'identifiant 
	 * ex 1-2-3-4-1
	 * 
	 * Plus facile comprendre lors de l'affichage de la solution et plus facile a manipuler pour le choix des voisins
	 */
	private ArrayList<Integer> cycleSolution;
	
	public SolutionPVC(){
		
	}
	
	public SolutionPVC(int taille) {
		super(taille);
		matriceSolution = new boolean[taille][taille];
		for(int i = 0; i < taille; i++){
			for(int j = 0; j < taille; j++)
				matriceSolution[i][j] = false;
		}
	}
	
	public SolutionPVC(Solution sol)
	{
		super(sol.getTaille());
		remplirMatriceSolution(sol.getResultat());
		remplirCycleSolution();
	}
	
	public SolutionPVC(ArrayList<Integer> cycle)
	{
		super(cycle.size()-1);
		String str = "";
		for(Integer i : cycle)
		{
			str += i + " ";
		}
		System.out.println(str);
		//setAll(cycle);
		this.cycleSolution = cycle;
	}

	public SolutionPVC(boolean matriceSolution[][])
	{
		super(matriceSolution.length);
		this.matriceSolution = matriceSolution;
		remplirCycleSolution();
	}
	
	public void remplirMatriceSolution(int resultat[])
	{
		int tailleMatrice = getTaille();
		
		for (int i = 0; i < resultat.length; i++)
		{
			matriceSolution[i%tailleMatrice][i/tailleMatrice] = resultat[i] == 1 ? true : false;
		}
	}
	
	public void remplirCycleSolution()
	{
		cycleSolution = new ArrayList<Integer>();
		int villeActuelle = 0;
		cycleSolution.add(0);
		
		do
		{
			for (int i = 0; i < getTaille(); i++) {
				if (matriceSolution[villeActuelle][i])
				{
					villeActuelle = i; 
					break;
				}
			}
			cycleSolution.add(villeActuelle);
		}
		while(villeActuelle != 0);
	}
	
	public void printCycleSolution()
	{
		String str = "";
		for(int ville : cycleSolution)
		{
			str += ville + "\t";
		}
		System.out.println(str);
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

	public ArrayList<Integer> getCycleSolution() {
		return cycleSolution;
	}
	
	public ArrayList<Integer> genererInversion2Opt()
	{
		ArrayList<Integer> inversion = new ArrayList<Integer>();
		Random rng = new Random();

		int extremite1 = rng.nextInt(cycleSolution.size() - 3) + 1;
		int extremite2 = rng.nextInt(cycleSolution.size() - extremite1 - 2) + extremite1 + 1; 
		//TODO etudier si c'est vraiment la meilleure sol pour un random
//		System.out.println("bornes : " + extremite1 + extremite2);
		
		for (int i = 0; i < extremite1; i++) {
			inversion.add(cycleSolution.get(i));
		}
		
		for (int i = extremite2; i >= extremite1; i--) {
			inversion.add(cycleSolution.get(i));
		}
		
		for (int i = extremite2 + 1; i < cycleSolution.size(); i++) {
			inversion.add(cycleSolution.get(i));
		}
		
		return inversion;
	}
	
	public void setResultat(boolean matSol[][])
	{
		for (int j = 0; j < matSol.length; j++) {
			for (int i = 0; i < matSol.length; i++) {
				getResultat()[i + j*this.getTaille()] = matSol[i][j] ? 1 : 0;
				//TODO est-ce que ça marche ça ?
			}
		}
	}
	
	public void setAll(ArrayList<Integer> cycle)
	{
		cycleSolution = cycle;
		System.out.println(taille);
		matriceSolution = new boolean[taille][taille];
		for(int i = 0; i < taille; i++){
			for(int j = 0; j < taille; j++)
				matriceSolution[i][j] = false;
		}
		
		System.out.println("cycle size " + cycle.size());
		
		for (int i = 0; i < cycle.size()-1; i++) {
			setTrue(cycle.get(i), cycle.get(i+1));
//			System.out.println("i " + i + "size - 2 "+ (cycle.size()-2)+ "set " + cycle.get(i) + "-" + cycle.get(i+1) + " to true");
		}
		
		setResultat(matriceSolution);
	}

	public ArrayList<ArrayList<Integer>> getSousTours() 
	{
		ArrayList<ArrayList<Integer>> sousTours = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> villesRestantes = new ArrayList<Integer>();
		int nbVilles = 0;
		
		for(int i = 0; i < taille; i++)
		{
			villesRestantes.add(i);
		}
		while(villesRestantes.size() > 0)
		{
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			int premiereVille = villesRestantes.get(0);
			int n = villesRestantes.get(0);
			do
			{
				for(int i = 0; i < taille; i++)
				{
					if(matriceSolution[n][i])
					{
						tmp.add(n);
						villesRestantes.remove((Integer)i);
						n = i;
						nbVilles++;
						break;
					}
				}
			}
			while(n != premiereVille);
			sousTours.add(tmp);
		}
		return sousTours;
	}
	
	public String toCSV() {
		String str = "Ordre Villes ; Id Ville \n";
		for(int i = 0; i < cycleSolution.size(); i++)
		{
			str += i + " ; " + cycleSolution.get(i) + "\n";
		}
		return str;
	}
	
}
