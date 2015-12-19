import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadResults_And_RelevanceSet {
	private String pathResult;
	private String pathRelevanceSet;
	private String typeRequestRelevanceSet;
	private int totalResult;
	private int k;

	private List<Request> listRequests = new ArrayList<Request>();
	private File repertoireResult;

	public ReadResults_And_RelevanceSet(String pathResult,
			String pathRelevanceSet, int k) {
		super();
		this.pathResult = pathResult;
		this.pathRelevanceSet = pathRelevanceSet;
		setTotalResult();
		setListRequests();
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
						requestName);
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
