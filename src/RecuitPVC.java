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
		Solution solActu = solutionInitiale();
		Solution meilleureSol = solActu;
		int deltaCout;
		
		initialiserTemp();
		
		while(temperature > 0){	// TODO : Définir un seuil
			while(ite < probleme.getDimension()){
				Solution x = selectionMouvement();
				
				deltaCout = x.getCout() - solActu.getCout();
				if(deltaCout < 0){
					solActu = x;
					if(solActu.getCout() < meilleureSol.getCout())
						meilleureSol = solActu;
				}
				else{
					if(Math.random() < Math.exp(deltaCout/temperature)){
						solActu = x;
					}
				}
				
				ite++;
			}
			
			temperature *= 0.9;
		}
		
		probleme.setSolution(meilleureSol);
	}

	@Override
	protected Solution selectionMouvement(){
		// TODO
		return new Solution(0);
	}
	
	@Override
	protected Solution solutionInitiale(){
		// TODO
		return new Solution(0);
	}

}
