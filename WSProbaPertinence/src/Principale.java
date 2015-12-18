public class Principale {

	static private String cheminResultat = "\\Fichiers\\Resultats";
	static private String cheminRelevanceSet = "\\Fichiers\\relevance_sets";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pathResult = System.getProperty("user.dir") + cheminResultat;
		String pathRelevanceSet = System.getProperty("user.dir") + cheminRelevanceSet;
		System.out.println("Le répertoire des résultat est: " + pathResult);
		ReadResultsRelevanceSet result = new ReadResultsRelevanceSet(pathResult, pathRelevanceSet);
		System.out.println("Le nombre de requettes est: " + result.getTotalResult());
	}
}
