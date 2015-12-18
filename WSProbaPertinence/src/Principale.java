public class Principale {

	static private String cheminResultat = "\\Fichiers\\Resultats";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pathResult = System.getProperty("user.dir") + cheminResultat;
		System.out.println("Le répertoire des résultat est: " + pathResult);
		ReadResults result = new ReadResults(pathResult);
		System.out.println("Le nombre de requettes est: " + result.getTotalResult());
	}
}
