package GUI;

import Graph.Graph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        this.setTitle("Approximation & Relaxation");
        this.setVisible(true);

        this.setSize(500, 500);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String[] ObjButtons = {"Yes", "No"};
                int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?",
                        "Task1", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null, ObjButtons, ObjButtons[1]);
                if (PromptResult == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        jbutton1.setText("Read File");
        jbutton2.setText("Clear");
        jbutton3.setText("Solve with algo1");
        jbutton4.setText("Solve with algo2");
        jbutton1.addActionListener(new OnClickActionListener());
        jbutton2.addActionListener(new OnClickButton2ActionListener());
        jbutton3.addActionListener(new OnClickButton3ActionListener());
        jbutton4.addActionListener(new OnClickButton4ActionListener());
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

    public static void main(String[] args) {
        BasicGUI gui = new BasicGUI();
    }

    static class OnClickActionListener implements ActionListener {

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

    static class OnClickButton2ActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            jtx.setText("");
        }
    }

    private static class OnClickButton3ActionListener implements ActionListener {

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
            
            th1.start();
            th2.start();
        }
    }

    private static class OnClickButton4ActionListener implements ActionListener {

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
            
            th1.start();
            th2.start();
        }
    }
}
