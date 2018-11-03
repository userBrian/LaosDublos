
public class Solution {
	
	private float resultat[];
	protected int taille; //nb de variables de la solution 
	protected PL probleme;

	public Solution(int taille) {
		this.taille = taille;
		resultat = new float[this.taille];
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
