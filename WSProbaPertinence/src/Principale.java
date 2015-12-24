import java.util.ArrayList;
import java.util.List;

public class Principale {

	static private String cheminResultat = "\\Fichiers\\resultats";
	static private String cheminRelevanceSet = "\\Fichiers\\relevance_sets";
	static private String cheminService = "\\Fichiers\\services";
	static private int K = 3;

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();

		String pathResult = System.getProperty("user.dir") + cheminResultat;
		String pathRelevanceSet = System.getProperty("user.dir")
				+ cheminRelevanceSet;
		String pathService = System.getProperty("user.dir") + cheminService;

		System.out.println("Le répertoire des résultat est: " + pathResult);
		System.out.println("Le répertoire des relevantSet est: "
				+ pathRelevanceSet);
		System.out.println("Le répertoire des Servives est: " + pathService);

		ReadResults_And_RelevanceSet result = new ReadResults_And_RelevanceSet(
				pathResult, pathRelevanceSet, K);

		int nbRequete = result.getListRequests().size();
		System.out.println("Le nombre de requettes est: "
				+ result.getTotalResult());
		System.out.println("Vérification de la lecture des données:");
		System.out.println("Le nombre de requettes est: " + nbRequete);

		/***********************************************************************/
		for (int index = 0; index < nbRequete; index++) {
			System.out
					.println("***********************************************************************");
			Request req = result.getListRequests().get(index);
			int nbServicePertinant = req.getListRelevantSet().size();
			int nbService = req.getResultCos().size();
			int nbCluster = req.getPourcentageServicePerResultCos().size();
			System.out.println("Traitement de la requete : "
					+ req.getRequestName());
			System.out.println("Service pertinants :" + nbServicePertinant);
			System.out.println("Service Résultat est : " + nbService);
			System.out.println("Nombre de cluster : " + nbCluster);
			System.out.println("% des services pertinants par cluster :");

			System.out.println("---------------- Cosinus :");
			for (int i = 0; i < nbCluster; i++) {
				if (req.getPourcentageServicePerResultCos().get(i)
						.doubleValue() != 0.0)
					System.out.println("--> Cluster n°"
							+ (i + 1)
							+ " le pourcentage est :"
							+ req.getPourcentageServicePerResultCos().get(i)
									.doubleValue());
			}
			System.out.println("---------------- EJ :");
			for (int i = 0; i < nbCluster; i++) {
				if (req.getPourcentageServicePerResultEj().get(i).doubleValue() != 0.0)
					System.out.println("--> Cluster n°"
							+ (i + 1)
							+ " le pourcentage est :"
							+ req.getPourcentageServicePerResultEj().get(i)
									.toString());
			}
			System.out.println("---------------- JS :");
			for (int i = 0; i < nbCluster; i++) {
				if (req.getPourcentageServicePerResultJs().get(i).doubleValue() != 0.0)
					System.out.println("--> Cluster n°"
							+ (i + 1)
							+ " le pourcentage est :"
							+ req.getPourcentageServicePerResultJs().get(i)
									.doubleValue());
			}
			System.out.println("---------------- LI :");
			for (int i = 0; i < nbCluster; i++) {
				if (req.getPourcentageServicePerResultLi().get(i).doubleValue() != 0.0)
					System.out.println("--> Cluster n°"
							+ (i + 1)
							+ " le pourcentage est :"
							+ req.getPourcentageServicePerResultLi().get(i)
									.doubleValue());
			}
			System.out.println("---------------- Log :");
			for (int i = 0; i < nbCluster; i++) {
				if (req.getPourcentageServicePerResultLog().get(i)
						.doubleValue() != 0.0)
					System.out.println("--> Cluster n°"
							+ (i + 1)
							+ " le pourcentage est :"
							+ req.getPourcentageServicePerResultLog().get(i)
									.doubleValue());
			}
		}

		long t2 = System.currentTimeMillis();
		System.out.println("temps d'execution :" + (t2 - t1));
	}
}
