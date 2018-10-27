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

	public Parseur() {
		
	}
	
	private static double[][] calculePos(double[][] cout, int dim){
		double[][] m = new double[dim][dim];
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++)
				m[i][j] = (cout[0][j]*cout[0][j] + cout[i][0]*cout[i][0] - cout[i][j]*cout[i][j])/3;
		}
		
		EigenvalueDecomposition ed = new EigenvalueDecomposition(new Matrix(m, dim, dim));
		for(int i = 0; i < ed.getV().getArray().length; i++){
			for(int j = 0; j < ed.getV().getArray()[i].length; j++)
				System.out.println(ed.getV().getArray()[i][j]);
		}
		Matrix s = ed.getD().copy();
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++)
				s.getArray()[i][j] = Math.sqrt(ed.getD().getArray()[i][j]);
		}
		Matrix x = ed.getV().times(s);
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < 2; j++){
				System.out.print(x.getArray()[i][j]);
			}
			System.out.println("");
		}
		return x.getArray();
	}

	public static int[][] parserTSP(FileReader f){
		String line;
		BufferedReader br = new BufferedReader(f);
		int pos[][], dim, i = 0;
		
		try{
			// Saut de lignes
			for(int j = 0; j < 3; j++)
				br.readLine();
			
			// Dimension du problème
			dim = Integer.parseInt(br.readLine().substring(11));
			pos = new int[dim][2];
			
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

			return pos;
		} catch(IOException e){
			
		};
		return new int[0][0];
	}
	
	public static List<double[][]> parserXML(File f, PL prob){
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
					for(int i = 0; i < dim-1; i++){
						cout[temp][Integer.parseInt(eElement.getElementsByTagName("edge").item(i).getTextContent())] = Double.parseDouble(((Element)(eElement.getElementsByTagName("edge").item(i))).getAttribute("cost"));
					}
				}
			}
		} catch(Exception e){
			
		};
		
		pos = calculePos(cout, dim);		
		
		infos.add(pos);
		infos.add(cout);
		
		return infos;
				
	}
	
	/*public int[][] parserFichier(File f){
		String ext = f.getName().split("\\.")[1];
		switch(ext){
			case "tsp":
				try{
					return parserTSP(new FileReader(f));
				} catch(FileNotFoundException e){

				};
				break;
			case "xml":
				try{
					return parserXML(new FileReader(f));
				} catch(FileNotFoundException e){
					
				};
				break;
			default:
				System.err.println("Ce type de fichier n'est pas reconnu par l'application.");
		}
		return new int[0][0];
	}*/
	
	private static String removeBlanks(String str){
		while(str.charAt(0) == ' ')
			str = str.substring(1);
		return str;
	}
}
