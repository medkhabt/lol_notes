package com.medkha.lol_notes.entities;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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
			   fetch = FetchType.LAZY,
			   cascade = CascadeType.REMOVE)
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((champion == null) ? 0 : champion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (champion != other.champion)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (role != other.role)
			return false;
		return true;
	}
	
	
	
}
