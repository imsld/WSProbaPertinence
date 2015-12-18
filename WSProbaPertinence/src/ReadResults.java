import java.io.File;
import java.util.List;

public class ReadResults {
	private String pathResult;
	private int totalResult;
	private List<Request> listRequests;
	private File repertoireResult;
	
	public ReadResults(String pathResult) {
		super();
		this.pathResult = pathResult;
		setTotalResult();
		setListRequests();
	}

	private void setTotalResult() {
		repertoireResult = new File(pathResult);
		totalResult = repertoireResult.listFiles().length;
	}
	
	public void setListRequests() {
		String requestName; 
		File[] f = repertoireResult.listFiles();
		for (int i = 0; i < totalResult; i++) {
			 if (f[i].isDirectory()) {
				 requestName = f[i].getName();
				 Request req = new Request(i, requestName);
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

}
