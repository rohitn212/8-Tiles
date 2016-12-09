package GUI;

// Helper class that stores the position of the row and column of a tile in a grid

public class Coordinate {
    protected int row;
    protected int col;

    protected boolean compareCoordinates(Coordinate otherCoordinate)    {
        return (this.row == otherCoordinate.row && this.col == otherCoordinate.col);
    }

    protected Coordinate subtractCoordinates(Coordinate otherCoordinate)  {
        Coordinate newCoord = new Coordinate();
        newCoord.row = this.row - otherCoordinate.row;
        newCoord.col = this.col - otherCoordinate.col;
        return newCoord;
    }
}
