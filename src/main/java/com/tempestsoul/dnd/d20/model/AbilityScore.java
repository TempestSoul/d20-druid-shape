package com.tempestsoul.dnd.d20.model;

public class AbilityScore {
	
	private Integer score;
	
	public AbilityScore() {
		score = 10;
	}
	
	public AbilityScore(Integer score) {
		if(score < 0)
			throw new IllegalArgumentException("Ability scores must be zero or a positive integer");
		this.score = score;
	}
	
	public Integer getScore() {
		return score;
	}
	
	public void setScore(Integer score) {
		if(score < 0)
			throw new IllegalArgumentException("Ability scores must be zero or a positive integer");
		this.score = score;
	}
	
	public Integer getModifier() {
		return ((score >= 10 ? score : score-1) - 10) / 2;
	}
	
	@Override
	public String toString() {
		// two integers in sequence, with the second always having a sign
		return String.format("%d(%+d)", getScore(), getModifier());
	}
}
