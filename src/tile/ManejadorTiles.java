package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;

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
		try {
			arregloTiles[0] = new Tile();
			arregloTiles[0].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/agua.png")));
			arregloTiles[0].setColision(true);
			
			arregloTiles[1] = new Tile();
			arregloTiles[1].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/arbol.png")));
			arregloTiles[1].setColision(true);
			
			arregloTiles[2] = new Tile();
			arregloTiles[2].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/arena.png")));
			
			arregloTiles[3] = new Tile();
			arregloTiles[3].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/muro.png")));
			arregloTiles[3].setColision(true);
			
			arregloTiles[4] = new Tile();
			arregloTiles[4].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/pasto.png")));
			
			arregloTiles[5] = new Tile();
			arregloTiles[5].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/suelo.png")));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int renMundo = 0, colMundo = 0;
		
		while(renMundo < this.gP.maxRenMundo && colMundo < this.gP.maxColMundo) {
			
			int numTile = codigosMapaTiles[renMundo][colMundo];
			
			int mundoX = colMundo * gP.getTamanioTile();
			int mundoY = renMundo * gP.getTamanioTile();
			
			int pantallaX = mundoX -gP.getJugador().getX() + gP.getJugador().getPantallaX();
			int pantallaY = mundoY - gP.getJugador().getY() + gP.getJugador().getPantallaY();
			
			
			//stop screen camera at the end
			
			if(gP.getJugador().getPantallaX() > gP.getJugador().getX()) {
				pantallaX = mundoX;
			}
			
			if(gP.getJugador().getPantallaY() > gP.getJugador().getY()) {
				pantallaY = mundoY;
			}
			
			
			int rOffs = gP.getAnchoPantalla() - gP.getJugador().getPantallaX();
			if(rOffs > gP.anchoMundo - gP.getJugador().getX()) {
				pantallaX = gP.getAnchoPantalla() - (gP.anchoMundo - mundoX);
 			}
			
			
			int bottomOffs = gP.getAltoPantalla() - gP.getJugador().getPantallaY();
			if(bottomOffs > gP.altoMundo - gP.getJugador().getY()) {
				pantallaY = gP.getAltoPantalla() - (gP.altoMundo - mundoY);
			}
			
			
			if(mundoX + gP.getTamanioTile() > gP.getJugador().getX() - gP.getJugador().getPantallaX() &&
			   mundoX - gP.getTamanioTile() < gP.getJugador().getX() + gP.getJugador().getPantallaX() &&
			   mundoY + gP.getTamanioTile() > gP.getJugador().getY() - gP.getJugador().getPantallaY() &&
			   mundoY - gP.getTamanioTile() < gP.getJugador().getY() + gP.getJugador().getPantallaY()) {
				
				g2.drawImage(this.arregloTiles[numTile].getImagen(), pantallaX, pantallaY, this.gP.getTamanioTile(), this.gP.getTamanioTile(), null);
				
			}else {
				if(gP.getJugador().getPantallaX() > gP.getJugador().getX() ||
						gP.getJugador().getPantallaY() > gP.getJugador().getY() ||
						rOffs > gP.anchoMundo - gP.getJugador().getX() ||
						bottomOffs > gP.altoMundo - gP.getJugador().getY()) {
					
					g2.drawImage(this.arregloTiles[numTile].getImagen(), pantallaX, pantallaY, this.gP.getTamanioTile(), this.gP.getTamanioTile(), null);
					}
				}
			
			colMundo++;
	
			
			if(colMundo == this.gP.maxColMundo) {
				colMundo = 0;
				renMundo++;
			}
		}
	}
	
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