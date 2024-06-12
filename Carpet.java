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



public class Carpet{
	private Strand[][] carp; // Double array of each strand in the carpet. It is used to generate the image
	private int colorablity = 3; //This is the number of colors in a carpet and it is important for the way the carpet is generated
	private boolean twoD = true; //Useless currently future uses maybe?
	private Strand[][] Starter;// Used to create carpet for knot theory carpets
	private int size; // size of the carpet size x size
	private double[] rows; // The average amount of 0's for each row
	private String location = "Q4"; // Not used currently. Future implementation
	private String template = "quadrants"; // Not used currently. Future implementation
	private String[] inputStarter; // The inputed set of starter strands 
	private boolean sumOf = false; // true if the carpet was made from the sum, difference or product of two carpets 
	private String[] diagonalStater; 
	private String[] regularStarter;
	private static String[] templates = {"quadrants", "diagonalX", "diagonalMinusX"}; // Not used currently. Future implementation
	private static String[] locations = {"Q1", "Q2", "Q3", "Q4", "full","left","right","top","bottom"}; //Not used currently. Future implementation
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
		for(int i=0; i<starter.length; i++){
			//if(starter[i] == null){break;}
			String temp = starter[i];
			//this if statement goes directly into the repeating portion of the starter pattern 
			if(temp.charAt(0) == '_'){
				int count = 0;
				int secondPartCount = size; //In example "1_012" this is 3 
				String star = temp.substring(1,temp.length());
				int repeatCount = star.length(); //In example "_012" this is 3
				for(int j=0; j<secondPartCount; j++){
					int indexSecondPart = j % repeatCount; 
					int color = Integer.parseInt(star.substring(indexSecondPart,indexSecondPart+1));
					Strand start = new Strand(i,color,true); 
					this.Starter[i][j] = start;
				}
			} else{
				String[] decimals = temp.split("_");
				int firstPartCount = decimals[0].length();
				for(int j = 0; j<firstPartCount; j++){
					int color = Integer.parseInt(decimals[0].substring(j,j+1));
					Strand start = new Strand(i,color,true);
					//System.out.println("Strand" + i + " " + j +  start);
					this.Starter[i][j] = start;
				}
				int count = 0;
				int secondPartCount = size - firstPartCount;
				int repeatCount = decimals[1].length(); //In example "1_012" this is 3 

				for(int j=0; j<secondPartCount; j++){
					int indexSecondPart = j % repeatCount; 
					int color = Integer.parseInt(decimals[1].substring(indexSecondPart,indexSecondPart+1));
					Strand start = new Strand(i,color,true); 
					this.Starter[i][firstPartCount+j] = start;
				}
			}
			
		}
	}

	/*
	* A few different Constructors:
	* 1. The first constructor: just inputs size and the starter
	* 2. The second constructor: inputs size, colorability, and the starter
	* 3. The next one takes two carpets that were already generated and creates the sum of the carpets
	* 4. We don't really use this one. Future use maybe
	*/

	public Carpet(int size, String[] starter){
		this.Starter = new Strand[4][size];
		this.rows = new double[size];
		this.size = size;
		createStarter(starter);
		this.carp = new Strand[size+1][size+1]; 
	}

	public Carpet(int size, int p, String[] starter){
		this.Starter = new Strand[4][size];
		//System.out.println(p);
		this.colorablity = p;
		//System.out.println(colorablity);
		this.rows = new double[size];
		this.size = size;
		createStarter(starter);
		this.carp = new Strand[size+1][size+1]; 
	}

	public Carpet(Carpet C1, Carpet C2, int opper){
		sumOf = true;
		if(C1.getColorablity() != C2.getColorablity()) throw new IllegalArgumentException("not the same colorablity");
		this.colorablity = C1.getColorablity();
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
	public double createKnotCarpet(){
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
					carp[i][j] = Starter[1][i-1];
					for (int x = i * squareSize; x < (i + 1) * squareSize; x++) {
                    	for (int y = j * squareSize; y < (j + 1) * squareSize; y++) {
                        	img.setRGB(y,x, carp[i][j].getRealColor().getRGB());
                    	}
                	}
				} else{
					if((i + j)%2 == 1){
						Strand start = new Strand(i,j);
						int temp = 2*(carp[i][j-1].getColor())-carp[i][j-2].getColor();
						int color = Math.floorMod(temp,colorablity);
						if(color == 0){
							zeroCount++;
							rowZeroCount++;
						}
						if(i == j+1){
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
					else{
						Strand start = new Strand(i,j);
						int temp = 2*(carp[i-1][j].getColor())-carp[i-2][j].getColor();
						int color = Math.floorMod(temp,colorablity);
						if(color == 0){
							zeroCount++;
							rowZeroCount++;
						}
						if(i == j){
							System.out.println(i+","+j + ": " + color);
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
			String filename = String.format("%d%s%d%s%s%s%s.png", colorablity ,"--", gridSize-1,"s1-", "idk","s2-","idk");
			try{
        		ImageIO.write(img, "png", new File(filename));
    		}
    		catch (IOException e) {

    		}
		}else if(inputStarter[0].length() > 60 || inputStarter[1].length() > 60){
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
		if(inputStarter[0].length() > 60 || inputStarter[1].length() > 60){
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
		if(inputStarter[0].length() > 60 || inputStarter[1].length() > 60){
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


	public static void main(String[] args){
		String[][] Dependancy = DiagonalStarterGetter();
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
	            c3.createKnotCarpet();
			}else if(args.length == 5){
				int size = Integer.valueOf(args[1]);
				String chosenStarter1 = args[2];
	            String[] starters1 = chosenStarter1.split(",");
	            String chosenStarter2 = args[3];
	            String[] starters2 = chosenStarter2.split(",");
	            int p = Integer.valueOf(args[4]);
	            Carpet c1 = new Carpet(size, starters1);
	            Carpet c2 = new Carpet(size, starters2);
	            Carpet c3 = new Carpet(c1, c2, -1);
	            c3.createKnotCarpet();
			}else if(args.length == 6){
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
	            c3.createKnotCarpet();
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
	            c3.createKnotCarpet();
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
	            c3.createKnotCarpet();
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
	            c3.createKnotCarpetDiag();
			}
		}
		else{
			if(args.length == 2){
				int size = Integer.valueOf(args[0]);
				String chosenStarter = args[1];
	            String[] starters = chosenStarter.split(",");
	            Carpet c1 = new Carpet(size, starters);
	            c1.createKnotCarpet();
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
				int size = Integer.valueOf(args[0]);
				String chosenStarter = args[1];
				int p = Integer.valueOf(args[2]);
    	        String[] starters = chosenStarter.split(",");
	            Carpet c1 = new Carpet(size, p , starters);
            //System.out.println(c1.getColorablity());
            	c1.createKnotCarpet();
			}
			else if(args.length == 4){
				int size = Integer.valueOf(args[0]);
				String chosenStarter = args[1];
				int p = Integer.valueOf(args[2]);
    	        String[] starters = chosenStarter.split(",");
	            Carpet c1 = new Carpet(size,p, starters);
	            if(args[3].equals("Minus") || args[3].equals("minus")){
	            	c1.createKnotCarpetDiagMinus(true);
	            }
	            else{
	            	c1.createKnotCarpetDiag();
	            }
            	Strand[][] Carpy = c1.getCarp();
	            /*for(int i = 0; i<12; i++){
	            	for(int j = 0; j<12; j++){
	            		System.out.print(Carpy[i][j]);
	            	}
	            	System.out.println();
	            }*/
			}
		}

		/*boolean state = true;
		boolean state2 = true;
		boolean state3 = true;
		System.out.println("What Size Do you want the carpet to be? (Min Size 10, Max Size 100)");
		while(state){ 
			int chosenSize = getIntFromUser();
			System.out.println(chosenSize);
            if (chosenSize > 9 && chosenSize <101) {
            	state = false; 
            	System.out.println("What will your first starter line be? (Please enter in this format, and do not add any spaces: [First Starter],[Second Starter]");
            	while(state2){
            		boolean isValid = true;
            		String chosenStarter = getStrFromUser();
            		String[] starters = chosenStarter.split(",");

            		if(starters.length == 2){
            			for(int i=0;i <2; i++){
            				if(starters[i].split("_").length >1){
            					for(int j=0; j<2; j++){
            						if(starters[i].split("_")[j].length() <1){
            							System.out.println(starters[i].split("_")[j]);
            							isValid = false;
            						}
            					}
            				}
            				else{
            					isValid = false;
            				}
            				if(starters[i].length()<1){
            					isValid = false;
            					System.out.println("too short!");
            					System.out.println(starters[i].length());

            				}
            				for (char ch: starters[i].toCharArray()) {
            					if(((int)ch <47 || (int)ch>50)&&(int)ch != 95){
            						isValid = false;
            					}
            					//System.out.println((int)ch);
            				}
						}
					}
					else{
						isValid = false;
					
            		}

            				
            		if(isValid){
            			state2 = false;
            			System.out.println(" Q3: What Type of Carpet Would you Like Made? (1. Knot, 2. DiagonalKnot, 3. Stripes, 4. checkerBoard)");
            			while(state3){
            				Carpet newCarpet = new Carpet(chosenSize, starters);
            				String chosenCarpet = getStrFromUser();
            				System.out.println(chosenCarpet);
            				if(chosenCarpet.equals("Knot")|| chosenCarpet.equals("knot")||chosenCarpet.equals("1")){
            					state3 = false;
            					newCarpet.createKnotCarpet();
            					Strand[][] fullCarp = newCarpet.getCarp();
            					CarpetGraphics f = new CarpetGraphics(chosenSize, fullCarp);
            				}
            				else if(chosenCarpet.equals("DiagonalKnot")|| chosenCarpet.equals("diagonalknot")||chosenCarpet.equals("2")){
            					state3 = false;
            					newCarpet.createKnotCarpetDiag();
            					Strand[][] fullCarp = newCarpet.getCarp();
            					CarpetGraphics f = new CarpetGraphics(chosenSize, fullCarp);
            				}
            				else if(chosenCarpet.equals("3")){
            					state3 = false;
            					newCarpet.stripes();
            					Strand[][] fullCarp = newCarpet.getCarp();
            					CarpetGraphics f = new CarpetGraphics(chosenSize, fullCarp);
            				}
            				else if(chosenCarpet.equals("4")){
            					state3 = false;
            					newCarpet.checkerBoard();
            					Strand[][] fullCarp = newCarpet.getCarp();
            					CarpetGraphics f = new CarpetGraphics(chosenSize, fullCarp);
            				}
            				else{
            					System.out.println("Please Choose a Valid Option");
            					System.out.println(" Q3: What Type of Carpet Would you Like Made? (1. Knot, 2. DiagonalKnot");
            				}
						}
            	
            		}
            		else{
            			System.out.println("ERROR: Invalid Starter Input");
            			System.out.println("What will your first starter line be? (Please enter in this format: [First Starter], [Second Starter]");
            		}
            	}
            }
            else if(chosenSize<10||chosenSize>100) {
            	System.out.println("ERROR: Chosen Number out of Bounds");
            	System.out.println("What Size Do you want the carpet to be? (Min Size 10, Max Size 100)");
            }
            else{
            	System.out.println("ERROR: Please Enter a Number");
            	System.out.println("What Size Do you want the carpet to be? (Min Size 10, Max Size 100)");
            }
        }*/
	}
}