import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInterface {
	public String getUserInput(String message) throws IOException {
		print(message);
		return getUserInput();
	}

	public String getUserInput() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		return reader.readLine();
	}

	public void print(String message) {
		System.out.println(message);
	}
}
