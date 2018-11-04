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
import java.io.PrintStream;
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
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
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

	//Panel contr�les
	private JPanel panControls = new JPanel();

	//Boutons importer/Resoudre/Exporter
	private JButton boutonImporter = new JButton("Importer");
	private JButton boutonResoudre = new JButton("R�soudre");
	private JButton boutonExporter = new JButton("Exporter");

	//TabbledPanes pour les informations textes Solution/PL
	JTabbedPane tabbedPane = new JTabbedPane();

	//Panel Solution
	private JPanel panSolution = new JPanel();
	private JTextArea displaySolution = new JTextArea();
	private JScrollPane scrollSolution = new JScrollPane(displaySolution);

	//RadioButton CPLEX/Recuit
	private JRadioButton radioBoutonCplex = new JRadioButton("CPLEX");
	private JRadioButton radioBoutonRecuit = new JRadioButton("Recuit Simul�");

	//Panel affichage villes 
	private PanelAffichageVilles panAffichageVilles = new PanelAffichageVilles();
	
	//Couleurs Application
	private Color couleurBoutons = new Color(169, 253, 172);
	private Color couleurFond = new Color(68, 207, 108);
	private Color couleurAffichageVilles = new Color(241,255,231);
	
	
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
	    panAffichageVilles.setBackground(couleurAffichageVilles);
	    panAffichageVilles.setPreferredSize(new Dimension(800, 600));
	    pan.add(panAffichageVilles);

	    //rend la fen�tre visible 
		this.setVisible(true);
	}
	
	public void initialiserBoutonImporter()
	{
		boutonImporter.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		    	FileNameExtensionFilter filter = new FileNameExtensionFilter("DATA", "xml", "tsp");
		    	fc.setFileFilter(filter);
		    	if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
		    	{
		            File file = fc.getSelectedFile();
		            infos = Parseur.parser(file);
		            if(infos != null){
			            probleme = new PLPVC(infos.get(1));
			            boutonResoudre.setEnabled(true);
			            displaySolution.setText("");
			            displaySolution.append("Vous avez import� un nouveau fichier : " + file.getName() + "\n");
			            displaySolution.append("Cliquez sur le bouton R�soudre pour la r�solution du probl�me\n");
			            displaySolution.append("Si le probl�me contient beauocup de villes, la r�solution risque d'�tre longue.\n");
		            } else{
		            }
		    	}
		    }
		});
	}
	
	public void initialiserBoutonResoudre()
	{
		boutonResoudre.setEnabled(false);
		boutonResoudre.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {
		    	System.out.println("bouton Resoudre");
		    	if(probleme != null)
		    	{
		    		System.out.println("problem != null");
		    		displaySolution.setText("");
		    		displaySolution.append("Premi�re �tape : Affichage des villes...\n");
		    		panAffichageVilles.getVilles(infos.get(0));
		    		panAffichageVilles.affichageVilles();
		    		displaySolution.append("Deuxi�me �tape : R�solution du probl�me...\n");
		    		displaySolution.append("Cette �tape peut durer longtemps en fonction du nombre de villes.\n");
		    		if(radioBoutonCplex.isSelected())
		    		{
		    			MethIte ite = new MethIte();
		    			probleme.setSolution(ite.resolutionProbleme((PLPVC)probleme));
		    		}
		    		else
		    		{
		    			RecuitPVC r = new RecuitPVC((PLPVC)probleme);
		    			r.mainLoop();
		    			probleme.setSolution(r.meilleureSolution);
		    		}
		    		displaySolution.setText("Voici la solution optimale trouv�e : \n");
		    		displaySolution.append(((SolutionPVC)probleme.getSolution()).toCSV());
					panAffichageVilles.tracerSolution((SolutionPVC)probleme.getSolution());
					boutonExporter.setEnabled(true);
		    	}
		    }
		});
	}
	public void initialiserBoutonExporter()
	{
		boutonExporter.setEnabled(false);
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
        
		panSolution.setBackground(couleurFond);
		panSolution.setPreferredSize(new Dimension(400,400));
		panSolution.setLayout(new BorderLayout());
        displaySolution.setEditable(false);
        displaySolution.setBackground(couleurAffichageVilles);
        scrollSolution.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panSolution.add(scrollSolution);
		panTerminal.add(panSolution);
		
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
	}

}
