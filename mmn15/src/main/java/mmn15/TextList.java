package mmn15;

public class TextList {
	private static final int ASCII_ALPHABET_START_INDEX = 'a';
	private WordNode first;
	/**
	 * Initializes empty {@link TextList}
	 */
	public TextList(){}
	/**
	 * Initializes new {@link TextList} from a String containing words seperated by spaces, each added node is added to the start of the list,
	 * After the list is formed, begins mergesort
	 * @param text
	 */
	public TextList(String text){
        while (text != null && text.length() > 0) {
               int index = text.indexOf(' ');
               if (index == -1) {
                     this.addAsHead(text); // last word
                     break;
               }
               String word = text.substring(0 , index);
               this.addAsHead(word);
               text = text.substring(++index, text.length());
        }
        this.first = mergeSort(this.first);
 }

	/**
	 * Recursively split and merge using divide an conquer strategy, computetional complexity is O(n log n)
	 * @param node {@link WordNode}
	 * @return merged {@link WordNode}
	 */
	private static WordNode mergeSort(WordNode node) {
			if(node == null || node.getNext() == null) return node;
			WordNodePair pair = split(node);
			return merge(mergeSort(pair.getWordNodeB()),(mergeSort(pair.getWordNodeA())));
	}
	/**
	 * Add value to head of list
	 * @param word
	 */
	private void addAsHead(String word){
		if(word == null || word.length() == 0 || word.equals(" ")) return;
		WordNode node = new WordNode(word);
		node.setNext(this.first);
		this.first = node;
	}
	/**
	 * Add new word to {@link TextList}, words are added in alphabetical order,
	 * Computational complexity - O(n) in the worst case scenario we go over all of the elements in the list.
	 * @param word - word to add
	 */
	public void addToData(String word){
		if(word == null || word.length() == 0 || word.equals(" ")) return;
		WordNode node = new WordNode(word);
		if(this.first == null){//if list is empty set as first
			this.first = node;
			return;
		}
		WordNode current = this.first;
		WordNode prev = null;
		while(current != null){
			if(current.getWord().compareTo(word) > 0){//set before first word with lower lexicographic value
				node.setNext(current);
				if(prev == null) this.first = node;
				else prev.setNext(node);
				return;
			}
			if(current.getWord().equals(word)){//if word exists in the list add to its count
				current.setCount(current.getCount() + 1);
				return;
			}
			prev = current;
			current = current.getNext();
		}
		prev.setNext(node);//add to the end of the list
		
	}
	/**
	 * Word Count
	 */
	public int howManyWords (){
		int sum = 0;
		WordNode currentNode = first;
		while(currentNode != null){
			sum += currentNode.getCount();
			currentNode = currentNode.getNext();
		}
		return sum;
	}
	/**
	 * Unique word count
	 */
	public int howManyDifferentWords (){
		int count = 0;
		WordNode currentNode = first;
		while(currentNode != null){
			count += 1;
			currentNode = currentNode.getNext();
		}
		return count;
	}
	/**
	 * Most frequent word
	 */
	public String mostFrequentWord (){
		int max = 0;
		String out = "";
		WordNode currentNode = first;
		while(currentNode != null){
			if(currentNode.getCount() > max){
				max = currentNode.getCount();
				out = currentNode.getWord();
			}
			currentNode = currentNode.getNext();
		}
		return out;
	}
	/**
	 * Count of words starting with a given letter
	 * @param letter
	 * TODO:recursion
	 */
	public int howManyStarting (char letter) {
		int count = 0;
		WordNode currentNode = first;
		while(currentNode != null){
			if(currentNode.getWord() != null && currentNode.getWord().length() > 0 && currentNode.getWord().charAt(0) == letter)
				count += currentNode.getCount();
			currentNode = currentNode.getNext();
		}
		return count;

	}
	/**
	 * Finds most frequent starting letter (initiates recursion)
	 * @return
	 */
	public char mostFrequentStartingLetter (){
		int[] lettersCount = new int[26];
		return mostFrequentStartingLetter(lettersCount, this.first);
	}
	/**
	 * recursion body
	 * @param lettersCount
	 * @param node
	 * @return
	 */
	private char mostFrequentStartingLetter(int[] lettersCount, WordNode node) {
		if (node == null) return mostFrequentStartingLetter(lettersCount, 'a', ' ', 0);
		lettersCount[node.getWord().charAt(0) - ASCII_ALPHABET_START_INDEX] += node.getCount();
		return mostFrequentStartingLetter(lettersCount, node.getNext());
	}
	/**
	 * recursion end, gets a finalized letter table and retuns the most frequent letter
	 * @param lettersCount
	 * @return
	 */
	private char mostFrequentStartingLetter(int[] lettersCount, char currentChar, char mostFrequent, int count) {
		//System.out.println("mostFrequentStartingLetter>" + currentChar + " mostFrequent>" + mostFrequent);
		int index = currentChar - ASCII_ALPHABET_START_INDEX;
		if(lettersCount[index] > count){
			mostFrequent = currentChar;
			count = lettersCount[index];
		}
		currentChar = (char) (currentChar + 1);
		if(index == lettersCount.length - 1) return mostFrequent;
		return mostFrequentStartingLetter(lettersCount,currentChar, mostFrequent, count);
		
	}
	@Override
	public String toString() {
		String out = "";
		WordNode current = this.first;
		while(current != null){
			if(out.length() > 0)
				out += "\n";
			out = out.concat(current.getWord() + "\t" + current.getCount());
			current = current.getNext();
		}
		return out;
	}
	/**
	 * merge 2 {@link WordNode} into a aingle sorted {@link WordNode}
	 * @param left {@link WordNode}
	 * @param right {@link WordNode}
	 * @return merged {@link WordNode}
	 */
	private static WordNode merge (WordNode left, WordNode right) {
		WordNode out;
		if(right == null)
			return left;		
		if(left.getWord().compareTo(right.getWord()) < 0)
			if(left.getNext() == null)
				return new WordNode(left , right);
			else
				return new WordNode(left , merge(left.getNext(), right));
		else if(left.getWord().compareTo(right.getWord()) == 0){
			if(left.getNext() == null){
				out = new WordNode(left , right.getNext());
				out.setCount(left.getCount() + right.getCount());
			}else{
				out = new WordNode(left , merge(left.getNext(),right.getNext()));
				out.setCount(left.getCount() + right.getCount());
			}
			return out;
		}
		else
			return new WordNode(right, merge(left,right.getNext()));
	}
	/**
	 * Recursively split a list of {@link WordNode} to 2 sub lists
	 * @return {@link WordNodePair}
	 */
	private static WordNodePair split(WordNode node) {
		if(node.getNext() == null)
			return new WordNodePair(node, null);
		WordNodePair pair = split(node.getNext());
		return new WordNodePair(new WordNode(node.getWord(),pair.getWordNodeB()),pair.getWordNodeA()); 
	}
}
