package Main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImagenesEscaladas {
	
	public BufferedImage scaleImage(BufferedImage original,int width, int heigth) {
		BufferedImage imagenEscalada = new BufferedImage(width,heigth, original.getType());
		Graphics2D g2 = imagenEscalada.createGraphics();
		g2.drawImage(original, 0, 0, width, heigth, null);
		g2.dispose();
		
		return imagenEscalada;
	}
	

}
