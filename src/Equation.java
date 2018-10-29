
public class Equation {

	private int nbVariables;
	private float coefficients[];
	private char signe;
	private float secondMembre;
	
	public Equation(int nbV, float coef[], char signe, float secondMembre) {
		nbVariables = nbV;
		coefficients = coef;
		this.signe = signe;
		this.secondMembre = secondMembre;
	}
	
	

	public int getNbVariables() {
		return nbVariables;
	}

	public float[] getCoefficients() {
		return coefficients;
	}

	public char getSigne() {
		return signe;
	}

	public float getSecondMembre() {
		return secondMembre;
	}

	
}
