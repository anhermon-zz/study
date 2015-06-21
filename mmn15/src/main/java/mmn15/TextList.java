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
		for(String word : text.split(" "))
			this.addToData(word);
		this.first = mergeSort(this.first);
	}
	/**
	 * Initializes a {@link TextList} with a single {@link WordNode}
	 * @param node
	 */
	public TextList(WordNode node){
		this.first = node;
	}
	/**
	 * 
	 * @param node
	 * @return
	 */
	private static WordNode mergeSort(WordNode node) {
			if(node == null || node.getNext() == null) return node;
			WordNodePair pair = split(node);
			return merge(mergeSort(pair.getWordNodeB()),(mergeSort(pair.getWordNodeA())));
	}
	/**
	 * Add new word to {@link TextList}, words are added in alphabetical order, this reduces the avarage Computational complexity, as we can conclude that a word is missing from the list
	 * as soon as we reach a word of higher alphabetical order rather then iterating over all of the elements
	 * Computational complexity - O(n) in the worst case scenario (new word starting with z) we go over all of the elements in the list.
	 * @param word - word to add
	 */
	public void addToData(String word){
		WordNode node = new WordNode(word);
		if(this.first == null){
			this.first = node;
			return;
		}
		node.setNext(this.first);
		this.first = node;
	}
	/**
	 * Word Count
	 */
	public int howManyWords (){
		int sum = 0;
		WordNode currentNode = first;
		while(currentNode != null){
			sum += currentNode.get_count();
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
			currentNode.getNext();
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
			if(currentNode.get_count() > max){
				max = currentNode.get_count();
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
			if(currentNode.getWord().charAt(0) == letter)
				count++;
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
		if (node.getNext() == null) return mostFrequentStartingLetter(lettersCount, 'a', 'a', 0);
		lettersCount[node.getWord().charAt(0) - ASCII_ALPHABET_START_INDEX] += node.get_count();
		return mostFrequentStartingLetter(lettersCount, node.getNext());
	}
	/**
	 * recursion end, gets a finilized letter table and retuns the most frequent letter
	 * @param lettersCount
	 * @return
	 */
	private char mostFrequentStartingLetter(int[] lettersCount, char currentChar, char mostFrequent, int count) {
		int index = currentChar - ASCII_ALPHABET_START_INDEX;
		if(lettersCount[index] > count){
			mostFrequent = currentChar;
			count = lettersCount[index];
		}
		currentChar = (char) (currentChar + 1);
		if(index == lettersCount.length - 1) return mostFrequent;
		return mostFrequentStartingLetter(lettersCount,currentChar, mostFrequent, count);
		
	}
//	private char mostFrequentStartingLetter(int[] lettersCount) {
//		WordNode currentNode = first;
//		while(currentNode != null){
//			lettersCount[charIndexOf(currentNode.getWord().charAt(0))] += currentNode.get_count();
//			currentNode = currentNode.getNext();
//		}
//		char mostFrequentChar = 'a';
//		int max = 0;
//		for (int i = 0; i < lettersCount.length; i++) {
//			if(lettersCount[i] > max){
//				max = lettersCount[i];
//				mostFrequentChar = indexCharOf(i);
//			}
//		}
//		return mostFrequentChar;
//}
	@Override
	public String toString() {
		String out = "";
		WordNode current = this.first;
		while(current != null){
			if(out.length() > 0)
				out += "\n";
			out = out.concat(current.getWord() + "\t" + current.get_count());
			current = current.getNext();
		}
		return out;
	}
	private static WordNode merge (WordNode left, WordNode right) {
		WordNode out;
		if(right == null)
			return left;		
		if(left.getWord().compareTo(right.getWord()) < 0)
			if(left.getNext() == null)
				return new WordNode(left.getWord(), right);
			else
				return new WordNode(left.getWord(), merge(left.getNext(), right));
		else if(left.getWord().compareTo(right.getWord()) == 0){
			if(left.getNext() == null){
				out = new WordNode(left.getWord(), right.getNext());
				out.addWord();
			}else{
				out = new WordNode(left.getWord(), merge(left.getNext(),right.getNext()));
				out.addWord();
			}
			return out;
		}
		else
			return new WordNode(right.getWord(), merge(left,right.getNext()));
	}
	/**
	 * 
	 * @return
	 */
	private static WordNodePair split(WordNode node) {
		if(node.getNext() == null)
			return new WordNodePair(node, null);
		WordNodePair pair = split(node.getNext());
		return new WordNodePair(new WordNode(node.getWord(),pair.getWordNodeB()),pair.getWordNodeA()); 
	}
	public static void main(String[] args) {
		TextList tl = new TextList("anything you can do i can do better");
		System.out.println(tl);
		//System.out.println(tl.mostFrequentStartingLetter());
		
	}
}
