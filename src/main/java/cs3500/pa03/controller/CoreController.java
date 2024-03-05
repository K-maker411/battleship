package cs3500.pa03.controller;

import cs3500.pa03.Coord;
import cs3500.pa03.Ship;
import cs3500.pa03.ShipType;
import cs3500.pa03.model.AiPlayerModel;
import cs3500.pa03.model.ConfigurationModel;
import cs3500.pa03.model.HumanPlayerModel;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.CoreView;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * represents the controller that deals with writing to the view and the communicating
 * with the model
 */
public class CoreController implements Controller {
  private Readable input;
  private Scanner reader;
  private CoreView view;
  private ConfigurationModel configModel;
  private List<Coord> lastTakenShots;
  private Player humanPlayer;
  private Player aiPlayer;
  private Random rand;
  private List<Ship> humanShipList;
  private List<Ship> aiShipList;

  /**
   * constructor
   *
   * @param input input "device"
   * @param output output "device"
   * @param rand random number generator
   */
  public CoreController(Readable input, Appendable output, Random rand) {
    this.rand = rand;
    this.input = Objects.requireNonNull(input);
    this.view = new CoreView(Objects.requireNonNull(output));
    this.reader = new Scanner(this.input);
    this.lastTakenShots = new ArrayList<>();
  }

  /**
   * kick-starts program
   */
  @Override
  public void run() {
    this.welcomePlayer();
    this.readBoardDimensions();
    this.askForShipCounts();
    this.readShipCounts();

    Map<ShipType, Integer> specs = new LinkedHashMap<>();
    specs.put(ShipType.CARRIER, configModel.getCarrierCount());
    specs.put(ShipType.BATTLESHIP, configModel.getBattleShipCount());
    specs.put(ShipType.DESTROYER, configModel.getDestroyerCount());
    specs.put(ShipType.SUBMARINE, configModel.getSubmarineCount());

    this.initializePlayers();
    this.setupBothPlayers(specs);


    do {
      this.view.writeToOutput(this.humanPlayer.toString());
      this.readShotCoordinates(this.unsunkCount(this.humanShipList));
      List<Coord> humanPlayerShotsAgainstOpponent = this.humanPlayer.takeShots();
      List<Coord> opponentShotsAgainstHumanPlayer = this.aiPlayer.takeShots();

      List<Coord> humanPlayerDamage =
          this.humanPlayer.reportDamage(opponentShotsAgainstHumanPlayer);
      List<Coord> opponentDamage =
          this.aiPlayer.reportDamage(humanPlayerShotsAgainstOpponent);

      this.humanPlayer.successfulHits(opponentDamage);
      this.aiPlayer.successfulHits(humanPlayerDamage);
    } while (!isGameOver());

  }

  /**
   * checks if the game is over
   *
   * @return true if game is over, false otherwise
   */
  private boolean isGameOver() {
    if (this.unsunkCount(this.humanShipList) == 0 && this.unsunkCount(this.aiShipList) > 0) {
      this.view.writeToOutput("You have lost!");
      return true;
    } else if (this.unsunkCount(this.humanShipList) > 0 && this.unsunkCount(this.aiShipList) == 0) {
      this.view.writeToOutput("You have won!");
      return true;
    } else if (this.unsunkCount(this.humanShipList) == 0
        && this.unsunkCount(this.aiShipList) == 0) {
      this.view.writeToOutput("You have tied!");
      return true;
    } else {
      return false;
    }


  }

  /**
   * sets up both players
   *
   * @param specs specifications for both players
   */
  private void setupBothPlayers(Map<ShipType, Integer> specs) {
    this.humanShipList = this.humanPlayer.setup(configModel.getBoardHeight(),
        configModel.getBoardWidth(), specs);

    this.aiShipList = this.aiPlayer.setup(configModel.getBoardHeight(),
        configModel.getBoardWidth(), specs);
  }

  /**
   * initializes the human and AI players
   */
  private void initializePlayers() {
    this.humanPlayer = new HumanPlayerModel(this.rand, this.lastTakenShots);
    this.aiPlayer = new AiPlayerModel(this.rand);
  }

  /**
   * gets number of unsunk (not fully sunk) ships
   *
   * @param shipList list of ships
   * @return how many unsunk ships in the given list of ships
   */
  private int unsunkCount(List<Ship> shipList) {
    int unsunkCount = 0;

    for (Ship ship : shipList) {
      if (!ship.isSunk()) {
        unsunkCount += 1;
      }
    }

    return unsunkCount;
  }

  /**
   * welcomes user to the game, asks for height and width
   */
  private void welcomePlayer() {
    String s =
        "Hello! Welcome to the OOD BattleSalvo Game! " + System.lineSeparator()
            + "Please enter a valid height and width below: " + System.lineSeparator()
            + "(only the first two inputs will be read)" + System.lineSeparator()
            + "------------------------------------------------------" + System.lineSeparator();

    this.view.writeToOutput(s);
  }

  /**
   * reads requested board dimensions form the user, verifying that they
   * are between 6 and 15 (inclusive) and that non-strings aren't entered instead,
   * then creates ConfigurationModel (from this.configModel) using the given height and width
   */
  private void readBoardDimensions() {
    int height;
    int width;
    //ConfigurationModel configModel;

    while (true) {
      try {
        height = this.reader.nextInt();
        width = this.reader.nextInt();
        this.configModel = new ConfigurationModel(height, width);
        break;
      } catch (InputMismatchException | IllegalArgumentException e) {
        String error =
            "------------------------------------------------------" + System.lineSeparator()
                + "Uh Oh! You've entered invalid dimensions. Please remember that the"
                + " height and width" + System.lineSeparator()
                + "of the game must be in the range (6, 15), inclusive. "
                + "Try again! (Only the first two numbers inputted will be read)"
                + System.lineSeparator()
                + "------------------------------------------------------" + System.lineSeparator();
        this.view.writeToOutput(error);
        this.reader.nextLine();
      }
    }


  }

  /**
   * reads number of ships of each type, does verification and validation,
   * then adds them to this.configModel
   */
  private void readShipCounts() {
    int carrierCount;
    int battleShipCount;
    int destroyerCount;
    int submarineCount;

    while (true) {
      try {
        carrierCount = this.reader.nextInt();
        battleShipCount = this.reader.nextInt();
        destroyerCount = this.reader.nextInt();
        submarineCount = this.reader.nextInt();
        this.configModel.validateShipCounts(carrierCount, battleShipCount,
            destroyerCount, submarineCount);
        break;
      } catch (InputMismatchException | IllegalArgumentException e) {
        String error =
            "--------------------------------------------------------------------------------"
                + System.lineSeparator()
                + "Uh Oh! You've entered invalid fleet sizes." + System.lineSeparator()
                + "Please enter your fleet in the order"
                + " [Carrier, Battleship, Destroyer, Submarine]."
                + System.lineSeparator()
                + "Remember, your fleet may not exceed size "
                + this.configModel.getSmallerDimensionSize() + "." + System.lineSeparator()
                + "(Only the first 4 inputs will be read)" + System.lineSeparator()
                + "--------------------------------------------------------------------------------"
                + System.lineSeparator();

        this.view.writeToOutput(error);
        this.reader.nextLine();
      }
    }
  }

  /**
   * gives prompt to ask for ship counts and outputs it to the view
   */
  private void askForShipCounts() {
    String askForShipCounts =
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]."
            + System.lineSeparator()
            + "Remember, your fleet may not exceed size "
            + this.configModel.getSmallerDimensionSize() + "." + System.lineSeparator()
            + "--------------------------------------------------------------------------------"
            + System.lineSeparator();

    this.view.writeToOutput(askForShipCounts);
  }

  /**
   * extracts the inputted shot coordinates
   *
   * @param numShotsRequired number of shots fired this turn
   */
  private void readShotCoordinates(int numShotsRequired) {
    this.askForShotCoordinates(numShotsRequired);
    while (true) {
      try {
        for (int i = 0; i < numShotsRequired; i++) {
          int inputtedX = this.reader.nextInt();
          int inputtedY = this.reader.nextInt();

          if (inputtedX < this.configModel.getBoardWidth()
              && inputtedY < this.configModel.getBoardHeight()) {
            Coord coord = new Coord(inputtedX, inputtedY);
            this.lastTakenShots.add(coord);
            this.reader.nextLine();
          } else {
            throw new IllegalArgumentException("Inputted coordinates must"
                + " be within the range of the board.");
          }
        }
        break;
      } catch (InputMismatchException | IllegalArgumentException e) {
        this.lastTakenShots.clear();
        String error =
            "--------------------------------------------------------------------------------"
                + System.lineSeparator()
                + "Uh Oh! You've entered invalid coordinates." + System.lineSeparator()
                + "Please enter " + numShotsRequired + " shots:"
                + System.lineSeparator()
                + "--------------------------------------------------------------------------------"
                + System.lineSeparator();

        this.view.writeToOutput(error);
        this.reader.nextLine();
      }

    }
  }

  /**
   * writes text prompt for asking the user for shot coordinates to the view
   */
  private void askForShotCoordinates(int numShotsRequired) {
    String s = "Please Enter " + numShotsRequired + " Shots:" + System.lineSeparator()
        + "------------------------------------------------------------------"
        + System.lineSeparator();
    this.view.writeToOutput(s);

  }

}
