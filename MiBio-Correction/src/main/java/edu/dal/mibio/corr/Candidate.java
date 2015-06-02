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

  @Override
  public String toString() { return name; }

  public double confidence() { return confidence; }
}