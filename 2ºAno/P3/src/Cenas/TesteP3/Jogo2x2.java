//João Gameiro  Nº93097
//P3-ECT-UA

package Cenas.TesteP3;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;

public class Jogo2x2 extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel1, panel2;
	private JButton b[] = new JButton[4];
	private String JBlabels[] = {"1", "2", "3", "4"};
	private JTextField jtxt1;
	
	//Construtor 
	public Jogo2x2(String nome)
	{
		super(nome);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(2,2));
		panel1.setLayout(new BorderLayout());
		panel1.add(panel2, BorderLayout.CENTER);
		for(int i=0;i<b.length;i++) {
			b[i] = new JButton(JBlabels[i]);
			panel2.add(b[i]);
			b[i].addActionListener(this);
		}
		jtxt1 = new JTextField(10);
		panel1.add(jtxt1, BorderLayout.SOUTH);
		panel1.setOpaque(true);
		setSize(400, 400);
		setContentPane(panel1);
		setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		jtxt1.setText(((JButton)(e.getSource())).getText());
	}

}
