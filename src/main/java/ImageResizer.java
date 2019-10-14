import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class ImageResizer implements Runnable {
	private Queue<Path> queue = new ConcurrentLinkedQueue<>();
	private String srcFolder;
	private String dstFolder;
	private int resizeToWidth;

	public ImageResizer(String srcFolder, String dstFolder, int resizeToWidth) throws IOException {
	    this.srcFolder = srcFolder;
	    this.dstFolder = dstFolder;
	    this.resizeToWidth = resizeToWidth;
		queue.addAll(Files.walk(Paths.get(srcFolder)).collect(Collectors.toList()));
	}

	@Override
	public void run() {
		for (;;) {
			Path currentFile = queue.poll();
			if (currentFile == null)
				break;
			try {
				if (!Files.isDirectory(currentFile)) {
					long start = System.currentTimeMillis();
					System.out.println("Trying to resize image " + currentFile.getFileName());
					BufferedImage srcImage = ImageIO.read(currentFile.toFile());
					BufferedImage resizedImage = Scalr.resize(srcImage, resizeToWidth);
					ImageIO.write(resizedImage, "png", new File(String.valueOf(Path.of(dstFolder).resolve(Path.of(srcFolder).relativize(currentFile)))));
					System.out.println("Image " + currentFile.getFileName() + " resized successfully in " + (System.currentTimeMillis() - start) + " ms.");
				} else {
					Path dir = Path.of(dstFolder).resolve(Path.of(srcFolder).relativize(currentFile));
				    if (!Files.exists(dir))
				    	Files.createDirectory(dir);
				}
			} catch (IOException e) {
				System.err.println("Can't resize file" + currentFile.getFileName());
			}
		}
	}
}
