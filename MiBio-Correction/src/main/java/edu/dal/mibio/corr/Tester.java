package edu.dal.mibio.corr;

import java.util.ArrayList;
import java.util.List;

import edu.dal.mibio.corr.corrector.Candidate;
import edu.dal.mibio.corr.corrector.DocumentCorrector;
import edu.dal.mibio.corr.corrector.Error;
import edu.dal.mibio.corr.corrector.ErrorCorrector;
import edu.dal.mibio.corr.corrector.ErrorDetector;
import edu.dal.mibio.corr.corrector.Word;
import edu.dal.mibio.corr.corrector.WordContext;
import edu.dal.mibio.corr.corrector.WordCorrector;

public class Tester
{
  public static void main(String[] args)
  {
    List<WordCorrector> corrs = new ArrayList<WordCorrector>();

    // Create on sample word corrector.
    WordCorrector wc1 = new WordCorrector(
        new ErrorDetector(){
          // Treat "h" as an error.
          @Override
          public boolean isError(Word word)
          {
            return word.word().equals("h");
          }
        },
        new ErrorCorrector(){
          // Correct error to "x" with 100% confidence.
          @Override
          public List<Error> correct(Word word)
          {
            Candidate cand = new Candidate("x", 1.0);
            List<Candidate> list = new ArrayList<Candidate>();
            list.add(cand);
            List<Error> errors = new ArrayList<Error>();
            for (WordContext wc : word.contexts())
              errors.add(new Error(wc, list));
            return errors;
          }
    }){};

    // Create another sample word corrector.
    WordCorrector wc2 = new WordCorrector(
        new ErrorDetector(){
          // Treat "h" and "a" as an error.
          @Override
          public boolean isError(Word word)
          {
            String w = word.word();
            return w.equals("a") || w.equals("h");
          }
        },
        new ErrorCorrector(){
          // Correct error to "y" with 50% confidence.
          @Override
          public List<Error> correct(Word word)
          {
            Candidate cand = new Candidate("y", 0.5);
            List<Candidate> list = new ArrayList<Candidate>();
            list.add(cand);
            List<Error> errors = new ArrayList<Error>();
            for (WordContext wc : word.contexts())
              errors.add(new Error(wc, list));
            return errors;
          }
    }){};

    corrs.add(wc1);
    corrs.add(wc2);

    List<Error> errors = new DocumentCorrector().correct(corrs,
        "a b c d e f g h i j k l m n");
    for (Error e : errors)
      System.out.println(e);
  }
}
