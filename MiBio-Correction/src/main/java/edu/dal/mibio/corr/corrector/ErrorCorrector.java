package edu.dal.mibio.corr.corrector;

import java.util.List;

public interface ErrorCorrector
{
  List<Error> correct(Word word);
}
