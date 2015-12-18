public class Service {
	private String IDService;
	private String IDRequete;
	private double scoreInput;
	private double scoreOutput;
	private double moyenne;

	public Service(String iDService, String iDRequete, double scoreInput,
			double scoreOutput, double moyenne) {
		super();
		IDService = iDService;
		IDRequete = iDRequete;
		this.scoreInput = scoreInput;
		this.scoreOutput = scoreOutput;
		this.moyenne = moyenne;
	}

	public String getIDService() {
		return IDService;
	}

	public String getIDRequete() {
		return IDRequete;
	}

	public double getScoreInput() {
		return scoreInput;
	}

	public double getScoreOutput() {
		return scoreOutput;
	}

	public double getMoyenne() {
		return moyenne;
	}

}
