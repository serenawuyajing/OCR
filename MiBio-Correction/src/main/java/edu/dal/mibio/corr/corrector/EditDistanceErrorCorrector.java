package edu.dal.mibio.corr.corrector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.dal.mibio.corr.util.CommonFuntions;
import edu.dal.mibio.corr.util.Unigram;

// TODO: Reformat EditDistanceCorrector.java
abstract class EditDistanceErrorCorrector
  implements ErrorCorrector
{
  private static final int EDIT_DISTANCE = 3;

  private static final int CANDIDATE_NUM = 50;

  public static final long MAX_FREQ = 19401194714L;
  
  public static Set<String> dSet = new HashSet<String>();

  abstract protected double score(String word, String candidate);
  
  abstract protected boolean contains(String word);
  
  public static void getEditDistanceResult(String word)
  {
	  dSet = new HashSet<String>();
	  Set<String> tmpWords = new HashSet<String>();
	  tmpWords.add(word);
	  CommonFuntions.oneDistanceWord(dSet,tmpWords,EDIT_DISTANCE);
  }
   
  @Override
  public List<Error> correct(Word word)
  {
    /* Select all the dictionary containing words. */
    List<Candidate> candList = new ArrayList<Candidate>();
    for(String dWord: dSet)
      if(contains(dWord))
        candList.add(new Candidate(dWord, score(word.word(), dWord)));
    
    /* Sort candidates in the descending order by their confidence. */
    Collections.sort(candList, new Comparator<Candidate>(){
      public int compare(Candidate s1, Candidate s2) {
        if(s1.confidence() < s2.confidence()) {
          return 1;
        } else if(s1.confidence() == s2.confidence()) {
          return 0; 
        } else {
          return -1;
        }
      }
    });
    
    /* Trim the first 50 elements in the candidate list. */
    for (int i = candList.size() - 1; i >= CANDIDATE_NUM; i--) {
      candList.remove(i);
    }

    /* Create and return the errors. */
    List<Error> erList = new ArrayList<Error>();
    for(int i=0;i < word.contexts().size(); i++) {
      erList.add(new Error(word.word(), word.contexts().get(i).position(), candList));
    }
    return erList;
  }
}
