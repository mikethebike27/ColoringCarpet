//package ColoringCarpets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import java.util.Random;
import java.util.ArrayList;

public class Strand{
	private static final Color SWAMPYBLUE = new Color(173, 216, 230);//12
	private static final Color SKYBLUE = new Color(0, 191, 255);//19
	private static final Color MEDIUMBLUE = new Color(30, 144, 255);//26
	private static final Color BLUE = new Color(0, 0, 255);//1
	private static final Color DARKBLUE = new Color(0, 0, 139);//20
	private static final Color DARKPURPLEYBLUE = new Color(72, 61, 139);//13
	private static final Color LIGHTPURPLE = new Color(123, 104, 238);//27
	private static final Color SOLIDPURPLE = new Color(138, 43, 226);//5
	private static final Color VIOLET = new Color(128, 0, 128);//21
	private static final Color PINKISH = new Color(218, 112, 214);//7
	private static final Color HOTPINK = new Color(255, 0, 255);//14
	private static final Color REDISHPINK = new Color(255, 20, 147);//22
	private static final Color REDVELVET = new Color(176, 48, 96);//28
	private static final Color RED = new Color(255, 0, 0);//2
	private static final Color LIGHTREDISHPINK = new Color(240, 128, 128);//29
	private static final Color ORANGISH = new Color(255, 69, 0);//9
	private static final Color ORANGISHYELLOW = new Color(255, 165, 0);//30
	private static final Color PEACH = new Color(244, 164, 96);//15
	private static final Color PEE = new Color(240, 230, 140);//23
	private static final Color MUSTARD = new Color(128, 128, 0);//16
	private static final Color BROWN = new Color(139, 69, 19);//10
	private static final Color YELLOW = new Color( 255, 255, 0);//6
	private static final Color LIME = new Color(154, 205, 50);//11
	private static final Color LIGHTGREEN = new Color(124, 252, 0);//31
	private static final Color WEIRDGREEN = new Color(144, 238, 144 );//24
	private static final Color MUDDYGREEN = new Color(143, 188, 143);//32
	private static final Color GREEN = new Color(0, 255, 0);//3
	private static final Color DARKGREEN = new Color(34, 139, 34 );//17
	private static final Color HOTGREEN = new Color(0, 255, 127);//25
	private static final Color BLUELY = new Color(0, 255, 255 );//8
	private static final Color TEAL = new Color(0, 139, 139 );//33
	private static final Color DARKGRAY = new Color(90,90,90);//18
	private static final Color GRAY = new Color(190,190,190);//4
	private static final Color WHITE = new Color(255,255,255);//0



	public static final Color BACKGROUND_COLOR = Color.BLACK; //colors of all the objects avaible listed here
	public static final Color[] CARPET_COLOR1 = {Color.RED, Color.BLUE, Color.GREEN,};
	public static final Color[] CARPET_COLOR2 = {WHITE, BLUE, LIME ,Color.DARK_GRAY, Color.GRAY};
	public static final Color[] CARPET_COLOR_DIFF = {WHITE, BLUE, RED, GREEN, DARKGRAY, SOLIDPURPLE, PINKISH, BLUELY,
		BROWN, SWAMPYBLUE, MUSTARD, GRAY, DARKBLUE, VIOLET, DARKGREEN, LIGHTPURPLE, HOTPINK, SKYBLUE,
		REDISHPINK, PEE, WEIRDGREEN, DARKPURPLEYBLUE, HOTGREEN, MEDIUMBLUE, REDVELVET, LIGHTREDISHPINK, ORANGISHYELLOW, LIGHTGREEN,
		MUDDYGREEN,YELLOW, ORANGISH, LIME, PEACH, TEAL};

		/*public static final Color[] CARPET_COLOR_DIFFversus3 = {WHITE, WHITE, WHITE, BLUE, BLUE, BLUE, RED, RED, RED, GREEN, GREEN, GREEN, DARKGRAY, DARKGRAY, DARKGRAY,
		SOLIDPURPLE, SOLIDPURPLE, SOLIDPURPLE, PINKISH, PINKISH, PINKISH, BLUELY, BLUELY, BLUELY,
		BROWN, BROWN, BROWN, SWAMPYBLUE, SWAMPYBLUE, SWAMPYBLUE, MUSTARD, MUSTARD, MUSTARD, GRAY, DARKBLUE, VIOLET, DARKGREEN, LIGHTPURPLE, HOTPINK, SKYBLUE,
		REDISHPINK, PEE, WEIRDGREEN, DARKPURPLEYBLUE, HOTGREEN, MEDIUMBLUE, REDVELVET, LIGHTREDISHPINK, ORANGISHYELLOW, LIGHTGREEN,
		MUDDYGREEN,YELLOW, ORANGISH, LIME, PEACH, TEAL};*/

		public static final Color[] CARPET_COLOR_DIFFversus = {WHITE, WHITE, WHITE, WHITE, WHITE, BLUE, BLUE, BLUE, BLUE, BLUE,
		 RED, RED, RED, RED, RED, GREEN, GREEN, GREEN, DARKGRAY, DARKGRAY, DARKGRAY,
		SOLIDPURPLE, SOLIDPURPLE, SOLIDPURPLE, PINKISH, PINKISH, PINKISH, BLUELY, BLUELY, BLUELY,
		BROWN, BROWN, BROWN, SWAMPYBLUE, SWAMPYBLUE, SWAMPYBLUE, MUSTARD, MUSTARD, MUSTARD, GRAY, DARKBLUE, VIOLET, DARKGREEN, LIGHTPURPLE, HOTPINK, SKYBLUE,
		REDISHPINK, PEE, WEIRDGREEN, DARKPURPLEYBLUE, HOTGREEN, MEDIUMBLUE, REDVELVET, LIGHTREDISHPINK, ORANGISHYELLOW, LIGHTGREEN,
		MUDDYGREEN,YELLOW, ORANGISH, LIME, PEACH, TEAL};

		//public static final Color[] CARPET_COLOR_DIFFversus;

		/*public static final Color[] CARPET_COLOR_DIFF = {WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE, WHITE,
		 BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, BLUE, RED, RED, RED, RED, RED, RED, RED, RED, RED,
		 GREEN, GREEN, GREEN, DARKGRAY, DARKGRAY, DARKGRAY,
		SOLIDPURPLE, SOLIDPURPLE, SOLIDPURPLE, PINKISH, PINKISH, PINKISH, BLUELY, BLUELY, BLUELY,
		BROWN, BROWN, BROWN, SWAMPYBLUE, SWAMPYBLUE, SWAMPYBLUE, MUSTARD, MUSTARD, MUSTARD, GRAY, DARKBLUE, VIOLET, DARKGREEN, LIGHTPURPLE, HOTPINK, SKYBLUE,
		REDISHPINK, PEE, WEIRDGREEN, DARKPURPLEYBLUE, HOTGREEN, MEDIUMBLUE, REDVELVET, LIGHTREDISHPINK, ORANGISHYELLOW, LIGHTGREEN,
		MUDDYGREEN,YELLOW, ORANGISH, LIME, PEACH, TEAL};//

		public static final Color[] CARPET_COLOR_DIFF9 = {WHITE, TEAL, TEAL, GREEN, TEAL, TEAL, PINKISH, TEAL,
		TEAL, SWAMPYBLUE, MUSTARD, GRAY, DARKBLUE, VIOLET, DARKGREEN, LIGHTPURPLE, HOTPINK, SKYBLUE,
		REDISHPINK, PEE, WEIRDGREEN, DARKPURPLEYBLUE, HOTGREEN, MEDIUMBLUE, REDVELVET, LIGHTREDISHPINK, ORANGISHYELLOW, LIGHTGREEN,
		MUDDYGREEN,YELLOW, ORANGISH, LIME, PEACH, TEAL};

		public static final Color[] CARPET_COLOR_DIFF25 = {WHITE, TEAL, TEAL, TEAL, TEAL, GREEN, TEAL, TEAL, TEAL, TEAL, PINKISH, TEAL,
		TEAL, TEAL, TEAL, SWAMPYBLUE, TEAL, TEAL, TEAL, TEAL, MUSTARD, TEAL, TEAL, TEAL, TEAL, GRAY, TEAL, TEAL, TEAL, TEAL, DARKBLUE, TEAL, TEAL, TEAL, TEAL, 
		VIOLET, TEAL, TEAL, TEAL, TEAL, DARKGREEN, TEAL, TEAL, TEAL, TEAL, LIGHTPURPLE, TEAL, TEAL, TEAL, TEAL, HOTPINK, SKYBLUE,
		REDISHPINK, PEE, WEIRDGREEN, DARKPURPLEYBLUE, HOTGREEN, MEDIUMBLUE, REDVELVET, LIGHTREDISHPINK, ORANGISHYELLOW, LIGHTGREEN,
		MUDDYGREEN,YELLOW, ORANGISH, LIME, PEACH, TEAL};

		public static final Color[] CARPET_COLOR_DIFF49 = {WHITE, TEAL, TEAL, TEAL, TEAL, TEAL, TEAL, GREEN, TEAL, TEAL, TEAL, TEAL, TEAL, TEAL, PINKISH, TEAL,
		TEAL, TEAL, TEAL, TEAL, TEAL, SWAMPYBLUE, TEAL, TEAL, TEAL, TEAL, TEAL, TEAL, MUSTARD, TEAL, TEAL, TEAL, TEAL, TEAL, TEAL,
		GRAY, TEAL, TEAL, TEAL, TEAL, TEAL, TEAL, DARKBLUE, TEAL, TEAL, TEAL, TEAL, TEAL, TEAL,
		VIOLET, TEAL, TEAL, TEAL, TEAL, TEAL, TEAL, DARKGREEN, TEAL, TEAL, TEAL, TEAL, TEAL, TEAL, 
		LIGHTPURPLE, TEAL, TEAL, TEAL, TEAL, TEAL, TEAL, HOTPINK, TEAL, TEAL, TEAL, TEAL, TEAL, TEAL, SKYBLUE,
		REDISHPINK, PEE, WEIRDGREEN, DARKPURPLEYBLUE, HOTGREEN, MEDIUMBLUE, REDVELVET, LIGHTREDISHPINK, ORANGISHYELLOW, LIGHTGREEN,
		MUDDYGREEN,YELLOW, ORANGISH, LIME, PEACH, TEAL};

		public static final Color[] CARPET_COLOR_DIFF27 = {WHITE, TEAL, TEAL, GREEN, TEAL, TEAL, PINKISH, TEAL,
		TEAL, SWAMPYBLUE, TEAL, TEAL, DARKBLUE, TEAL, TEAL, LIGHTPURPLE, TEAL, TEAL,
		REDISHPINK, TEAL, TEAL, DARKPURPLEYBLUE, TEAL, TEAL, REDVELVET, TEAL, TEAL, LIGHTGREEN,
		TEAL, TEAL, ORANGISH, TEAL, TEAL, PEE};

		public static final Color[] CARPET_COLOR_DIFF27_3 = {WHITE, TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,TEAL, TEAL, BLUE, 
		TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,TEAL, TEAL, RED, 
		TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,TEAL, TEAL, GREEN, 
		TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,TEAL, TEAL, DARKGRAY, 	
		TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,TEAL, TEAL, SOLIDPURPLE, 
		TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,TEAL, TEAL, PINKISH, 
		TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,TEAL, TEAL, BLUELY, 
		TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,  BROWN,
		TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,TEAL, TEAL, SWAMPYBLUE,
		TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,  MUSTARD, 
		TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,TEAL, TEAL,  GRAY};

		public static final Color[] CARPET_COLOR_DIFF27_9 = {WHITE, TEAL, TEAL, BLUE, 
		TEAL, TEAL, RED, 
		TEAL, TEAL, GREEN, 
		TEAL, TEAL, DARKGRAY, 	
		TEAL, TEAL, SOLIDPURPLE, 
		TEAL, TEAL, PINKISH, 
		TEAL, TEAL, BLUELY, 
		TEAL, TEAL,  BROWN,
		TEAL, TEAL, SWAMPYBLUE,
		TEAL, TEAL,  MUSTARD, 
		TEAL, TEAL,  GRAY};*/
		
		/*public void createVersus(int ps, int p){
			CARPET_COLOR_DIFFversus = new Color[ps];
			for (int i = 0;i<ps/p ;i++) {
				for (int j =0;j<p ;j++ ) {
					CARPET_COLOR_DIFFversus[i*p+j] = CARPET_COLOR_DIFFversus[i];
				}
			}	
		}*/

	private Color realColor = null;
	private int color;
	private int colorVersus;
	private boolean twoD = true;
	private int horizontal;
	private boolean starter;

	public Strand(int i, int color, boolean start){
		if(i%2 == 0){
			horizontal = 0;
		}
		else{
			horizontal = 1;
		}
		this.starter = true;
		this.color = color;
		if(color == -1){
			realColor = BACKGROUND_COLOR;
		}
		else if(color >= CARPET_COLOR_DIFF.length){
			realColor = CARPET_COLOR_DIFF[CARPET_COLOR_DIFF.length -1];
		}
		else{
			realColor = CARPET_COLOR_DIFF[color];
		}
	}

	public Strand(int i, int color, int colorablity){
		if(i%2 == 0){
			horizontal = 0;
		}
		else{
			horizontal = 1;
		}
		this.starter = true;
		if(color<0){
			this.color = Math.floorMod(color, colorablity);
		}else{
			this.color = color;
		}
		
		if(color == -1){
			realColor = BACKGROUND_COLOR;
		}
		else if(color >= CARPET_COLOR_DIFF.length){
			realColor = CARPET_COLOR_DIFF[color%(CARPET_COLOR_DIFF.length -1)];
		}
		else{
			realColor = CARPET_COLOR_DIFF[this.color];
		}

		/*if(color == -1){
			realColor = BACKGROUND_COLOR;
		}
		else if(color > 0){
			realColor = CARPET_COLOR_DIFF[1];
		}
		else{
			realColor = CARPET_COLOR_DIFF[this.color];
		}*/
	}
	
	public Strand(int color){
		this.color = color;
		if(color == -1){
			realColor = BACKGROUND_COLOR;
		}
		else if(color >= CARPET_COLOR_DIFF.length){
			realColor = CARPET_COLOR_DIFF[color%(CARPET_COLOR_DIFF.length -1)];
		}
		else{
			realColor = CARPET_COLOR_DIFF[color];
			//realColor = CARPET_COLOR_DIFFversus[color];
		}
	}

	public Strand(int i, int j){
		this.horizontal = (i+j)%2;
	}

	//get color method
	public int getColor(){
		return this.color;
	}

	public static Color[] getCOLORS(){
		return CARPET_COLOR_DIFF;
	}
	public Color getRealColor(){
		return this.realColor;
	}
	public int getColorSversus(){

		return this.findIndex(CARPET_COLOR_DIFFversus,this.realColor);
	}
	public Color getRealColorV(int c){
		return CARPET_COLOR_DIFFversus[c];
	}
public static int findIndex(Color a[], Color t)
    {
        if (a == null)
            return -1;

        int len = a.length;
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (a[i].equals(t)) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
      
        return -1;
    }

	//set color method
	public void setColor(int col){
		this.color = col;
		if(col == -1){
			realColor = BACKGROUND_COLOR;
		}
		else if(color >= CARPET_COLOR_DIFF.length){
			realColor = CARPET_COLOR_DIFF[color%(CARPET_COLOR_DIFF.length -1)];
		}
		else{
			realColor = CARPET_COLOR_DIFF[col];
		}
		/*if(color == -1){
			realColor = BACKGROUND_COLOR;
		}
		else if(color > 0){
			realColor = CARPET_COLOR_DIFF[1];
		}
		else{
			realColor = CARPET_COLOR_DIFF[this.color];
		}*/
	}
	public void setColorVersus(int col){
		this.color = col;
		if(col == -1){
			realColor = BACKGROUND_COLOR;
		}
		else if(color >= CARPET_COLOR_DIFFversus.length){
			realColor = CARPET_COLOR_DIFF[color%(CARPET_COLOR_DIFFversus.length -1)];
		}
		else{
			realColor = CARPET_COLOR_DIFFversus[col];
		}
		/*if(color == -1){
			realColor = BACKGROUND_COLOR;
		}
		else if(color > 0){
			realColor = CARPET_COLOR_DIFF[1];
		}
		else{
			realColor = CARPET_COLOR_DIFF[this.color];
		}*/
	}
	//isHorizontal 
	//isStarter
	public String toString(){
		return String.valueOf(color);
	}

	
}