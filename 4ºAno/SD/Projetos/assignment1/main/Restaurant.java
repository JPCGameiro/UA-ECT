package main;

import entities.*;
import sharedRegions.*;

/**
 *   Simulation of the Problem of the Restaurant.
 */

public class Restaurant {
	
	/**
	   *    Main method.
	   *    @param args runtime arguments
	   */
	public static void main(String[] args) {
		Chef chef;											// chef thread						
		Waiter waiter;										// waiter thread
		Student[] student = new Student[ExecuteConst.N];	// array of student threads
		Kitchen kit;										// reference to the kitchen
		Bar bar;											// reference to the bar
		Table tab;											// reference to the tab
		GeneralRepos repos;									// reference to the general Repository
		
		System.out.println("Start of Simulation");
		
		/* problem initialization */
		
		repos = new GeneralRepos("log.txt");
		kit = new Kitchen(repos);
		tab = new Table(repos);
		bar = new Bar(repos, tab);
		
		chef = new Chef("chef", kit, bar);
		waiter = new Waiter("waiter", kit, bar, tab);
		for(int i = 0; i < ExecuteConst.N; i++) {
			student[i] = new Student("student_"+(i), i, bar, tab);
		}
		
		/* start of simulation */
		
		chef.start();
		System.out.println("Lauching Chef Thread");
		waiter.start();
		System.out.println("Lauching Waiter Thread");
		for(int i = 0; i < ExecuteConst.N; i++)
		{
			student[i].start();
			System.out.println("Launching Student Thread "+i);
		}
		
		/* wait for the end of simulation */
		for(int i = 0; i < ExecuteConst.N; i++) {
			try
			{
				student[i].join();
			} catch(InterruptedException e) { }
			System.out.println("The student "+(i)+" terminated");
		}
		
		try
		{
			waiter.join();
		} catch(InterruptedException e) { }
		System.out.println("The waiter terminated");
		
		try
		{
			chef.join();
		} catch(InterruptedException e) { }
		System.out.println("The chef terminated");
		
		repos.reportLegend();						// report Legend in log file
		
		/* end of simulation */
		
		System.out.println("End of Simulation");
	}

}
