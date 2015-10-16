package edu.dal.mibio.corr.util;

import gnu.trove.map.hash.TObjectLongHashMap;
import gnu.trove.set.hash.THashSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class ReaderUtils
{
  public static String read(Reader reader)
      throws IOException
  {
    StringBuilder sb = new StringBuilder();
    try (
      BufferedReader br = new BufferedReader(reader);
    ){
      for (String line; (line = br.readLine()) != null;) {
        sb.append(line).append('\n');
      }
    }
    return sb.toString();
  }

  public static THashSet<String> readList(Reader reader)
      throws IOException
  {
    THashSet<String> dic = new THashSet<String>();
    try (
      BufferedReader br = new BufferedReader(reader);
    ){
      for (String line; (line = br.readLine()) != null;) {
        dic.add(line);
      }
    }
    return dic;
  }

  public static TObjectLongHashMap<String> readUnigram(Reader reader)
      throws IOException
  {
    TObjectLongHashMap<String> uniMap = new TObjectLongHashMap<String>();
    try (
      BufferedReader br = new BufferedReader(reader);
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
}