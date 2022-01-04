package texteditor;

import javax.swing. *;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JFrame;

public class jtawheel {
  JFrame jf;
  JTextArea jta;
  JScrollPane jsp;
  MouseWheelListener syswheel;
  public jtawheel (JTextArea text ,JScrollPane jsp) {
    jf=new JFrame ("Scroll Zoom");
    jf.setBounds(500,500,600,400);
    syswheel=jsp.getMouseWheelListeners()[0];//Get the system scroll event
    //jsp.removemousewheellistener (syswheel);//Remove system scrolling,Add as needed
    jsp.addMouseWheelListener(syswheel);
    jf.add(jsp);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.setVisible(true);
  }
  private class event extends MouseAdapter{
    public void MouseWheelMoved(MouseWheelEvent e) {
      if (e.isControlDown()) {//When the ctrl key is pressed,Scroll to zoom in and out
        Font f=jta.getFont();
        if (e.getWheelRotation()<0) {//Enlarge text if scroll bar goes forward
          jta.setFont(new Font (f.getFamily(), f.getStyle(), f.getSize() + 1));
        } else if (e.getWheelRotation()>0) {//Scroll down to reduce text
        	jta.setFont(new Font (f.getFamily(), f.getStyle(), f.getSize() -1));
        }
      } else {///when ctrl is not pressed,System scroll
        jsp.addMouseWheelListener(syswheel);
        syswheel.mouseWheelMoved(e);
        jsp.removeMouseWheelListener(syswheel);
      }
    }
  }
}
