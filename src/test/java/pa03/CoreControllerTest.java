package pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.ShipType;
import cs3500.pa03.controller.CoreController;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * tests CoreController
 */
public class CoreControllerTest {
  Random rand;
  Map<ShipType, Integer> sampleSpecs;

  String sep;
  String welcomePlayer;
  String invalidDims;
  String askForShipCounts;
  String invalidFleetSize;
  String invalidCoordinates;
  String askForShotCoordinates;
  String askForThreeShots;
  String askForTwoShots;
  String askForOneShot;
  String youHaveWon;
  String opponentBoardData;
  String yourBoard;
  String initialOpponentBoard;
  String initialHumanBoard;
  String youHaveLost;

  /**
   * sets up random and sampleSpecs
   */
  @BeforeEach
  public void setUp() {
    rand = new Random(1);
    sampleSpecs = new LinkedHashMap<>();
    sampleSpecs.put(ShipType.CARRIER, 1);
    sampleSpecs.put(ShipType.BATTLESHIP, 1);
    sampleSpecs.put(ShipType.DESTROYER, 1);
    sampleSpecs.put(ShipType.SUBMARINE, 1);

    sep = System.lineSeparator();

    welcomePlayer = "Hello! Welcome to the OOD BattleSalvo Game! " + sep
        + "Please enter a valid height and width below: " + sep
        + "(only the first two inputs will be read)" + sep
        + "------------------------------------------------------" + sep;

    invalidDims =
        "------------------------------------------------------" + sep
            + "Uh Oh! You've entered invalid dimensions. Please remember that the"
            + " height and width" + sep
            + "of the game must be in the range (6, 15), inclusive. "
            + "Try again! (Only the first two numbers inputted will be read)" + sep
            + "------------------------------------------------------" + sep;

    askForShipCounts =
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]."
            + sep + "Remember, your fleet may not exceed size 6" + "." + sep
            + "--------------------------------------------------------------------------------"
            + sep;

    invalidFleetSize =
        "--------------------------------------------------------------------------------"
            + sep
            + "Uh Oh! You've entered invalid fleet sizes." + sep
            + "Please enter your fleet in the order"
            + " [Carrier, Battleship, Destroyer, Submarine]."
            + sep
            + "Remember, your fleet may not exceed size 6." + sep
            + "(Only the first 4 inputs will be read)" + sep
            + "--------------------------------------------------------------------------------"
            + sep;

    invalidCoordinates =
        "--------------------------------------------------------------------------------"
            + sep + "Uh Oh! You've entered invalid coordinates." + sep
            + "Please enter " + 4 + " shots:" + sep
            + "--------------------------------------------------------------------------------"
            + sep;

    askForShotCoordinates = "Please Enter " + 4 + " Shots:" + sep
        + "------------------------------------------------------------------"
        + sep;

    askForThreeShots = "Please Enter " + 3 + " Shots:" + sep
        + "------------------------------------------------------------------"
        + sep;

    askForTwoShots = "Please Enter " + 2 + " Shots:" + sep
        + "------------------------------------------------------------------"
        + sep;

    askForOneShot = "Please Enter " + 1 + " Shots:" + sep
        + "------------------------------------------------------------------"
        + sep;

    youHaveWon = "You have won!";

    youHaveLost = "You have lost!";

    opponentBoardData = "Opponent Board Data:" + sep;

    yourBoard = "Your Board:" + sep;

    initialOpponentBoard =
        "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep + sep;

    initialHumanBoard =
        "D 0 0 0 0 0 " + sep
            + "D B B B B B " + sep
            + "D 0 0 0 0 0 " + sep
            + "D 0 0 0 0 0 " + sep
            + "C C C C C C " + sep
            + "S S S 0 0 0 " + sep + sep;

  }


  /**
   * tests run (in win case)
   */
  @Test
  public void testRunWin() {

    String inputs =
        "16 5" + sep + "6 6" + sep + "0 1 1 1" + sep + "1 1 1 1" + sep + "6 1" + sep + "1 6"
            + sep + "1 1" + sep + "2 1" + sep + "3 1" + sep + "4 1" + sep + "5 1" + sep + "0 2"
            + sep + "1 2" + sep + "2 2" + sep + "3 2" + sep + "0 4" + sep + "1 4" + sep + "2 4"
            + sep + "3 4" + sep + "4 4" + sep + "5 4" + sep + "2 5" + sep + "3 5" + sep + "4 5"
            + sep + "4 4" + sep + "5 4" + sep;

    String turnOneOpponentBoard =
        "0 0 0 0 0 0 " + sep
            + "0 H H H H 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep + sep;

    String turnOnePlayerBoard =
        "D 0 M 0 0 0 " + sep
            + "D H B B B B " + sep
            + "D 0 0 0 0 0 " + sep
            + "D 0 M 0 0 0 " + sep
            + "H C C C C C " + sep
            + "S S S 0 0 0 " + sep + sep;

    String turnTwoOpponentBoard =
        "0 0 0 0 0 0 " + sep
            + "0 H H H H H " + sep
            + "H H H 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep + sep;

    String turnTwoPlayerBoard =
        "D 0 M 0 M 0 " + sep
            + "D H B B B H " + sep
            + "D M M 0 0 0 " + sep
            + "D 0 M 0 0 0 " + sep
            + "H C C C C C " + sep
            + "S S S 0 0 0 " + sep + sep;

    String turnThreeOpponentBoard =
        "0 0 0 0 0 0 " + sep
            + "0 H H H H H " + sep
            + "H H H H 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "H H H 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep + sep;

    String turnThreePlayerBoard =
        "D 0 M 0 M 0 " + sep
            + "D H B B B H " + sep
            + "D M M 0 0 0 " + sep
            + "D 0 M 0 0 0 " + sep
            + "H H H C H C " + sep
            + "S S S 0 0 0 " + sep + sep;

    String turnFourOpponentBoard =
        "0 0 0 0 0 0 " + sep
            + "0 H H H H H " + sep
            + "H H H H 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "H H H H H H " + sep
            + "0 0 H 0 0 0 " + sep + sep;

    String turnFourPlayerBoard =
        "D 0 M 0 M M " + sep
            + "D H B B B H " + sep
            + "D M M 0 0 0 " + sep
            + "D 0 M 0 0 0 " + sep
            + "H H H C H C " + sep
            + "S S S M 0 0 " + sep + sep;

    Appendable output = new StringBuilder();
    Readable input = new StringReader(inputs);

    CoreController controller = new CoreController(input, output, rand);
    controller.run();

    String expected = welcomePlayer + invalidDims + askForShipCounts + invalidFleetSize
        + opponentBoardData + initialOpponentBoard + yourBoard + initialHumanBoard
        + askForShotCoordinates + invalidCoordinates + invalidCoordinates + opponentBoardData
        + turnOneOpponentBoard
        + yourBoard + turnOnePlayerBoard + askForShotCoordinates + opponentBoardData
        + turnTwoOpponentBoard + yourBoard + turnTwoPlayerBoard + askForShotCoordinates
        + opponentBoardData + turnThreeOpponentBoard + yourBoard + turnThreePlayerBoard
        + askForShotCoordinates + opponentBoardData + turnFourOpponentBoard
        + yourBoard + turnFourPlayerBoard + askForShotCoordinates + youHaveWon;

    assertEquals(expected, output.toString());

  }

  /**
   * tests run (in loss case)
   */
  @Test
  public void testRunLose() {
    String inputs = "6 6" + sep + "1 1 1 1"
        + sep + "0 0" + sep + "0 0" + sep + "0 0" + sep + "0 0"
        + sep + "0 0" + sep + "0 0" + sep + "0 0" + sep + "0 0"
        + sep + "0 0" + sep + "0 0" + sep + "0 0" + sep + "0 0"
        + sep + "0 0" + sep + "0 0" + sep + "0 0" + sep + "0 0"
        + sep + "0 0" + sep + "0 0" + sep + "0 0" + sep + "0 0"
        + sep + "0 0" + sep + "0 0" + sep + "0 0" + sep + "0 0"
        + sep + "0 0" + sep + "0 0" + sep + "0 0" + sep + "0 0"
        + sep + "0 0" + sep + "0 0" + sep + "0 0"
        + sep + "0 0" + sep + "0 0"
        + sep + "0 0" + sep + "0 0"
        + sep + "0 0" + sep;

    String opponentBoard =
        "M 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep
            + "0 0 0 0 0 0 " + sep + sep;

    String turnOnePlayerBoard =
        "D 0 M 0 0 0 " + sep
            + "D H B B B B " + sep
            + "D 0 0 0 0 0 " + sep
            + "D 0 M 0 0 0 " + sep
            + "H C C C C C " + sep
            + "S S S 0 0 0 " + sep + sep;

    String turnTwoPlayerBoard =
        "D 0 M 0 M 0 " + sep
            + "D H B B B H " + sep
            + "D M M 0 0 0 " + sep
            + "D 0 M 0 0 0 " + sep
            + "H C C C C C " + sep
            + "S S S 0 0 0 " + sep + sep;

    String turnThreePlayerBoard =
        "D 0 M 0 M M " + sep
            + "D H B B B H " + sep
            + "D M M 0 0 0 " + sep
            + "D 0 M 0 0 0 " + sep
            + "H H H C H C " + sep
            + "S S S 0 0 0 " + sep + sep;

    String turnFourPlayerBoard =
        "D 0 M 0 M M " + sep
            + "D H B B B H " + sep
            + "D M M 0 0 0 " + sep
            + "D 0 M M M M " + sep
            + "H H H C H C " + sep
            + "S S S M 0 0 " + sep + sep;

    String turnFivePlayerBoard =
        "H 0 M 0 M M " + sep
            + "D H B H H H " + sep
            + "D M M 0 0 0 " + sep
            + "D 0 M M M M " + sep
            + "H H H C H C " + sep
            + "S H S M 0 0 " + sep + sep;

    String turnSixPlayerBoard =
        "H M M 0 M M " + sep
            + "D H B H H H " + sep
            + "D M M 0 M 0 " + sep
            + "D 0 M M M M " + sep
            + "H H H C H C " + sep
            + "S H H M 0 M " + sep + sep;

    String turnSevenPlayerBoard =
        "H M M 0 M M " + sep
            + "D H B H H H " + sep
            + "D M M 0 M 0 " + sep
            + "H M M M M M " + sep
            + "H H H H H C " + sep
            + "H H H M 0 M " + sep + sep;

    String turnEightPlayerBoard =
        "H M M M M M " + sep
            + "H H B H H H " + sep
            + "D M M M M 0 " + sep
            + "H M M M M M " + sep
            + "H H H H H H " + sep
            + "H H H M 0 M " + sep + sep;

    Appendable output = new StringBuilder();
    Readable input = new StringReader(inputs);

    CoreController controller = new CoreController(input, output, rand);
    controller.run();

    String expected = welcomePlayer + askForShipCounts + opponentBoardData
        + initialOpponentBoard + yourBoard + initialHumanBoard
        + askForShotCoordinates + opponentBoardData + opponentBoard + yourBoard + turnOnePlayerBoard
        + askForShotCoordinates
        + opponentBoardData + opponentBoard + yourBoard + turnTwoPlayerBoard
        + askForShotCoordinates
        + opponentBoardData + opponentBoard + yourBoard + turnThreePlayerBoard
        + askForShotCoordinates
        + opponentBoardData + opponentBoard + yourBoard + turnFourPlayerBoard
        + askForShotCoordinates
        + opponentBoardData + opponentBoard + yourBoard + turnFivePlayerBoard
        + askForShotCoordinates
        + opponentBoardData + opponentBoard + yourBoard + turnSixPlayerBoard
        + askForShotCoordinates
        + opponentBoardData + opponentBoard + yourBoard + turnSevenPlayerBoard
        + askForThreeShots
        + opponentBoardData + opponentBoard + yourBoard + turnEightPlayerBoard
        + askForTwoShots + youHaveLost;

    assertEquals(expected, output.toString());
  }


}
