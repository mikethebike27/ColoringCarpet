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


public class Carpet{
	private Strand[][] carp; // Double array of each strand in the carpet. It is used to generate the image. 
	//First coord is the row and second is the column so [1][2] is the 2nd row and 3rd column or down 1 to the left 2
	private BigInteger[][] whiteSqaureCarp;
	private int colorablity = 3; //This is the number of colors in a carpet and it is important for the way the carpet is generated
	private boolean twoD = true; //Useless currently future uses maybe?
	private Strand[][] Starter;// Used to create carpet for knot theory carpets
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
								//System.out.println(indexSecondPart+1);
								color = Integer.parseInt(star.substring(indexSecondPart+1,indexSecondPart+3));
								//System.out.println(color); 
								Strand start = new Strand(i,color,colorablity);
								astrik = true;
								this.Starter[i][j-count2] = start;
							}
							else if(star.charAt(indexSecondPart-3) == '*' || star.charAt(indexSecondPart-2) == '*' || star.charAt(indexSecondPart-1) == '*'){
								//color = -1;
								count2++;
							}else{
								//System.out.println(star.substring(indexSecondPart,indexSecondPart+1));
								color = Integer.parseInt(star.substring(indexSecondPart,indexSecondPart+1));
								Strand start = new Strand(i,color,colorablity);
								this.Starter[i][j-count2] = start;
							}
						}
						else{
							if(star.charAt(indexSecondPart) == '*'){
								//System.out.println(indexSecondPart+1);
								color = Integer.parseInt(star.substring(indexSecondPart+1,indexSecondPart+3));
								//System.out.println(color); 
								Strand start = new Strand(i,color,colorablity);
								astrik = true;
								this.Starter[i][j-count2] = start;
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
								Strand start = new Strand(i,color,colorablity);
								this.Starter[i][j-count2] = start;
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
						Strand start = new Strand(i,color,colorablity);
						//System.out.println("Strand " + i + " " + j + " " + start);
						this.Starter[i][j] = start;
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
								Strand start = new Strand(i,color,colorablity);
								astrik = true;
								this.Starter[i][j-count2+firstPartCount] = start;
							}
							else if(decimals[1].charAt(indexSecondPart-3) == '*' || decimals[1].charAt(indexSecondPart-2) == '*' || decimals[1].charAt(indexSecondPart-1) == '*'){
								//color = -1;
								count2++;
							}else{
								//System.out.println(star.substring(indexSecondPart,indexSecondPart+1));
								color = Integer.parseInt(decimals[1].substring(indexSecondPart,indexSecondPart+1));
								Strand start = new Strand(i,color,colorablity);
								this.Starter[i][j-count2+firstPartCount] = start;
							}
						}
						else{
								color = Integer.parseInt(decimals[1].substring(indexSecondPart,indexSecondPart+1));
								Strand start = new Strand(i,color,colorablity);
								this.Starter[i][j-count2+firstPartCount] = start;
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
	public Carpet(int size, String[] starter){
		this.Starter = new Strand[4][size];
		this.rows = new double[size];
		this.size = size;
		this.polynomial = new BigInteger[size+1][size+1];
		createStarter(starter);
		this.carp = new Strand[size+1][size+1]; 
	}

	/*2*/
	public Carpet(int size, int p, String[] starter){
		this.Starter = new Strand[4][size];
		//System.out.println(p);
		this.colorablity = p;
		//System.out.println(colorablity);
		this.rows = new double[size];
		this.size = size;
		this.polynomial = new BigInteger[size+1][size+1];
		createStarter(starter);
		this.carp = new Strand[size+1][size+1]; 
	}

	public Carpet(int size, int p){
		this.colorablity = p;
		this.size = size;
	}

	/*3*/
	public Carpet(Carpet C1, Carpet C2, int opper){
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
		this.Starter = new Strand[4][this.size];
		this.rows = new double[size];

		Strand[][] Starter1 = C1.getStarter();
		/*for(int i = 0; i<numRows;i++){
			for(int j = 0; j<this.size; j++){
				System.out.println(Starter1[i][j]);
			}
		}*/
		this.polynomial = new BigInteger[size+1][size+1];
		Strand[][] Starter2 = C2.getStarter();
		for(int i = 0; i < 2; i++){
			for(int j = 0; j<this.size; j++){
				int Color1 = Starter1[i][j].getColor();
				int Color2 = Starter2[i][j].getColor();
				int newColor = 0;
				if(opper == 1){
					newColor = Math.floorMod((Color1 + Color2),this.colorablity);
				}else if(opper == -1){
					newColor = Math.floorMod((Color1 - Color2),this.colorablity);
				}
				else if(opper == 0){
					newColor = Math.floorMod((Color1*Color2),this.colorablity);
				}
				Strand start = new Strand(i, newColor, true);
				this.Starter[i][j] = start;
			}
		}
		this.carp = new Strand[this.size+1][this.size+1];
	}

// the constructor for getting the generalized diagonal formula

	/*public Carpet(int size, int length){
		
		this.rows = new double[size];
		this.size = size;
		//this.polynomial = new BigInteger[size+1][size+1];
		//createStarter(starter);
		//this.carp = new Strand[size+1][size+1]; 
	}*/

	/*
		brick version
	*/
	public void createStarterBrick(String[] starter, String[] locations){
		//makes bricks 
		for(int i = 0; i<Starter.length; i++){
			for(int j = 0; j<starter[i].length(); j++){
				int color = Integer.parseInt(starter[i].substring(j,j+1));
				Strand start = new Strand(i,color,true); 
				this.Starter[i][j] = start;
			}
		}	
		//makes brick location
		boolean ended = false;
		for(int i = 0; i<locations.length; i++){
			String temp = locations[i];
				//this if statement goes directly into the repeating portion of the starter pattern 
				if(temp.charAt(0) == '_'){
					int count = 0;
					int secondPartCount = size;
					String star = temp.substring(1,temp.length());
					int repeatCount = star.length(); //In example "_012" this is 3
					for(int j=0; j<secondPartCount; j++){
						int indexSecondPart = j % repeatCount;
						int loc;
						boolean twoDig = false;
						if(star.substring(indexSecondPart,indexSecondPart+1).equals("'")){
							loc = Integer.parseInt(star.substring(indexSecondPart+1,indexSecondPart+3));
							twoDig = true;
						}else{
							loc = Integer.parseInt(star.substring(indexSecondPart,indexSecondPart+1));
						}
						this.locationBrick[i][j] = loc;
						if(twoDig){
							j = j+2;
						}
					}
				} else{
					String[] decimals = temp.split("_");
					int firstPartCount = decimals[0].length();
					for(int j = 0; j<firstPartCount; j++){
						if(j == size){
							ended = true;
							break;
						}
						//System.out.println(j<firstPartCount);
						int loc;
						boolean twoDig = false;
						if(decimals[0].substring(j,j+1).equals("'") ){
							loc = Integer.parseInt(decimals[0].substring(j+1,j+3));
							twoDig = true;
						}
						else{
							loc = Integer.parseInt(decimals[0].substring(j,j+1));
						}
						this.locationBrick[i][j] = loc;
						//System.out.println(i + "," + j + ":" + this.locationBrick[i][j]);
						if(twoDig){
							j = j+2;
						}
					}
					int count = 0;
					int secondPartCount = (size+1) - firstPartCount;
					int repeatCount = decimals[1].length(); //In example "1_012" this is 3 
					if(!ended){
						for(int j=0; j<secondPartCount; j++){
							int indexSecondPart = j % repeatCount; 
							int loc;
							boolean twoDig = false;
							if(decimals[1].substring(indexSecondPart,indexSecondPart+1).equals("'")){
								loc = Integer.parseInt(decimals[1].substring(indexSecondPart+1,indexSecondPart+3));
								twoDig = true;
							}else{
								loc = Integer.parseInt(decimals[1].substring(indexSecondPart,indexSecondPart+1));
							}
							//System.out.println(loc);
							this.locationBrick[i][j] = loc;
							//System.out.println(i + "," + j + ":" + this.locationBrick[i][j]);
							//System.out.println(this.locationBrick[i][j]);
							if(twoDig){
								j = j+2;
							}
						}
					}
				}
			}
		}

	public Carpet(int size, int p, int brickSize, String[] starterPiece, String[] locations){
		//this.polynomial = new int[size+1][size+1];
		//this.brickStarter = starterPiece;
		this.Starter = new Strand[starterPiece.length][brickSize*2];
		this.locationBrick = new int[starterPiece.length-1][size];
		this.colorablity = p;
		this.size = size;
		this.brickSize = brickSize;
		createStarterBrick(starterPiece, locations);
		this.carp = new Strand[2+(size+1)*(brickSize+1)][2+(size+1)*(brickSize+1)]; 
	}

	
	public Carpet(int size, int p, String[] starter, int first){
		this.Starter = new Strand[4][size];
		this.colorablity = p;
		this.firstSp = first;
		//this.rows = new double[size];
		this.size = size;
		//this.polynomial = new BigInteger[size+1][size+1];
		createStarter(starter);
		this.carp = new Strand[size+1][size+1];
	}

	public Carpet(int size, int p, String[] starter, int a, int b){
		this.Starter = new Strand[4][size];
		this.rows = new double[size];
		this.size = size;
		this.polynomial = new BigInteger[size+1][size+1];
		createStarter(starter);
		this.carp = new Strand[size+1][size+1];
		this.colorablity = p;
		this.orientation = new int[2][size];
		this.aCoef = a;
		this.bCoef = b;
		for(int i = 0; i<size; i++){
			orientation[0][i] = 0;
			orientation[1][i] = 0;
		}
	}

	public Carpet(int size, int p, String[] starter, int a, int b, String[] orient){
		this.Starter = new Strand[4][size];
		this.rows = new double[size];
		this.size = size;
		this.polynomial = new BigInteger[size+1][size+1];
		createStarter(starter);
		this.carp = new Strand[size+1][size+1];
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
								//System.out.println(color); 
								//Strand start = new Strand(i,color,colorablity);
								astrik = true;
								this.orientation[i][j-count2] = color;
							}
							else if(star.charAt(indexSecondPart-3) == '*' || star.charAt(indexSecondPart-2) == '*' || star.charAt(indexSecondPart-1) == '*'){
								//color = -1;
								count2++;
							}else{
								//System.out.println(star.substring(indexSecondPart,indexSecondPart+1));
								color = Integer.parseInt(star.substring(indexSecondPart,indexSecondPart+1));
								//Strand start = new Strand(i,color,colorablity);
								this.orientation[i][j-count2] = color;
							}
						}
						else{
							if(star.charAt(indexSecondPart) == '*'){
								//System.out.println(indexSecondPart+1);
								color = Integer.parseInt(star.substring(indexSecondPart+1,indexSecondPart+3));
								//System.out.println(color); 
								//Strand start = new Strand(i,color,colorablity);
								astrik = true;
								this.orientation[i][j-count2] = color;
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
						//Strand start = new Strand(i,color,colorablity);
						//System.out.println("Strand " + i + " " + j + " " + start);
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
								//System.out.println(color); 
								//Strand start = new Strand(i,color,colorablity);
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
	public Carpet(int size, int p, String[] starter, String location, String template){
		this.size = size;
		//this.twoD = twoD;
		this.colorablity = p;
		this.rows = new double[size];
		this.Starter = new Strand[4][size];
		//maybe make this into a helper method
		createStarter(starter);
		if(!isIn(this.templates,this.template)) throw new IllegalArgumentException("invalid template");
		if(!isIn(this.locations,this.location)) throw new IllegalArgumentException("invlid location");
		this.location = location;
		this.template = template;
		this.carp = new Strand[size+1][size+1];
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


	//this method only works for Q4 carpets
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
					Strand start = new Strand(i,j);
					start.setColor(-1);
					if(polynom){
						polynomial[i][j] = BigInteger.ZERO;
					}
					carp[i][j] = start;
					for (int x = i*squareSize; x < (i + 1)*squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, start.getRealColor().getRGB());
                    	}
                	}
				} else if(i == 0 || (i == 1 && (j%2) == 1)){
					if(Starter[0][j-1].getColor() == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[0][j-1].getColor()));
					}
					carp[i][j] = Starter[0][j-1];
					for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}
				} else if(j == 0){
					if(Starter[1][i-1].getColor() == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[1][i-1].getColor()));
					}
					carp[i][j] = Starter[1][i-1];
					for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}
				} else if(i == 1 & j == 1){
					if(Starter[0][j-1].getColor() == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[0][j-1].getColor()));
					}
					carp[i][j] = Starter[0][j-1];
					for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}
				}
				/*else if(i == 1 && j%2 == 1){
					if(Starter[0][j-1].getColor() == 0){
						zeroCount++;
						rowZeroCount++;
					}
					carp[i][j] = Starter[0][j-1];
				} */else if(j == 1 && i%2 == 0){
					if(Starter[1][i-1].getColor() == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[1][i-1].getColor()));
					}
					carp[i][j] = Starter[1][i-1];
					for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}
				} else{
					if((i + j)%2 == 1){
						Strand start = new Strand(i,j);
						int temp;
						int color;
						if((orientation[1][i-1] == 0)){
							temp = (aCoef+bCoef)*(carp[i][j-1].getColor())-aCoef*carp[i][j-2].getColor();
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
							temp = (aCoef+bCoef)*(carp[i][j-1].getColor())-bCoef*carp[i][j-2].getColor();
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
						//BigInteger temp2 = two.multiply(polynomial[i][j-1]).subtract(polynomial[i][j-2]);
						//polynomial[i][j] = temp2;
						//int color = Math.floorMod(temp,colorablity);
						if(color == 0){
							zeroCount++;
							rowZeroCount++;
						}
						if(i == j+1){
							//System.out.println(i+","+j + ": " + color);
						}
						//polynomial[i][j] = temp2;
						start.setColor(color);
						carp[i][j] = start;
						for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        		img.setRGB(y,x, start.getRealColor().getRGB());
                    		}
                		}
					}
					else{
						Strand start = new Strand(i,j);
						int temp;
						int color;
						if((orientation[0][j-1] == 1)){
							temp = (carp[i-1][j].getColor())-aCoef*carp[i-2][j].getColor();
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
							temp = (aCoef+bCoef)*(carp[i-1][j].getColor())-(bCoef*carp[i-2][j].getColor());
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
						start.setColor(color);
						carp[i][j] = start;
						for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        		img.setRGB(y,x, start.getRealColor().getRGB());
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
					Strand start = new Strand(i,j);
					start.setColor(-1);
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
					if(Starter[0][j-1].getColor() == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[0][j-1].getColor()));
					}
					carp[i][j] = Starter[0][j-1];
					/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}*/
				} else if(j == 0){
					if(Starter[1][i-1].getColor() == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[1][i-1].getColor()));
					}
					carp[i][j] = Starter[1][i-1];
					/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}*/
				} else if(i == 1 & j == 1){
					if(Starter[0][j-1].getColor() == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[0][j-1].getColor()));
					}
					carp[i][j] = Starter[0][j-1];
					/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}*/
				}
				/*else if(i == 1 && j%2 == 1){
					if(Starter[0][j-1].getColor() == 0){
						zeroCount++;
						rowZeroCount++;
					}
					carp[i][j] = Starter[0][j-1];
				} */else if(j == 1 && i%2 == 0){
					if(Starter[1][i-1].getColor() == 0){
						zeroCount++;
						rowZeroCount++;
					}
					if(polynom){
						polynomial[i][j] = new BigInteger(Integer.toString(Starter[1][i-1].getColor()));
					}
					carp[i][j] = Starter[1][i-1];
					/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}*/
				} else{
					if((i + j)%2 == 1){
						Strand start = new Strand(i,j);
						int temp = 2*(carp[i][j-1].getColor())-carp[i][j-2].getColor();
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
						//polynomial[i][j] = temp2;
						start.setColor(color);
						carp[i][j] = start;
						/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        		img.setRGB(y,x, start.getRealColor().getRGB());
                    		}
                		}*/
                		start = null;
                		//if(i == j && i%1000 == 0){
                		//	System.gc();
                		//}
                		//System.gc();
					}
					else{
						Strand start = new Strand(i,j);
						int temp = 2*(carp[i-1][j].getColor())-carp[i-2][j].getColor();
						if(polynom){
							BigInteger temp2 = two.multiply(polynomial[i-1][j]).subtract(polynomial[i-2][j]);
							polynomial[i][j] = temp2;
						}
						int color = Math.floorMod(temp,colorablity);
						if(color == 0){
							zeroCount++;
							rowZeroCount++;
						}
						if(i == j && i%500 == 0){
							System.out.println(i+","+j + ": " + color);
						}
						start.setColor(color);
						carp[i][j] = start;
						/*for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    		for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        		img.setRGB(y,x, start.getRealColor().getRGB());
                    		}
                		}*/
                		start = null;
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



	/*

	*/


	
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
							System.out.println((i-1) + " and " + j + ": "+ carp[i-1][j].getColorSversus() + " " + carp[i-1][j]);
							System.out.println((i-2) + " and " + j + ": "+ carp[i-2][j].getColorSversus() + " " + carp[i-2][j]);
							System.out.println((i+1) + " and " + j + ": "+ carp[i+1][j].getColorSversus() + " " + carp[i+1][j]);
							System.out.println((i+2) + " and " + j + ": "+ carp[i+2][j].getColorSversus() + " " + carp[i+2][j]);
							System.out.println(i + " and " + (j-1) + ": "+ carp[i][j-1].getColorSversus() + " " + carp[i][j-1]);
							System.out.println(i + " and " + (j+1) + ": "+ carp[i][j+1].getColorSversus() + " " + carp[i][j+1]);


							System.out.println(color);
						}*/
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
	}

	public void createKnotCarpetBrick(){
		final int desiredImageSize = 10 * 550;

        int gridSize = 2+(size+1)*(brickSize+1);

        int squareSize = desiredImageSize/gridSize;
        if(desiredImageSize/gridSize == 0){
        	System.out.println(desiredImageSize/gridSize);
        	squareSize = 1;
        }
        else{
        	squareSize = desiredImageSize/gridSize;
        }
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

		for(int i = 0; i < (size+1); i++){
			boolean jUP1 = false;
			boolean jUP2 = false;
			for(int j = 0; j < brickSize; j++){
				if(i == 0 && j == 0){
					//dont forget graphics
					carp[0][1] = Starter[0][0];

					for (int x = (0) * squareSize; x < (1) * squareSize; x++) {
                   		for (int y = (1) * squareSize; y < (2) * squareSize; y++) {
                       		img.setRGB(x, y, carp[0][1].getRealColor().getRGB());
                   		}
                	}

					carp[1][1] = Starter[0][1];

					for (int x = (1) * squareSize; x < (2) * squareSize; x++) {
                   		for (int y = (1) * squareSize; y < (2) * squareSize; y++) {
                       		img.setRGB(x, y, carp[1][1].getRealColor().getRGB());
                   		}
                	}

					carp[2][1] = new Strand(Math.floorMod(2*carp[1][1].getColor() - carp[0][1].getColor(), colorablity));

					for (int x = (2) * squareSize; x < (3) * squareSize; x++) {
                   		for (int y = (1) * squareSize; y < (2) * squareSize; y++) {
                       		img.setRGB(x, y, carp[2][1].getRealColor().getRGB());
                   		}
                	}
				}
				//if i == 0 do top left corner 
				if(i == 0){
					for(int k = 0; k<2; k++){
						if(j == 0){
							carp[k+2][j+2] = Starter[1][k];
							for (int x = (k+2) * squareSize; x < (k+3) * squareSize; x++) {
		                   		for (int y = (j+2) * squareSize; y < (j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[k+2][j+2].getRealColor().getRGB());
		                   		}
		                	}
						}
						else{
							carp[k+j+1][j+2] = Starter[1][j+k+j];
							//if(j == 3 & k==1){System.out.println(Starter[1][j+k+j]);}
							//System.out.println(2*j + " " + k+j +  " " + j+2 +" "+ k + " " + Starter[1][2*j+k]);
							for (int x = (k+j+1) * squareSize; x < (k+j+2) * squareSize; x++) {
			               		for (int y = (j+2) * squareSize; y < (j+3) * squareSize; y++) {
			                   		img.setRGB(x, y, carp[k+j+1][j+2].getRealColor().getRGB());
		                   		}
		                	}
						}
					}
				}
				//otherwise do both the two bricks diagonally across from each other
				else{
					//horizontal
					if(i%2 == 1){
						//if top part added its vertical strand already
						if(jUP1){
							//System.out.println(locationBrick[0][i-1] + " " + j);
							carp[(brickSize+1)*i+j+2][j+2] = Starter[locationBrick[0][i-1]][2*j+1];
							for (int x = ((brickSize+1)*i+j+2) * squareSize; x < ((brickSize+1)*i+j+3) * squareSize; x++) {
		                   		for (int y = (j+2) * squareSize; y < (j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[(brickSize+1)*i+j+2][j+2].getRealColor().getRGB());
		                   		}
		                	}
						}else{
							carp[(brickSize+1)*i+j+2][j+2] = Starter[locationBrick[0][i-1]][j];
							for (int x = ((brickSize+1)*i+j+2) * squareSize; x < ((brickSize+1)*i+j+3) * squareSize; x++) {
		                   		for (int y = (j+2) * squareSize; y < (j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[(brickSize+1)*i+j+2][j+2].getRealColor().getRGB());
		                   		}
		                	}
						}

						//if left part added its vertical part 
						if(jUP2){
							carp[j+2][(brickSize+1)*i+j+2] = Starter[locationBrick[1][i-1]][2*j+1];
							for (int x = (j+2) * squareSize; x < (j+3) * squareSize; x++) {
		                   		for (int y = ((brickSize+1)*i+j+2) * squareSize; y < ((brickSize+1)*i+j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[j+2][(brickSize+1)*i+j+2].getRealColor().getRGB());
		                   		}
		                	}
						}else{
							carp[j+2][(brickSize+1)*i+j+2] = Starter[locationBrick[1][i-1]][j];
							for (int x = (j+2) * squareSize; x < (j+3) * squareSize; x++) {
		                   		for (int y = ((brickSize+1)*i+j+2) * squareSize; y < ((brickSize+1)*i+j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[j+2][(brickSize+1)*i+j+2].getRealColor().getRGB());
		                   		}
		                	}

						}
						//add extra vertical for top bricks
						if(j == brickSize-2){
							//System.out.println("hor: " + i);
							carp[(brickSize+1)*i+j+3][j+2] = Starter[locationBrick[0][i-1]][2*j+1];
							for (int x = ((brickSize+1)*i+j+3) * squareSize; x < ((brickSize+1)*i+j+4) * squareSize; x++) {
		                   		for (int y = (j+2) * squareSize; y < (j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[(brickSize+1)*i+j+3][j+2].getRealColor().getRGB());
		                   		}
		                	}
							jUP1 = true;
						}//add extra vertical for left bricks 
						if(j == 0){
							//System.out.println(Starter[locationBrick[1][i-1]][j+1]);
							//System.out.println(j+3);
							//System.out.println((brickSize+1)*i+j+3);
							carp[j+3][(brickSize+1)*i+j+2] = Starter[locationBrick[1][i-1]][j+1];
							for (int x = (j+3) * squareSize; x < (j+4) * squareSize; x++) {
		                   		for (int y = ((brickSize+1)*i+j+2) * squareSize; y < ((brickSize+1)*i+j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[j+3][(brickSize+1)*i+j+2].getRealColor().getRGB());
		                   		}
		                	}
							jUP2 = true;
						}
					}else{
						//add extra horizontal for top bricks
						if(j == 1){
							//System.out.println("vert: " + starter[]);
							carp[(brickSize+1)*i+j+1][j+2] = Starter[locationBrick[0][i-1]][j+1];
							for (int x = ((brickSize+1)*i+j+1) * squareSize; x < ((brickSize+1)*i+j+2) * squareSize; x++) {
		                   		for (int y = (j+2) * squareSize; y < (j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[(brickSize+1)*i+j+1][j+2].getRealColor().getRGB());
		                   		}
		                	}
							jUP1 = true;
						}//add extra vertical for left bricks 
						if(j == brickSize-1){
							//System.out.println(Starter[locationBrick[1][i-1]][j+1]);
							//System.out.println(j+3);
							//System.out.println((brickSize+1)*i+j+3);
							carp[j+1][(brickSize+1)*i+j+2] = Starter[locationBrick[1][i-1]][j+1];
							for (int x = (j+1) * squareSize; x < (j+2) * squareSize; x++) {
		                   		for (int y = ((brickSize+1)*i+j+2) * squareSize; y < ((brickSize+1)*i+j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[j+1][(brickSize+1)*i+j+2].getRealColor().getRGB());
		                   		}
		                	}
							jUP2 = true;
						}

						if(jUP1){
							carp[(brickSize+1)*i+j+2][j+2] = Starter[locationBrick[0][i-1]][2*j+1];
							for (int x = ((brickSize+1)*i+j+2) * squareSize; x < ((brickSize+1)*i+j+3) * squareSize; x++) {
		                   		for (int y = (j+2) * squareSize; y < (j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[(brickSize+1)*i+j+2][j+2].getRealColor().getRGB());
		                   		}
		                	}
						}else{
							carp[(brickSize+1)*i+j+2][j+2] = Starter[locationBrick[0][i-1]][j];
							for (int x = ((brickSize+1)*i+j+2) * squareSize; x < ((brickSize+1)*i+j+3) * squareSize; x++) {
		                   		for (int y = (j+2) * squareSize; y < (j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[(brickSize+1)*i+j+2][j+2].getRealColor().getRGB());
		                   		}
		                	}
						}

						//if left part added its vertical part 
						if(jUP2){
							carp[j+2][(brickSize+1)*i+j+2] = Starter[locationBrick[1][i-1]][2*j+1];
							for (int x = (j+2) * squareSize; x < (j+3) * squareSize; x++) {
		                   		for (int y = ((brickSize+1)*i+j+2) * squareSize; y < ((brickSize+1)*i+j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[j+2][(brickSize+1)*i+j+2].getRealColor().getRGB());
		                   		}
		                	}
						}else{
							carp[j+2][(brickSize+1)*i+j+2] = Starter[locationBrick[1][i-1]][j];
							for (int x = (j+2) * squareSize; x < (j+3) * squareSize; x++) {
		                   		for (int y = ((brickSize+1)*i+j+2) * squareSize; y < ((brickSize+1)*i+j+3) * squareSize; y++) {
		                       		img.setRGB(x, y, carp[j+2][(brickSize+1)*i+j+2].getRealColor().getRGB());
		                   		}
		                	}

						}
					}	
				}
			}
			/*
			 *
			 */
			//once you have all the starters for a brick generate everything inside of the brick
			if(i==0){
				carp[1][2] = new Strand(Math.floorMod(2*carp[2][2].getColor() - carp[3][2].getColor(), colorablity));
				for (int x = (1) * squareSize; x < (2) * squareSize; x++) {
					for (int y = (2) * squareSize; y < (3) * squareSize; y++) {
						img.setRGB(x, y, carp[1][2].getRealColor().getRGB());
					}
				}
				for(int j = 0; j<brickSize-1;j++){
					carp[4+j][3+j] = new Strand(Math.floorMod(2*carp[3+j][3+j].getColor() - carp[2+j][3+j].getColor(), colorablity));
					for (int x = (4+j) * squareSize; x < (4+j+1) * squareSize; x++) {
						for (int y = (3+j) * squareSize; y < (3+j+1) * squareSize; y++) {
							img.setRGB(x, y, carp[4+j][3+j].getRealColor().getRGB());
						}
					}
				}
				boolean top = true;
				//replicate code for diagonal
				int count = 0;
				while(count < brickSize){
					if(top){
						if(count%2 == 0){
							for(int j = 0; j < (brickSize-count); j++){
								//System.out.println((count+1)*2+j);
								//System.out.println(count);
								carp[count+2+j][j] = new Strand(Math.floorMod(2*carp[count+2+j][j+1].getColor() - carp[count+2+j][j+2].getColor(), colorablity));
								for (int x = (count+2+j) * squareSize; x < (count+2+j+1) * squareSize; x++) {
									for (int y = (j) * squareSize; y < (j+1) * squareSize; y++) {
										img.setRGB(x, y, carp[count+2+j][j].getRealColor().getRGB());
									}
								}
							}
						}else{
							for(int j = 0; j < (brickSize-count); j++){
								//System.out.println(((count+1)*2+j));
								carp[count+3+j][1+j] = new Strand(Math.floorMod(2*carp[count+3+j-1][1+j].getColor() - carp[count+3+j-2][j+1].getColor(), colorablity));
								for (int x = (count+3+j) * squareSize; x < (count+3+j+1) * squareSize; x++) {
									for (int y = (1+j) * squareSize; y < (j+2) * squareSize; y++) {
										img.setRGB(x, y, carp[count+3+j][1+j].getRealColor().getRGB());
									}
								}
							}
						}
					}
					else{
						//System.out.println(count);
						if(count%2 == 0){
							for(int j = 0; j < (brickSize-count); j++){
								carp[j+1][count+j+3] = new Strand(Math.floorMod(2*carp[j+1][count+j+2].getColor() - carp[j+1][count+j+1].getColor(), colorablity));
								for (int x = (j+1) * squareSize; x < (j+2) * squareSize; x++) {
									for (int y = (count+j+3) * squareSize; y < (count+j+4) * squareSize; y++) {
										img.setRGB(x, y, carp[j+1][count+j+3].getRealColor().getRGB());
									}
								}
								//System.out.println(carp[j+1][(count+1)*2+j+1]);
							}
						}else{
							for(int j = 0; j < (brickSize-count); j++){
								//System.out.println(((count)*2+j+1));
								carp[j][count+j+2] = new Strand(Math.floorMod(2*carp[j+1][count+j+2].getColor() - carp[j+2][count+j+2].getColor(), colorablity));
								for (int x = (j) * squareSize; x < (j+1) * squareSize; x++) {
									for (int y = (count+j+2) * squareSize; y < (count+j+3) * squareSize; y++) {
										img.setRGB(x, y, carp[j][count+j+2].getRealColor().getRGB());
									}
								}
							}
						}
					}
					count++;
					if(count == brickSize && top == false){
						break;
					}
					else if(count == brickSize){
						count = 0;
						top = false;
					}

				}
			}
			else{

				if(i%2 == 1){
					//top horizontal strands
					for(int j = 0; j < 2*(brickSize+1); j++){
						int count = 0;
						if(j%2 == 0){
							int xLoc = brickSize+2 +(i-1)*(brickSize+1)+j/2;
							/*System.out.println(xLoc);
							System.out.println(carp[xLoc-1][2+j/2]);
							System.out.println(carp[xLoc+1][2+j/2]);*/

							//the if statement is because if j=2*bricksize then the vertical strand of that
							//row is given form the starter pattern and it will determine that column
							if(j!=2*(brickSize)){
								//System.out.println();
								System.out.println(xLoc + " " + (2+j/2) + " " + j + "crunch strands");
								System.out.println("strand we want: "+ carp[xLoc][2+j/2] + " ... it's index is " + xLoc + " " + (2+j/2));
								System.out.println("strand we use 1(over): " + carp[xLoc-1][2+j/2] + " ... it's index is " + (xLoc-1) + " " + (2+j/2)); //uh
								System.out.println("strand we use 2: " + carp[xLoc+1][2+j/2] + " ... it's index is " + (xLoc+1) + " " + (2+j/2));
carp[xLoc][2+j/2] = new Strand(Math.floorMod((colorablity-2)*(carp[xLoc-1][2+j/2].getColor()+carp[xLoc+1][2+j/2].getColor()), colorablity));
System.out.println(Math.floorMod((colorablity-2)*(carp[xLoc-1][2+j/2].getColor()+carp[xLoc+1][2+j/2].getColor()), colorablity));
							for (int x = (xLoc) * squareSize; x < (xLoc+1) * squareSize; x++) {
									for (int y = (j/2+2) * squareSize; y < (j/2+3) * squareSize; y++) {
										img.setRGB(x, y, carp[xLoc][2+j/2].getRealColor().getRGB());
									}
								}
							}
							while(((count < brickSize/2) &  (j/2)%2 == 1) || ((count < 1+brickSize/2) &  (j/2)%2 == 0)){
								int temp = 2+j/2;
								int temp2 = 2+j/2;
								if((j/2)%2 == 0){
									while(temp > 0){
										if(j!=2*(brickSize)){
											temp = temp-2;
										}
										else if(count == 0){
											//this is needed because the strand at temp -2 is already generated from the starter
											temp = temp-4;
											count++;
										}
										else{
											temp = temp-2;
										}
										count++;
										/*System.out.println(xLoc + " " + temp + " " + j);
										System.out.println("strand we want: "+ carp[xLoc][temp] + " ... it's index is " + xLoc + " " + temp);
										System.out.println("strand we use 1(over): " + carp[xLoc][temp+1] + " ... it's index is " + xLoc + " " + (temp+1)); //uh
										System.out.println("strand we use 2: " + carp[xLoc][temp+2] + " ... it's index is " + xLoc + " " + (temp+2));*/
										carp[xLoc][temp] = new Strand(Math.floorMod(2*carp[xLoc][temp+1].getColor()-carp[xLoc][temp+2].getColor(), colorablity));
										for (int x = (xLoc) * squareSize; x < (xLoc+1) * squareSize; x++) {
											for (int y = (temp) * squareSize; y < (temp+1) * squareSize; y++) {
												img.setRGB(x, y, carp[xLoc][temp].getRealColor().getRGB());
											}
										}
									}
									while(temp2<brickSize+2){
										temp2 = temp2+2;
										count++;
										carp[xLoc][temp2] = new Strand(Math.floorMod(2*carp[xLoc][temp2-1].getColor()-carp[xLoc][temp2-2].getColor(), colorablity));
										for (int x = (xLoc) * squareSize; x < (xLoc+1) * squareSize; x++) {
											for (int y = (temp2) * squareSize; y < (temp2+1) * squareSize; y++) {
												img.setRGB(x, y, carp[xLoc][temp2].getRealColor().getRGB());
											}
										}
									}
								}else{
									while(temp > 1){
										temp = temp-2;
										count++;
										carp[xLoc][temp] = new Strand(Math.floorMod(2*carp[xLoc][temp+1].getColor()-carp[xLoc][temp+2].getColor(), colorablity));
										for (int x = (xLoc) * squareSize; x < (xLoc+1) * squareSize; x++) {
											for (int y = (temp) * squareSize; y < (temp+1) * squareSize; y++) {
												img.setRGB(x, y, carp[xLoc][temp].getRealColor().getRGB());
											}
										}
									}
									while(temp2<brickSize+1){
										temp2 = temp2+2;
										count++;
										carp[xLoc][temp2] = new Strand(Math.floorMod(2*carp[xLoc][temp2-1].getColor()-carp[xLoc][temp2-2].getColor(), colorablity));
										for (int x = (xLoc) * squareSize; x < (xLoc+1) * squareSize; x++) {
											for (int y = (temp2) * squareSize; y < (temp2+1) * squareSize; y++) {
												img.setRGB(x, y, carp[xLoc][temp2].getRealColor().getRGB());
											}
										}
									}
								}
							}
						}
						else{
							int xLoc = brickSize+2 +(i-1)*(brickSize+1)+j/2+1;
							int k;
							if(j%4 == 3){
								k = 1;
							}else{
								k = 2;
							}
							for(k = k; k< brickSize+3;k = k+2){
								if(carp[xLoc-1][k] != null && carp[xLoc-2][k] != null && carp[xLoc][k] == null){
									carp[xLoc][k] = new Strand(Math.floorMod(2*carp[xLoc-1][k].getColor()-carp[xLoc-2][k].getColor(), colorablity));
									for (int x = (xLoc) * squareSize; x < (xLoc+1) * squareSize; x++) {
										for (int y = (k) * squareSize; y < (k+1) * squareSize; y++) {
											img.setRGB(x, y, carp[xLoc][k].getRealColor().getRGB());
										}
									}
								}
							}
						}
					}
					//left horizontal
					for(int j = 0; j < 2*(brickSize+1); j++){
						int yLoc = brickSize+2 +(i-1)*(brickSize+1)+j/2+1;
						//System.out.println(yLoc);
						if(j == 0){
							carp[3][yLoc-1] = new Strand(Math.floorMod((colorablity-2)*(carp[3][yLoc].getColor()+carp[3][yLoc-2].getColor()), colorablity));
							for (int x = (3) * squareSize; x < (3+1) * squareSize; x++) {
								for (int y = (yLoc-1) * squareSize; y < (yLoc) * squareSize; y++) {
									img.setRGB(x, y, carp[3][yLoc-1].getRealColor().getRGB());
								}
							}
							carp[1][yLoc-1] = new Strand(Math.floorMod(2*carp[2][yLoc-1].getColor()-carp[3][yLoc-1].getColor(), colorablity));
							for (int x = (1) * squareSize; x < (1+1) * squareSize; x++) {
								for (int y = (yLoc-1) * squareSize; y < (yLoc) * squareSize; y++) {
									img.setRGB(x, y, carp[1][yLoc-1].getRealColor().getRGB());
								}
							}
							for(int k = 5; k < brickSize+2; k = k+2){
								carp[k][yLoc-1] = new Strand(Math.floorMod(2*carp[k-1][yLoc-1].getColor()-carp[k-2][yLoc-1].getColor(), colorablity));
								for (int x = (k) * squareSize; x < (k+1) * squareSize; x++) {
									for (int y = (yLoc-1) * squareSize; y < (yLoc) * squareSize; y++) {
										img.setRGB(x, y, carp[k][yLoc-1].getRealColor().getRGB());
									}
								}
							}
						}
						else if(j%2 == 0){
							//copied
							int count = 0;
							while(((count < 1+brickSize/2) &  (j/2)%2 == 1) || ((count < brickSize/2) &  (j/2)%2 == 0)){
								int temp = 1+j/2;
								int temp2 = 1+j/2;
								if((j/2)%2 == 1){
									while(temp > 0){
										temp = temp-2;	
										count++;
										
										System.out.println(temp + " " + (yLoc-1) + " " + j + " what is going on");
										System.out.println("strand we want: "+ carp[temp][yLoc-1] + " ... it's index is " + temp + " " + (yLoc-1));
										System.out.println("strand we use 1(over): " + carp[temp+1][yLoc-1] + " ... it's index is " + (temp+1) + " " + (yLoc-1)); //uh
										System.out.println("strand we use 2: " + carp[temp+2][yLoc-1] + " ... it's index is " + (temp+2) + " " + (yLoc-1));
										carp[temp][yLoc-1] = new Strand(Math.floorMod(2*carp[temp+1][yLoc-1].getColor()-carp[temp+2][yLoc-1].getColor(), colorablity));
										for (int x = (temp) * squareSize; x < (temp+1) * squareSize; x++) {
											for (int y = (yLoc-1) * squareSize; y < (yLoc) * squareSize; y++) {
												img.setRGB(x, y, carp[temp][yLoc-1].getRealColor().getRGB());
											}
										}
									}
									while(temp2<brickSize+2){
										temp2 = temp2+2;
										count++;
										carp[temp2][yLoc-1] = new Strand(Math.floorMod(2*carp[temp2-1][yLoc-1].getColor()-carp[temp2-2][yLoc-1].getColor(), colorablity));
										for (int x = (temp2) * squareSize; x < (temp2+1) * squareSize; x++) {
											for (int y = (yLoc-1) * squareSize; y < (yLoc) * squareSize; y++) {
												img.setRGB(x, y, carp[temp2][yLoc-1].getRealColor().getRGB());
											}
										}
									}
								}else{
									while(temp > 1){
										temp = temp-2;
										count++;

										System.out.println(temp + " " + (yLoc-1) + " " + j + " WHERE");
										System.out.println("strand we want: "+ carp[temp][yLoc-1] + " ... it's index is " + temp + " " + (yLoc-1));
										System.out.println("strand we use 1(over): " + carp[temp+1][yLoc-1] + " ... it's index is " + (temp+1) + " " + (yLoc-1)); //uh
										System.out.println("strand we use 2: " + carp[temp+2][yLoc-1] + " ... it's index is " + (temp+2) + " " + (yLoc-1));
										carp[temp][yLoc-1] = new Strand(Math.floorMod(2*carp[temp+1][yLoc-1].getColor()-carp[temp+2][yLoc-1].getColor(), colorablity));
										for (int x = (temp) * squareSize; x < (temp+1) * squareSize; x++) {
											for (int y = (yLoc-1) * squareSize; y < (yLoc) * squareSize; y++) {
												img.setRGB(x, y, carp[temp][yLoc-1].getRealColor().getRGB());
											}
										}
									}
									while(temp2<brickSize+1){
										temp2 = temp2+2;
										count++;
										
										System.out.println(temp2 + " " + (yLoc-1) + " " + j + " error");
										System.out.println("strand we want: "+ carp[temp2][yLoc-1] + " ... it's index is " + temp2 + " " + (yLoc-1));
										System.out.println("strand we use 1(over): " + carp[temp2-1][yLoc-1] + " ... it's index is " + (temp2-1) + " " + (yLoc-1)); //uh
										System.out.println("strand we use 2: " + carp[temp2-2][yLoc-1] + " ... it's index is " + (temp2-2) + " " + (yLoc-1));
										carp[temp2][yLoc-1] = new Strand(Math.floorMod(2*carp[temp2-1][yLoc-1].getColor()-carp[temp2-2][yLoc-1].getColor(), colorablity));
										for (int x = (temp2) * squareSize; x < (temp2+1) * squareSize; x++) {
											for (int y = (yLoc-1) * squareSize; y < (yLoc) * squareSize; y++) {
												img.setRGB(x, y, carp[temp2][yLoc-1].getRealColor().getRGB());
											}
										}
									}

								}
							}
						}
						else{
							//error is in here somewhere
							int k;
							if(j%4 == 1){
								k = 1;
							}
							else{
								k = 2;
							}
							for(k = k; k < brickSize+3;k = k+2){
								if(carp[k][yLoc-1] != null && carp[k][yLoc-2] != null && carp[k][yLoc] == null){
									System.out.println("YEEHAW");
									System.out.println(k + " " + yLoc + " " + j);
									System.out.println("strand we want: "+ carp[k][yLoc] + " ... it's index is " + k + " " + yLoc);
									System.out.println("strand we use 1(over): " + carp[k][yLoc-1] + " ... it's index is " + (k) + " " + (yLoc-1)); //uh
									System.out.println("strand we use 2: " + carp[k][yLoc-2] + " ... it's index is " + (k) + " " + (yLoc-2));
									System.out.println();
									carp[k][yLoc] = new Strand(Math.floorMod(2*carp[k][yLoc-1].getColor()-carp[k][yLoc-2].getColor(), colorablity));
									for (int x = (k) * squareSize; x < (k+1) * squareSize; x++) {
										for (int y = (yLoc) * squareSize; y < (yLoc+1) * squareSize; y++) {
											img.setRGB(x, y, carp[k][yLoc].getRealColor().getRGB());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		/*
		for(){
			for(){

			}
		}*/

		if(sumOf){
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"squares", gridSize-1,"s1-", "Uh","s2-","Uh");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}
		else{
			String filename = String.format("%d%s%d%s%s.png", colorablity ,"squares", gridSize-1,"s1-","s2-");
			try{
    	    	ImageIO.write(img, "png", new File(filename));
	    	}
    		catch (IOException e) {

    		}
    	}
	}

	public void findNbricks(int p, int N){
		//the following code is code for enumerating the vertical 2-bricks with topleft strand equal to 2 mod 3 for some power of 3
            		/* ALL we need to know is the 8 strands located at:
					*   (9a+1,9b+1),  (9a+1,9b+3),  (9a+1,9b+5),  (9a+1,9b+7)
					* and  (9a,9b+6), (9a+3,9b), (9a+9,9b+3), (9a+6,9b+9)
            		*/
					int leng = (int) Math.pow(p, N);
					int[][] posible = new int[50000][2*(leng-1)];
            		int[] counts = new int[p+1];
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
            					brick[k] = carp[index1][index2+2*k].getColor(); // top inside brick
            					brick[k+brick.length/4] = carp[index1+1+2*k][index2].getColor();// left inside brick
            					brick[k+brick.length/2] = carp[index1+2*k][index2-1].getColor();// left grout
            					brick[k+(3*brick.length)/4] = carp[index1-1][index2+1+2*k].getColor();// top grout
            				}
            				boolean flag = true; 
            				for(int k =0;k <counts[p]; k++){
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
            					posible[counts[p]] = brick;
            					counts[p] = counts[p]+1;
            					for(int k =0; k<counts.length; k++){
            						if(brick[0]%p == k){
            							counts[k] = counts[k]+1;
            							if(k==0){
            								for(int k2 = 0; k2 < 2*(leng-1); k2++){
            									System.out.print(brick[k2] + " ");
            								}
            								System.out.println("");
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
            					brick[k] = carp[index1+2*k][index2-1].getColor();// left grout
            					brick[k+brick.length/4] = carp[index1-1][index2+1+2*k].getColor();// top grout
            					brick[k+brick.length/2] = carp[index1+1+2*k][index2+leng-1].getColor(); // right grout
            					brick[k+(3*brick.length)/4] = carp[index1+leng-1][index2+2*k].getColor();// bottom grout
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
            						if((carp[index1][index2].getColor())%(lengCount) == k){
            							counts[k] = counts[k]+1;
            							if(counts[counts.length-1] == 1){
            								for(int k2 = 0; k2 < 2*(leng-1); k2++){
            									System.out.print(brick[k2] + " ");
            								}
            								System.out.println("");
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
			}*/
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
			}*/
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
	}
		// A bunch of get methods
	public Strand[][] getCarp(){
		return this.carp;
	}
	public void setCarp(int i, int j, Strand s){
		this.carp[i][j] = s;
	}

	public BigInteger[][] getCordSeq(){
		return this.cordSeq;
	}

	public BigInteger[][] getPolynomial(){
		return this.polynomial;
	}


	public Strand[][] getStarter(){
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
			csvList[i] = Integer.toString(this.carp[i+1][i].getColor()) + ", " + Integer.toString(this.carp[i+1][i+1].getColor()) + "\n";
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
			csvList[i] = Integer.toString(this.carp[i+1][i].getColor()) + ", " + Integer.toString(this.carp[i+1][i+1].getColor()) + ", ";
			for(int j = 1; 2*j+i<size; j++){
				if(2*(j+1)+i > size){
					csvList[i] = csvList[i] + Integer.toString(this.carp[i+1][i+2*j].getColor()) + ", " + Integer.toString(this.carp[i][i+2*j].getColor());
				}
				else{
					csvList[i] = csvList[i] + Integer.toString(this.carp[i+1][i+2*j].getColor()) + ", " + Integer.toString(this.carp[i][i+2*j].getColor()) + ", ";
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



	public static int getIntFromUser() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) { scanner.nextLine(); }
        return scanner.nextInt();
    }
    public static String getStrFromUser() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextLine()) { scanner.nextLine(); }
        return scanner.nextLine();
    }

    /*public static String baseConverter(int baseFrom, int baseTo, String[] num){
    	String numTo = "";
    	String[] digits = {"1",""}
    	else{
    			for(int i = 0; i<num.length;i++){
    			num[i]
    		}
    	}
    }*/
    public static String baseConversion(String number, int sBase, int dBase)
    {
        // Parse the number with source radix
        // and return in specified radix(base)
        String temp = Integer.toString(Integer.parseInt(number, sBase), dBase);
        for(int i = temp.length(); i<7;i++){
        	temp = "0" + temp;
        }
        return temp;
    }
    public static String baseConversion2(String number, int sBase, int dBase)
    {
        // Parse the number with source radix
        // and return in specified radix(base)
        String temp = Integer.toString(Integer.parseInt(number, sBase), dBase);
        for(int i = temp.length(); i<3;i++){
        	temp = "0" + temp;
        }
        return temp;
    }

   	public static String[][] DiagonalStarterGetter(){
		String[][] Dependancy = new String [10][10];
		Dependancy[0][0] = "H_n";
		Dependancy[1][0] = "V_n";
		//int i = 1;
		for(int i = 1; i<10; i++){
			String tempV = Dependancy[1][i-1];
			String tempH = Dependancy[0][i-1];
			if(tempV.length() == 3){
				Dependancy[0][i] = "2(" + tempV.substring(0, tempV.length()-1)+ 
				"{n+0}) - " + tempH;
				Dependancy[1][i] = "2(" + Dependancy[0][i] + ") - " + tempV.substring(0, tempV.length()-1)+"{n+1}";
			}else{
				Dependancy[0][i] = "2(" + tempV.substring(0, tempV.length()-2)+ 
				(Integer.parseInt(String.valueOf(tempV.charAt(tempV.length()-2)))+1) +"}) - " + tempH;
				Dependancy[1][i] = "2(" + Dependancy[0][i] + ") - " + tempV.substring(0, tempV.length()-2) +
				(Integer.parseInt(String.valueOf(tempV.charAt(tempV.length()-2)))+2)+"}";
			}
		}
			return Dependancy;
   	}

   	/*public static String equationReduce(String s){


   	}*/

   	public static int[] caseTester(int a, int b, int c){
   	//int aa = Math.floorMod(2*a,3);
   	//int bb = Math.floorMod(2*b,3);
   	//int cc = Math.floorMod(2*c,3);
   	int out[] = new int[4];
   	out[0] = Math.floorMod(a + 2*b + c, 3);
   	out[1] = Math.floorMod(2*out[0],3);
   	out[2] = out[0];
   	out[3] = out[1];
   	return out;
   	}

   	public static int[] caseTester2(int a, int b, int c){
   	//int aa = Math.floorMod(2*a,3);
   	//int bb = Math.floorMod(2*b,3);
   	//int cc = Math.floorMod(2*c,3);
   	int out[] = new int[4];
   	out[0] = Math.floorMod(a + b + 2*c, 3);
   	out[1] = Math.floorMod(2*out[0],3);
   	out[2] = out[0];
   	out[3] = out[1];
   	return out;
   	}

   	public static ArrayList<int[]> caseTester3(ArrayList<int[]> given){
   		ArrayList<int[]> vert = new ArrayList<int[]>();
   		ArrayList<int[]> hori = new ArrayList<int[]>();
   		ArrayList<int[]> returner = new ArrayList<int[]>();

   		for(int i = 0; i<given.size();i++){
   			if(given.get(i)[0] == -1){
   				vert.add(given.get(i));
   			}
   			else{
   				hori.add(given.get(i));
   			}
   			if(Helper(given.get(i), returner)){
				returner.add(given.get(i));
	   		}
   		}

   		for(int a = 0; a < vert.size(); a++){
   			for(int b = 0; b < hori.size(); b++){
   				for(int c = 0; c < hori.size(); c++){
   					int[] temp = new int[5];
   					int f = Math.floorMod(vert.get(a)[4] + vert.get(a)[3], 3);
   					int g = Math.floorMod(hori.get(b)[4] + hori.get(b)[3], 3);
   					int f2 = Math.floorMod(vert.get(a)[4] + vert.get(a)[1], 3);
   					int g2 = Math.floorMod(hori.get(c)[4] + hori.get(c)[1], 3);
   					if(f == g & f2 == g2){
	   					temp[0] = -1;
	   					temp[1] = Math.floorMod(vert.get(a)[4] + vert.get(a)[2] + hori.get(b)[1] + 2*hori.get(b)[4] + 2*hori.get(c)[2],3);
	   					temp[2] = Math.floorMod(2*temp[1] + hori.get(c)[1] + hori.get(c)[2],3);
	   					temp[3] = Math.floorMod(2*temp[2] + hori.get(b)[1] + hori.get(b)[2],3);
	   					temp[4] = Math.floorMod(vert.get(a)[4] + vert.get(a)[2] + vert.get(a)[3] + hori.get(b)[1] + 2*hori.get(c)[1] + 2*hori.get(c)[2] + 2*hori.get(c)[3],3);
	   					if(Helper(temp, returner)){
	   						returner.add(temp);
	   					}
   					}
   				}
   			}
   		}
   		
   		for(int a = 0; a < hori.size(); a++){
   			for(int b = 0; b < vert.size(); b++){
   				for(int c = 0; c < vert.size(); c++){
   					int f = Math.floorMod(hori.get(a)[2] + hori.get(a)[3], 3);
   					int g = Math.floorMod(vert.get(c)[2] + vert.get(c)[3], 3);
   					int f2 = Math.floorMod(hori.get(a)[2] + hori.get(a)[1], 3);
   					int g2 = Math.floorMod(vert.get(b)[2] + vert.get(b)[1], 3);
   					if(f == g & f2 == g2){
	   					int[] temp = new int[5];
	   					temp[0] = -2;
	   					temp[1] = Math.floorMod(hori.get(a)[4] + hori.get(a)[2] + vert.get(c)[1] + 2*vert.get(b)[4] + 2*vert.get(c)[2],3);
	   					temp[2] = Math.floorMod(hori.get(a)[4] + hori.get(a)[2] + hori.get(a)[3] + vert.get(c)[1] + 2*vert.get(b)[1] + 2*vert.get(b)[4] + 2*vert.get(b)[3],3);
	   					temp[4] = Math.floorMod(2*temp[1] + vert.get(b)[1] + vert.get(b)[4],3);
	   					temp[3] = Math.floorMod(2*temp[4] + vert.get(c)[3] + vert.get(c)[4],3);
	   					if(Helper(temp, returner)){
	   						returner.add(temp);
	   					}
	   				}
   				}
   			}
   		}
   		return returner;
   	}

   	public static boolean Helper(int[] a, ArrayList<int[]> given){
   		
   		for(int i = 0; i<given.size(); i++){
   			if(Arrays.equals(a, given.get(i))){
   				return false;
   			}
   		}
   		return true;
   	}
   	/*
   	public static int[][] computerAssisted(int[][] input){
   		int[][] output = new int[10][9];
   		//for(int i = 0; i<input.length; i++){
   			for(int j = 0;j<2;j = j+2){
   				for(int k = 1; k<4; k=k+2){
   					for(int x = 1; x<4; x = x+2){
   						int temp = caseTester((input[j][0])/2, (input[k][6])/2, (input[x][2])/2)[0];

   					}
   				}
   			}
   		//}
   	}*/

	public static void main(String[] args){
		//int[][] input = {{3,6,5,4,1,4,5,6,3}, {4,3,6,5,2,5,6,3,4}, {5,4,3,6,1,6,3,4,5}, {6,5,4,3,2,3,4,5,6}};
		//ArrayList<int[]> twobytwo = new ArrayList<int[]>();
		/*ArrayList<int[]> twobytwoTRY = new ArrayList<int[]>();
		ArrayList<int[]> twobytwo012 = new ArrayList<int[]>();*/
		/*int[] rows = new int[6561];
		for(int a = 0; a<3; a++){
			for(int b = 0; b<3; b++){
				for(int c= 0; c<3; c++){
					for(int d = 0; d<3;d++){
						for(int e = 0; e<3; e++){
							for(int f = 0; f<3; f++){
								for(int g = 0; g<3; g++){
									for(int h = 0; h<3; h++){
										int[] first = {-1,a,b,c,d};
										int[] second = {-2,e,f,g,h};
										int types = 2;
										ArrayList<int[]> twobytwo = new ArrayList<int[]>();
										twobytwo.add(first);
										twobytwo.add(second);
										for(int i = 0; i<20;i++){
											twobytwo = caseTester3(twobytwo);
										}
										if(a*2187 + 729*b + 243*c + 81*d + 27*e+ 9*f + g*3 +h == 6){
											System.out.println(first[1] + " " + first[2] + " " +first[3] + " " + first[4]);
											System.out.println(second[1] + " " + second[2] + " " +second[3] + " " + second[4]);
										}
										rows[a*2187 + 729*b + 243*c + 81*d + 27*e+ 9*f + g*3 +h] = twobytwo.size();
									}
								}
							}
						}
					}
				}
			}
		}
		String[] csvList = new String[6561];
		for(int i = 0; i<6561; i++){
			csvList[i] = String.valueOf(rows[i]) + "\n";
		}
		try{
			FileWriter fw = new FileWriter("C:/Users/mikey/ColoringCarpet/filetest.txt");
			String csv = String.join("", csvList);
			fw.write(csv);
			fw.flush();
			fw.close();	
		} catch (Exception e)
		{
			System.out.println("Error");
		}*/
		//ArrayList<int[]> twobytwo = new ArrayList<int[]>();
		/*
		String perood = "_";
		for(int i = 1; i<51; i++){
			if(i<10){
				perood = perood + i;
			}else{
				perood = perood + "*" + i + "*";
			}
		}
		String[] perood2 = {perood,perood};
		Carpet c = new Carpet(51,perood2);
		c.createKnotCarpet(true);
		c.writePolynomialCarpCSV();*/
		/*
		int[] first = {-1,0,0,0,0};
		int[] second = {-2,0,0,0,0};
		int[] third = {-1,1,2,1,2};
		int[] fourth = {-2,1,2,1,2};
		int[] fifth = {-1,2,1,2,1};
		int[] sixth = {-2,2,1,2,1};

		int[] first2 = {-1,1,2,1,1};
		int[] second2 = {-2,2,1,1,1};
		int[] third2 = {-1,2,1,2,2};
		int[] fourth2 = {-2,1,2,2,2};
		int[] fifth2 = {-2,2,0,0,0};
		int[] sixth2 = {-1,2,1,2,2};
		int[] seventh = {-2,1,0,0,0};

		int[] please = {-1,1,2,1,0};
		int[] please2 = {-2,2,1,0,2};
		int[] please3 = {-1,2,1,2,0};
		int[] please4 = {-2,0,0,1,2};

		twobytwoTRY.add(please2);
		twobytwoTRY.add(please);*/
		//twobytwoTRY.add(please3);
		//twobytwoTRY.add(please4);
		/*ArrayList<int[]> twobytwo2 = caseTester3(caseTester3(caseTester3(caseTester3(caseTester3(caseTester3(caseTester3(twobytwoTRY)))))));
		System.out.println(twobytwo2.size());
		for(int i = 0; i<twobytwo2.size(); i++){
			for(int j = 0; j<twobytwo2.get(i).length; j++){
				System.out.print(twobytwo2.get(i)[j] + " ");
			}
			System.out.println();
		}*/

		/*twobytwo012.add(first2);
		twobytwo012.add(second2);
		twobytwo012.add(third2);
		twobytwo012.add(fourth2);
		twobytwo012.add(fifth2);
		twobytwo012.add(sixth2);
		twobytwo012.add(seventh);
		ArrayList<int[]> twobytwo2 = caseTester3(caseTester3(caseTester3(twobytwo012)));*/
		/*System.out.println(twobytwo2.size());
		for(int i = 0; i<twobytwo2.size(); i++){
			for(int j = 0; j<twobytwo2.get(i).length; j++){
				System.out.print(twobytwo2.get(i)[j] + " ");
			}
			System.out.println();
		}*/
		/*		
		twobytwo.add(third);
		twobytwo.add(fourth);
		twobytwo.add(fifth);
		twobytwo.add(sixth);
		//ArrayList<int[]> twobytwo3 = caseTester3(twobytwo);
		ArrayList<int[]> twobytwo2 = caseTester3(caseTester3(twobytwo));
		System.out.println(twobytwo2.size());
		for(int i = 0; i<twobytwo2.size(); i++){
			for(int j = 1; j<twobytwo2.get(i).length; j++){
				System.out.print(twobytwo2.get(i)[j] + " ");
			}
			System.out.println();
		}*/

		/*String[][] Dependancy = DiagonalStarterGetter();
		for(int i = 0; i<2;i++){
			for(int j = 1; j<1;j++){
				if(i == 0){
					System.out.println("H^"+j +"_n = " + Dependancy[i][j]);
				}
				else{
					System.out.println("V^"+j +"_n = " + Dependancy[i][j]);
				}
			}
		}
		for(int i = 0; i<0; i++){
			for(int j = 0; j<3; j++){
				for(int k = 0; k<3; k++){
					int out[] = caseTester(i,j,k);
					String uh = "";
					//for(int x = 0; x<4;x++){
					//	uh = uh + " " + out[x];
					//}
					System.out.println("If a,b,c start with " + i + " "+ j + " "+ k + " then it forms: " + out[0] + "V");
				}
			}
		}

		for(int i = 0; i<0; i++){
			for(int j = 0; j<3; j++){
				for(int k = 0; k<3; k++){
					int out[] = caseTester2(i,j,k);
					String uh = "";
					//for(int x = 0; x<4;x++){
					//	uh = uh + " " + out[x];
					//}
					System.out.println("If a,b,c start with " + i + " "+ j + " "+ k + " then it forms: " + out[0] + "H");
				}
			}
		}*/
	// A bunch of tests 
		//String str = "1_0";
		//String two = "1_0";
		//String[] starter1 = {"1_00","1_00"};
		//String[] starter2 = {"02_00","02_00"};

		//String[] irraToRat = {"010122121122010122010122121122010122010122010122121122010122010122010122010122_00", "2_00"};
		//String sqrtTwoIn128 = "1:53:2:60:102:63:78:121:73:4:44:95:49:27:27:85:21:62:79:71:45:118:5:106:18:59:84:19:29:81:61:50:11:3:25:102:34:84:87:114:6:4:29:46:20:44:29:34:99:126:55:119:16:61:89:100:30:116:84:23:72:57:99:109:20:120:2:33:102:99:111:104:78:104:105:8:5:104:58:10:94:31:39:20:96:89:99:0:35:60:25:94:121:43:13:114:34:22:68:17:49:36:48:42:120:114:23:95:9:99:30:27:43:40:81:98:118:126:78:115:22:42:49:80:102:31:30:33:27:117:23:68:65:120:27:57:48:46:117:41:20:8:76:65:1:74:37:32:39:20:85:91:49:125:7:63:62:77:29:0:89:107:100:79:65:68:19:19:53:60:106:74:22:101:13:99:30:52:13:91:112:35:54:28:2:4:110:0:63:99:17:84:94:51:21:23:83:119:94:104:61:63:123:40:55:99:126:40:68:19:3:119:84:6:18:7:111:42:64:112:88:31:101:119:61:34:123:119:6:49:68:14:20:46:87:125:62:59:22:73:30:74:59:15:117:75:68:11:123:126:11:67:122:59:15:94:1:121:86:51:72:23:75:124:80:5:97:17:79:5:116:74:42:112:17:87:121:120:123:111:20:62:81:78:105:87:99:2:75:102:25:120:124:20:66:124:118:73:38:96:87:70:4:22:88:39:109:16:124:73:19:107:55:15:96:91:77:20:64:108:9:111:58:18:18:76:15:41:106:86:67:93:35:16:125:105:9:122:26:79:105:1:66:124:32:125:93:82:78:125:86:25:25:66:51:58:112:80:118:82:4:46:15:34:51:80:26:8:35:107:56:112:48:30:100:3:58:78:104:104:118:52:17:114:108:46:53:50:114:110:3:126:53:32:48:104:18:74:11:44:64:48:64:61:118:126:27:61:17:10:40:86:79:18:35:13:9:99:0:23:52:5:34:106:107:2:7:122:93:115:56:27:80:99:27:41:3:15:121:127:127:43:85:124:71:8:38:1:77:94:87:4:104:36:23:52:94:72:108:124:47:16:22:110:39:18:110:46:29:48:40:63:8:71:66:20:10:55:4:1:76:118:112:89:101:94:102:21:110:56:121:5:98:52:52:111:38:3:75:100:32:17:81:66:63:126:8:72:83:33:103:85:23:84:102:54:108:79:15:28:113:124:83:44:9:21:51:66:32:85:99:74:80:112:53:42:17:52:33:70:23:60:51:108:3:40:52:5:126:66:124:83:108:69:121:56:114:92:1:117:117:59:70:112:37:89:66:65:103:65:93:85:17:96:35:13:39:120:62:30:50:29:77:69:1:105:9:45:9:96:45:53:2:2:31:55:24:79:88:117:10:50:58:10:25:94:3:33:65:113:8:80:4:66:54:41:116:124:110:103:84:89:71:41:9:107:84:70:27:46:24:103:68:64:50:45:62:42:38:126:93:75:25:113:52:105:49:27:90:67:30:123:14:36:0:83:126:84:50:2:115:43:31:5:58:30:79:29:29:127:2:122:27:38:117:110:17:60:1:47:54:74:109:125:47:13:65:56:34:100:53:63:110:94:65:111:126:100:91:55:15:59:99:120:28:21:110:121:97:90:39:87:117:87:81:12:8:65:37:49:45:104:7:20:127:13:90:120:11:63:48:55:70:112:41:93:91:99:31:112:91:19:83:65:6:13:78:66:55:43:70:98:101:36:97:103:82:8:113:85:46:52:118:0:30:43:101:8:91:99:2:71:96:82:63:49:22:71:45:114:75:110:28:5:86:74:23:53:20:88:18:72:64:101:47:18:67:67:112:122:38:126:67:103:62:69:61:70:5:82:78:66:119:20:34:89:33:31:109:0:108:76:10:34:93:65:110:65:83:42:99:48:29:104:69:51:105:50:15:85:34:63:45:110:87:81:1:37:23:45:91:105:40:68:120:122:9:21:108:56:104:42:32:27:66:116:102:40:123:27:75:61:90:2:120:67:18:124:86:99:57:19:20:37:52:2:48:102:24:14:89:20:53:77:34:54:111:68:25:30:61:43:84:99:19:44:106:71:6:18:94:82:58:43:105:50:119:42:31:116:15:48:27:24:50:119:42:9:111:56:64:122:45:22:85:118:108:10:78:75:115:62:11:14:127:113:36:81:83:16:108:82:71:7:43:72:21:69:79:29:92:50:43:60:30:33:70:66:56:93:94:124:115:2:109:13:119:127:2:120:117:39:124:125:64:33:47:57:5:89:73:51:22:41:10:80:123:67:16:124:61:11:97:97:120:97:22:107:23:64:24:26:85:92:11:122:78:105:124:124:21:68";
		//String sqrtTwoInBin = "1011010100000100111100110011001111111001110111100110010010000100010110010111110110001001101100110111010101001010101111101001111100011101011011110110000010111010100010010011101110101000010011001110110100010111101011001000010110000011001100111001100100010101010010101111110010000011000001000011101010111000101000101100001110101000101100011111111001101111110111001000001111011011001110010000111101110100101010000101111001000011100111000111101101001010011110000000010010000111001101100011110111111010001001110110100011010010001000000010111010000111010000101010111100011111010011100101001100000101100111000110000000010001101111000011001101111011110010101011000110111100100100010001011010001000010001011000101001000110000010101011110001110010001011110111110001001110001100111100011011010101101010001010001110001011101101111110100111011100110010110010101001100011010000110011000111110011110010000100110111110101001011110001001000001111100000110110111001011000001011101110101010100100101000001000100110010000010000001100101001001";
		//String[] uh = sqrtTwoInBin.split("");
		//String[] arraySqrt128 = sqrtTwoIn128.split(":");
		//String starty = "012012";
		/*for(int i = 1; i<138;i++){
			String temp = baseConversion(arraySqrt128[i], 10, 2);
			for(int j = 0; j<7;j++){
				if(temp.charAt(j) == '0'){
					starty = starty + "000012";
				}
				else{
					starty = starty + "012012";
				}
			}
		}*/
		/*for(int k = 0; k<27;k++){
			String temp = baseConversion2(String.valueOf(k), 10, 3);
			System.out.println(temp);
			//Random rand = new Random(System.currentTimeMillis());
			String starty = "_" + temp + "012";
			/*for(int i = 0; i<1100; i++){
				int a = rand.nextInt(2);
				if(a == 1){
					starty = starty + temp + "012";
				}
				else{
					starty = starty + "012012";
				}
			}*/
			/*for(int i = 0; i<1020;i++){
				if(uh[i].equals("0")){
					starty = starty+"00";
				}else{
					starty = starty+"11";
				}
			}
			//starty = starty+"_0";
			String[] uhh = {starty,starty};
			Carpet uhhh = new Carpet(6561, uhh);
			uhhh.createKnotCarpet();
		}*/
		//Carpet c1 = new Carpet(size, starter1);
		//Carpet c2 = new Carpet(size, starter2);
		//Carpet irra = new Carpet(size, irraToRat);
		//Carpet carp = new Carpet(c1,c2);
		//Strand[][] start = irra.getStarter();
		//System.out.println(irra.createKnotCarpet());
		//Strand[][] irrat = irra.getCarp();
		if(args[0].equals("diff")){ 
			if(args.length == 4){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            Carpet c1 = new Carpet(size, starters1);
	            Carpet c2 = new Carpet(size, starters2);
	            Carpet c3 = new Carpet(c1, c2, -1);
	            c3.createKnotCarpet(false);
			}else if(args.length == 5){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            int p = Integer.valueOf(args[4]);
	            Carpet c1 = new Carpet(size,p, starters1);
	            Carpet c2 = new Carpet(size,p, starters2);
	            Carpet c3 = new Carpet(c1, c2, -1);
	            c3.createKnotCarpet(false);
			}else if(args.length == 6){
				if(args[5].toLowerCase().equals("diag")){
					int size = Integer.valueOf(args[1]);
					String chosenStarter1 = args[2];
		            String[] starters1 = chosenStarter1.split(",");
		            String chosenStarter2 = args[3];
	    	        String[] starters2 = chosenStarter2.split(",");
	        	    int p = Integer.valueOf(args[4]);
	            	Carpet c1 = new Carpet(size,p, starters1);
	        	    Carpet c2 = new Carpet(size,p, starters2);
	            	Carpet c3 = new Carpet(c1, c2, -1);
	            	c3.createKnotCarpetDiag();
				}else{
					int size = Integer.valueOf(args[1]);
					String chosenStarter1 = args[2];
	            	String[] starters1 = chosenStarter1.split(",");
	            	String chosenStarter2 = args[3];
	            	String[] starters2 = chosenStarter2.split(",");
	            	int p1 = Integer.valueOf(args[4]);
	            	int p2 = Integer.valueOf(args[5]);
	            	Carpet c1 = new Carpet(size,p1, starters1);
	            	Carpet c2 = new Carpet(size,p2, starters2);
	            	Carpet c3 = new Carpet(c1, c2, -1);
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
	            Carpet c1 = new Carpet(size, starters1);
	            Carpet c2 = new Carpet(size, starters2);
	            Carpet c3 = new Carpet(c1, c2, 0);
	            c3.createKnotCarpet(false);
			}else if(args.length == 5){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            int p = Integer.valueOf(args[4]);
	            Carpet c1 = new Carpet(size,p, starters1);
	            Carpet c2 = new Carpet(size,p, starters2);
	            Carpet c3 = new Carpet(c1, c2, 0);
	            c3.createKnotCarpet(false);
			}else if(args.length == 6){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            int p = Integer.valueOf(args[4]);
	            Carpet c1 = new Carpet(size,p, starters1);
	            Carpet c2 = new Carpet(size,p, starters2);
	            Carpet c3 = new Carpet(c1, c2, 0);
	            c3.createKnotCarpetDiag();
			}
		}
		else if(args[0].equals("sum")){
			if(args.length == 4){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            Carpet c1 = new Carpet(size, starters1);
	            Carpet c2 = new Carpet(size, starters2);
	            Carpet c3 = new Carpet(c1, c2, 1);
	            c3.createKnotCarpet(false);
			}else if(args.length == 5){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            int p = Integer.valueOf(args[4]);
	            Carpet c1 = new Carpet(size,p, starters1);
	            Carpet c2 = new Carpet(size,p, starters2);
	            Carpet c3 = new Carpet(c1, c2, 1);
	            c3.createKnotCarpet(false);
			}else if(args.length == 6){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            int p = Integer.valueOf(args[4]);
	            Carpet c1 = new Carpet(size,p, starters1);
	            Carpet c2 = new Carpet(size,p, starters2);
	            Carpet c3 = new Carpet(c1, c2, 1);
	            if(args[5].equals("minus")){
	            	c3.createKnotCarpetDiagMinus(true);
	            }
	            else{
	            	c3.createKnotCarpetDiag();
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
	            	Carpet c1 = new Carpet(size, starters);
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
					Carpet c1 = new Carpet(size, starters);
		            c1.createKnotCarpet(false);
		            c1.diagonalStaterCSV();
				}
				else if(args[0].toLowerCase().equals("getdiagtop")){
					int size = Integer.valueOf(args[1]);
					String chosenStarter = args[2];	
					String[] starters = chosenStarter.split(",");
					Carpet c1 = new Carpet(size, starters);
		            c1.createKnotCarpet(false);
		            c1.diagonalFullCSV();
				}
				else if(args[2].equals("true")){
					int size = Integer.valueOf(args[0]);
					String chosenStarter = args[1];				
	            	String[] starters = chosenStarter.split(",");
					Carpet c1 = new Carpet(size, starters);
		            c1.createKnotCarpet(true);
		            c1.writePolynomialCarpCSV();
				}else{
					int size = Integer.valueOf(args[0]);
					String chosenStarter = args[1];
					int p = Integer.valueOf(args[2]);
    	       		String[] starters = chosenStarter.split(",");
	            	Carpet c1 = new Carpet(size, p , starters);
            		c1.createKnotCarpet(false);
            		//System.out.println(c1.carp[1][2].getColor());

            		//the following code is code for enumerating the vertical 2-bricks with topleft strand equal to 2 mod 3 for some power of 3
            		/* ALL we need to know is the 8 strands located at:
					*   (9a+1,9b+1),  (9a+1,9b+3),  (9a+1,9b+5),  (9a+1,9b+7)
					* and  (9a,9b+6), (9a+3,9b), (9a+9,9b+3), (9a+6,9b+9)
            		*/

            		/*for(int i =0; i< size/18 -2;i++){
            			for (int j = 0;j<size/18 -2 ;j++ ) {
            				int index1 = 2+18*i;
            				int index2 = 2+18*j;
            				int[] brick = {c1.carp[index1][index2].getColor(), c1.carp[index1][index2+2].getColor(), c1.carp[index1][index2+4].getColor(), c1.carp[index1][index2+6].getColor(),
            				c1.carp[index1-1][index2+5].getColor(),c1.carp[index1+2][index2-1].getColor(),c1.carp[index1+8][index2+2].getColor(),c1.carp[index1+5][index2+8].getColor()};
            				boolean flag = true; 
            				for(int k =0;k <count; k++){
            					if(brick[0] == posible[k][0] && brick[1] == posible[k][1] && brick[2] == posible[k][2] && brick[3] == posible[k][3]
            					&& brick[4] == posible[k][4] && brick[5] == posible[k][5] && brick[6] == posible[k][6] && brick[7] == posible[k][7]){
            						flag = false;
            					}
            				}
            				if(flag){
            					posible[count] = brick;
            					count++;
            					if(brick[0] == 2 || brick[0] == 5 || brick[0] == 8 || brick[0] ==11 || brick[0] == 14 || brick[0] == 17 || brick[0] == 20 || brick[0] == 23 || brick[0] == 26){
            						System.out.print(brick[0]+ " " + brick[1]+ " " + brick[2]+ " " + brick[3] + " " + brick[4]+ " " + brick[5]+ " " + brick[6]+ " " + brick[7] +
            						 ": extra" + c1.carp[index1+1][index2].getColor());
            						System.out.println();
            						count2++;
            					}
            					else if(brick[0] == 3 || brick[0] == 0 || brick[0] == 6 || brick[0] == 9 || brick[0] == 12 || brick[0] == 15 || brick[0] == 18 || brick[0] == 21 || brick[0] == 24){
            						//System.out.print(brick[0]+ " " + brick[1]+ " " + brick[2]+ " " + brick[3]);
            						System.out.println();
            						count0++;
            					}
            					else{
            						count1++;
            					}
            				}
            			}
            		}
            		System.out.println(count);
            		System.out.println(count0);
            		System.out.println(count1);
            		System.out.println(count2);*/

            		//the following code is code for enumerating the vertical 1-bricks with topleft strand equal to 2 mod 3 for some power of 3

				}
			}
			else if(args.length == 5){
				if(args[0].equals("tiling")){
					int size = Integer.valueOf(args[1]);
					int p = Integer.valueOf(args[2]);
					String chosenStarter = args[3];
					String[] starters = chosenStarter.split(",");
					int firstty = Integer.valueOf(args[4]);
					Carpet c1 = new Carpet(size, p, starters, firstty);
					c1.whiteSquareCarpetOneGo();

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
				}else{
					int size = Integer.valueOf(args[0]);
					int sizeBrick = Integer.valueOf(args[1]); 
					int p = Integer.valueOf(args[2]);
					String chosenStarter = args[3];
					String[] starters = chosenStarter.split(",");
					String starterLoc = args[4];
					String[] starterlocation = starterLoc.split(",");
					Carpet c1 = new Carpet(size, p, sizeBrick, starters, starterlocation);
					c1.createKnotCarpetBrick();
					Strand[][] Carpy = c1.getCarp();
					/*for(int i = 0; i<Carpy.length-1; i++){
		            	for(int j = 0; j<Carpy.length-1; j++){
		            		System.out.print(Carpy[j][i] + " ");
		            	}
		            	System.out.println();
		            }*/
				}
			}
			else if(args.length == 6){
				if(args[0].equals("find")){
					int size = Integer.valueOf(args[1]);
					String chosenStarter = args[2];
					int p = Integer.valueOf(args[3]);
					int REALp = Integer.valueOf(args[4]);
					int N = Integer.valueOf(args[5]);
    	       		String[] starters = chosenStarter.split(",");
	            	Carpet c1 = new Carpet(size, p , starters);
            		c1.createKnotCarpet(false);
            		c1.findNbricks(REALp, N);
				}
				/**/
				else{
					int size = Integer.valueOf(args[1]);
					String chosenStarter = args[2];
					String[] starters = chosenStarter.split(",");
					int p = Integer.valueOf(args[3]);
					int acof = Integer.valueOf(args[4]);
					int bcof = Integer.valueOf(args[5]);
					Carpet c1 = new Carpet(size, p, starters, acof, bcof);
					c1.createGeneralizedKnotCarpet(false);
				}
			}
			else if(args.length == 7){
				if(args[0].equals("findG")){
					int size = Integer.valueOf(args[1]);
					String chosenStarter = args[2];
					int p = Integer.valueOf(args[3]);
					int REALp = Integer.valueOf(args[4]);
					int N = Integer.valueOf(args[5]);
    	       		String[] starters = chosenStarter.split(",");
	            	Carpet c1 = new Carpet(size, p , starters);
            		c1.createKnotCarpet(false);
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
					Carpet c1 = new Carpet(size, p, starters, acof, bcof, orie);
					c1.createGeneralizedKnotCarpet(false);
				}
			}
		}
	}
}