
public class RecuitPVCStocha extends RecuitPVC {

	public RecuitPVCStocha(PLPVC prob) {
		super(prob);
	}
	
	@Override
	protected SolutionPVC selectionMouvement(Solution solInitiale){
	
		SolutionPVCStocha sol;
		
		do {
			sol = (SolutionPVCStocha) super.selectionMouvement(solInitiale);
		}while(sol.solutionValide());
		
		return sol;
	}
}
