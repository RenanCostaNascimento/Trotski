package br.edu.ifes.tpa.trotski.app;

import javax.swing.JFrame;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * First examples of JGraphX - Creating a simple frame that contains a graph
 * component with two vertices and an edge connecting them.
 * 
 * @author vainolo
 */
public class JGraphXLearning1 extends JFrame {

	private static final long serialVersionUID = 196831535599934813L;

	public JGraphXLearning1() {
		super("JGraphXLearning1");
		mxGraph graph = new mxGraph();
		Object defaultParent = graph.getDefaultParent();
		graph.getModel().beginUpdate();
		Object v1 = graph.insertVertex(defaultParent, null, "Hello", 20, 20,
				80, 30);
		Object v2 = graph.insertVertex(defaultParent, null, "World", 240, 150,
				80, 30);
		graph.insertEdge(defaultParent, null, "Edge", v1, v2);
		graph.getModel().endUpdate();
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
	}

	public static void main(String args[]) {
		JGraphXLearning1 frame = new JGraphXLearning1();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}
}