
public class Solution {
	
	private int resultat[];
	private int cout;
	private int taille; //nb de variables de la solution 

	public Solution(int taille) {
		this.taille = taille;
		resultat = new int[this.taille*this.taille];
		//pour un probleme de taille n on a n*n arcs à décider
	}

	public int calculeCout(){
		// TODO pour ça il faut connaitre le probleme associé 
		return 0;
	}
	
	public int getCout(){
		return cout;
	}
	
	public int[] getResultat(){
		return resultat;
	}

	public void setResultat(int[] resultat) {
		this.resultat = resultat;
	}

	public int getTaille() {
		return taille;
	}
	
	
}
