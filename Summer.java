/**
   A Runnable (threaded) class that adds random values to a series of sums shared among many threads
 */
public class Summer implements Runnable
{
	private static final int DELAY = 10;

	private String threadID;
	private SharedSums sums;
	private int count;
	private int[] mySums;

    /**
		Constructs an object for adding random values to a series of shared sums
		@param threadID the given name or ID for this thread
		@param sums the object containing multiple shared sums, to which random values are added
		@param count the number of random values to add per sum
     */
	public Summer (String threadID, SharedSums sums, int count)
	{
		this.threadID = threadID;
		this.sums = sums;
		this.count = count;
		mySums = new int[sums.size()];
	}
	
	public int[] getThreadSums () {
		return mySums;
	}

	public void run()
	{
		try
		{
			int num_sums = sums.size();

			for (int j = 0; j < count; j++)
			{
				for (int i = 0; i < num_sums; i++)
				{
					int rand = (int) (Math.random() * 1000);

					mySums[i] += rand;
					sums.addToIndex (i, rand);
				}

				Thread.sleep((int) (Math.random() * DELAY));
			}
		}
		catch (InterruptedException exception)
		{
		}
	}
}
