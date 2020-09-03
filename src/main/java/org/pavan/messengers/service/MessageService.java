package org.pavan.messengers.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.pavan.messengers.database.DatabaseClass;
import org.pavan.messengers.exception.DataNotFoundException;
import org.pavan.messengers.model.Message;

import com.sun.xml.bind.v2.Messages;

public class MessageService {
	
	private Map<Long,Message> messages= DatabaseClass.getMessages();
	
	public MessageService()
	{
		messages.put(1L,new Message(1,"HelloWorld","Bala"));
		messages.put(2L,new Message(2,"HelloWorld$","pavan"));
		messages.put(3L,new Message(3,"HelloWorld#","kumar"));
		messages.put(4L,new Message(4,"HelloWorld@","Kiran"));
		messages.put(5L,new Message(5,"HelloWorld!","sharan"));
		messages.put(6L,new Message(6,"HelloWorld!!","bala"));
	}
	
	public List<Message> getAllMessages()
	{
		return new ArrayList<Message>(messages.values());
		
		/*
		Message m1=new Message(1L,"HelloWorld","kaushik");
		Message m2=new Message(2L,"Hello pavan","pavan");
		Message m3=new Message(3L,"Hello sharan","sharan");
		List<Message> list= new ArrayList<>();
	    list.add(m1);
	    list.add(m2);
	    list.add(m3);
	    return list;
	    */
	}
	
	public List<Message> getAllMessagesForYear(int year)
	{
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal =Calendar.getInstance();
		for(Message message:messages.values()) {
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR)==year) {
				messagesForYear.add(message);
			}
		}
		
		
		return messagesForYear;
	}
	
	public List<Message> getAllMessagePaginated(int start,int size)
	{
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if(start+size >list.size()) return new ArrayList<Message>();
		return list.subList(start, start+size);
		
	}
	
	public Message getMessage(long id)
	{
		Message message= messages.get(id);
		if(message == null)
		{
			throw new DataNotFoundException("Message with id " +id+"not found");
		}
		return message;
	}
  public Message addMessage(Message message)
  {
	message.setId(messages.size() + 1);
	messages.put(message.getId(), message);
	return message;	
}
  public Message updateMessage(Message message)
  {
	  if(message.getId() <=0)
	  {
	 return null;
      }
	  messages.put(message.getId(), message);
	  return message;
  }
  public Message removeMessage(Long id)
  {
	  return messages.remove(id);
  }
  
  
  
}
