package helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.CommentTie;
import edge.ForwardTie;
import edge.FriendTie;
import edge.HyperEdge;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.NetworkConnection;
import edge.SameMovieHyperEdge;
import edge.WordNeighborhood;
import graph.ConcreteGraph;
import graph.GraphPoet;
import graph.MovieGraph;
import graph.NetworkTopology;
import graph.SocialNetwork;
import vertex.Actor;
import vertex.Computer;
import vertex.Director;
import vertex.Movie;
import vertex.Person;
import vertex.Router;
import vertex.Server;
import vertex.Vertex;
import vertex.Word;

public class ParseCommandHelper {
	public static void parseAndExecuteCommand(String command,ConcreteGraph g, Scanner sb) throws Exception {
		Pattern r=Pattern.compile("vertex\\s+--add\\s+(\\w+)\\s+(\\w+)");
		Matcher m = r.matcher(command);
		
		Pattern r1=Pattern.compile("vertex\\s+--delete\\s+(.+)");
		Matcher m1 = r1.matcher(command);
		
		Pattern r2=Pattern.compile("edge\\s+--add\\s+(\\w+)\\s+(\\w+)\\s+\\[(.+)\\]\\s+\\[(.+)\\]\\s+\\[(.+)\\]\\s+(.+)\\s+(.+)");
		Matcher m2=r2.matcher(command);
		
		Pattern r3=Pattern.compile("edge\\s+--delete\\s+(.+)");
		Matcher m3 = r3.matcher(command);
		
		Pattern r4=Pattern.compile("hyperedge\\s+--add\\s+(\\w+)\\s+(\\w+)\\s+(.+)");
		Matcher m4 = r4.matcher(command);
		
		Pattern r5=Pattern.compile("vertex\\s+--change\\s+(\\w+)\\s+(\\w+)\\s+to\\s+(\\w+)");
		Matcher m5 = r5.matcher(command);
		
		Pattern r6=Pattern.compile("edge\\s+--reweight\\s+(\\w+)\\s+to\\s+(\\w+)");
		Matcher m6 = r6.matcher(command);
		
		Pattern r7=Pattern.compile("edge\\s+--redirect\\s+(\\w+)");
		Matcher m7 = r7.matcher(command);
		
		Pattern r8=Pattern.compile("hyperedge\\s+--remove\\s+(\\w+)\\s+(\\w+)");
		Matcher m8 = r8.matcher(command);
		
		Pattern r9=Pattern.compile("hyperedge\\s+--add\\s+(\\w+)\\s+(\\w+)");
		Matcher m9 = r9.matcher(command);
		
		Pattern r10=Pattern.compile("edge\\s+--remove\\s+GraphPoet\\s+weight\\s+below\\s+(\\w+)");
		Matcher m10=r10.matcher(command);
		
		Pattern r11=Pattern.compile("compute\\s+(\\w+)\\s+(\\w+)");
		Matcher m11=r11.matcher(command);
		if(m.find()) {
			//System.out.println(m.group(1)+" "+m.group(2));
			if(g instanceof GraphPoet) {
				if(m.group(2).equals("Word")) {
					Word word=new Word(m.group(1));
					g.addVertex(word);
				}else {
					System.out.println("Type mismatch");
				}
			}else if(g instanceof SocialNetwork) {
				if(m.group(2).equals("Person")) {
					Person person=new Person(m.group(1));
					g.addVertex(person);
				}else {
					System.out.println("Type mismatch");
				}
			}else if(g instanceof NetworkTopology) {
				if(m.group(2).equals("Computer")) {
					Computer computer=new Computer(m.group(1));
					g.addVertex(computer);
				}else if(m.group(2).equals("Server")) {
					Server server=new Server(m.group(1));
					g.addVertex(server);
				}else if(m.group(2).equals("Router")) {
					Router router=new Router(m.group(1));
					g.addVertex(router);
				}else {
					System.out.println("Type mismatch");
				}
			}else if(g instanceof MovieGraph) {
				if(m.group(2).equals("Movie")) {
					Movie movie=new Movie(m.group(1));
					g.addVertex(movie);
				}else if(m.group(2).equals("Actor")) {
					Actor actor=new Actor(m.group(1));
					g.addVertex(actor);
				}else if(m.group(2).equals("Director")) {
					Director director=new Director(m.group(1));
					g.addVertex(director);
				}else {
					System.out.println("Type mismatch");
				}
			}
		}
		else if(m1.find()) {
			//System.out.println(m1.group(1));
			Pattern p1=Pattern.compile(m1.group(1));
			Matcher mMatcher;
			for(int i=0;i<g.getVertex().size();i++) {
				mMatcher=p1.matcher(g.getVertex().get(i).getLabel());
				if(mMatcher.find()) {
					System.out.print("确认删除顶点"+g.getVertex().get(i).getLabel()+"以及相连的边吗(Y/N)?");	
					char c='N';
					try{ 
					    c = (char)System.in.read();
					    sb.nextLine();
					    }   
					    catch(IOException e){   
					      e.printStackTrace();   
					    }   
					if(c=='Y'||c=='y') {
						g.removeVertex(g.getVertex().get(i));
						i--;
					}
				}
			}
		}
		else if(m2.find()) {
			//System.out.println(m2.group(1)+" "+m2.group(2)+" "+m2.group(3)+" "+m2.group(4)+" "+m2.group(5)+" "+m2.group(6)+" "+m2.group(7));
			if(g instanceof GraphPoet) {
				if(m2.group(2).equals("WordNeighborhood")) {
					WordNeighborhood w=new WordNeighborhood(m2.group(1),Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
						}
					}
					w.addVertices(vertices);
					g.addEdge(w);
				}else {
					System.out.println("Type mismatch");
				}
			}else if(g instanceof SocialNetwork) {
				if(m2.group(2).equals("FriendTie")) {
					FriendTie f=new FriendTie(m2.group(1), Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
						}
					}
					f.addVertices(vertices);
					g.addEdge(f);
				}else if(m2.group(2).equals("CommentTie")) {
					CommentTie c=new CommentTie(m2.group(1), Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
						}
					}
					c.addVertices(vertices);
					g.addEdge(c);
				}else if(m2.group(2).equals("ForwardTie")) {
					ForwardTie f=new ForwardTie(m2.group(1), Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
						}
					}
					f.addVertices(vertices);
					g.addEdge(f);
				}else {
					System.out.println("Type mismatch");
				}
			}else if(g instanceof NetworkTopology) {
				if(m2.group(2).equals("NetworkConnection")) {
					NetworkConnection n=new NetworkConnection(m2.group(1), Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
						}
					}
					n.addVertices(vertices);
					g.addEdge(n);
				}else {
					System.out.println("Type mismatch");
				}
			}else if(g instanceof MovieGraph) {
				if(m2.group(2).equals("MovieActorRelation")) {
					MovieActorRelation a=new MovieActorRelation(m2.group(1), Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
						}
					}
					a.addVertices(vertices);
					g.addEdge(a);
				}else if(m2.group(2).equals("MovieDirectorRelation")) {
					MovieDirectorRelation a=new MovieDirectorRelation(m2.group(1), Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
						}
					}
					a.addVertices(vertices);
					g.addEdge(a);
				}else if(m2.group(2).equals("SameMovieHyperEdge")) {
					System.out.println("Please use command:hyperedge --add");
				}
			}
		}
		else if(m3.find()) {
			System.out.println(m3.group(1));
			Pattern p1=Pattern.compile(m3.group(1));
			Matcher mMatcher;
			for(int i=0;i<g.getEdge().size();i++) {
				mMatcher=p1.matcher(g.getEdge().get(i).getLabel());
				if(mMatcher.find()) {
					g.removeEdge(g.getEdge().get(i));
					i--;
				}
			}
		}
		else if(m4.find()) {
			System.out.println(m4.group(1)+" "+m4.group(2)+" "+m4.group(3));
			String []temp=m4.group(3).split("\\s+");
			if(g instanceof MovieGraph) {
				SameMovieHyperEdge s=new SameMovieHyperEdge(m4.group(1), -1);
				int flag=0,tag=0;
				for(int i=0;i<g.getEdge().size();i++) {
					if(g.getEdge().get(i).getLabel().equals(m4.group(1))) {
						flag=1;
						List<Vertex> list=new ArrayList<>();
						for(int j=0;j<temp.length;j++) {
							tag=0;
							for(int k=0;k<g.getEdge().get(i).getList().size();k++) {
								if(temp[j].equals(g.getEdge().get(i).getList().get(k).getLabel())) {
									tag=1;
									break;
								}
							}
							if(tag==0) {
								for(Vertex v:g.vertices()) {
									if(temp[j].equals(v.getLabel())) {
										list.add(v);
									}
								}
							}
						}
						break;
					}
				}
				if(flag==0) {
					List<Vertex> list=new ArrayList<>();
					for(int i=0;i<temp.length;i++) {
						for(Vertex v:g.vertices()) {
							if(v.getLabel().equals(temp[i])) {
								list.add(v);
							}
						}
					}
					s.addVertices(list);
					g.addEdge(s);
				}
			}
		}else if(m5.find()) {
			for(int i=0;i<g.getVertex().size();i++) {
				if(g.getVertex().get(i).getLabel().equals(m5.group(1))) {
					g.getVertex().get(i).changeAttr(m5.group(2),m5.group(3));
				}
			}
		}else if(m6.find()) {
			for(int i=0;i<g.getEdge().size();i++) {
				if(g.getEdge().get(i).getLabel().equals(m6.group(1))) {
					g.reWeight(i,Double.valueOf(m6.group(2)));
				}
			}
		}else if(m7.find()) {
			for(int i=0;i<g.getEdge().size();i++) {
				if(g.getEdge().get(i).getLabel().equals(m7.group(1))) {
					List<Vertex>list=new ArrayList<>();
					list.add(g.getEdge().get(i).getList().get(1));
					list.add(g.getEdge().get(i).getList().get(0));
					g.getEdge().get(i).listRemove();
					g.getEdge().get(i).addVertices(list);
				}
			}
		}else if(m8.find()) {
			for(int i=0;i<g.getEdge().size();i++) {
				if(g.getEdge().get(i).getLabel().equals(m8.group(1))&&(g.getEdge().get(i) instanceof HyperEdge)) {
					for(int j=0;j<g.getEdge().get(i).getList().size();j++) {
						if(g.getEdge().get(i).getList().get(j).getLabel().equals(m8.group(2))) {
							g.removeVertex(g.getEdge().get(i).getList().get(j));
							break;
						}
					}
				}
			}
		}else if(m9.find()) {
			for(int i=0;i<g.getEdge().size();i++) {
				if(g.getEdge().get(i).getLabel().equals(m9.group(1))&&g.getEdge().get(i) instanceof HyperEdge) {
					for(int j=0;j<g.getVertex().size();j++) {
						if(g.getVertex().get(j).getLabel().equals(m9.group(2))) {
							List<Vertex>list=new ArrayList<>();
							list.add(g.getVertex().get(j));
							g.getEdge().get(i).addVertices(list);
						}
					}
				}
			}
		}else if(m10.find()) {
			if(g instanceof GraphPoet) {
				 g.removeEdgeBelowN(Integer.valueOf(m10.group(1)));
			}else {
				System.out.println("指令不可用");
			}
		}else if(command.equals("graph --toString")) {
			System.out.println(g.toString());
		}else if(command.equals("graph --show")) {
			GraphVisualizationHelper.visualize(g);
		}else if(m11.find()) {
			Vertex v1=null;
			Vertex v2=null;
			for(Vertex vt:g.vertices()) {
				if(vt.getLabel().equals(m11.group(1))) {
					v1=vt;
				}
				if(vt.getLabel().equals(m11.group(2))) {
					v2=vt;
				}
			}
			if(v1!=null) {
			Context c1=new Context((Strategy)new ComptuteEccentricity());
			System.out.println("Eccentricity "+c1.executeStrategy(g,v1));
			Context c2=new Context((Strategy)new ComputeBetweennessCentrality());
			System.out.println("BetweennessCentrality of "+v1.getLabel()+" "+c2.executeStrategy(g, v1));
			Context c3=new Context((Strategy)new ComputeClosenessCentrality());
			System.out.println("ClosenessCentrality of "+v1.getLabel()+" "+c3.executeStrategy(g, v1));
			Context c4=new Context((Strategy)new ComputeDegreeCentrality());
			System.out.println("DegreeCentrality of the graph "+c4.executeStrategy(g));
			Context c5=new Context((Strategy)new ComputeDegreeCentrality());
			System.out.println("DegreeCentrality of "+v1.getLabel()+" "+c5.executeStrategy(g, v1));
			Context c6=new Context((Strategy)new ComputeDiameter());
			System.out.println("Diameter of the graph "+c6.executeStrategy(g));
			if(v2!=null) {
			Context c7=new Context((Strategy)new ComputeDistance());
			System.out.println("Distance between "+v1.getLabel()+" "+v2.getLabel()+":"+c7.executeStrategy(g, v1, v2));
			}
			Context c8=new Context((Strategy)new ComputeInDegreeCentrality());
			System.out.println("InDegree of "+v1.getLabel()+" "+c8.executeStrategy(g, v1));
			Context c9=new Context((Strategy)new ComputeOutDegreeCentrality());
			System.out.println("OutDegree of "+v1.getLabel()+" "+c9.executeStrategy(g, v1));
			Context c10=new Context((Strategy)new ComputeRadius());
			System.out.println("Radius of the graph "+c10.executeStrategy(g));
			}else {
				System.out.println("指令有误");
			}
		}else if(command.equals("cmd --help")){
			System.out.println("cmd --help    　　　　　　　　　　　　                               查看帮助");
			System.out.println("vertex --add label type                                              增加节点");
			System.out.println("vertex --delete regex　　　　　                                                                                                                                                  删除满足条件的点(regex=点label时可以删除相应点)");
			
			System.out.println("vertex --change label Attributes(e.g. age/sex/year)　to attrs        　修改标签为label的顶点的属性值");
			
			System.out.println("edge --add label type [weighted=Y|N] [weight] [directed=Y|N] v1 v2   增加边");
			System.out.println("edge --delete regex                                                  删除满足条件的边(regex=边label时可以删除相应边)");
			
			System.out.println("edge --reweight label to x                                          　修改标签为label的边的权值为x");
			System.out.println("edge --redirect label                                               　修改标签为label的边的方向");
			System.out.println("edge --remove GraphPoet weight below n                              　删除GraphPoet中权值小于ｎ的边");
	
			System.out.println("hyperedge --add label type vertex1                  加入超边");
			System.out.println("hyperedge --remove edgelabel  vertexlabel                            从标签为edgelabel的超边中删除vertexlabel");
			System.out.println("hyperedge --add edgelabel  vertexlabel                               从标签为edgelabel的超边中加入vertexlabel(之前已经存在的顶点)");
			
			System.out.println("compute v1 v2/null                                                    计算图的各种度的信息，第二个顶点参数可选，如果不需要，则填null");
			
			System.out.println("graph --toString                                                     打印出图的信息");
			System.out.println("graph --show                                                         打印出图");
		}else {
			System.out.println("指令格式有误，可以用cmd --help查看帮助");
		}
	}
}
