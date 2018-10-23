import java.util.ArrayList;

public class PL {

	private boolean maximiser;
	private int nbVariables;
	private int[][] foncObj;
	private ArrayList<Equation> contraintes;
	private ArrayList<Solution> solutions;
	private int dimension;
	
	public PL(int dimension){
		foncObj = new int[dimension][dimension];
		this.dimension = dimension;
	}

	private int calculDistance(int x1, int y1, int x2, int y2){
		return (int)Math.sqrt(Math.abs(x1-x2)*Math.abs(x1-x2) + Math.abs(y1-y2)*Math.abs(y1-y2));
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
	
	public void setFoncObj(int[][] pos){
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++)
				foncObj[i][j] = calculDistance(pos[i][0], pos[i][1], pos[j][0], pos[j][1]);
		}
	}

	public ArrayList<Equation> getContraintes() {
		return contraintes;
	}

	public ArrayList<Solution> getSolutions() {
		return solutions;
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
