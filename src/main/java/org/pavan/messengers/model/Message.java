package org.pavan.messengers.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import jakarta.xml.bind.annotation.XmlTransient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
	
	private long id;
	private String message;
	private Date created;
	private String author;
	
	private Map<Long,Comment> comments= new HashMap<>();
	private List<Link> links= new ArrayList<>();
	
	
	
	public void setComments(Map<Long, Comment> comments) {
		this.comments = comments;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public Message() 
	{
		 
	}
	
	public Message(long id, String message, String author) {
		
		this.id = id;
		this.message = message;
		this.author = author;
		this.created = new Date();
	}
	public long getId() {
		return id;
	}
	public String getMessage() {
		return message;
	}
	public Date getCreated() {
		return created;
	}
	public String getAuthor() {
		return author;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@XmlTransient
	public Map<Long,Comment> getComments(){
		return comments;
	}
	public void SetComments(Map<Long,Comment> comments)
	{
		this.comments= comments;
	}

	public void addLink(String url, String rel) {
		Link link= new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
		
		
	}
	
}
