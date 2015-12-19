import java.util.ArrayList;
import java.util.List;

public class Principale {

	static private String cheminResultat = "\\Fichiers\\resultats";
	static private String cheminRelevanceSet = "\\Fichiers\\relevance_sets";
	static private String cheminService = "\\Fichiers\\services";
	static private int K = 100;

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

		int k = K;
		ReadResults_And_RelevanceSet result = new ReadResults_And_RelevanceSet(
				pathResult, pathRelevanceSet, k);

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

			List<String> listSerRS = req.getListRelevantSet();
			System.out.println("nombre de services pertinants:"
					+ listSerRS.size());

			int interval = 1007 / K;

			List<Integer> listValK = new ArrayList<Integer>();
			for (int pos = 0; pos < K; pos++) {
				List<Service> listSerCos = req.getResultCos();
				int borneInf = interval * pos;
				int borneSup = interval * (pos + 1);
				int totalServicePertinantInInterval = 0;

				for (int j = borneInf; j < borneSup; j++) {
					String IDService = listSerCos.get(j).getIDService();
					IDService = IDService.substring(IDService.indexOf('*') + 1,
							IDService.length());
					if (listSerRS.contains(IDService))
						totalServicePertinantInInterval = totalServicePertinantInInterval + 1;
				}
				if (totalServicePertinantInInterval != 0)
					System.out.println("Nombre de service pertinant dans la "
							+ pos + " position est : "
							+ totalServicePertinantInInterval);
				listValK.add(totalServicePertinantInInterval);

				// if (totalServicePertinantInInterval == listSerRS.size())
			}
			req.setNbservicePerResultCos(listValK);
		}
	}
}
