import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class Principale {

	// Variables concernant le chemin relatif des fichiers XML
	static private String cheminResultat = "\\Fichiers\\resultats";
	static private String cheminRelevanceSet = "\\Fichiers\\relevance_sets";
	static private String cheminService = "\\Fichiers\\services\\1.1";
	//

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
		int posMeilleurMap = -1;
		double MeilleurMap = 0;

		String pathResult = System.getProperty("user.dir") + cheminResultat;
		String pathRelevanceSet = System.getProperty("user.dir")
				+ cheminRelevanceSet;
		String pathService = System.getProperty("user.dir") + cheminService;

		System.out.println("Le répertoire des résultat est: " + pathResult);
		System.out.println("Le répertoire des relevantSet est: "
				+ pathRelevanceSet);
		System.out.println("Le répertoire des Servives est: " + pathService);

		// Instantiation de de l'objet Result, en spécifiant les chemins des
		// resultats, releventSet et les services. plus le nombre de cluster.
		// Dans ce cas on a pris 2 cluster, c a d 1007/2
		ReadResults_And_RelevanceSet result = new ReadResults_And_RelevanceSet(
				pathResult, pathRelevanceSet, pathService, 2);

		// pour balayer tout les cas possible, dans cette boucle on limite K à
		// 500, c a d le max sera 1007/500
		int ballillageK = 100;
		/*for (ballillageK = 2; ballillageK < 500; ballillageK++)*/{

			double R_moy = 0;
			double P_moy = 0;

			result.setNombreCluster(ballillageK);

			// Pour initialiser les différente liste lors de la nouvelle
			// itération
			for (int i = 0; i < result.getListRequests().size(); i++) {
				result.getListRequests().get(i).setNombreCluster(ballillageK);
				result.getListRequests().get(i).setPourcentage();
				result.getListRequests().get(i).getListResultatProb().clear();
			}

			int nbRequete = result.getListRequests().size();
			int nbService = result.getListServiceEntrepot().size();

			double mapAgr = 0;
			double mapAgrLi = 0;
			int interval = 0;

			for (interval = 0; interval < 1; interval++) {
				List<Calculs> listCalculs = new ArrayList<Calculs>();
				double map = 0;
				double mapLi = 0;

				for (int i = 0; i < nbService; i++) {
					Service ser = result.getListServiceEntrepot().get(i);
					Calculs cal = new Calculs();
					cal.service = ser;
					String serviceName1 = ser.getIDService();

					/*
					 * System.out.println("Apprentissage du service :" +
					 * serviceName1);
					 */
					for (int j = 0; j < nbRequete; j++) {

						Request req = result.getListRequests().get(j);

						/*
						 * if (j == (interval * 3)%29 || j == ((interval * 3) +
						 * 1)%29 || j == ((interval * 3) + 2)%29)
						 */{
							cal.listRequetteFusion.add(req);
							/* continue; */
						}

						cal.listRequetteApprentissage.add(req);
						// Somme du service par rapport a toutes les requetes
						calculPourcentage(req, serviceName1);
					}

					int totalRequeteApp = cal.listRequetteApprentissage.size();
					ser.calcul_D_Cos = d_cos / (totalRequeteApp);
					ser.calcul_D_Ej = d_ej / (totalRequeteApp);
					ser.calcul_D_Js = d_js / (totalRequeteApp);
					ser.calcul_D_Li = d_li / (totalRequeteApp);
					ser.calcul_D_Log = d_log / (totalRequeteApp);

					cal.getMoyBaseApp().add(ser.calcul_D_Cos);
					cal.getMoyBaseApp().add(ser.calcul_D_Ej);
					cal.getMoyBaseApp().add(ser.calcul_D_Js);
					cal.getMoyBaseApp().add(ser.calcul_D_Li);
					cal.getMoyBaseApp().add(ser.calcul_D_Log);
					// récupération de la moyenne d'apprentissage pour chaque
					// service
					listCalculs.add(cal);

					// initialisation des variables ntermédiaires
					d_cos = 0;
					d_ej = 0;
					d_js = 0;
					d_li = 0;
					d_log = 0;
				}

				List<Calculs> listCal = new ArrayList<Calculs>();
				for (int i = 0; i < listCalculs.size(); i++) {
					Calculs c = listCalculs.get(i);
					c.getCalcul();
					listCal.add(c);
				}

				/********************************/
				Calculs c = null;
				// Remplissage de la liste de service qui contient le
				// scoreProFuse pour chaque requete.
				// result : est un tableau de 29 cases (nombre de requetes)
				//
				for (int i = 0; i < listCal.size(); i++) {
					c = listCal.get(i);
					for (int j = 0; j < c.listRequetteFusion.size(); j++) {

						int pos = getPosition(c.listRequetteFusion.get(j),
								nbRequete, result);

						String serID = c.service.getIDService();
						String reqID = result.getListRequests().get(pos)
								.getRequestName();

						Service ServiceSD = new Service(serID, reqID, 0, 0, 0);
						String s = ServiceSD.getIDService();

						ServiceSD.calcul_D_ProbFuse = c.listScoreSD.get(j);
						ServiceSD.setPertinant(ifIsPertinent(result, pos, s));

						result.getListRequests().get(pos).getListResultatProb()
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

					

					try {
						req.saveInXMLFile();
					} catch (ParserConfigurationException e) {
						e.printStackTrace();
					}
					//req.trierListMapp();
					
					double Vp = 0;
					double Fp = 0;
					double Fn = 0;

					int pas = 10;
					for (int j = 0; j < pas; j++) {
						Service ser = req.getListResultatProb().get(j);
						/*System.out.println("Serive (" + j + ")"
								+ ser.getIDService());*/
						if (ser.isPertinant())
							Vp = Vp + 1;
					}
					Fp = pas - Vp;
					Fn = req.getListRelevantSet().size() - Vp;
					/*System.out.println("requette: " + req.getRequestName());
					System.out.println("Nbre service pert : "
							+ req.getListRelevantSet().size());*/
					double P = Vp / (Vp + Fp);
					double R = Vp / (Vp + Fn);
					/*System.out.println("P(" + pas + ")=" + P + " ,R(" + pas
							+ ")=" + R);*/

					R_moy = R_moy + R;
					P_moy = P_moy + P;

					double rend = 1;
					double maploc = 0;
					for (int i = 0; i < req.getListResultatProb().size(); i++) {
						Service ser = req.getListResultatProb().get(i);
						if (ser.isPertinant()) {
							double div = (rend / (i + 1));
							maploc = maploc + div;
							rend++;

						}
					}
					/******************/
					double rendLi = 1;
					double maplocLi = 0;
					for (int i = 0; i < req.getResultLi().size(); i++) {
						Service ser = req.getResultLi().get(i);
						if (ser.isPertinant()) {
							double div = (rendLi / (i + 1));
							maplocLi = maplocLi + div;
							rendLi++;

						}
					}
					mapLi = mapLi + (maplocLi / (rendLi - 1));
					/*****************/

					map = map + (maploc / (rend - 1));

				}
				/**/
				map = map / c.listRequetteFusion.size();

				mapAgr = mapAgr + map;

				mapLi = mapLi / c.listRequetteFusion.size();
				mapAgrLi = mapAgrLi + mapLi;

				// System.out.println("liste des service fusion :");

				/*
				 * for (int i = 0; i < c.listRequetteFusion.size(); i++) {
				 * System
				 * .out.println(c.listRequetteFusion.get(i).getRequestName());
				 * 
				 * }
				 */

				/*System.out.println(interval + ") MappLocal(" + ballillageK
						+ ")= " + map);*/
				/*System.out.println(interval + ") MappLocalLi(" + ballillageK
						+ ")= " + mapLi);*/
			}
			mapAgr = mapAgr / (interval);
			mapAgrLi = mapAgrLi / (interval);

			System.out.println("\t MappAgre(" + ballillageK + ")= " + mapAgr
					/*+ "\n ================"*/);
			/*System.out.println("\t MappAgreLi(" + ballillageK + ")= "
					+ mapAgrLi + "\n ================");*/

			/*System.out.println("P(moy)=" + P_moy / 29 + " ,R(moy)=" + R_moy
					/ 29);*/

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

	private static boolean ifIsPertinent(ReadResults_And_RelevanceSet result,
			int pos, String s) {
		// TODO Auto-generated method stub
		boolean etat = false;
		if (result.getListRequests().get(pos).getListRelevantSet().contains(s))
			etat = true;
		return etat;
	}

	private static int getPosition(Request request, int nbRequete,
			ReadResults_And_RelevanceSet result) {
		int pos = -1;
		String req1 = request.getRequestName();
		for (int k = 0; k < nbRequete; k++) {
			String req2 = result.getListRequests().get(k).getRequestName();
			if (req1.equals(req2)) {
				pos = k;
				break;
			}
		}
		return pos;
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
		int positionCluster = -1;
		double pourcentage = 0;

		int taille = req.getResultCos().size();
		for (int k = 0; k < taille; k++) {
			String serviceName2 = resultMethode.get(k).getIDService();
			serviceName2 = serviceName2.substring(
					serviceName2.indexOf('*') + 1, serviceName2.length());
			if (serviceName1.equals(serviceName2)) {

				positionCluster = resultMethode.get(k).getPositionK();

				if (!req.getListRelevantSet().contains(serviceName1))
					break;

				pourcentage = pourcentageMethode.get(positionCluster - 1);
				break;
			}
		}
		return pourcentage;
	}
}
