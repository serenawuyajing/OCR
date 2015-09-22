package edu.dal.mibio.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.spi.resource.Singleton;

import edu.dal.mibio.corr.Corrector;
import edu.dal.mibio.corr.TestCorrector;

@Path("/services")
@Singleton
public class Services
{
  private Corrector corrector;

  public Services()
  {
    System.out.print("Initializing MiBio correction service... ");
    corrector = new TestCorrector();
  }

  @POST
  @Path("/corr")
  @Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
  @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
  public Response correctionService(CorrectionRequest request)
  {
    CorrectionResponse response = new CorrectionResponse(
        corrector.correct(request.getConent()));
    return Response
        .ok(response, MediaType.APPLICATION_JSON + ";charset=utf-8")
        .build();
  }
}
