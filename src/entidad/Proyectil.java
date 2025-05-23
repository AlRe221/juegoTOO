package entidad;

import java.awt.Graphics2D;

import Main.GamePanel;

public abstract class Proyectil extends Entidad{
	
	Entidad e;
	Graphics2D g2;
	public Proyectil(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
	}
	
	public void set(int wX, int wY, String direccion, boolean vivo, Entidad usuario) {
		this.mundoX = wX; 
		this.mundoY = wY; 
		this.direccion = direccion; 
		this.vivo = vivo; 
		this.e = usuario;
		this.vida = this.vidaMaxima;
		
		
	}
	public void update() {
		  if(direccion.equals("izquierda")) {
		        mundoX -= velocidad;
		    } else if(direccion.equals("derecha")) {
		        mundoX += velocidad;
		    }

		    // Condición para marcarlo como no vivo y eliminarlo
		    if(mundoX < 0 || mundoX > gP.getAnchoPantalla()) {
		        vivo = false;
		    }
	}
	public abstract void dibujar(Graphics2D g2);

	@Override
	public void setColisionOn(boolean colisionOn) {
		// TODO Auto-generated method stub
		
	}

}
