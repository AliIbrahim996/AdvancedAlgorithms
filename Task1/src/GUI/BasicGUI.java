package GUI;

import Graph.Graph;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 *
 * @author Ali Ibrahim
 */
public class BasicGUI extends JFrame {

    JPanel jp = new JPanel();
    final static Process p = new Process();
    static JTextArea jtx = new JTextArea(20, 40);
    JButton jbutton1 = new JButton();
    JButton jbutton2 = new JButton();
    JButton jbutton3 = new JButton();
    JButton jbutton4 = new JButton();
    JScrollPane sPane = new JScrollPane();

    Graph graph;

    public BasicGUI() {
        setTitle("Approximation & Relaxtion");
        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        jbutton1.setText("Read File");
        jbutton2.setText("Clear");
        jbutton3.setText("Solve with algo1");
        jbutton4.setText("Solve with algo2");
        jbutton1.addActionListener(new OnClickActionListner());
        jbutton2.addActionListener(new OnClickButton2ActionListner());
        jbutton3.addActionListener(new OnClickButton3ActionListner());
        jbutton4.addActionListener(new OnClickButton4ActionListner());
        sPane.add(jtx);
        sPane.setViewportView(jtx);
        //jp.add(jtx);
        jp.add(sPane);
        jp.add(jbutton1);
        jp.add(jbutton2);
        jp.add(jbutton3);
        jp.add(jbutton4);
        add(jp);
    }

    static class OnClickActionListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            final File file;
            jtx.setText("");
            String path = System.getProperty("user.dir");
            JFileChooser jFileChooser1 = new JFileChooser(path);
            if (jFileChooser1.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                file = jFileChooser1.getSelectedFile();
                jtx.append("Start Reading file....\n");
                Thread th1 = new Thread(() -> {
                    try {
                        p.process();
                    } catch (InterruptedException ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());

                    }
                });

                Thread th2 = new Thread(() -> {
                    try {
                        p.readGraphFromFile(file);
                    } catch (IOException | InterruptedException ex) {
                        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    }
                });

                th1.start();
                th2.start();
            }

        }
    }

    static class OnClickButton2ActionListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            jtx.setText("");
        }
    }

    public static void main(String args[]) {
        BasicGUI gui = new BasicGUI();
    }

    private static class OnClickButton3ActionListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Thread th1 = new Thread(() -> {
                try {
                    p.process();
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());

                }
            });

            Thread th2 = new Thread(() -> {
                try {
                    p.solveAlgoritm1();
                } catch (InterruptedException ex) {
                    Logger.getLogger(BasicGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            jtx.append("Solving with algo1\n");
            th1.start();
            th2.start();
        }
    }

    private static class OnClickButton4ActionListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Thread th1 = new Thread(() -> {
                try {
                    p.process();
                } catch (InterruptedException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());

                }
            });

            Thread th2 = new Thread(() -> {
                try {
                    p.solveAlgoritm2();
                } catch (InterruptedException ex) {
                    Logger.getLogger(BasicGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            jtx.append("Solving with algo2\n");
            th1.start();
            th2.start();
        }
    }   
}
