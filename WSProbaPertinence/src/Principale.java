import java.util.List;

public class Principale {

	static private String cheminResultat = "\\Fichiers\\resultats";
	static private String cheminRelevanceSet = "\\Fichiers\\relevance_sets";
	static private String cheminService = "\\Fichiers\\services";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pathResult = System.getProperty("user.dir") + cheminResultat;
		String pathRelevanceSet = System.getProperty("user.dir")
				+ cheminRelevanceSet;
		String pathService = System.getProperty("user.dir") + cheminService;

		System.out.println("Le répertoire des résultat est: " + pathResult);
		System.out.println("Le répertoire des relevantSet est: "
				+ pathRelevanceSet);
		System.out.println("Le répertoire des Servives est: " + pathService);

		ReadResultsRelevanceSet result = new ReadResultsRelevanceSet(
				pathResult, pathRelevanceSet);

		System.out.println("Le nombre de requettes est: "
				+ result.getTotalResult());

		
		
		System.out.println("Vérification de la lecture des données:");
		List<Request> list = result.getListRequests();
		int size = list.size();
		System.out.println("Le nombre de requettes est: " + size);
		for (int i = 0; i < size; i++) {
			System.out.println("*******************************");
			Request req = list.get(i);
			System.out.println("ID de la requete :" + req.getRequestID());
			System.out.println("nom de la requete :" + req.getRequestName());
			List<Service> listSerRS = req.getListRelevantSet();
			System.out.println("nombre de services pertinants:"
					+ listSerRS.size());
		}
	}
}
