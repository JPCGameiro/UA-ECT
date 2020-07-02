import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.awt.event.*;
import java.lang.Math.*;
import java.io.*;


public class KuestInterface{
    private JFrame frame;
    private JPanel questions;
    private JTextField questionText;
    private JCheckBox answerBox;
    private JPanel answers;
    private JPanel advance;
    private JButton next;
    private int answer_points=0;
    private boolean respondeu;
    private Timer timer;
    private int time=0;
    private int time_limit=0;
    private JLabel displayTime;
    
    //para o savefile
    private static ArrayList<Integer> times = new ArrayList<>();
    private static ArrayList<String> respostas = new ArrayList<>();
    private static ArrayList<String> perguntas = new ArrayList<>();
    private static ArrayList<Integer> pontos = new ArrayList<>();

    public KuestInterface(Question kuest ,String type ,int time_limit){
        frame = new JFrame("Kuestionair");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750,500);
        frame.setLayout(new BorderLayout());
        
        answer_points=0;
        respondeu=false;
        time = time_limit;
        this.time_limit = time_limit;

        questions = new JPanel();
        advance = new JPanel(new FlowLayout());
        answers = new JPanel();

        if(type.equals("mc")){
            displayMC(kuest);
        }else if(type.equals("vf")){
            displayVF(kuest);
        }else if(type.equals("txt")){
            displayTXT(kuest);
        }

        timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                time--;
                if(time<0){
                    respondeu=true;
                    timer.stop();
                    frame.dispose(); 
                    return;
                }
                displayTime.setText(String.valueOf(time));
            }
        });
        timer.start();

        displayTime = new JLabel(String.valueOf(time));

        advance.add(next);
        advance.add(displayTime);
        
        frame.add(questions, BorderLayout.NORTH);
        frame.add(answers, BorderLayout.CENTER);
        frame.add(advance, BorderLayout.SOUTH);  
        frame.setVisible(true);
    }

    public KuestInterface(Question kuest ,String type){
             
        frame = new JFrame("Kuestionair");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750,500);
        frame.setLayout(new BorderLayout());
        answer_points=0;
        respondeu=false;
        time=0;
        if(type.equals("mc")){
            displayMC(kuest);
        }else if(type.equals("vf")){
            displayVF(kuest);
        }else if(type.equals("txt")){
            displayTXT(kuest);
        }
        
        timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                time++;
                displayTime.setText(String.valueOf(time));
            }
        });
        timer.start();

        displayTime = new JLabel(String.valueOf(time));

        advance.add(next);
        advance.add(displayTime);
        
        frame.remove(questions);
        frame.remove(answers);
        frame.remove(advance);
        
        frame.add(questions, BorderLayout.NORTH);
        frame.add(answers, BorderLayout.CENTER);
        frame.add(advance, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }

    private void displayMC(Question kuest){
        questions = new JPanel();
        advance = new JPanel(new FlowLayout());
        ArrayList<JCheckBox> buttons = new ArrayList<>();
        
        HashMap<String,Integer> kuest_answers = kuest.getRespostas();
        answers = new JPanel(new GridLayout(kuest_answers.size(),1));
        for(String resp :kuest_answers.keySet()){
            answerBox = new JCheckBox(resp);
            
            answerBox.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    answer_points = kuest_answers.get(resp);
                }
            });
            buttons.add(answerBox);
            
        }

        for(JCheckBox box : buttons){
            box.addItemListener(new ItemListener(){
            
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED){
                        for(JCheckBox box2 : buttons){
                            if(!(buttons.indexOf(box2) == buttons.indexOf(box)))
                                box2.setEnabled(false);

                        }
                    }
                    if(e.getStateChange() == ItemEvent.DESELECTED){
                        for(JCheckBox box2 : buttons){
                            box2.setEnabled(true);
                        }
                    }
                }
            });
            answers.add(box);
        }
        questionText = new JTextField(kuest.getPergunta());
        questionText.setEditable(false);
        Font font = new Font("SansSerif", Font.BOLD, 15);
        questionText.setFont(font);
        questions.add(questionText);

        times.add(0);
        perguntas.add(kuest.getPergunta());
        respostas.add("didnt answer");
        pontos.add(0);

        next = new JButton("next");
        next.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String resposta="didnt answer";
                timer.stop();

                pontos.remove(pontos.get(pontos.size()-1));
                times.remove(times.get(times.size()-1));
                respostas.remove(respostas.get(respostas.size()-1));
                
                for(JCheckBox box : buttons){
                    if(box.isSelected()){
                       resposta = box.getText();
                    }
                }

                respostas.add(resposta);
                pontos.add(answer_points);
                times.add(Math.abs(time_limit-time));
                
                respondeu = true;
                frame.dispose();
            }
        });
    }

    private void displayVF(Question kuest){
        questions = new JPanel();
        answers = new JPanel(new GridLayout(2,1));
        advance = new JPanel(new FlowLayout());
        HashMap<String,Integer> kuest_answers = kuest.getRespostas();
        String[] ans = kuest_answers.keySet().toArray(new String[kuest_answers.size()]);
        String answer = ans[(int)Math.random()*3];
        
        JCheckBox answerBox1 = new JCheckBox("true");
        JCheckBox answerBox2 = new JCheckBox("false");

        answerBox1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                answer_points = kuest_answers.get(answer);
            }
        });
       

        answerBox2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                answer_points = 100 - kuest_answers.get(answer);
            }
        });
    
        

        answerBox1.addItemListener(new ItemListener(){
        
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    answerBox2.setEnabled(!answerBox1.isSelected());
                }
                if(e.getStateChange() == ItemEvent.DESELECTED){
                    answerBox2.setEnabled(true);
                }
            }
        });

        answerBox2.addItemListener(new ItemListener(){
        
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    answerBox1.setEnabled(!answerBox2.isSelected());
                }
                if(e.getStateChange() == ItemEvent.DESELECTED){
                    answerBox1.setEnabled(true);
                }
            }
        });
        
        answers.add(answerBox1);
        answers.add(answerBox2);

        questionText = new JTextField(kuest.getPergunta()+" "+answer);
        questionText.setEditable(false);
        Font font = new Font("SansSerif", Font.BOLD, 15);
        questionText.setFont(font);
        questions.add(questionText);

        times.add(0);
        perguntas.add(kuest.getPergunta());
        respostas.add("didnt answer");
        pontos.add(0);

        next = new JButton("next");
        next.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                timer.stop();
                
                pontos.remove(pontos.get(pontos.size()-1));
                times.remove(times.get(times.size()-1));
                respostas.remove(respostas.get(respostas.size()-1));
                
                if(answerBox1.isSelected()){
                    respostas.add(answerBox1.getText());
                }else if(answerBox2.isSelected()){
                    respostas.add(answerBox2.getText());
                }else{
                    respostas.add("didnt answer");
                }
                
                pontos.add(answer_points);
                times.add(Math.abs(time_limit-time));
                
                respondeu = true;
                frame.dispose();
            }
            
        });
    }

    private void displayTXT(Question kuest){
        questions = new JPanel();
        advance = new JPanel(new FlowLayout());
        answers = new JPanel();
        HashMap<String,Integer> kuest_answers = kuest.getRespostas();
        JTextField answer = new JTextField();
        questionText = new JTextField(kuest.getPergunta());
        questionText.setEditable(false);
        Font font = new Font("SansSerif", Font.BOLD, 15);
        questionText.setFont(font);
        questions.add(questionText);

        times.add(0);
        perguntas.add(kuest.getPergunta());
        respostas.add("didnt answer");
        pontos.add(0);

        next = new JButton("next");
        next.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                timer.stop();

                pontos.remove(pontos.get(pontos.size()-1));
                times.remove(times.get(times.size()-1));
                respostas.remove(respostas.get(respostas.size()-1));
                
                for(String k: kuest_answers.keySet()){
                    if(answer.getText().equals(k)){
                        answer_points = kuest_answers.get(k);
                    }
                }
                if(!answer.getText().equals(""))
                    respostas.add(answer.getText());
                else{
                    respostas.add("didnt answer");
                }
                    
                times.add(Math.abs(time_limit-time));
                pontos.add(answer_points);

                respondeu = true;
                frame.dispose();
            }
        });
        answer.setPreferredSize(new Dimension(200,25));
        answers.add(answer);
    }

    public void exit(){
        frame.dispose();
    }

    public int points(){
        return answer_points;
    }

    public boolean respondeu(){
        return respondeu;
    }

    public static void saveFile(String name) throws IOException{
        double media = 0;
        double totalpoints = 0;
        int totaltime = 0;

        FileWriter filewriter = new FileWriter(name+".txt");
        
        for(int i=0; i<perguntas.size();i++){
            int aux = i + 1;
            filewriter.write("Pergunta " + aux + ": \n");
            filewriter.write(perguntas.get(i));filewriter.write("\n");
            filewriter.write(respostas.get(i));filewriter.write("\t Pontos: " + pontos.get(i));filewriter.write("\n");
            filewriter.write("Tempo: " + times.get(i));
            filewriter.write("\n");filewriter.write("\n");
            totalpoints += pontos.get(i);
            totaltime += times.get(i);
        }
        media = totalpoints /pontos.size();
        filewriter.write("Pontos totais= " + totalpoints);filewriter.write("\n");
        filewriter.write("Média por pergunta= " + media);filewriter.write("\n");
        filewriter.write("Tempo total= " + totaltime);filewriter.write("\n");
        
        filewriter.close();
        times = new ArrayList<>();
        respostas = new ArrayList<>();
        perguntas = new ArrayList<>();
        pontos = new ArrayList<>();

    }
}
