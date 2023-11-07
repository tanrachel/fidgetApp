package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class View extends JFrame {
    // variables that we would need to refer to in order to add/remove panels from
    private JTextArea userInput = new JTextArea();
    private JTextArea display = new JTextArea();
    private JButton read = new JButton("Read From File"), write = new JButton("Write to File");
    private JTextField ReadingFileName = new JTextField(20);
    private JTextField WritingFileName = new JTextField(20);
    private JLabel readingPrompt = new JLabel("Read from Filename", JLabel.RIGHT);
    private JLabel writingPrompt = new JLabel("Write to Filename", JLabel.RIGHT);

    private JPanel commands = new JPanel();
//    allows passing file names from the GUI to controller
    public String getReadingFileName(){
        return this.ReadingFileName.getText();
    }
    public String getWritingFileName(){
        return this.WritingFileName.getText();
    }
    public String getUserInput(){
        return this.userInput.getText();
    }
    public String getFirst3LinesOfReadingFile(){
        String[] lines = this.display.getText().split("\n");
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<lines.length;i++){
            if (i==4){
                return sb.toString();
            }
            sb.append(lines[i]+"\n");
        }
        return sb.toString();
    }
//    lengthen the textArea in GUI
    View(){
        buildGUI();
        userInput.setRows(40);
        display.setRows(40);

    }

    // coordinates all the sub panels in the main frame
    private void buildGUI(){
//        autofill with default valu
        ReadingFileName.setText("file.txt");
        WritingFileName.setText("file.txt");
        read.setActionCommand("read");
        write.setActionCommand("write");
        commands.setLayout(new GridLayout(3,2,1,1));
        commands.add(readingPrompt);
        commands.add(writingPrompt);
        commands.add(ReadingFileName);
        commands.add(WritingFileName);
        commands.add(read);
        commands.add(write);
        JPanel displayPanel = new JPanel(new GridLayout(1,2));
        displayPanel.add(new JScrollPane(display));
        displayPanel.add(new JScrollPane(userInput));
        displayPanel.setPreferredSize(new Dimension(400,300));


        JFrame mainframe = new JFrame();
        mainframe.setPreferredSize(new Dimension(400,400));
        mainframe.setLayout(new BoxLayout(mainframe.getContentPane(),BoxLayout.Y_AXIS));
        mainframe.add(commands);
        mainframe.add(displayPanel);

        mainframe.pack();
        mainframe.setVisible(true);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
//    allows refreshing of text area in the GUI
    public void displayText(String input){
        this.display.setText("");
        this.display.append(input);
    }
    public void userInputText(String input){
        this.userInput.setText("");
        this.userInput.append(input);
    }
    // registers controller
    public void registerController(Controller controller) {
        read.addActionListener(controller);
        write.addActionListener(controller);

    }
}
