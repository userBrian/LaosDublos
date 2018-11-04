import java.util.ArrayList;
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
				if (accepterSolution(SolutionPVC.genererSolutionAleatoire(((SolutionPVC) solutionActuelle).getNbVilles())))
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
				System.out.println("Nombre d'acceptations trop faible, temperature : " + temperature + " -> " + (temperature*2));
				temperature *= 2;
				nbAcceptations = 0;
			}
		}
		
		System.out.println("Temperature initialisee a  " + temperature);
		
	}
	
	
	protected boolean accepterSolution(Solution solConsideree)
	{
		SolutionPVC sol = (SolutionPVC) solConsideree;
				
		deltaCout = sol.getCout(probleme) - ((SolutionPVC) solutionActuelle).getCout(probleme);
		
		if(deltaCout < 0) return true;
		else return (Math.random() < Math.exp(-deltaCout/temperature));
	}

	
	/**
	 * Utilise l'algorithme 2-opt
	 */
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

		
		PLPVC pb = (PLPVC) probleme;
		
		ArrayList<Integer> cycle = new ArrayList<Integer>();
		for (int i = 0; i < pb.getNbVilles(); i++) {
			cycle.add(i);
		}
		cycle.add(cycle.get(0));
		
		return new SolutionPVC(cycle);
	}

	@Override
	protected void initialiserSolutionActuelle() {
		
		solutionActuelle = solutionInitiale();
		setMeilleurCout(((SolutionPVC) solutionActuelle).getCout(probleme));
		coutActuel = getMeilleurCout();
		setMeilleureSolution(solutionActuelle);
		
	}
}
