
public class PLPVC extends PL {

	/*public PLPVC(int[][] pos) {
		dimension = pos.length;
		foncObj = new int[dimension][dimension];
		setFoncObj(pos);
	}*/
	
	/*public PLPVC(int dim){
		dimension = dim;
		foncObj = new double[dim][dim];
	}*/
	
	public PLPVC(double[][] cout){
		dimension = cout.length;
		foncObj = new double[dimension][dimension];
		setFoncObj(cout);
	}

	private int calculDistance(int x1, int y1, int x2, int y2){
		return (int)Math.sqrt(Math.abs(x1-x2)*Math.abs(x1-x2) + Math.abs(y1-y2)*Math.abs(y1-y2));
	}
	/*
	public void setFoncObj(int[][] pos){
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++)
				foncObj[i][j] = calculDistance(pos[i][0], pos[i][1], pos[j][0], pos[j][1]);
		}
	}*/
	
	public void setFoncObj(double[][] cout){
		for(int i = 0; i < dimension; i++){
			for(int j = 0; j < dimension; j++)
				foncObj[i][j] = cout[i][j];
		}
	}
}
