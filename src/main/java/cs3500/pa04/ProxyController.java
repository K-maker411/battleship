package cs3500.pa04;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.Coord;
import cs3500.pa03.GameResult;
import cs3500.pa03.Ship;
import cs3500.pa03.ShipType;
import cs3500.pa03.controller.Controller;
import cs3500.pa03.model.Player;
import cs3500.pa04.jsonrecords.EndGameJson;
import cs3500.pa04.jsonrecords.FleetJson;
import cs3500.pa04.jsonrecords.JoinJson;
import cs3500.pa04.jsonrecords.MessageJson;
import cs3500.pa04.jsonrecords.SetupJson;
import cs3500.pa04.jsonrecords.VolleyJson;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

/**
 * uses proxy pattern to talk ton the server and dispatch methods to the player
 */
public class ProxyController implements Controller {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");


  /**
   * constructor
   *
   * @param server server to connect to
   * @param player the player to use
   * @throws IOException if input or output stream can't be found
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }

  /**
   * starts the given controller
   */
  @Override
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        this.delegateMessage(message);
      }
    } catch (IOException e) {
      //throw new IllegalArgumentException("Could not connect to server using given port.");
    }
  }

  /**
   * delegates the message to the appropriate helper based on the
   * name of the method sent by the server
   *
   * @param message json sent by the server containing the method name and the arguments
   */
  private void delegateMessage(MessageJson message) {
    String name = message.methodName();
    JsonNode arguments = message.arguments();

    switch (name) {
      case "join" -> this.handleJoin();
      case "setup" -> this.handleSetup(arguments);
      case "take-shots" -> this.handleTakeShots();
      case "report-damage" -> this.handleReportDamage(arguments);
      case "successful-hits" -> this.handleSuccessfulHits(arguments);
      case "end-game" -> this.handleEndGame(arguments);
    }

  }

  /**
   * handles join message
   */
  private void handleJoin() {
    String name = this.player.name();
    String gameType = "SINGLE";
    JoinJson response = new JoinJson(name, gameType);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson message = new MessageJson("join", jsonResponse);
    this.writeMessageToOutput(message);
  }

  /**
   * handles setup message
   *
   * @param arguments arguments for setup method given by the server
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson setupArgs = this.mapper.convertValue(arguments, SetupJson.class);
    int height = setupArgs.height();
    int width = setupArgs.width();
    Map<ShipType, Integer> specs = setupArgs.specs();
    List<Ship> setupShips = this.player.setup(height, width, specs);

    FleetJson response = new FleetJson(setupShips);

    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson message = new MessageJson("setup", jsonResponse);
    this.writeMessageToOutput(message);
  }

  /**
   * handles takeShots message
   */
  private void handleTakeShots() {
    List<Coord> shots = this.player.takeShots();

    VolleyJson response = new VolleyJson(shots);

    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson message = new MessageJson("take-shots", jsonResponse);
    this.writeMessageToOutput(message);
  }

  /**
   * handles reportDamage message
   *
   * @param arguments json of coordinates shot by the opponent ai
   */
  private void handleReportDamage(JsonNode arguments) {
    VolleyJson coords = this.mapper.convertValue(arguments, VolleyJson.class);
    List<Coord> coordsAsList = coords.volley();

    List<Coord> shotsThatHit = this.player.reportDamage(coordsAsList);
    VolleyJson response = new VolleyJson(shotsThatHit);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);

    MessageJson message = new MessageJson("report-damage", jsonResponse);
    this.writeMessageToOutput(message);
  }

  /**
   * handles successfulHits message
   *
   * @param arguments json of coordinates that successfully hit the opponent
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    ObjectMapper mapper = new ObjectMapper();
    VolleyJson coords = this.mapper.convertValue(arguments, VolleyJson.class);
    List<Coord> coordsAsList = coords.volley();
    this.player.successfulHits(coordsAsList);

    MessageJson message = new MessageJson("successful-hits", mapper.createObjectNode());
    this.writeMessageToOutput(message);
  }

  /**
   * handles endGame message
   *
   * @param arguments json of whether the user won or lost
   */
  private void handleEndGame(JsonNode arguments) {
    ObjectMapper mapper = new ObjectMapper();
    EndGameJson endGameJson = this.mapper.convertValue(arguments, EndGameJson.class);
    GameResult result = endGameJson.result();
    String reason = endGameJson.reason();

    this.player.endGame(result, reason);

    MessageJson message = new MessageJson("end-game", mapper.createObjectNode());
    this.writeMessageToOutput(message);

    try {
      this.server.close();
    } catch (IOException ignored) {

    }

  }

  /**
   * serializes and sends MessageJson back to the server
   *
   * @param message message to serialize and write to the server
   */
  private void writeMessageToOutput(MessageJson message) {
    JsonNode jsonMessage = JsonUtils.serializeRecord(message);
    this.out.println(jsonMessage);
  }


}
