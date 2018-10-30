import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class SolutionPVCTest {

	@Test
	void testRemplirMatriceSolution() {
		int resultat[] = {0,1,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0,1,0,0,0,0};
		boolean expected[][] = new boolean[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				expected[i][j] = false;
			}
		}
		expected[1][0] = true;
		expected[0][1] = true;
		expected[0][2] = true;
		expected[0][3] = true;
		expected[0][4] = true;

		
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
		boolean matSol[][] = new boolean[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				matSol[i][j] = false;
			}
		}
		matSol[0][1] = true;
		matSol[1][2] = true;
		matSol[2][0] = true;
		matSol[3][4] = true;
		matSol[4][3] = true;
		
		
		boolean matSol2[][] = new boolean[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				matSol2[i][j] = false;
			}
		}
		matSol2[0][1] = true;
		matSol2[1][2] = true;
		matSol2[2][3] = true;
		matSol2[3][4] = true;
		matSol2[4][0] = true;
		
		SolutionPVC solPVC = new SolutionPVC(matSol);
		SolutionPVC solPVC2 = new SolutionPVC(matSol2);
		
		assertEquals(false, solPVC.contrainteSousToursSatisfaite());
		assertEquals(true, solPVC2.contrainteSousToursSatisfaite());
		
	}
	
	@Test
	void testRemplirCycleSolution()
	{
		boolean matSol[][] = new boolean[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				matSol[i][j] = false;
			}
		}
		matSol[0][1] = true;
		matSol[1][2] = true;
		matSol[2][3] = true;
		matSol[3][0] = true;
		
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
		boolean matSol[][] = new boolean[6][6];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				matSol[i][j] = false;
			}
		}
		matSol[0][1] = true;
		matSol[1][2] = true;
		matSol[2][3] = true;
		matSol[3][4] = true;
		matSol[4][5] = true;
		matSol[5][0] = true;
		
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
		
		boolean expectedMatrice[][] = {
				
				{false, true, false, false},
				{false, false, true, false},
				{false, false, false, true},
				{true, false, false, false}
				
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
