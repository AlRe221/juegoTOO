package entidad;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import Main.GamePanel;

public abstract class Zombie extends Entidad{
	String nombre;
	BufferedImage i;
	private final int radioDeteccion = 200;
	

	public Zombie(String n, GamePanel gP) {
		super(gP);
		this.tipoE = 1;
		this.nombre = n; 
		velocidad = 1;
		direccion = "abajo";
		this.solidArea = new Rectangle(8,16,32,32); 
		this.solidAreaDefaultX = this.solidArea.x;
		this.solidAreaDefaultY = this.solidArea.y;
		
	}
	//para que se mueva en cierto espacio.
	public void setAction() {
	    // 1Calculamos la posición del jugador y la distancia al zombi
	    int posJugadorX = gP.getJugador().getMundoX();
	    int posJugadorY = gP.getJugador().getMundoY();
	    int deltaX      = posJugadorX - this.mundoX;
	    int deltaY      = posJugadorY - this.mundoY;
	    double distancia = Math.hypot(deltaX, deltaY);

	    // Si está dentro del radio de detección, perseguir al jugador
	    if (distancia < radioDeteccion) {
	        if (Math.abs(deltaX) > Math.abs(deltaY)) {
	            direccion = (deltaX > 0) ? "derecha" : "izquierda";
	        } else {
	            direccion = (deltaY > 0) ? "abajo"   : "arriba";
	        }
	        // Salimos para que colisiones() mueva al zombi inmediatamente
	        return;
	    }

	    // Fuera de alcance: cada 120 frames patrulla de forma aleatoria
	    actionLockCounter++;
	    if (actionLockCounter >= 120) {
	        Random aleatorio = new Random();
	        int n = aleatorio.nextInt(4);  // valor entre 0 y 3
	        switch (n) {
	            case 0: direccion = "arriba";    break;
	            case 1: direccion = "abajo";     break;
	            case 2: direccion = "izquierda"; break;
	            default: direccion = "derecha";  break;
	        }
	        actionLockCounter = 0;
	    }
	}

		
		int dañototal = 60; 
		int dañoContador = 0;
		
		public void update() {
			setAction();
			colisionOn = false;
			gP.getchecadorColision().checkTile(this);
			boolean res = gP.getchecadorColision().checkJugador(this);
			
			reducirVidaPocoApoco(res);
			colisiones();
		    contarSprites();
		}
		
		
		public void reducirVidaPocoApoco(boolean res) {
			if (this.tipoE == 1 && res == true) {
			    if (dañoContador <= 0) {
			        gP.getJugador().setVida(gP.getJugador().getVida() - 3);
			        dañoContador = dañototal;
			    }
			}

			if (dañoContador > 0) {
			    dañoContador--;
			}
		}
		
		public void colisiones() {
			 if(colisionOn == false) {
			    	switch(direccion) {
			    	case "arriba":{
			    		if(this.mundoY - this.velocidad >= 0) {
			    			this.setMundoY(this.mundoY - this.velocidad);
			    		 }else {
			    			 this.setMundoY(0);
			    		 }
			    		break;
			    		} 
			    	case "abajo" :{ 
			    		if(this.mundoY + this.velocidad <= gP.altoMundo - gP.getTamanioTile()) {
			    			this.setMundoY(this.mundoY + this.velocidad);
			    			
			    		}else {
			    			this.setMundoY(gP.altoMundo - gP.getTamanioTile());
			    		}
			    		break;
			    		}
			    	case "izquierda" :{
			    		if(this.mundoX - velocidad >= 0) {
			    			this.setMundoX(this.mundoX - velocidad);
			    		}else {
			    			this.setMundoX(0);
			    		}
			    		break;
			    		} 
			    	case "derecha" :{
			    		if(this.mundoX + velocidad + gP.getTamanioTile() <= gP.anchoMundo) {
			    			this.setMundoX(this.mundoX + this.velocidad); 
			    		}else {
			    			this.setMundoX(gP.anchoMundo - gP.getTamanioTile());
			    		}
			    		break; 
			    		}
			    	}
			    }
		}
		
		public void contarSprites() {
			  this.contadorSprites++;
			    
			    if (this.contadorSprites > this.cambiaSprite) {
			        if (this.numeroSprite == 1)
			            this.numeroSprite = 2;
			        else
			            this.numeroSprite = 1;
			        this.contadorSprites = 0;
			    }
		}
		
		
		
		public void draw(Graphics2D g2) {
			int pantallaX = mundoX -gP.getJugador().getMundoX() + gP.getJugador().getPantallaX();
			int pantallaY = mundoY - gP.getJugador().getMundoY() + gP.getJugador().getPantallaY();
			
			//detener camara
		     colocarObjeto(pantallaX,pantallaY,g2,gP);
		}
		
		
		public void colocarObjeto(int x,int y, Graphics2D g2, GamePanel gP) {
	    	
	    	if(gP.getJugador().getMundoX() < gP.getJugador().getPantallaX()) {
				x = mundoX;
			}
			if(gP.getJugador().getMundoY() < gP.getJugador().getPantallaY()) {
				y = mundoY;
			}
			
			int rOffs = gP.getAnchoPantalla() - gP.getJugador().getPantallaX(); 
			if(rOffs > gP.getAnchoMundo() - gP.getJugador().getMundoX()) {
				x = gP.getAnchoPantalla() - (gP.anchoMundo - mundoX); 	
			}
			
			int bOffs = gP.getAltoPantalla() - gP.getJugador().getPantallaY(); 
			if(bOffs > gP.getAltoMundo() - gP.getJugador().getMundoY()) {
				y = gP.getAltoPantalla() - (gP.altoMundo - mundoY); 	
			}
			
			if(mundoX + gP.getTamanioTile() > gP.getJugador().getMundoX() - gP.getJugador().getPantallaX() &&
			   mundoX - gP.getTamanioTile() < gP.getJugador().getMundoX() + gP.getJugador().getPantallaX() &&
			   mundoY + gP.getTamanioTile() > gP.getJugador().getMundoY() - gP.getJugador().getPantallaY() &&
			   mundoY- gP.getTamanioTile() < gP.getJugador().getMundoY() + gP.getJugador().getPantallaY()) {
				i = direcciones();
				g2.drawImage(i, x, y, gP.getTamanioTile(), gP.getTamanioTile(), null);
			}else {
				if(gP.getJugador().getMundoX() < gP.getJugador().getPantallaX() ||
						gP.getJugador().getMundoY() < gP.getJugador().getPantallaY() ||
						rOffs > gP.getAnchoMundo() - gP.getJugador().getMundoX() ||
						bOffs > gP.getAltoMundo() - gP.getJugador().getMundoY()) {
						g2.drawImage(i, x, y, gP.getTamanioTile(), gP.getTamanioTile(), null);
					}
			}
			
	    }
		
public BufferedImage direcciones() {
	BufferedImage sprite = null;
	switch(this.direccion)
	{
	case "arriba" : 
		if(this.numeroSprite == 1)
			sprite = this.arriba1; 
		if(this.numeroSprite == 2)
			sprite = this.arriba2; 
		break;
	case "abajo" : 
		if(this.numeroSprite == 1)
			sprite = this.abajo1; 
		if(this.numeroSprite == 2)
			sprite = this.abajo2; 
		break;
	case "izquierda" : 
		if(this.numeroSprite == 1)
			sprite = this.izquierda1; 
		if(this.numeroSprite == 2)
			sprite = this.izquierda2; 
		break;
	case "derecha" : 
		if(this.numeroSprite == 1)
			sprite = this.derecha1; 
		if(this.numeroSprite == 2)
			sprite = this.derecha2; 
		break;
	case "estatico" : 
		if(this.numeroSprite == 1)
			sprite = this.estatico1; 
		if(this.numeroSprite == 2)
			sprite = this.estatico2; 
		break;
	case "estaticoArriba" : 
		if(this.numeroSprite == 1)
			sprite = this.estaticoA1; 
		if(this.numeroSprite == 2)
			sprite = this.estaticoA2; 
		break;	
	case "estaticoDerecha" : 
		if(this.numeroSprite == 1)
			sprite = this.estaticoD1; 
		if(this.numeroSprite == 2)
			sprite = this.estaticoD2; 
		break;
	case "estaticoIzquierda" : 
		if(this.numeroSprite == 1)
			sprite = this.estaticoI1; 
		if(this.numeroSprite == 2)
			sprite = this.estaticoI2; 
		break;	
	}
	return sprite;
}
		
		
		
		@Override
		public void setColisionOn(boolean colisionOn) {
			this.colisionOn = colisionOn;
		}
		

}