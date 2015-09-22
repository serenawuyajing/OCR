package edu.dal.mibio.corr;

import java.util.List;

public class Error
{
  private String name;
  private long position;
  private List<Candidate> candidates;

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
  
  public String name() { return name; }

  public final long position() { return position; }

  public final int size() { return name.length(); }

  public List<Candidate> candidates() { return candidates; }
  
  public boolean add(Candidate candidate)
  {
    return candidates.add(candidate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(name);
    sb.append("\t(").append(position).append(")\t").append(candidates.size()).append("{");
    for (Candidate cand : candidates)
      sb.append(cand.name()).append(':').append(cand.confidence()).append(',');
    sb.deleteCharAt(sb.length() - 1).append("}");
    return sb.toString();
  }
}