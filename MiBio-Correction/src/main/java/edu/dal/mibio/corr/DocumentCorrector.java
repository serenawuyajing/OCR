package edu.dal.mibio.corr;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class DocumentCorrector
{
  public List<Error> correct(List<WordCorrector> correctors, String content)
  {
    /* Store eight latest reading consecutive tokens and positions. */
    String[] context = new String[8];
    int[] position = new int[8];
    for (int i = 0; i < 8; i++) {
      context[i] = "";
      position[i] = -1;
    }
    
    Map<String, Word> wordMap = new HashMap<String, Word>();

    /* Tokenize content using Peen Treebank. */
    PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(new StringReader(content),
        new CoreLabelTokenFactory(), "");
    int tidx;
    for (tidx = 0; ptbt.hasNext(); tidx++) {
      
      /* Add token to context. */
      CoreLabel token = ptbt.next();
      int idx = tidx % 8;
      context[idx] = token.toString();
      position[idx] = token.beginPosition();
      if (tidx < 3)
        continue;
      
      /* Store the full-context token. */
      idx = (tidx - 3) % 8;
      addToMap(wordMap, new WordContext(
          position[idx],
          context[(tidx + 1) % 8],
          context[(tidx + 2) % 8],
          context[(tidx + 3) % 8],
          context[(tidx + 4) % 8],
          context[idx],
          context[(tidx - 2) % 8],
          context[(tidx - 1) % 8],
          context[tidx % 8]
      ));
    }
    /* Store the tailing tokens. */
    for (int i = 0; i < 3; tidx++, i++) {
      int idx = (tidx - 3) % 8;
      if (position[idx] >= 0)
        addToMap(wordMap, new WordContext(
            position[idx],
            context[(tidx + 1) % 8],
            context[(tidx + 2) % 8],
            context[(tidx + 3) % 8],
            context[(tidx + 4) % 8],
            context[idx],
            (i > 1 ? "" : context[(tidx - 2) % 8]),
            (i > 0 ? "" : context[(tidx - 1) % 8]),
            ""
        ));
    }

    return correct(correctors, new ArrayList<Word>(wordMap.values()));
  }
  
  private void addToMap(Map<String, Word> map, WordContext context)
  {
    String tkn = context.word();
    if (map.containsKey(tkn))
      map.get(tkn).add(context);
    else
      map.put(tkn, new Word(tkn, context));
  }
  
  private List<Error> correct(List<WordCorrector> correctors, List<Word> words)
  {
    Map<String, Error> errMap = new HashMap<String, Error>();
    for (WordCorrector cor : correctors) {
      List<Error> errs = cor.correct(words);

      /* Select the union of two error list. */
      for (Error err : errs) {
        String name = err.name();
        if (errMap.containsKey(name)) {
          /* Select the union of two candidate lists of the overlapping error. */
          Error error = errMap.get(name);
          Set<Candidate> errSet = new HashSet<Candidate>(error.candidates());
          for (Candidate cand : err.candidates())
            if (!errSet.contains(cand))
              error.add(cand);
        } else {
          errMap.put(name, err);
        }
      }
    }
    return new ArrayList<Error>(errMap.values());
  }
}
