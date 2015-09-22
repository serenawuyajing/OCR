package edu.dal.mibio.corr;

import java.util.ArrayList;
import java.util.List;

public abstract class WordCorrector
{
  private ErrorDetector detector;
  private ErrorCorrector corrector;

  public WordCorrector(ErrorDetector detector, ErrorCorrector corrector)
  {
    this.detector = detector;
    this.corrector = corrector;
  }

  public List<Error> correct(List<Word> words)
  {
    List<Error> errors = new ArrayList<Error>();
    for (Word word : words)
      if (detector.isError(word))
        errors.addAll(corrector.correct(word));
    return errors;
  }
}
