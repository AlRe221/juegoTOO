package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.ImagenesEscaladas;

public class ManejadorTiles {
	
	private GamePanel gP;
	private int maxTiles = 10;
	Tile[] arregloTiles;
	private int codigosMapaTiles[][];
	
	public ManejadorTiles(GamePanel gP) {
		this.gP = gP;
		this.arregloTiles = new Tile[maxTiles];
		this.codigosMapaTiles = new int[gP.maxRenMundo]
								[gP.maxRenMundo];
		getImagenesTile();
		cargaMapa("/mapas/world02.txt");
	}
	public void cargaMapa(String rutaMapa){
		try {
			InputStream mapa = getClass().getResourceAsStream(rutaMapa);
			BufferedReader br = new BufferedReader(new InputStreamReader(mapa));
			
			int ren = 0, col = 0;
			
			while(ren < gP.maxRenMundo && col < gP.maxColMundo) {
				String renglonDatos = br.readLine();
				while(col < gP.maxColMundo) {
					
					String[] codigos = renglonDatos.split(" ");
					
					int codigo = Integer.parseInt(codigos[col]);
					
					this.codigosMapaTiles[ren][col] = codigo;
					
					col++;
				}
				if(col == gP.maxColMundo) {
					ren++;
					col = 0;
				}
			}
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getImagenesTile() {
		
		setup(0,"agua",true);
		setup(1,"arbol",true);
		setup(2,"arena",false);
		setup(3,"muro",true);
		setup(4,"pasto",false);
		setup(5,"suelo",false);
	}
	
	public void setup(int index, String nombreImagen, boolean colision) {
		ImagenesEscaladas iE = new ImagenesEscaladas();
		try {
			arregloTiles[index] = new Tile(); 
			arregloTiles[index].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/" + nombreImagen + ".png")));
			arregloTiles[index].setImagen(iE.scaleImage(arregloTiles[index].getImagen(), gP.getTamanioTile(), gP.getTamanioTile()));
			arregloTiles[index].setColision(colision);
		}catch(IOException e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		int renMundo = 0, colMundo = 0;
		
		while(renMundo < this.gP.maxRenMundo && colMundo < this.gP.maxColMundo) {
			
			int numTile = codigosMapaTiles[renMundo][colMundo];
			
			int mundoX = colMundo * gP.getTamanioTile();
			int mundoY = renMundo * gP.getTamanioTile();
			
			int pantallaX = mundoX - gP.getJugador().getMundoX() + gP.getJugador().getPantallaX();
			int pantallaY = mundoY - gP.getJugador().getMundoY() + gP.getJugador().getPantallaY();
			
			
			//stop screen camera at the end
			
			if(gP.getJugador().getPantallaX() > gP.getJugador().getMundoX()) {
				pantallaX = mundoX;
			}
			
			if(gP.getJugador().getPantallaY() > gP.getJugador().getMundoY()) {
				pantallaY = mundoY;
			}
			
			
			int rOffs = gP.getAnchoPantalla() - gP.getJugador().getPantallaX();
			if(rOffs > gP.anchoMundo - gP.getJugador().getMundoX()) {
				pantallaX = gP.getAnchoPantalla() - (gP.anchoMundo - mundoX);
 			}
			
			
			int bottomOffs = gP.getAltoPantalla() - gP.getJugador().getPantallaY();
			if(bottomOffs > gP.altoMundo - gP.getJugador().getMundoY()) {
				pantallaY = gP.getAltoPantalla() - (gP.altoMundo - mundoY);
			}
			
			
			
			if(mundoX + gP.getTamanioTile() > gP.getJugador().getMundoX() - gP.getJugador().getPantallaX() &&
			   mundoX - gP.getTamanioTile() < gP.getJugador().getMundoX() + gP.getJugador().getPantallaX() &&
			   mundoY + gP.getTamanioTile() > gP.getJugador().getMundoY() - gP.getJugador().getPantallaY() &&
			   mundoY - gP.getTamanioTile() < gP.getJugador().getMundoY() + gP.getJugador().getPantallaY()) {
				
				g2.drawImage(this.arregloTiles[numTile].getImagen(), pantallaX, pantallaY, null);
				
			}else {
				if(gP.getJugador().getPantallaX() > gP.getJugador().getMundoX() ||
						gP.getJugador().getPantallaY() > gP.getJugador().getMundoY() ||
						rOffs > gP.anchoMundo - gP.getJugador().getMundoX() ||
						bottomOffs > gP.altoMundo - gP.getJugador().getMundoY()) {
					
					g2.drawImage(this.arregloTiles[numTile].getImagen(), pantallaX, pantallaY, null);
					}
				}
			
			colMundo++;
	
			
			if(colMundo == this.gP.maxColMundo) {
				colMundo = 0;
				renMundo++;
			}
		}
	}
	
	
	//este no pone las lineas, pero se come el mapa.
	/*public void draw(Graphics2D g2) {
		int tileSize = gP.getTamanioTile();
		int pantallaAncho = gP.getAnchoPantalla();
		int pantallaAlto = gP.getAltoPantalla();
		int jugadorX = gP.getJugador().getX();
		int jugadorY = gP.getJugador().getY();
		int pantallaJugadorX = gP.getJugador().getPantallaX();
		int pantallaJugadorY = gP.getJugador().getPantallaY();

		int mundoX=0;
		int mundoY=0;
		int pantallaX=0;
		int pantallaY=0;
		int rOffs=0; 
		int bOffs=0;
		int numTile=0;
		
		for (int ren = 0; ren < gP.maxRenMundo; ren++) {
			for (int col = 0; col < gP.maxColMundo; col++) {
				
				 mundoX = col * tileSize;
				 mundoY = ren * tileSize;

				// Solo dibujar si está dentro de la zona visible
				if (
					mundoX + tileSize > jugadorX - pantallaJugadorX &&
					mundoX - tileSize < jugadorX + pantallaJugadorX &&
					mundoY + tileSize > jugadorY - pantallaJugadorY &&
					mundoY - tileSize < jugadorY + pantallaJugadorY
				) {
					// Calculamos la posición en pantalla
					pantallaX = mundoX - jugadorX + pantallaJugadorX;
					pantallaY = mundoY - jugadorY + pantallaJugadorY;

					// Correcciones de bordes
					if (pantallaJugadorX > jugadorX) pantallaX = mundoX;
					if (pantallaJugadorY > jugadorY) pantallaY = mundoY;

					rOffs = pantallaAncho - pantallaJugadorX;
					if (rOffs > gP.anchoMundo - jugadorX) {
						pantallaX = pantallaAncho - (gP.anchoMundo - mundoX);
					}

					bOffs = pantallaAlto - pantallaJugadorY;
					if (bOffs > gP.altoMundo - jugadorY) {
						pantallaY = pantallaAlto - (gP.altoMundo - mundoY);
					}

					numTile = codigosMapaTiles[ren][col];
					g2.drawImage(arregloTiles[numTile].getImagen(), pantallaX, pantallaY, tileSize, tileSize, null);
				}else {
					if(gP.getJugador().getPantallaX() > gP.getJugador().getX() ||
						gP.getJugador().getPantallaY() > gP.getJugador().getY() ||
						rOffs > gP.anchoMundo - gP.getJugador().getX() ||
						bOffs > gP.altoMundo - gP.getJugador().getY()) {
					
					g2.drawImage(this.arregloTiles[numTile].getImagen(), pantallaX, pantallaY, this.gP.getTamanioTile(), this.gP.getTamanioTile(), null);
					}
				} 
			}
		}
	}*/

	
	
	
	
	public int getCodigoMapaTiles(int ren, int col) {
		return this.codigosMapaTiles[ren][col];
		}
	
	
	public boolean getColisionDeTile(int index) {
		return this.arregloTiles[index].getColision();
		}
	public int getMaxFilas() {
		return this.codigosMapaTiles.length;
	}
	
	
	
}