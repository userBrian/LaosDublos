import java.util.ArrayList;

public class PL {

	private boolean maximiser;
	private int nbVariables;
	protected double[] foncObj;
	private ArrayList<Equation> contraintes;
	private Solution solution;
	protected int dimension;
	
	public PL(){
		
	}

	/**
	 * Retourne la dimension du probleme
	 * @return Dimension du probleme
	 */
	public int getDimension(){
		return dimension;
	}
	
	/**
	 * Set la dimension du probleme
	 * @param dim Dimension du probleme
	 */
	public void setDimension(int dim){
		dimension = dim;
	}
	
	/*
	 * Retourne le nombre de variables du probleme
	 */
	public int getNbVariables(){
		return nbVariables;
	}

	/**
	 * Retourne la nature du probleme (vrai pour maximisation, faux pour minimisation)
	 * @return Nature du probleme
	 */
	public boolean isMaximiser() {
		return maximiser;
	}

	/**
	 * Retourne la fonction objectif du probleme
	 * @return Fonction objectif du probleme
	 */
	public double[] getFoncObj() {
		return foncObj;
	}

	/**
	 * Retourne les contraintes du probleme
	 * @return Contraintes du probleme
	 */
	public ArrayList<Equation> getContraintes() {
		return contraintes;
	}

	/**
	 * Retourne la solution du probleme
	 * @return SOlution du probleme
	 */
	public Solution getSolution() {
		return solution;
	}
	
	/**
	 * Set la solution du probleme
	 */
	public void setSolution(Solution sol){
		solution = sol;
	}
	
	@Override
	public String toString(){
		String str = "Fonction Objectif : \n";

		str += "\t Un bail";
		/*for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++)
				str += foncObj[i][j] + " ";
			str += '\n';
		}*/
		str += "\nContraintes : \n";
		/*for(int i = 0; i < contraintes.size(); i++)
		{
			str += "\t"+contraintes.get(i).toString();
		}*/
		return str;
	}

}
