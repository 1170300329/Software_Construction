package src.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.edge.CommentTie;
import src.edge.ForwardTie;
import src.edge.FriendTie;
import src.edge.HyperEdge;
import src.edge.MovieActorRelation;
import src.edge.MovieDirectorRelation;
import src.edge.NetworkConnection;
import src.edge.SameMovieHyperEdge;
import src.edge.WordNeighborhood;
import src.exception.InvalidCmdException;
import src.graph.ConcreteGraph;
import src.graph.GraphPoet;
import src.graph.MovieGraph;
import src.graph.NetworkTopology;
import src.graph.SocialNetwork;
import src.vertex.Actor;
import src.vertex.Computer;
import src.vertex.Director;
import src.vertex.Movie;
import src.vertex.Person;
import src.vertex.Router;
import src.vertex.Server;
import src.vertex.Vertex;
import src.vertex.Word;

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
		
		Pattern r4=Pattern.compile("hyperedge\\s+--addEdge\\s+(\\w+)\\s+(.+)");
		Matcher m4 = r4.matcher(command);
		
		Pattern r5=Pattern.compile("vertex\\s+--change\\s+(\\w+)\\s+(\\w+)\\s+to\\s+(.+)");
		Matcher m5 = r5.matcher(command);
		
		Pattern r6=Pattern.compile("edge\\s+--reweight\\s+(\\w+)\\s+to\\s+(.+)");
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
		
		Pattern r12=Pattern.compile("log\\s+between\\s+(.+)\\s+and\\s+(.+)");
		Matcher m12=r12.matcher(command);
		
		Pattern r13=Pattern.compile("log\\s+in\\s+class\\s+(.+)");
		Matcher m13=r13.matcher(command);
		
		Pattern r14=Pattern.compile("log\\s+in\\s+method\\s+(.+)");
		Matcher m14=r14.matcher(command);
		
		Pattern r15=Pattern.compile("log\\s+in\\s+level\\s+(.+)");
		Matcher m15=r15.matcher(command);
		if(m.find()) {
			//System.out.println(m.group(1)+" "+m.group(2));
			if(g instanceof GraphPoet) {
				if(m.group(2).equals("Word")) {
					Word word=new Word(m.group(1));
					g.addVertex(word);
				}else {
					throw new InvalidCmdException("不合法的结点类型");
				}
			}else if(g instanceof SocialNetwork) {
				if(m.group(2).equals("Person")) {
					Person person=new Person(m.group(1));
					g.addVertex(person);
				}else {
					throw new InvalidCmdException("不合法的结点类型");
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
					throw new InvalidCmdException("不合法的结点类型");
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
					throw new InvalidCmdException("不合法的结点类型");
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
			
			System.out.println(m2.group(1)+" "+m2.group(2)+" "+m2.group(3)+" "+m2.group(4)+" "+m2.group(5)+" "+m2.group(6)+" "+m2.group(7));
			if(!m2.group(3).equals("[weighted=Y]")||!m2.group(3).equals("[weighted=N]")) {
				throw new InvalidCmdException("边是否有权的参数错误，应该是[weighted=Y|N],您的输入是："+m2.group(3));
			}
			try {
				@SuppressWarnings("unused")
				double ms=Double.valueOf(m2.group(4));
			}catch (Exception e) {
				throw new InvalidCmdException("边的权重参数错误，请输入合法数字");
			}
			if(!m2.group(5).equals("[directed=Y]")||!m2.group(5).equals("[directded=N]")) {
				throw new InvalidCmdException("边是否有向的参数错误，应该是[directed=Y|N],您的输入是："+m2.group(5));
			}
			if(g instanceof GraphPoet) {
				if(m2.group(2).equals("WordNeighborhood")) {
					WordNeighborhood w=new WordNeighborhood(m2.group(1),Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					int flag1=0,flag2=0;
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
							flag1=1;
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
							flag2=1;
						}
					}
					if(flag1==0) {
						throw new InvalidCmdException("结点"+m2.group(6)+"不存在");
					}
					if(flag2==0) {
						throw new InvalidCmdException("结点"+m2.group(7)+"不存在");
					}
					w.addVertices(vertices);
					g.addEdge(w);
				}else {
					throw new InvalidCmdException("向单词图中加入的边不符合类型");
				}
			}else if(g instanceof SocialNetwork) {
				if(m2.group(2).equals("FriendTie")) {
					FriendTie f=new FriendTie(m2.group(1), Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					int flag1=0,flag2=0;
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
							flag1=1;
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
							flag2=1;
						}
					}
					if(flag1==0) {
						throw new InvalidCmdException("结点"+m2.group(6)+"不存在");
					}
					if(flag2==0) {
						throw new InvalidCmdException("结点"+m2.group(7)+"不存在");
					}
					f.addVertices(vertices);
					g.addEdge(f);
				}else if(m2.group(2).equals("CommentTie")) {
					CommentTie c=new CommentTie(m2.group(1), Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					int flag1=0,flag2=0;
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
							flag1=1;
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
							flag2=1;
						}
					}
					if(flag1==0) {
						throw new InvalidCmdException("结点"+m2.group(6)+"不存在");
					}
					if(flag2==0) {
						throw new InvalidCmdException("结点"+m2.group(7)+"不存在");
					}
					c.addVertices(vertices);
					g.addEdge(c);
				}else if(m2.group(2).equals("ForwardTie")) {
					ForwardTie f=new ForwardTie(m2.group(1), Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					int flag1=0,flag2=0;
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
							flag1=1;
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
							flag2=1;
						}
					}
					if(flag1==0) {
						throw new InvalidCmdException("结点"+m2.group(6)+"不存在");
					}
					if(flag2==0) {
						throw new InvalidCmdException("结点"+m2.group(7)+"不存在");
					}
					f.addVertices(vertices);
					g.addEdge(f);
				}else {
					throw new InvalidCmdException("向社交图中加入的边不符合类型");
				}
			}else if(g instanceof NetworkTopology) {
				if(m2.group(2).equals("NetworkConnection")) {
					NetworkConnection n=new NetworkConnection(m2.group(1), Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					int flag1=0,flag2=0;
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
							flag1=1;
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
							flag2=1;
						}
					}
					if(flag1==0) {
						throw new InvalidCmdException("结点"+m2.group(6)+"不存在");
					}
					if(flag2==0) {
						throw new InvalidCmdException("结点"+m2.group(7)+"不存在");
					}
					n.addVertices(vertices);
					g.addEdge(n);
				}else {
					throw new InvalidCmdException("向拓扑图中加入的边不符合类型");
				}
			}else if(g instanceof MovieGraph) {
				if(m2.group(2).equals("MovieActorRelation")) {
					MovieActorRelation a=new MovieActorRelation(m2.group(1), Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					int flag1=0,flag2=0;
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
							flag1=1;
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
							flag2=1;
						}
					}
					if(flag1==0) {
						throw new InvalidCmdException("结点"+m2.group(6)+"不存在");
					}
					if(flag2==0) {
						throw new InvalidCmdException("结点"+m2.group(7)+"不存在");
					}
					a.addVertices(vertices);
					g.addEdge(a);
				}else if(m2.group(2).equals("MovieDirectorRelation")) {
					MovieDirectorRelation a=new MovieDirectorRelation(m2.group(1), Double.valueOf(m2.group(4)));
					List<Vertex> vertices=new ArrayList<>();
					int flag1=0,flag2=0;
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(6))) {
							vertices.add(v);
							flag1=1;
						}
					}
					for(Vertex v:g.vertices()) {
						if(v.getLabel().equals(m2.group(7))) {
							vertices.add(v);
							flag2=1;
						}
					}
					if(flag1==0) {
						throw new InvalidCmdException("结点"+m2.group(6)+"不存在");
					}
					if(flag2==0) {
						throw new InvalidCmdException("结点"+m2.group(7)+"不存在");
					}
					a.addVertices(vertices);
					g.addEdge(a);
				}else if(m2.group(2).equals("SameMovieHyperEdge")) {
					throw new InvalidCmdException("指令误用，应该用：hyperedge --add");

				}else {
					throw new InvalidCmdException("向社交图中加入的边不符合类型");
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
			//System.out.println(m4.group(1)+" "+m4.group(2)+" "+m4.group(3));
			String []temp=m4.group(2).split("\\s+");
			if(temp.length<2) {
				throw new InvalidCmdException("新加超边的节点数目太少");
			}
			if(g instanceof MovieGraph) {
				SameMovieHyperEdge s=new SameMovieHyperEdge(m4.group(2), -1);
				int flag=0,tag=0;
				for(int i=0;i<g.getEdge().size();i++) {
					int k;
					if(g.getEdge().get(i).getLabel().equals(m4.group(2))) {
						flag=1;
						List<Vertex> list=new ArrayList<>();
						for(int j=0;j<temp.length;j++) {
							tag=0;
							for(k=0;k<g.getEdge().get(i).getList().size();k++) {
								if(temp[j].equals(g.getEdge().get(i).getList().get(k).getLabel())) {
									tag=1;
									break;
								}
							}
							if(tag==0) {
								for(Vertex v:g.vertices()) {
									if(temp[j].equals(v.getLabel())) {
										list.add(v);
										tag=1;
									}
								}
								if(tag==0) {
									throw new InvalidCmdException("不存在的顶点："+temp[j]);
								}
							}
						}
						g.getEdge().get(i).addVertices(list);
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
			int flag=0;
			for(int i=0;i<g.getVertex().size();i++) {
				if(g.getVertex().get(i).getLabel().equals(m5.group(1))) {
					try {
					g.getVertex().get(i).changeAttr(m5.group(2),m5.group(3));
					}catch (Exception e) {
						throw e;
					}
					flag=1;
				}
			}
			if(flag==0) {
				throw new InvalidCmdException("要修改的结点不存在");
			}
		}else if(m6.find()) {
			int flag=0;
			for(int i=0;i<g.getEdge().size();i++) {
				if(g.getEdge().get(i).getLabel().equals(m6.group(1))) {
					flag=1;
					@SuppressWarnings("unused")
					double weight=0;
					try {
						weight=Double.valueOf(m6.group(2));
					}catch (Exception e) {
						throw new InvalidCmdException("非法边权重");
					}
					try {
					if(g instanceof SocialNetwork) {
						((SocialNetwork) g).reWeightTie(i, Double.valueOf(m6.group(2)));
					}else {
						g.reWeight(i,Double.valueOf(m6.group(2)));
					}}catch (Exception e) {
						throw e;
					}
				}
			}
			if(flag==0) {
				throw new InvalidCmdException("要修改的结点不存在");
			}
		}else if(m7.find()) {
			int flag=0;
			for(int i=0;i<g.getEdge().size();i++) {
				if(g.getEdge().get(i).getLabel().equals(m7.group(1))) {
					flag=1;
					List<Vertex>list=new ArrayList<>();
					list.add(g.getEdge().get(i).getList().get(1));
					list.add(g.getEdge().get(i).getList().get(0));
					g.getEdge().get(i).listRemove();
					g.getEdge().get(i).addVertices(list);
				}
			}
			if(flag==0){
				throw new InvalidCmdException("要修改的结点不存在");
			}
		}else if(m8.find()) {
			int flag=0;
			for(int i=0;i<g.getEdge().size();i++) {
				if(g.getEdge().get(i).getLabel().equals(m8.group(1))&&(g.getEdge().get(i) instanceof HyperEdge)) {
					flag=1;
					int tag=0;
					for(int j=0;j<g.getEdge().get(i).getList().size();j++) {
						if(g.getEdge().get(i).getList().get(j).getLabel().equals(m8.group(2))) {
							try {
							g.removeVertex(g.getEdge().get(i).getList().get(j));
							}catch (Exception e) {
								throw e;
							}
							tag=1;
							break;
						}
					}
					if(tag==0) {
						throw new InvalidCmdException("要删除的结点不存在");
					}
				}
			}
			if(flag==0) {
				throw new InvalidCmdException("要删除的结点所在的边不存在");
			}
		}else if(m9.find()) {
			int flag=0;
			for(int i=0;i<g.getEdge().size();i++) {
				if(g.getEdge().get(i).getLabel().equals(m9.group(1))&&g.getEdge().get(i) instanceof HyperEdge) {
					flag=1;
					int tag=0;
					for(int j=0;j<g.getVertex().size();j++) {
						if(g.getVertex().get(j).getLabel().equals(m9.group(2))) {
							List<Vertex>list=new ArrayList<>();
							list.add(g.getVertex().get(j));
							g.getEdge().get(i).addVertices(list);
							tag=1;
						}
					}
					if(tag==0) {
						throw new InvalidCmdException("要加入的结点不存在");
					}
				}
			}
			if(flag==0) {
				throw new InvalidCmdException("要加入的结点所在的边不存在");
			}
		}else if(m10.find()) {
			if(g instanceof GraphPoet) {
				try {
				 g.removeEdgeBelowN(Integer.valueOf(m10.group(1)));
				}catch (Exception e) {
					throw new InvalidCmdException("输入的数字不合法");
				}
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
				throw new InvalidCmdException("输入的第一个参数在图中不存在");
			}
		}
		else if(m12.find()) {
			System.out.println(m12.group(1)+"     "+m12.group(2).length());
			int temp=0;
			temp=compare_date(m12.group(1), m12.group(2));
			if(temp==1) {
				throw new InvalidCmdException("不合法的日期");
			}else {
				File f=new File("src/src/log/graph.log");
				FileReader reader = new FileReader(f);
				String s="";
				BufferedReader bReader = new BufferedReader(reader);
				while ((s =bReader.readLine()) != null) {
					String[] date=s.split("\\s+");
					String dString=date[0]+" "+date[1];
					if(compare_date(dString, m12.group(2))==-1||compare_date(dString, m12.group(2))==0) {
						if(compare_date(m12.group(1), dString)==-1||compare_date(m12.group(1), dString)==0) {
							System.out.println(s);
						}else {
							continue;
						}
					}else {
						continue;
					}
				}
				 bReader.close();
			}
			
		}else if(m13.find()) {
			File f=new File("src/src/log/graph.log");
			FileReader reader = new FileReader(f);
			String s="";
			BufferedReader bReader = new BufferedReader(reader);
			while ((s =bReader.readLine()) != null) {
				if(s.contains(m13.group(1)))
					System.out.println(s);
			}
			bReader.close();
		}else if(m14.find()) {
			File f=new File("src/src/log/graph.log");
			FileReader reader = new FileReader(f);
			String s="";
			BufferedReader bReader = new BufferedReader(reader);
			while ((s =bReader.readLine()) != null) {
				if(s.contains(m14.group(1)))
					System.out.println(s);
			}
			bReader.close();
		}else if(m15.find()) {
			File f=new File("src/src/log/graph.log");
			FileReader reader = new FileReader(f);
			String s="";
			BufferedReader bReader = new BufferedReader(reader);
			while ((s =bReader.readLine()) != null) {
				if(s.contains(m15.group(1)))
					System.out.println(s);
			}
			bReader.close();
		}
		else if(command.equals("cmd --help")){
			System.out.println("cmd --help    　　　　　　　　　　　　                               查看帮助");
			System.out.println("vertex --add label type                                              增加节点");
			System.out.println("vertex --delete regex　　　　　                                                                               删除满足条件的点(regex=点label时可以删除相应点)");
			
			System.out.println("vertex --change label Attributes(e.g. age/sex/year)　to attrs        修改标签为label的顶点的属性值");
			
			System.out.println("edge --add label type [weighted=Y|N] [weight] [directed=Y|N] v1 v2      增加边");
			System.out.println("edge --delete regex                                                  删除满足条件的边(regex=边label时可以删除相应边)");
			
			System.out.println("edge --reweight label to x                                          　修改标签为label的边的权值为x");
			System.out.println("edge --redirect label                                               　修改标签为label的边的方向");
			System.out.println("edge --remove GraphPoet weight below n                              　删除GraphPoet中权值小于ｎ的边");
	
			System.out.println("hyperedge --addEdge label vertex1 vertex2..                          加入超边(顶点个数不能少于两个)");
			System.out.println("hyperedge --remove edgelabel  vertexlabel                            从标签为edgelabel的超边中删除vertexlabel");
			System.out.println("hyperedge --add edgelabel  vertexlabel                               从标签为edgelabel的超边中加入vertexlabel(之前已经存在的顶点)");
			
			System.out.println("compute v1 v2/null                                                    计算图的各种度的信息，第二个顶点参数可选，如果不需要，则填null");
			
			System.out.println("log between time1 and time2          查找两个日期之间的日志，示例：(log between 2018.05.20 13:11:15 and 2018.05.20 13:11:15)");
			System.out.println("log in class A                  查找A类相关的日志信息，示例：(log in class GraphPoet)");
			System.out.println("log in method A                 查找A类相关的日志信息，示例：(log in method addVertex)");
			System.out.println("log in level A                 查找A类相关的日志信息，示例：(log in level ERROR)");
			
			System.out.println("graph --toString                                                     打印出图的信息");
			System.out.println("graph --show                                                         打印出图");
		}else {
			System.out.println("指令格式有误，可以用cmd --help查看帮助");
			throw new InvalidCmdException("Exception:指令格式有误");
		}
	}
	
	 public static int compare_date(String DATE1, String DATE2) throws InvalidCmdException, ParseException {
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	            Date dt1 = df.parse(DATE1);
	            Date dt2 = df.parse(DATE2);
	            if (dt1.getTime() > dt2.getTime()) {
	                return 1;
	            } else if (dt1.getTime() < dt2.getTime()) {
	                return -1;
	            } else {
	                return 0;
	            }
	    }
}
