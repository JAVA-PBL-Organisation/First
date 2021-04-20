package com.studentguide.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="FAVORITE")
public class Favorite {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int cId;
	private String name;
	// to know why we added their contact
	@Column(length=500)
	private String info;
	// to set their work they do
	@Column(length=500)
	private String work;
	private String email;
	private String phone;
	private String image;
	@Column(length=1000)
	private String description;
	@Override
	public String toString() {
		return "Favorite [cId=" + cId + ", name=" + name + ", info=" + info + ", work=" + work + ", email=" + email
				+ ", phone=" + phone + ", image=" + image + ", description=" + description + ", user=" + user + "]";
	}
	@ManyToOne
	@JsonIgnore
	private User user;
	
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		// TODO Auto-generated method stub
		this.user= user;
		
	}

	
	
}
