import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
public class Parseur {
	
	/**
	 * Determine les coordonnees des villes a partir de la matrice des distances
	 * @param cout Matrice des distances
	 * @param dim Taille du probleme
	 * @return Tableau de coordonnees [dim][2], chaque ligne correspondant a une ville
	 */
	private static double[][] calculePos(double[][] cout, int dim){
		double[][] m = new double[dim][dim];
		System.out.println("Calcul M");
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++)
				m[i][j] = ((cout[0][j])*(cout[0][j]) + (cout[i][0])*(cout[i][0]) - (cout[i][j])*(cout[i][j]))/2;
		}
		System.out.println("Décomposition");
		// Decomposition de la matrice
		EigenvalueDecomposition e = (new Matrix(m)).eig();
		Matrix U = e.getV();
		Matrix S = e.getD();

		int rankrow = S.getRowDimension();
		int rankcol = S.getColumnDimension();
		System.out.println("Calcul racine");
		// Calcul de la racine des valeurs propres
		for(int i = 0; i < rankcol; i++){
			for(int j = 0; j < rankrow; j++){
				double a = S.get(i, j);
				a = Math.sqrt(a);
				S.set(i, j, a);
			}
		}
		System.out.println("Solution");
		// Remplissage du tableau des solutions
		Matrix x = U.times(S);
		double[][] pos = new double[dim][2];
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < 2; j++)
				pos[i][j] = x.get(i, dim-(2-j));
		}
		
		return pos;
	}

	/**
	 * Determine les distances entre les villes a partir du tableau des coordonnees
	 * @param pos Tableau des coordonnees des villes
	 * @param dim Taille du probleme
	 * @return Matrice [dim][dim] contenant les distances entre chaque ville
	 */
	private static double[][] calculDistance(double[][] pos, int dim){
		double[][] cout = new double[dim][dim];
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++)
				cout[i][j] = Math.sqrt(Math.abs(pos[i][0]-pos[j][0])*Math.abs(pos[i][0]-pos[j][0]) + Math.abs(pos[i][1]-pos[j][1])*Math.abs(pos[i][1]-pos[j][1]));
		}
		return cout;
	}
	
	public static List<double[][]> parser(File f){
		switch(f.getName().split("\\.")[1]){
		case "tsp":
			try{
				return parserTSP(new FileReader(f));
			} catch(FileNotFoundException e){
				e.printStackTrace();
			};
			break;
		case "xml":
			return parserXML2(f);
		default:
			System.err.println("Ce format de fichier n'est pas pris en charge par l'application (formats acceptes : .tsp, .xml");
		}
		return null;
	}
	
	/**
	 * Parse un fichier .tsp
	 * @param f Fichier .tsp
	 * @return Liste contenant les positions et les distances pour chaque ville
	 */
	public static List<double[][]> parserTSP(FileReader f){
		String line;
		BufferedReader br = new BufferedReader(f);
		int dim = 0, i = 0;
		double pos[][] = new double[0][0];
		double cout[][] = new double[0][0];
		List<double[][]> infos = new ArrayList<double[][]>();
		
		try{
			// Saut de lignes
			for(int j = 0; j < 3; j++)
				br.readLine();
			
			// Dimension du problème
			dim = Integer.parseInt(removeBlanks(br.readLine().split(":")[1]));
			pos = new double[dim][2];
			
			// Saut de lignes
			br.readLine();
			br.readLine();
			
			// Positions des villes
			while(true){
				line = br.readLine();
				if(line.charAt(0) == 'E' && line.charAt(1) == 'O' && line.charAt(2) == 'F')
					break;
				pos[i][0] = Double.parseDouble(removeBlanks(line).split("\\s+")[1]);
				pos[i][1] = Double.parseDouble(removeBlanks(line).split("\\s+")[2]);
				i++;
			}

			infos.add(pos);
		} catch(IOException e){
			e.printStackTrace();
		};
		
		// Calcul des distances
		cout = new double[dim][dim];
		cout = calculDistance(pos, dim);
		
		infos.add(cout);
		
		return infos;
	}
	
	/**
	 * Parse un fichier .xml
	 * @param f Fichier .xml
	 * @return Liste contenant les positions et les distances pour chaque ville
	 */
	public static List<double[][]> parserXML(File f){
		double pos[][] = new double[0][0];
		double cout[][] = new double[0][0];
		List<double[][]> infos = new ArrayList<double[][]>();
		int dim = 0;
		
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);
			
			NodeList nList = doc.getElementsByTagName("vertex");
			dim = nList.getLength();
			cout = new double[dim][dim];

			for (int temp = 0; temp < dim; temp++){ 
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					for(int i = 0; i < dim-1; i++)
						cout[temp][Integer.parseInt(eElement.getElementsByTagName("edge").item(i).getTextContent())] = Double.parseDouble(((Element)(eElement.getElementsByTagName("edge").item(i))).getAttribute("cost"));
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		};

		pos = calculePos(cout, dim);
		infos.add(pos);
		infos.add(cout);
		
		return infos;
				
	}
	
	public static List<double[][]> parserXML2(File f){
		List<double[][]> infos = new ArrayList<double[][]>();
		
		try {
	         SAXParserFactory factory = SAXParserFactory.newInstance();
	         SAXParser saxParser = factory.newSAXParser();
	         TestSAX2Handler userhandler = new TestSAX2Handler();
	         saxParser.parse(f, userhandler);
	         System.out.println("CalculePos");
	         infos.add(calculePos(userhandler.c, userhandler.c.length));
	         infos.add(userhandler.c);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		
		return infos;
	}
	
	/**
	 * Retire les espaces en debut de ligne
	 * @param str Chaine a traiter
	 * @return Chaine traitee
	 */
	private static String removeBlanks(String str){
		while(str.charAt(0) == ' ')
			str = str.substring(1);
		return str;
	}
}

class TestSAX2Handler extends DefaultHandler
{
		boolean bFirstName = false;
	   boolean bLastName = false;
	   boolean name = false;
	   boolean bMarks = false;
	   public double[][] c;
	   int i = 0, j = 0;
	   String temp;

	   @Override
	   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	      
	      if (qName.equalsIgnoreCase("vertex")) {
	         bFirstName = true;
	      } else if (qName.equalsIgnoreCase("edge")) {
	    	 temp = attributes.getValue("cost");
	         bLastName = true;
	      } else if(qName.equalsIgnoreCase("name")){
	    	  name = true;
	      }
	   }

	   @Override
	   public void endElement(String uri, 
	      String localName, String qName) throws SAXException {
	      
	      if (qName.equalsIgnoreCase("vertex")) {
	    	  i+=1;
	    	  System.out.println(i);
	      }
	   }

	   @Override
	   public void characters(char ch[], int start, int length) throws SAXException {

	      if (bFirstName) {
	         bFirstName = false;
	      } else if (bLastName) {
	    	  j = Integer.parseInt(new String(ch, start, length));
	    	  if(i != j)
	    		  c[i][j] = Double.parseDouble(temp);
	    	  else
	    		  c[i][j] = 0;
	         bLastName = false;
	      } else if(name){
	    	  String temp = new String(ch, start, length);
	    	  /*for(int i = 0; i < temp.length(); i++){
	    		  if(temp.charAt(i) <= 47 && temp.charAt(i) >= 58){
	    			  temp = temp.substring(1);
	    			  i++;
	    		  }
	    	  }*/
	    	  while(temp.charAt(0) <= 47 || temp.charAt(0) >= 58)
	    		  temp = temp.substring(1);
	    	  System.out.println(temp);
	    	  c = new double[Integer.parseInt(temp)][Integer.parseInt(temp)];
	    	  name = false;
	      }
	   }
}
