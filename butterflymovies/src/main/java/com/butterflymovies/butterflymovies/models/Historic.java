package com.butterflymovies.butterflymovies.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Historic {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long cod;	
	private String data;
	
	public long getCod() {
		return cod;
	}
	public void setCod(long cod) {
		this.cod = cod;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
