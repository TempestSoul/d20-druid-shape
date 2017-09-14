package com.tempestsoul.dnd.d20.model;

public enum SpecialAbilityType {
	NATURAL(null) {
		@Override
		public boolean isDispelled() { return false; }
		@Override
		public boolean isCancelledBySpellResistance() { return false; }
		@Override
		public boolean isSuppressedByAntimagic() { return false; }
		@Override
		public boolean doesProvokeAttackOfOpportunity() { return false; }
	}, EXTRAORDINARY("Ex") {
		@Override
		public boolean isDispelled() { return false; }
		@Override
		public boolean isCancelledBySpellResistance() { return false; }
		@Override
		public boolean isSuppressedByAntimagic() { return false; }
		@Override
		public boolean doesProvokeAttackOfOpportunity() { return false; }
	}, SPELL_LIKE("Sp") {
		@Override
		public boolean isDispelled() { return true; }
		@Override
		public boolean isCancelledBySpellResistance() { return true; }
		@Override
		public boolean isSuppressedByAntimagic() { return true; }
		@Override
		public boolean doesProvokeAttackOfOpportunity() { return true; }
	}, SUPERNATURAL("Su"){
		@Override
		public boolean isDispelled() { return false; }	
		@Override
		public boolean isCancelledBySpellResistance() { return false; }	
		@Override
		public boolean isSuppressedByAntimagic() { return true; }
		@Override
		public boolean doesProvokeAttackOfOpportunity() { return true; }
	};

	private String abbrev;
	SpecialAbilityType(String abbrev) {
		this.abbrev = abbrev;
	}
	public abstract boolean isDispelled();
	public abstract boolean isCancelledBySpellResistance();
	public abstract boolean isSuppressedByAntimagic();
	public abstract boolean doesProvokeAttackOfOpportunity();
	public String getAbbrev() { return abbrev; }

	public static SpecialAbilityType fromAbbrev(String abbrev) {

		for (SpecialAbilityType type : values()) {
			if (type.abbrev == abbrev)
				return type;
		}
		return null;
	}
}
