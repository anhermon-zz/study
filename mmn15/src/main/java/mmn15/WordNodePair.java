package mmn15;

/**
 * Helper class representing a pair of {@link WordNode}, used for splitting a list of nodes to be later used for merge sort
 * @author Angel
 *
 */
public class WordNodePair {
	/**
	 * {@link WordNode} a and {@link WordNode} b
	 */
	private WordNode _wordNodeA, _wordNodeB;
	/**
	 * Constructor receiving 2 {@link WordNode}
	 * @param a
	 * @param b
	 */
	public WordNodePair (WordNode a, WordNode b) {
		this._wordNodeA = a;
		this._wordNodeB = b;
	}
	//Getters
	public WordNode getWordNodeA() {
		return _wordNodeA;
	}
	public WordNode getWordNodeB() {
		return _wordNodeB;
	}	
}
