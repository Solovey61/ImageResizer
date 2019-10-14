import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        UserInterface ui = new UserInterface();
        ui.print("Copying resized pictures using multithreading program.");
        String srcFolder = ui.getUserInput("Source folder: ");
        String dstFolder = ui.getUserInput("Destination folder: ");
        int resizeToWidth = Integer.parseInt(ui.getUserInput("Resized pictures width: "));
        ThreadsManager.startThreads(srcFolder, dstFolder, resizeToWidth);
    }
}
