package semaphoreAssignment;

public class Assignment2 {
	  public static void main(String[] args) throws InterruptedException {
	        if (args.length != 4) {
	            System.exit(1);
	        }
	        
	        int numTellers = Integer.parseInt(args[0]);
	        int meanArrivalTime = Integer.parseInt(args[1]);
	        int meanServiceTime = Integer.parseInt(args[2]);
	        int simulationLength = Integer.parseInt(args[3]);
	        
	        if (numTellers <= 0 || meanArrivalTime <= 0 || meanServiceTime <= 0 || simulationLength <= 0 ||
	                meanArrivalTime % 10 != 0 || meanServiceTime % 10 != 0 || simulationLength % 10 != 0) {
	            System.out.println("All time values must be positive multiples of 10.");
	            return;
	        }
	        
	        System.out.println("Mean inter-arrival time: " + meanArrivalTime);
	        System.out.println("Mean servicve time: " + meanServiceTime);
	        System.out.println("Number of tellers: " + numTellers);
	        System.out.println("Length of simulation: " + simulationLength);
	        System.out.println();

	        BankSimulation bank = new BankSimulation(numTellers, meanArrivalTime, meanServiceTime, simulationLength);
	        Thread simulator = new Thread(new Customer(bank));
	        simulator.start();
	  }
}
