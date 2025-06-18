//package ColoringCarpets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.lang.Math;
import javax.swing.JFrame;
import java.util.Random;
import java.util.ArrayList;
import java.io.FileWriter; 
import java.io.IOException; 
import java.util.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.math.BigInteger;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import java.util.Random;
import java.util.ArrayList;


	
public class Carpet2{
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

	private int[][] carp; // Double array of each strand in the carpet. It is used to generate the image. 
	//First coord is the row and second is the column so [1][2] is the 2nd row and 3rd column or down 1 to the left 2
	private BigInteger[][] whiteSqaureCarp;
	private int colorablity = 3; //This is the number of colors in a carpet and it is important for the way the carpet is generated
	private boolean twoD = true; //Useless currently future uses maybe?
	private int[][] Starter;// Used to create carpet for knot theory carpets
	private int size; // size of the carpet size x size
	private double[] rows; // The average amount of 0's for each row
	private String location = "Q4"; // Not used currently. Future implementation
	private String template = "quadrants"; // Not used currently. Future implementation
	private String[] inputStarter; // The inputed set of starter strands 
	private BigInteger[][] polynomial;
	private BigInteger[][] cordSeq;
	//private String[][] diagonalPeriodicFormula;
	private boolean sumOf = false; // true if the carpet was made from the sum, difference or product of two carpets 
	private String[] diagonalStater; 
	private String[] regularStarter;
	private static String[] templates = {"quadrants", "diagonalX", "diagonalMinusX"}; // Not used currently. Future implementation
	private static String[] locations = {"Q1", "Q2", "Q3", "Q4", "full","left","right","top","bottom"}; //Not used currently. Future implementation
	private int[][] locationBrick;
	private int brickSize;
	private boolean astrik = false;
	private int firstSp;
	private int[][] orientation; 
	private int aCoef;
	private int bCoef;

//private boolean period_; 

	//private Strand[] brickStarter;
	/*
	* This code desiphers the inputed starter array of strings inputed from the user 
	* Then it creates Strand[][] Starter which is used to create the carpet
	*/

	/*the way we input the string is the following _ means everything after this repeats infinitely 
	* everything before it is only used once this limits us to rational number staters
	* for example "012_012" will generate 012012012012...
	* "1_00" will be 1_000000000...
	* make sure to put two numbers after the underscore or else it will error
	* if it is an entirely repeating pattern put one period of the pattern before the underscore and then the same thing after
	* so if you want 012012012... put "012_012" and not "_012" or if you want 111111... put "1_11" not "_1" and not "_11"
	*/

	public void createStarter(String[] starter){
		inputStarter = starter; 
		boolean ended = false;
		boolean astrik2 = false;
		int[] starterRealLength = new int[starter.length];
		for(int i = 0; i < starter.length; i++){
			int count = 0;
			for(int j = 0; j < starter[i].length(); j++){
				if(starter[i].charAt(j) == '*'){
					j=j+3;
				}
				count++;
			}
			starterRealLength[i] = count-1;
			//System.out.println(count);
		}
			for(int i=0; i<starter.length; i++){
				//if(starter[i] == null){break;}
				String temp = starter[i];
				//this if statement goes directly into the repeating portion of the starter pattern 
				if(temp.charAt(0) == '_'){
					int count = 0;
					int count2 = 0; 
					int secondPartCount = size; //In example "1_012" this is 3 
					String star = temp.substring(1,temp.length());
					int repeatCount = star.length(); //In example "_012" this is 3
					for(int j=0; j<secondPartCount+count2; j++){
						int indexSecondPart = j % repeatCount;
						int color;
						if(indexSecondPart>=3){
							if(star.charAt(indexSecondPart) == '*' && star.charAt(indexSecondPart-3) != '*'){
								color = Integer.parseInt(star.substring(indexSecondPart+1,indexSecondPart+3));
								astrik = true;
								this.Starter[i][j-count2] = color;
							}
							else if(star.charAt(indexSecondPart-3) == '*' || star.charAt(indexSecondPart-2) == '*' || star.charAt(indexSecondPart-1) == '*'){
								count2++;
							}else{
								color = Integer.parseInt(star.substring(indexSecondPart,indexSecondPart+1));
								this.Starter[i][j-count2] = color;
							}
						}
						else{
							if(star.charAt(indexSecondPart) == '*'){
								color = Integer.parseInt(star.substring(indexSecondPart+1,indexSecondPart+3));
								//Strand start = new Strand(i,color,colorablity);
								astrik = true;
								this.Starter[i][j-count2] = color;
							}/*else if(indexSecondPart == 2){
								if(star.charAt(indexSecondPart-2) == '*' || star.charAt(indexSecondPart-1) == '*'){
									count2++;
								}else{
									System.out.println(star.substring(indexSecondPart,indexSecondPart+1));
									color = Integer.parseInt(star.substring(indexSecondPart,indexSecondPart+1));
									Strand start = new Strand(i,color,colorablity);
									this.Starter[i][j-count2] = start;
								}
							}else if(indexSecondPart == 1){
								if(star.charAt(indexSecondPart-1) == '*'){
									count2++;
								}else{
									//System.out.println(star.substring(indexSecondPart,indexSecondPart+1));
									color = Integer.parseInt(star.substring(indexSecondPart,indexSecondPart+1));
									Strand start = new Strand(i,color,colorablity);
									this.Starter[i][j-count2] = start;
								}
							}*/else{
								//System.out.println(star.substring(indexSecondPart,indexSecondPart+1));
								color = Integer.parseInt(star.substring(indexSecondPart,indexSecondPart+1));
								//Strand start = new Strand(i,color,colorablity);
								this.Starter[i][j-count2] = color;
							}
						}
					}
				}//fix this shit  
				else{
					String[] decimals = temp.split("_");
					int firstPartCount = decimals[0].length();
					for(int j = 0; j<firstPartCount; j++){
						if(j == size){
							ended = true;
							break;
						}
						int color;
						color = Integer.parseInt(decimals[0].substring(j,j+1));
						//Strand start = new Strand(i,color,colorablity);
						//System.out.println("Strand " + i + " " + j + " " + start);
						this.Starter[i][j] = color;
					}
					int count = 0;
					int count2 = 0;
					int secondPartCount = size - firstPartCount;
					int repeatCount = decimals[1].length(); //In example "1_012" this is 3 
					if(!ended){
						for(int j=0; j<secondPartCount+count2; j++){
							int indexSecondPart = j % repeatCount; 
							int color;
							if(indexSecondPart>=3){
							if(decimals[1].charAt(indexSecondPart) == '*' && decimals[1].charAt(indexSecondPart-3) != '*'){
								//System.out.println(indexSecondPart+1);
								color = Integer.parseInt(decimals[1].substring(indexSecondPart+1,indexSecondPart+3));
								//System.out.println(color); 
								//Strand start = new Strand(i,color,colorablity);
								astrik = true;
								this.Starter[i][j-count2+firstPartCount] = color;
							}
							else if(decimals[1].charAt(indexSecondPart-3) == '*' || decimals[1].charAt(indexSecondPart-2) == '*' || decimals[1].charAt(indexSecondPart-1) == '*'){
								//color = -1;
								count2++;
							}else{
								//System.out.println(star.substring(indexSecondPart,indexSecondPart+1));
								color = Integer.parseInt(decimals[1].substring(indexSecondPart,indexSecondPart+1));
								//Strand start = new Strand(i,color,colorablity);
								this.Starter[i][j-count2+firstPartCount] = color;
							}
						}
						else{
								color = Integer.parseInt(decimals[1].substring(indexSecondPart,indexSecondPart+1));
								//Strand start = new Strand(i,color,colorablity);
								this.Starter[i][j-count2+firstPartCount] = color;
						}
						}
					}
				}
			}
			/*for(int i = 0; i< this.Starter.length; i++){
				for(int j = 0; j< this.Starter[i].length; j++){
					System.out.print(this.Starter[i][j] + " ");
				}
				System.out.println();
				System.out.println();
			}*/
		}


	/*
	* A few different Constructors:
	* 1. The first constructor: just inputs size and the starter
	* 2. The second constructor: inputs size, colorability, and the starter
	* 3. The next one takes two carpets that were already generated and creates the sum of the carpets
	* 4. We don't really use this one. Future use maybe
	*/

	/*1*/
	public Carpet2(int size, String[] starter){
		this.Starter = new int[4][size];
		this.rows = new double[size];
		this.size = size;
		this.polynomial = new BigInteger[size+1][size+1];
		createStarter(starter);
		this.carp = new int[size+1][size+1]; 
	}

	/*2*/
	public Carpet2(int size, int p, String[] starter){
		this.Starter = new int[4][size];
		//System.out.println(p);
		this.colorablity = p;
		//System.out.println(colorablity);
		this.rows = new double[size];
		this.size = size;
		this.polynomial = new BigInteger[size+1][size+1];
		createStarter(starter);
		this.carp = new int[size+1][size+1]; 
	}
	/*2.5?*/
	public Carpet2(int size, int p){
		this.colorablity = p;
		this.size = size;
	}

	/*3*/
	public Carpet2(Carpet2 C1, Carpet2 C2, int opper){
		sumOf = true;
		//if(C1.getColorablity() != C2.getColorablity()) throw new IllegalArgumentException("not the same colorablity");
		this.colorablity = C2.getColorablity();
		if(!(C1.getTemplate().equals(C2.getTemplate()))) throw new IllegalArgumentException("not the same template");
		int numRows;
		if(C1.getStarter()[0].length < C2.getStarter()[0].length){
			numRows = C1.getStarter()[0].length;
		}
		else{
			numRows = C2.getStarter()[0].length;
		}

		if(C1.getSize() < C2.getSize()){
			this.size = C1.getSize();
		}
		else{
			this.size = C2.getSize();
		}
		this.Starter = new int[4][this.size];
		this.rows = new double[size];

		int[][] Starter1 = C1.getStarter();
		this.polynomial = new BigInteger[size+1][size+1];
		int[][] Starter2 = C2.getStarter();
		for(int i = 0; i < 2; i++){
			for(int j = 0; j<this.size; j++){
				int Color1 = Starter1[i][j];
				int Color2 = Starter2[i][j];
				int newColor = 0;
				if(opper == 1){
					newColor = Math.floorMod((Color1 + Color2),this.colorablity);
				}else if(opper == -1){
					newColor = Math.floorMod((Color1 - Color2),this.colorablity);
				}
				else if(opper == 0){
					newColor = Math.floorMod((Color1*Color2),this.colorablity);
				}
				this.Starter[i][j] = newColor;
			}
		}
		this.carp = new int[this.size+1][this.size+1];
	}

// the constructor for getting the generalized diagonal formula

	/*public Carpet(int size, int length){
		
		this.rows = new double[size];
		this.size = size;
		//this.polynomial = new BigInteger[size+1][size+1];
		//createStarter(starter);
		//this.carp = new Strand[size+1][size+1]; 
	}*/

	
	public Carpet2(int size, int p, String[] starter, int first){
		this.Starter = new int[4][size];
		this.colorablity = p;
		this.firstSp = first;
		//this.rows = new double[size];
		this.size = size;
		//this.polynomial = new BigInteger[size+1][size+1];
		createStarter(starter);
		this.carp = new int[size+1][size+1];
	}

	public Carpet2(int size, int p, String[] starter, int a, int b){
		this.Starter = new int[4][size];
		this.rows = new double[size];
		this.size = size;
		this.polynomial = new BigInteger[size+1][size+1];
		createStarter(starter);
		this.carp = new int[size+1][size+1];
		this.colorablity = p;
		this.orientation = new int[2][size];
		this.aCoef = a;
		this.bCoef = b;
		for(int i = 0; i<size; i++){
			orientation[0][i] = 0;
			orientation[1][i] = 0;
		}
	}

	public Carpet2(int size, int p, String[] starter, int a, int b, String[] orient){
		this.Starter = new int[4][size];
		this.rows = new double[size];
		this.size = size;
		this.polynomial = new BigInteger[size+1][size+1];
		createStarter(starter);
		this.carp = new int[size+1][size+1];
		this.colorablity = p;
		this.orientation = new int[2][size];
		this.aCoef = a;
		this.bCoef = b;
	}

	public void orient(String[] orienta){
		//inputStarter = orienta; 
		boolean ended = false;
		//boolean astrik2 = false;
		//System.out.println(count);
			for(int i=0; i<orienta.length; i++){
				//if(starter[i] == null){break;}
				String temp = orienta[i];
				//this if statement goes directly into the repeating portion of the starter pattern 
				if(temp.charAt(0) == '_'){
					int count = 0;
					int count2 = 0; 
					int secondPartCount = size; //In example "1_012" this is 3 
					String star = temp.substring(1,temp.length());
					int repeatCount = star.length(); //In example "_012" this is 3
					for(int j=0; j<secondPartCount+count2; j++){
						int indexSecondPart = j % repeatCount;
						int color;
						if(indexSecondPart>=3){
							if(star.charAt(indexSecondPart) == '*' && star.charAt(indexSecondPart-3) != '*'){
								//System.out.println(indexSecondPart+1);
								color = Integer.parseInt(star.substring(indexSecondPart+1,indexSecondPart+3));
								astrik = true;
								this.orientation[i][j-count2] = color;
							}
							else if(star.charAt(indexSecondPart-3) == '*' || star.charAt(indexSecondPart-2) == '*' || star.charAt(indexSecondPart-1) == '*'){
								//color = -1;
								count2++;
							}else{
								//System.out.println(star.substring(indexSecondPart,indexSecondPart+1));
								color = Integer.parseInt(star.substring(indexSecondPart,indexSecondPart+1));
								this.orientation[i][j-count2] = color;
							}
						}
						else{
							if(star.charAt(indexSecondPart) == '*'){
								//System.out.println(indexSecondPart+1);
								color = Integer.parseInt(star.substring(indexSecondPart+1,indexSecondPart+3));
								astrik = true;
								this.orientation[i][j-count2] = color;
							}/*else if(indexSecondPart == 2){
								if(star.charAt(indexSecondPart-2) == '*' || star.charAt(indexSecondPart-1) == '*'){
									count2++;
								}else{
									System.out.println(star.substring(indexSecondPart,indexSecondPart+1));
									color = Integer.parseInt(star.substring(indexSecondPart,indexSecondPart+1));
									this.Starter[i][j-count2] = start;
								}
							}else if(indexSecondPart == 1){
								if(star.charAt(indexSecondPart-1) == '*'){
									count2++;
								}else{
									//System.out.println(star.substring(indexSecondPart,indexSecondPart+1));
									color = Integer.parseInt(star.substring(indexSecondPart,indexSecondPart+1));

									this.Starter[i][j-count2] = start;
								}
							}*/else{
								//System.out.println(star.substring(indexSecondPart,indexSecondPart+1));
								color = Integer.parseInt(star.substring(indexSecondPart,indexSecondPart+1));
								this.orientation[i][j-count2] = color;
							}
						}
					}
				}//fix this shit  
				else{
					String[] decimals = temp.split("_");
					int firstPartCount = decimals[0].length();
					for(int j = 0; j<firstPartCount; j++){
						if(j == size){
							ended = true;
							break;
						}
						int color;
						color = Integer.parseInt(decimals[0].substring(j,j+1));
						this.orientation[i][j] = color;
					}
					int count = 0;
					int count2 = 0;
					int secondPartCount = size - firstPartCount;
					int repeatCount = decimals[1].length(); //In example "1_012" this is 3 
					if(!ended){
						for(int j=0; j<secondPartCount+count2; j++){
							int indexSecondPart = j % repeatCount; 
							int color;
							if(indexSecondPart>=3){
							if(decimals[1].charAt(indexSecondPart) == '*' && decimals[1].charAt(indexSecondPart-3) != '*'){
								//System.out.println(indexSecondPart+1);
								color = Integer.parseInt(decimals[1].substring(indexSecondPart+1,indexSecondPart+3));
								astrik = true;
								this.orientation[i][j-count2+firstPartCount] = color;
							}
							else if(decimals[1].charAt(indexSecondPart-3) == '*' || decimals[1].charAt(indexSecondPart-2) == '*' || decimals[1].charAt(indexSecondPart-1) == '*'){
								//color = -1;
								count2++;
							}else{
								//System.out.println(star.substring(indexSecondPart,indexSecondPart+1));
								color = Integer.parseInt(decimals[1].substring(indexSecondPart,indexSecondPart+1));
								//Strand start = new Strand(i,color,colorablity);
								this.orientation[i][j-count2+firstPartCount] = color;
							}
						}
						else{
								color = Integer.parseInt(decimals[1].substring(indexSecondPart,indexSecondPart+1));
								//Strand start = new Strand(i,color,colorablity);
								this.orientation[i][j-count2+firstPartCount] = color;
						}
						}
					}
				}
			}
			/*for(int i = 0; i< this.Starter.length; i++){
				for(int j = 0; j< this.Starter[i].length; j++){
					System.out.print(this.Starter[i][j] + " ");
				}
				System.out.println();
				System.out.println();
			}*/
		}


	//constructor not used
	public Carpet2(int size, int p, String[] starter, String location, String template){
		this.size = size;
		//this.twoD = twoD;
		this.colorablity = p;
		this.rows = new double[size];
		this.Starter = new int[4][size];
		//maybe make this into a helper method
		createStarter(starter);
		if(!isIn(this.templates,this.template)) throw new IllegalArgumentException("invalid template");
		if(!isIn(this.locations,this.location)) throw new IllegalArgumentException("invlid location");
		this.location = location;
		this.template = template;
		this.carp = new int[size+1][size+1];
	}

	//Helper method for a constructor that isn't even used
	public boolean isIn(String[] list, String str){
		for(int i = 0; i<list.length; i++){
			if(list[i].equals(str)){
				return true;
			}
		}
		return false;
	}


	/*
	* This method creates the carpet
	* It uses the knot theory coloribilty formula to generate each strand that doesn't come from the starter
	*/
	//returns the number of strands with 0's over the number of strands with 
	public double createGeneralizedKnotCarpet(boolean polynom){
		final int desiredImageSize = 10 * 550;

        int gridSize = size+1;
        int squareSize;
        if(desiredImageSize/gridSize == 0){
        	System.out.println(desiredImageSize/gridSize);
        	squareSize = 1;
        }
        else{
        	squareSize = desiredImageSize/gridSize;
        }
        BigInteger two = new BigInteger("2");
        BigInteger aCoef2 = new BigInteger(Integer.toString(aCoef));
        BigInteger bCoef2 = new BigInteger(Integer.toString(bCoef));
        BigInteger absum = new BigInteger(Integer.toString(aCoef+bCoef));
        BufferedImage img = new BufferedImage(gridSize * squareSize, gridSize * squareSize, BufferedImage.TYPE_INT_ARGB);

		/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        img.setRGB(x, y, pixelColor.getRGB());
                    }
                }*/

		double fullArea = carp.length*carp.length;
		double zeroCount = 0;
		for(int j = 0; j<carp.length; j++){
			double[] tempArr = new double[size];
			double rowArea = carp.length;
			double rowZeroCount = 0;
			for(int i = 0; i<carp.length; i++){

				//The series of if statements is to determine if a strand is a starter strand
				//And what type of strand it is
				if(i == 0 && j == 0){
					int start = -1;
					if(polynom){
						polynomial[i][j] = BigInteger.ZERO;
					}
					carp[i][j] = start;
					if(start<0){
						for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                    			img.setRGB(y,x, BACKGROUND_COLOR.getRGB());
                    		}
                		}
                	}else{
                		for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                    			img.setRGB(y,x, CARPET_COLOR_DIFF[start%34].getRGB());
                    		}
                		}
                	}
				} else if(i == 0 || (i == 1 && (j%2) == 1)){
					if(Starter[0][j-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[0][j-1]));
					}
					carp[i][j] = Starter[0][j-1];
               		for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                   		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                 			img.setRGB(y,x, CARPET_COLOR_DIFF[carp[i][j]%34].getRGB());
                   		}
                	}
				} else if(j == 0){
					if(Starter[1][i-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[1][i-1]));
					}
					carp[i][j] = Starter[1][i-1];
					for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                   		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                 			img.setRGB(y,x, CARPET_COLOR_DIFF[carp[i][j]%34].getRGB());
                   		}
                	}
				} else if(i == 1 & j == 1){
					if(Starter[0][j-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[0][j-1]));
					}
					carp[i][j] = Starter[0][j-1];
					for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                   		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                 			img.setRGB(y,x, CARPET_COLOR_DIFF[carp[i][j]%34].getRGB());
                   		}
                	}
				}
				/*else if(i == 1 && j%2 == 1){
					if(Starter[0][j-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					carp[i][j] = Starter[0][j-1];
				} */else if(j == 1 && i%2 == 0){
					if(Starter[1][i-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[1][i-1]));
					}
					carp[i][j] = Starter[1][i-1];
					for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                   		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                 			img.setRGB(y,x, CARPET_COLOR_DIFF[carp[i][j]%34].getRGB());
                   		}
                	}
				} else{
					if((i + j)%2 == 1){
						//Strand start = new Strand(i,j);
						int temp;
						int color;
						if((orientation[1][i-1] == 0)){
							temp = (aCoef+bCoef)*(carp[i][j-1])-aCoef*carp[i][j-2];
							if(Math.floorMod(temp,colorablity) == 0){
								color = Math.floorMod(temp,colorablity);
							}
							else{
								while(temp/((double)bCoef) != temp/bCoef){
									temp = temp + colorablity;
								}
								color = Math.floorMod(temp/bCoef,colorablity);
							}
							if(polynom){
								BigInteger temp2 = absum.multiply(polynomial[i][j-1]).subtract(aCoef2.multiply(polynomial[i][j-2]));
								polynomial[i][j] = temp2;
							}
						}
						else{
							temp = (aCoef+bCoef)*(carp[i][j-1])-bCoef*carp[i][j-2];
						//	color = Math.floorMod(aCoef*Math.floorMod(temp,colorablity), colorablity;
							if(Math.floorMod(temp,colorablity) == 0){
								color = Math.floorMod(temp,colorablity);
							}
							else{
								while(temp/((double)aCoef) != temp/aCoef){
									temp = temp + colorablity;
								}
								color = Math.floorMod(temp/aCoef,colorablity);
							}
							if(polynom){
								BigInteger temp2 = two.multiply(polynomial[i][j-1]).subtract(polynomial[i][j-2]);
								polynomial[i][j] = temp2;
							}
						}
						
						if(color == 0){
							zeroCount++;
							rowZeroCount++;
						}
						if(i == j+1){
							//System.out.println(i+","+j + ": " + color);
						}
						//polynomial[i][j] = temp2;
						//start.setColor(color);
						carp[i][j] = color;
						for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                   			for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                 				img.setRGB(y,x, CARPET_COLOR_DIFF[carp[i][j]%34].getRGB());
                   			}
                		}
					}
					else{
						int temp;
						int color;
						if((orientation[0][j-1] == 1)){
							temp = (carp[i-1][j])-aCoef*carp[i-2][j];
							if(Math.floorMod(temp,colorablity) == 0){
								color = Math.floorMod(temp,colorablity);
							}
							else{
								while(temp/((double)bCoef) != temp/bCoef){
									temp = temp + colorablity;
									//System.out.println("1: " + temp);
								}
								color = Math.floorMod(temp/bCoef,colorablity);
							}
							if(polynom){
								BigInteger temp2 = absum.multiply(polynomial[i][j-1]).subtract(aCoef2.multiply(polynomial[i][j-2]));
								polynomial[i][j] = temp2;
							}
						}
						else{
							temp = (aCoef+bCoef)*(carp[i-1][j])-(bCoef*carp[i-2][j]);
						//	color = Math.floorMod(aCoef*Math.floorMod(temp,colorablity), colorablity;
								if(Math.floorMod(temp,colorablity) == 0){
									color = Math.floorMod(temp,colorablity);
								}
								else{
									while(temp/((double)aCoef) != temp/aCoef){
										temp = temp + colorablity;
										//System.out.println("2: " + temp);
									}
									color = Math.floorMod(temp/aCoef,colorablity);
								}
								if(polynom){
									BigInteger temp2 = two.multiply(polynomial[i][j-1]).subtract(polynomial[i][j-2]);
									polynomial[i][j] = temp2;
								}
						}
						if(color == 0){
							zeroCount++;
							rowZeroCount++;
						}
						if(i == j){
							//System.out.println(i+","+j + ": " + color);
						}
						//start.setColor(color);
						carp[i][j] = color;
						for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                   			for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                 				img.setRGB(y,x, CARPET_COLOR_DIFF[carp[i][j]%34].getRGB());
                   			}
                		}
					}
				}
			}
			if((carp.length - 1)!= j){
				rows[j] = (double)(rowZeroCount/rowArea);
			}	
		}
		if(sumOf){
			String filename = String.format("%d%s%s%d%s%s%s%s%s%d%d.png", colorablity ,"--", "Generalized_", gridSize-1,"s1-", "idk","s2-","idk", "ab", aCoef, bCoef);
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else if(astrik){
			String filename = String.format("%d%s%s%d%s%s%s%s%s%d%d.png", colorablity ,"--", "Generalized_",gridSize-1,"s1-", "womp","s2-","womp", "ab", aCoef, bCoef);
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else if(inputStarter[0].length() > 82 || inputStarter[1].length() > 82){
			String filename = String.format("%d%s%s%d%s%s%s%s%s%d%d.png", colorablity ,"--", "Generalized_",gridSize-1,"s1-", "TOOBIG","s2-","TOOBIG", "ab", aCoef, bCoef);
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else{
			String filename = String.format("%d%s%s%d%s%s%s%s%s%d%d.png", colorablity ,"--", "Generalized_",gridSize-1,"s1-",inputStarter[0],"s2-",inputStarter[1], "ab", aCoef, bCoef);
			try{
    	    	ImageIO.write(img, "png", new File(filename));
	    	}
    		catch (IOException e) {

    		}
    	}
        return zeroCount/fullArea;
	}


	public double createKnotCarpetImageless(boolean polynom){
		final int desiredImageSize = 10 * 550;

        int gridSize = size+1;
        int squareSize;
        if(desiredImageSize/gridSize == 0){
        	System.out.println(desiredImageSize/gridSize);
        	squareSize = 1;
        }
        else{
        	squareSize = desiredImageSize/gridSize;
        }
        BigInteger two = new BigInteger("2");
        //BufferedImage img = new BufferedImage(gridSize * squareSize, gridSize * squareSize, BufferedImage.TYPE_INT_ARGB);

		/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        img.setRGB(x, y, pixelColor.getRGB());
                    }
                }*/

		double fullArea = carp.length*carp.length;
		double zeroCount = 0;
		for(int j = 0; j<carp.length; j++){
			//double[] tempArr = new double[size];
			double rowArea = carp.length;
			double rowZeroCount = 0;
			for(int i = 0; i<carp.length; i++){
				//The series of if statements is to determine if a strand is a starter strand
				//And what type of strand it is
				if(i == 0 && j == 0){
					int start = -1;
					if(polynom){
						polynomial[i][j] = BigInteger.ZERO;
					}
					carp[i][j] = start;
					/*for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, start.getRealColor().getRGB());
                    	}
                	}*/
				} else if(i == 0 || (i == 1 && (j%2) == 1)){
					if(Starter[0][j-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[0][j-1]));
					}
					carp[i][j] = Starter[0][j-1];
					/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}*/
				} else if(j == 0){
					if(Starter[1][i-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[1][i-1]));
					}
					carp[i][j] = Starter[1][i-1];
					/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}*/
				} else if(i == 1 & j == 1){
					if(Starter[0][j-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[0][j-1]));
					}
					carp[i][j] = Starter[0][j-1];
					/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}*/
				}
				/*else if(i == 1 && j%2 == 1){
					if(Starter[0][j-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					carp[i][j] = Starter[0][j-1];
				} */else if(j == 1 && i%2 == 0){
					if(Starter[1][i-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[1][i-1]));
					}
					carp[i][j] = Starter[1][i-1];
					/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}*/
				} else{
					if((i + j)%2 == 1){
						//Strand start = new Strand(i,j);
						int temp = 2*(carp[i][j-1])-carp[i][j-2];
						if(polynom){
							BigInteger temp2 = two.multiply(polynomial[i][j-1]).subtract(polynomial[i][j-2]);
							polynomial[i][j] = temp2;
						}
						//BigInteger temp2 = two.multiply(polynomial[i][j-1]).subtract(polynomial[i][j-2]);
						//polynomial[i][j] = temp2;
						int color = Math.floorMod(temp,colorablity);
						if(color == 0){
							zeroCount++;
							rowZeroCount++;
						}
						if(i == j+1){
							//System.out.println(i+","+j + ": " + color);
						}
						carp[i][j] = color;
						/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        		img.setRGB(y,x, start.getRealColor().getRGB());
                    		}
                		}*/
                		//if(i == j && i%1000 == 0){
                		//	System.gc();
                		//}
                		//System.gc();
					}
					else{
						int temp = 2*(carp[i-1][j])-carp[i-2][j];
						if(polynom){
							BigInteger temp2 = two.multiply(polynomial[i-1][j]).subtract(polynomial[i-2][j]);
							polynomial[i][j] = temp2;
						}
						int color = Math.floorMod(temp,colorablity);
						if(color == 0){
							zeroCount++;
							rowZeroCount++;
						}
						if(i == j && i == size-1){
							System.out.println(i+","+j + ": " + color);
						}
						carp[i][j] = color;
						/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        		img.setRGB(y,x, start.getRealColor().getRGB());
                    		}
                		}*/
                		//if(i == j && i%1000 == 0){
                		//	System.gc();
                		//}
                		//System.gc();
					}
				}
			}
			if((carp.length - 1)!= j){
				rows[j] = (double)(rowZeroCount/rowArea);
			}	
		}/*
		if(sumOf){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-", "idk","s2-","idk");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else if(astrik){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-", "womp","s2-","womp");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else if(inputStarter[0].length() > 82 || inputStarter[1].length() > 82){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-", "TOOBIG","s2-","TOOBIG");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else{
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-",inputStarter[0],"s2-",inputStarter[1]);
			try{
    	    	ImageIO.write(img, "png", new File(filename));
	    	}
    		catch (IOException e) {

    		}
    	}*/
        return zeroCount/fullArea;
	}

	public double createKnotCarpet(boolean polynom){
		final int desiredImageSize = 10 * 550;

        int gridSize = size+1;
        int squareSize;
        if(desiredImageSize/gridSize == 0){
        	System.out.println(desiredImageSize/gridSize);
        	squareSize = 1;
        }
        else{
        	squareSize = desiredImageSize/gridSize;
        }
        BigInteger two = new BigInteger("2");
        BufferedImage img = new BufferedImage(gridSize * squareSize, gridSize * squareSize, BufferedImage.TYPE_INT_ARGB);

		/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        img.setRGB(x, y, pixelColor.getRGB());
                    }
                }*/

		double fullArea = carp.length*carp.length;
		double zeroCount = 0;
		for(int j = 0; j<carp.length; j++){
			//double[] tempArr = new double[size];
			double rowArea = carp.length;
			double rowZeroCount = 0;
			for(int i = 0; i<carp.length; i++){
				//The series of if statements is to determine if a strand is a starter strand
				//And what type of strand it is
				if(i == 0 && j == 0){
					int start = -1;
					if(polynom){
						polynomial[i][j] = BigInteger.ZERO;
					}
					carp[i][j] = start;
					if(start<0){
						for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                    			img.setRGB(y,x, BACKGROUND_COLOR.getRGB());
                    		}
                		}
                	}else{
                		for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                    			img.setRGB(y,x, CARPET_COLOR_DIFF[start%34].getRGB());
                    		}
                		}
                	}
				} else if(i == 0 || (i == 1 && (j%2) == 1)){
					if(Starter[0][j-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[0][j-1]));
					}
					carp[i][j] = Starter[0][j-1];
					for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, CARPET_COLOR_DIFF[carp[i][j]%34].getRGB());
                    	}
                	}
				} else if(j == 0){
					if(Starter[1][i-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[1][i-1]));
					}
					carp[i][j] = Starter[1][i-1];
					for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, CARPET_COLOR_DIFF[carp[i][j]%34].getRGB());
                    	}
                	}
				} else if(i == 1 & j == 1){
					if(Starter[0][j-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[0][j-1]));
					}
					carp[i][j] = Starter[0][j-1];
					for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, CARPET_COLOR_DIFF[carp[i][j]%34].getRGB());
                    	}
                	}
				}
				/*else if(i == 1 && j%2 == 1){
					if(Starter[0][j-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					carp[i][j] = Starter[0][j-1];
				} */else if(j == 1 && i%2 == 0){
					if(Starter[1][i-1] == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[1][i-1]));
					}
					carp[i][j] = Starter[1][i-1];
					for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, CARPET_COLOR_DIFF[carp[i][j]%34].getRGB());
                    	}
                	}
				} else{
					if((i + j)%2 == 1){
						int temp = 2*(carp[i][j-1])-carp[i][j-2];
						if(polynom){
							BigInteger temp2 = two.multiply(polynomial[i][j-1]).subtract(polynomial[i][j-2]);
							polynomial[i][j] = temp2;
						}
						//BigInteger temp2 = two.multiply(polynomial[i][j-1]).subtract(polynomial[i][j-2]);
						//polynomial[i][j] = temp2;
						int color = Math.floorMod(temp,colorablity);
						if(color == 0){
							zeroCount++;
							rowZeroCount++;
						}
						if(i == j+1){
							//System.out.println(i+","+j + ": " + color);
						}
						carp[i][j] = color;
						for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        		img.setRGB(y,x, CARPET_COLOR_DIFF[carp[i][j]%34].getRGB());
                    		}
                		}
                		//if(i == j && i%1000 == 0){
                		//	System.gc();
                		//}
                		//System.gc();
					}
					else{
						int temp = 2*(carp[i-1][j])-carp[i-2][j];
						if(polynom){
							BigInteger temp2 = two.multiply(polynomial[i-1][j]).subtract(polynomial[i-2][j]);
							polynomial[i][j] = temp2;
						}
						int color = Math.floorMod(temp,colorablity);
						if(color == 0){
							zeroCount++;
							rowZeroCount++;
						}
						/*if(i == j && i%500 == 0){
							System.out.println(i+","+j + ": " + color);
						}*/
						carp[i][j] = color;
						for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        		img.setRGB(y,x, CARPET_COLOR_DIFF[carp[i][j]%34].getRGB());
                    		}
                		}
                		//if(i == j && i%1000 == 0){
                		//	System.gc();
                		//}
                		//System.gc();
					}
				}
			}
			if((carp.length - 1)!= j){
				rows[j] = (double)(rowZeroCount/rowArea);
			}	
		}
		if(sumOf){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-", "idk","s2-","idk");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else if(astrik){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-", "womp","s2-","womp");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else if(inputStarter[0].length() > 82 || inputStarter[1].length() > 82){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-", "TOOBIG","s2-","TOOBIG");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else{
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-",inputStarter[0],"s2-",inputStarter[1]);
			try{
    	    	ImageIO.write(img, "png", new File(filename));
	    	}
    		catch (IOException e) {

    		}
    	}
        return zeroCount/fullArea;
	}


/*
		I don't wanna do this shit now 


		public void createValidityCarpet(){
		final int desiredImageSize = 10 * 550;
		Carpet Carp = new Carpet(this.getSize(), 2);
        int gridSize = size+1;
        int squareSize;
        if(desiredImageSize/gridSize == 0){
        	System.out.println(desiredImageSize/gridSize);
        	squareSize = 1;
        }
        else{
        	squareSize = desiredImageSize/gridSize;
        }
        BigInteger two = new BigInteger("2");
        BufferedImage img = new BufferedImage(gridSize * squareSize, gridSize * squareSize, BufferedImage.TYPE_INT_ARGB);

		for(int j = 0; j<carp.length-3; j++){
			for(int i = 0; i<carp.length-3; i++){

				//The series of if statements is to determine if a strand is a starter strand
				//And what type of strand it is


				if(i < 2 || j < 2){
					Strand start = new Strand(i,j);
					start.setColor(-1);
					//System.out.println(start);
					carp[i][j] = start;
					for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, start.getRealColor().getRGB());
                    	}
                	}
				} else{
					if((i + j)%2 == 0){
						Strand start = new Strand(i,j);
						//System.out.println("hi");
						int temp = carp[i][j].getColorSversus();
						int color = 1;
						// fill in with the colorablitiy equation for 

						if(Math.floorMod(2*carp[i-1][j].getColorSversus() - carp[i][j].getColorSversus() -  carp[i-2][j].getColorSversus(), 3) != 0){
							color = 0;
						}
						if(Math.floorMod(2*carp[i+1][j].getColorSversus() - carp[i][j].getColorSversus() -  carp[i+2][j].getColorSversus(), 3) != 0){
							color = 0;
						}
						if(Math.floorMod(2*carp[i][j].getColorSversus() - carp[i][j-1].getColorSversus() -  carp[i][j+1].getColorSversus(), 3) != 0){
							color = 0;
						}
						if(i == j+1){
							//System.out.println(i+","+j + ": " + color);
						}
						start.setColorVersus(color);
						start.setColor(color);
						//System.out.println(start);
						//Carp.getCarp()[i,j] =start;
						for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        		img.setRGB(y,x, start.getRealColorV(color).getRGB());
                    		}
                		}
					}
					else{
						Strand start = new Strand(i,j);
						//System.out.println("hi");
						int temp = carp[i][j].getColorSversus();
						int color = 1;
						// fill in with the colorablitiy equation for 
						if(Math.floorMod(2*carp[i][j-1].getColorSversus() - carp[i][j].getColorSversus() -  carp[i][j-2].getColorSversus(), 3) != 0){
							color = 0;
						}
						if(Math.floorMod(2*carp[i][j+1].getColorSversus()- carp[i][j].getColorSversus() -  carp[i][j+2].getColorSversus(), 3) != 0){
							color = 0;
						}
						if(Math.floorMod(2*carp[i][j].getColorSversus() - carp[i-1][j].getColorSversus() -  carp[i+1][j].getColorSversus(), 3) != 0){
							color = 0;
						}
						if(i == j+1){
							//System.out.println(i+","+j + ": " + color);
						}
						/*if(i==5 && j== 4 ){
							System.out.println(Math.floorMod(2*carp[i][j-1].getColorSversus() - carp[i][j].getColorSversus() -  carp[i][j-2].getColorSversus(), 3));
							System.out.println(Math.floorMod(2*carp[i][j+1].getColorSversus() - carp[i][j].getColorSversus() -  carp[i][j+2].getColorSversus(), 3));
							System.out.println(Math.floorMod(2*carp[i][j].getColorSversus() - carp[i-1][j].getColorSversus() -  carp[i+1][j].getColorSversus(), 3));
							System.out.println(i + " and " + j + ": " + carp[i][j].getColorSversus() + " " + carp[i][j]);
							System.out.println((i-1) + " and " + j + ": "+ carp[i-1][j].(getColor)Sversus() + " " + carp[i-1][j]);
							System.out.println((i-2) + " and " + j + ": "+ carp[i-2][j].getColorSversus() + " " + carp[i-2][j]);
							System.out.println((i+1) + " and " + j + ": "+ carp[i+1][j].getColorSversus() + " " + carp[i+1][j]);
							System.out.println((i+2) + " and " + j + ": "+ carp[i+2][j].getColorSversus() + " " + carp[i+2][j]);
							System.out.println(i + " and " + (j-1) + ": "+ carp[i][j-1].getColorSversus() + " " + carp[i][j-1]);
							System.out.println(i + " and " + (j+1) + ": "+ carp[i][j+1].getColorSversus() + " " + carp[i][j+1]);


							System.out.println(color);
						}
						start.setColorVersus(color);
						start.setColor(color);
						//System.out.println(start);
						//Carp.getCarp()[i,j] =start;
						for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        		img.setRGB(y,x, start.getRealColorV(color).getRGB());
                    		}
                		}
					}
				}
			}
		}
		if(sumOf){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-", "idk","s2-","idk");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else if(astrik){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-", "womp","s2-","womp");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else if(inputStarter[0].length() > 82 || inputStarter[1].length() > 82){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-", "TOOBIG","s2-","TOOBIG");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else{
			String filename = String.format("%d%s%d%s%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-",inputStarter[0],"s2-",inputStarter[1], "versus");
			try{
    	    	ImageIO.write(img, "png", new File(filename));
	    	}
    		catch (IOException e) {

    		}
    	}
	}




	//This generates a carpet if you let the start pattern be the diagonal and every other strand is colored from them
	//It first generates the section of the carpet under the diagonal then the top part
	//After the starter are put into the correct spot it goes through a diagonal under which will be size-1 strands long
	//After each diagonal line of strands is generate the next line will be one less strand long
	//The same is true for the top section of the carpet

	public void createKnotCarpetDiag(){
		final int desiredImageSize = 10 * 550;

        int gridSize = size+1;

        int squareSize = desiredImageSize/gridSize;

        BufferedImage img = new BufferedImage(gridSize * squareSize, gridSize * squareSize, BufferedImage.TYPE_INT_ARGB);

		for(int i = 0; i<2; i++){
			int temp;
			if(i == 0){
				temp = 0;
			}
			else{
				temp = 1;
			}
			for(int j = temp; j<size+1; j = j+2){
				Strand strand = new Strand(0,0);
				strand.setColor(-1);
				if(i == 0){
					carp[j][size] = strand;
					for (int x = j * squareSize; x < (j + 1) * squareSize; x++) {
                   		for (int y = size * squareSize; y < (size + 1) * squareSize; y++) {
                       		img.setRGB(x, y, strand.getRealColor().getRGB());
                   		}
                	}
				}
				else{
					carp[size][j] = strand;
					for (int x = size * squareSize; x < (size + 1) * squareSize; x++) {
                   		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                     		img.setRGB(x, y, strand.getRealColor().getRGB());
                   		}
                	}
				}
			}
		}

		int count = 0; // this variable helps let us know if we are generating the top or the bottom of the carpet
		boolean bottom = true;
		for(int i = 0; i<2*(this.size)+1; i++){
			if(!(i == 0 || i ==1) && count<size){
				count++;//this increases count after each diagonal line of strands is generate and once 
			}
			else if(count == size){
				count = 1;
				bottom = false;
			}
			int increment = size - count;
			int j = 0; //helps locate where in the carpet the strand should be
			while(increment > 0){
				//The series of if statements is to determine if a strand is a starter strand
				//And what type of strand it is
				if(bottom){
					if(i == 0){
						this.carp[j][size-1-j] = Starter[0][j];
						for (int x = j * squareSize; x < (j + 1) * squareSize; x++) {
                   			for (int y = (size-1-j) * squareSize; y < (size-j) * squareSize; y++) {
                     			img.setRGB(x, y, carp[j][size-1-j].getRealColor().getRGB());
                   			}
                		}
					}
					else if(i == 1){
						this.carp[j+1][size-1-j] = Starter[1][j];
						for (int x = (j+1) * squareSize; x < (j + 2) * squareSize; x++) {
                   			for (int y = (size-1-j) * squareSize; y < (size-j) * squareSize; y++) {
                     			img.setRGB(x, y, carp[j+1][size-1-j].getRealColor().getRGB());
                   			}
                		}
					}
					else if(count%2 == 1){
						Strand strand = new Strand(i,j);
						int temp = 2*(carp[count+j][size-j-1].getColor())-carp[count+j][size-j-2].getColor();
						int color = Math.floorMod(temp,colorablity);
						strand.setColor(color);
						carp[count+j][size-j] = strand;
						for (int x = (count+j) * squareSize; x < (count+j+1) * squareSize; x++) {
                   			for (int y = (size-j) * squareSize; y < (size-j+1) * squareSize; y++) {
                     			img.setRGB(x, y, strand.getRealColor().getRGB());
                   			}
                		}
					}
					else{
						Strand strand = new Strand(i,j);
						int temp = 2*(carp[count+j][size-j-1].getColor())-carp[count+j-1][size-j-1].getColor();
						int color = Math.floorMod(temp,colorablity);
						strand.setColor(color);
						carp[count+j+1][size-1-j] = strand;
						for (int x = (count+j+1) * squareSize; x < (count+j+2) * squareSize; x++) {
                   			for (int y = (size-j-1) * squareSize; y < (size-j) * squareSize; y++) {
                     			img.setRGB(x, y, strand.getRealColor().getRGB());
                   			}
                		}
					}
				}
				else{
					if(count%2 == 1){
						Strand strand = new Strand(i,j);
						int temp = 2*(carp[1+j][size-count-1-j].getColor())-carp[j+2][size-count-1-j].getColor();
						int color = Math.floorMod(temp,colorablity);
						strand.setColor(color);
						carp[j][size-count-1-j] = strand;
						for (int x = (j) * squareSize; x < (j+1) * squareSize; x++) {
                   			for (int y = (size-count-1-j) * squareSize; y < (size-count-j) * squareSize; y++) {
                     			img.setRGB(x, y, strand.getRealColor().getRGB());
                   			}
                		}
					}
					else{
						Strand strand = new Strand(i,j);
						int temp = 2*(carp[j][size-count-j].getColor())-carp[j][size-count-j+1].getColor();
						int color = Math.floorMod(temp,colorablity);
						strand.setColor(color);
						carp[j][size-count-j-1] = strand;
						for (int x = (j) * squareSize; x < (j+1) * squareSize; x++) {
                   			for (int y = (size-count-1-j) * squareSize; y < (size-count-j) * squareSize; y++) {
                     			img.setRGB(x, y, strand.getRealColor().getRGB());
                   			}
                		}
					}
				}
				increment--;
				j++;
			}
		}
		if(inputStarter[0].length() > 82 || inputStarter[1].length() > 82){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"diagonal", gridSize-1,"s1-", "TOOBIG","s2-","TOOBIG");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		} else if(sumOf){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"diagonal", gridSize-1,"s1-", "Uh","s2-","Uh");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}
		else{
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"diagonal", gridSize-1,"s1-",inputStarter[0],"s2-",inputStarter[1]);
			try{
    	    	ImageIO.write(img, "png", new File(filename));
	    	}
    		catch (IOException e) {

    		}
    	}
	}


	public void createKnotCarpetDiagMinus(boolean Hfirst){
		final int desiredImageSize = 10 * 550;
		this.carp = new Strand[size+2][size+2];
        int gridSize = size+2;

        int squareSize = desiredImageSize/gridSize;

        BufferedImage img = new BufferedImage(gridSize * squareSize, gridSize * squareSize, BufferedImage.TYPE_INT_ARGB);

		for(int i = 0; i<4; i++){
			int temp = -1;
			if(i == 0){
				temp = 0;
			}
			else if(i == 1){
				temp = 1;
			}
			else if(gridSize%2 == 0){
				if(i == 2){
					temp = 1;
				}
				else{
					temp = 0;
				}

			}
			else if(gridSize%2 == 1){
				if(i == 2){
					temp = 0;
				}
				else{
					temp = 1;
				}

			}
			for(int j = temp; j<gridSize; j = j+2){
				Strand strand = new Strand(0,0);
				strand.setColor(-1);
				if(i == 0){
					carp[0][j] = strand;
					for (int x = 0; x <squareSize; x++) {
                   		for (int y = j*squareSize; y < (j + 1) * squareSize; y++) {
                       		img.setRGB(x, y, strand.getRealColor().getRGB());
                   		}
                	}
				}
				else if(i==1){
					carp[j][0] = strand;
					for (int x = j * squareSize; x < (j + 1) * squareSize; x++) {
                   		for (int y = 0; y < squareSize; y++) {
                       		img.setRGB(x, y, strand.getRealColor().getRGB());
                   		}
                	}
				}else if(i == 2){
					carp[gridSize-1][j] = strand;
					for (int x = (gridSize-1) * squareSize; x < (gridSize) * squareSize; x++) {
                   		for (int y = (j) * squareSize; y < (j+1) * squareSize; y++) {
                   			//System.out.println(x + " " + y + " "+ strand.getRealColor().getRGB());
                       		img.setRGB(x, y, strand.getRealColor().getRGB());
                   		}
                	}
				}
				else{
					carp[j][gridSize-1] = strand;
					for (int x = (j) * squareSize; x < (j+1) * squareSize; x++) {
                   		for (int y = (gridSize-1) * squareSize; y < (gridSize) * squareSize; y++) {
                       		img.setRGB(x, y, strand.getRealColor().getRGB());
                   		}
                	}
				}
			}
		}

		int count = 0; // this variable helps let us know if we are generating the top or the bottom of the carpet
		boolean top = true;
		for(int i = 0; i<2*(size)+1; i++){
			if(Hfirst){
				if(!(i == 0 || i ==1 || i==2) && count<size-1){
					count++;//this increases count after each diagonal line of strands is generate and once 
				}
				else if(count == size-1){
					count = 1;
					top = false;
				}

				int increment = size - count;
				int j = 0; //helps locate where in the carpet the strand should be
				while(increment > 0){
					//The series of if statements is to determine if a strand is a starter strand
					//And what type of strand it is
					if(top){
						if(i == 0){
							this.carp[j][1+j] = Starter[1][j];
							//System.out.println("1");
							for (int x = j * squareSize; x < (j + 1) * squareSize; x++) {
	                   			for (int y = (j+1) * squareSize; y < (j+2) * squareSize; y++) {
	                     			img.setRGB(x, y, carp[j][1+j].getRealColor().getRGB());
	                   			}
	                		}
						}
						else if(i == 1){
							this.carp[j+1][1+j] = Starter[0][j];
							for (int x = (j+1) * squareSize; x < (j + 2) * squareSize; x++) {
	                   			for (int y = (1+j) * squareSize; y < (2+j) * squareSize; y++) {
	                     			img.setRGB(x, y, carp[j+1][1+j].getRealColor().getRGB());
	                   			}
	                		}
						}
						else if(count%2 == 0){
							Strand strand = new Strand(i,j);
							int temp = 2*(carp[count+1+j][j+1].getColor())-carp[count+j][j+1].getColor();
							int color = Math.floorMod(temp,colorablity);
							strand.setColor(color);
							carp[count+2+j][j+1] = strand;
							for (int x = (count+2+j) * squareSize; x < (count+3+j) * squareSize; x++) {
	                   			for (int y = (j+1) * squareSize; y < (j+2) * squareSize; y++) {
	                     			img.setRGB(x, y, carp[count+2+j][j+1].getRealColor().getRGB());
	                   			}
	                		}
						}
						else{
							Strand strand = new Strand(i,j);
							//System.out.println(carp[count+1+j][j+1].getColor() + " ");
							int temp = 2*(carp[count+1+j][j+1].getColor())-carp[count+1+j][j+2].getColor();
							int color = Math.floorMod(temp,colorablity);
							strand.setColor(color);
							carp[count+j+1][j] = strand;
							for (int x = (count+j+1) * squareSize; x < (count+j+2) * squareSize; x++) {
	                   			for (int y = (j) * squareSize; y < (j+1) * squareSize; y++) {
	                     			img.setRGB(x, y, carp[count+j+1][j].getRealColor().getRGB());
	                   			}
	                		}
						}
					}
					else{
						if(count%2 == 1){
							Strand strand = new Strand(i,j);
							int temp = 2*(carp[j+1][count+j+1].getColor())-carp[j+1][count+j].getColor();
							int color = Math.floorMod(temp,colorablity);
							strand.setColor(color);
							carp[j+1][count+j+2] = strand;
							for (int x = (j+1) * squareSize; x < (j+2) * squareSize; x++) {
	                   			for (int y = (count+2+j) * squareSize; y < (3+count+j) * squareSize; y++) {
	                     			img.setRGB(x, y, carp[j+1][count+j+2].getRealColor().getRGB());
	                   			}
	                		}
						}
						else{
							Strand strand = new Strand(i,j);
							int temp = 2*(carp[j+1][count+j+1].getColor())-carp[j+2][count+j+1].getColor();
							int color = Math.floorMod(temp,colorablity);
							strand.setColor(color);
							carp[j][count+j+1] = strand;
							for (int x = (j) * squareSize; x < (j+1) * squareSize; x++) {
	                   			for (int y = (count+j+1) * squareSize; y < (count+j+2) * squareSize; y++) {
	                     			img.setRGB(x, y, carp[j][count+j+1].getRealColor().getRGB());
	                   			}
	                		}
						}
					}
					increment--;
					j++;
				}
			}
		else{

		}
	}
		if(inputStarter[0].length() > 82 || inputStarter[1].length() > 82){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"diagonalMinus", gridSize-1,"s1-", "TOOBIG","s2-","TOOBIG");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		} else if(sumOf){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"diagonalMinus", gridSize-1,"s1-", "Uh","s2-","Uh");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}
		else{
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"diagonalMinus", gridSize-1,"s1-",inputStarter[0],"s2-",inputStarter[1]);
			try{
    	    	ImageIO.write(img, "png", new File(filename));
	    	}
    		catch (IOException e) {

    		}
    	}
	}*/

	public void findNbricks(int p, int N, int s){
		//the following code is code for enumerating the vertical 2-bricks with topleft strand equal to 2 mod 3 for some power of 3
            		/* ALL we need to know is the 8 strands located at:
					*   (9a+1,9b+1),  (9a+1,9b+3),  (9a+1,9b+5),  (9a+1,9b+7)
					* and  (9a,9b+6), (9a+3,9b), (9a+9,9b+3), (9a+6,9b+9)
            		*/
					int leng = (int) Math.pow(p, N);
					int lengCount = (int) Math.pow(p, s);
					int[][] posible = new int[50000][2*(leng-1)];
            		int[] counts = new int[lengCount+1];
            		for(int i = 0; i< counts.length; i++){
            			counts[i] = 0;
            		}
            		//System.out.println(counts[p]);
            		for(int i =0; i< size/(2*leng) -2;i++){
            			for (int j = 0;j<size/(2*leng) -2 ;j++ ) {
            				int index1 = 2+2*leng*i;
            				int index2 = 2+2*leng*j;
            				int[] brick = new int[2*(leng-1)];
            				for(int k = 0; k< brick.length/4; k++){
            					brick[k] = carp[index1][index2+2*k]; // top inside brick
            					brick[k+brick.length/4] = carp[index1+1+2*k][index2];// left inside brick
            					brick[k+brick.length/2] = carp[index1+2*k][index2-1];// left grout
            					brick[k+(3*brick.length)/4] = carp[index1-1][index2+1+2*k];// top grout
            				}
            				boolean flag = true; 
            				for(int k =0;k <counts[lengCount]; k++){
            					boolean flag2 = true; 
            					for(int k2 = 0; k2<brick.length; k2++){
            						if(brick[k2] != posible[k][k2]){
            							flag2 = false;
            						}
            					}
            					if(flag2){
            						flag = false;
            						break;
            					}
            				}
            				if(flag){
            					posible[counts[lengCount]] = brick;
            					counts[lengCount] = counts[lengCount]+1;
            					if(counts[lengCount]%1000 == 0){
            						System.out.println(counts[lengCount]);
            					}
            					for(int k =0; k<counts.length; k++){
            						if(brick[0]%(lengCount) == k){
            							counts[k] = counts[k]+1;
            							if(k==0){
            								/*for(int k2 = 0; k2 < 2*(leng-1); k2++){
            									System.out.print(brick[k2] + " ");
            								}
            								System.out.println("");*/
            							}
            						}
            					}
            				}
            			}
            		}
            		for(int i = 0; i< counts.length; i++){
            			System.out.println(counts[i]);
            		}
	}

	public void findNgrout(int p, int N, int s){
		//the following code is code for enumerating the vertical 2-bricks with topleft strand equal to 2 mod 3 for some power of 3
            		/* ALL we need to know is the 8 strands located at:
					*   (9a+1,9b+1),  (9a+1,9b+3),  (9a+1,9b+5),  (9a+1,9b+7)
					* and  (9a,9b+6), (9a+3,9b), (9a+9,9b+3), (9a+6,9b+9)
            		*/
					int leng = (int) Math.pow(p, N);
					int lengCount = (int) Math.pow(p, s);
					int[][] posible = new int[50000][2*(leng-1)];
            		int[] counts = new int[lengCount+1];
            		for(int i = 0; i< counts.length; i++){
            			counts[i] = 0;
            		}
            		//System.out.println(counts[p]);
            		for(int i =0; i< size/(2*leng) -2;i++){
            			for (int j = 0;j<size/(2*leng) -2 ;j++ ) {
            				int index1 = 2+2*leng*i;
            				int index2 = 2+2*leng*j;
            				int[] brick = new int[2*(leng-1)];
            				for(int k = 0; k< brick.length/4; k++){
            					brick[k] = carp[index1+2*k][index2-1];// left grout
            					brick[k+brick.length/4] = carp[index1-1][index2+1+2*k];// top grout
            					brick[k+brick.length/2] = carp[index1+1+2*k][index2+leng-1]; // right grout
            					brick[k+(3*brick.length)/4] = carp[index1+leng-1][index2+2*k];// bottom grout
            				}
            				boolean flag = true; 
            				for(int k =0;k <counts[counts.length-1]; k++){
            					boolean flag2 = true; 
            					for(int k2 = 0; k2<brick.length; k2++){
            						if(brick[k2] != posible[k][k2]){
            							flag2 = false;
            						}
            					}
            					if(flag2){
            						flag = false;
            						break;
            					}
            				}
            				if(flag){
            					posible[counts[counts.length-1]] = brick;
            					counts[counts.length-1] = counts[counts.length-1]+1;
            					for(int k =0; k<counts.length; k++){
            						if((carp[index1][index2])%(lengCount) == k){
            							counts[k] = counts[k]+1;
            							if(counts[counts.length-1] == 1){
            								/*for(int k2 = 0; k2 < 2*(leng-1); k2++){
            									System.out.print(brick[k2] + " ");
            								}
            								System.out.println("");*/
            							}
            						}
            					}
            				}
            			}
            		}
            		for(int i = 0; i< counts.length; i++){
            			System.out.println(counts[i]);
            		}
	}
/*
	I don't feel like doing this shit right now


	public void whiteSquareCarpetOneGo(){
		final int desiredImageSize = 10 * 650;

        int gridSize = size;

        int squareSize = desiredImageSize/gridSize;
        if(desiredImageSize/gridSize == 0){
        	System.out.println(desiredImageSize/gridSize);
        	squareSize = 1;
        }
        else{
        	squareSize = desiredImageSize/gridSize;
        }
        BufferedImage img = new BufferedImage(gridSize * squareSize, gridSize * squareSize, BufferedImage.TYPE_INT_ARGB);
        carp[0][0] = new Strand(firstSp);
        for (int x = (0) * squareSize; x < (1) * squareSize; x++) {
			for (int y = (0) * squareSize; y < (1) * squareSize; y++) {
				img.setRGB(x, y, carp[0][0].getRealColor().getRGB());
			}
		}
        for(int i = 1; i < size; i++){
        	carp[i][0] = Starter[0][i-1];
        	for (int x = (i) * squareSize; x < (i+1) * squareSize; x++) {
				for (int y = (0) * squareSize; y < (1) * squareSize; y++) {
					img.setRGB(x, y, carp[i][0].getRealColor().getRGB());
				}
			}
			carp[0][i] = Starter[1][i-1];
        	for (int x = (0) * squareSize; x < (1) * squareSize; x++) {
				for (int y = (i) * squareSize; y < (i+1) * squareSize; y++) {
					img.setRGB(x, y, carp[0][i].getRealColor().getRGB());
				}
			}
        }
        
        for(int i = 1; i<size; i++){
        	for(int j = 1; j<size; j++){
        		if(Math.floorMod(colorablity-1, 4) == 0){
        			if((i+j)%2 == 0){
        				//A-C+B
        				int temp = Math.floorMod(carp[i-1][j-1].getColor()-carp[i-1][j].getColor()+carp[i][j-1].getColor(), colorablity);
        				carp[i][j] = new Strand(temp);
        			}
        			else{
        				//A+C-B
        				int temp = Math.floorMod(carp[i-1][j-1].getColor()+carp[i-1][j].getColor()-carp[i][j-1].getColor(), colorablity);
        				carp[i][j] = new Strand(temp);
        			}
        		}
        		else{
        			if((i+j)%2 == 0){
        				//A+C-B
        				int temp = Math.floorMod(carp[i-1][j-1].getColor()+carp[i-1][j].getColor()-carp[i][j-1].getColor(), colorablity);
        				carp[i][j] = new Strand(temp);
        			}else{
        				//A-C+B
        				int temp = Math.floorMod(carp[i-1][j-1].getColor()-carp[i-1][j].getColor()+carp[i][j-1].getColor(), colorablity);
        				carp[i][j] = new Strand(temp);
        			}
        		}
	        	for (int x = (i) * squareSize; x < (i+1) * squareSize; x++) {
					for (int y = (j) * squareSize; y < (j+1) * squareSize; y++) {
						img.setRGB(x, y, carp[i][j].getRealColor().getRGB());
					}
				}
        	}
        }
        if(astrik){
        	String filename = String.format("%d%s%d.png", gridSize,"bricks", colorablity);
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
        }
        else{
			String filename = String.format("%d%s%s%s%s%d%s%d.png", gridSize,"bricks", inputStarter[0] + "_",inputStarter[1]+"_" , "tc", firstSp,"_color", colorablity);
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}
	}

	public void whiteSquareCarpet(){
		whiteSqaureCarp = new BigInteger[size][size];
		for(int i = 0; i<size; i++){
			/*if(i == 0){
				whiteSqaureCarp[0][0] = new BigInteger(Integer.toString(1));
			}
			else{
				whiteSqaureCarp[i][0] = new BigInteger(Integer.toString(1));
				whiteSqaureCarp[0][i] = new BigInteger(Integer.toString(2));
			}*//*
			whiteSqaureCarp[i][0] = new BigInteger("1");
			whiteSqaureCarp[0][i] = new BigInteger("1");
			/*if(i%4 == 0){
				if(i == 0){
					whiteSqaureCarp[0][0] = new BigInteger("2");
				}
				whiteSqaureCarp[i][0] = new BigInteger("2");
				whiteSqaureCarp[0][i] = new BigInteger("2");
			}
			else if(i%4 == 1){
				whiteSqaureCarp[i][0] = new BigInteger("2");
				whiteSqaureCarp[0][i] = new BigInteger("-2");
			}
			else if(i%4 == 2){
				whiteSqaureCarp[i][0] = new BigInteger("-2");
				whiteSqaureCarp[0][i] = new BigInteger("-2");
			}
			else{
				whiteSqaureCarp[i][0] = new BigInteger("-2");
				whiteSqaureCarp[0][i] = new BigInteger("2");
			}*//*
		}
		for(int i = 1; i<size; i++){
			for(int j = 1; j<size; j++){
				if((i+j)%2 == 0){
					whiteSqaureCarp[i][j] = whiteSqaureCarp[i-1][j-1].add(whiteSqaureCarp[i][j-1].add(whiteSqaureCarp[i-1][j]));
				}else{
					whiteSqaureCarp[i][j] = whiteSqaureCarp[i-1][j-1].add(whiteSqaureCarp[i-1][j].add(whiteSqaureCarp[i][j-1]));
				}
			}
		}
	}

	public void CreatewhiteSquareCarpet(int c){
		final int desiredImageSize = 10 * 350;

        int gridSize = size;

        int squareSize = desiredImageSize/gridSize;
        if(desiredImageSize/gridSize == 0){
        	System.out.println(desiredImageSize/gridSize);
        	squareSize = 1;
        }
        else{
        	squareSize = desiredImageSize/gridSize;
        }
        BufferedImage img = new BufferedImage(gridSize * squareSize, gridSize * squareSize, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i < size; i++){
        	for(int j = 0; j < size; j++){
        		for (int x = (i) * squareSize; x < (i+1) * squareSize; x++) {
					for (int y = (j) * squareSize; y < (j+1) * squareSize; y++) {
						BigInteger cBIG = new BigInteger(Integer.toString(c));
						//System.out.println(c + " " + whiteSqaureCarp[i][j]);
							if(whiteSqaureCarp[i][j].mod(cBIG).equals(BigInteger.ZERO)){
								img.setRGB(x, y, Strand.getCOLORS()[0].getRGB());
							}
							else{
								//img.setRGB(x, y, Strand.getCOLORS()[(whiteSqaureCarp[i][j].mod(cBIG)).intValue()%(Strand.getCOLORS().length-1)].getRGB());
								img.setRGB(x, y, Strand.getCOLORS()[1].getRGB());
							}
						//img.setRGB(x, y, Strand.getCOLORS()[Math.floorMod(whiteSqaureCarp[i][j], c)%(Strand.getCOLORS().length-1)].getRGB());
					}
				}
        	}
        }
        if(true){
			String filename = String.format("%d%s%s.png", gridSize,"Difference Carpet white squares", c);
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}
	}

	public void checkerBoard(){
		for(int i = 0; i<size+1; i++){
			for(int j = 0; j<size+1;j++){
				Strand strand = new Strand(i,j);
				int color;
				if((i+j)%2 == 0){
					color = 0;
				}
				else{
					color = 1;
				}
				strand.setColor(color);
				carp[i][j] = strand;
			}
		}
	}

	public void stripes(){
		for(int i = 0; i<size+1; i++){
			for(int j = 0; j<size+1;j++){
				Strand strand = new Strand(i,j);
				int color;
				if(i%2 == 0){
					color = 0;
				}
				else{
					color = 1;
				}
				strand.setColor(color);
				carp[i][j] = strand;
			}
		}
	}*/
		// A bunch of get methods
	public int[][] getCarp(){
		return this.carp;
	}
	public void setCarp(int i, int j, int s){
		this.carp[i][j] = s;
	}

	public BigInteger[][] getCordSeq(){
		return this.cordSeq;
	}

	public BigInteger[][] getPolynomial(){
		return this.polynomial;
	}


	public int[][] getStarter(){
		return this.Starter;
	}

	public double[] getRowAve(){
		return this.rows;
	}

	public int getSize(){
		return this.size;
	}

	public int getColorablity(){
		return this.colorablity;
	}

	public String getTemplate(){
		return this.template;
	}
	public BigInteger[][] getWhiteSqaureCarp(){
		return this.whiteSqaureCarp;
	}
	//Makes a csv of the averge number number of 0's for a given row
	//I think it only works for my computer
	//I made this for my own curiosity not intended to be used

	public void writeRowAveCSV(){
		String[] csvList = new String[this.size];
		for(int i = 0; i<this.size; i++){
			csvList[i] = String.valueOf(this.rows[i]) + "\n";
		}
		try{
			FileWriter fw = new FileWriter("C:/Users/mikey/cs136/ColoringCarpets/filetest.txt");
			String csv = String.join("", csvList);
			fw.write(csv);
			fw.flush();
			fw.close();	
		} catch (Exception e)
		{
			System.out.println("Error");
		}
	}

	public void writePolynomialCSV(){
		String[] csvList = new String[(this.size-2)*2];
		for(int i = 2; i<this.size; i++){
			for(int j = 2; j<this.size; j++){
				if(i%2 == 0){
					if(j == 2){
						csvList[2*(i-2)] = this.polynomial[i][j].toString() + ", ";
					}
					else if(j%2 == 0){
						csvList[2*(i-2)] = csvList[2*(i-2)] + this.polynomial[i][j].toString() + ", ";
					}
					else if(j == 3){
						csvList[2*(i-2)+1] = this.polynomial[i][j].toString() + ", ";
					}
					else if(j%2 == 1){
						csvList[2*(i-2)+1] = csvList[2*(i-2)+1] + this.polynomial[i][j].toString() + ", ";
					}
				}
				else{
					if(j == 2){
						csvList[2*(i-2)+1] = this.polynomial[i][j].toString() + ", ";
					}
					else if(j%2 == 0){
						csvList[2*(i-2)+1] = csvList[2*(i-2)+1] + this.polynomial[i][j].toString() + ", ";
					}
					else if(j == 3){
						csvList[2*(i-2)] = this.polynomial[i][j].toString() + ", ";
					}
					else if(j%2 == 1){
						csvList[2*(i-2)] = csvList[2*(i-2)] + this.polynomial[i][j].toString() + ", ";
					}
				}
			}
			csvList[2*(i-2)] = csvList[2*(i-2)] + "\n";
			csvList[2*(i-2)+1] = csvList[2*(i-2)+1] + "\n";
			//System.out.println(csvList[i-2]);
		}
		try{
			String filename = String.format("%s%d%s%s%s%s.txt" ,"split" ,this.size-2,"s1-", inputStarter[0] ,"s2-", inputStarter[1]);
			FileWriter fw = new FileWriter("C:/Users/mikey//ColoringCarpet/" + filename);
			String csv = String.join("", csvList);
			fw.write(csv);
			fw.flush();
			fw.close();	
		} catch (Exception e)
		{
			System.out.println("Error");
		}
	}


	public void writeAddCSV(BigInteger a[][], BigInteger b[][]){
		String[] csvList = new String[a.length];
		for(int i = 1; i<this.size; i++){
			for(int j = 2; j<this.size;j++ ){
				//if(!(i == 0 & j == 0)){
				BigInteger aAB= a[i-1][j-2].abs(); 
				BigInteger bAB= b[j][i].abs(); 
					if(j == 2){
						csvList[i] = (aAB.subtract(bAB)).toString() + ", ";
					}
					else{
						csvList[i] = csvList[i] + (aAB.subtract(bAB)).toString() + ", ";
					}
				//}
				/*else{
					csvList[i] = "null, ";
				}*/
			}
			csvList[i] = csvList[i] + "\n";
		}
		for(int i = 1; i<5;i++){
			for(int j = 2; j<5;j++ ){
				System.out.println( i + " " + j +" " + a[i-1][j-2].subtract(b[j][i]) + " " + a[i-1][j-2] + " " + b[j][i]);
			}
		}
		try{
			String filename = String.format("%s%d.txt" ,"addition" ,this.size);
			FileWriter fw = new FileWriter("C:/Users/mikey//ColoringCarpet/" + filename);
			String csv = String.join("", csvList);
			fw.write(csv);
			fw.flush();
			fw.close();	
		} catch (Exception e)
		{
			System.out.println("Error");
		}
	}

	public void createCordSeq(){
		this.cordSeq = new BigInteger[polynomial.length][polynomial.length];
		for(int i = 0; i<polynomial.length;i++){
			for(int j = 0; j<polynomial.length;j++){
				if((i+j)%2 == 0){
					cordSeq[i][j] = polynomial[i][j];
				}
				else{
					cordSeq[j][i] = polynomial[i][j];
				}
			}
		}
	}

	public void writePolynomialCordSeqCSV(){
		createCordSeq();
		String[] csvList = new String[this.size];
		for(int i = 0; i<this.size; i++){
			for(int j =0; j<this.size;j++ ){
				if(!(i == 0 & j == 0)){
					if(j == 0){
						csvList[i] = this.cordSeq[i][j].toString() + ", ";
					}
					else{
						csvList[i] = csvList[i] + this.cordSeq[i][j].toString() + ", ";
					}
				}
				else{
					csvList[i] = "null, ";
				}
			}
			csvList[i] = csvList[i] + "\n";
		}
		try{
			String filename = String.format("%s%d%s%s%s%s.txt" ,"CordSeq", this.size,"sV-", inputStarter[0] ,"sH-", inputStarter[1]);
			FileWriter fw = new FileWriter("C:/Users/mikey//ColoringCarpet/" + filename);
			String csv = String.join("", csvList);
			fw.write(csv);
			fw.flush();
			fw.close();	
		} catch (Exception e)
		{
			System.out.println("Error");
		}
	}

	public void diagonalStaterCSV(){
		String[] csvList = new String[this.size];
		for(int i = 0; i < this.size; i++){
			csvList[i] = Integer.toString(this.carp[i+1][i]) + ", " + Integer.toString(this.carp[i+1][i+1]) + "\n";
		}
		try{
			String filename = String.format("%s%d%s%s%s%s.txt" ,"Diagonals", this.size,"sV-", inputStarter[0] ,"sH-", inputStarter[1]);
			FileWriter fw = new FileWriter("C:/Users/mikey//ColoringCarpet/" + filename);
			String csv = String.join("", csvList);
			fw.write(csv);
			fw.flush();
			fw.close();	
		} catch (Exception e)
		{
			System.out.println("Error");
		}
	}

	public void diagonalFullCSV(){
		String[] csvList = new String[this.size];
		for(int i = 0; i < this.size; i++){
			csvList[i] = Integer.toString(this.carp[i+1][i]) + ", " + Integer.toString(this.carp[i+1][i+1]) + ", ";
			for(int j = 1; 2*j+i<size; j++){
				if(2*(j+1)+i > size){
					csvList[i] = csvList[i] + Integer.toString(this.carp[i+1][i+2*j]) + ", " + Integer.toString(this.carp[i][i+2*j]);
				}
				else{
					csvList[i] = csvList[i] + Integer.toString(this.carp[i+1][i+2*j]) + ", " + Integer.toString(this.carp[i][i+2*j]) + ", ";
				}
			}
			csvList[i] = csvList[i] + "\n";
		}
		try{
			String filename = String.format("%s%d%s%s%s%s.txt" ,"TOP_Diagonals", this.size,"sV-", inputStarter[0] ,"sH-", inputStarter[1]);
			FileWriter fw = new FileWriter("C:/Users/mikey//ColoringCarpet/" + filename);
			String csv = String.join("", csvList);
			fw.write(csv);
			fw.flush();
			fw.close();	
		} catch (Exception e)
		{
			System.out.println("Error");
		}
	}

	public void writePolynomialCarpCSV(){
		String[] csvList = new String[this.size];
		for(int i = 0; i<this.size; i++){
			for(int j =0; j<this.size;j++ ){
				if(!(i == 0 & j == 0)){
					if(j == 0){
						csvList[i] = this.polynomial[i][j].toString() + ", ";
					}
					else{
						csvList[i] = csvList[i] + this.polynomial[i][j].toString() + ", ";
					}
				}
				else{
					csvList[i] = "null, ";
				}
			}
			csvList[i] = csvList[i] + "\n";
		}
		try{
			if(astrik){
				String filename = String.format("%s%d%s.txt" ,"RowsTogether", this.size,"periodicFringe");
				FileWriter fw = new FileWriter("C:/Users/mikey//ColoringCarpet/" + filename);
				String csv = String.join("", csvList);
				fw.write(csv);
				fw.flush();
				fw.close();	
			}else{
				String filename = String.format("%s%d%s%s%s%s.txt" ,"RowsTogether", this.size,"sV-", inputStarter[0] ,"sH-", inputStarter[1]);
				FileWriter fw = new FileWriter("C:/Users/mikey//ColoringCarpet/" + filename);
				String csv = String.join("", csvList);
				fw.write(csv);
				fw.flush();
				fw.close();	
			}
		} catch (Exception e)
		{
			System.out.println("Error");
		}
	}

public static void main(String[] args){
		if(args[0].equals("diff")){ 
			if(args.length == 4){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            Carpet2 c1 = new Carpet2(size, starters1);
	            Carpet2 c2 = new Carpet2(size, starters2);
	            Carpet2 c3 = new Carpet2(c1, c2, -1);
	            c3.createKnotCarpet(false);
			}else if(args.length == 5){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            int p = Integer.valueOf(args[4]);
	            Carpet2 c1 = new Carpet2(size,p, starters1);
	            Carpet2 c2 = new Carpet2(size,p, starters2);
	            Carpet2 c3 = new Carpet2(c1, c2, -1);
	            c3.createKnotCarpet(false);
			}else if(args.length == 6){
				if(args[5].toLowerCase().equals("diag")){
					int size = Integer.valueOf(args[1]);
					String chosenStarter1 = args[2];
		            String[] starters1 = chosenStarter1.split(",");
		            String chosenStarter2 = args[3];
	    	        String[] starters2 = chosenStarter2.split(",");
	        	    int p = Integer.valueOf(args[4]);
	            	Carpet2 c1 = new Carpet2(size,p, starters1);
	        	    Carpet2 c2 = new Carpet2(size,p, starters2);
	            	Carpet2 c3 = new Carpet2(c1, c2, -1);
	            	//c3.createKnotCarpetDiag();
				}else{
					int size = Integer.valueOf(args[1]);
					String chosenStarter1 = args[2];
	            	String[] starters1 = chosenStarter1.split(",");
	            	String chosenStarter2 = args[3];
	            	String[] starters2 = chosenStarter2.split(",");
	            	int p1 = Integer.valueOf(args[4]);
	            	int p2 = Integer.valueOf(args[5]);
	            	Carpet2 c1 = new Carpet2(size,p1, starters1);
	            	Carpet2 c2 = new Carpet2(size,p2, starters2);
	            	Carpet2 c3 = new Carpet2(c1, c2, -1);
	            	c3.createKnotCarpet(false);
				}
			}
		}
		else if(args[0].equals("prod")){
			if(args.length == 4){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            Carpet2 c1 = new Carpet2(size, starters1);
	            Carpet2 c2 = new Carpet2(size, starters2);
	            Carpet2 c3 = new Carpet2(c1, c2, 0);
	            c3.createKnotCarpet(false);
			}else if(args.length == 5){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            int p = Integer.valueOf(args[4]);
	            Carpet2 c1 = new Carpet2(size,p, starters1);
	            Carpet2 c2 = new Carpet2(size,p, starters2);
	            Carpet2 c3 = new Carpet2(c1, c2, 0);
	            c3.createKnotCarpet(false);
			}else if(args.length == 6){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            int p = Integer.valueOf(args[4]);
	            Carpet2 c1 = new Carpet2(size,p, starters1);
	            Carpet2 c2 = new Carpet2(size,p, starters2);
	            Carpet2 c3 = new Carpet2(c1, c2, 0);
	            //c3.createKnotCarpetDiag();
			}
		}
		else if(args[0].equals("sum")){
			if(args.length == 4){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            Carpet2 c1 = new Carpet2(size, starters1);
	            Carpet2 c2 = new Carpet2(size, starters2);
	            Carpet2 c3 = new Carpet2(c1, c2, 1);
	            c3.createKnotCarpet(false);
			}else if(args.length == 5){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            int p = Integer.valueOf(args[4]);
	            Carpet2 c1 = new Carpet2(size,p, starters1);
	            Carpet2 c2 = new Carpet2(size,p, starters2);
	            Carpet2 c3 = new Carpet2(c1, c2, 1);
	            c3.createKnotCarpet(false);
			}else if(args.length == 6){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            int p = Integer.valueOf(args[4]);
	            Carpet2 c1 = new Carpet2(size,p, starters1);
	            Carpet2 c2 = new Carpet2(size,p, starters2);
	            Carpet2 c3 = new Carpet2(c1, c2, 1);
	            if(args[5].equals("minus")){
	            	//c3.createKnotCarpetDiagMinus(true);
	            }
	            else{
	            	//c3.createKnotCarpetDiag();
	            }
			}
		}
		else{
			if(args.length == 1){
				//int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997};
				//String[] t = {"012111000102102102021120012111111111111111111111111111000102021120120120012111000","021111120210210210222012021111111111111111111111111111120210222012012012021111120"};
				/*for(int i = 0; i<82; i++){
					String t1 = t[0].substring(0,i+1) + "_0";
					String t2 = t[1].substring(0,i+1) + "_0";
					String[] t_star = {t2,t1}; 
					//System.out.println(t_star);
					Carpet c1 = new Carpet(81, 3, t_star);
					c1.createKnotCarpetDiagMinus(true);
				}	*/				
				
				//int size = Integer.valueOf(args[0]);
				//
				//int size = 243;
				/*for(int x = 0; x<3; x++){
					for(int y = 0; y<3; y++){
						for(int z = 0; z<3; z++){
							String temp1 = "_"+ Integer.toString(x) + Integer.toString(y) + Integer.toString(z);
							String temp2 = Integer.toString(x) + Integer.toString(y) + Integer.toString(z) + "_0";
							String[] tempa1 = {temp1,temp1};
							String[] tempa2 = {temp2,temp2};
							Carpet cr = new Carpet(size, tempa1);
							Carpet cs = new Carpet(size, tempa2);
							cr.createKnotCarpet(false);
							cs.createKnotCarpet(false);
							cr.diagonalStaterCSV();
							cs.diagonalStaterCSV();
						}
					}
				}*/
				/*int size = 243;
						int p = 9;
						//String chosenStarter = "_172247827752,_247827752172";
						String chosenStarter = "_1122,_2112";
						String[] starters = chosenStarter.split(",");
						int firstty = 2;
						Carpet c1 = new Carpet(size, p, starters, firstty);
						c1.whiteSquareCarpetOneGo();*/
						//c1.CreatewhiteSquareCarpet(p);
				/*for(int i = 3; i < 27; i++){
					if(primes[i]*primes[i]*primes[i] < 9100){
						int size = primes[i]*primes[i]*primes[i]+1;
						int p = primes[i];
						String chosenStarter = "_0,_0";
						String[] starters = chosenStarter.split(",");
						int firstty = 2;
						Carpet c1 = new Carpet(size, p, starters, firstty);
						c1.whiteSquareCarpetOneGo();
					}
					else if(primes[i]*primes[i] < 9100){
						int size = primes[i]*primes[i]+1;
						int p = primes[i];
						String chosenStarter = "_0,_0";
						String[] starters = chosenStarter.split(",");
						int firstty = 2;
						Carpet c1 = new Carpet(size, p, starters, firstty);
						c1.whiteSquareCarpetOneGo();
					}
					else{
						int size = primes[i]*primes[i]+1;
						int p = primes[i];
						String chosenStarter = "_0,_0";
						String[] starters = chosenStarter.split(",");
						int firstty = 2;
						Carpet c1 = new Carpet(size, p, starters, firstty);
						c1.whiteSquareCarpetOneGo();
					}*/ 
					/*int size = 250;
					String startTemp = "";
					for(int i = 0; i<250; i++){
						String chosenStarter = startTemp +"1_0,_0";
						int p = 3;
    	       			String[] starters = chosenStarter.split(",");
	            		Carpet c1 = new Carpet(size+i,p, starters);
						startTemp = startTemp + "0";
						c1.createKnotCarpetDiagMinus(true);
					}*/
					/*BigInteger[][] uh = c1.getWhiteSqaureCarp();
					for(int j = 0; j<uh.length; j++){
						for(int k = 0; k<uh[j].length; k++){
							System.out.print(uh[j][k]);
						}
						System.out.println();
					}
					//System.out.println();
				}*/
			}
			else if(args.length == 2){
				int size = Integer.valueOf(args[0]);
				String chosenStarter = args[1];				
	            String[] starters = chosenStarter.split(",");
	            //System.out.println(starters.length);
	            if(starters.length == 4){
	            	//Carpet c1 = new Carpet(size, 3, starters, 0);
	            	//c1.createKnotCarpetSquare();
	            	//int temp[][] = c1.getPolynomial();
	            }else{
	            	Carpet2 c1 = new Carpet2(size, starters);
		            c1.createKnotCarpet(false);
		            //BigInteger temp[][] = c1.getPolynomial();
		            /*for(int i = 2; i<10; i++){
		            	for(int j = 2; j<10; j++){
		            		System.out.print(temp[i][j] + ", ");
		            	}
		            	System.out.println();
		            }*/
		            //c1.writePolynomialCSV();
		            //c1.writePolynomialCSV();
		            /*c1.whiteSquareCarpet();
		            c1.writeAddCSV(c1.getWhiteSqaureCarp(),temp);
		            c1.CreatewhiteSquareCarpet(13);*/
	            }
	            //String starterSum = "";
	            /*
	            for(int i = 0; i<size-1; i++){
	            	starterSum = starterSum + starters[0].substring((i%(starters[0].length()-1))+1, (i%(starters[0].length() -1))+2);
	            	String temp = starterSum + "_0";
	            	String[] tempy = {temp,temp};
	            	Carpet c2 = new Carpet(size, tempy);
	            	c2.createKnotCarpet();
	            }*/
	            /*
	            String starterSum = "_";
	            for(int i = 0; i<size-1; i++){
	            	starterSum = starterSum + starters[0].substring((i%(starters[0].length()-1))+1, (i%(starters[0].length() -1))+2);
	            	String temp = starterSum;
	            	String[] tempy = {temp,temp};
	            	Carpet c2 = new Carpet(size, tempy);
	            	c2.createKnotCarpet();
	            }*/
	            /*Strand[][] Carpy = c1.getCarp();
	            for(int i = 0; i<6; i++){
	            	for(int j = 0; j<6; j++){
	            		System.out.print(Carpy[i][j]);
	            	}
	            	System.out.println();
	            }*/
			}
			else if(args.length == 3){
				if(args[0].toLowerCase().equals("getdiagstarter")){
					int size = Integer.valueOf(args[1]);
					String chosenStarter = args[2];	
					String[] starters = chosenStarter.split(",");
					Carpet2 c1 = new Carpet2(size, starters);
		            c1.createKnotCarpet(false);
		            c1.diagonalStaterCSV();
				}
				else if(args[0].toLowerCase().equals("getdiagtop")){
					int size = Integer.valueOf(args[1]);
					String chosenStarter = args[2];	
					String[] starters = chosenStarter.split(",");
					Carpet2 c1 = new Carpet2(size, starters);
		            c1.createKnotCarpet(false);
		            c1.diagonalFullCSV();
				}
				else if(args[2].equals("true")){
					int size = Integer.valueOf(args[0]);
					String chosenStarter = args[1];				
	            	String[] starters = chosenStarter.split(",");
					Carpet2 c1 = new Carpet2(size, starters);
		            c1.createKnotCarpet(true);
		            c1.writePolynomialCarpCSV();
				}else{
					int size = Integer.valueOf(args[0]);
					String chosenStarter = args[1];
					int p = Integer.valueOf(args[2]);
    	       		String[] starters = chosenStarter.split(",");
	            	Carpet2 c1 = new Carpet2(size, p , starters);
            		c1.createKnotCarpet(false);
            		//System.out.println(c1.carp[1][2].getColor());

				}
			}
			else if(args.length == 5){
				if(args[0].equals("tiling")){
					int size = Integer.valueOf(args[1]);
					int p = Integer.valueOf(args[2]);
					String chosenStarter = args[3];
					String[] starters = chosenStarter.split(",");
					int firstty = Integer.valueOf(args[4]);
					Carpet2 c1 = new Carpet2(size, p, starters, firstty);
					//c1.whiteSquareCarpetOneGo(); WELL ADD THIS LATER

					/*for(int i = 0; i<200; i++){
						String temp = "";
						String[] starty = new String[2];
						for(int j = 0; j<i;j++){
							temp = temp + "0";
						}
						temp = temp + "1_0";
						starty[0] = temp;
						starty[1] = temp;
						Carpet c2 = new Carpet(2187, 3, starty, 0);
						c2.whiteSquareCarpetOneGo();
					}*/
				}/*else{
					int size = Integer.valueOf(args[0]);
					int sizeBrick = Integer.valueOf(args[1]); 
					int p = Integer.valueOf(args[2]);
					String chosenStarter = args[3];
					String[] starters = chosenStarter.split(",");
					String starterLoc = args[4];
					String[] starterlocation = starterLoc.split(",");
					Carpet c1 = new Carpet(size, p, sizeBrick, starters, starterlocation);
					//c1.createKnotCarpetBrick();
					Strand[][] Carpy = c1.getCarp();
					/*for(int i = 0; i<Carpy.length-1; i++){
		            	for(int j = 0; j<Carpy.length-1; j++){
		            		System.out.print(Carpy[j][i] + " ");
		            	}
		            	System.out.println();
		            }
				}*/
			}
			else if(args.length == 6){
				int size = Integer.valueOf(args[1]);
				String chosenStarter = args[2];
				String[] starters = chosenStarter.split(",");
				int p = Integer.valueOf(args[3]);
				int acof = Integer.valueOf(args[4]);
				int bcof = Integer.valueOf(args[5]);
				Carpet2 c1 = new Carpet2(size, p, starters, acof, bcof);
				c1.createGeneralizedKnotCarpet(false);
			}
			else if(args.length == 7){
				if(args[0].equals("find")){
					int size = Integer.valueOf(args[1]);
					String chosenStarter = args[2];
					int p = Integer.valueOf(args[3]);
					int REALp = Integer.valueOf(args[4]);
					int N = Integer.valueOf(args[5]);
    	       		String[] starters = chosenStarter.split(",");
	            	Carpet2 c1 = new Carpet2(size, p , starters);
	            	int s = Integer.valueOf(args[6]);
            		c1.createKnotCarpetImageless(false);
            		c1.findNbricks(REALp, N,s);
				}
				else if(args[0].equals("findG")){
					int size = Integer.valueOf(args[1]);
					String chosenStarter = args[2];
					int p = Integer.valueOf(args[3]);
					int REALp = Integer.valueOf(args[4]);
					int N = Integer.valueOf(args[5]);
    	       		String[] starters = chosenStarter.split(",");
	            	Carpet2 c1 = new Carpet2(size, p , starters);
            		c1.createKnotCarpetImageless(false);
            		int s = Integer.valueOf(args[6]);
            		//System.out.println(Math.pow(p, 1/REALp) + " " + s);
            		c1.findNgrout(REALp, N, s);
				}
				else{
					int size = Integer.valueOf(args[1]);
					String chosenStarter = args[2];
					String[] starters = chosenStarter.split(",");
					int p = Integer.valueOf(args[3]);
					int acof = Integer.valueOf(args[4]);
					int bcof = Integer.valueOf(args[5]);
					String ore = args[6];
					String[] orie = ore.split(",");
					Carpet2 c1 = new Carpet2(size, p, starters, acof, bcof, orie);
					c1.createGeneralizedKnotCarpet(false);
				}
			}
		}
	}
}