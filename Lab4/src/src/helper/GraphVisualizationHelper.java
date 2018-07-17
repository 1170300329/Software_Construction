package src.helper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.commons.collections15.Transformer;

import src.edge.DirectedEdge;
import src.edge.Edge;
import src.edge.HyperEdge;
import src.edge.UndirectedEdge;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.AbstractGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import src.graph.ConcreteGraph;
import src.graph.GraphPoet;
import src.graph.NetworkTopology;
import src.graph.SocialNetwork;
import src.vertex.Computer;
import src.vertex.Director;
import src.vertex.Movie;
import src.vertex.Person;
import src.vertex.Router;
import src.vertex.Vertex;

public class GraphVisualizationHelper {
	public static void visualize(ConcreteGraph g) throws Exception {
		AbstractGraph<Vertex, Edge>graph;
		if(g instanceof GraphPoet||g instanceof SocialNetwork)
			graph=new DirectedSparseMultigraph<>();
		else 
			graph=new SparseMultigraph<>();
		
		BasicVisualizationServer<Vertex, Edge> vv;
		Transformer<Vertex, Paint> vertexPaint;
		Transformer<Edge, Paint> edgePaint; 
//		@SuppressWarnings("unused")
//		Transformer<Edge, Stroke> edgeStrokeTransformer;
		JFrame frame;
		frame = new JFrame("Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton jButton=new JButton("close");
		jButton.setBounds(10, 10, 80, 20);
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		frame.add(jButton);
		
		JTextField tf = new JTextField();
        tf.setBounds(100, 40, 80, 20);
        frame.add(tf);
        
		JButton jButton1=new JButton("rm-Ver");
		jButton1.setBounds(10, 40, 80, 20);
		jButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String mString=null;
					mString=tf.getText();
					if(mString!=null) {
						int flag=0;
						for(Vertex v:g.vertices()) {
							if(v.getLabel().equals(mString)) {
								graph.removeVertex(v);
								flag=1;
							}
						}
						if(flag==0)tf.setText("error:not found!");
					}else {
						tf.setText("error:the blank shoule be filled!");
					}
					frame.repaint();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.add(jButton1);
		
		
		JTextField tf1 = new JTextField();
        tf1.setBounds(100, 70, 80, 20);
        frame.add(tf1);
        
		JButton jButton2=new JButton("rm-Edg");
		jButton2.setBounds(10, 70, 80, 20);
		jButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String mString=null;
					mString=tf1.getText();
					if(mString!=null) {
						int flag=0;
						for(Edge er:g.edges()) {
							if(er.getLabel().equals(mString)) {
								graph.removeEdge(er);
								flag=1;
							}
						}
						if(flag==0)tf1.setText("error:not found!");
					}else {
						tf1.setText("error:the blank shoule be filled!");
					}
					frame.repaint();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.add(jButton2);
		
		
		for(Vertex v:g.vertices()){
			graph.addVertex(v);
		}
		for(Edge e:g.edges()) {
			if(e instanceof HyperEdge) {
				for(int i=0;i<e.getList().size()-1;i++) {
					Edge edge=new HyperEdge(e.getLabel()+":"+e.getList().get(i).getLabel()+","+e.getList().get(i+1).getLabel(),-1);
					List<Vertex>list=new ArrayList<>();
					list.add(e.getList().get(i));
					list.add(e.getList().get(i+1));
					edge.addVertices(list);
					graph.addEdge(edge, e.getList().get(i),e.getList().get(i+1));
				}
			}
			else 	graph.addEdge(e, e.getList().get(0), e.getList().get(1));
			
		}
		
		Transformer<Vertex, String>vertexLabel=new Transformer<Vertex, String>() {
			public String transform(Vertex p) {
				return  p.getLabel();
				
			}
		};
		
		Transformer<Edge, String>edgeLabel=new Transformer<Edge, String>() {
			public String transform(Edge p) {
				if(p instanceof DirectedEdge&&p.getWeight()!=-1) {
					try {
						return p.getLabel()+":"+p.getList().get(0).getLabel()+"->"+p.getList().get(1).getLabel()+":"+String.format("%.2f", p.getWeight());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(p instanceof DirectedEdge&&p.getWeight()==-1) {
					try {
						return p.getLabel()+":"+p.getList().get(0).getLabel()+"->"+p.getList().get(1).getLabel();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(p instanceof UndirectedEdge&&p.getWeight()==-1) {
					return p.getLabel();
				}else if(p instanceof UndirectedEdge&&p.getWeight()!=-1){
					return p.getLabel()+":"+String.format("%.2f", p.getWeight());
				}else if(p instanceof HyperEdge) {
					return p.getLabel();
				}else {
					return p.getLabel();
				}
				return null;
				
			}
		};
		edgePaint=new Transformer<Edge, Paint>() {
			public Paint transform(Edge e) {
				if(e instanceof HyperEdge)
					return Color.RED;
				return null;
			}
		};
		vertexPaint = new Transformer<Vertex, Paint>() {
			public Paint transform(Vertex p) {	
				if(g instanceof GraphPoet)
					return Color.GREEN;
				else if(g instanceof SocialNetwork) {
					Person temp=(Person) p;
					if(temp.getSex().equals("M")) {
						return Color.red;
					}else {
						return Color.blue;
					}
				}
				else if(g instanceof NetworkTopology) {
					if(p instanceof Computer) {
						return Color.black;
					}else if(p instanceof Router) {
						return Color.red;
					}else {
						return Color.orange;
					}
				}else {
					if(p instanceof Movie) {
						return Color.RED;
					}else if(p instanceof Director) {
						return Color.GREEN;
					}else {
						return Color.blue;
					}
				}
			}
		};
		//float dash[] = { 10.0f };
		//final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
//		@SuppressWarnings("unused")
//		Transformer<Edge, Stroke> edgeStrokeTransformer = new Transformer<Edge, Stroke>() {
//			public Stroke transform(Edge link) {
//				return edgeStroke;
//			}
//		};
				Layout<Vertex, Edge> layout = new CircleLayout<Vertex, Edge>(graph);
				layout.setSize(new Dimension(1000,700)); 
				
				vv = new BasicVisualizationServer<Vertex,Edge>(layout);
				vv.setPreferredSize(new Dimension(1000, 700)); // Sets the viewing area
				// size

				// apply transformers
				vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
				vv.getRenderContext().setVertexLabelTransformer(vertexLabel);
				vv.getRenderContext().setEdgeLabelTransformer(edgeLabel);
				vv.getRenderContext().setEdgeFillPaintTransformer(edgePaint);
				//vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
				vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
				frame.getContentPane().add(vv);
				frame.pack();
				frame.setSize(1000, 750);
				frame.setVisible(true);
	}
}
