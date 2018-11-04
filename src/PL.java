import java.util.ArrayList;

public class PL {

	private boolean maximiser;
	protected double[] foncObj;
	private ArrayList<Equation> contraintes;
	private Solution solution;
	protected int dimension;
	
	public PL(){
		
	}

	public int getDimension(){
		return dimension;
	}
	
	public void setDimension(int dim){
		dimension = dim;
	}

	public boolean isMaximiser() {
		return maximiser;
	}

	public double[] getFoncObj() {
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
}
