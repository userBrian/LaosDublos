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
			for(int j = 0; j < dim; j++){
				//System.out.print(cout[i][j]);
				m[i][j] = (cout[0][j]*cout[0][j] + cout[i][0]*cout[i][0] - cout[i][j]*cout[i][j])/2;
			}
			//System.out.println("\n");
		}
		
		EigenvalueDecomposition ed = new EigenvalueDecomposition(new Matrix(m, dim, dim));
		/*for(int i = 0; i < ed.getD().getArray().length; i++){
			for(int j = 0; j < ed.getD().getArray()[i].length; j++)
				System.out.println(ed.getD().getArray()[i][j]);
		}*/
		//ed.getD().print(5, 5);
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++)
				ed.getD().set(i, j, Math.sqrt(ed.getD().getArray()[i][j]));
		}
		Matrix x = ed.getV().times(ed.getD());
		/*for(int i = 0; i < dim; i++){
			for(int j = 0; j < 2; j++){
				System.out.print(x.getArray()[i][j]);
			}
			System.out.println("");
		}*/
		//x.print(2, 3);
		return x.getArray();
	}

	private static double calculDistance(double x1, double y1, double x2, double y2){
		return Math.sqrt(Math.abs(x1-x2)*Math.abs(x1-x2) + Math.abs(y1-y2)*Math.abs(y1-y2));
	}
	
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
			
		};
		
		cout = new double[dim][dim];
		for(int k = 0; k < dim; k++){
			for(int j = 0; j < dim; j++)
				cout[k][j] = calculDistance(pos[k][0], pos[k][1], pos[j][0], pos[j][1]);
		}
		
		infos.add(cout);
		
		return infos;
	}
	
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
