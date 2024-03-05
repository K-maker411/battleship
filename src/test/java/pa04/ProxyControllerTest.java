package pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.Coord;
import cs3500.pa03.GameResult;
import cs3500.pa03.Ship;
import cs3500.pa03.ShipOrientation;
import cs3500.pa03.ShipType;
import cs3500.pa03.controller.Controller;
import cs3500.pa03.model.AiPlayerModel;
import cs3500.pa03.model.Player;
import cs3500.pa04.JsonUtils;
import cs3500.pa04.ProxyController;
import cs3500.pa04.jsonrecords.EndGameJson;
import cs3500.pa04.jsonrecords.FleetJson;
import cs3500.pa04.jsonrecords.MessageJson;
import cs3500.pa04.jsonrecords.SetupJson;
import cs3500.pa04.jsonrecords.VolleyJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private Random rand;
  private Player aiPlayer;
  private Controller proxyController;
  private ObjectMapper mapper;
  private Map<ShipType, Integer> specs;


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
    this.rand = new Random(1);
    this.aiPlayer = new AiPlayerModel(this.rand);
    this.mapper = new ObjectMapper();
    this.specs = new LinkedHashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);
  }

  /**
   * Check that the player sends back the correct response after being
   * given a join request by the server
   */
  @Test
  public void testHandleJoin() {

    MessageJson messageJson = new MessageJson("join", this.mapper.createObjectNode());

    Mocket socket = new Mocket(this.testLog,
        List.of(JsonUtils.serializeRecord(messageJson).toString()));

    try {
      this.proxyController = new ProxyController(socket, this.aiPlayer);
    } catch (IOException e) {
      fail("Failed to create proxy controller");
    }

    this.proxyController.run();
    String expected = "{\"method-name\":\"join\",\"arguments\":{\"name\":"
        + "\"K-maker411\",\"game-type\":\"SINGLE\"}}"
        + System.lineSeparator();
    assertEquals(expected, logToString());

  }

  // confirmed: test that i'm sending the correct thing back, makes more sense anyway

  /**
   * tests that a setup request from the server is sent back
   * the correct information by the ProxyController
   */
  @Test
  public void testHandleSetup() {


    SetupJson setupJson = new SetupJson(6, 6, this.specs);
    JsonNode jsonNode = this.createSampleMessage("setup", setupJson);
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.proxyController = new ProxyController(socket, this.aiPlayer);
    } catch (IOException e) {
      fail("Failed to create proxy controller");
    }

    this.proxyController.run();
    List<Ship> expectedShips = new ArrayList<>();
    expectedShips.add(
        new Ship(ShipType.CARRIER, ShipOrientation.HORIZONTAL, new Coord(0, 4)));
    expectedShips.add(
        new Ship(ShipType.BATTLESHIP, ShipOrientation.HORIZONTAL, new Coord(1, 1)));
    expectedShips.add(
        new Ship(ShipType.DESTROYER, ShipOrientation.VERTICAL, new Coord(0, 0)));
    expectedShips.add(
        new Ship(ShipType.SUBMARINE, ShipOrientation.HORIZONTAL, new Coord(0, 5)));

    FleetJson fleetJson = new FleetJson(expectedShips);


    JsonNode expectedMessage = this.createSampleMessage("setup", fleetJson);

    assertEquals(expectedMessage.toString() + System.lineSeparator(), this.logToString());
  }

  /**
   * tests that the expected shots are taken by the AI and are sent to the server
   * based on the initial request from the server to take shots
   */
  @Test
  public void testHandleTakeShots() {
    this.aiPlayer.setup(6, 6, this.specs);

    MessageJson messageJson = new MessageJson("take-shots",
        this.mapper.createObjectNode());

    Mocket socket = new Mocket(this.testLog,
        List.of(JsonUtils.serializeRecord(messageJson).toString()));

    try {
      this.proxyController = new ProxyController(socket, this.aiPlayer);
    } catch (IOException e) {
      fail("Failed to create proxy controller");
    }

    this.proxyController.run();
    List<Coord> expected = new ArrayList<>();
    expected.add(new Coord(4, 1));
    expected.add(new Coord(2, 2));
    expected.add(new Coord(1, 4));
    expected.add(new Coord(0, 4));


    VolleyJson volleyJson = new VolleyJson(expected);
    JsonNode expectedMessage = this.createSampleMessage("take-shots", volleyJson);

    assertEquals(expectedMessage.toString() + System.lineSeparator(), this.logToString());
  }

  /**
   * tests that the AI player reports back the shots from the that hit its ship(s)
   */
  @Test
  public void testHandleReportDamage() {
    this.aiPlayer.setup(6, 6, specs);

    List<Coord> volleyForServerMessage = new ArrayList<>();
    volleyForServerMessage.add(new Coord(0, 4));
    volleyForServerMessage.add(new Coord(5, 5));

    VolleyJson volleyJson = new VolleyJson(volleyForServerMessage);

    JsonNode jsonNode = this.createSampleMessage("report-damage", volleyJson);
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.proxyController = new ProxyController(socket, this.aiPlayer);
    } catch (IOException e) {
      fail("Failed to create proxy controller");
    }

    this.proxyController.run();

    List<Coord> volleyForResponse = new ArrayList<>();
    volleyForResponse.add(new Coord(0, 4));

    VolleyJson responseVolley = new VolleyJson(volleyForResponse);
    JsonNode expected = this.createSampleMessage("report-damage",
        responseVolley);

    assertEquals(expected.toString() + System.lineSeparator(), this.logToString());
  }

  /**
   * tests that when the AI gets hit, it sends a response back (with an empty body)
   */
  @Test
  public void testHandleSuccessfulHits() {
    this.aiPlayer.setup(6, 6, specs);
    List<Coord> volleyForServerMessage = new ArrayList<>();
    volleyForServerMessage.add(new Coord(0, 4));
    volleyForServerMessage.add(new Coord(5, 5));

    VolleyJson volleyJson = new VolleyJson(volleyForServerMessage);

    JsonNode jsonNode = this.createSampleMessage("successful-hits", volleyJson);
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.proxyController = new ProxyController(socket, this.aiPlayer);
    } catch (IOException e) {
      fail("Failed to create proxy controller");
    }

    this.proxyController.run();

    String expected = "{\"method-name\":\"successful-hits\",\"arguments\":{}}";
    assertEquals(expected + System.lineSeparator(), this.logToString());
  }

  /**
   * tests that when the end-game request is sent by the server,
   * the client sends back an empty-args response
   */
  @Test
  public void testHandleEndGame() {
    this.aiPlayer.setup(6, 6, this.specs);

    EndGameJson endGameJson = new EndGameJson(GameResult.WIN,
        "Player 1 sank all of Player 2's ships");

    JsonNode jsonNode = this.createSampleMessage("end-game", endGameJson);
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.proxyController = new ProxyController(socket, this.aiPlayer);
    } catch (IOException e) {
      fail("Failed to create proxy controller");
    }

    this.proxyController.run();
    String expected = "{\"method-name\":\"end-game\",\"arguments\":{}}";

    assertEquals(expected + System.lineSeparator(), this.logToString());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson = new MessageJson(messageName,
        JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}
