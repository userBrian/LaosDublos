import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
public class Parseur {
	
	/**
	 * Determine les coordonnees des villes a partir de la matrice des distances
	 * @param cout Matrice des distances
	 * @param dim Taille du probleme
	 * @return Tableau de coordonnees [dim][2], chaque ligne correspondant a une ville
	 */
	private static double[][] calculePos(double[][] cout, int dim){	// TODO : commenter et optimiser
		double[][] M = new double[dim][dim];
		int i = 0;
		int j = 0;
		
		for (i = 0; i < dim; i++){
			for (j = 0; j < dim; j++)
				M[i][j] = ((cout[0][j])*(cout[0][j]) + (cout[i][0])*(cout[i][0]) - (cout[i][j])*(cout[i][j]))*0.5;
		}
		
		Matrix m = new Matrix(M);
		EigenvalueDecomposition e = m.eig();
		Matrix U = e.getV();
		Matrix S = e.getD();

		int rankrow = S.getRowDimension();
		int rankcol = S.getColumnDimension();
		
		for (i = 0; i < rankcol; i++){
			for (j = 0; j < rankrow; j++){
				double a = S.get(i, j);
				a = Math.sqrt(a);
				S.set(i, j, a);
			}
		}
		
		Matrix x = U.times(S);
		double[][] pos = new double[dim][2];
		for(i = 0; i < dim; i++){
			for(j = 0; j < 2; j++)
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
		double[][] cout = new double[dim][2];
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++)
				cout[i][j] = Math.sqrt(Math.abs(pos[i][0]-pos[j][0])*Math.abs(pos[i][0]-pos[j][0]) + Math.abs(pos[i][1]-pos[j][1])*Math.abs(pos[i][1]-pos[j][1]));
		}
		return cout;
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
			dim = Integer.parseInt(br.readLine().substring(11));
			pos = new double[dim][2];
			
			// Saut de lignes
			br.readLine();
			br.readLine();
			
			// Positions des villes
			while(true){
				line = br.readLine();
				if(line.charAt(0) == 'E' && line.charAt(1) == 'O' && line.charAt(2) == 'F')
					break;
				pos[i][0] = Integer.parseInt(removeBlanks(line).split("\\s+")[1]);
				pos[i][1] = Integer.parseInt(removeBlanks(line).split("\\s+")[2]);
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
