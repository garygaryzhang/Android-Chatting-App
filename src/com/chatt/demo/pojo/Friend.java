package com.chatt.demo.pojo;

public class Friend {

	private Long id;
	private String friend_name;
	
	public Friend(){
		
	}
	
	@Override
	public String toString() {
		return "Friend [id=" + id + ", friend_name=" + friend_name + "]";
	}
	
	public Friend(String friend_name){
		this.friend_name=friend_name;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFriend_name() {
		return friend_name;
	}

	public void setFriend_name(String friend_name) {
		this.friend_name = friend_name;
	}

	
	
	
	
	
	
}
