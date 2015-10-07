package edu.dal.mibio.corr.util;

import java.io.File;

public class ResourceUtils
{
  public static File TEST_INPUT = getResource("test.in.txt");
  public static File TEST_INPUT_SEGMENT = getResource("test.in.seg.txt");
  public static File SPECIAL_LIST = getResource("specialList.txt");
  public static File LEXICON_LIST = getResource("lexiconList.txt");
  public static File WIKI_LIST = getResource("wiki.txt");
  public static File UNIGRAM = getResource("unigram.txt");

  /**
   * Get resource file from compiled path.
   * 
   * @param path  The resource path from compiled path.
   * @return The resource file.
   */
  public static File getResource(String path)
  {
    try {
      return new File(FileUtils.class.getClassLoader().getResource(path).getPath());
    } catch (NullPointerException e) {
      throw new RuntimeException("Error: cannot find resource \"" + path + "\".");
    }
  }
}
