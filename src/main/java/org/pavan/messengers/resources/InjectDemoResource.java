package org.pavan.messengers.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/injectdemo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InjectDemoResource {

	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations()
	{
		return "Test";
	}
}
