package edu.dal.mibio.corr.corrector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO: Reformat EditDistanceCorrector.java
abstract class EditDistanceErrorCorrector
  implements ErrorCorrector
{
  private static final int EDIT_DISTANCE = 3;

  private static final int CANDIDATE_NUM = 50;

  public static final long MAX_FREQ = 19401194714L;

  private static final List<Character> ASCII_LIST = new ArrayList<Character>();
  static {
    for(int i = 45; i < 58; i++)
      ASCII_LIST.add((char)i);
    for(int i = 65; i < 91; i++)
      ASCII_LIST.add((char)i);
    for(int i = 97; i < 123; i++)
      ASCII_LIST.add((char)i);
    ((ArrayList<Character>)ASCII_LIST).trimToSize();
  }

  abstract protected double score(String word, String candidate);
  
  abstract protected boolean contains(String word);
   
  @Override
  public List<Error> correct(Word word)
  {
    Set<String> dSet = new HashSet<String>();
    oneDistanceWord(dSet, word.word(), EDIT_DISTANCE);
     
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

  private void oneDistanceWord(Set<String> distanceWords, String word, int distance)
  {
    if (distance-- > 0) {
      /* Deletion operation. */
      for (int i = 0;i < word.length(); i++) {
        StringBuffer e = new StringBuffer(word);
        String newWord = e.deleteCharAt(i).toString();
        if (newWord != "") {
          if(!distanceWords.contains(newWord)) {
            distanceWords.add(newWord);
            oneDistanceWord(distanceWords, newWord, distance);
          }
        }
      }
      /* Insertion operation. */
      for(int charIndex = 0; charIndex < ASCII_LIST.size(); charIndex++) {
        for(int i = 0; i< word.length() + 1; i++) {
          StringBuffer e = new StringBuffer(word);
          String newWord = e.insert(i, ASCII_LIST.get(charIndex)).toString();
          if(!distanceWords.contains(newWord)) {
            distanceWords.add(newWord);
            oneDistanceWord(distanceWords, newWord, distance);
          }
        }
      }
      /* Substitution operation. */
      for(int charIndex = 0; charIndex < ASCII_LIST.size(); charIndex++) {
        for(int i = 0;i < word.length(); i++) {
          String newWord = word.replace(word.charAt(i), ASCII_LIST.get(charIndex));
          if(!distanceWords.contains(newWord)) {
            distanceWords.add(newWord);
            oneDistanceWord(distanceWords, newWord, distance);
          }
        }
      } 
    }
  } 
}
