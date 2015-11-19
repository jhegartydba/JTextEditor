package JTextEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;
import java.util.Date;

/*
 *           Simple Java Text Editor
 *   Copyright (C) 2015 Joseph Hegarty
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

public class JTextEditor extends JFrame implements ActionListener 
{
    private TextArea textArea = new TextArea("", 0,0, TextArea.SCROLLBARS_VERTICAL_ONLY);
    private JMenuBar menuBar = new JMenuBar(); // JMenuBar for file, edit, etc options
    private JMenu file = new JMenu("File"); // File menu
    private JMenu edit = new JMenu("Edit"); // Edit menu
    private JMenu help = new JMenu("Help"); // Help menu
    private JMenuItem openFile = new JMenuItem("Open");  // Open option
    private JMenuItem saveFile = new JMenuItem("Save"); // Save option
    private JMenuItem close = new JMenuItem("Close"); // Close option
    private JMenuItem about = new JMenuItem("About"); // About option
    private JMenuItem replace = new JMenuItem("Replace"); // Replace option
    private JMenuItem fontSize = new JMenuItem("Font"); // Font option
    private JMenuItem dTime = new JMenuItem("Date/Time"); // Date / Time option
    private Font aFont;
    
    public JTextEditor() 
    {
        this.setSize(600, 600); // Set window size
        this.setTitle("Simple Java Text Editor"); // Title
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Set default close operation (exit)
        aFont = new Font("Courier New", Font.BOLD, 12); // Set font size
        this.textArea.setFont(aFont); // Set font
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(textArea);

        // Add menu bar to the GUI and add menu options
        this.setJMenuBar(this.menuBar);
        this.menuBar.add(this.file);
        this.menuBar.add(this.edit);
        this.menuBar.add(this.help);
        
        JPopupMenu.setDefaultLightWeightPopupEnabled( false );
        ToolTipManager.sharedInstance().setLightWeightPopupEnabled( false );
        
        // Open
        this.openFile.addActionListener(this); // Add an action listener to open file menu option
        // you can set a keyboard shortcut if you so wish. Below is how
        //this.openFile.setShortcut(new JMenuShortcut(KeyEvent.VK_O, false)); 
        this.file.add(this.openFile); // Add File menu
        
        // Save
        this.saveFile.addActionListener(this); // Add an action listener to save file menu option
        // you can set a keyboard shortcut if you so wish.
        //this.saveFile.setShortcut(new JMenuShortcut(KeyEvent.VK_S, false));
        this.file.add(this.saveFile); // Add Save menu
        
        // Close
        this.close.addActionListener(this);
        // Add keyboard shortcuts as above......
        this.file.add(this.close);
        
        // Replace
        this.replace.addActionListener(this);
        this.edit.add(this.replace);
        
        // Font
        this.fontSize.addActionListener(this);
        this.edit.add(this.fontSize);
        
        // Date / Time
        this.dTime.addActionListener(this);
        this.edit.add(this.dTime);
        
        // About / Help
        this.about.addActionListener(this);
        this.help.add(this.about);
    }
    
    @Override
    public void actionPerformed (ActionEvent e) 
    {
        
        if (e.getSource() == this.close)
            this.dispose(); // If close is clicked then exit and dispose
        // If open file is clicked then open file chooser
        else 
        if (e.getSource() == this.openFile) 
        {
            JFileChooser open = new JFileChooser(); 
            int option = open.showOpenDialog(this); 

            if (option == JFileChooser.APPROVE_OPTION) 
            {
                this.textArea.setText(""); // clear the JTextArea
                try 
                {
                    // create a scanner to read the file
                    Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
                    while (scan.hasNext()) 
                        this.textArea.append(scan.nextLine() + "\n"); // Append line to JTextArea
                } 
                catch (Exception ex) 
                {
                    JOptionPane.showMessageDialog(null, "Unable to open file", "Exception", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        
        // If the save option was clicked
        else if (e.getSource() == this.saveFile) 
        {
            JFileChooser save = new JFileChooser(); // Open a file chooser
            int option = save.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) 
            {
                try 
                {
                    // Create a buffered writer to write file
                    BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
                    out.write(this.textArea.getText()); // write the contents to the file
                    out.close(); // Close stream
                } 
                catch (Exception ex) 
                {
                    JOptionPane.showMessageDialog(null, "Unable to save file", "Exception", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        // If the replace option is clicked
        else if(e.getSource() == this.replace) 
        {
            // Show box to input value to be replaced
            String findStr = JOptionPane.showInputDialog("Please enter value to be replaced?");
            // Show box to input new value
            String replaceStr = JOptionPane.showInputDialog("Please enter the new value?");
            if(findStr != null && replaceStr != null)
            {
            String replace = this.textArea.getText();
            replace = replace.replaceAll(findStr, replaceStr);
            this.textArea.setText(replace);
            }
        }
        // If change font is clicked
        else if(e.getSource() == this.fontSize) 
        {
            int setFontSize = Integer.parseInt(JOptionPane.showInputDialog("Please enter new font size?"));
            float size = aFont.getSize() + setFontSize;
            textArea.setFont( aFont.deriveFont(size));
        }
        // If add date option is clicked
        else if(e.getSource() == this.dTime) 
        {
            
            Date dateNow = new Date();
            this.textArea.append(dateNow.toString());
        }
        
        else if(e.getSource() == this.about) 
        {
            
            JOptionPane.showMessageDialog(null, "Simple Java Text Editor, 2015", "Simple Java Text Editor", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Main method. Creates text editor frame and makes it visible.
    public static void main(String args[]) 
    {
        JTextEditor app = new JTextEditor();
        app.setVisible(true);
    }
}
