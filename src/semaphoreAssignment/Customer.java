package semaphoreAssignment;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Customer implements Runnable {

	private static BlockingQueue<Thread> customersQueue = new LinkedBlockingQueue<Thread>();
	private static long startTime = System.currentTimeMillis();
	private int counter;
	private BankSimulation bank;
	private Teller teller;
	private RandomIntMean randomIntMean;

	public Customer(BankSimulation bank) {
		this.bank = bank;
		this.teller = new Teller(bank);
		this.randomIntMean = new RandomIntMean();
		this.counter = 0;
	}

	public static int getElapsedTime() {
		return (int) ((System.currentTimeMillis() - startTime) / 100);
	}

	private int getArrivalTime() {
		return randomIntMean.random_int(bank.getMeanArrivalTime() / 10) * 1000;
	}

	public void customerSimulator() throws InterruptedException {
		while (getElapsedTime() < bank.getLengthOfSimulation()) {
            Thread.sleep(getArrivalTime());
            Thread thread = new Thread(new BankSimulation(teller));
            thread.setName(String.valueOf(++counter));
            customersQueue.add(thread);
            thread.start();
        }

		while (!customersQueue.isEmpty()) {
			Thread thread = customersQueue.peek();
			if (!thread.isAlive()) {
				thread.join();
				customersQueue.poll();

			}
		}
		
		System.out.println();
		System.out.println("Simulation terminated after " + counter + " customers served");
		System.out.printf("Average waiting time = %.2f", Teller.getAverageWaitingTime());
	}

	@Override
	public void run() {
		try {
			customerSimulator();
		} catch (Exception e) {
			System.err.println("Customer thread interrupted: " + e.getMessage());
		}
	}
}