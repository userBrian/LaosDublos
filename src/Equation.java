
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
	
	@Override
	public String toString() {
		String str = "";
		for(int i = 0; i < coefficients.length; i++)
		{
			if(coefficients[i] != 0)
			{
				if(str != "")
				{
					str += "+" + coefficients[i] + "x" + i + " ";
				}
				else
				{
					str += coefficients[i] + "x" + i + " ";
				}
			}
		}
		str += " " + signe + " " + secondMembre;
		return str;
	}

	
}
