package com.medkha.lol_notes.entities;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
public class Game {
	@Id
	@GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@org.hibernate.annotations.CreationTimestamp
	@Column(updatable = false)
	private Date createdOn; 
	
	@NotNull
	private String roleName;

	@NotNull
	private String laneName;
	
	@NotNull
	private Integer championId;

	@NotNull
	private Integer queueId;
	
	public Game() { 
		
	}
	public Game(Integer championId, String roleName, String laneName, Integer queueId) {
		this.roleName = roleName;
		this.laneName = laneName;
		this.championId = championId;
		this.queueId = queueId;
	}
	
	public static Game copy(Game game) {
		Game copy = new Game(); 
		copy.setId(game.getId());
		copy.setChampionId(game.getChampionId());
		copy.setRoleName(game.getRoleName());
		copy.setLaneName(game.getLaneName());
		copy.setQueueId(game.getQueueId());
		return copy; 
	}

	public Integer getQueueId() {
		return queueId;
	}

	public void setQueueId(Integer queueId) {
		this.queueId = queueId;
	}

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getChampionId() {
		return championId;
	}
	public void setChampionId(Integer championId) {
		this.championId = championId;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedOn() {
		return createdOn;
	}

	public String getLaneName() {
		return laneName;
	}

	public void setLaneName(String laneName) {
		this.laneName = laneName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Game game = (Game) o;
		return id.equals(game.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
