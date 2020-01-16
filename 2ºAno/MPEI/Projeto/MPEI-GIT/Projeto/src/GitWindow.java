

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GitWindow {
	
	private GitSystem g;
	private JFrame frame;
	private JFrame frame0;
	private JFrame frame1;
	private JFrame frame2;
	private JFrame frame3;
	private JButton btn0 = new JButton("ADD FILES");
	private JButton btn1 = new JButton("REMOVE FILES");
	private JButton btn2 = new JButton("SEARCH FILES");
	private JButton btn3 = new JButton("LIST FILES");
	private JButton btn4 = new JButton("REMOVE ALL FILES");
	private JButton btn5 = new JButton("FIND SIMILAR FILES");
	private JButton btn6 = new JButton("EXIT");
	private JTextField getFile = new JTextField(20);		//Textfield da janela adicionar ficheiros
	private JTextField remFile = new JTextField(20);		//Textfield da janela remover ficheiros
	private JTextField rchFile = new JTextField(20);		//Textfield da janela pesquisar ficheiros
	private JTextField simFile = new JTextField(20);		//TextField para pesquizar ficheiros similares
	
	//Construtor
	public GitWindow()
	{
		g = new GitSystem();
	}
	
	//----------------------------------------//
	//----------------------------------------//
	//Janela principal do programa			  //
	public void createWindow()
	{
		//Frame base da aplicação
		frame = new JFrame("GitSystem");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(450, 500);
		frame.setVisible(true);	
		
		//Painel header
		JPanel header = new JPanel();
		header.setBackground(Color.white);
		JLabel l0 = new JLabel ("GitSystem");
		Font font = new Font("Marker Felt", Font.ITALIC, 35);
		l0.setFont(font);
		header.add(l0);
		frame.add(header, BorderLayout.PAGE_START);
		
		//Painel com os botões
		JPanel buttons = new JPanel();
		btn0.setPreferredSize(new Dimension(350, 40));
		btn1.setPreferredSize(new Dimension(350, 40));
		btn2.setPreferredSize(new Dimension(350, 40));
		btn3.setPreferredSize(new Dimension(350, 40));
		btn4.setPreferredSize(new Dimension(350, 40));
		btn5.setPreferredSize(new Dimension(350, 40));
		btn6.setPreferredSize(new Dimension(350, 40));
		buttons.add(btn0);
		buttons.add(btn1);
		buttons.add(btn2);
		buttons.add(btn3);
		buttons.add(btn4);
		buttons.add(btn5);
		buttons.add(btn6);
		frame.add(buttons, BorderLayout.CENTER);
		
		//Painel Roda pé
		JPanel footer = new JPanel();
		footer.setBackground(Color.lightGray);
		JLabel lf = new JLabel("Universidade de Aveiro - MPEI");
		Font font0 = new Font("Marker Felt", Font.BOLD, 15);
		lf.setFont(font0);
		footer.add(lf);
		frame.add(footer, BorderLayout.PAGE_END);
		
		
		
		//Abrir a janela para adicionar ficheiros
		btn0.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addFilesWindow();
			}
		});
		//Abrir a janela para remover ficheiros
		btn1.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeFilesWindow();
			}
		});
		//Abrir a janela para pesquisar ficheiros
		btn2.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFilesWindow();
			}
		});
		//Listar ficheiros adicionados
		btn3.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(g.list().isEmpty())
					JOptionPane.showMessageDialog(frame, "No files were added yet");
				else {
					JOptionPane.showMessageDialog(frame, g.listAllFiles());
				}
			}
		});
		//Remover todos os ficheiros adicionados até ao momento
		btn4.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.removeAllFiles();
				JOptionPane.showMessageDialog(frame, "All files were removed");
			}
		});
		//Abrir a janela para pesquisar ficheiros similares
		btn5.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchSimilarFilesWindow();
			}
		});
		//Fechar a aplicação
		btn6.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "System will shut down");
				System.exit(0);
			}
		});
	}
	//-----------------------------------------//
	//-----------------------------------------//
	//Janela para adiconar ficheiros ao sistema//
	public void addFilesWindow()
	{
		frame0 = new JFrame("AddFiles");
		frame0.setSize(300, 150);
		frame0.setVisible(true);
		
		JPanel action = new JPanel();
		JLabel jlFile = new JLabel("Insert a valid file name: ");
		JButton add  = new JButton("Add File");	
		action.add(jlFile);
		action.add(getFile);
		action.add(add);
		frame0.add(action, BorderLayout.CENTER);
		
		//Adicionar um ficheiro e mostrar a mensagem correspondente (sucesso ou erro)
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i=-1;
				if(getFile.getText().equals(""))
					JOptionPane.showMessageDialog(frame0, "ERROR: Empty field");
				else
					try {
						i = g.addFile(getFile.getText());
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					if(i==0)
						JOptionPane.showMessageDialog(frame0, "File added sucessfully");
					else if(i==3)
						JOptionPane.showMessageDialog(frame0, "WARNING: There are similar files in the system");
					else if(i==2)
						JOptionPane.showMessageDialog(frame0, "ERROR: File already added");
					else
						JOptionPane.showMessageDialog(frame0, "ERROR: File does not exist");
				getFile.setText("");
			}
		});
		
	}
	
	
	//----------------------------------------//
	//----------------------------------------//
	//Janela para remover ficheiros ao sistema//
	public void removeFilesWindow()
	{
		frame1 = new JFrame("removeFiles");
		frame1.setSize(300, 150);
		frame1.setVisible(true);
		
		JPanel action = new JPanel();
		JLabel jlFile = new JLabel("Insert a valid file name: ");
		JButton rem  = new JButton("Remove File");
		action.add(jlFile);
		action.add(remFile);
		action.add(rem);
		frame1.add(action, BorderLayout.CENTER);

		//Remover um ficheiro e mostrar a mensagem correspondente (sucesso ou erro)
		rem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(remFile.getText().equals(""))
					JOptionPane.showMessageDialog(frame1, "ERROR: Empty field");
				else if(g.list().isEmpty())
					JOptionPane.showMessageDialog(frame1, "ERROR: No files were added yet");
				else if(g.removeFile(remFile.getText()))
					JOptionPane.showMessageDialog(frame1, "File removed sucessfully");
				else
					JOptionPane.showMessageDialog(frame1, "ERROR: File does not exist");
				remFile.setText("");
			}
		});
			
	}
	
	//------------------------------------------//
	//------------------------------------------//
	//Janela para pesquisar ficheiros no sistema//
	public void searchFilesWindow()
	{
		frame2 = new JFrame("removeFiles");
		frame2.setSize(300, 150);
		frame2.setVisible(true);
				
		JPanel action = new JPanel();
		JLabel jlFile = new JLabel("Insert a valid file name: ");
		JButton rch  = new JButton("Search File");
		action.add(jlFile);
		action.add(rchFile);
		action.add(rch);
		frame2.add(action, BorderLayout.CENTER);
				
		//Pesquisar um ficheiro e mostrar a mensagem correspondente (sucesso ou erro)
		rch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rchFile.getText().equals(""))
					JOptionPane.showMessageDialog(frame2, "ERROR: Empty field");
				else if(g.list().isEmpty())
					JOptionPane.showMessageDialog(frame2, "ERROR: No files were added yet");
				else
					try {
						if(!g.searchFile(rchFile.getText()).equals("")) {							
							JTextArea textArea = new JTextArea(30, 55);
						    textArea.setText(g.searchFile(rchFile.getText()));
						    textArea.setEditable(false);
						    
						    JScrollPane scrollPane = new JScrollPane(textArea);				    
						    JOptionPane.showMessageDialog(frame2, scrollPane);
						}
						else
							JOptionPane.showMessageDialog(frame2, "ERROR: File does not exist");
					} catch (HeadlessException | IOException e2) {						
						e2.printStackTrace();						
					}
				rchFile.setText("");
			}
		});	
	}	
	
	
	//------------------------------------------//
	//------------------------------------------//
	//Janela para pesquisar ficheiros similares //
	public void searchSimilarFilesWindow()
	{
		frame3 = new JFrame("findSimilar");
		frame3.setSize(300, 150);
		frame3.setVisible(true);
				
		JPanel action = new JPanel();
		JLabel jlFile = new JLabel("Insert a valid file name: ");
		JButton sim  = new JButton("Search File");
		action.add(jlFile);
		action.add(simFile);
		action.add(sim);
		frame3.add(action, BorderLayout.CENTER);
		
		sim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(simFile.getText().equals(""))
					JOptionPane.showMessageDialog(frame3, "ERROR: Empty field");
				else if(g.list().isEmpty())
					JOptionPane.showMessageDialog(frame3, "ERROR: No Files were added yet");
				else
					try {
						if(!g.searchSimilarFiles(simFile.getText()).equals(""))
							JOptionPane.showMessageDialog(frame3, g.searchSimilarFiles(simFile.getText()));
						else
							JOptionPane.showMessageDialog(frame3, "ERROR: File does not exist");
					} catch (HeadlessException | IOException e1) {
						e1.printStackTrace();
					}
				simFile.setText("");
			}
			
		});
	}

}
