import java.net.Socket;
import java.net.InetAddress;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;

public class Http09Client {
	public static void main(String[] args) throws Throwable {
		String uri;
		if (args.length > 0) {
			uri = args[0];
		} else {
			System.out.print("Írd be az URI-t: ");
			Scanner sc = new Scanner(System.in);
			uri = sc.nextLine();
			sc.close();
		}

		if (!uri.startsWith("http://")) {
			System.err.println("Hibás az URI! Hiányzik a kezdő 'http://'!");
			return;
		}

		String domain = uri.substring("http://".length()).split("/")[0];
		int pathOffset = "http://".length() + domain.length();
		int port = 80;
		if (domain.split(":").length >= 2) {
			port = Integer.parseInt(domain.split(":")[1]);
		}
		domain = domain.split(":")[0];

		String path = uri.substring(pathOffset);

		System.out.printf("Domain: %s%n", domain);
		System.out.printf("Port:   %d%n", port);
		System.out.printf("Path:   %s%n", path);

		InetAddress address = InetAddress.getByName(domain);
		Socket socket = new Socket(address, port);

		OutputStream os = socket.getOutputStream();
		os.write(String.format("GET %s\r\n", path).getBytes());

		System.out.printf("%nVálasz:%n");
		InputStream is = socket.getInputStream();
		int c;
		while ((c = is.read()) != -1) {
			System.out.print((char) c);
		}

		socket.close();
	}
}