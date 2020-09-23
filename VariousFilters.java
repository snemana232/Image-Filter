import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/*author: Srikanth Nemana, UT Computer Science & Mathematics
 * 
 */

public class VariousFilters {
	
	public static void main(String[] args) throws IOException {
		while (true) {
			Scanner sc = new Scanner(System.in);
			File fileName = checkFile(sc);
			BufferedImage img = getImage(fileName);
			BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
			giveOptions(sc, img, result, fileName);
		}
	}
	
	public static File checkFile(Scanner sc) {
		System.out.print("Please enter the name of the file you wish to process: ");
		File fileName = new File(sc.nextLine());
		System.out.println();
		return fileName;	
	}
	
	/*
	 * @param sc: a Scanner connected to System.in
	 * 
	 */
	public static void giveOptions(Scanner sc, BufferedImage original, BufferedImage end, File writeToOutput) throws IOException {
		// TODO Auto-generated method stub
		String keepProcessing = "y";
		while (keepProcessing.equals("y")) {	
			System.out.println("Enter 1 to invert image colors\n"
					+ "Enter 2 to blur image\n"
					+ "Enter 3 to reflect image horizontally\n"
					+ "Enter 4 to reflect image vertically\n");
			System.out.print("Please choose an image processing method: ");
			int result = sc.nextInt();
			if (result == 1) {
				invertColors(original, end, writeToOutput);
			} else if (result == 2) {
				blurColors(original, end, writeToOutput);
			} else if (result == 3) {
				flipOverYAxis(original, end, writeToOutput);
			} else if (result == 4) {
				flipOverXAxis(original, end, writeToOutput);
			} else {
				System.out.println("Invalid input.");
			}
			System.out.print("Do you wish to continue processing this image? ");
			keepProcessing = sc.nextLine();
		}
		
		
		
	}

	private static void flipOverXAxis(BufferedImage original, BufferedImage end, File writeToOutput) throws IOException {
		// TODO Auto-generated method stub
		for (int x = 0; x < original.getWidth(); x++) {
			for (int y = 0; y < original.getHeight(); y++) {
				end.setRGB(x,  original.getHeight() - y - 1,  original.getRGB(x, y));
			}
		}
		ImageIO.write(end, "png", writeToOutput);
	}

	private static void flipOverYAxis(BufferedImage original, BufferedImage end, File writeToOutput) throws IOException {
		// TODO Auto-generated method stub
		for (int x = 0; x < original.getWidth(); x++) {
			for (int y = 0; y < original.getHeight(); y++) {
				end.setRGB(original.getWidth() - x  -1, y, original.getRGB(x, y));
			}
		}
		ImageIO.write(end, "png", writeToOutput);
	}

	
	/*
	 * 
	 */
	private static void blurColors(BufferedImage original, BufferedImage end, File writeToOutput) throws IOException {
		// TODO Auto-generated method stub
		for (int x = 0; x < original.getWidth(); x++) {
			for (int y = 0; y < original.getHeight(); y++) {
				end.setRGB(x, y, getNeighAverage(original, x, y, 3).getRGB());
			}
		}
		ImageIO.write(end, "png", writeToOutput);
	}
	
	private static Color getNeighAverage(BufferedImage original, int initX, int initY, int neighSize) {
		int rTotal = 0;
		int gTotal = 0;
		int bTotal = 0;
		int count = 0;
		for (int x = initX - neighSize; x <= initX + neighSize; x++) {
			for (int y = initY - neighSize; y <= initY + neighSize; y++) {
				if (inbounds(x, y, original)) {
					Color c1 = new Color(original.getRGB(x,  y));
					rTotal += c1.getRed();
					gTotal += c1.getGreen();
					bTotal += c1.getBlue();
					count++;
				}
			}
		}
		return new Color(rTotal/count, gTotal/count, bTotal/count);
	}

	private static boolean inbounds(int x, int y, BufferedImage original) {
		// TODO Auto-generated method stub
		return 0 <= x && x < original.getWidth() && 0 <= y && y < original.getHeight();
	}

	public static BufferedImage getImage(File fileName) throws IOException {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(fileName);
		} catch (IOException e) {
			System.out.println("That wasn't supposed to happen: uh - oh");
			System.out.println("Invalid file name/not an image. Restart the program");
		}
		return img;
	}
	
	
	public static void invertColors(BufferedImage original, BufferedImage end, File fileName) throws IOException {
		int max = 255;
		for (int x = 0; x < original.getWidth(); x++) {
			for (int y = 0; y < original.getHeight(); y++) {
				Color pixel = new Color(original.getRGB(x, y));
				int rd = max - pixel.getRed();
				int g = max - pixel.getGreen();
				int b = max - pixel.getBlue();
				end.setRGB(x, y, new Color(rd, g, b).getRGB());
			}
		}
		ImageIO.write(end, "png", fileName);	
	}
	
	
	public static void panoramicStitcher(BufferedImage[] o1, BufferedImage end, File fileName) throws IOException {
			
	}
}
