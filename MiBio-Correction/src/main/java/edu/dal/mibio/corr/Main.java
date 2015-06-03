package edu.dal.mibio.corr;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
  public static void main(String[] args) throws IOException
  {
	// TODO Auto-generated method stub
	readBioFile rb = new readBioFile();
	List<File> list = new ArrayList<File>();
	list = rb.readAllFile("BioFile\\");
	//for(int i=0;i<list.size();i++){
	//	rb.readFile(list.get(i));
	//}
	rb.readFile(list.get(0));
	for(int i=0;i<rb.allWordsOneFile.size();i++){
		 System.out.println(rb.allWordsOneFile.get(i));
	}
  }
}
