import java.util.ArrayList;
import java.util.List;

public class Calculs {

	Service service;
	List<Double> D = new ArrayList<Double>();
	List<Request> listRequetteApprentissage = new ArrayList<Request>();
	List<Request> listRequetteFusion = new ArrayList<Request>();
	List<Double> listScoreSD = new ArrayList<Double>();
	double Score = 0;

	public void getCalcul(int interval) {

		for (int i = 0; i < listRequetteFusion.size(); i++) {
			Request req = listRequetteFusion.get(i);
			// System.out.println(req.getRequestName());
			int pos = 0;
			for (int j = 0; j < req.getResultCos().size(); j++) {
				String ser = req.getResultCos().get(j).getIDService();
				ser = ser.substring(ser.indexOf('*') + 1, ser.length());
				if (service.getIDService().equals(ser)) {
					pos = req.getResultCos().get(j).getPositionK();
					break;
				}
			}
			Score = Score + (D.get(0) / pos);

			pos = 0;
			for (int j = 0; j < req.getResultEj().size(); j++) {
				String ser = req.getResultEj().get(j).getIDService();
				ser = ser.substring(ser.indexOf('*') + 1, ser.length());
				if (service.getIDService().equals(ser)) {
					pos = req.getResultEj().get(j).getPositionK();
					break;
				}
			}
			Score = Score + (D.get(1) / pos);
			pos = 0;
			for (int j = 0; j < req.getResultJs().size(); j++) {
				String ser = req.getResultJs().get(j).getIDService();
				ser = ser.substring(ser.indexOf('*') + 1, ser.length());
				if (service.getIDService().equals(ser)) {
					pos = req.getResultJs().get(j).getPositionK();
					break;
				}
			}
			Score = Score + (D.get(2) / pos);

			pos = 0;
			for (int j = 0; j < req.getResultLi().size(); j++) {
				String ser = req.getResultLi().get(j).getIDService();
				ser = ser.substring(ser.indexOf('*') + 1, ser.length());
				if (service.getIDService().equals(ser)) {
					pos = req.getResultLi().get(j).getPositionK();
					break;
				}
			}
			Score = Score + (D.get(3) / pos);

			pos = 0;
			for (int j = 0; j < req.getResultLog().size(); j++) {
				String ser = req.getResultLog().get(j).getIDService();
				ser = ser.substring(ser.indexOf('*') + 1, ser.length());
				if (service.getIDService().equals(ser)) {
					pos = req.getResultLog().get(j).getPositionK();
					break;
				}
			}
			Score = Score + (D.get(4) / pos);

			// System.out.println("Score("+ service.getIDService()
			// +")="+Score );

			// service.Score_SD = Score;
			listScoreSD.add(Score);

			Score = 0;

		}
	}
}
