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
public class GraphWindow extends JFrame {

	private static final long serialVersionUID = 196831535599934813L;

	public GraphWindow() {
		super("Graph Window");
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

		// Destiva algumas propriedades da visualização
		graph.setCellsEditable(false);   // Want to edit the value of a cell in the graph?
//		graph.setCellsMovable(false);    // Moving cells in the graph. Note that an edge is also a cell.
		graph.setCellsResizable(false);  // Inhibit cell re-sizing.
//		graph.setCellsSelectable(false); // Now I can't even select the cells!!!
//		graph.setEnabled(false); // Catch-All: no interaction with the graph.
		graphComponent.setConnectable(false); // Inhibit edge creation in the graph.
	}

	public static void main(String args[]) {
		GraphWindow frame = new GraphWindow();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}
}