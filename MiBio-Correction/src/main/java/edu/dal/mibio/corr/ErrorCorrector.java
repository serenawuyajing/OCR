package edu.dal.mibio.corr;

import java.util.List;

public interface ErrorCorrector
{
  List<Error> correct(Word word);
}
