package edu.dal.mibio.corr.corrector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class WordCorrector
{
  public static List<String> typeSet = new ArrayList<String>();

  private ErrorDetector detector;
  private ErrorCorrector corrector;
  private String type;
  
  public WordCorrector(ErrorDetector detector, ErrorCorrector corrector)
  {
    this(detector, corrector, "");
  }

  public WordCorrector(ErrorDetector detector, ErrorCorrector corrector, String type)
  {
    this.detector = detector;
    this.corrector = corrector;
    this.type = type;
    typeSet.add(type);
  }

  public List<Error> correct(Word word)
  {
    List<Error> errors = new ArrayList<Error>();
	if (detector.isError(word))
	{
		 errors.addAll(corrector.correct(word));
	}      
    return errors;
  }
  
  protected String type()
  {
    return type;
  }
  
  public double weight(List<WordCorrector> correctors)
  {
    int subTypes = 1;
    int noSubTypes = 0;
    Set<String> withSubTypeSet = new HashSet<String>();
    for (WordCorrector corr : correctors) {
      if (corr.type().length() == 0) {
        noSubTypes++;
      } else {
        withSubTypeSet.add(corr.type());
      }
      if (type.equals(corr.type()))
        subTypes++;
    }
    double typeWeight = 1.0 / (noSubTypes + withSubTypeSet.size());
    if (type.length() == 0)
      return typeWeight;
    else
      return typeWeight / subTypes;
  }
}
