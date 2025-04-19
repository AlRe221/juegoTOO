package Main;

import java.util.List;

import Inventario.Arma;
import Inventario.Coins;
import Inventario.Comida;
import Inventario.Objeto;
import java.util.Random;

public class AssetSetter {
	private GamePanel gP;
	
	public AssetSetter(GamePanel gP) {
		this.gP = gP;
	}
	
	public void setObject() {
		Random rand = new Random();
		for (int i = 0; i < gP.o.length; i++) {
			int tipo = rand.nextInt(3); // 0: Coins, 1: Arma, 2: Comida

			switch (tipo) {
				case 0:
					gP.o[i] = new Coins(10.0, 5, 6.0, true, 6.0);
					break;
				case 1:
					gP.o[i] = new Arma("espada", 5, 6.0, true, 6.0);
					break;
				case 2:
					gP.o[i] = new Comida(5.0, true, 7.5);
					break;
			}

			// posición aleatoria
			int x = rand.nextInt(50); // suponiendo 50 tiles en X
			int y = rand.nextInt(30); // suponiendo 30 tiles en Y
			gP.o[i].setWorldX(x * gP.getTamanioTile());
			gP.o[i].setWorldY(y * gP.getTamanioTile());
		}
	}



}
