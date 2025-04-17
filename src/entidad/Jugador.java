package entidad;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.ManejadorTeclas;

public class Jugador extends Entidad
{
	private GamePanel gP;
	private ManejadorTeclas mT;
	private final int pantallaX, pantallaY;
	
	public Jugador(GamePanel gP, ManejadorTeclas mT)
	{
		this.gP = gP;
		this.mT = mT;
		
		this.pantallaX = gP.getAnchoPantalla() / 2 - (gP.getTamanioTile()/2);
		this.pantallaY = gP.getAltoPantalla() / 2 - (gP.getTamanioTile()/2);
		
		this.solidArea = new Rectangle(8,16,32,32); 
		
		configuracionInicial();
		getSpritesJugador();
	}
	public void configuracionInicial()
	{
		this.mundoX = gP.getTamanioTile() * 23;
		this.mundoY = gP.getTamanioTile() * 21;
		this.velocidad = 4;
		this.direccion = "abajo";
	}
	public void getSpritesJugador()
	{
		try {
			this.estatico1 =ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstatico1.png"));
			this.estatico2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstatico2.png"));
			
			this.arriba1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoArriba1.png"));
			this.arriba2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoArriba2.png"));
			
			this.estaticoA1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstaticoA1.png"));
			this.estaticoA2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoEstaticoA2.png"));
			
			this.abajo1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoAbajo1.png"));
			this.abajo2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/pacoAbajo2.png"));
			
			this.izquierda1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverIzquierda1.png"));
			this.izquierda2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverIzquierda2.png"));
			this.derecha1 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverDerecha1.png"));
			this.derecha2 = ImageIO.read(getClass().getResourceAsStream("/spritesjugador/moverDerecha2.png"));
		}catch(IOException e)
		{
			System.out.println(e);
		}
	}
	
	
	public void update() {
	    boolean moviendo = false;

	    if (mT.getTeclaArriba()) {
	        this.direccion = "arriba";
	        moviendo = true;
	    } else if (mT.getTeclaAbajo()) {
	        this.direccion = "abajo";
	        moviendo = true;
	    } else if (mT.getTeclaIzquierda()) {
	        this.direccion = "izquierda";
	        moviendo = true;
	    } else if (mT.getTeclaDerecha()) {
	        this.direccion = "derecha";
	        moviendo = true;
	    }

	    // Si no se está moviendo, aplicar estático dependiendo de la última dirección
	    if (!moviendo) {
	        if (this.direccion.equals("arriba")) {
	            this.direccion = "estaticoArriba";
	        } else if (this.direccion.equals("abajo")) {
	            this.direccion = "estatico";
	        } // Puedes agregar también estaticoIzquierda y estaticoDerecha si quieres
	    }
	    
	    
	    //revisa coli con tiles
	    this.colisionOn = false; 
	    gP.getchecadorColision().checkTile(this);
	    
	    
	    //si no hubo colisión
	    if(colisionOn == false) {
	    	switch(direccion) {
	    	case "arriba":
	    		 setY(getY() - getVelocidad());
	    		break; 
	    	case "abajo" : 
	    		setY(getY() + getVelocidad());
	    		break;
	    	case "izquierda" : 
	    		setX(getX() - getVelocidad());
	    		break; 
	    	case "derecha" :
	    		setX(getX() + getVelocidad()); 
	    		break; 
	    	}
	    }

	    this.contadorSprites++;
	    
	    if (this.contadorSprites > this.cambiaSprite) {
	        if (this.numeroSprite == 1)
	            this.numeroSprite = 2;
	        else
	            this.numeroSprite = 1;
	        this.contadorSprites = 0;
	    }
	}

	
	/*public void update()
	{
	   //aquí se hizo la modicación para que se mueva estando estatico, si no les gusta, pueden: 
		//borrar las variables de estatico (tanto en getSprite como aqui como en draw y de la clase entidad)
		//implementar el if que esta en comentarios 
		//meter todo el codigo restante dentro de este if. 
		/*if(mT.getTeclaArriba() == true || mT.getTeclaAbajo() == true ||
			mT.getTeclaIzquierda() == true || mT.getTeclaDerecha() == true) {
			this.contadorSprites++;
			}
		
		this.direccion = "estatico";
		if(mT.getTeclaArriba())
		{
			setY(getY() - getVelocidad());
			this.direccion = "arriba";
		} else 
			if(mT.getTeclaAbajo())
			{
				setY(getY() + getVelocidad());
				this.direccion = "abajo";
			}else 
				if(mT.getTeclaIzquierda())
				{
					setX(getX() - getVelocidad());
					this.direccion = "izquierda";
				}else 
					if(mT.getTeclaDerecha())
					{
						setX(getX() + getVelocidad());
						this.direccion = "derecha";
					}
			
			this.contadorSprites++;
		 
				if(this.contadorSprites > this.cambiaSprite)
				{
					if(this.numeroSprite == 1)
						this.numeroSprite = 2;
					else
						this.numeroSprite = 1;
					this.contadorSprites = 0;
				}
		}*/		
	
	
	public void draw(Graphics2D g2)
	{
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
		}	
		g2.drawImage(sprite, this.pantallaX, this.pantallaY, gP.getTamanioTile(), gP.getTamanioTile(),null);
	}
	
	
	public int getX()
	{
		return this.mundoX;
	}
	public int getY()
	{
		return this.mundoY;
	}
	public int getVelocidad()
	{
		return this.velocidad;
	}
	public void setX(int valor)
	{
		this.mundoX = valor;
	}
	public void setY(int valor)
	{
		this.mundoY = valor;
	}
	
	
	public int getPantallaX() {
		return pantallaX;
	}
	public int getPantallaY() {
		return pantallaY;
	}
	
	
	public int getAreaSolidaX() {
		return this.solidArea.x;
	}
	
	public int getAreaSolidaY() {
		return this.solidArea.y;
	}
	
	public int getAreaSolidaWidth() {
		return this.solidArea.width;
	}
	
	public int getAreaSolidaHeigth() {
		return this.solidArea.height;
	}
	
	public String getDireccion() {
		return this.direccion;
	}
	
}
