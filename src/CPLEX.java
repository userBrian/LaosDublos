import java.util.ArrayList;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class CPLEX {
	
	/**
	 * modele CPLEX qui définit le problème Linéaire à résoudre
	 */
	private IloCplex modele;
	
	/**
	 * Variables du problème Linéaire
	 */
	private IloNumVar[][] x;
	
	/**
	 * Dimension du problème linéaire (nombre de variables)
	 */
	private int dim;
	
	public void initialiserModele(PLPVC pb){
		dim = pb.getNbVilles();
		double[][] couts = pb.getMatObj();
		try {
			modele = new IloCplex();
			modele.setParam(IloCplex.IntParam.Threads, 8);
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		declarerVariables();
		declarerFoncObj(couts);
		ajouterContraintes();
	}
	
	public void ajouterContraintes()
	{
		try{
			for(int j = 0; j < dim; j++){
				IloLinearNumExpr expr = modele.linearNumExpr();
				for(int i = 0; i < dim; i++){
					if(i != j)
						expr.addTerm(1.0, x[i][j]);
				}
				modele.addEq(expr, 1.0);
			}
			for(int i = 0; i < dim; i++){
				IloLinearNumExpr expr = modele.linearNumExpr();
				for(int j = 0; j < dim; j++){
					if(i != j)
						expr.addTerm(1.0, x[i][j]);
				}
				modele.addEq(expr, 1.0);
			}
		} catch(IloException e){
			e.printStackTrace();
		};
	}
	
	public SolutionPVC resoudre(){
		int sol[][] = new int[dim][dim];
		try {
			
		// Resolution
		modele.solve();

		// Recuperation de la solution
		for(int i = 0; i < dim; i++)
		{
			for(int j = 0; j < dim; j++)
			{
				if(i != j && modele.getValue(x[i][j]) == 1)
				{
					sol[i][j] = 1;
				}
				else
				{
					sol[i][j] = 0;
				}
			}
		}

		} catch(IloException e){
			e.printStackTrace();
		}
				
		return new SolutionPVC(sol);	
	}
	
	public void declarerVariables(){
		try{
			x = new IloNumVar[dim][];
			for(int i = 0; i < dim; i++)
				x[i] = modele.boolVarArray(dim);
			IloNumVar[] u = modele.numVarArray(dim, 0, Double.MAX_VALUE);
		} catch(IloException e){
			e.printStackTrace();
		};
	}
	
	public void declarerFoncObj(double[][] couts){
		try{
			IloLinearNumExpr obj = modele.linearNumExpr();
			for(int i = 0; i < dim; i++){
				for(int j = 0; j < dim; j++){
					if(i != j);
						obj.addTerm(couts[i][j], x[i][j]);
				}
			}
			modele.addMinimize(obj);
		} catch(IloException e){
			e.printStackTrace();
		};
	}
	
	/**
	 * Resout un probleme du voyageur de commerce avec l'aide de cplex.
	 * @param pb Le probleme a resoudre
	 * @return La solution du probleme
	 */
	public SolutionPVC solvePVC(PLPVC pb){
		
		initialiserModele(pb);
		return resoudre();
	}
	
	public void ajoutContrainteSousTours(ArrayList<Integer> cycle)
	{
		try {
			
		int secondMembre = cycle.size() - 1;
		IloLinearNumExpr expr = modele.linearNumExpr();
		
			for(int i = 0; i < cycle.size(); i++)
			{
				 for(int j=0; j < cycle.size(); j++)
				 {
					 	if(i!=j)
					 	{
							expr.addTerm(1.0,x[cycle.get(i)][cycle.get(j)]);
					 	}	
				 }
			}
			modele.addLe(expr, secondMembre);
			
		} catch (IloException e) {
			e.printStackTrace();
		}
	}
}
