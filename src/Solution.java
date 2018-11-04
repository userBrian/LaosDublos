
public class Solution {
	
	private float resultat[];
	protected int taille; //nb de variables de la solution 
	protected PL probleme;

	public Solution(){
		
	}
	
	public Solution(int taille) {
		this.taille = taille;
//		System.out.println("Constructeur solution : taille" + this.taille);
		resultat = new float[this.taille];
	}
	
	
	 public double getCout(PL pb)
	    {
	    	double cout = 0;
	    	
	    	for (int i = 0; i < pb.getDimension(); i++) {
				cout += pb.getFoncObj()[i]*getResultat()[i];
			}
	    	
	    	return cout;
	    }
	
	public float[] getResultat(){
		return resultat;
	}

	public void setResultat(float[] resultat) {
		this.resultat = resultat;
	}

	public int getTaille() {
		return taille;
	}

	public String toCSV() {
		return resultat.toString();
	}
	
	
	
}
