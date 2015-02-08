package br.edu.ifes.tpa.trotski.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

import br.edu.ifes.tpa.trotski.dominio.Estado;
import br.edu.ifes.tpa.trotski.dominio.MatrizTransicao;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

/**
 * Janela que irá conter a representação visual da grafo.
 * 
 * @author Lucas Possatti
 */
public class GraphWindow extends JFrame {

	private static final long serialVersionUID = 196831535599934813L;

	/**
	 * Contrutor para o teste do JGraphX.
	 */
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
		graph.setCellsEditable(false); // Want to edit the value of a cell in
										// the graph?
		// graph.setCellsMovable(false); // Moving cells in the graph. Note that
		// an edge is also a cell.
		graph.setCellsResizable(false); // Inhibit cell re-sizing.
		// graph.setCellsSelectable(false); // Now I can't even select the
		// cells!!!
		// graph.setEnabled(false); // Catch-All: no interaction with the graph.
		graphComponent.setConnectable(false); // Inhibit edge creation in the
												// graph.
	}

	/**
	 * Constrói um JFrame que representa uma matriz de transição.
	 * 
	 * @param matriz
	 *            Matriz de Transição.
	 */
	public GraphWindow(MatrizTransicao matriz) {
		// Inicialização do JFrame
		super("Graph Window");

		// Inicialização da grafo
		mxGraph graph = new mxGraph();
		Object defaultParent = graph.getDefaultParent();
		graph.getModel().beginUpdate();

		// Permite loops na grafo
		graph.setAllowLoops(true);

		// Mapa entre os estados e suas representações nas janelas
		Map<Estado, Object> map = new HashMap<>();

		// Exibe os estados na janela, organizados em um quadrado
		int width = 450;
		int height = 450;
		Set<Estado> estados = matriz.getEstados();
		double verticesPorEixo = (int) Math.floor(Math.sqrt(estados.size()));
		double espacamento = width / verticesPorEixo;
		Iterator<Estado> iterator = estados.iterator();
		for (double x = 30; x <= width + 30; x = x + espacamento) {
			for (double y = 30; y <= height + 30; y = y + espacamento) {
				if (iterator.hasNext()) {
					Estado estado = iterator.next();
					Object vertice = graph.insertVertex(defaultParent, null,
							estado.nomeRepresentativoEstado(), x, y, 80, 20);
					// Mapeia o estado a sua representação
					map.put(estado, vertice);
				}
			}
		}

		// Cria as arestas na grafo visual
		Set<Estado[]> transicoes = matriz.getTransicoes();
		for (Estado[] transicao : transicoes) {
			Object v1 = map.get(transicao[0]);
			Object v2 = map.get(transicao[1]);
			graph.insertEdge(defaultParent, null, null, v1, v2);
		}

		// Finalização a criação da grafo e adiciona a grafo ao JFrame
		graph.getModel().endUpdate();
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);

		// Destiva algumas propriedades da grafo visualizada
		graph.setCellsEditable(false);
		// graph.setCellsMovable(false);
		graph.setCellsResizable(false);
		// graph.setCellsSelectable(false);
		// graph.setEnabled(false);
		graphComponent.setConnectable(false);
	}

	/**
	 * Pequeno método para testar o JGraphX.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		GraphWindow frame = new GraphWindow();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 320);
		frame.setVisible(true);
	}
}