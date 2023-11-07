package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private View v;
    private Model m;
    public Controller(Model m, View v){
        this.m = m;
        this.v = v;
    }

    // gets values from user input and calculates weighted grades before returning it View
    @Override
    public void actionPerformed(ActionEvent e){
//        triggers reading from file and updates the GUI to reflect the content
        if (e.getActionCommand() == "read"){
            String fileContent = m.readTextFile(v.getReadingFileName());
            v.displayText(fileContent);
        } else if (e.getActionCommand() == "write") {
//            triggers writing and updates the userinput text area to show what was written to the file
            StringBuilder sb = new StringBuilder();
            if (m.checkIfFileExists(v.getWritingFileName())){
                String currentFileContent = m.readTextFile(v.getWritingFileName());
                sb.append(currentFileContent);
            }
//            gets user's input -- commented out as it is not required from specification
//            String userInput = v.getUserInput();
            String contentFromReadFile = v.getFirst3LinesOfReadingFile();
            sb.append(contentFromReadFile);
            m.writeTextFile(v.getWritingFileName(),sb.toString());
            v.userInputText(m.readTextFile(v.getWritingFileName()));
        }
    }


}
