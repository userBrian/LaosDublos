import java.util.ArrayList;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class CPLEX {

	private IloCplex modele;
	private IloNumVar[][] x;
	private int dim;
	
	public CPLEX()
	{
		
	}
	
	
	public void initialiserModele(PLPVC pb){
		int dim = pb.getDimension();
		double[][] couts = pb.getFoncObj();
		
		declarerVariables(dim);
		declarerFoncObj(dim, couts);
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
		boolean sol[][] = new boolean[dim][dim];
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
					sol[i][j] = true;
				}
				else
				{
					sol[i][j] = false;
				}
			}
		}

		} catch(IloException e){
			e.printStackTrace();
		}
				
		return new SolutionPVC(sol);	
	}
	
	public void declarerVariables(int dim){
		try{
			x = new IloNumVar[dim][];
			for(int i = 0; i < dim; i++)
				x[i] = modele.boolVarArray(dim);
			IloNumVar[] u = modele.numVarArray(dim, 0, Double.MAX_VALUE);
		} catch(IloException e){
			e.printStackTrace();
		};
	}
	
	public void declarerFoncObj(int dim, double[][] couts){
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
		dim = pb.getDimension();
		double[][] couts = pb.getFoncObj();
		
		try{
			modele = new IloCplex();
			modele.setParam(IloCplex.IntParam.Threads, 8);
			
			// Variables
			x = new IloNumVar[dim][];
			for(int i = 0; i < dim; i++)
				x[i] = modele.boolVarArray(dim);
			IloNumVar[] u = modele.numVarArray(dim, 0, Double.MAX_VALUE);
			
			// Objectif
			IloLinearNumExpr obj = modele.linearNumExpr();
			for(int i = 0; i < dim; i++){
				for(int j = 0; j < dim; j++){
					if(i != j)
						obj.addTerm(couts[i][j], x[i][j]);
				}
			}
			modele.addMinimize(obj);
			
			// Contraintes
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
		}
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
