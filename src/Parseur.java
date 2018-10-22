import java.io.File;

public class Parseur {

	public Parseur() {
		parserFichier(new File("a280.tsp"));
	}

	private PL parserTSP(File f){
		return new PL();
	}
	
	private PL parserXML(File f){
		return new PL();
	}
	
	public PL parserFichier(File f){
		PL probleme = new PL();
		String ext = f.getName().split("\\.")[1];
		switch(ext){
			case "tsp":
				probleme = parserTSP(f);
				break;
			case "xml":
				probleme = parserXML(f);
				break;
			default:
				System.err.println("Ce type de fichier n'est pas reconnu par l'application.");
		}
		return new PL();
	}
}
