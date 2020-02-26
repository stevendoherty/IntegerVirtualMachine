package vm2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Machine {


	private Command command;
	private Register one;
	private Register two;
	private Register input;
	ArrayList<Memory> memory=new ArrayList<Memory>();
	//public Memory[] memory;
	public int memorySlots;
	private int currentSlot=0;
	private ALU alu=new ALU();
	private boolean manual=false;
	View view=null;
	FileWriter writer;
	

	public Machine() {
		try {
			writer=new FileWriter("log.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		memorySlots=1;
		memory.add(new Memory());
		one=new Register();
		two=new Register();
		input=new Register();
	}

	public Machine(int memorySlots) {
		try {
			writer=new FileWriter("log.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.memorySlots=memorySlots;
		
		for (int i=0; i<memorySlots; i++) {
			memory.add(new Memory());
		}
		one=new Register();
		two=new Register();
		input=new Register();
	}

	public Machine(ArrayList<Memory> memory){
		try {
			writer=new FileWriter("log.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.memorySlots=memory.size();
		this.memory=memory;
		one=new Register();
		two=new Register();
		input=new Register();
	}

	//loads data file into the machine memory
	public void initMemory(String fileName, int memorySlot){
		try {
			File f=new File(fileName);
			Scanner file=new Scanner(f);
			while(file.hasNext()) {
				memory.get(memorySlot).readMemFile(file);
				memorySlot++;
				if (memorySlot>=memory.size()) {
					System.out.println("Not enough memory");
					break;
				}
			}
			memorySlot=0;
		}catch(FileNotFoundException e) {
			e.printStackTrace();
			System.exit(5001);
		}

	}

	private void writeRegister(int register, int data) {
		switch (register) {
		case 1: one.write(data); view.setOne(data); break;
		case 2: two.write(data); view.setTwo(data); break;
		case 3: input.write(data); view.setinput(data); break;
		}		
	}
	private void writeMemory(int location, int data) {
		memory.get(currentSlot).write(location, data);
		view.populateMemoryBox();
	}
	private void writeView(int data) {
		view.setOut(data);
	}

	public int runCommand() {
		if(manual) {
			command=new Command(view.getinput());
		}else {
			command=new Command(memory.get(currentSlot).execute());
		}
		
		try {
			writer.write(command.toString()+"\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int outputData=-1;
		int a=0;
		int b=0;

		switch (command.getInputType()) {
		case 0:
			a=command.getA();
			b=command.getB();
			break;
		case 1:
			a=memory.get(currentSlot).read(command.getA());
			b=memory.get(currentSlot).read(command.getB());
			break;
		case 2:
			a=command.getA();
			b=memory.get(currentSlot).read(command.getB());
			break;
		case 3:
			a=memory.get(currentSlot).read(command.getA());
			b=command.getB();
			break;
		case 4:
			a=one.read();
			b=two.read();
			break;
		case 5:
			a=memory.get(currentSlot).read(command.getA());
			b=one.read();
			break;
		case 6:
			a=command.getA();
			b=one.read();
			break;
		}

		switch (command.getCommandType()) {
		case 0:
			try {
				writer.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return 0;
		case 1:
			outputData=alu.increment(a);
			break;
		case 2:
			outputData=alu.decrement(a);
			break;
		case 3:
			outputData=alu.add(a, b);
			break;
		case 4:
			outputData=alu.subtract(a, b);
			break;
		case 5:
			outputData=alu.multiply(a, b);
			break;
		case 6:
			outputData=alu.divide(a, b);
			break;
		case 7:
			outputData=alu.lShift(a);
			break;
		case 8:
			outputData=alu.rShift(a);
			break;
		case 11:
			outputData=memory.get(currentSlot).read(a);
			break;
		case 12:
			outputData=memory.get(currentSlot).write(a, b);
			view.populateMemoryBox();
			break;
		case 13:
			memory.get(currentSlot).goTo(a);
			outputData=memory.get(currentSlot).read(a);
			break;
		case 14:
			try {
				Thread.sleep((long)a*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		case 17:
			if (!(a>b)) {
				outputData=memory.get(currentSlot).incrementExecutionLine();
			}else {
				outputData=0;
			}
			break;
		case 18:
			if (!(a<=b)) {
				outputData=memory.get(currentSlot).incrementExecutionLine();
			}else {
				outputData=0;
			}
			break;
		case 20:
			manual=!manual;
			view.setManual(manual);
			break;
		case 21:
			currentSlot=a;
			break;
		case 22:
			memory.add(new Memory());
			memorySlots++;
			outputData=memorySlots;
			view.addMemorySlot(memorySlots-1);
			break;
		case 23:
			outputData=memory.get(two.read()).read(a);
			break;
		case 24:
			outputData=memory.get(two.read()).write(a, b);
			view.populateMemoryBox();
			break;
		}


		switch (command.getOutputType()) {
		case 0:
			writeView(outputData);
			break;
		case 4:
			writeMemory(command.getA(), outputData);
			break;
		case 5:
			writeMemory(command.getB(), outputData);
			break;
		case 6:
			writeMemory(memory.get(currentSlot).read(command.getA()), outputData);
			break;
		case 7:
			writeMemory(memory.get(currentSlot).read(command.getB()), outputData);
			break;
		default:
			writeRegister(command.getOutputType(), outputData);
			break;
		}

		return 1;

	}

	public String printRegisters() {
		String out="";
		out=out+one.read()+"\n"+two.read()+"\n"+input.read();

		return out;
	}


	public int run(){
		int exit=1;
		view=new View(this);
		while(exit!=0) {
			if(manual == false) {
				exit=runCommand();
			} else {
				try {
					Thread.sleep(1);
				}catch(InterruptedException e) {
					e.printStackTrace();
					System.exit(5001);
				}
			}
		}
		return one.read();

	}



	public static void main(String[] args){
		Machine computer=new Machine(3);
		computer.initMemory("data", 0);
		int out=computer.run();
		System.out.println(out);


	}

}
