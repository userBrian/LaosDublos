
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
	
	/*
	 * A appeler a l'initialisation, ajoute au problème les contraintes spécifiques au PVC
	 */
	public void ajouterContraintesPVC()
	{
		int nbVariable = this.getNbVariables();
		char signe = '=';
		int scMmb = 1;
		float coef[] = new float[nbVariable];
		
		//un seul arc entrant par ville
		for (int i = 0; i < getDimension(); i++) {
			
			for (int j = 0; j < coef.length; j++) {
				coef[j] = 0;
			}
			
			for (int j = 0; j < getDimension(); j++) {
				coef[i + j*getDimension()] = 1;
			}
			
			getContraintes().add(new Equation(nbVariable, coef, signe, scMmb));
		}
		
		//un seul arc sortant par ville
			for (int i = 0; i < getDimension(); i++) {
				
				for (int j = 0; j < coef.length; j++) {
					coef[j] = 0;
				}
				
				for (int j = 0; j < getDimension(); j++) {
					coef[i*getDimension() + j] = 1;
				}
				
				getContraintes().add(new Equation(nbVariable, coef, signe, scMmb));
			}
		
		//contrainte de sous tours
		//TODO
			
		//valeurs des variables, sera defini autrement dans IloCPLEX
			
		
	}
	

}
