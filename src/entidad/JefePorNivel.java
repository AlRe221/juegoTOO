package entidad;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import Main.GamePanel;

public class JefePorNivel extends Entidad{
	int id_Nivel;  
	boolean activa_combate;
	String nombre;
    String ubicacion;

    // Atributos para el disparo de proyectiles
    private long tiempoUltimoDisparoJefe = 0;
    private final long COOLDOWN_DISPARO_JEFE = 2000; // Disparar cada 2 segundos (ajustable)

    public JefePorNivel(GamePanel gp, int id, String nombre, String ubicacion, String spritePath1, String spritePath2) {
        super(gp);
        this.id_Nivel = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        
        this.activa_combate = false;
        this.direccion = "estatico";
        this.solidArea = new Rectangle(8, 16, 32, 32);
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        
        getImage(spritePath1, spritePath2);
        this.tipoE = 4;
    }
    
    public void getImage(String path1, String path2) {
        estatico1 = setup1(path1);
        estatico2 = setup1(path2);
    }
    
    public String getLocation() {
        return this.ubicacion;
    }

	@Override
	public void setColisionOn(boolean colisionOn) {
		this.colisionOn = colisionOn;
		
	}
	
	public void update() {
		 contadorSprites();
		 gP.getchecadorColision().checkJugador(this);
		 
	}
	
	
	public void draw(Graphics2D g2) {
		int pantallaX = mundoX -gP.getJugador().getMundoX() + gP.getJugador().getPantallaX();
		int pantallaY = mundoY - gP.getJugador().getMundoY() + gP.getJugador().getPantallaY();
		
	   colocarObjeto(pantallaX, pantallaY,g2,gP);
	     
	}
	
	public void drawEnCombate(Graphics2D g2, int x, int y, int ancho, int largo) {
		BufferedImage sprite = direcciones(); 
		
		if(sprite != null) {
			g2.drawImage(sprite, x, y, ancho, largo, null);
		}
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
		
		switch(this.direccion) {
		case "estatico" : 
			if(this.numeroSprite == 1)
				sprite = this.estatico1; 
			if(this.numeroSprite == 2)
				sprite = this.estatico2; 
			break;
		}
		
		return sprite;
	}
	
	
	
	
	
	public int getIdNivel() {
		return this.id_Nivel;
	}
	
	public void setIdNivel(int v) {
		this.id_Nivel = v;
	}
	
	
	
	public boolean getActivaDesactivaCombate() {
		return this.activa_combate;
	}

	public void setActivaDesactivaCombate(boolean b) {
		this.activa_combate =  b;
		
	}


}
