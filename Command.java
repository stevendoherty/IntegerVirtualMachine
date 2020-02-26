package vm2;

/*
 * 
 * This class breaks down the command into an easy to use object
 * 
 * 
 */

public class Command {
	private int a;
	private int b;
	private int outputType;
	private int inputType;
	private int original;
	private int commandType;
	
	public Command(int command) {
		original=command;
		b=command%100;
		command=command/100;
		a=command%100;
		command=command/100;
		outputType=command%10;
		command=command/10;
		inputType=command%10;
		command=command/10;
		commandType=command;
	}
	
	public String toString() {
		return ""+original;
	}
	
	public int getA(){
		return a;
	}
	public int getB(){
		return b;
	}
	public int getOutputType(){
		return outputType;
	}
	public int getInputType(){
		return inputType;
	}
	public int getOriginal(){
		return original;
	}
	public int getCommandType() {
		return commandType;
	}
	
}
