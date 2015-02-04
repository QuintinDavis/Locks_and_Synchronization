/**
   This program runs multiple threads in parallel.
 */
public class ThreadTester
{
	public static void main (String[] args)
	{
		ThreadTester main = new ThreadTester ();
		
		final int NUM_SUMS = 5;
		final int NUM_THREADS = 11;
		final int NUM_RAND_VALS = 100;
		
		Summer[] r = new Summer[NUM_THREADS];
		Thread[] t = new Thread[NUM_THREADS];

		SharedSums sumArray = new SharedSums (NUM_SUMS);
		SharedSums sumArray2 = new SharedSums (NUM_SUMS);

		for (int i = 0; i < NUM_THREADS; i++)
		{
			SharedSums sums = null;
			
			if (i != (NUM_THREADS-1))
				sums = sumArray;
			else
				sums = sumArray2;
			
			r[i] = new Summer ("t" + i, sums, NUM_RAND_VALS);
			t[i] = new Thread (r[i]);
			t[i].start ();
		}

		try
		{
			// wait for all threads to finish (join), then print out sums
			for (int i = 0; i < t.length; i++)
				t[i].join();
			
			int[] sumOfIndivThreads = new int[NUM_SUMS];
			
			for (int j = 0; j < t.length; j++)
			{
				if (j != (NUM_THREADS-1))
				{
					int[] sumsThisThread = r[j].getThreadSums();

					for (int i = 0; i < sumOfIndivThreads.length; i++)
						sumOfIndivThreads[i] += sumsThisThread[i];
				}
			}

			main.printSums("sumArray's final sums:          (", sumArray.getSums());
			main.printSums("Sum of individual thread sums:  (", sumOfIndivThreads);
			System.out.println ();
			main.printSums("sumArray2's final sums:         (", sumArray2.getSums());
			main.printSums("Last thread's sums:             (", r[NUM_THREADS-1].getThreadSums());
		}
		catch (InterruptedException e)
		{}
		
		// any thread clean-up code (removing locks, etc.) goes here
	}
	
	public void printSums (String header, int[] sums)
	{
		System.out.print (header);
		for (int i = 0; i < sums.length; i++)
		{
			if (i == (sums.length-1))
				System.out.println (sums[i] + ")");
			else
				System.out.print (sums[i] + ", ");
		}		
	}
}

