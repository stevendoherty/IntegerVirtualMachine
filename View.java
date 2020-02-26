package vm2;

import static javax.swing.GroupLayout.Alignment.BASELINE;

import java.util.List;

import java.awt.event.*;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


public class View extends JFrame{
	private JLabel memorySlot=new JLabel("Memory Slot:");
	private JLabel memory=new JLabel("Memory:");
	private JLabel one=new JLabel("Register One:");
	private JLabel two=new JLabel("Register Two:");
	private JLabel input=new JLabel("Input Register:");
	private JLabel output=new JLabel("Output Register:");

	JButton cancel = new JButton("Cancel");

	private JTextField oneText = new JTextField();
	private JTextField twoText = new JTextField();
	private JTextField inputText = new JTextField();
	private JTextField outText = new JTextField();
	private JButton execute = new JButton("Execute");
	JScrollPane memoryScroll = new JScrollPane();
	private Machine machine=null;

	JList<String> memoryList = new JList<String>();
	JComboBox<String> slotComboBox=new JComboBox<String>();

	public View(Machine mach) {
		//sets frame
		setSize(700, 400);
		setTitle("VM");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		machine=mach;
		initUI(machine.memorySlots);
		
	}
	
	public void setManual(boolean enable) {
		execute.setEnabled(enable);
	}
	
	public void setOne(int i) {
		oneText.setText(""+i);
	}
	public void setTwo(int i) {
		twoText.setText(""+i);
	}
	public void setinput(int i) {
		inputText.setText(""+i);
	}
	public void setOut(int i) {
		outText.setText(""+i);
	}
	public int getinput() {
		String input=inputText.getText();
		return Integer.parseInt(input);
	}

	private void initUI(int slots){      
		//makes new panel
		JPanel panel = new JPanel();
		panel.setBorder(null);
		oneText.setEditable(false);
		twoText.setEditable(false);
		outText.setEditable(false);
		execute.setEnabled(false);
		
		//creates group layout
		GroupLayout gl = new GroupLayout(panel);
		panel.setLayout(gl);
		populateMemoryComboBox(slots);
		populateMemoryBox();
		execute.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				machine.runCommand();
				inputText.setText("");
			}
			
		});
		slotComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				populateMemoryBox();
			}
			
		});

		gl.setAutoCreateGaps(true);
		gl.setAutoCreateContainerGaps(true);


		//Create the layout
		gl.setHorizontalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(memorySlot)
						.addComponent(slotComboBox)
						.addComponent(execute)
						.addComponent(memory)
						.addComponent(memoryScroll))
				.addGroup(gl.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(one)
						.addComponent(oneText)
						.addComponent(two)
						.addComponent(twoText)
						.addComponent(input)
						.addComponent(inputText)
						.addComponent(output)
						.addComponent(outText)
						));

		gl.setVerticalGroup(gl.createSequentialGroup()
				.addGroup(gl.createParallelGroup(BASELINE)
						.addComponent(memorySlot)
						.addComponent(one))
				.addGroup(gl.createParallelGroup(BASELINE)
						.addComponent(slotComboBox)
						.addComponent(oneText))
				.addGroup(gl.createParallelGroup(BASELINE)
						.addComponent(memory)
						.addComponent(two))
				.addGroup(gl.createParallelGroup(BASELINE)
						.addComponent(memoryScroll)
						.addComponent(twoText))
				.addGroup(gl.createParallelGroup(BASELINE)
						.addComponent(input))
				.addGroup(gl.createParallelGroup(BASELINE)
						.addComponent(inputText))
				.addGroup(gl.createParallelGroup(BASELINE)
						.addComponent(output))         
				.addGroup(gl.createParallelGroup(BASELINE)
						.addComponent(execute)
						.addComponent(outText)
						));

		

		//makes visible
		add(panel);      
		setVisible(true);


	}
	private void populateMemoryComboBox(int slots) {
		String[] comboText=new String[slots];

		for (int i=0; i<slots; i++) {
			comboText[i]=""+i;
		}
		slotComboBox=new JComboBox(comboText);
	}
	public void addMemorySlot(int i) {
		slotComboBox.addItem(""+i);
	}

	public void populateMemoryBox() {
		DefaultListModel<String> memoryPrintOut = new DefaultListModel<String>();
		int i=0;
		int[] m=machine.memory.get(slotComboBox.getSelectedIndex()).getMemory();
		for(int command: m) {
			String s=i+":"+ command;
			if (i<10) {
				s=i+"  :"+ command;
			}
			i++;
			memoryPrintOut.addElement(s);
		}
		memoryList.setModel(memoryPrintOut);
		memoryScroll.setViewportView(memoryList);
		memoryScroll.revalidate();
		memoryScroll.repaint();


	}


}
