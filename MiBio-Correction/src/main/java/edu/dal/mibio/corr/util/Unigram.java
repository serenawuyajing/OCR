package edu.dal.mibio.corr.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import edu.dal.mibio.corr.util.ResourceUtils;
import gnu.trove.map.hash.TObjectLongHashMap;

public class Unigram
{
  /* Singleton */
  private static Unigram instance = null;

  private TObjectLongHashMap<String> map;
  
  private Unigram()
  {
    try {
      map = ReaderUtils.readUnigram(new InputStreamReader(ResourceUtils.UNIGRAM));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  private Unigram(File unigram)
  {
    try {
      map = ReaderUtils.readUnigram(new FileReader(unigram));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Unigram getInstance()
  {
    if (instance == null) {
      instance = new Unigram();
    }
    return instance;
  }
  
  public static Unigram getInstance(File unigram)
  {
    if (instance == null) {
      instance = new Unigram(unigram);
    }
    return instance;
  }
  
  public TObjectLongHashMap<String> map()
  {
    return map;
  }
}