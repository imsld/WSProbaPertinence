import java.util.ArrayList;
import java.util.List;

public class Principale {

	static private String cheminResultat = "\\Fichiers\\resultats";
	static private String cheminRelevanceSet = "\\Fichiers\\relevance_sets";
	static private String cheminService = "\\Fichiers\\services\\1.1";
	static private int K = 3;
	static int positionCluster = -1;

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
				pathResult, pathRelevanceSet, pathService, K);

		System.out.println("Vérification de la lecture des données:");
		int nbRequete = result.getListRequests().size();
		int nbService = result.getListServiceEntrepot().size();
		System.out.println("Le nombre de requettes est: " + nbRequete);
		System.out.println("Le nombre total de service est : " + nbService);

		Request requet;
		/***********************************************************************/
		for (int index = 0; index < nbRequete; index++) {
			System.out
					.println("***********************************************************************");
			requet = result.getListRequests().get(index);
			int nbServicePertinant = requet.getListRelevantSet().size();
			nbService = requet.getResultCos().size();
			int nbCluster = requet.getPourcentageServicePerResultCos().size();
			System.out.println("Traitement de la requete : "
					+ requet.getRequestName());
			System.out.println("Service pertinants :" + nbServicePertinant);
			System.out.println("Service Résultat est : " + nbService);
			System.out.println("Nombre de cluster : " + nbCluster);
			System.out.println("% des services pertinants par cluster :");

			System.out.println("---------------- Cosinus :");
			for (int i = 0; i < nbCluster; i++) {
				if (requet.getPourcentageServicePerResultCos().get(i)
						.doubleValue() != 0.0)
					System.out.println("--> Cluster n°"
							+ (i + 1)
							+ " le pourcentage est :"
							+ requet.getPourcentageServicePerResultCos().get(i)
									.doubleValue());
			}
			System.out.println("---------------- EJ :");
			for (int i = 0; i < nbCluster; i++) {
				if (requet.getPourcentageServicePerResultEj().get(i)
						.doubleValue() != 0.0)
					System.out.println("--> Cluster n°"
							+ (i + 1)
							+ " le pourcentage est :"
							+ requet.getPourcentageServicePerResultEj().get(i)
									.toString());
			}
			System.out.println("---------------- JS :");
			for (int i = 0; i < nbCluster; i++) {
				if (requet.getPourcentageServicePerResultJs().get(i)
						.doubleValue() != 0.0)
					System.out.println("--> Cluster n°"
							+ (i + 1)
							+ " le pourcentage est :"
							+ requet.getPourcentageServicePerResultJs().get(i)
									.doubleValue());
			}
			System.out.println("---------------- LI :");
			for (int i = 0; i < nbCluster; i++) {
				if (requet.getPourcentageServicePerResultLi().get(i)
						.doubleValue() != 0.0)
					System.out.println("--> Cluster n°"
							+ (i + 1)
							+ " le pourcentage est :"
							+ requet.getPourcentageServicePerResultLi().get(i)
									.doubleValue());
			}
			System.out.println("---------------- Log :");
			for (int i = 0; i < nbCluster; i++) {
				if (requet.getPourcentageServicePerResultLog().get(i)
						.doubleValue() != 0.0)
					System.out.println("--> Cluster n°"
							+ (i + 1)
							+ " le pourcentage est :"
							+ requet.getPourcentageServicePerResultLog().get(i)
									.doubleValue());
			}
		}
		/*************************************************************************/

		double d_cos = 0;
		double d_li = 0;
		double d_ej = 0;
		double d_js = 0;
		double d_log = 0;
		double pourcentage_cos = 0;
		double pourcentage_li = 0;
		double pourcentage_ej = 0;
		double pourcentage_js = 0;
		double pourcentage_log = 0;
		int pos_cos = -1;
		int pos_li = -1;
		int pos_ej = -1;
		int pos_js = -1;
		int pos_log = -1;

		List<Calculs> listCalculs = new ArrayList<Calculs>();

		for (int i = 0; i < nbService; i++) {

			Service ser = result.getListServiceEntrepot().get(i);
			Calculs cal = new Calculs();
			cal.service = ser;
			String serviceName1 = ser.getIDService();
			for (int j = 0; j < nbRequete; j++) {
				Request req = result.getListRequests().get(j);
				cal.listRequette.add(req);
				pourcentage_cos = getPourcentage(req, req.getResultCos(),
						req.getPourcentageServicePerResultCos(), serviceName1);
				pos_cos = positionCluster;

				pourcentage_ej = getPourcentage(req, req.getResultEj(),
						req.getPourcentageServicePerResultEj(), serviceName1);
				pos_ej = positionCluster;

				pourcentage_js = getPourcentage(req, req.getResultJs(),
						req.getPourcentageServicePerResultJs(), serviceName1);
				pos_js = positionCluster;

				pourcentage_li = getPourcentage(req, req.getResultLi(),
						req.getPourcentageServicePerResultLi(), serviceName1);
				pos_li = positionCluster;

				pourcentage_log = getPourcentage(req, req.getResultLog(),
						req.getPourcentageServicePerResultLog(), serviceName1);
				pos_log = positionCluster;

				d_cos = d_cos + pourcentage_cos;
				d_ej = d_ej + pourcentage_ej;
				d_js = d_js + pourcentage_js;
				d_li = d_li + pourcentage_li;
				d_log = d_log + pourcentage_log;
			}
			System.out
					.println("------------------------------------------------------------------------");
			d_cos = d_cos / nbRequete;
			ser.calcul_D_Cos = d_cos;
			System.out.println("D(" + ser.getIDService() + " |cos)=" + d_cos);

			d_ej = d_ej / nbRequete;
			ser.calcul_D_Ej = d_ej;
			System.out.println("D(" + ser.getIDService() + " |ej)=" + d_ej);

			d_js = d_js / nbRequete;
			ser.calcul_D_Js = d_js;
			System.out.println("D(" + ser.getIDService() + " |js)=" + d_js);

			d_li = d_li / nbRequete;
			ser.calcul_D_Li = d_li;
			System.out.println("D(" + ser.getIDService() + " |li)=" + d_li);

			d_log = d_log / nbRequete;
			ser.calcul_D_Log = d_log;
			System.out.println("D(" + ser.getIDService() + " |log)=" + d_log);

			cal.D.add(d_cos);
			cal.D.add(d_ej);
			cal.D.add(d_js);
			cal.D.add(d_li);
			cal.D.add(d_log);
			listCalculs.add(cal);

			d_cos = 0;
			d_ej = 0;
			d_js = 0;
			d_li = 0;
			d_log = 0;
		}

		// ////////////////////////////////////////////////////////////////////////////////////////////////////
		// cal.StartCalcul();

		List<Calculs> listCal = new ArrayList<Calculs>();
		for (int i = 0; i < listCalculs.size(); i++) {
			Calculs c = listCalculs.get(i);
			c.getCalcul();
			listCal.add(c);
		}

		// //////////////////////////////////////////////////////////////////////////////////////////////
		for (int i = 0; i < listCal.size(); i++) {
			Calculs c = listCal.get(i);
			for (int j = 0; j < c.listRequette.size(); j++) {
				Request req = c.listRequette.get(j);
				String req1 = req.getRequestName();
				int pos = -1;
				for (int k = 0; k < nbRequete; k++) {

					String req2 = result.getListRequests().get(k)
							.getRequestName();
					if (req1.equals(req2)) {
						pos = k;
						break;
					}
				}

				String serID = c.service.getIDService();
				String reqID = result.getListRequests().get(pos)
						.getRequestName();

				Service ServiceSD = new Service(serID, reqID, 0, 0, 0);
				String s = ServiceSD.getIDService();
				double score = c.listScoreSD.get(pos);
				ServiceSD.Score_SD = score;

				if (result.getListRequests().get(pos).getListRelevantSet()
						.contains(s))
					ServiceSD.setPertinant(true);

				result.getListRequests().get(pos).listServiceSD.add(ServiceSD);

			}

		}

		// //////////////////////////////////////////////////
		System.out
				.println("*****************************Affichage final***********************************");
		for (int k = 0; k < nbRequete; k++) {
			Request req = result.getListRequests().get(k);
			System.out.println(req.getRequestName());
			for (int i = 0; i < req.listServiceSD.size(); i++) {
				Service ser = req.listServiceSD.get(i);
				System.out.println(ser.getIDService());
				System.out.println(ser.Score_SD);
			}
		}

		// trier les nouveaux scores
		double map = 0;
		for (int k = 0; k < nbRequete; k++) {
			Request req = result.getListRequests().get(k);
			req.trierListMapp();
			System.out.println("/////////////////////////////////////////////");
			System.out.println("Liste trié des service de la requete : "
					+ req.getRequestName());
			
			double rend = 1;
			double maploc = 0;
			for (int i = 0; i < req.listServiceSD.size(); i++) {
				Service ser = req.listServiceSD.get(i);
				System.out.println("Service : " + ser.getIDService()
						+ "\t pertinant = " + ser.isPertinant() + "\t Score = "
						+ ser.Score_SD);
				if (ser.isPertinant()){
					double div = (rend/(i+1));
					maploc = maploc + div;
					rend++;
					
				}
				
			}
			map = map + (maploc/(rend-1));
		}
		map = map / nbRequete;
		
		System.out.println("Mapp ="+map);
		long t2 = System.currentTimeMillis();
		System.out.println("temps d'execution :" + (t2 - t1));

	}

	private static double getPourcentage(Request req,
			List<Service> resultMethode, List<Double> pourcentageMethode,
			String serviceName1) {
		double pourcentage = 0;

		int taille = req.getResultCos().size();
		for (int k = 0; k < taille; k++) {
			String serviceName2 = resultMethode.get(k).getIDService();
			if (serviceName1.equals(serviceName2)) {
				positionCluster = resultMethode.get(k).getPositionK();
				pourcentage = pourcentageMethode.get(positionCluster - 1);
				break;
			}
		}
		return pourcentage;
	}
}
