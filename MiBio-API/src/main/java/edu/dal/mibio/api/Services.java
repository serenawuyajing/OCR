package edu.dal.mibio.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.spi.resource.Singleton;

import edu.dal.mibio.corr.corrector.DocumentCorrector;
import edu.dal.mibio.corr.corrector.DomainWordCorrector;
import edu.dal.mibio.corr.corrector.LexiconWordCorrector;
import edu.dal.mibio.corr.corrector.UnigramWordCorrector;
import edu.dal.mibio.corr.corrector.WikiWordCorrector;
import edu.dal.mibio.corr.corrector.WordCorrector;
import edu.dal.mibio.corr.util.ResourceUtils;

@Path("/services")
@Singleton
public class Services
{
  private DocumentCorrector docCorr;
  private WordCorrector[] wordCorr;

  public Services()
      throws FileNotFoundException, IOException
  {
    System.out.print("\n\nInitializing MiBio correction service... ");
    docCorr = new DocumentCorrector();
    wordCorr = new WordCorrector[4];
    wordCorr[0] = new WikiWordCorrector(getResource("wiki.txt"));
    wordCorr[1] = new DomainWordCorrector(getResource("specialList.txt"));
    wordCorr[2] = new LexiconWordCorrector(getResource("lexiconList.txt")); 
    wordCorr[3] = new UnigramWordCorrector(getResource("unigram.txt"));
    System.out.println("Done!\n\n");
  }

  @POST
  @Path("/corr")
  @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
  @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
  public Response correctionService(CorrectionRequest request)
  {
    List<WordCorrector> corrs = new ArrayList<WordCorrector>();
    for (int i = 0; i < request.getOptions().size(); i++) {
      if (request.getOptions().get(i) == true) {
        corrs.add(wordCorr[i]);
      }
    }
    CorrectionResponse response = new CorrectionResponse(
        docCorr.correct(corrs, request.getConent()));
    return Response
        .ok(response, MediaType.APPLICATION_JSON + ";charset=utf-8")
        .build();
  }
  
  public static File getResource(String path)
  {
    try {
      return new File(ResourceUtils.class.getClassLoader().getResource(path).getPath());
    } catch (NullPointerException e) {
      throw new RuntimeException("Error: cannot find resource \"" + path + "\".");
    }
  }
}
