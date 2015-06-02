package edu.dal.mibio.corr;

import java.util.List;

public interface Corrector
{
  public List<Error> correct(String content);
}
