package fish.payara.jumpstartjee.hms.pharmacy;

import java.util.List;

import fish.payara.jumpstartjee.AddItemEvent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pharmacy")
public class PharmacyResource {

	@Inject
	private PharmacyItemService pharmacyItemService;
	
	@Inject
	private Event<AddItemEvent> addItemEvent;

	// search for item
	@GET
	@Path("/item/{itemName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchForItem(@PathParam("itemName") String itemName) {
		return Response.ok(pharmacyItemService.searchForItem(itemName)).build();
//		if (item.isPresent()) {
//			return Response.ok(item.get()).build();
//		}
//		return Response.noContent().build();
	}

	// display items, count and price
	@GET
	@Path("/items")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PharmacyEntity> itemInventory(){
		return pharmacyItemService.itemInventory();
	}
	
	@POST
	@Path("/addItem/{quantity}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PharmacyEntity addItem(PharmacyEntity item, @PathParam("quantity") Long quantity) {
		var addedItem = pharmacyItemService.addItemToInventory(item, quantity);
		addItemEvent.fire(new AddItemEvent(addedItem));
		return addedItem;
	}

}
