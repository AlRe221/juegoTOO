package Main;

import java.util.List;

import Inventario.Alimento;
import Inventario.Arma;
import Inventario.Bebida;
import Inventario.Celular;
import Inventario.Coins;
import Inventario.Comida;
import Inventario.Extintor;
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
			int tipo = rand.nextInt(8); // 0: Coins, 1: Alimento (comida), 2. Bebida (comida), 3: itemVelocidad, 4. Mochila (arma), 5. Laptop(arma), 6.Celular(arma), 7.Extintor(arma).

			switch (tipo) {
				case 0:
					gP.o[i] = new Coins(10.0, 5, 6.0, true, 6.0);
					break;
				case 1:
					gP.o[i] = new Alimento(5.0, true, 7.5, "Torta Chilaquil");
					break;
				case 2 :
					gP.o[i] = new Bebida(10.0, true, 6.0, "Aguita");
					break;	
				case 3:
					gP.o[i] = new ItemVelocidad(5.0, true, 6.0);
					break;
				case 4:
					gP.o[i] = new Mochila(3, "Mochila", 3.0, true, 6.0);
					break;
				case 5: 
					gP.o[i] = new Laptop(4,"Laptop",10.0,true,12.5);
					break;
				case 6 :
					gP.o[i] = new Celular(4,"Celular",8.5,true,10.0);
					break;
				case 7 :
					gP.o[i] = new Extintor(5, "Extintor", 7.0, true, 8.0);
					break;
					
			}

			// posición aleatoria
			int x,y,tilen;
			do { //esto es para que los objetos no salgan en espacios donde el perosnaje no puede pasar por colisión
			x = rand.nextInt(50); // suponiendo 50 tiles en X
			y = rand.nextInt(30); // suponiendo 30 tiles en Y
			
			tilen = gP.mTi.getCodigoMapaTiles(y,x);
			
			}while(gP.mTi.getColisionDeTile(tilen) == true);
			
			gP.o[i].setWorldX(x * gP.getTamanioTile());
			gP.o[i].setWorldY(y * gP.getTamanioTile());
		}
	}



}
