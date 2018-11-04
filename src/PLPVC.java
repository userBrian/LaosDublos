
public class PLPVC extends PL {
	
	/**
	 * 
	 * @param cout
	 */
	public PLPVC(double[][] cout){
		dimension = cout.length;
		foncObj = new double[dimension][dimension];
		setFoncObj(cout);
		//ajouterContraintesPVC();
	}
	
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
	{// TODO : Corriger cette fonction
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
		//pas todo en fait ça se fait plus tard, pendant l'oracle
			
		//valeurs des variables, sera defini autrement dans IloCPLEX
			
		
	}
	
}
