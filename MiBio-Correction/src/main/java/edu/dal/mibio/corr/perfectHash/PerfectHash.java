package edu.dal.mibio.corr.perfectHash;

import java.util.List;

import edu.dal.mibio.corr.corrector.Google5gramDetector;

public class PerfectHash {
	private int[] state = new int[Lookupa.CHECKSTATE];
	private int[] scrambles;
	private int value ;
	private int index = -1;
	
	public PerfectHash(int[] state,int[] scrambles,int value,int index)
	{
		  this.state = state;
		  this.scrambles = scrambles;
		  this.value = value;
		  this.index = index;
	} 
	
	public int phash(int k[],int len,List<List<Integer>> tabValues)
	{
		 int phValue = 0;
		 int[] returnStates = Lookupa.checkSum(k, len,state);
		 int temp = tabValues.get(index).get(returnStates[1]& value); 
		 phValue= (returnStates[0]&value) ^ scrambles[temp];
		 return phValue;
	}
	
	public int[] getState() {return state;}
	
	public int[] getScrambles() {return scrambles;}
	
	public int getValue() {return value;}
	
	public int getIndex() {return index;}
	
}
