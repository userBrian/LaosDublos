import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class Controleur extends JFrame {
	
	/////////////DONNEES/////////////
	private PL probleme;
	private MethIte iterative;
	private Recuit recuit;
	private List<double[][]> infos = new ArrayList<double[][]>();

	/////////////AFFFICHAGE/////////////
	//General JPanel
	private JPanel pan = new JPanel();

	//Panel contrôles
	private JPanel panControls = new JPanel();

	//Boutons importer/Resoudre/Exporter
	private JButton boutonImporter = new JButton("Importer");
	private JButton boutonResoudre = new JButton("Résoudre");
	private JButton boutonExporter = new JButton("Exporter");

	//TabbledPanes pour les informations textes Solution/PL
	JTabbedPane tabbedPane = new JTabbedPane();

	//Panel Solution (Onglet)
	private JPanel panSolution = new JPanel();
	private JTextArea displaySolution = new JTextArea();
	private JScrollPane scrollSolution = new JScrollPane(displaySolution);
	private String informationSolution;

	//Panel ProblèmeLinéaire (Onglet)
	private JPanel panPL = new JPanel();
	private JTextArea displayPL = new JTextArea();
	private JScrollPane scrollPL = new JScrollPane(displayPL);
	private String informationPL;

	//RadioButton CPLEX/Recuit
	private JRadioButton radioBoutonCplex = new JRadioButton("CPLEX");
	private JRadioButton radioBoutonRecuit = new JRadioButton("Recuit Simulé");

	//Panel affichage villes 
	private PanelAffichageVilles panAffichageVilles = new PanelAffichageVilles();
	
	public Controleur() {
		
		//Définit un titre la fenêtre
	    this.setTitle("PVC Resolveur - LaosDublos");
	    
	    //Définit sa taille
	    this.setSize(1200, 600);
	    
	    //Positionne la fenêtre au centre
	    this.setLocationRelativeTo(null);
	    
	    //Termine le processus lorsqu'on clique sur la croix rouge
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Organisation de la Fenêtre
	    pan.setLayout(new BoxLayout(pan, BoxLayout.X_AXIS));
	    this.add(pan);
	    
	    //Panel contrôles
	    initialiserPanelControles();
	    
	    //Panel affichage villes
	    //panAffichageVilles.setBackground(Color.BLUE);
	    panAffichageVilles.setPreferredSize(new Dimension(800, 600));
	    pan.add(panAffichageVilles);
	    
	    initialiserBoutonImporter();
	    
	    //rend la fenêtre visible 
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
		            //infos = Parseur.parserXML(file);
		            infos = Parseur.parserXML(new File("a280.xml"));
		            probleme = new PLPVC(infos.get(1));
		            informationPL = "Opening: " + file.getName() + "\n";
		            informationPL += probleme.toString();
		            
		        } else {
		        	System.out.println("Open command cancelled by user.");
		        }
		    }
		});
	}
	
	public void initialiserBoutonResoudre()
	{
		boutonResoudre.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	
				RecuitPVC r = new RecuitPVC((PLPVC)probleme);
				System.out.println("Go !");
				SolutionPVC sol = r.solutionInitiale();
				for(int i = 0; i < 280; i++){
					for(int j = 0; j < 280; j++)
						System.out.println(sol.getMatriceSolution()[i][j]);
					System.out.println("\n");
				}
				
				System.out.println(infos.get(0)[1][0]);
				panAffichageVilles.getVilles(infos.get(0));
				panAffichageVilles.affichageVilles();
				panAffichageVilles.tracerSolution(sol);
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
        displayPL.setEditable(false);
        scrollPL.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panPL.add(scrollPL);
        
		panSolution.setBackground(Color.YELLOW);
		panSolution.setPreferredSize(new Dimension(400,400));
		panSolution.setLayout(new BorderLayout());
        displaySolution.setEditable(false);
        scrollSolution.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panSolution.add(scrollSolution);
		tabbedPane.setTabPlacement(JTabbedPane.TOP);
		tabbedPane.addTab("Problème Linéaire", panPL);
		tabbedPane.addTab("Solution", panSolution);
		panTerminal.add(tabbedPane);
		
		panControls.add(Box.createRigidArea(new Dimension(0,20)));
		panControls.add(panBoutons);
		panControls.add(Box.createRigidArea(new Dimension(0,20)));
		panControls.add(panRadioBoutons);
		panControls.add(panTerminal);
		
	    pan.add(panControls);
	    
	    initialiserBoutonImporter();
	    initialiserBoutonResoudre();
	}
	
		
	public static void main(String[] args) {
		
		Controleur c = new Controleur();
		try{
			c.infos = Parseur.parserXML2(new File("fl3795.xml"));
		} catch(Exception e){
			e.printStackTrace();
		};
		System.out.println("Done 2");
		c.panAffichageVilles.getVilles(c.infos.get(0));
		c.panAffichageVilles.affichageVilles();
		/*double[][] coor = {{10, 5}, {5, 10}, {15, 10}, {7, 20}, {13, 20}};
		double[][] cout = {{0, 1, 2, 3, 4, 2, 3, 1, 2}, {1, 0, 3, 6, 2, 8, 7, 2, 4}, {2, 3, 0, 7, 8, 7, 6, 3, 6}, {3, 6, 7, 0, 1, 5, 8, 4, 8}, {4, 2, 8, 1, 0, 1, 5, 5, 1}, {2, 8, 7, 5, 1, 0, 2, 6, 3}, {3, 7, 6, 8, 5, 2, 0, 7, 5}, {1, 2, 3, 4, 5, 6, 7, 0, 7}, {2, 4, 6, 8, 1, 3, 5, 7, 0}};
		MethIte ite = new MethIte();
		c.probleme = new PLPVC(c.infos.get(1));
		
		c.panAffichageVilles.getVilles(c.infos.get(0));
		c.panAffichageVilles.affichageVilles();
		SolutionPVC s = ite.resolutionProbleme((PLPVC)c.probleme);
		s.printCycleSolution();

		System.out.println(s.contrainteSousToursSatisfaite());
		c.panAffichageVilles.tracerSolution(s);*/
		//c.panAffichageVilles.tracerSolution(cplex.solveBrian((PLPVC)c.probleme));
	}

}
