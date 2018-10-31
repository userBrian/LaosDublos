import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileSystemView;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;



public class Controleur extends JFrame {
	
	/////////////DONNEES/////////////
	private PL probleme;
	private MethIte iterative;
	private Recuit recuit;
	
	/////////////AFFFICHAGE/////////////
	
	//General JPanel
	private JPanel pan = new JPanel();
		
	//Panel contr�les
	private JPanel panControls = new JPanel();
		
	//Boutons importer/Resoudre/Exporter
	private JButton boutonImporter = new JButton("Importer");
	private JButton boutonResoudre = new JButton("R�soudre");
	private JButton boutonExporter = new JButton("Exporter");
		
	//TabbledPanes pour les informations textes Solution/PL
	JTabbedPane tabbedPane = new JTabbedPane();
		
	//Panel Solution (Onglet)
	private JPanel panSolution = new JPanel();
		
	//Panel Probl�meLin�aire (Onglet)
	private JPanel panPL = new JPanel();
		
	//RadioButton CPLEX/Recuit
	private JRadioButton radioBoutonCplex = new JRadioButton("CPLEX");
	private JRadioButton radioBoutonRecuit = new JRadioButton("Recuit Simul�");
		
	//Panel affichage villes 
	private PanelAffichageVilles panAffichageVilles = new PanelAffichageVilles();
	
	private JButton bouton = new JButton("Mon bouton");
	
	public Controleur() {
		
		//D�finit un titre la fen�tre
	    this.setTitle("PVC Resolveur - LaosDublos");
	    
	    //D�finit sa taille
	    this.setSize(1200, 600);
	    
	    //Positionne la fen�tre au centre
	    this.setLocationRelativeTo(null);
	    
	    //Termine le processus lorsqu'on clique sur la croix rouge
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Organisation de la Fen�tre
	    pan.setLayout(new BoxLayout(pan, BoxLayout.X_AXIS));
	    this.add(pan);
	    
	    //Panel contr�les
	    initialiserPanelControles();
	    
	    //Panel affichage villes
	    //panAffichageVilles.setBackground(Color.BLUE);
	    panAffichageVilles.setPreferredSize(new Dimension(800, 600));
	    pan.add(panAffichageVilles);
	    
	    initialiserBoutonImporter();
	    
	    //rend la fen�tre visible 
		this.setVisible(true);
	}
	
	public void initialiserBoutonImporter()
	{
		boutonImporter.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		    	int returnVal = fc.showOpenDialog(null);
		    	if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            //This is where a real application would open the file.
		            System.out.println("Opening: " + file.getName());
		        } else {
		        	System.out.println("Open command cancelled by user.");
		        }
		    }
		});
	}
	
	public void initialiserPanelControles()
	{
	    panControls.setBackground(Color.ORANGE);
	    panControls.setPreferredSize(new Dimension(400, 600));
	    panControls.setLayout(new BoxLayout(panControls, BoxLayout.Y_AXIS));
	    
		JPanel panBoutons = new JPanel();
		panBoutons.setBackground(Color.GREEN);
		panBoutons.setPreferredSize(new Dimension(400, 50));
		panBoutons.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
		panBoutons.setLayout(new GridLayout(1,3,15,15));
		
		panBoutons.add(boutonImporter);
		panBoutons.add(boutonResoudre);
		panBoutons.add(boutonExporter);
		
		JPanel panRadioBoutons = new JPanel();
		panRadioBoutons.setBackground(Color.PINK);
		panRadioBoutons.setPreferredSize(new Dimension(400, 50));
		panRadioBoutons.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
		panRadioBoutons.setLayout(new GridLayout(1,2,15,15));
		panRadioBoutons.add(radioBoutonCplex);
		panRadioBoutons.add(radioBoutonRecuit);
		
		JPanel panTerminal = new JPanel();
		panTerminal.setBackground(Color.GRAY);
		panTerminal.setPreferredSize(new Dimension(400, 400));
		panTerminal.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
		panTerminal.setLayout(new GridLayout(1,1,15,15));
		panPL.setBackground(Color.MAGENTA);
		panPL.setPreferredSize(new Dimension(400,400));
		panPL.setLayout(new BorderLayout());
		JTextArea displayPL = new JTextArea();
        displayPL.setEditable(false);
        JScrollPane scrollPL = new JScrollPane(displayPL);
        scrollPL.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panPL.add(scrollPL);
        
		panSolution.setBackground(Color.YELLOW);
		panSolution.setPreferredSize(new Dimension(400,400));
		panSolution.setLayout(new BorderLayout());
		JTextArea displaySolution = new JTextArea();
        displaySolution.setEditable(false);
        JScrollPane scrollSolution = new JScrollPane(displaySolution);
        scrollSolution.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panSolution.add(scrollSolution);
		tabbedPane.setTabPlacement(JTabbedPane.TOP);
		tabbedPane.addTab("Probl�me Lin�aire", panPL);
		tabbedPane.addTab("Solution", panSolution);
		panTerminal.add(tabbedPane);
		
		panControls.add(Box.createRigidArea(new Dimension(0,20)));
		panControls.add(panBoutons);
		panControls.add(Box.createRigidArea(new Dimension(0,20)));
		panControls.add(panRadioBoutons);
		panControls.add(panTerminal);
		
	    pan.add(panControls);
	}
	
		
	public static void main(String[] args) {
		/*File f = new File("a280.tsp");
		try{
			List<double[][]> infos = Parseur.parserTSP(new FileReader(f));
			probleme = new PLPVC(infos.get(1));
			fenetre.affichageVilles(infos.get(0));
			System.out.println(probleme.toString());
		} catch(FileNotFoundException e){
		}*/
		
		/*Controleur c = new Controleur();
		List<double[][]> infos = new ArrayList<double[][]>();
		infos = Parseur.parserXML(new File("a280.xml"));
		c.probleme = new PLPVC(infos.get(1));
		RecuitPVC r = new RecuitPVC((PLPVC)c.probleme);
		System.out.println("Go !");
		SolutionPVC sol = r.solutionInitiale();
		for(int i = 0; i < 280; i++){
			for(int j = 0; j < 280; j++)
				System.out.println(sol.getMatriceSolution()[i][j]);
			System.out.println("\n");
		}
		
		System.out.println(infos.get(0)[1][0]);
		c.panAffichageVilles.getVilles(infos.get(0));
		c.panAffichageVilles.affichageVilles();
		c.panAffichageVilles.tracerSolution(sol);*/
		
		Controleur c = new Controleur();
		List<double[][]> infos = new ArrayList<double[][]>();
		infos = Parseur.parserXML(new File("a280.xml"));
		//c.probleme = new PLPVC(infos.get(1));
		double[][] coor = {{10, 5}, {5, 10}, {15, 10}, {7, 20}, {13, 20}};
		double[][] cout = {{0, 1, 2, 3, 4}, {1, 0, 3, 6, 2}, {2, 3, 0, 7, 8}, {3, 6, 7, 0, 1}, {4, 2, 8, 1, 0}};
		CPLEX cplex = new CPLEX();
		c.probleme = new PLPVC(infos.get(1));
		c.panAffichageVilles.getVilles(infos.get(0));
		c.panAffichageVilles.affichageVilles();
		cplex.solveBrian((PLPVC)c.probleme).printCycleSolution();
		//c.panAffichageVilles.tracerSolution(cplex.solveBrian((PLPVC)c.probleme));
	}

}
