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

	/* (non-Javadoc)
	 * @see Recuit#mainLoop()
	 */
	@Override
	protected void mainLoop() {
		int ite = 0;
		SolutionPVC solActu = solutionInitiale();
		SolutionPVC meilleureSol = solActu;
		int deltaCout;
		
		initialiserTemp();
		
		while(temperature > 0){	// TODO : D�finir un seuil
			while(ite < probleme.getDimension()){
				SolutionPVC x = selectionMouvement(solActu);
				
				deltaCout = x.getCout() - solActu.getCout();
				if(deltaCout < 0){
					solActu = x;
					if(solActu.getCout() < meilleureSol.getCout())
						meilleureSol = solActu;
				}
				else{
					if(Math.random() < Math.exp(deltaCout/temperature))
						solActu = x;
				}
				
				ite++;
			}
			
			temperature *= 0.9;
		}
		
		probleme.setSolution(meilleureSol);
	}

	
	@Override
	protected SolutionPVC selectionMouvement(SolutionPVC solInit){
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
