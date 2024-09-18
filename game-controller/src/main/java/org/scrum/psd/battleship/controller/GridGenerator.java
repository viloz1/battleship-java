package org.scrum.psd.battleship.controller;

import org.scrum.psd.battleship.controller.dto.Letter;
import org.scrum.psd.battleship.controller.dto.Position;

import java.util.ArrayList;
import java.util.List;

public class GridGenerator {

    public static List<Position> generateGrid() {
        List<Position> positions = new ArrayList<>();

        // Loop through all columns (letters A to H)
        for (Letter column : Letter.values()) {
            // Loop through all rows (1 to 8)
            for (int row = 1; row <= 8; row++) {
                positions.add(new Position(column, row));
            }
        }

        return positions;
    }
}