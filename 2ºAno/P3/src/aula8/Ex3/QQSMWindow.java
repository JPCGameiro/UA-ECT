//João Gameiro  Nº93097
//P3-ECT-UA

package aula8.Ex3;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;


public class QQSMWindow implements ActionListener{
	
	protected QQSMGame jogo = new QQSMGame();
	protected JFrame frame;
	
	//Criar a janela de jogo
	public void createWindow()
	{
		//Frame
		frame = new JFrame("Quem Quer ser Milionário");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 700);
		
		//Painel
		JPanel panel = new JPanel();
		frame.setContentPane(panel);
		
		JLabel money = new JLabel(jogo.money()+" €  \n");
		panel.add(money);
		frame.setVisible(true);
		
		JButton confirm = new JButton("Confirmar");
		confirm.addActionListener(this);
		JButton quit = new JButton("Desistir");
		quit.addActionListener(this);
		panel.add(confirm);
		panel.add(quit);
	}

	@Override public void actionPerformed(ActionEvent event) {	
		JButton but = (JButton) event.getSource();
		
	}
}
