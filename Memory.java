package vm2;


import java.util.Scanner;

/*
 * 
 * this class represents the machine memory
 * 
 */

public class Memory {
	private int executionLine=0;//current line
	private int[] mem=new int[100];//array of ints that stores data

	public Memory() {//constructor
		for (int i=0; i<=99; i++) {
			mem[i]=0;
		}
	}

	public void readMemFile(Scanner f) {//takes an input and reads from it to fill memory
		int i=0;
		while(f.hasNextInt()) {
			mem[i]=f.nextInt();
			
			i++;
			if(i==100) {
				break;
			}
		}
	}	
	public int execute() {//just outputs the line of memory to execute
		int i=mem[executionLine];
		executionLine++;
		return i;
	}
	public int resetExecLine() {//resets execution line
		executionLine=0;
		return executionLine;
	}
	public int incrementExecutionLine() {
		executionLine++;
		return executionLine;
	}
	public int read(int location) {
		return mem[location];
	}
	public int write(int location, int data) {
		mem[location]=data;
		return mem[location];
	}
	public int getLocation() {
		return executionLine;
	}
	public int[] getMemory() {
		return mem;
	}
	public int goTo(int location) {
		executionLine=location;
		return executionLine;
	}


}







