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
	    JefePorNivel jefe = gP.getJefeActualEnCombate(); 

	    if (jefe != null && jefe.getVivo()) {
	        // Se actualiza la posición del área de colisión del proyectil para el frame actual.
	        solidArea.x = mundoX + solidArea.x;
	        solidArea.y = mundoY + solidArea.y;
	       	        
	        jefe.getSolidArea().x = 1000 + jefe.getSolidAreaDefaultX();
	        jefe.getSolidArea().y = 400 + jefe.getSolidAreaDefaultY();

	        // Si el área del proyectil se intersecta 
	        if (this.getSolidArea().intersects(jefe.getSolidArea())) {
	            jefe.recibirDaño(this.ataque); 
	            this.vivo = false;            
	        }

	        // Se restauran las posiciones relativas de las áreas de colisión.
	        solidArea.x = solidAreaDefaultX;
	        solidArea.y = solidAreaDefaultY;
	        jefe.getSolidArea().x = jefe.getSolidAreaDefaultX();
	        jefe.getSolidArea().y = jefe.getSolidAreaDefaultY();
	    }

	    if (this.vivo) {
	        if (direccion.equals("izquierda")) {
	            mundoX -= velocidad;
	        } else if (direccion.equals("derecha")) {
	            mundoX += velocidad;
	        }

	        
	        if (mundoX < 0 || mundoX > gP.getAnchoPantalla()) {
	            this.vivo = false;
	        }
	    }
	}
	public abstract void dibujar(Graphics2D g2);

	@Override
	public void setColisionOn(boolean colisionOn) {
		// TODO Auto-generated method stub
		
	}

}
