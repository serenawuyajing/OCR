package edu.dal.mibio.corr.corrector;

public class PhashValues {
	
	  private int candidate;
	  private int position;
	  private long frequency;
	  
	  public PhashValues(int position,int candidate,long frequency)
	  {
	    this.candidate = candidate;
	    this.frequency = frequency;
	    this.position = position;
	  }

	  public final int getCandidate() { return candidate; }
	  
	  public final int getPosition() { return position; }
	  
	  public final long getFrequency() { return frequency; }
}
