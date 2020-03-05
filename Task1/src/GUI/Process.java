package GUI;

import static GUI.BasicGUI.jtx;
import Graph.Graph;
import Graph.Relaxation;
import Graph.VertexcoverApproximation;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Ali Ibrahim
 */
public class Process {

    Graph graph;
    ArrayList<Integer> solve=new ArrayList<>();

    public void readGraphFromFile(File file) throws IOException, InterruptedException {
        Thread.sleep(2000);
        synchronized (this) {
            Scanner read = new Scanner(file, StandardCharsets.UTF_8.name());
            ArrayList<Integer> bigger = new ArrayList<>();
            jtx.append("Getting node counts from file:\n");
            System.out.println("Getting nodes count from file:\n");
            while (read.hasNextLine()) {
                String[] s = read.nextLine().split(" ");
                int big = Integer.max(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
                if (!bigger.contains(big)) {
                    bigger.add(big);
                }
            }
            Collections.sort(bigger, Collections.reverseOrder());
            
            graph = new Graph(bigger.get(0)+1);
            bigger=null;
            read = new Scanner(file, StandardCharsets.UTF_8.name());
            System.out.println("Adding edges...\n");
            jtx.append("Adding edges...\n");
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] s;
                if (!line.trim().equals("")) {
                    s = line.split(" ");
                    System.out.println(s[0] + " " + s[1]);
                    graph.addEdge(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
                }

            }
            jtx.append("finished!..\n");
            notify();
        }

    }

    public void process() throws InterruptedException {
        synchronized (this) {
            wait();
            jtx.append("Program Resumed!..\n");
        }
    }

    public void solveAlgoritm2() throws InterruptedException {
        Thread.sleep(2000);
        if (graph == null) {
            JOptionPane.showMessageDialog(null, "Please read dataset first!");
        } else {
            synchronized (this) {
                Relaxation r = new Relaxation();
                
                solve = r.solve(graph);
                jtx.append("finished!..\n");
                String text = "Result {\n";
                for (int i : solve) {
                    text += i + "\n";
                }
                text += "}\n"+ "Number of selected Nodes: "+solve.size()+"\n";
                jtx.append(text);
                graph = null;
                solve.clear();
                notify();

            }
        }
    }

    public void solveAlgoritm1() throws InterruptedException {
        Thread.sleep(2000);
        if (graph == null) {
            JOptionPane.showMessageDialog(null, "Please read dataset first!");
        } else {
            synchronized (this) {
                VertexcoverApproximation v = new VertexcoverApproximation();
                String calculate = v.calculate(graph);
                jtx.append("finished!..\n");
                jtx.append("Number of selected edges: "+v.getNumofSelectedEdges());
                System.out.println("Result: {+\n"+calculate+"}\n");
                graph = null;
                notify();

            }
        }
    }
}
