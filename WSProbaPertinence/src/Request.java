public class Request {
	private int requestID;
	private String requestName;
	private Object resultCos;
	private Object resultLi;
	private Object resultEj;
	private Object resultJs;
	private Object resultLog;

	public Request(int requestID, String requestName) {
		super();
		this.requestID = requestID;
		this.requestName = requestName;
		System.out.println(requestName);
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public Object getResultCos() {
		return resultCos;
	}

	public void setResultCos(Object resultCos) {
		this.resultCos = resultCos;
	}

	public Object getResultLi() {
		return resultLi;
	}

	public void setResultLi(Object resultLi) {
		this.resultLi = resultLi;
	}

	public Object getResultEj() {
		return resultEj;
	}

	public void setResultEj(Object resultEj) {
		this.resultEj = resultEj;
	}

	public Object getResultJs() {
		return resultJs;
	}

	public void setResultJs(Object resultJs) {
		this.resultJs = resultJs;
	}

	public Object getResultLog() {
		return resultLog;
	}

	public void setResultLog(Object resultLog) {
		this.resultLog = resultLog;
	}
}
