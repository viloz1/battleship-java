package org.scrum.psd.battleship.ascii;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.scrum.psd.battleship.controller.GameController;
import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;
import org.scrum.psd.battleship.controller.dto.Ship;

import java.util.ArrayList;
import java.util.List;

@Execution(ExecutionMode.CONCURRENT)
public class MainTest {

    @Test
    public void testParsePosition() {
        Position actual = Main.parsePosition("A1");
        Position expected = new Position(Letter.A, 1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testParsePosition2() {
        //given
        Position expected = new Position(Letter.B, 1);
        //when
        Position actual = Main.parsePosition("B1");
        //then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testHitName() {

        List<Ship> enemyFleet = GameController.initializeShips();

        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 4));

        Ship result = GameController.checkIsHit(enemyFleet, new Position(Letter.B, 4));

        Assertions.assertEquals(result.getName(), "Aircraft Carrier");
    }

    @Test
    public void testIsSunken() {

        List<Ship> enemyFleet = GameController.initializeShips();

        List<Position> positions = new ArrayList<>();
        positions.add(new Position(Letter.B, 4));
        positions.add(new Position(Letter.B, 5));

        enemyFleet.get(0).setPositions(positions);

        Ship result = GameController.checkIsHit(enemyFleet, new Position(Letter.B, 4));
        result.isHit(new Position(Letter.B, 4));

        Assertions.assertFalse(result.isSunken());

        result = GameController.checkIsHit(enemyFleet, new Position(Letter.B, 5));
        result.isHit(new Position(Letter.B, 5));

        Assertions.assertTrue(result.isSunken());
    }

    @Test
    public void positionsIsSet() {
        List<Ship> enemyFleet = GameController.initializeShips();

        enemyFleet.get(0).getPositions().add(new Position(Letter.B, 4));

        Assertions.assertTrue(enemyFleet.get(0).getPositions().contains(new Position(Letter.B, 4)));
    }

    @Test
    public void remainingPositionsIsSet() {
        List<Ship> enemyFleet = GameController.initializeShips();

        List<Position> positions = new ArrayList<>();
        positions.add(new Position(Letter.B, 4));

        enemyFleet.get(0).setPositions(positions);

        Assertions.assertTrue(enemyFleet.get(0).getRemainingPositions().contains(new Position(Letter.B, 4)));
    }

}
