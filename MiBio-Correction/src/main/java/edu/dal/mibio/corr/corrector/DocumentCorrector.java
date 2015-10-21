package edu.dal.mibio.corr.corrector;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.dal.mibio.corr.util.CommonFuntions;
import edu.dal.mibio.corr.util.Unigram;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import gnu.trove.map.hash.TObjectLongHashMap;

public class DocumentCorrector
{
  private static Pattern SPLIT_PATTERN = Pattern.compile("(\\w+)");

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
        new CoreLabelTokenFactory(), "ptb3Escaping=false,normalizeOtherBrackets=false");
    int widx = 0;
    while (ptbt.hasNext()) {
      CoreLabel token = ptbt.next();

      /* Split if '-' in token. */
      Matcher m = SPLIT_PATTERN.matcher(token.toString());
      while(m.find()) {
        
        /* Add token to context. */
        int idx = widx % 8;
        context[idx] = m.group();
        position[idx] = token.beginPosition() + m.start();

        if (widx > 3) {

          /* Store the full-context token. */
          idx = (widx - 3) % 8;
          addToMap(wordMap, new WordContext(
              position[idx],
              context[(widx + 1) % 8],
              context[(widx + 2) % 8],
              context[(widx + 3) % 8],
              context[(widx + 4) % 8],
              context[idx],
              context[(widx - 2) % 8],
              context[(widx - 1) % 8],
              context[widx % 8]));
        }
        widx++;
      }
    }
    /* Store the tailing tokens. */
    for (int i = 0; i < 3; widx++, i++) {
      int idx = (widx + 5) % 8;
      if (position[idx] >= 0) {
        addToMap(wordMap, new WordContext(
            position[idx],
            context[(widx + 1) % 8],
            context[(widx + 2) % 8],
            context[(widx + 3) % 8],
            context[(widx + 4) % 8],
            context[idx],
            (i > 1 ? "" : context[(widx - 2) % 8]),
            (i > 0 ? "" : context[(widx - 1) % 8]),
            ""
        ));
      }
    }

    /* Filter words that exists in the unigram. */
    TObjectLongHashMap<String> map = Unigram.getInstance().map();
    List<Word> words = new LinkedList<Word>(wordMap.values());
    for (int i = 0; i < words.size();) {
      if (CommonFuntions.validUniGram(words.get(i).word(), map.get(words.get(i).word()))) {
        words.remove(words.get(i));
      } else {
        i++;
      }
    }

    return correct(correctors, words);
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
      List<Error> errs = cor.correct(words, correctors);

      /* Select the union of two error list. */
      for (Error e : errs) {
        String name = e.name();
        if (errMap.containsKey(name)) {
          /* Select the union of two candidate lists of the overlapping error. */
          Error errInMap = errMap.get(name);
          Map<String, Candidate> candSetInMap = new HashMap<String, Candidate>();
          for (Candidate c : errInMap.candidates())
            candSetInMap.put(c.name(), c);
          for (Candidate c : e.candidates())
            if (!candSetInMap.containsKey(c.name())) {
              errInMap.add(c);
            } else {
              Candidate candInMap = candSetInMap.get(c.name());
              candInMap.confidence(candInMap.confidence() + c.confidence());
            }
        } else {
          errMap.put(name, e);
        }
      }
    }
    /* Confidence normalization. */
    List<Error> errors = new ArrayList<Error>(errMap.values());
    for (Error e : errors)
      e.normalize().sort();
    
    /* Sort errors by position. */
    Collections.sort(errors, new Comparator<Error>(){
      @Override
      public int compare(Error e1, Error e2)
      {
        return (int)(e1.position() - e2.position());
      }
    });
    return errors;
  }
}
