package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.animation.*;
import javafx.util.Duration;

// Class that connects the GUI and rest of the code

public class Controller {
    @FXML private Button setBoardButton;
    @FXML private Button solveButton;
    @FXML private Button exitButton;
    @FXML private Button boardButton1;
    @FXML private Button boardButton2;
    @FXML private Button boardButton3;
    @FXML private Button boardButton4;
    @FXML private Button boardButton5;
    @FXML private Button boardButton6;
    @FXML private Button boardButton7;
    @FXML private Button boardButton8;
    @FXML private Button boardButton9;
    private Button[][] gridButtonArray = new Button[Constants.boardRowNo][Constants.boardColumnNo];
    private boolean[][] gridButtonSet = new boolean[Constants.boardRowNo][Constants.boardColumnNo];
    private Board myBoard = new Board();
    private boolean setBoardFlag = false;
    private int gridCounter = 0;
    private PauseTransition[] pauseArr;
    private int pauseArrCounter = 0;

    @FXML public void initialize()  {
        initializeGridButtonArray();
        this.myBoard.initializeBoardRandom();
        int[][] currBoard = myBoard.getCurrentBoard();
        for(int i=0; i<Constants.boardColumnNo; i++)    {
            for(int j=0; j<Constants.boardRowNo; j++)   {
                if(currBoard[i][j] != 9) {
                    this.gridButtonArray[i][j].setText(Integer.toString(currBoard[i][j]));
                }
            }
        }
    }


    private void initializeGridButtonArray()    {
        this.gridButtonArray[0][0] = boardButton1;
        this.gridButtonArray[0][1] = boardButton2;
        this.gridButtonArray[0][2] = boardButton3;
        this.gridButtonArray[1][0] = boardButton4;
        this.gridButtonArray[1][1] = boardButton5;
        this.gridButtonArray[1][2] = boardButton6;
        this.gridButtonArray[2][0] = boardButton7;
        this.gridButtonArray[2][1] = boardButton8;
        this.gridButtonArray[2][2] = boardButton9;
    }

    public void setGridButtonArray(Button[][] gridButtonArray)    {
        this.gridButtonArray = gridButtonArray;
    }

    public Button[][] getGridButtonArray()  {
        return this.gridButtonArray;
    }

    @FXML private void exitOnClick()   {
        System.exit(0);
    }

    /**
       @return emptyButtonCoordinates A Coordinate object containing the position of the empty button
                in the grid of buttons.
    */
    private Coordinate getEmptyButtonCoordinate()  {
        Coordinate emptyButtonCoordinates = new Coordinate();
        for(int i=0; i<Constants.boardRowNo; i++)    {
            for(int j=0; j<Constants.boardColumnNo; j++)   {
                if(this.gridButtonArray[i][j].getText().equals(""))   {
                    emptyButtonCoordinates.row = i;
                    emptyButtonCoordinates.col = j;
                    break;
                }
            }
        }
        return emptyButtonCoordinates;
    }

    /**
       @return currentButtonCoordinate A Coordinate object containing the position of button, that was recently
                clicked, in the grid of buttons.
    */
    private Coordinate getCurrentButtonCoordinate(ActionEvent event)   {
        Coordinate currentButtonCoordinate = new Coordinate();
        for(int i=0; i<Constants.boardRowNo; i++)    {
            for(int j=0; j<Constants.boardColumnNo; j++)   {
                if(event.getSource().equals(this.gridButtonArray[i][j])) {
                    currentButtonCoordinate.row = i;
                    currentButtonCoordinate.col = j;
                    break;
                }
            }
        }
        return currentButtonCoordinate;
    }

    private Button getButtonFromGridAtCoordinate(Coordinate buttonCoordinate)  {
        return this.gridButtonArray[buttonCoordinate.row][buttonCoordinate.col];
    }

    private String getButtonTextFromGridCoordinate(Coordinate buttonCoordinate) {
        return getButtonFromGridAtCoordinate(buttonCoordinate).getText();
    }

    private void setButtonTextInGridAtCoordinate(Coordinate buttonCoordinate, String input)  {
        this.gridButtonArray[buttonCoordinate.row][buttonCoordinate.col].setText(input);
    }

    /**
     * Maps the given board state to the GUI
     * @param myBoard The board that we want our grid of buttons to look like
     */
    private void setBoardToGrid(Board myBoard)    {
        int[][] currBoardState = myBoard.getCurrentBoard();
        for(int i=0; i<Constants.boardRowNo; i++)   {
            for(int j=0; j<Constants.boardColumnNo; j++)    {
                if(currBoardState[i][j] == 9)
                    this.gridButtonArray[i][j].setText("");
                else
                    this.gridButtonArray[i][j].setText(Integer.toString(currBoardState[i][j]));
            }
        }
    }

    /**
     * Swaps the text fields of the button that was clicked with the empty button if
     * the clicked button is next to the empty button
     * @param event The action performed that resulted in this function being called
     */
    private void swapButtonText(ActionEvent event)   {
        Coordinate emptyButtonCoordinate = getEmptyButtonCoordinate();
        Coordinate currButtonCoordinate = getCurrentButtonCoordinate(event);
        if(!emptyButtonCoordinate.compareCoordinates(currButtonCoordinate))   {
            String currButtonText = getButtonTextFromGridCoordinate(currButtonCoordinate);
            int currButtonVal = Integer.parseInt(currButtonText);
            if(this.myBoard.checkIfPieceCanMove(currButtonVal))    {
                this.myBoard.swapPieceInBoard(currButtonVal);
                setButtonTextInGridAtCoordinate(emptyButtonCoordinate, currButtonText);
                setButtonTextInGridAtCoordinate(currButtonCoordinate, "");
            }
        }
    }

    private void updateGridCounter()    {
        this.gridCounter++;
        if(this.gridCounter >= 9)    {
            this.gridCounter = 0;
            this.setBoardFlag = false;
        }
    }

    /**
     * Takes care of setting the grid of buttons and the behind the scenes, board, to
     * exactly what the user manually clicks.
     * @param event The action performed that resulted in this function being called
     */
    private void setBoard(ActionEvent event) {
        int[][] tempBoard = this.myBoard.getCurrentBoard();
        Coordinate currButtonCoordinate = getCurrentButtonCoordinate(event);
        if(!this.gridButtonSet[currButtonCoordinate.row][currButtonCoordinate.col])   {
            if(this.gridCounter == 0)    {
                setButtonTextInGridAtCoordinate(currButtonCoordinate, "");
                tempBoard[currButtonCoordinate.row][currButtonCoordinate.col] = 9;
            }
            else    {
                setButtonTextInGridAtCoordinate(currButtonCoordinate, Integer.toString(this.gridCounter));
                tempBoard[currButtonCoordinate.row][currButtonCoordinate.col] = this.gridCounter;
            }
            this.myBoard.setCurrentBoard(tempBoard);
            this.gridButtonSet[currButtonCoordinate.row][currButtonCoordinate.col] = true;
            updateGridCounter();
        }
    }

    /**
     * Called when the user clicks a button in the grid
     * @param event The action performed that resulted in this function being called
     */
    @FXML private void handleGridButtonClick(ActionEvent event) {
        if(this.setBoardFlag) setBoard(event);
        else swapButtonText(event);
    }

    /**
     * Wipes the grid of buttons and the board clean.
     */
    private void resetBoardClean()  {
        int[][] tempBoard = myBoard.getCurrentBoard();
        for(int i=0; i<Constants.boardRowNo; i++)    {
            for(int j=0; j<Constants.boardColumnNo; j++)   {
                this.gridButtonArray[i][j].setText("");
                this.gridButtonSet[i][j] = false;
                tempBoard[i][j] = 0;
            }
        }
        this.myBoard.setCurrentBoard(tempBoard);
    }

    /**
     * Called when the user clicks the button 'Set Board'
     */
    @FXML private void handleSetBoardClick()  {
        resetBoardClean();
        this.setBoardFlag = true;
        this.gridCounter = 0;
    }

    /**
     * Adds a pause transition to an array of pause transition
     * @param currNode the current node in the recursive call of printSolution
     */
    private void addPauseTransition(Node currNode)    {
        this.pauseArr[pauseArrCounter] = new PauseTransition(Duration.millis(300));
        this.pauseArr[pauseArrCounter].setOnFinished(event ->
                setBoardToGrid(currNode.getCurrentBoardState())
        );
        this.pauseArrCounter++;
    }

    /**
     * Finds the solution path for the current board by recursively finding the first
     * node from the final node
     * @param currNode the node that is recursively called till it reaches it's last ancestor
     */
    private void findSolutionPath(Node currNode) {
        if (currNode == null) return;
        findSolutionPath(currNode.getParentNode());
        addPauseTransition(currNode);
    }

    /**
     * Called when the user clicks the button 'Set Board'.
     * If a solution is possible for the current board, shows the moves made to get to the solution
     * from the current board.
     * Else, it shows the best possible board.
     */
    @FXML private void handleSolveClick()   {
        SolutionPath solPath = new SolutionPath();
        final Node finalNode = solPath.solveBoard(this.myBoard);
        final int numMoves = finalNode.findNumberOfMoves();
        this.pauseArr = new PauseTransition[numMoves];
        findSolutionPath(finalNode);
        SequentialTransition seqTrans = new SequentialTransition(pauseArr);
        seqTrans.play();
    }
}