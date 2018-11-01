import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;
import com.sun.javafx.geom.Vec2d;

class PanelAffichageVilles extends JPanel
{
	public ArrayList<Vec2d> villes = new ArrayList<Vec2d>();
	
	private int radius = 10;
	
	public void getVilles(double[][] pos)
	{
		for(double[] ville : pos)
		{
			this.villes.add(new Vec2d(ville[0],ville[1]));
		}
		int maxX = -1;
		int minX = -1;
		int maxY = -1;
		int minY = -1;
		for(Vec2d v : villes)
		{
			if(v.x < minX || minX == -1)
			{
				minX = (int)v.x;
			}
			if(v.y < minY || minY == -1)
			{
				minY = (int)v.y;
			}
			if(maxX == -1|| v.x > maxX )
			{
				maxX = (int)v.x;
			}
			if(maxY == -1 || v.y > maxY)
			{
				maxY = (int)v.y;
			}
		}
		minX -= 1;
		minY -= 1;

		float coeffX = this.getWidth()/(float)(maxX - minX);
		float coeffY = this.getHeight()/(float)(maxY - minY);
		
		for(int i = 0; i < villes.size(); i++)
		{
			villes.get(i).x = ((villes.get(i).x - minX) * coeffX);
			villes.get(i).y = ((villes.get(i).y - minY) * coeffY);
		}
	}
	
	public void affichageVilles()
	{
		for(int i = 0; i < villes.size(); i++)
		{
			this.getGraphics().fillOval((int)villes.get(i).x - radius/2, (int)villes.get(i).y - radius/2, radius, radius);
		}
	}
	
	public void tracerSolution(SolutionPVC s)
	{
		for(int i = 0; i < s.getCycleSolution().size() - 1; i++)
		{
			this.getGraphics().drawLine((int)villes.get(s.getCycleSolution().get(i)).x, (int)villes.get(s.getCycleSolution().get(i)).y, (int)villes.get(s.getCycleSolution().get(i + 1)).x, (int)villes.get(s.getCycleSolution().get(i + 1)).y);
		}

	}
}
