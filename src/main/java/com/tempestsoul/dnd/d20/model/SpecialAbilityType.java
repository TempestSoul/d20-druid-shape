package com.tempestsoul.dnd.d20.model;

public enum SpecialAbilityType {
	NATURAL {
		@Override
		public boolean isDispelled() { return false; }
		@Override
		public boolean isCancelledBySpellResistance() { return false; }
		@Override
		public boolean isSuppressedByAntimagic() { return false; }
		@Override
		public boolean doesProvokeAttackOfOpportunity() { return false; }
	}, EXTRAORDINARY {
		@Override
		public boolean isDispelled() { return false; }
		@Override
		public boolean isCancelledBySpellResistance() { return false; }
		@Override
		public boolean isSuppressedByAntimagic() { return false; }
		@Override
		public boolean doesProvokeAttackOfOpportunity() { return false; }
	}, SPELL_LIKE {
		@Override
		public boolean isDispelled() { return true; }
		@Override
		public boolean isCancelledBySpellResistance() { return true; }
		@Override
		public boolean isSuppressedByAntimagic() { return true; }
		@Override
		public boolean doesProvokeAttackOfOpportunity() { return true; }
	}, SUPERNATURAL{
		@Override
		public boolean isDispelled() { return false; }	
		@Override
		public boolean isCancelledBySpellResistance() { return false; }	
		@Override
		public boolean isSuppressedByAntimagic() { return true; }
		@Override
		public boolean doesProvokeAttackOfOpportunity() { return true; }
	};
	
	public abstract boolean isDispelled();
	public abstract boolean isCancelledBySpellResistance();
	public abstract boolean isSuppressedByAntimagic();
	public abstract boolean doesProvokeAttackOfOpportunity();

	public static SpecialAbilityType fromAbbrev(String abbrev) {
		if (abbrev == null || abbrev.isEmpty() || "Ex".equals(abbrev)) {
			return EXTRAORDINARY;
		} else if ("Su".equals(abbrev)) {
			return SUPERNATURAL;
		} else if ("Sp".equals(abbrev)) {
			return SPELL_LIKE;
		} else {
			return null;
		}
	}
}
