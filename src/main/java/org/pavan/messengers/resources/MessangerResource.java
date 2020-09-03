package org.pavan.messengers.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.glassfish.jersey.message.internal.MediaTypes;
import org.pavan.messengers.model.Message;
import org.pavan.messengers.resources.beans.MessageFilterBean;
import org.pavan.messengers.service.MessageService;

import com.sun.xml.bind.v2.Messages;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;



@Path("/messages")
public class MessangerResource {
	MessageService messageService = new MessageService();
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean)	{
		if(filterBean.getYear()>0)
		{
			return  messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart()>=0  && filterBean.getSize() >= 0)
		{
			return messageService.getAllMessagePaginated(filterBean.getStart(),  filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMessage(Message message,@Context UriInfo uriInfo) throws URISyntaxException
	{
		//System.out.println(uriInfo.getAbsolutePath());
		Message newMessage= messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		//UriInfo.getAbsolutePath().toString() +newId
		 URI uri=uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
				       .entity(newMessage)
				       .build();
	}
	
	@PUT
	@Path("/{messageId}") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId")  long id ,Message message)
	{
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteMessage(@PathParam("messageId")  long id)
	{
		messageService.removeMessage(id);
	}
	
	
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("messageId")  long id ,@Context UriInfo uriInfo)
	{
		Message message =  messageService.getMessage(id);
		String uri = getUriForSelf(uriInfo, message);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		return message;
	}



	private String getUriForComments(UriInfo uriInfo, Message message) {
		 URI uri = uriInfo.getBaseUriBuilder()
				 .path(MessangerResource.class)
					.path(MessangerResource.class,"getCommentResource")
					.path(CommentResource.class)
					.resolveTemplate("messageId", message.getId())
					.build();
			return uri.toString();
		
	}



	private String getUriForProfile(UriInfo uriInfo, Message message) {
		 URI uri = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build();
		return uri.toString();
	}



	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(MessangerResource.class)
				.path(Long.toString(message.getId()))
				.build()
				.toString();
		return uri;
	}
    // creating another resource and linking to it
    @Path("/{messageId}/comments")
    public CommentResource getCommentResource()
    {
    	return  new CommentResource();
    }
}
