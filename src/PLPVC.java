
public class PLPVC extends PL {
	
	protected int nbVilles;
	protected double[][] matObj;
	/**
	 * 
	 * @param cout
	 */
	public PLPVC(double[][] cout){
		nbVilles = cout.length;
		dimension = nbVilles*nbVilles;
		matObj = new double[nbVilles][nbVilles];
		setmatObj(cout);
	}
	
	public void setmatObj(double[][] cout){
		matObj = cout;
	}
	
	/*
	 * A appeler a l'initialisation, ajoute au problème les contraintes spécifiques au PVC
	 */
	public void ajouterContraintesPVC()
	{
		int nbVariable = this.getDimension();
		System.out.println("nombre variable" + nbVariable);
		char signe = '=';
		int scMmb = 1;
		float coef[] = new float[nbVariable];
		
		//un seul arc entrant par ville
		for (int i = 0; i < nbVilles; i++) {
			
			for (int j = 0; j < coef.length; j++) {
				coef[j] = 0;
			}
			
			for (int j = 0; j < nbVilles; j++) {
				coef[i + j*nbVilles] = 1;
			}
			
			getContraintes().add(new Equation(nbVariable, coef, signe, scMmb));
		}
		
		//un seul arc sortant par ville
			for (int i = 0; i < nbVilles; i++) {
				
				for (int j = 0; j < coef.length; j++) {
					coef[j] = 0;
				}
				
				for (int j = 0; j < nbVilles; j++) {
					coef[i*nbVilles + j] = 1;
				}
				
				getContraintes().add(new Equation(nbVariable, coef, signe, scMmb));
			}
	}

	public int getNbVilles() {
		return nbVilles;
	}

	public double[][] getMatObj() {
		return matObj;
	}
	
	
	
}
