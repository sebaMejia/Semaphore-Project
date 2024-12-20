package semaphoreAssignment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Teller implements Runnable {

	private Semaphore tellerSemaphore;
	private RandomIntMean randomIntMean;
	private BankSimulation bank;
	private static List<Integer> averageWaitingTime;

	public Teller(BankSimulation bank) {
		this.bank = bank;
		tellerSemaphore = new Semaphore(bank.getNumberOfTellers(), true);
		randomIntMean = new RandomIntMean();
		averageWaitingTime = new ArrayList<Integer>();
	}

	public synchronized void tellerSimulator(BankSimulation bank) throws InterruptedException {
		tellerSemaphore.acquire();
		try {
		System.out.println("At Time \t" + Customer.getElapsedTime() + ", Customer \t" + Thread.currentThread().getName() + " starts being served");
		int waittime = servicetime();
		Thread.sleep(waittime);
		averageWaitingTime.add(waittime);
		}
		finally {
			tellerSemaphore.release();
		}
	}
	
	private synchronized int servicetime() {
		return randomIntMean.random_int(bank.getMeanServiceTime() / 10) * 1000;
	}

	public synchronized static double getAverageWaitingTime() {
		int sum = 0;
		for (int i = 0; i < averageWaitingTime.size(); i++)
			sum += averageWaitingTime.get(i);
		return (sum * 0.001) / averageWaitingTime.size();
	}
	
	public void run() {
		try {
			tellerSimulator(bank);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}