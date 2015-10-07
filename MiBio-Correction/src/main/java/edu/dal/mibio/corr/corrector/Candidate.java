package edu.dal.mibio.corr.corrector;

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

  void confidence(double value)
  {
    if (value > 1 || value < 0)
      throw new IllegalArgumentException("Invalid confidence value is given.");
    confidence = value;
  }

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