package cs3500.pa04;

import cs3500.pa03.controller.Controller;
import cs3500.pa03.controller.CoreController;
import cs3500.pa03.model.AiPlayerModel;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    Readable input = new InputStreamReader(System.in);
    if (args.length == 0) {
      Appendable output = new PrintStream(System.out);
      CoreController controller = new CoreController(input, output, new Random(1));
      controller.run();
    } else if (args.length == 2) {
      try {
        runClient(args[0], Integer.parseInt(args[1]));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

    } else {
      throw new IllegalArgumentException("Invalid number of arguments (must be 0 or 2).");
    }


  }

  private static void runClient(String host, int port)
      throws IOException, IllegalStateException {
    Socket server = new Socket(host, port);
    Controller proxyController = new ProxyController(server, new AiPlayerModel(new Random()));
    proxyController.run();
  }
}