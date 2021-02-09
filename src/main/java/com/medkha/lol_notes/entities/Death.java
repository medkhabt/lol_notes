package com.medkha.lol_notes.entities;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;



@Entity
public class Death {
	
	@Id
	@GeneratedValue(generator = Constants.ID_GENERATOR)
	private Long id; 
	
	@NotNull
	private int minute; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	private @Valid Reason reasonOfDeath ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GAME_ID", nullable = false)
	private @Valid Game game; 
	
	protected Death() {}
	
	public Death(int minute, Game game) { 
		this.minute = minute; 
		this.game = game; 
		this.reasonOfDeath = new Reason(); 
	}
	
	public Death(int minute, Reason reasonOfDeath, Game game) {
		this.minute = minute; 
		this.reasonOfDeath = reasonOfDeath; 
		this.game = game; 
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public Reason getReasonOfDeath() {
		return reasonOfDeath;
	}

	public void setReasonOfDeath(Reason reasonOfDeath) {
		this.reasonOfDeath = reasonOfDeath;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Long getId() {
		return id;
	}

	
	
}
