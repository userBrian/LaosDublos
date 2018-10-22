import java.util.ArrayList;

public class PL {

	private boolean maximiser;
	private int nbVariables;
	private int[] foncObj;
	private ArrayList<Equation> contraintes;
	private ArrayList<Solution> solutions;
	
	public PL() {
		// TODO Auto-generated constructor stub
	}

	public int getNbVariables(){
		return nbVariables;
	}

	public boolean isMaximiser() {
		return maximiser;
	}

	public int[] getFoncObj() {
		return foncObj;
	}

	public ArrayList<Equation> getContraintes() {
		return contraintes;
	}

	public ArrayList<Solution> getSolutions() {
		return solutions;
	}
	
	
}
