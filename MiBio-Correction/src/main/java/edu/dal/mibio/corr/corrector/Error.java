package edu.dal.mibio.corr.corrector;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Error
{
  private String name;
  private long position;
  private List<Candidate> candidates;
  private static final int CANDIDATE_NUM = 50;
  
  public Error(WordContext context, List<Candidate> candidates)
  {
    this(context.word(), context.position(), candidates);
  }

  public Error(String name, long position, List<Candidate> candidates)
  {
    this.name = name;
    this.position = position;
    this.candidates = candidates;
  }
  
  @Override
  public boolean equals(Object other)
  {
    if (other instanceof Error
        && ((Error) other).name().equals(name)
        && ((Error) other).position() == position) {
      return true;
    }
    return false;
  }
  
  public int hashCode(){
	  return 0;
  }
  
  
  public String name() { return name; }

  public final long position() { return position; }

  public final int size() { return name.length(); }

  public List<Candidate> candidates() { return candidates; }
  
  public boolean add(Candidate candidate)
  {
    return candidates.add(candidate);
  }
  
  // Weight all the candidate confidence.
  public void weightConfidence(double weight)
  {
    for (Candidate cand : candidates)
      cand.confidence(cand.confidence() * weight);
  }
  
  /**
   * Normalize all the candidate confidence. The sum of all confidences should
   * be 1 after normalization.
   */
  public Error normalize()
  {
    double sum = 0;
    for (Candidate c : candidates)
      sum += c.confidence();
    for (Candidate c : candidates)
      c.confidence(c.confidence() / sum);
    return this;
  }
  
  public Error sort()
  {
    Collections.sort(candidates, new Comparator<Candidate>(){
      public int compare(Candidate s1, Candidate s2) {
        if(s1.confidence() < s2.confidence()) {
          return 1;
        } else if(s1.confidence() == s2.confidence()) {
          return 0; 
        } else {
          return -1;
        }
      }
    });
    for (int i = candidates.size() - 1; i >= CANDIDATE_NUM; i--) {
    	candidates.remove(i);
      }
    return this;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(name);
    sb.append("\t(").append(position).append(")\t").append(candidates.size()).append("{");
    for (Candidate cand : candidates)
      sb.append(cand.name()).append(':').append(cand.confidence()).append(',');
    if (candidates.size() > 0) {
      sb.deleteCharAt(sb.length() - 1);
    }
    sb.append("}");
    return sb.toString();
  }
}