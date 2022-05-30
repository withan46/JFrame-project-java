//import java.io.IOException;
//
//import javax.swing.JComponent;
//import javax.swing.JFrame;
//import javax.xml.parsers.ParserConfigurationException;
//
//import org.apache.commons.collections15.BidiMap;
//import org.apache.commons.collections15.Factory;
//import org.xml.sax.SAXException;
//
//import edu.uci.ics.jung.algorithms.layout.FRLayout;
//import edu.uci.ics.jung.graph.Graph;
//import edu.uci.ics.jung.graph.UndirectedSparseGraph;
//import edu.uci.ics.jung.io.GraphMLReader;
//import edu.uci.ics.jung.visualization.VisualizationViewer;
//import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
//import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
//
//public class ThirdWindow extends JComponent {
//	protected static Graph<MyNode,MyEdge> readFile(String filename) throws ParserConfigurationException, SAXException, IOException {
//		Factory<MyNode> nodeFactory = new Factory<MyNode>() {
//			@Override
//			public MyNode create() {
//				return new MyNode(""); 
//			}
//		};
//		
//		Factory<MyEdge> edgeFactory = new Factory<MyEdge>() {
//			@Override
//			public MyEdge create() {
//				return new MyEdge("");
//			}
//		};
//		
//		GraphMLReader<Graph<MyNode,MyEdge>, MyNode,MyEdge> graphMLReader = new GraphMLReader<Graph<MyNode,MyEdge>, MyNode,MyEdge>(nodeFactory, edgeFactory);
//    	Graph<MyNode,MyEdge> graph = new UndirectedSparseGraph<MyNode,MyEdge>();
//    	graphMLReader.load(filename, graph);
//    	
//        final BidiMap<MyNode, String> nodeIds = graphMLReader.getVertexIDs();        
//		for (MyNode node : graph.getVertices()) {
//		    node.label = nodeIds.get(node);
//		}
//		return graph;
//    }
//
//    public ThirdWindow() throws ParserConfigurationException, SAXException, IOException {
//    	String filename = "data/sample-data1.xml";
//        Graph<MyNode,MyEdge> graph = readFile(filename);
//        VisualizationViewer<MyNode,MyEdge> panel = new VisualizationViewer<MyNode,MyEdge>(new FRLayout<MyNode,MyEdge>(graph));
//        panel.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MyNode>());
//        panel.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
//        
//        JFrame frame = new JFrame("Graph View: Reading GraphML(2)");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(panel);
//        frame.pack();
//        frame.setVisible(true);
//    }
//
//}

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class SuspectsNetworkWindow extends JComponent {
	private int width = 600;
	private int height = 600;
	JTextField diameter;

	SuspectsNetworkWindow(Registry registry) {

		// Window name
		JFrame suspectsNetwordWindow = new JFrame("Suspects Network");

		Graph<MyNode, MyEdge> graph = new UndirectedSparseGraph<MyNode, MyEdge>();
		MyNode[] node = new MyNode[registry.getSuspectsList().size()];

		int count = 0;
		// Create every suspect node with suspect's name
		for (Suspect suspect : registry.getSuspectsList()) {
			node[count] = new MyNode(suspect.getCodeName());
			count++;

		}
		for (int i = 0; i < registry.getSuspectsList().size(); i++) {

			graph.addVertex(node[i]);
		}

		// if there are in a line then first is the first one second is the second etc
		count = 0;
		for (Suspect suspect : registry.getSuspectsList()) {
			int countFunction = 0;
			// Find the names
			String stringWithSuspects = suspect.getSuspects();
			for (Suspect aSuspect : registry.getSuspectsList()) {
				// Find if the name is linked with suspect's collaborators 
				int intIndex = stringWithSuspects.indexOf(aSuspect.getCodeName());
				
				// If it is true create an edge between them
				if (!(intIndex == -1)) {
					graph.addEdge(new MyEdge(suspect.getCodeName()), node[count], node[countFunction]);
				}
				countFunction++;
			}
			count++;
		}

		Layout<MyNode, MyEdge> layout = new StaticLayout<MyNode, MyEdge>(graph);

		int number = 0;
		// number is growing by 100 every time so that the letters are more visible ( If
		// we had more than 5 suspects I would use a rand so everyone could be in the
		// field )
		for (int i = 0; i < registry.getSuspectsList().size(); i++) {
			layout.setLocation(node[i], new Point2D.Double(100 + number, getRundomNumber()));
			number += 100;
		}

		DijkstraShortestPath<MyNode, MyEdge> alg = new DijkstraShortestPath<MyNode, MyEdge>(graph);

		double max = 0;
		for (int i = 0; i < registry.getSuspectsList().size(); i++) {
			for (int j = 0; j < registry.getSuspectsList().size(); j++) {
				// cast Number to double to use it in if
				double distance = alg.getDistance(node[i], node[j]).doubleValue();
				if (distance > max) {
					max = distance;
				}
			}
		}

		diameter = new JTextField("Diameter = " + max);
		diameter.setBounds(0, 542, 585, 20);

		// Creating graphical environment
		BasicVisualizationServer<MyNode, MyEdge> panel = new BasicVisualizationServer<MyNode, MyEdge>(layout,
				new Dimension(width, height - 60));

		// Display vertexes' names
		panel.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MyNode>());

		// Set vertexes' position
		panel.getRenderer().getVertexLabelRenderer().setPosition(Position.S);

		// Close window from x button (top right of window)
		suspectsNetwordWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Appear graph into window
		suspectsNetwordWindow.getContentPane().add(panel);
		suspectsNetwordWindow.pack();

		// Add JtextField to window
		suspectsNetwordWindow.add(diameter);

		// Set window size
		suspectsNetwordWindow.setSize(width, height);

		// Make button style
		suspectsNetwordWindow.setLayout(null);

		// Make window visible
		suspectsNetwordWindow.setVisible(true);

		// Set window in screen's center
		suspectsNetwordWindow.setLocationRelativeTo(null);

	}

	public int getRundomNumber() {
		Random rand = new Random();

		// create random number between 100-200
		int n = rand.nextInt(300);

		// n is starting from 100
		n += 100;

		return n;
	}

}
