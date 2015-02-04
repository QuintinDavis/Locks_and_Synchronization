/** 
    A class that maintains an array of sums, which are manipulated by multiple threads
*/ 
public class SharedSums
{ 
//   private volatile int[] sums; 
   private int[] sums; 
   
   private Object[] sumLocks;

   /** 
		Constructs an an array of sums, which will be shared among many threads 
		@param numSums the number of sums to store
    */
   public SharedSums (int numSums)
   { 
      sums = new int[numSums]; 
      resetSums ();

      sumLocks = createLocks (numSums);
   }
   
   private Object[] createLocks (int numSums)
   {
	   Object[] locks = new Object[numSums];

	   for (int i = 0; i < locks.length; i++)
		   locks[i] = new Object ();
	   
	   return locks;
   }

   /** 
       Resets all sums to zero
   */ 
//   public void synchronized resetSums () {
   public void resetSums () {
      for (int i = 0; i < sums.length; i++)
         sums[i] = 0;
   }

   /** 
       Gets number of shared sums
       @return the number of shared sums
   */ 
   public int size () {
      return sums.length;
   }

   /** 
       Adds a value to the sum at the specified index
       @param index the index of the sum to add value to
       @param addAmount the value to be added to the sum at specified index
   */ 
//   public synchronized void addToIndex (int index, int addAmount) { 
   public void addToIndex (int index, int addAmount)
   {
////	   if (index == 1)
		   synchronized (sumLocks[index])
		   {
			   sums[index] += addAmount;
		   }
////	   else
////		   sums[index] += addAmount;
   } 

   /** 
       Gets an array of all the sums
       @return the array with all the sums
   */ 
//   public synchronized int[] getSums () {
   public int[] getSums () {
      return sums;
   }
}

