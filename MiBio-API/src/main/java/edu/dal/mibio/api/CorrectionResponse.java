package edu.dal.mibio.api;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.dal.mibio.corr.corrector.Candidate;
import edu.dal.mibio.corr.corrector.Error;

@XmlRootElement
public class CorrectionResponse
{
  @XmlElement
  private List<ErrorElement> errors;

  public CorrectionResponse(List<Error> errors)
  {
    this.errors = new ArrayList<ErrorElement>();
    for (Error e : errors)
      this.errors.add(new ErrorElement(e));
  }
  
  @XmlRootElement
  private class ErrorElement
  {
    @XmlElement
    private String name;
    @XmlElement
    private long position;
    @XmlElement
    private List<CandidateElement> candidates;
    
    public ErrorElement(Error error)
    {
      name = error.name();
      position = error.position();
      candidates = new ArrayList<CandidateElement>();
      for (Candidate c : error.candidates())
        candidates.add(new CandidateElement(c));
    }
  }
  
  @XmlRootElement
  private class CandidateElement
  {
    @XmlElement
    private String name;
    @XmlElement
    private double confidence;

    private CandidateElement(Candidate candidate)
    {
      name = candidate.toString();
      confidence = candidate.confidence();
    }
  }
}