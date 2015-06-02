package edu.dal.mibio.corr;

import java.util.List;

public class Error
{
  private String name;
  private long position;
  private List<Candidate> candidates;
  
  public Error(String name, long position, List<Candidate> candidates)
  {
    this.name = name;
    this.position = position;
    this.candidates = candidates;
  }

  public final long position() { return position; }

  public final int size() { return name.length(); }

  public List<Candidate> getCandidates() { return candidates; }

  @Override
  public String toString() { return name; }
}
