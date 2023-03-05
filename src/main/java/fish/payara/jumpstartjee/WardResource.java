package fish.payara.jumpstartjee;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/ward")
public class WardResource {
	
	@Inject
	private WardService wardService;
	
	//book ward
	
	//available wards
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<WardEntity> getWardDetails(){
		return wardService.getAllWardDetails();
	}
	
	//occupied wards

}
