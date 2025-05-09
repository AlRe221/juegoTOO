package entidad;

import java.util.Random;

import Main.GamePanel;

public class Zombie3 extends Zombie{
	public Zombie3(String n,GamePanel gP) {
		super(n, gP);	
		getImage();
	}

	public void getImage() {
		abajo1 = setup1("/Zombie3/infectado3Abajo1");
		abajo2 = setup1("/Zombie3/infectado3Abajo2");
		
		arriba1 = setup1("/Zombie3/infectado3Arriba1");
		arriba2 = setup1("/Zombie3/infectado3Arriba2");
		
		derecha1 = setup1("/Zombie3/infectado3CamD1");
		derecha2 = setup1("/Zombie3/infectado3D1");
		
		izquierda1 = setup1("/Zombie3/infectado3CamIz1");
		izquierda2 = setup1("/Zombie3/infectado3Iz1");
	}
	

}
