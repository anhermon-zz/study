package mmn15;

public class WordNode {

	private String _word;
	private WordNode _next;
	private int _count;
	
	public WordNode(String word){
		this._word = word;
		this._count = 1;
	}
	
	public WordNode(String word, WordNode next){
		this._word = word;
		this._count = 1;
		this._next = next;
	}
	
	public WordNode(WordNode word, WordNode next){
		this._word = word.getWord();
		this._count = word.getCount();
		this._next = next;
	}
	///////////
	// Getters Setters
	////////////
	
	
	public String getWord() {
		return _word;
	}

	public void setWord(String _word) {
		this._word = _word;
	}

	public WordNode getNext() {
		return _next;
	}

	public void setNext(WordNode next) {
		this._next = next;
	}
	/**
	 * Checks if the next node is not empty
	 */
	public boolean hasNext(){
		return this._next != null;
	}
	/**
	 * Pushes a {@link WordNode} in between two existing {@link WordNode} instances
	 * @param node
	 */
	public void push(WordNode node) {
		if(this.hasNext())
			node.setNext(this.getNext());
		this.setNext(node);
	}	
	@Override
	public String toString() {
		return this._word + "("+ this._count +")";
	}


	public int getCount() {
		return _count;
	}
	public void setCount(int _count) {
		this._count = _count;
	}
}
