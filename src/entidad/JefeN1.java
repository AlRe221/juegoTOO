package entidad;

import Main.GamePanel;

public class JefeN1 extends JefePorNivel{

	public JefeN1(GamePanel gp) {
		super(gp);
		this.id_Nivel =1;
		getImage();
	}
	
	
	public void getImage() {
		estatico1 = setup1("/JefesPorNivel/miguelito1");
		estatico2 = setup1("/JefesPorNivel/miguelito2");
	}

}
