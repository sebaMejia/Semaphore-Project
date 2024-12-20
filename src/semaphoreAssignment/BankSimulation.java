package semaphoreAssignment;

public class BankSimulation implements Runnable{
	private Teller teller;
	private int numTellers;
	private int meanTimeArrival;
	private int meanServiceTime;
	private int lengthSimulation;

	public BankSimulation(Teller teller) {
		this.teller = teller;
	}

	public BankSimulation(int nTellers, int mTimeArrival, int mServiceTime, int lSimulation) {
		this.setNumberOfTellers(nTellers);
		this.setMeanArrivalTime(mTimeArrival);
		this.setMeanServiceTime(mServiceTime);
		this.setLengthOfSimulation(lSimulation);
	}

    public int getNumberOfTellers() { return numTellers; }
    public int getMeanArrivalTime() { return meanTimeArrival; }
    public int getMeanServiceTime() { return meanServiceTime; }
    public int getLengthOfSimulation() { return lengthSimulation; }

    public void setNumberOfTellers(int numTellers) { this.numTellers = numTellers; }
    public void setMeanArrivalTime(int meanTimeArrival) { this.meanTimeArrival = meanTimeArrival; }
    public void setMeanServiceTime(int meanServiceTime) { this.meanServiceTime = meanServiceTime; }
    public void setLengthOfSimulation(int lengthSimulation) { this.lengthSimulation = lengthSimulation; }


	public void display(String message) {
        System.out.printf("At Time \t%d, Customer \t%s %s%n", 
                          Customer.getElapsedTime(), Thread.currentThread().getName(), message);
    }
	
	@Override
    public void run() {
        display("arrives at the bank");
        try {
            teller.tellerSimulator(this);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Simulation interrupted: " + e.getMessage());
        }
        display("leaves the bank");
    }
}