
package model;

import java.util.Date;
import java.util.List;

public class Message {
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String content;
	private Date datePublication;
	private User editor;
	private Forum destination;

	public Forum getDestination() {
		return destination;
	}

	public void setDestination(Forum destination) {
		this.destination = destination;
	}

	/*
	 * java.text.SimpleDateFormat sdf = new
	 * java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 * 
	 * String currentTime = sdf.format(dt);
	 */
	public Message() {
		this.datePublication = new Date();

	}

	public Message(String contenu, User editeur) {
		this.content = contenu;
		this.datePublication = new Date();
		this.editor = editeur;
	}

	public Message(String content, User editor, Date datePublication) {
		this.content = content;
		this.datePublication = datePublication;
		this.editor = editor;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}

	public User getEditor() {
		return editor;
	}

	public void setEditor(User editor) {
		this.editor = editor;
	}

//DB access methods

	public static Message FindbyId(int id) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

	public static List<Message> FindbyForum(int idForum) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

	public static List<Message> FindbyUser(int idUser) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

}
