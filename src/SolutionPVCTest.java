import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class SolutionPVCTest {

	@Test
	void testRemplirMatriceSolution() {
		float[] resultat = {0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0};
		int expected[][] = new int[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				expected[i][j] = 0;
			}
		}
		expected[1][0] = 1;
		expected[0][1] = 1;
		expected[0][2] = 1;
		expected[0][3] = 1;
		expected[0][4] = 1;

		
		Solution sol = new Solution(5);
		sol.setResultat(resultat);
		
		SolutionPVC solPVC = new SolutionPVC(sol);
		assertEquals(5, solPVC.getMatriceSolution().length);
		System.out.println("Taille de matriceSolution : " + solPVC.getMatriceSolution().length);
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				assertEquals(expected[i][j], solPVC.getMatriceSolution()[i][j]);
			}
		}
	}
	
	
	@Test
	void testContrainteSousToursSatisfaite()
	{
		int[][] matSol = new int[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				matSol[i][j] = 0;
			}
		}
		matSol[0][1] = 1;
		matSol[1][2] = 1;
		matSol[2][0] = 1;
		matSol[3][4] = 1;
		matSol[4][3] = 1;
		
		
		int matSol2[][] = new int[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				matSol2[i][j] = 0;
			}
		}
		matSol2[0][1] = 1;
		matSol2[1][2] = 1;
		matSol2[2][3] = 1;
		matSol2[3][4] = 1;
		matSol2[4][0] = 1;
		
		SolutionPVC solPVC = new SolutionPVC(matSol);
		SolutionPVC solPVC2 = new SolutionPVC(matSol2);
		
		assertEquals(0, solPVC.contrainteSousToursSatisfaite());
		assertEquals(1, solPVC2.contrainteSousToursSatisfaite());
		
	}
	
	@Test
	void testRemplirCycleSolution()
	{
		int matSol[][] = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				matSol[i][j] = 0;
			}
		}
		matSol[0][1] = 1;
		matSol[1][2] = 1;
		matSol[2][3] = 1;
		matSol[3][0] = 1;
		
		ArrayList<Integer> expected = new ArrayList<Integer>();
		expected.add(0);
		expected.add(1);
		expected.add(2);
		expected.add(3);
		expected.add(0);
		
		SolutionPVC solPVC = new SolutionPVC(matSol);
		
		solPVC.remplirCycleSolution();
		solPVC.printCycleSolution();
		
		assertEquals(expected, solPVC.getCycleSolution());
	}
	
	
	/*
	 * Vu que c'est du random je sais pas trop comment assert
	 */
	@Test
	void testGenererInversion2Opt()
	{
		int matSol[][] = new int[6][6];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				matSol[i][j] = 0;
			}
		}
		matSol[0][1] = 1;
		matSol[1][2] = 1;
		matSol[2][3] = 1;
		matSol[3][4] = 1;
		matSol[4][5] = 1;
		matSol[5][0] = 1;
		
		SolutionPVC solPVC = new SolutionPVC(matSol);

		System.out.println("Cycle de depart");
		solPVC.printCycleSolution();
		
		for (int i = 0; i < 10; i++)
		{
			System.out.println("Inversion " + i + " : " + solPVC.genererInversion2Opt().toString());
		}
	}
	
	@Test
	void testSetAll()
	{
		ArrayList<Integer> cycle = new ArrayList<Integer>();
		cycle.add(0);
		cycle.add(1);
		cycle.add(2);
		cycle.add(3);
		cycle.add(0);
		
		SolutionPVC solPVC = new SolutionPVC(cycle);
		
		assertEquals(cycle, solPVC.getCycleSolution());
		
		int expectedMatrice[][] = {
				
				{0, 1, 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1},
				{1, 0, 0, 0}
				
		};
		
		for (int i = 0; i < expectedMatrice.length; i++) {
			for (int j = 0; j < expectedMatrice.length; j++) {
				assertEquals(expectedMatrice[i][j], solPVC.getMatriceSolution()[i][j]);
			}
		}
		
		
		int expectedResultat[] = {0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0};
		
		for (int i = 0; i < expectedResultat.length; i++) {
			assertEquals(expectedResultat[i], solPVC.getResultat()[i]);

		}
	}
}
