package edu.dal.mibio.corr;

public class Candidate
{
  private String name;
  private double confidence;
  
  public Candidate(String name, double confidence)
  {
    this.name = name;
    this.confidence = confidence;
  }
  
  public String name() { return name; }

  public double confidence() { return confidence; }

  @Override
  public String toString() { return name; }
  
  @Override
  public boolean equals(Object other)
  {
    if (other instanceof Candidate
        && ((Candidate) other).name().equals(name)
        && ((Candidate) other).confidence() == confidence) {
      return true;
    }
    return false;
  }
}