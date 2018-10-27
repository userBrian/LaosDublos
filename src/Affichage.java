import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

import com.sun.javafx.geom.Vec2d;

public class Affichage extends JFrame{
	
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
	
	//Panel ProblèmeLinéaire (Onglet)
	private JPanel panPL = new JPanel();
	
	//RadioButton CPLEX/Recuit
	private JRadioButton radioBoutonCplex = new JRadioButton("CPLEX");
	private JRadioButton radioBoutonRecuit = new JRadioButton("Recuit Simulé");
	
	//Panel affichage villes 
	private PanelAffichageVilles panAffichageVilles = new PanelAffichageVilles();
	
	private JButton bouton = new JButton("Mon bouton");
	
	public Affichage() {
		
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
		tabbedPane.addTab("Problème Linéaire", panPL);
		tabbedPane.addTab("Solution", panSolution);
		panTerminal.add(tabbedPane);
		
		panControls.add(Box.createRigidArea(new Dimension(0,20)));
		panControls.add(panBoutons);
		panControls.add(Box.createRigidArea(new Dimension(0,20)));
		panControls.add(panRadioBoutons);
		panControls.add(panTerminal);
		
	    pan.add(panControls);
	}
	
	public void affichageVilles(double[][] pos)
	{
		for(double[] ville : pos)
		{
			panAffichageVilles.villes.add(new Vec2d(ville[0],ville[1]));
			System.out.println(ville[0] +  "          " + ville[1]);
		}
		panAffichageVilles.paintComponent(panAffichageVilles.getGraphics());
	}
	
	class PanelAffichageVilles extends JPanel
	{
		public ArrayList<Vec2d> villes = new ArrayList<Vec2d>();
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			for(Vec2d v : villes)
			{
				g.drawOval((int)v.x, (int)v.y, 2, 2);
			}
		}
	}
}
