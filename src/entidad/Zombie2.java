package entidad;

import java.util.Random;

import Main.GamePanel;

public class Zombie2 extends Zombie{
	public Zombie2(String n,GamePanel gP) {
		super(n, gP);	
		getImage();
	}

	public void getImage() {
		abajo1 = setup1("/Zombie2/infectado2Abajo1");
		abajo2 = setup1("/Zombie2/infectado2Abajo2");
		
		arriba1 = setup1("/Zombie2/infectado2Arriba1");
		arriba2 = setup1("/Zombie2/infectado2Arriba2");
		
		derecha1 = setup1("/Zombie2/infectado2CamD1");
		derecha2 = setup1("/Zombie2/infectado2EstaticoD1");
		
		izquierda1 = setup1("/Zombie2/infectado2CamIz1");
		izquierda2 = setup1("/Zombie2/infectado2EstaticoIz1");
	}
	
	
		
	

}
