public class Service {
	private String IDService;
	private String IDRequete;
	private double scoreInput;
	private double scoreOutput;
	private double moyenne;
	private int positionK = -1;
	private boolean isPertinant = false; 
	double calcul_D_Cos = 0;
	double calcul_D_Li = 0;	
	double calcul_D_Ej = 0;
	double calcul_D_Js = 0;
	double calcul_D_Log = 0;
	double calcul_D_ProbFuse = 0;
	

	private int tailleCluster = -1;
	private int nbrServicePertinant = -1;

	public Service(String iDService, String iDRequete, double scoreInput,
			double scoreOutput, double moyenne) {
		super();
		IDService = iDService;
		IDRequete = iDRequete;
		this.scoreInput = scoreInput;
		this.scoreOutput = scoreOutput;
		this.moyenne = moyenne;
	}

	public boolean isPertinant() {
		return isPertinant;
	}

	public void setPertinant(boolean isPertinant) {
		this.isPertinant = isPertinant;
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

	public int getPositionK() {
		return positionK;
	}

	public void setPositionK(int positionK) {
		this.positionK = positionK;
	}

	public int getTailleCluster() {
		return tailleCluster;
	}

	public int getNbrServicePertinant() {
		return nbrServicePertinant;
	}

	public void setTailleCluster(int tailleCluster) {
		this.tailleCluster = tailleCluster;
	}

	public void setIDRequete(String iDRequete) {
		IDRequete = iDRequete;
	}
	public void setNbrServicePertinant(int nbrServicePertinant) {
		this.nbrServicePertinant = nbrServicePertinant;
	}
}
