package hangMan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Gallow extends JPanel{

	//Globals
	private Image dbImage;
	private Graphics dbg;
	public static Integer health = 4;
	Image gallow;

	public Gallow() {
		// Load Image assets
		ImageIcon sprite = new ImageIcon("img/hangman.jpg");
		gallow = sprite.getImage();
	}

	//Override Paint Controller
	@Override
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage, 0, 0, this);
	}

	public void paintComponent(Graphics g) {
		g.drawString("HANGMAN - By: Davis Amyot", 120, 90);
		g.drawImage(gallow, 100, 100, 249, 335, this);

		g.setColor(Color.white); //Set Cover Color
		if (health==0){//default
			g.fillRect(175, 140, 65, 70);//Cover Head
			g.fillRect(180, 210, 56, 95);//Cover Body
			g.fillRect(243, 210, 25, 95);//Cover Arm 2
			g.fillRect(147, 210, 25, 95);//Cover Arm 1
			g.fillRect(180, 305, 25, 95);//Cover Leg 2
			g.fillRect(210, 305, 26, 95);//Cover Leg 1
		}else if(health==1){//show head
			g.fillRect(180, 210, 56, 95);//Cover Body
			g.fillRect(243, 210, 25, 95);//Cover Arm 2
			g.fillRect(147, 210, 25, 95);//Cover Arm 1
			g.fillRect(180, 305, 25, 95);//Cover Leg 2
			g.fillRect(210, 305, 26, 95);//Cover Leg 1
		}else if(health==2){//show body
			g.fillRect(243, 210, 25, 95);//Cover Arm 2
			g.fillRect(147, 210, 25, 95);//Cover Arm 1
			g.fillRect(180, 305, 25, 95);//Cover Leg 2
			g.fillRect(210, 305, 26, 95);//Cover Leg 1
		}else if(health==3){//show arm 2
			g.fillRect(147, 210, 25, 95);//Cover Arm 1
			g.fillRect(180, 305, 25, 95);//Cover Leg 2
			g.fillRect(210, 305, 26, 95);//Cover Leg 1
		}else if(health==4){//show arm 1
			g.fillRect(180, 305, 25, 95);//Cover Leg 2
			g.fillRect(210, 305, 26, 95);//Cover Leg 1
		}else if(health==5){//show leg 2
			g.fillRect(210, 305, 26, 95);//Cover Leg 1
		}else{

		}
		//repaint(); // Repaints canvas !important
	}

}
