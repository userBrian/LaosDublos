import java.util.ArrayList;

public class PL {

	private boolean maximiser;
	private int nbVariables;
	protected int[][] foncObj;
	private ArrayList<Equation> contraintes;
	private Solution solution;
	protected int dimension;
	
	public PL(){
		
	}

	public int getDimension(){
		return dimension;
	}
	
	public int getNbVariables(){
		return nbVariables;
	}

	public boolean isMaximiser() {
		return maximiser;
	}

	public int[][] getFoncObj() {
		return foncObj;
	}

	public ArrayList<Equation> getContraintes() {
		return contraintes;
	}

	public Solution getSolution() {
		return solution;
	}
	
	public void setSolution(Solution sol){
		solution = sol;
	}
	
	@Override
	public String toString(){
		String str = "Bonjour\n";
		
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++)
				str += foncObj[i][j] + " ";
			str += '\n';
		}
			
		
		return str;
	}
}
