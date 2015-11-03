package edu.dal.mibio.corr.corrector;

public class WordContext
  implements Comparable<WordContext>
{
  private String[] context;
  private int position;

  public WordContext(int position, String... context)
  {
    if (context.length != 8)
      throw new IllegalArgumentException("Incorrect context is given.");
    this.context = context;
    this.position = position;
  }
   
  public int position()
  {
    return position;
  }

  public String word()
  {
    return context[4];
  }

  public String[] get(int index)
  {
    switch(index) {
      case 4:  return new String[]{context[0], context[1], context[2], context[3]};
      case 3:  return new String[]{context[1], context[2], context[3], context[5]};
      case 2:  return new String[]{context[2], context[3], context[5], context[6]};
      case 1:  return new String[]{context[3], context[5], context[6], context[7]};
      default: throw new IllegalArgumentException("Incorrect context index: " + index);
    }
  }

  @Override
  public int compareTo(WordContext other)
  {
    int wordDif = word().compareTo(other.word());
    if (wordDif != 0) {
      return wordDif;
    } else {
      return position() - other.position();
    }
  }
}