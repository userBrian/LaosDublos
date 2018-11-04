import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
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

	//Panel ProblèmeLinéaire (Onglet)
	private JPanel panPL = new JPanel();
	private JTextArea displayPL = new JTextArea();
	private JScrollPane scrollPL = new JScrollPane(displayPL);

	//RadioButton CPLEX/Recuit
	private JRadioButton radioBoutonCplex = new JRadioButton("CPLEX");
	private JRadioButton radioBoutonRecuit = new JRadioButton("Recuit Simulé");

	//Panel affichage villes 
	private PanelAffichageVilles panAffichageVilles = new PanelAffichageVilles();
	
	//Couleurs Application
	private Color couleurBoutons = new Color(141, 141, 146);
	private Color couleurFond = new Color(93, 94, 96);
	private Color couleurAffichageVilles = new Color(215,214,214);
	
	
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
	    panAffichageVilles.setBackground(couleurAffichageVilles);
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
		    	if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            //infos = Parseur.parserXML(file);
		            infos = Parseur.parserXML2(new File("att48.xml"));
		            probleme = new PLPVC(infos.get(1));
		            displayPL.setText("");
		            displayPL.append("Opening: " + file.getName() + "\n");
		            displayPL.append(probleme.toString());
		            
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
		    public void actionPerformed(ActionEvent e) 
		    {
		    	if(probleme != null)
		    	{
		    		panAffichageVilles.getVilles(infos.get(0));
		    		panAffichageVilles.affichageVilles();
		    		if(radioBoutonCplex.isSelected())
		    		{
		    			MethIte ite = new MethIte();
		    			probleme.setSolution(ite.resolutionProbleme((PLPVC)probleme));
		    		}
		    		else
		    		{
		    			RecuitPVC r = new RecuitPVC((PLPVC)probleme);
						probleme.setSolution(r.solutionInitiale());
		    		}
					panAffichageVilles.tracerSolution((SolutionPVC)probleme.getSolution());
		    		
		    	}
		    }
		});
	}
	public void initialiserBoutonExporter()
	{
		boutonExporter.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				
				int userSelection = chooser.showSaveDialog(null);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
			        try {
			        	
			            PrintWriter pw = new PrintWriter(chooser.getSelectedFile()+".csv");
			    		pw.write(probleme.getSolution().toCSV());
			    		pw.flush();
			    		pw.close();
			            
			        } catch (Exception ex) {
			            ex.printStackTrace();
			        }
			    }
				/*File f = chooser.getCurrentDirectory();
				String filename = f.getAbsolutePath();
				System.out.println(filename);*/
		    }
		});
	}
	public void initialiserRadioBoutonCplex()
	{
		radioBoutonCplex.setSelected(true);
		radioBoutonCplex.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	radioBoutonRecuit.setSelected(!radioBoutonCplex.isSelected());
		    }
		});
	}
	public void initialiserRadioBoutonRecuit()
	{
		radioBoutonRecuit.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	radioBoutonCplex.setSelected(!radioBoutonRecuit.isSelected());
		    }
		});
	}
	public void initialiserPanelControles()
	{
	    panControls.setBackground(couleurFond);
	    panControls.setPreferredSize(new Dimension(400, 600));
	    panControls.setLayout(new BoxLayout(panControls, BoxLayout.Y_AXIS));
	    
		JPanel panBoutons = new JPanel();
		panBoutons.setBackground(couleurFond);
		panBoutons.setPreferredSize(new Dimension(400, 50));
		panBoutons.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
		panBoutons.setLayout(new GridLayout(1,3,15,15));
		
		boutonImporter.setBackground(couleurBoutons);
		boutonResoudre.setBackground(couleurBoutons);
		boutonExporter.setBackground(couleurBoutons);
		panBoutons.add(boutonImporter);
		panBoutons.add(boutonResoudre);
		panBoutons.add(boutonExporter);
		
		JPanel panRadioBoutons = new JPanel();
		panRadioBoutons.setBackground(couleurFond);
		panRadioBoutons.setPreferredSize(new Dimension(400, 50));
		panRadioBoutons.setBorder(BorderFactory.createEmptyBorder(10, 70, 10, 70));
		panRadioBoutons.setLayout(new GridLayout(1,2,15,15));
		radioBoutonCplex.setBackground(couleurFond);
		radioBoutonRecuit.setBackground(couleurFond);
		panRadioBoutons.add(radioBoutonCplex);
		panRadioBoutons.add(radioBoutonRecuit);
		
		JPanel panTerminal = new JPanel();
		panTerminal.setBackground(couleurFond);
		panTerminal.setPreferredSize(new Dimension(400, 400));
		panTerminal.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
		panTerminal.setLayout(new GridLayout(1,1,15,15));
		panPL.setBackground(Color.MAGENTA);
		panPL.setPreferredSize(new Dimension(400,400));
		panPL.setLayout(new BorderLayout());
        displayPL.setEditable(false);
        displayPL.setBackground(couleurAffichageVilles);
        scrollPL.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panPL.add(scrollPL);
        
		panSolution.setBackground(couleurFond);
		panSolution.setPreferredSize(new Dimension(400,400));
		panSolution.setLayout(new BorderLayout());
        displaySolution.setEditable(false);
        displaySolution.setBackground(couleurAffichageVilles);
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
	    initialiserBoutonExporter();
	    initialiserRadioBoutonCplex();
	    initialiserRadioBoutonRecuit();
	}

	
	public static void main(String[] args) {
		
		Controleur c = new Controleur();
		/*try{
			c.infos = Parseur.parserXML2(new File("a280.xml"));
		} catch(Exception e){
			e.printStackTrace();
		};
		System.out.println("Done 2");
		c.panAffichageVilles.getVilles(c.infos.get(0));
		c.panAffichageVilles.affichageVilles();
		
		MethIte ite = new MethIte();
		c.probleme = new PLPVC(c.infos.get(1));
		SolutionPVC s = ite.resolutionProbleme((PLPVC)c.probleme);
		System.out.println("hey");
		s.printCycleSolution();
		c.panAffichageVilles.tracerSolution(s);*/
		
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
