import java.util.ArrayList;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

public class CPLEX {

	private IloCplex modele;
	
	/**
	 * 
	 */
	public CPLEX(){
		
	}
	
	/*public CPLEX(PLPVC pb) {
		try{
			modele = new IloCplex();
			modele.setParam(IloCplex.IntParam.Threads, 8);
		
			initialiserModele(pb);
		} catch(IloException e){
			e.printStackTrace();
		};
	}
	
	public void initialiserModele(PLPVC pb){
		int dim = pb.getDimension();
		double[][] couts = pb.getFoncObj();
		
		declarerVariables(dim);
		declarerFoncObj(dim, couts);
	}
	
	public void ajouterContraintes(){
		
	}
	
	public SolutionPVC resoudre(){
		
	}
	
	public void declarerVariables(int dim){
		try{
			IloNumVar[][] x = new IloNumVar[dim][];
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
					if(i != j)
						obj.addTerm(couts[i][j], x[i][j]);
				}
			}
			modele.addMinimize(obj);
		} catch(IloException e){
			e.printStackTrace();
		};
	}*/
	
	/**
	 * Resout un probleme du voyageur de commerce avec l'aide de cplex.
	 * @param pb Le probleme a resoudre
	 * @return La solution du probleme
	 */
	public SolutionPVC solveBrian(PLPVC pb){
		int dim = pb.getDimension();
		double[][] couts = pb.getFoncObj();
		ArrayList<Integer> sol = new ArrayList<Integer>();
		
		try{
			modele = new IloCplex();
			modele.setParam(IloCplex.IntParam.Threads, 8);
			
			// Variables
			IloNumVar[][] x = new IloNumVar[dim][];
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
			for(int i = 1; i < dim; i++){
				for(int j = 1; j < dim; j++){
					if(i != j){
						IloLinearNumExpr expr = modele.linearNumExpr();
						expr.addTerm(1.0, u[i]);
						expr.addTerm(-1.0, u[j]);
						expr.addTerm(dim-1, x[i][j]);
						modele.addLe(expr, dim-2);
					}
				}
			}
			
			// Resolution
			modele.solve();

			// Recuperation de la solution
			for(int i = 0; i < dim; i++){
				for(int j = 0; j < dim; j++){
					if(i != j && modele.getValue(x[i][j]) == 1)
						sol.add(j);
				}
			}
			
			modele.end();
		} catch(IloException e){
			e.printStackTrace();
		}
		
		return new SolutionPVC(sol);
	}
	
}
