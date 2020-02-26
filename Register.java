package vm2;



public class Register {
	private int mem;//array of ints that stores data

	public Register() {//constructor
		mem=0;
	}
	public int read() {
		return mem;
	}
	public int write(int data) {
		mem=data;
		return mem;
	}
}







