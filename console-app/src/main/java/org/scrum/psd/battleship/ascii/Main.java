package org.scrum.psd.battleship.ascii;

import jdk.internal.net.http.common.Pair;
import org.scrum.psd.battleship.ascii.sound.SoundEffectPlayer;
import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.GridGenerator;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.*;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

import sun.java2d.pipe.ShapeDrawPipe;

public class Main {
    private static List<Ship> myFleet;
    private static List<Ship> enemyFleet;
    private static List<Position> enemyUntriedPositions;

    private static List<String> myShipsSunken;
    private static List<String> enemyShipsSunken;

    private static Position lastEnemyHit = null; 

    private static final Telemetry telemetry = new Telemetry();

    public static void main(String[] args) {
        telemetry.trackEvent("ApplicationStarted", "Technology", "Java");
        System.out.println(colorize("                                     |__", MAGENTA_TEXT()));
        System.out.println(colorize("                                     |\\/", MAGENTA_TEXT()));
        System.out.println(colorize("                                     ---", MAGENTA_TEXT()));
        System.out.println(colorize("                                     / | [", MAGENTA_TEXT()));
        System.out.println(colorize("                              !      | |||", MAGENTA_TEXT()));
        System.out.println(colorize("                            _/|     _/|-++'", MAGENTA_TEXT()));
        System.out.println(colorize("                        +  +--|    |--|--|_ |-", MAGENTA_TEXT()));
        System.out.println(colorize("                     { /|__|  |/\\__|  |--- |||__/", MAGENTA_TEXT()));
        System.out.println(colorize("                    +---------------___[}-_===_.'____                 /\\", MAGENTA_TEXT()));
        System.out.println(colorize("                ____`-' ||___-{]_| _[}-  |     |_[___\\==--            \\/   _", MAGENTA_TEXT()));
        System.out.println(colorize(" __..._____--==/___]_|__|_____________________________[___\\==--____,------' .7", MAGENTA_TEXT()));
        System.out.println(colorize("|                        Welcome to Battleship                         BB-61/", MAGENTA_TEXT()));
        System.out.println(colorize(" \\_________________________________________________________________________|", MAGENTA_TEXT()));
        System.out.println("");

        InitializeGame();

        StartGame();
    }

    private static void StartGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\033[2J\033[;H");
        System.out.println("                  __");
        System.out.println("                 /  \\");
        System.out.println("           .-.  |    |");
        System.out.println("   *    _.-'  \\  \\__/");
        System.out.println("    \\.-'       \\");
        System.out.println("   /          _/");
        System.out.println("  |      _  /\" \"");
        System.out.println("  |     /_\'");
        System.out.println("   \\    \\_/");
        System.out.println("    \" \"\" \"\" \"\" \"");

        do {
            if(enemyFleet.size() == enemyShipsSunken.size()) {
                System.out.println(colorize("You are the winner!", BLUE_TEXT()));
                break;
            } else if (myFleet.size() == myShipsSunken.size()) {
                System.out.println(colorize("You lost!", YELLOW_TEXT()));
                break;
            }

            System.out.println("");
            System.out.println("Player, it's your turn");
            System.out.println("Enter coordinates for your shot :");
            Position position = parsePosition(scanner.next());
            Ship checkHitResult = GameController.checkIsHit(enemyFleet, position);
            boolean isHit = !(checkHitResult==null);
            boolean sunken = false;
            if (isHit) {
                beep();

                sunken = checkHitResult.isHit(position);
                lastEnemyHit = position;

                System.out.println(colorize("                \\         .  ./", BLUE_TEXT()));
                System.out.println(colorize("              \\      .:\" \";'.:..\" \"   /", BLUE_TEXT()));
                System.out.println(colorize("                  (M^^.^~~:.'\" \").", BLUE_TEXT()));
                System.out.println(colorize("            -   (/  .    . . \\ \\)  -", BLUE_TEXT()));
                System.out.println(colorize("               ((| :. ~ ^  :. .|))", BLUE_TEXT()));
                System.out.println(colorize("            -   (\\- |  \\ /  |  /)  -", BLUE_TEXT()));
                System.out.println(colorize("                 -\\  \\     /  /-", BLUE_TEXT()));
                System.out.println(colorize("                   \\  \\   /  /", BLUE_TEXT()));
                if (!sunken) {
                    SoundEffectPlayer.playHitSound();
                }
            } else {
                SoundEffectPlayer.playMissSound();
            }

            String hit = String.format("Yeah ! Nice hit ! ");



            System.out.println(isHit ? colorize(hit, BLUE_TEXT()) : colorize("Miss", YELLOW_TEXT() ));
            if(sunken) {
                SoundEffectPlayer.playSinkSound();
                System.out.printf("You have sunken a %s\n", checkHitResult.getName());
                enemyShipsSunken.add(checkHitResult.getName());
            }

            telemetry.trackEvent("Player_ShootPosition", "Position", position.toString(), "IsHit", Boolean.valueOf(isHit).toString());


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // Handle the exception if the sleep is interrupted
                e.printStackTrace();
            }

            
            position = enemyChoosePosition();

            checkHitResult = GameController.checkIsHit(myFleet, position);
            isHit = !(checkHitResult==null);

            hit = String.format("hit you!");

            System.out.println("");
            System.out.println(String.format("Computer shoot in %s%s and %s", position.getColumn(), position.getRow(), isHit ? hit : "miss"));
            telemetry.trackEvent("Computer_ShootPosition", "Position", position.toString(), "IsHit", Boolean.valueOf(isHit).toString());
            lastEnemyHit = null;

            if (isHit) {
                beep();

                sunken = checkHitResult.isHit(position);
                lastEnemyHit = position;

                System.out.println(colorize("                \\         .  ./", YELLOW_TEXT()));
                System.out.println(colorize("              \\      .:\" \";'.:..\" \"   /", YELLOW_TEXT()));
                System.out.println(colorize("                  (M^^.^~~:.'\" \").", YELLOW_TEXT()));
                System.out.println(colorize("            -   (/  .    . . \\ \\)  -", YELLOW_TEXT()));
                System.out.println(colorize("               ((| :. ~ ^  :. .|))", YELLOW_TEXT()));
                System.out.println(colorize("            -   (\\- |  \\ /  |  /)  -", YELLOW_TEXT()));
                System.out.println(colorize("                 -\\  \\     /  /-", YELLOW_TEXT()));
                System.out.println(colorize("                   \\  \\   /  /", YELLOW_TEXT()));


                if(sunken) {
                    SoundEffectPlayer.playSinkSound();
                    System.out.printf("The enemey have sunken a %s\n", checkHitResult.getName());
                    myShipsSunken.add(checkHitResult.getName());
                }
                else{
                    SoundEffectPlayer.playHitSound();
                }

            }else {
                SoundEffectPlayer.playMissSound();
            }

            System.out.println("--------------------------------");
        } while (true);
    }

    private static void beep() {
        System.out.print("\007");
    }

    protected static Position parsePosition(String input) {
        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));
        return new Position(letter, number);
    }

    private static Position getRandomPosition() {
        int rows = 8;
        int lines = 8;
        Random random = new Random();
        Letter letter = Letter.values()[random.nextInt(lines)];
        int number = random.nextInt(rows);
        Position position = new Position(letter, number);
        return position;
    }

    private static void InitializeGame() {

        InitializeMyFleet();

        InitializeEnemyFleet();

        enemyShipsSunken = new ArrayList();
        myShipsSunken = new ArrayList();

        enemyUntriedPositions = GridGenerator.generateGrid();
    }

    private static void InitializeMyFleet() {
        Scanner scanner = new Scanner(System.in);
        myFleet = GameController.initializeShips();

        System.out.println("Please position your fleet (Game board has size from A to H and 1 to 8) :");

        for (Ship ship : myFleet) {
            System.out.println("");
            System.out.println(String.format("Please enter the positions for the %s (size: %s)", ship.getName(), ship.getSize()));
            for (int i = 1; i <= ship.getSize(); i++) {
                System.out.println(String.format("Enter position %s of %s (i.e A3):", i, ship.getSize()));

                String positionInput = scanner.next();
                ship.addPosition(positionInput);
                telemetry.trackEvent("Player_PlaceShipPosition", "Position", positionInput, "Ship", ship.getName(), "PositionInShip", Integer.valueOf(i).toString());
            }
        }

    }

    private static void InitializeEnemyFleet() {
        enemyFleet = GameController.initializeShips();

        List<Position> positions = new ArrayList<>();

        positions.add(new Position(Letter.B, 4));
        positions.add(new Position(Letter.B, 5));
        positions.add(new Position(Letter.B, 6));
        positions.add(new Position(Letter.B, 7));
        positions.add(new Position(Letter.B, 8));

        enemyFleet.get(0).setPositions(positions);
        positions = new ArrayList<>();

        positions.add(new Position(Letter.E, 6));
        positions.add(new Position(Letter.E, 7));
        positions.add(new Position(Letter.E, 8));
        positions.add(new Position(Letter.E, 9));

        enemyFleet.get(1).setPositions(positions);
        positions = new ArrayList<>();

        positions.add(new Position(Letter.A, 3));
        positions.add(new Position(Letter.B, 3));
        positions.add(new Position(Letter.C, 3));

        enemyFleet.get(2).setPositions(positions);
        positions = new ArrayList<>();

        positions.add(new Position(Letter.F, 8));
        positions.add(new Position(Letter.G, 8));
        positions.add(new Position(Letter.H, 8));

        enemyFleet.get(3).setPositions(positions);
        positions = new ArrayList<>();

        positions.add(new Position(Letter.C, 5));
        positions.add(new Position(Letter.C, 6));

        enemyFleet.get(4).setPositions(positions);
    }

    private static Position enemyChoosePosition() {
        Random random = new Random();

        // Get a random index from the list
        int randomIndex = random.nextInt(enemyUntriedPositions.size());

        // Remove the element at the random index and return it
        return enemyUntriedPositions.remove(randomIndex);
    }

    public static List<Position> getEnemyUntriedPositions() {
        return new ArrayList<>(enemyUntriedPositions);
    }
}


