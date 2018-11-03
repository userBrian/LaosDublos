import java.util.Arrays;

/**
 * 
 */

/**
 * @author Brian
 *
 */
public class RecuitPVC extends Recuit {

	/**
	 * 
	 */
	public RecuitPVC(PLPVC prob) {
		super(prob);
	}
	
	@Override
	protected void initialiserTemp(){
		
		System.out.println("Initialisation temperature ...");
		setSolutionActuelle(solutionInitiale());
		
		boolean ok = false;
		int nbAcceptations = 0;
		
		while(!ok)
		{
			for (int i = 0; i < 100; i++) {
				if (accepterSolution(SolutionPVC.genererSolutionAleatoire(solutionActuelle.getTaille())))
				{
					nbAcceptations++;
				}
			}
			System.out.println(nbAcceptations + " acceptees");
			if ( nbAcceptations  > 80)
			{
				ok = true;
			}
			else {
				System.out.println("Nombre d'acceptations trop faible, température : " + temperature + " -> " + (temperature*2));
				temperature *= 2;
			}
		}
		
		System.out.println("Temperature initialisée à " + temperature);
		
	}
	


	
	@Override
	protected SolutionPVC selectionMouvement(Solution solInitiale){
		
		SolutionPVC solInit = (SolutionPVC) solInitiale; //on peut essayer de gerer l'exceion ici
		SolutionPVC voisin;
		solInit.remplirCycleSolution();
		do{
			// TODO : Algo 3-opt
			
			//Ici c'est le 2-opt alias l'inversion du sous-tour
			voisin = new SolutionPVC(solInit.genererInversion2Opt());
			
		}while(!voisin.solutionValide());
		return voisin;
	}
	
	/**
	 * Retourne une solution initiale pour le PVC. L'algorithme consiste a
	 * choisir a chque fois l'arc le plus court. Il interdit les sous-tours.
	 */
	@Override
	protected SolutionPVC solutionInitiale(){
		SolutionPVC solInit = new SolutionPVC(probleme.getDimension());
		double[][] couts = probleme.getFoncObj();
		double min = Double.MAX_VALUE;
		int index = 0, nbIte = 0, i = 0;
		while(nbIte < probleme.getDimension()){
			System.out.print(i +"->");
			for(int j = 0; j < probleme.getDimension(); j++){
				if(couts[i][j] != 0 && couts[i][j] < min){
					min = couts[i][j];
					index = j;
				}
			}
			for(int j = 0; j < probleme.getDimension(); j++)
				couts[j][i] = Double.MAX_VALUE;
			min = Double.MAX_VALUE;
			System.out.println(index);
			solInit.setTrue(nbIte, index);
			i = index;
			index = 0;
			nbIte++;
			
		}
		return solInit;
	}

}
