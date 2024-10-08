package org.scrum.psd.battleship.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private boolean isPlaced;
    private String name;
    private int size;
    private List<Position> positions;
    private Color color;
    private List<Position> remainingPositions;

    public Ship() {
        this.positions = new ArrayList<>();
        this.remainingPositions = new ArrayList<>();
    }

    public Ship(String name, int size) {
        this();

        this.name = name;
        this.size = size;
    }

    public Ship(String name, int size, List<Position> positions) {
        this(name, size);

        this.positions = positions;
        this.remainingPositions = positions;
    }

    public Ship(String name, int size, Color color) {
        this(name, size);

        this.color = color;
    }

    public void addPosition(String input) {
        if (positions == null) {
            positions = new ArrayList<>();
        }

        if (remainingPositions == null) {
            remainingPositions = new ArrayList<>();
        }

        Letter letter = Letter.valueOf(input.toUpperCase().substring(0, 1));
        int number = Integer.parseInt(input.substring(1));

        Position position = new Position(letter, number);

        positions.add(position);
        remainingPositions.add(position);
    }

    // TODO: property change listener implementieren

    public boolean isHit(Position position) {
        remainingPositions.remove(position);
        return remainingPositions.isEmpty();
    }

    public boolean isSunken() {
        return remainingPositions.isEmpty();
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public List<Position> getRemainingPositions() {
        return remainingPositions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
        this.remainingPositions = positions;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
