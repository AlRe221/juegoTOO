package entidad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import Main.GamePanel;

public class Zombie extends Entidad{
	String nombre;
	private final int radioDeteccion = 200;
	private boolean persiguiendo = false;
	private char currentAxis  = 'x'; 
	

	public Zombie(GamePanel gP, String nombreInstancia, int tipoZombieKey, double vidaMaxima, int velocidad) {
		super(gP);
		this.tipoE = 1;
		this.nombre = nombreInstancia; 
		this.velocidad = velocidad;
        this.vidaMaxima = vidaMaxima;
        this.vida = this.vidaMaxima;
        this.direccion = "abajo";
        
		this.solidArea = new Rectangle(8,16,32,32); 
		this.solidAreaDefaultX = this.solidArea.x;
		this.solidAreaDefaultY = this.solidArea.y;
		
		getZombieSprites(tipoZombieKey);
		
	}
	
		public void setAction() {
	
		    int px = gP.getJugador().getMundoX();
		    int py = gP.getJugador().getMundoY();
		    int dx = px - this.mundoX;
		    int dy = py - this.mundoY;
		    double dist = Math.hypot(dx, dy);
		    
		    // Persigue jugador
		    if (dist < radioDeteccion) {
	
		        if (!persiguiendo) {
		            persiguiendo = true;
		            currentAxis  = (Math.abs(dx) >= Math.abs(dy)) ? 'x' : 'y';
		        }
	
		        // Cambiar de eje 
		        boolean alineadoEnX = Math.abs(dx) < velocidad; 
		        boolean alineadoEnY = Math.abs(dy) < velocidad; 

		        
		        if (currentAxis == 'x' && alineadoEnX && !alineadoEnY) {
		            currentAxis = 'y';
		        } else if (currentAxis == 'y' && alineadoEnY && !alineadoEnX) {
		            currentAxis = 'x';
		        }

		        // Establecer dirección basada en el eje actual
		        if (currentAxis == 'x' && !alineadoEnX) {
		            direccion = (dx > 0) ? "derecha" : "izquierda";
		        } else if (currentAxis == 'y' && !alineadoEnY) {
		            direccion = (dy > 0) ? "abajo" : "arriba";
		        }

		        return;
		    }
	
		    // Patrulla
		    persiguiendo = false;
		    actionLockCounter++;
		    if (actionLockCounter >= 120) {
		        switch (new Random().nextInt(4)) {
		            case 0 -> direccion = "arriba";
		            case 1 -> direccion = "abajo";
		            case 2 -> direccion = "izquierda";
		            default-> direccion = "derecha";
		        }
		        actionLockCounter = 0;
		    }
		}

		public void getZombieSprites(int tipoID) {
	        String carpeta = "/Zombie" + tipoID + "/infectado"; 	        	        	       
	            abajo1 = setup1(carpeta + "Abajo1");
	            abajo2 = setup1(carpeta + "Abajo2");
	            arriba1 = setup1(carpeta + "Arriba1");
	            arriba2 = setup1(carpeta + "Arriba2");
	            derecha1 = setup1(carpeta + "CamD1");
                derecha2 = setup1(carpeta + "EstaticoD1"); 
                izquierda1 = setup1(carpeta + "CamIz1");
                izquierda2 = setup1(carpeta + "EstaticoIz1"); 	           
	    }

		
		int dañototal = 60; 
		int dañoContador = 0;
		
		public void update() {
			if (!getVivo()) { 
		        return;
		    }
			
		    // ---> AÑADIR LÓGICA PARA EL CONTADOR DEL FLASH <---
		    if (fueGolpeadoRecientemente) {
		        contadorFlashDaño--;
		        if (contadorFlashDaño <= 0) {
		            fueGolpeadoRecientemente = false;
		        }
		    }
		    // ---> FIN DE LÓGICA PARA EL CONTADOR DEL FLASH <---

			setAction();
			colisionOn = false;
			gP.getchecadorColision().checkTile(this);
			boolean res = gP.getchecadorColision().checkJugador(this);
			
			reducirVidaPocoApoco(res);
			colisiones();
		    contadorSprites();
		}
		
		
		public void reducirVidaPocoApoco(boolean res) {
			if (this.tipoE == 1 && res == true) {
			    if (dañoContador <= 0) {
			    	gP.playSE(5);
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
				
		
		
		public void draw(Graphics2D g2) {
			if (!getVivo()) { 
		        return;
		    }
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
				if (i != null) {
		            g2.drawImage(i, x, y, gP.getTamanioTile(), gP.getTamanioTile(), null);
				
				// ---> AÑADIR PARA DIBUJAR EL FLASH ROJO <---
	            if (fueGolpeadoRecientemente) {
	                // Guardar la composición original
	                java.awt.Composite originalComposite = g2.getComposite();
	                // Aplicar transparencia
	                g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.5f)); // 50% de opacidad
	                g2.setColor(Color.RED);
	                g2.fillRect(x, y, gP.getTamanioTile(), gP.getTamanioTile());
	                // Restaurar la composición original para no afectar otros dibujos
	                g2.setComposite(originalComposite);
	            }            
	            // ---> FIN DE DIBUJAR EL FLASH ROJO <---
				}            
				
			}else {
				if(gP.getJugador().getMundoX() < gP.getJugador().getPantallaX() ||
						gP.getJugador().getMundoY() < gP.getJugador().getPantallaY() ||
						rOffs > gP.getAnchoMundo() - gP.getJugador().getMundoX() ||
						bOffs > gP.getAltoMundo() - gP.getJugador().getMundoY()) {
						i = direcciones();
			            if (i != null) {
			                g2.drawImage(i, x, y, gP.getTamanioTile(), gP.getTamanioTile(), null);
			                // ---> AÑADIR PARA DIBUJAR EL FLASH ROJO (también aquí) <---
			                if (fueGolpeadoRecientemente) {
			                    java.awt.Composite originalComposite = g2.getComposite();
			                    g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.5f));
			                    g2.setColor(Color.RED);
			                    g2.fillRect(x, y, gP.getTamanioTile(), gP.getTamanioTile());
			                    g2.setComposite(originalComposite);
			                }
			                // ---> FIN DE DIBUJAR EL FLASH ROJO <---
			            }
						
						
					}
			}
			
	    }
		
public BufferedImage direcciones() {
	BufferedImage sprite = null;
	switch(this.direccion) {
		case "arriba" : sprite = (this.numeroSprite == 1) ? this.arriba1 : this.arriba2; break;
	    case "abajo" : sprite = (this.numeroSprite == 1) ? this.abajo1 : this.abajo2; break;
	    case "izquierda" : sprite = (this.numeroSprite == 1) ? this.izquierda1 : this.izquierda2; break;
	    case "derecha" : sprite = (this.numeroSprite == 1) ? this.derecha1 : this.derecha2; break;
	    case "estatico" : sprite = (this.numeroSprite == 1) ? this.estatico1 : this.estatico2; break;
	    case "estaticoArriba" : sprite = (this.numeroSprite == 1) ? this.estaticoA1 : this.estaticoA2; break;	
	    case "estaticoDerecha" : sprite = (this.numeroSprite == 1) ? this.estaticoD1 : this.estaticoD2; break;
	    case "estaticoIzquierda" : sprite = (this.numeroSprite == 1) ? this.estaticoI1 : this.estaticoI2; break;
	}
	return sprite;
}
		
		
		
		@Override
		public void setColisionOn(boolean colisionOn) {
			this.colisionOn = colisionOn;
		}
		

}