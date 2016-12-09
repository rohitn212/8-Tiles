package GUI;
import java.util.Random;
import java.lang.Math;

// Class that stores the 2x2 grid containing all the pieces

public class Board {

    private int[][] currentBoard = new int[Constants.boardRowNo][Constants.boardColumnNo];
    private boolean[] visitedArray = new boolean[Constants.maxBoardSize];
    private int possibleMoveCounter;

    public Board()	{
    }

    public Board(Board boardCopy)	{
        for (int i=0; i<Constants.boardRowNo; i++)	{
            for (int j=0; j<Constants.boardColumnNo; j++)	{
                this.currentBoard[i][j] = boardCopy.currentBoard[i][j];
            }
        }
    }

    public void setCurrentBoard(int[][] board)	{
        this.currentBoard = board;
    }

    public int[][] getCurrentBoard()	{
        return this.currentBoard;
    }

    /***
     * Generates a random value from 1-9
     * @param randomGenerator instance of random class passed in from parent function
     * @return randomValue a random value from 1-9
     */
    private int getRandomValue(Random randomGenerator)	{
        int randomValue;
        while (true)	{
            randomValue = randomGenerator.nextInt(9) + 1;
            if (!this.visitedArray[randomValue])	{
                this.visitedArray[randomValue] = true;
                break;
            }
        }
        return randomValue;
    }

    /**
     * Sets up a board with random unique values from 1-8
     */
    protected void initializeBoardRandom()	{
        Random randomGenerator = new Random();
        randomGenerator.setSeed(System.currentTimeMillis());
        for (int i=0; i<Constants.boardRowNo; i++)	{
            for (int j=0; j<Constants.boardColumnNo; j++)	{
                int randomValue = getRandomValue(randomGenerator);
                this.currentBoard[i][j] = randomValue;
            }
        }
        System.out.println();
    }

    /**
     * @return Coordinate of the empty tile in the board
     */
    private Coordinate findEmptyTileCoordinate()	{
        return findBoardValueCoordinate(9);
    }

    /**
     * Finds the coordinate in the board for a given value
     * @param value the value whose coordinate in the board needs to be found
     * @return Coordinate the position of the tile containing 'value' in the board
     */
    private Coordinate findBoardValueCoordinate(int value)	{
        Coordinate boardValueCoordinate = new Coordinate();
        for (int i=0; i<Constants.boardRowNo; i++)	{
            for (int j=0; j<Constants.boardColumnNo; j++)	{
                if (this.currentBoard[i][j] == value)	{
                    boardValueCoordinate.row = i;
                    boardValueCoordinate.col = j;
                    break;
                }
            }
        }
        return boardValueCoordinate;
    }

    /**
     * Checks if the piece is next to the empty tile
     * @param piece the next piece the user wants to move
     * @return true or false depending on whether the piece can move or not
     */
    public boolean checkIfPieceCanMove(int piece)	{
        Coordinate differenceCoordinate = findBoardValueCoordinate(piece).subtractCoordinates(findEmptyTileCoordinate());
        int absXCoordinateDifference = Math.abs(differenceCoordinate.row);
        int absYCoordinateDifference = Math.abs(differenceCoordinate.col);
        return absXCoordinateDifference + absYCoordinateDifference <= 1;
    }

    /**
     * Swaps the blank space tile in the board with the tile containing the given piece and vice versa
     * @param piece the piece the user wants to move
     */
    protected void swapPieceInBoard(int piece)	{
        Coordinate pieceCoordinate = findBoardValueCoordinate(piece);
        Coordinate emptyTileCoordinate = findEmptyTileCoordinate();
        this.currentBoard[emptyTileCoordinate.row][emptyTileCoordinate.col] = piece;
        this.currentBoard[pieceCoordinate.row][pieceCoordinate.col] = 9;
    }

    /**
     * Finds the total number of moves possible for the current board
     * @return numPossibleMoves the number of possible moves for the current board
     */
    protected int calcNumPossibleMoves()	{
        int numPossibleMoves;
        Coordinate emptyTileCoordinate = findEmptyTileCoordinate();
        int sumCoordinatesEmptyTile = emptyTileCoordinate.row + emptyTileCoordinate.col;
        if (emptyTileCoordinate.row == 1 && emptyTileCoordinate.col == 1)	{
            numPossibleMoves = 4;
        }
        else if(sumCoordinatesEmptyTile % 2 == 0)	{
            numPossibleMoves = 2;
        }
        else	{
            numPossibleMoves = 3;
        }
        return numPossibleMoves;
    }

    /**
     * Finds the possible vertical moves that can be made in the current board.
     * @param rowOffset an integer that determines the vertical position of the empty tile from the center
     * @param possibleMoves an integer array that contains the possibleMoves
     * @return possibleMoves an updated integer array containing the possible vertical moves
     */
    private int[] verticalPossibleMoves(int[] possibleMoves, int rowOffset)	{
        Coordinate emptyTileCoordinate = findEmptyTileCoordinate();
        if (rowOffset % 2 != 0)	{
            possibleMoves[this.possibleMoveCounter] =
                    this.currentBoard[emptyTileCoordinate.row+rowOffset][emptyTileCoordinate.col];
            this.possibleMoveCounter++;
        }
        else	{
            possibleMoves[this.possibleMoveCounter] =
                    this.currentBoard[emptyTileCoordinate.row+1][emptyTileCoordinate.col];
            this.possibleMoveCounter++;
            possibleMoves[this.possibleMoveCounter] =
                    this.currentBoard[emptyTileCoordinate.row-1][emptyTileCoordinate.col];
            this.possibleMoveCounter++;
        }
        return possibleMoves;
    }

    /**
     * Finds the possible vertical moves that can be made in the current board.
     * @param colOffset an integer that determines the horizontal position of the empty tile from the center
     * @param possibleMoves an integer array that contains the possibleMoves
     * @return possibleMoves an updated integer array containing the possible horizontal moves
     */
    private int[] horizontalPossibleMoves(int[] possibleMoves, int colOffset)	{
        Coordinate emptyTileCoordinate = findEmptyTileCoordinate();
        if (colOffset % 2 != 0)	{
            possibleMoves[this.possibleMoveCounter] =
                    this.currentBoard[emptyTileCoordinate.row][emptyTileCoordinate.col+colOffset];
            this.possibleMoveCounter++;
        }
        else	{
            possibleMoves[this.possibleMoveCounter] =
                    this.currentBoard[emptyTileCoordinate.row][emptyTileCoordinate.col+1];
            this.possibleMoveCounter++;
            possibleMoves[this.possibleMoveCounter] =
                    this.currentBoard[emptyTileCoordinate.row][emptyTileCoordinate.col-1];
            this.possibleMoveCounter++;
        }
        return possibleMoves;
    }

    /**
     * Calculates the next possible, both horizontal and vertical, moves for the current board
     * @return possibleMoves an integer array containing the possible moves for the current board
     */
    protected int[] nextPossibleMoves()	{
        int numPossibleMoves = calcNumPossibleMoves();
        int[] possibleMoves = new int[numPossibleMoves];
        this.possibleMoveCounter = 0;
        Coordinate emptyTileCoordinate = findEmptyTileCoordinate();
        int rowOffset = 1 - emptyTileCoordinate.row;
        int colOffset = 1 - emptyTileCoordinate.col;
        possibleMoves = horizontalPossibleMoves(possibleMoves, colOffset);
        possibleMoves = verticalPossibleMoves(possibleMoves, rowOffset);
        return possibleMoves;
    }

    /**
     * Calculates the heuristic value of the current board by finding the difference in position of a piece
     * in the current board and the final board
     * @return accumulator the heuristic value of a board
     */
    protected int calcHeuristicValue()	{
        int[] tempArray = new int[Constants.maxBoardSize];
        for (int i=1; i<Constants.maxBoardSize; i++)	{
            tempArray[i] = i;
        }
        int rowCoordinateDifference;
        int colCoordinateDifference;
        Coordinate boardValueCoordinate;
        int accumulator = 0;
        for (int i=0; i<Constants.boardRowNo; i++)	{
            for (int j=0; j<Constants.boardColumnNo; j++)	{
                boardValueCoordinate = findBoardValueCoordinate(tempArray[(3*i)+j+1]);
                rowCoordinateDifference = Math.abs(i - boardValueCoordinate.row);
                colCoordinateDifference = Math.abs(j - boardValueCoordinate.col);
                accumulator += rowCoordinateDifference + colCoordinateDifference;
            }
        }
        return accumulator;
    }

    /**
     * Converts the current board to a String that can be used as the key for the current board in a hash map
     * @return output the current board represented as a String
     */
    protected String boardToString()	{
        String output = " ";
        for(int i=0; i<Constants.boardRowNo; i++)	{
            for(int j=0; j<Constants.boardColumnNo; j++)	{
                output += String.valueOf(this.currentBoard[i][j]);
            }
        }
        return output;
    }

}
