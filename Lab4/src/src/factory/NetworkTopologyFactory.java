package src.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.edge.Edge;
import src.edge.NetworkConnection;
import src.exception.AttrNumException;
import src.exception.EdgeTypeException;
import src.exception.ExistedEdgeException;
import src.exception.HEdgeVerNumException;
import src.exception.InvalidInstructionException;
import src.exception.InvalidLabelException;
import src.exception.MismatchEdgeException;
import src.exception.MultiEdgeException;
import src.exception.VertexTypeException;
import src.exception.WeightException;
import src.graph.NetworkTopology;
import src.log.MyLog;
import src.vertex.Computer;
import src.vertex.Router;
import src.vertex.Server;
import src.vertex.Vertex;

public class NetworkTopologyFactory {
	public static String[] rmNullEle(String[] temp) {
		StringBuffer sb = new StringBuffer();
        for(int i=0; i<temp.length; i++) {
            if("".equals(temp[i])) {
                continue;
            }
            sb.append(temp[i]);
            if(i != temp.length - 1) {
                sb.append(";");
            }
        }
        temp = sb.toString().split(";");
        return temp;
	}
	public static NetworkTopology createGraph(String filePath) throws Exception {
		File f=new File(filePath);
		FileReader reader = new FileReader(f);
		String s="";
		String name="";
        @SuppressWarnings("resource")
		BufferedReader bReader = new BufferedReader(reader);
        String pattern = "GraphName\\=\\“(.+)\\”";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        int id=0;
        while ((s =bReader.readLine()) != null) {
        	m = r.matcher(s);
        	if(m.find()) {
        		if(!m.group(1).matches("\\w+")) {
        			MyLog.logger.error("InvalidLabelException:不合法的Label：图的Label含有不合法字符");
					throw new InvalidLabelException("不合法的Label：图的Label含有不合法字符");
				}
        		name=m.group(1);
        		break;
        	}
        }
        NetworkTopology ng=new NetworkTopology(name);
        
        String pattern1 = "VertexType\\=(.+)";
        Pattern r1 = Pattern.compile(pattern1);
        Matcher m1;
        
        String pattern2 = "Vertex\\=\\<(.+)\\>";
        Pattern r2 = Pattern.compile(pattern2);
        Matcher m2;
        
        String pattern3 = "^EdgeType\\=(.+)";
        Pattern r3 = Pattern.compile(pattern3);
        Matcher m3;
        
        String pattern4 = "Edge\\=\\<(.+)\\>";
        Pattern r4 = Pattern.compile(pattern4);
        Matcher m4;
        
        String pattern5 = "HyperEdge\\=\\<(.+)\\>";
		Pattern r5 = Pattern.compile(pattern5);
		Matcher m5;
		while ((s =bReader.readLine()) != null) {
			id++;
			m1 = r1.matcher(s);
		    m2 = r2.matcher(s);
		    m3 = r3.matcher(s);
		    m4 = r4.matcher(s);
		    m5 = r5.matcher(s);
		    if(m1.find()) {
		    	String t=m1.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
		    	String[] temp=t.split("\\#+");
		        temp=rmNullEle(temp);
		    	for(int i=0;i<temp.length;i++) {
		    		if(!temp[i].equals("Computer")&&!temp[i].equals("Router")&&!temp[i].equals("Server")) {
		    			MyLog.logger.error("VertexTypeException:不匹配的节点类型");
		    			throw new VertexTypeException("不匹配的节点类型："+temp[i]);
		    		}
		    	}
		    }else if(m2.find()) {
		    	String t=m2.group(1);
		    	String pattern0 = "(.+)\\<(.+)\\>";
				Pattern r0 = Pattern.compile(pattern0);
				Matcher m0;
				m0 = r0.matcher(t);
				if(!m0.find()) {
					MyLog.logger.error("InvalidInstructionException:文档内指令分量缺少或格式有误");
					throw new InvalidInstructionException("文档内指令分量缺少或格式有误");
				}else {
					if(m0.group(1).split("\\,|\\，").length!=2) {
						MyLog.logger.error("InvalidInstructionException:文档内指令分量缺少label或者type");
						throw new InvalidInstructionException("文档内指令分量缺少label或者type");
					}
				}
		    	
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
//System.out.println(t);
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
		    	if (!temp[0].matches("\\w+")) {
		    		MyLog.logger.error("InvalidLabelException:不合法的Label：顶点Label含有不合法字符");
					throw new InvalidLabelException("不合法的Label：顶点Label含有不合法字符");
				}
		    	if(temp[1].equals("Computer")) {
		    		Computer computer=new Computer(temp[0]);
		    		String[] args=new String[temp.length-2];
		    		for(int i=2;i<temp.length;i++) {
		    			args[i-2]=temp[i];
		    		}
		    		if(args.length!=1) {
		    			MyLog.logger.error("AttrNumException:属性个数有误");
						throw new AttrNumException("Computer结点应该有1个属性值，但是这里只有"+args.length+"个");
					}
		    		try {
		    		computer.fillVertexInfo(args);
		    		}catch (Exception e) {
						throw e;
					}
		    		try {
		    		ng.addVertex(computer);
		    		}catch (Exception e) {
						Computer computer2=new Computer(temp[0]+""+id);
						computer2.fillVertexInfo(args);
						ng.addVertex(computer2);
						MyLog.logger.warn("Label重复将"+temp[0]+"改为"+temp[0]+""+id);
						System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
					}
		    	}else if(temp[1].equals("Router")) {
		    		Router router=new Router(temp[0]);
		    		String[] args=new String[temp.length-2];
		    		for(int i=2;i<temp.length;i++) {
		    			args[i-2]=temp[i]; 
		    		}
		    		if(args.length!=1) {
		    			MyLog.logger.error("AttrNumException:属性个数有误");
						throw new AttrNumException("Router结点应该有1个属性值，但是这里只有"+args.length+"个");
					}
		    		try {
		    		router.fillVertexInfo(args);
		    		}catch (Exception e) {
						throw e;
					}
		    		try {
		    		ng.addVertex(router);
		    		}catch (Exception e) {
						Router router2=new Router(temp[0]+""+id);
						router2.fillVertexInfo(args);
						ng.addVertex(router2);
						MyLog.logger.warn("Label重复将"+temp[0]+"改为"+temp[0]+""+id);
						System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
					}
		    	}else if(temp[1].equals("Server")) {
		    		Server server=new Server(temp[0]);
		    		String[] args=new String[temp.length-2];
		    		for(int i=2;i<temp.length;i++) {
		    			args[i-2]=temp[i];
		    		}
		    		if(args.length!=1) {
		    			MyLog.logger.error("AttrNumException:属性个数有误");
						throw new AttrNumException("Server结点应该有1个属性值，但是这里只有"+args.length+"个");
					}
		    		try {
		    		server.fillVertexInfo(args);
		    		}catch (Exception e) {
						throw e;
					}
		    		try {
		    		ng.addVertex(server);
		    		}catch (Exception e) {
						Server server2=new Server(temp[0]+""+id);
						server2.fillVertexInfo(args);
						ng.addVertex(server2);
						MyLog.logger.warn("Label重复将"+temp[0]+"改为"+temp[0]+""+id);
						System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
					}
		    	}else {
		    		MyLog.logger.error("VertexTypeException:不匹配的节点类型");
					throw new VertexTypeException("不匹配的节点类型："+temp[1]);
				}
		    }else if(m3.find()) {
		    	String t=m3.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
//System.out.println(t);
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
		    	for(int i=0;i<temp.length;i++) {
		    		if(!temp[i].equals("NetworkConnection")) {
		    			MyLog.logger.error("EdgeTypeException:不匹配的边类型");
		    			throw new EdgeTypeException("不匹配的边类型："+temp[i]);
		    		}
		    	}
		    }else if(m4.find()) {
		    	String t=m4.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
//System.out.println(t);
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
		    	if (!temp[0].matches("\\w+")) {
		    		MyLog.logger.error("InvalidLabelException:不合法的Label：边Label含有不合法字符");
					throw new InvalidLabelException("不合法的Label：边Label含有不合法字符");
				}
		    	if(!temp[1].equals("NetworkConnection")) {
		    		MyLog.logger.error("EdgeTypeException:不匹配的边类型");
		    		throw new EdgeTypeException("不匹配的边类型："+temp[1]);
		    	}
		    	if(Double.valueOf(temp[2])==-1.0) {
		    		MyLog.logger.error("WeightException:带权边未给出权值");
					throw new WeightException("带权边"+temp[0]+"未给出权值");
				}
		    	if(Double.valueOf(temp[2])<0) {
		    		MyLog.logger.error("WeightException:带权边权值为负值");
					throw new WeightException("带权边"+temp[0]+"权值为负");
				}
		    	try {
					if(!temp[5].equals("YES")) {
						throw new MismatchEdgeException("无向图引入了有向边！");
					}}catch (Exception e) {
						MyLog.logger.warn("无向图引入了有向边！");
						temp[5]="NO";
					}
		    	
		    	try {
		    	for(Edge ed:ng.edges()) {
		    		if(ed.getList().get(0).getLabel().equals(temp[3])&&ed.getList().get(1).getLabel().equals(temp[4])) {
		    			throw new MultiEdgeException(temp[3]+"和"+temp[4]+"之间不能存在多重边："+temp[0]);
		    		}
		    		if(ed.getList().get(0).getLabel().equals(temp[4])&&ed.getList().get(1).getLabel().equals(temp[3])) {
		    			throw new MultiEdgeException(temp[3]+"和"+temp[4]+"之间不能存在多重边："+temp[0]);
		    		}
		    	}}catch (MultiEdgeException e) {
		    		MyLog.logger.warn("存在多重边");
					continue;
				}
		    	NetworkConnection n=new NetworkConnection(temp[0], Double.valueOf(temp[2]));
		    	List<Vertex>list=new ArrayList<>();
		    	for(Vertex v:ng.vertices()) {
		    		if(v.getLabel().equals(temp[3])) {
		    			list.add(v);
		    		}else if(v.getLabel().equals(temp[4])) {
		    			list.add(v);
		    		}
		    	}
		    	try {
		    	n.addVertices(list);
		    	}catch (Exception e) {
					continue;
				}
		    	try {
		    	ng.addEdge(n);
		    	}catch (ExistedEdgeException e) {
		    		NetworkConnection n1=new NetworkConnection(temp[0]+""+id, Double.valueOf(temp[2]));
		    		n1.addVertices(list);
		    		ng.addEdge(n1);
		    		System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
				}
		    	catch (Exception e) {
		    		MyLog.logger.warn("Label重复将"+temp[0]+"改为"+temp[0]+""+id);
					throw e;
				}
		    }else if(m5.find()){
		    	try {
		    	throw new HEdgeVerNumException("在此图中不应该有超边");
		    	}catch (Exception e) {
		    		MyLog.logger.warn("在此图中不应该有超边");
					continue;
				}
		    } else {
		    	MyLog.logger.error("InvalidInstructionException:无效的指令");
				throw new InvalidInstructionException("无效的指令");
			}
        }
        bReader.close();
        return ng;
	}
}
