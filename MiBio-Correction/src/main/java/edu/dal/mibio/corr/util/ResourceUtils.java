package edu.dal.mibio.corr.util;

import java.io.InputStream;

public class ResourceUtils
{
  public static InputStream TEST_INPUT = getResource("test.in.txt");
  public static InputStream TEST_INPUT_SEGMENT = getResource("test.in.seg.txt");
  public static InputStream SPECIAL_LIST = getResource("specialList.txt");
  public static InputStream LEXICON_LIST = getResource("lexiconList.txt");
  public static InputStream WIKI_LIST = getResource("wiki.txt");
  public static InputStream UNIGRAM = getResource("unigram.txt");

  /**
   * Get resource file from compiled path.
   * 
   * @param path  The resource path from compiled path.
   * @return The resource file.
   */
  public static InputStream getResource(String path)
  {
    try {
      return ResourceUtils.class.getClassLoader().getResourceAsStream(path);
    } catch (NullPointerException e) {
      throw new RuntimeException("Error: cannot find resource \"" + path + "\".");
    }
  }
}
