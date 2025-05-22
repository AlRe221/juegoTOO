package Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entidad.JefePorNivel;
import entidad.Jugador;

public class FightGame {
	protected GamePanel gP; 
	protected Jugador jug; 
	protected JefePorNivel jN;
	protected BufferedImage image;
	
	private boolean peleaTerminada = false; 
	private boolean gano = false;
	
	public FightGame(GamePanel gP, Jugador jug, JefePorNivel jN) {
		this.gP = gP;
		this.jug = jug;
		this.jN = jN;
	} 
	
	
	public void iniciaCombate() {
		
	}
	
	public void update() {
	  jefeAparecer();
	  jug.updateCombate();
		
	}
	
	
	public void draw(Graphics2D g2) {
		mostrarFondoCombates(g2);
		
		
	
	}
	
	public void jefeAparecer() {
		jN.contadorSprites();
	}
	
	
	   public void mostrarFondoCombates(Graphics2D g2) {
		   int nivel = gP.getAssS().getidJFN();
		   switch(nivel) {
		   case 0 :{
			   try {
				   image = ImageIO.read(getClass().getResourceAsStream("/ImagenesPantallas/im4.png"));
				   g2.drawImage(image, 0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla(), null);
			   }catch(Exception e) {   
			   }
			   break;
		   }
		   case 1: {
			   try {
				   image = ImageIO.read(getClass().getResourceAsStream("/ImagenesPantallas/im2.png"));
				   g2.drawImage(image, 0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla(), null);
			   }catch(Exception e) {   
			   }
			   break;
		   }
		   case 2: {
			   try {
				   image = ImageIO.read(getClass().getResourceAsStream("/ImagenesPantallas/im3.png"));
				   g2.drawImage(image, 0, 0, gP.getAnchoPantalla(), gP.getAltoPantalla(), null);
			   }catch(Exception e) {   
			   }
			   break;
		   }
		   }
		   
		   g2.setColor(Color.GRAY);
		   g2.fillRect(0, 590, gP.getWidth(), 150);
		   
		   if(jN != null) {
			   jN.drawEnCombate(g2,1000,400,200,200);
		   }
		   
		   if(jug != null) {
			  jug.drawEnCombate(g2);
		   }
	   }
	
	
	public void terminaCombate(boolean win) {
		this.gano = win; 
		this.peleaTerminada = true;
	}
	

}
