package edu.dal.mibio.corr.util;

import java.io.InputStream;

public class ResourceUtils
{
  public static String TEST_INPUT = "test.in.txt";
  public static String TEST_INPUT_SEGMENT = "test.in.seg.txt";
  public static String TEST_INPUT_SIMPLE = "test.txt";
  public static String SPECIAL_LIST = "specialList.txt";
  public static String LEXICON_LIST = "lexiconList.txt";
  public static String WIKI_LIST = "wiki.txt";
  public static String UNIGRAM ="unigram.txt";
  public static String RELAX_MATCHING = "/raid6/relaxmatching";

  /**
   * Get resource file from compiled path.
   * 
   * @param path  The resource path from compiled path.
   * @return The resource file.
   */
//  public static InputStream getResource(String path)
//  {
//    try {
//      return ResourceUtils.class.getClassLoader().getResourceAsStream(path);
//    } catch (NullPointerException e) {
//      throw new RuntimeException("Error: cannot find resource \"" + path + "\".");
//    }
//  }
}
