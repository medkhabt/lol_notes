package com.medkha.lol_notes.entities;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

@Entity
public class Game {
	
	@Id
	@GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@org.hibernate.annotations.CreationTimestamp
	private Date createdOn; 
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role; 
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Champion champion; 
	
	@OneToMany(mappedBy = "game",
			   fetch = FetchType.LAZY)
	private Set<Death> deaths = new LinkedHashSet<>(); 
	
	protected Game() { 
		
	}
	public Game(Role role, Champion champion) {
		this.role = role; 
		this.champion = champion; 
	}
	
	public Game(Role role, Champion champion, Set<Death> deaths) { 
		this.role = role; 
		this.champion = champion; 
		this.deaths = deaths; 
	}
	
	
	public void addDeath(Death death) { 
		this.deaths.add(death); 
	}
	
	public void removeDeath(Death death) { 
		this.deaths.remove(death); 
	}
	
	public void resetNotes() {
		this.deaths.clear(); 
	}
	
	
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Champion getChampion() {
		return champion;
	}
	public void setChampion(Champion champion) {
		this.champion = champion;
	}
	public Set<Death> getDeaths() {
		return deaths;
	}
	public void setDeaths(Set<Death> deaths) {
		this.deaths = deaths;
	}
	public Long getId() {
		return id;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	
	
	
}
