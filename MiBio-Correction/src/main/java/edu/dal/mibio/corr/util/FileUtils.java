package edu.dal.mibio.corr.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import gnu.trove.map.hash.TObjectLongHashMap;
import gnu.trove.set.hash.THashSet;

public class FileUtils
{
  public static String read(File file)
      throws FileNotFoundException, IOException
  {
    StringBuilder sb = new StringBuilder();
    try (
      BufferedReader br = new BufferedReader(new FileReader(file));
    ){
      for (String line; (line = br.readLine()) != null;) {
        sb.append(line).append('\n');
      }
    }
    return sb.toString();
  }

  public static THashSet<String> readList(File file)
      throws FileNotFoundException, IOException
  {
    THashSet<String> dic = new THashSet<String>();
    try (
      BufferedReader br = new BufferedReader(new FileReader(file));
    ){
      for (String line; (line = br.readLine()) != null;) {
        dic.add(line);
      }
    }
    return dic;
  }

  public static TObjectLongHashMap<String> readUnigram(File unigram)
      throws FileNotFoundException, IOException
  {
    TObjectLongHashMap<String> uniMap = new TObjectLongHashMap<String>();
    try (
      BufferedReader br = new BufferedReader(new FileReader(unigram));
    ){
      for (String line; (line = br.readLine()) != null;) {
        String[] fields = line.split("\t");
        String gram = fields[0];
        long freq = Long.parseLong(fields[1]);
        uniMap.put(gram, freq);
      }
    }
    return uniMap;
  }
  
  public static void main(String[] args)
      throws FileNotFoundException, IOException
  {
    readUnigram(ResourceUtils.UNIGRAM);
  }
}
