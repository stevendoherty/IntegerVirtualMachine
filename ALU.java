package vm2;

/*
 * 
 * A simple class the executes arithmetic
 * 
 */

public class ALU {
	private int out;
	
	public ALU() {
		out=0;
	}
	
	public int increment(int a) {
		out=a+1;
		return out;
	}
	public int decrement(int a) {
		out=a-1;
		return out;
	}
	public int rShift(int a) {
		out=a/10;
		return out;
	}
	public int lShift(int a) {
		out=a*10;
		return out;
	}
	public int add(int a, int b) {
		out=a+b;
		return out;
	}
	public int subtract(int a, int b) {
		out=b-a;
		return out;
	}
	public int multiply(int a, int b) {
		out=b*a;
		return out;
	}
	public int divide(int a, int b) {
		out=b/a;
		return out;
	}
	
}
