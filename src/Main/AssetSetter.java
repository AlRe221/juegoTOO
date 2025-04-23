package Main;

import java.util.List;

import Inventario.Arma;
import Inventario.Celular;
import Inventario.Coins;
import Inventario.Comida;
import Inventario.ItemVelocidad;
import Inventario.Laptop;
import Inventario.Mochila;
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
			int tipo = rand.nextInt(6); // 0: Coins,, 1: Comida, 2: itemVelocidad, 3. Mochila (arma), 4. Laptop(arma), 5.Celular(arma).

			switch (tipo) {
				case 0:
					gP.o[i] = new Coins(10.0, 5, 6.0, true, 6.0);
					break;
				case 1:
					gP.o[i] = new Comida(5.0, true, 7.5);
					break;
				case 2:
					gP.o[i] = new ItemVelocidad(5.0, true, 6.0);
					break;
				case 3:
					gP.o[i] = new Mochila(3, "Mochila", 3.0, true, 6.0);
					break;
				case 4: 
					gP.o[i] = new Laptop(4,"Laptop",10.0,true,12.5);
					break;
				case 5 :
					gP.o[i] = new Celular(4,"Celular",8.5,true,10.0);
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
