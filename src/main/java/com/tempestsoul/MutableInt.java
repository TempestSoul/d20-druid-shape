package com.tempestsoul;

/**
 * Mutable integer used for counting in maps. Included to avoid dependency on 
 * Apache's commons-lang, since I'm not using a dependency-manager.
 * Method arguments are named for mathematical terms.
 * @author TempestSoul
 *
 */
public class MutableInt {
	int value = 1;
	public MutableInt() {}
	public MutableInt(int value) {
		this.value = value;
	}
	public void inc() { ++value; }
	public void dec() { --value; }
	public void add(int addend) { value += addend; }
	public void sub(int subtrahend) { value -= subtrahend; }
	public int getValue() { return value; }
}
