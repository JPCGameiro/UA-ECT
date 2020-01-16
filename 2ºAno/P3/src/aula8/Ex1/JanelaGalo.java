//João Gameiro  Nº93097
//P3-ECT-UA

package aula8.Ex1;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.*;

public class JanelaGalo implements ActionListener{
	
	protected Jogo game;
	protected JPanel panel = new JPanel();
	protected JToggleButton[] btn = new JToggleButton[9];
	
	//Construtor
	public JanelaGalo(Jogo game)
	{
		this.game = game;
	}
	
	//Janela do Jogo
	public void criarJanela() {
		int i;
		JFrame frame = new JFrame("Jogo do Galo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(400, 400);
		Font myFont = new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT | Font.BOLD , 50);
		panel.setLayout(new  GridLayout(3,3));
		for(i=0;i<btn.length;i++) {
			btn[i] = new JToggleButton();
			btn[i].addActionListener(this);
			btn[i].setFont(myFont);
			panel.add(btn[i]);
		}
		frame.setContentPane(panel);
		
		

	}

	//Ações ao pressionar os botões (incluindo verificação de vencedor ou empate)
	@Override public void actionPerformed(ActionEvent e) 
	{		
		JToggleButton but = (JToggleButton) e.getSource();
		but.setEnabled(false);
	
		if(but==btn[0])
			game.fazerJogada(0,0);
		else if(but==btn[1])
			game.fazerJogada(0, 1);
		else if(but==btn[2])
			game.fazerJogada(0, 2);
		else if(but==btn[3])
			game.fazerJogada(1, 0);
		else if(but==btn[4])
			game.fazerJogada(1, 1);
		else if(but==btn[5])
			game.fazerJogada(1, 2);
		else if(but==btn[6])
			game.fazerJogada(2, 0);
		else if(but==btn[7])
			game.fazerJogada(2, 1);
		else if(but==btn[8])
			game.fazerJogada(2, 2);
		
		but.setText(game.ultimoJogador());
		
		
		if(game.jogoTerminou().equals(game.JogadorX())) {
			JOptionPane.showMessageDialog(panel, "JogadorX Venceu");
			System.exit(0);
		}
		else if(game.jogoTerminou().equals(game.JogadorO())) {
			JOptionPane.showMessageDialog(panel, "JogadorO Venceu");
			System.exit(0);
		}
		else if(game.jogoTerminou().equals("Empate")) {
			JOptionPane.showMessageDialog(panel, "Empate");
			System.exit(0);
		}		
			
	}
}
