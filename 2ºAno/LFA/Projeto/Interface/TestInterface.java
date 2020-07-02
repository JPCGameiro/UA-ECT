
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class TestInterface{

    public static void main(String[] args) throws IOException{
        HashMap<String,Integer> answers = new HashMap<>();
        answers.put("answer1",100);
        answers.put("answer2",0);
        answers.put("answer3",0);
        answers.put("answer4",0);
        Question kuest = new Question("tema","1",4,answers,"question");
        //KuestInterface questionario = new KuestInterface(kuest,"mc",10);
        boolean check;
        KuestInterface no_time = new KuestInterface(kuest,"mc");
        while(!no_time.respondeu())System.out.print("");

        KuestInterface multipla = new KuestInterface(kuest,"mc",10);
        while(!multipla.respondeu())System.out.print("");

        KuestInterface verdadeiro = new KuestInterface(kuest,"vf",10);
        while(!verdadeiro.respondeu())System.out.print("");

        
        KuestInterface texto = new KuestInterface(kuest,"txt",10);   
        while(!texto.respondeu())System.out.print("");
        KuestInterface.saveFile("results");

/*<<<<<<< HEAD
        
        KuestInterface.Save2file();
=======

        texto.saveFile("results");
>>>>>>> 954c3d6c193f95f3dc50631df544b45833f8710c*/
        
    }
}
