/* Goal: Creates a SortingAlgorithm instance to use.
* CS 245 - Assignment 1
* Brizan
* 10/17/2018
* Mina Xu @jxu74
*/
public class SortingFactory {
/* Factory class made to construct object */

	public final String defaultSort = "MergeSort"; /* If improper name used, divert to this */
	public boolean useDefault = false; /* Do not use default unless necessary */


	public SortingFactory (boolean returnDefault) {
		useDefault = returnDefault;
	}

	public SortingAlgorithm getSortingAlgorithm(String algInput) throws Exception {
		String lowerAlg = algInput.toLowerCase();
		if(lowerAlg.contains("merge")) {
			return new MergeSort();
		}

		if(lowerAlg.contains("hybrid")) {
			return new HybridSort();
		}

		if(useDefault) {
			System.out.println("Invalid algorithm name was input - default to " + defaultSort);
			return getSortingAlgorithm(defaultSort);
		} else {
			throw new Exception ("Invalid algorithm name was input.");
		}
	}

}