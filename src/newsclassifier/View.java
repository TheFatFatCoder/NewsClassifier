/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsclassifier;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

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
        
        String html = "<html><head><title>First parse</title></head>"
  + "<body><p>Parsed HTML into a doc.</p></body></html>";
        Document doc = Jsoup.parse(html);
        String document = doc.toString();
        outputLabel.setText(document);
        
        add(panel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
