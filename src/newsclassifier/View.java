/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsclassifier;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        setSize(800,500);
        
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
        
        outputLabel = new JLabel("");
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(outputLabel,c);
        try{
            Document doc = Jsoup.connect("http://www.thejakartapost.com/news/2018/05/18/bandung-surakarta-zoos-exchange-deer-to-prevent-inbreeding.html").get();
            Elements newsContent = doc.select("div.show-define-text");
            //System.out.println(newsContent);
            newsContent = newsContent.select("p");
            System.out.print("");
            for (Element headline: newsContent){
                outputLabel.setText(outputLabel.getText()+headline.text());
                //System.out.println(headline);
            }
            //outputLabel.setText(document);
        }catch(IOException ie){
            
        }
        
        add(panel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main (String[] args){
        new View();
    }
}
