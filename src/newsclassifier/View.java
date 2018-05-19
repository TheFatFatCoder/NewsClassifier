/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsclassifier;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author john
 */
public class View extends JFrame{
        private JTextField inputField;
        private JButton enterButton;
        private JLabel inputLabel;
        private JLabel outputLabel;
    
    public View(){
        super("News Classifier");
        setLocation(0, 10);
        setSize(500,300);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0,10,10,0);
        inputLabel = new JLabel("Input news link here");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(inputLabel, c);
        
        inputField = new JTextField(20);
        c.gridx = 1;
        panel.add(inputField, c);
        
        enterButton = new JButton("Enter");
        c.gridx=2;
        panel.add(enterButton, c);
        
        outputLabel = new JLabel("Please only enter TheJakartaPost link");
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(outputLabel,c);
        
        ActionListener enterListener = new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (inputField.getText().contains("thejakartapost.com")) {
                            try{
                                Document doc = Jsoup.connect(inputField.getText()).get();
                                Elements newsContent = doc.select("div.show-define-text");
                                //System.out.println(newsContent);
                                newsContent = newsContent.select("p");
                                System.out.print("");
                                String body = "";
                                for (Element headline: newsContent){
                                    body += headline.text();
                                }
                                System.out.println(body);
                                NLPModel.findCategory(body);
                            }catch(IllegalArgumentException ie){
                                JOptionPane.showMessageDialog(null, "Please enter a valid link", "WARNING", JOptionPane.WARNING_MESSAGE);
                            }catch(IOException ie){
                                
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Please ONLY use thejakartapost.com link", "WARNING", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                };
        
        enterButton.addActionListener(enterListener);
        inputField.addActionListener(enterListener);
        
        add(panel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main (String[] args){
        new View();
    }
}
