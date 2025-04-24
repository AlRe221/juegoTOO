package Main;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

public class Tipografia {
	
	public static Font cargaFuente(float tam) {
		try {
		InputStream is = Tipografia.class.getResourceAsStream("/pixeled/Pixeled.ttf");
		Font fuente = Font.createFont(Font.TRUETYPE_FONT, is);
		
		fuente = fuente.deriveFont(tam);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		return fuente;
	}catch(Exception e) {
		e.printStackTrace();
		return new Font("SamsSerif", Font.PLAIN,30);
	}

}
}
