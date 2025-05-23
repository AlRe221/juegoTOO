package Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entidad.JefePorNivel;
import entidad.Jugador;
import entidad.Proyectil;

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
	  
	  for (int i = 0; i < gP.getListaProyectilJugador().size(); i++) {
		    Proyectil p = gP.getListaProyectilJugador().get(i);
		    if (p != null) {
		        if (p.getVivo()) {
		            p.update();
		        } else {
		            gP.getListaProyectilJugador().remove(i);
		            i--; // <-- para revisar correctamente el siguiente proyectil
		        }
		    }
		}

		
	}
	
	
	public void draw(Graphics2D g2) {
		mostrarFondoCombates(g2);
		gP.getUi().mostrarBarraVida(g2);
		gP.getUi().mostrarBarraVidaJ(g2);
		pintarProyectil(g2);
	}
	
	public void jefeAparecer() {
		jN.contadorSprites();
	}
	
	
	public void pintarProyectil(Graphics2D g2) {
		for(int i = 0; i < gP.getListaProyectilJugador().size(); i++) {
			  if(gP.getListaProyectilJugador().get(i) != null) {
				  gP.getListaProyectilJugador().get(i).dibujar(g2);
			  }
		  }
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
