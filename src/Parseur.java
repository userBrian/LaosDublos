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


public class Parseur {

	public Parseur() {
		
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
		int dim;
		
		try{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(f);
			
			NodeList nList = doc.getElementsByTagName("vertex");
			dim = nList.getLength();
			cout = new double[dim][dim];
			
			/*for(int i = 0; i < 280; i++){
				for(int j = 0; j < 280; j++){
					if(i == j)
						cout[i][j] = 1;
				}
			}*/

			for (int temp = 0; temp < dim; temp++){ 
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					//int j = 0;
					for(int i = 0; i < dim-1; i++){
						//System.out.println("Premier coût : " + ((Element)(eElement.getElementsByTagName("edge").item(i))).getAttribute("cost"));
						
						cout[temp][Integer.parseInt(eElement.getElementsByTagName("edge").item(i).getTextContent())] = Double.parseDouble(((Element)(eElement.getElementsByTagName("edge").item(i))).getAttribute("cost"));
						//System.out.println(temp + " " + Integer.parseInt(eElement.getElementsByTagName("edge").item(i).getTextContent()) + " " + Double.parseDouble(((Element)(eElement.getElementsByTagName("edge").item(i))).getAttribute("cost")));
						//j+=1;
					}
				}
			}
			//System.out.println(doc.getElementsByTagName("vertex").toString());
		} catch(Exception e){
			
		};
		
		/*for(int i = 0; i < 280; i++){
			for(int j = 0; j < 280-1; j++)
				System.out.println(cout[i][j]);
		}*/
		
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
