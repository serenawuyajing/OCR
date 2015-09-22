package edu.dal.mibio.corr;

import java.util.ArrayList;
import java.util.List;

public class TestCorrector
    implements Corrector
{
  @Override
  public List<Error> correct(String content)
  {
    List<Error> errors = new ArrayList<Error>();
    
    // Assume there is only one candidate: "hello" with 100% confidence.
    List<Candidate> cands = new ArrayList<Candidate>();
    cands.add(new Candidate("hello", 1.0));

    // Error detection.
    StringBuilder sb = new StringBuilder();
    int offset = 0;
    int size = 0;
    // Naive tokenizer.
    for (int i = 0; i < content.length(); i++) {
      char c = content.charAt(i);
      if ((c > 64 && c < 91) || (c > 96 && c < 123)) {
        sb.append(c);
        size++;
      } else {
        String token = sb.toString();
        if (token.equals("hollo"))
          errors.add(new Error(token, offset, cands));
        offset += size + 1;
      }
    }
    return errors;
  }
  
  public static void main(String[] args)
  {
    Corrector test = new TestCorrector();
    List<Error> errors = test.correct("hollo world");
    for (Error e : errors) {
      System.out.println("Error: " + e.toString());
      System.out.println("Candidate: " + e.candidates());
    }
  }
}