package edu.dal.mibio.corr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Word
{
  private String name;
  private List<WordContext> contexts;
  
  public Word(String word)
  {
    name = word;
    contexts = new ArrayList<WordContext>();
  }

  public Word(String word, WordContext context)
  {
    this(word);
    add(context);
  }
  
  public String word()
  {
    return name;
  }
  
  public List<WordContext> contexts()
  {
    return contexts;
  }
  
  public void add(WordContext context)
  {
    if (word() != context.word())
      throw new IllegalArgumentException("Given context is for word: " + name);
    contexts.add(context);
  }
  
  public void sort()
  {
    Collections.sort(contexts);
  }
}