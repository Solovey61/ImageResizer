import java.io.IOException;

public abstract class ThreadsManager {
    private static final int processorsCount = Runtime.getRuntime().availableProcessors();

    public static void startThreads(String srcFolder, String dstFolder, int resizeToWidth) throws IOException {
        ImageResizer imageResizer = new ImageResizer(srcFolder, dstFolder, resizeToWidth);
        for (int i = 0; i < processorsCount; i++) {
            new Thread(imageResizer).start();
        }
    }
}
