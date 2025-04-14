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
		this.codigosMapaTiles = new int[gP.getMaxRenPantalla()]
								[gP.getMaxColPantalla()];
		getImagenesTile();
		cargaMapa();
	}
	public void cargaMapa(){
		try {
			InputStream mapa = getClass().getResourceAsStream("/mapas/mapa01.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(mapa));
			int ren = 0, col = 0;
			while(ren < gP.getMaxRenPantalla() && col < gP.getMaxColPantalla()) {
				String renglonDatos = br.readLine();
				while(col < gP.getMaxColPantalla()) {
					String[] codigos = renglonDatos.split(" ");
					int codigo = Integer.parseInt(codigos[col]);
					this.codigosMapaTiles[ren][col] = codigo;
					col++;
				}
				if(col == gP.getMaxColPantalla()) {
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
			arregloTiles[1] = new Tile();
			arregloTiles[1].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/arbol.png")));
			arregloTiles[2] = new Tile();
			arregloTiles[2].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/arena.png")));
			arregloTiles[3] = new Tile();
			arregloTiles[3].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/muro.png")));
			arregloTiles[4] = new Tile();
			arregloTiles[4].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/pasto.png")));
			arregloTiles[5] = new Tile();
			arregloTiles[5].setImagen(ImageIO.read(getClass().getResourceAsStream("/tiles/suelo.png")));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g2) {
		int ren = 0, col = 0;
		int x = 0, y = 0;
		while(ren < this.gP.getMaxRenPantalla() && col < this.gP.getMaxColPantalla()) {
			g2.drawImage(this.arregloTiles[this.codigosMapaTiles[ren][col]].getImagen(), x, y, this.gP.getTamanioTile(), this.gP.getTamanioTile(), null);
			col++;
			x += this.gP.getTamanioTile();
			if(col == this.gP.getMaxColPantalla()) {
				col = 0;
				x = 0;
				ren++;
				y += this.gP.getTamanioTile();
			}
		}
	}
}
