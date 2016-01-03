import java.util.ArrayList;
import java.util.List;

public class Principale {

	static private String cheminResultat = "\\Fichiers\\resultats";
	static private String cheminRelevanceSet = "\\Fichiers\\relevance_sets";
	static private String cheminService = "\\Fichiers\\services\\1.1";
	static int positionCluster = -1;

	static double pourcentage_cos = 0;
	static double pourcentage_li = 0;
	static double pourcentage_ej = 0;
	static double pourcentage_js = 0;
	static double pourcentage_log = 0;

	static double d_cos = 0;
	static double d_li = 0;
	static double d_ej = 0;
	static double d_js = 0;
	static double d_log = 0;

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

		int ballillageK;
		int posMeilleurMap = -1;
		double MeilleurMap = 0;

		ReadResults_And_RelevanceSet result = new ReadResults_And_RelevanceSet(
				pathResult, pathRelevanceSet, pathService, 2);

		for (ballillageK = 2; ballillageK < 1007; ballillageK++) {

			result.k = ballillageK;
			for (int i = 0; i < result.getListRequests().size(); i++) {
				result.getListRequests().get(i).K = ballillageK;
				result.getListRequests().get(i).setPourcentage();
				result.getListRequests().get(i).listServiceSD.clear();
			}

			int nbRequete = result.getListRequests().size();
			int nbService = result.getListServiceEntrepot().size();

			double mapAgr = 0;
			// /////////////////
			int interval = 0;
			for (interval = 0; interval < 10; interval++) {
				List<Calculs> listCalculs = new ArrayList<Calculs>();
				double map = 0;
				for (int i = 0; i < nbService; i++) {
					Service ser = result.getListServiceEntrepot().get(i);
					Calculs cal = new Calculs();
					cal.service = ser;
					String serviceName1 = ser.getIDService();

					/*System.out.println("Apprentissage du service :"
							+ serviceName1);
					*/
					for (int j = 0; j < nbRequete; j++) {
						
						Request req = result.getListRequests().get(j);
						
						if (j == (interval * 3) || j == (interval * 3) + 1
								|| j == (interval * 3) + 2) {
							cal.listRequetteFusion.add(req);
							continue;
						}
						
						/*System.out.println("\t La requete(" + j + ")= "
								+ req.getRequestName());*/
						cal.listRequetteApprentissage.add(req);
						calculPourcentage(req, serviceName1);
					}

					int totalRequeteApp = cal.listRequetteApprentissage.size();
					ser.calcul_D_Cos = d_cos / (totalRequeteApp);
					ser.calcul_D_Ej = d_ej / (totalRequeteApp);
					ser.calcul_D_Js = d_js / (totalRequeteApp);
					ser.calcul_D_Li = d_li / (totalRequeteApp);
					ser.calcul_D_Log = d_log / (totalRequeteApp);
					/*System.out.println("Apprentissage du service :"
							+ serviceName1);
					System.out.println("Cos= " + ser.calcul_D_Cos + "\t Ej= "
							+ ser.calcul_D_Ej + "\t Js= " + ser.calcul_D_Js
							+ "\t Li= " + ser.calcul_D_Li + "\t Log= "
							+ ser.calcul_D_Log);
					System.out
							.println("---------------------------------------");*/
					cal.D.add(ser.calcul_D_Cos);
					cal.D.add(ser.calcul_D_Ej);
					cal.D.add(ser.calcul_D_Js);
					cal.D.add(ser.calcul_D_Li);
					cal.D.add(ser.calcul_D_Log);
					listCalculs.add(cal);

					d_cos = 0;
					d_ej = 0;
					d_js = 0;
					d_li = 0;
					d_log = 0;
				}

				List<Calculs> listCal = new ArrayList<Calculs>();
				for (int i = 0; i < listCalculs.size(); i++) {
					Calculs c = listCalculs.get(i);
					c.getCalcul(interval);
					listCal.add(c);					
					
				}

				/********************************/
				Calculs c = null;
				for (int i = 0; i < listCal.size(); i++) {
					c = listCal.get(i);
					for (int j = 0; j < c.listRequetteFusion.size(); j++) {
						Request req = c.listRequetteFusion.get(j);
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
						double score = c.listScoreSD.get(j);
						ServiceSD.Score_SD = score;

						if (result.getListRequests().get(pos)
								.getListRelevantSet().contains(s))
							ServiceSD.setPertinant(true);

						result.getListRequests().get(pos).listServiceSD
								.add(ServiceSD);
					}
				}
				/********************************/
				/**/
				for (int k = 0; k < c.listRequetteFusion.size(); k++) {
					Request req1 = c.listRequetteFusion.get(k);
					
					int pos = -1;
					for (int i = 0; i < nbRequete; i++) {
						String req2 = result.getListRequests().get(i)
								.getRequestName();
						if (req2.equals(req1.getRequestName())) {
							pos = i;
							break;
						}
					}
					
					Request req = result.getListRequests().get(pos);
					
					req.trierListMapp();
					
					double rend = 1;
					double maploc = 0;
					for (int i = 0; i < req.listServiceSD.size(); i++) {
						Service ser = req.listServiceSD.get(i);
						if (ser.isPertinant()) {
							double div = (rend / (i + 1));
							maploc = maploc + div;
							rend++;

						}
					}
					map = map + (maploc / (rend - 1));
				}
				/**/
				map = map / c.listRequetteFusion.size();
				mapAgr = mapAgr + map;
				//System.out.println("liste des service fusion :");
				
				/*for (int i = 0; i < c.listRequetteFusion.size(); i++) {
					System.out.println(c.listRequetteFusion.get(i).getRequestName());
					
				}*/
				
				System.out.println(interval + ") MappLocal(" + ballillageK
						+ ")= \t" + map);	
			}
			mapAgr = mapAgr / (interval+1);
			
			

			System.out.println("\t MappAgre(" + ballillageK
					+ ")= " + mapAgr+ "\n ================");
			
			
			if (mapAgr > MeilleurMap) {
				MeilleurMap = mapAgr;
				posMeilleurMap = ballillageK;
			}
		}
		// ///////////////////
		System.out.println("Meilleur map : ============>Mapp(" + posMeilleurMap
				+ ")= " + MeilleurMap);

		long t2 = System.currentTimeMillis();
		System.out.println("temps d'execution :" + (t2 - t1));
	}

	private static void calculPourcentage(Request req, String serviceName1) {
		pourcentage_cos = getPourcentage(req, req.getResultCos(),
				req.getPourcentageServicePerResultCos(), serviceName1);

		pourcentage_ej = getPourcentage(req, req.getResultEj(),
				req.getPourcentageServicePerResultEj(), serviceName1);

		pourcentage_js = getPourcentage(req, req.getResultJs(),
				req.getPourcentageServicePerResultJs(), serviceName1);

		pourcentage_li = getPourcentage(req, req.getResultLi(),
				req.getPourcentageServicePerResultLi(), serviceName1);

		pourcentage_log = getPourcentage(req, req.getResultLog(),
				req.getPourcentageServicePerResultLog(), serviceName1);

		d_cos = d_cos + pourcentage_cos;
		d_ej = d_ej + pourcentage_ej;
		d_js = d_js + pourcentage_js;
		d_li = d_li + pourcentage_li;
		d_log = d_log + pourcentage_log;

	}

	private static double getPourcentage(Request req,
			List<Service> resultMethode, List<Double> pourcentageMethode,
			String serviceName1) {
		double pourcentage = 0;

		int taille = req.getResultCos().size();
		for (int k = 0; k < taille; k++) {
			String serviceName2 = resultMethode.get(k).getIDService();
			serviceName2 = serviceName2.substring(
					serviceName2.indexOf('*') + 1, serviceName2.length());
			if (serviceName1.equals(serviceName2)) {
				positionCluster = resultMethode.get(k).getPositionK();
				pourcentage = pourcentageMethode.get(positionCluster - 1);
				break;
			}
		}
		return pourcentage;
	}
}
