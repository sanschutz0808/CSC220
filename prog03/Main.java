/*
 * Main.java
 *
 * Created on September 12, 2006, 10:10 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package prog03;
import prog02.UserInterface;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
	public static double fibn;

	public static double time (Fib fib, int n, int ncalls) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < ncalls; i++)
			fibn = fib.fib(n);
		long end = System.currentTimeMillis();
		return (double) (end - start) / ncalls;
	}

	public static double time (Fib fib, int n) {
		int ncalls = 1;
		double theTime = time(fib, n, ncalls);

                // To get three figures of accuracy, the total time of
                // the experiment has to be at least 1000
                // milliseconds.  If the total time is too small,
                // increase the number of calls by a factor of 10.
		while (ncalls * theTime <= 1000) {
			ncalls *= 10;
			theTime = time(fib, n, ncalls);
		}

		return theTime;
	}

	private static double oldC = 0;

	private static UserInterface ui = new GUI();

	public static void doExperiments (Fib fib) {
		while (true) {
			String sn = ui.getInfo("Enter n");
			if (sn == null)
				return;
			int n = -1;
			try {
				n = Integer.parseInt(sn);
			} catch (Exception e) {
				ui.sendMessage(sn + " is not an integer.");
				continue;
			}
			if (n < 0) {
				ui.sendMessage(n + " is negative...invalid.");
				continue;
			}

			if (oldC != 0) {
				double estTime = oldC * fib.o(n);
				ui.sendMessage("Estimated time on input " + n + " is " +
						estTime + " milliseconds.");

				if (estTime > 1000 * 60 * 60) {
					ui.sendMessage("Estimated time is more than an hour.\n" +
					"I will ask you if you really want to run it.");
					String choices[] = { "yes", "no" };
					int choice = ui.getCommand(choices);
					if (choice == 1)
						continue;
				}
			}

			double fn = fib.fib(n);
			double newTime = time(fib, n);
			oldC = newTime / fib.o(n);
			ui.sendMessage("fib(" + n + ") = " + fn + " in " +
					newTime + " milliseconds.");
		}
	}

	public static void doExperiments () {
		String[] choices =
		{ "ExponentialFib", "LinearFib", "LogFib", "ConstantFib" };
		int choice = ui.getCommand(choices);
		Fib fib = new ExponentialFib();
		switch (choice) {
		case 0:
			doExperiments(new ExponentialFib());
			break;
		case 1:
			doExperiments(new LinearFib());
			break;
		case 2:
			doExperiments(new LogFib());
			break;
		case 3:
			doExperiments(new ConstantFib());
			break;
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		doExperiments();
	}

}
