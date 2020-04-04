package com.lingnan.supermarket.view;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class SuperView extends JPanel{
 
 private JLabel label;
 private JFrame jFrame;

 public SuperView(JFrame jFrame) {
  this.setLayout(new BorderLayout());
  initView();
  this.jFrame = jFrame;
 }
 
 private void initView() {
   
  
  //中间
  label = new JLabel();
  label.setIcon(new ImageIcon("static\\img\\3.png"));
  label.setHorizontalAlignment(SwingUtilities.CENTER);
  label.setVerticalAlignment(SwingUtilities.CENTER);
 
  this.add(label,"Center");
  
  setVisible(true);
 }
}
