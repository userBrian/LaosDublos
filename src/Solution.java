
public class Solution {
	
	private float resultat[];
	protected int taille; //nb de variables de la solution 
	protected PL probleme;

	public Solution(int taille, PL pb) {
		this.taille = taille;
		resultat = new float[this.taille];
		probleme = pb;
	}

	public double getCout(){
		// TODO pour ça il faut connaitre le probleme associé 
		double cout = 0;
		
		for (int i = 0; i < resultat.length; i++) {
			cout += resultat[i]*probleme.getFoncObj()[i];
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
	
	
	
}
