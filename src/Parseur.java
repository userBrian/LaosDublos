import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parseur {

	public Parseur() {
		
	}

	private PL parserTSP(FileReader f){
		PL probleme;
		String line;
		BufferedReader br = new BufferedReader(f);
		int pos[][], dim, i = 0;
		
		try{
			// Saut de lignes
			for(int j = 0; j < 3; j++)
				br.readLine();
			
			// Dimension du problème
			dim = Integer.parseInt(br.readLine().substring(11));
			probleme = new PL(dim);
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
			probleme.setFoncObj(pos);

			return probleme;
		} catch(IOException e){
			
		};
		return new PL(0);
	}
	
	private PL parserXML(FileReader f){
		return new PL(0);
	}
	
	public PL parserFichier(File f){
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
		return new PL(0);
	}
	
	private String removeBlanks(String str){
		while(str.charAt(0) == ' ')
			str = str.substring(1);
		return str;
	}
}
