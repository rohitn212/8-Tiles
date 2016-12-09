package GUI;

/**
    Class that stores the current board state and it's parent 'Node' from which it was formed.
 */

import java.util.HashMap;

public class Node {
    private Board boardState;
    private Node parent;

    public Node(Board board, Node parent) {
        this.boardState = board;
        this.parent = parent;
    }

    // setters and getters for private class variables

    public void setCurrentBoardState(Board board)	{
        this.boardState = board;
    }

    public Board getCurrentBoardState()	{
        return this.boardState;
    }

    public void setParentNode(Node parentNode)	{
        this.parent = parentNode;
    }

    public Node getParentNode()	{
        return this.parent;
    }

    /**
	 * @return counter the number of moves it took to reach the final node
	 */
    protected int findNumberOfMoves()	{
        int counter = 0;
        Node tempNode = this;
        while (tempNode != null)	{
            counter++;
            tempNode = tempNode.getParentNode();
        }
        return counter;
    }

    /**
        For each possible move for the current board, creates a node and adds to the hash map
        if the node doesn't exist in the hash map already.
        @param solPath instance of the SolutionPath containing the priority queue
        @param hMap hash map containing all the moves made so far
    */
    protected void addChildrenNodesToHashMap(SolutionPath solPath, HashMap<String, Node> hMap) {
        int numChildren = this.getCurrentBoardState().calcNumPossibleMoves();
        int[] possibleMoves = this.getCurrentBoardState().nextPossibleMoves();
        for (int i=0; i<numChildren; i++)	{
            Board child = new Board(this.getCurrentBoardState());
            child.swapPieceInBoard(possibleMoves[i]);
            if (!hMap.containsKey(child.boardToString()))	{
                Node temp = new Node(child, this);
                hMap.put(child.boardToString(), temp);
                solPath.pq.add(temp);
            }
        }
    }

}
