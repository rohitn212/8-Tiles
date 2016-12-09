package GUI;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;

// Class that contains the priority queue and is in charge of finding a solution to a board

public class SolutionPath {

    protected PriorityQueue<Node> pq = new PriorityQueue<Node>(10, new Comparator<Node> () {
        public int compare(Node node1, Node node2)	{
            if (node1.getCurrentBoardState().calcHeuristicValue() < node2.getCurrentBoardState().calcHeuristicValue())	{
                return -1;
            }
            else if(node1.getCurrentBoardState().calcHeuristicValue() > node2.getCurrentBoardState().calcHeuristicValue()) {
                return 1;
            }
            return 0;
        }
    });

    /**
	 * Populates the priority queue with possible moves and finds either a solution or the best possible board
	 * for the current board.
	 * @param initialBoardState the state of the board when the user wants the program to solve the board.
	 */
    protected Node solveBoard(Board initialBoardState)	{
        HashMap<String, Node> hMap = new HashMap<String, Node>();
        Node firstNode = new Node(initialBoardState, null);
        this.pq.add(firstNode);
        hMap.put(initialBoardState.boardToString(), firstNode);
        Node bestNode = new Node(initialBoardState, null);
        while (!this.pq.isEmpty())	{
            Node currNode = this.pq.remove();
            if (bestNode.getCurrentBoardState().calcHeuristicValue() >
                    currNode.getCurrentBoardState().calcHeuristicValue()) {
                bestNode = currNode;
            }
            if (currNode.getCurrentBoardState().calcHeuristicValue() == 0)	{
                break;
            }
            currNode.addChildrenNodesToHashMap(this, hMap);
        }
        return bestNode;
    }
}
