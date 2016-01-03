import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadResults_And_RelevanceSet {
	private String pathResult;
	private String pathRelevanceSet;
	private String typeRequestRelevanceSet;
	private String pathService;
	private List<Service> listServiceEntrepot = new ArrayList<Service>(); 
	

	private int totalResult;
	int k;

	private List<Request> listRequests = new ArrayList<Request>();
	private File repertoireResult;
	private File repertoireService;

	public ReadResults_And_RelevanceSet(String pathResult,
			String pathRelevanceSet, String pathService, int k) {
		super();
		this.pathResult = pathResult;
		this.pathRelevanceSet = pathRelevanceSet;
		this.pathService = pathService;
		this.k = k;
		setTotalResult();
		setListRequests();
		setListServiceEntrepot();
	}

	public ReadResults_And_RelevanceSet(List<Service> resultCos,
			List<Service> resultLi, List<Service> resultEj,
			List<Service> resultJs, List<Service> resultLog,
			List<Service> relevanceSet, int totalResult,
			String pathResult, String pathRelevanceSet, int ballillageK) {
		// TODO Auto-generated constructor stub
		this.pathResult = pathResult;
		this.pathRelevanceSet = pathRelevanceSet;
		this.pathService = pathService;
		this.k = k;
		
		this.totalResult = totalResult;
		setListRequests();
		setListServiceEntrepot();
	}

	public List<Service> getListServiceEntrepot() {
		return listServiceEntrepot;
	}

	public void setListServiceEntrepot() {
		String serviceName;
		repertoireService = new File(pathService);
		File[] f = repertoireService.listFiles();
		int totalResult = repertoireService.listFiles().length;
		for (int i = 0; i < totalResult; i++) {
			if (f[i].isFile()) {
				serviceName = f[i].getName();
				Service serv = new Service(serviceName,null,0,0,0);
				listServiceEntrepot.add(serv);
			}
		}
		
	}
	
	private void setTotalResult() {
		repertoireResult = new File(pathResult);
		totalResult = repertoireResult.listFiles().length;
	}

	private void setListRequests() {
		String requestName;
		File[] f = repertoireResult.listFiles();
		for (int i = 0; i < totalResult; i++) {
			if (f[i].isDirectory()) {
				requestName = f[i].getName();
				Request req = new Request(pathResult, pathRelevanceSet, i,
						requestName, k);
				listRequests.add(req);
			}
		}
	}

	public String getPathResult() {
		return pathResult;
	}

	public void setPathResult(String pathResult) {
		this.pathResult = pathResult;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public List<Request> getListRequests() {
		return listRequests;
	}

	public int getK() {
		return k;
	}
}
