package entidad;

import java.util.Random;

import Main.GamePanel;

public class Zombie1 extends Zombie{


	public Zombie1(String n,GamePanel gP) {
		super(n, gP);	
		getImage();
	}

	public void getImage() {
		abajo1 = setup1("/Zombie1/infectadoAbajo1");
		abajo2 = setup1("/Zombie1/infectadoAbajo2");
		
		arriba1 = setup1("/Zombie1/infectadoArriba1");
		arriba2 = setup1("/Zombie1/infectadoArriba2");
		
		derecha1 = setup1("/Zombie1/infectadoCamD1");
		derecha2 = setup1("/Zombie1/infectadoEstaticoD1");
		
		izquierda1 = setup1("/Zombie1/infectadoCamI1");
		izquierda2 = setup1("/Zombie1/infectadoEstaticoIz1");
	}
	
	
}
