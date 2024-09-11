package com.imagex;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) throws IOException {

    }


    public static boolean isWithinTolerance(int r, int g, int b, int targetR, int targetG, int targetB, int tolerance) {
        return Math.abs(r - targetR) <= tolerance &&
                Math.abs(g - targetG) <= tolerance &&
                Math.abs(b - targetB) <= tolerance;
    }


    // Function to convert image to grayscale with transparency (Alpha)
    public static void toGrayscale(String inputImagePath, String outputImagePath) {
        try {
            BufferedImage inputImage = ImageIO.read(new File(inputImagePath));
            BufferedImage outputImage = new BufferedImage(inputImage.getWidth(),
                    inputImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

            for (int y = 0; y < inputImage.getHeight(); y++) {
                for (int x = 0; x < inputImage.getWidth(); x++) {
                    int rgb = inputImage.getRGB(x, y);

                    // Extract ARGB components
                    int alpha = (rgb >> 24) & 0xFF;
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    int gray = (int) (red * 0.299 + green * 0.587 + blue * 0.114);

                    int grayRGB = (alpha << 24) | (gray << 16) | (gray << 8) | gray;

                    outputImage.setRGB(x, y, grayRGB);
                }
            }

            ImageIO.write(outputImage, "png", new File(outputImagePath));
            System.out.println("Image converted to grayscale with transparency successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static void imagePixelate(String inputImagePath, String outputImagePath, int pixelSize) throws IOException {
        BufferedImage inputImage = ImageIO.read(new File(inputImagePath));
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        for (int y = 0; y < height - pixelSize; y += pixelSize) {
            for (int x = 0; x < width - pixelSize; x += pixelSize) {
                int red = 0, green = 0, blue = 0;

                // Sum the RGB values within the pixel block
                for (int i = 0; i < pixelSize; i++) {
                    for (int j = 0; j < pixelSize; j++) {
                        int rgb = inputImage.getRGB(x + i, y + j);
                        red += (rgb >> 16) & 0xFF;
                        green += (rgb >> 8) & 0xFF;
                        blue += rgb & 0xFF;
                    }
                }

                // Average the color components
                red /= (pixelSize * pixelSize);
                green /= (pixelSize * pixelSize);
                blue /= (pixelSize * pixelSize);

                int p = (255 << 24) | (red << 16) | (green << 8) | blue;

                for (int i = 0; i < pixelSize; i++) {
                    for (int j = 0; j < pixelSize; j++) {
                        outputImage.setRGB(x + i, y + j, p);
                    }
                }
            }
        }

        ImageIO.write(outputImage, "png", new File(outputImagePath));
        System.out.println("Image pixelated successfully.");
    }



    // Create a random image
    public static void createRandomImage(int width, int height) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("image.ppm"))) {
            // Write the PPM header
            writer.write("P3\n");
            writer.write(width + " " + height + "\n");
            writer.write("255\n");

            Random random = new Random();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int r = random.nextInt(256) % 256; // Red component
                    int g = random.nextInt(256) % 256; // Green component
                    int b = random.nextInt(256) % 256; // Blue component

                    writer.write(String.format("%d %d %d ", r, g, b));
                }
                writer.write("\n");
            }
        }
    }


    public static void imageBlueFilter(String inputImagePath, String outputImagePath, int filterIntensity) throws IOException {
        try {
            BufferedImage inputImage = ImageIO.read(new File(inputImagePath));
            BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());

            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = inputImage.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    // Apply blue filter
                    if (blue > 0 && blue < 255) {
                        blue = (int) (blue * (1 + (filterIntensity / 100.0)));
                        blue = Math.min(255, Math.max(0, blue));
                    }

                    int newRGB = (red << 16) | (green << 8) | blue;
                    outputImage.setRGB(x, y, newRGB);
                }
            }

            ImageIO.write(outputImage, "png", new File(outputImagePath));
            System.out.println("Image blue filter applied successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }


    // Function to apply red filter
    public static void imageRedFilter(String inputImagePath, String outputImagePath, int filterIntensity) throws IOException {
        try {
            BufferedImage inputImage = ImageIO.read(new File(inputImagePath));
            BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());

            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = inputImage.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    // Apply red filter
                    if (red > 0 && red < 255) {
                        red = (int) (red * (1 + (filterIntensity / 100.0)));
                        red = Math.min(255, Math.max(0, red));
                    }

                    int newRGB = (red << 16) | (green << 8) | blue;
                    outputImage.setRGB(x, y, newRGB);
                }
            }

            ImageIO.write(outputImage, "png", new File(outputImagePath));
            System.out.println("Image red filter applied successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // Function to apply green filter
    public static void imageGreenFilter(String inputImagePath, String outputImagePath, int filterIntensity) throws IOException {
        try {
            BufferedImage inputImage = ImageIO.read(new File(inputImagePath));
            BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());

            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = inputImage.getRGB(x, y);
                    int red = (rgb >> 16) & 0xFF;
                    int green = (rgb >> 8) & 0xFF;
                    int blue = rgb & 0xFF;

                    // Apply green filter
                    if (green > 0 && green < 255) {
                        green = (int) (green * (1 + (filterIntensity / 100.0)));
                        green = Math.min(255, Math.max(0, green));
                    }

                    int newRGB = (red << 16) | (green << 8) | blue;
                    outputImage.setRGB(x, y, newRGB);
                }
            }

            ImageIO.write(outputImage, "png", new File(outputImagePath));
            System.out.println("Image green filter applied successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // Green Screen Effect
    public static void applyGreenScreen(String inputImagePath, String outputImagePath, int tolerance) throws IOException {
        BufferedImage inputImage = ImageIO.read(new File(inputImagePath));

        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        int targetR = 0;
        int targetG = 255;
        int targetB = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                if (isWithinTolerance(r, g, b, targetR, targetG, targetB, tolerance)) {
                    outputImage.setRGB(x, y, 0x00000000); // Set to transparent
                } else {
                    outputImage.setRGB(x, y, rgb); // Keep the original pixel
                }
            }
        }

        ImageIO.write(outputImage, "png", new File(outputImagePath));
        System.out.println("Green screen applied successfully.");
    }


    public static void applyChromaKeying(String inputImagePath, String outputImagePath, Color targetColor, int tolerance) throws IOException {
        BufferedImage inputImage = ImageIO.read(new File(inputImagePath));

        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        int targetR = targetColor.getRed();
        int targetG = targetColor.getGreen();
        int targetB = targetColor.getBlue();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                if (isWithinTolerance(r, g, b, targetR, targetG, targetB, tolerance)) {
                    outputImage.setRGB(x, y, 0x00000000); // Set to transparent
                } else {
                    outputImage.setRGB(x, y, rgb); // Keep the original pixel color
                }
            }
        }

        ImageIO.write(outputImage, "png", new File(outputImagePath));
        System.out.println("Dynamic color screen applied successfully.");
    }


    public static void applyChromaKeyingFromPixel(String inputImagePath, String outputImagePath, int pixelX, int pixelY, int tolerance) throws IOException {
        BufferedImage inputImage = ImageIO.read(new File(inputImagePath));

        // Ensure the specified pixel is within the image bounds
        if (pixelX < 0 || pixelX >= inputImage.getWidth() || pixelY < 0 || pixelY >= inputImage.getHeight()) {
            throw new IllegalArgumentException("Specified pixel is out of image bounds.");
        }

        // Get the color of the specified pixel
        int targetRGB = inputImage.getRGB(pixelX, pixelY);
        int targetR = (targetRGB >> 16) & 0xFF;
        int targetG = (targetRGB >> 8) & 0xFF;
        int targetB = targetRGB & 0xFF;

        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        int width = inputImage.getWidth();
        int height = inputImage.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = inputImage.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                if (isWithinTolerance(r, g, b, targetR, targetG, targetB, tolerance)) {
                    outputImage.setRGB(x, y, 0x00000000); // Set to transparent
                } else {
                    outputImage.setRGB(x, y, rgb); // Keep the original pixel color
                }
            }
        }

        ImageIO.write(outputImage, "png", new File(outputImagePath));
        System.out.println("Dynamic color screen applied successfully.");
    }


}
