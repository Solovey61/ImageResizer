import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        final int processorsCount = Runtime.getRuntime().availableProcessors();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Copying resized pictures using multithreading program.");
        System.out.print("Source folder: ");
        File srcDir = new File(reader.readLine());
        System.out.print("Destination folder: ");
        String dstFolder = reader.readLine();
        System.out.print("Resized pictures width: ");
        final int newWidth = Integer.parseInt(reader.readLine());

        long start = System.currentTimeMillis();
        File[] files = srcDir.listFiles();

        int step = files.length / processorsCount;
        for (int i = 0; i < processorsCount; i++) {
            File[] filesCurrentThread;
            if (i != processorsCount - 1) {
                filesCurrentThread = Arrays.copyOfRange(files, i * step, i * step + step);
            } else {
                filesCurrentThread = Arrays.copyOfRange(files, i * step, files.length);
            }
            int finalI = i;
            new Thread(() -> {
                for (File file : filesCurrentThread) {
                    try {
                        BufferedImage image = ImageIO.read(file);
                        if (image == null) {
                            continue;
                        }

                        int newHeight = (int) Math.round(
                                image.getHeight() / (image.getWidth() / (double) newWidth)
                        );

                        BufferedImage newImage = Scalr.resize(image, Scalr.Mode.AUTOMATIC, newWidth, newHeight);
                        File newFile = new File(dstFolder + "/" + file.getName());
                        ImageIO.write(newImage, "jpg", newFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println("Thread[" + finalI + "] runtime - " + (System.currentTimeMillis() - start + "ms."));
            }).start();
        }
    }
}
