package factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.NetworkConnection;
import graph.NetworkTopology;
import vertex.Computer;
import vertex.Router;
import vertex.Server;
import vertex.Vertex;

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
		String name=null;
        BufferedReader bReader = new BufferedReader(reader);
        String pattern = "GraphName\\=\\“(.+)\\”";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        while ((s =bReader.readLine()) != null) {
        	m = r.matcher(s);
        	if(m.find()) {
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
        
        String pattern3 = "EdgeType\\=(.+)";
        Pattern r3 = Pattern.compile(pattern3);
        Matcher m3;
        
        String pattern4 = "Edge\\=\\<(.+)\\>";
        Pattern r4 = Pattern.compile(pattern4);
        Matcher m4;
		while ((s =bReader.readLine()) != null) {
			m1 = r1.matcher(s);
		    m2 = r2.matcher(s);
		    m3 = r3.matcher(s);
		    m4 = r4.matcher(s);
		    if(m1.find()) {
		    	String t=m1.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
		    	String[] temp=t.split("\\#+");
		        temp=rmNullEle(temp);
		    	for(int i=0;i<temp.length;i++) {
		    		if(!temp[i].equals("Computer")&&!temp[i].equals("Router")&&!temp[i].equals("Server")) {
		    			System.out.println("file contain illegal content");
		    			System.exit(0);
		    		}
		    	}
		    }else if(m2.find()) {
		    	String t=m2.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
//System.out.println(t);
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
		    	if(temp[1].equals("Computer")) {
		    		Computer computer=new Computer(temp[0]);
		    		String[] args=new String[temp.length-2];
		    		for(int i=2;i<temp.length;i++) {
		    			args[i-2]=temp[i];
		    		}
		    		computer.fillVertexInfo(args);
		    		ng.addVertex(computer);
		    	}else if(temp[1].equals("Router")) {
		    		Router router=new Router(temp[0]);
		    		String[] args=new String[temp.length-2];
		    		for(int i=2;i<temp.length;i++) {
		    			args[i-2]=temp[i];
		    		}
		    		router.fillVertexInfo(args);
		    		ng.addVertex(router);
		    	}else if(temp[1].equals("Server")) {
		    		Server server=new Server(temp[0]);
		    		String[] args=new String[temp.length-2];
		    		for(int i=2;i<temp.length;i++) {
		    			args[i-2]=temp[i];
		    		}
		    		server.fillVertexInfo(args);
		    		ng.addVertex(server);
		    	}
		    }else if(m3.find()) {
		    	String t=m3.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
//System.out.println(t);
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
		    	for(int i=0;i<temp.length;i++) {
		    		if(!temp[i].equals("NetworkConnection")) {
		    			System.out.println("file contain illegal content");
		    			System.exit(0);
		    		}
		    	}
		    }else if(m4.find()) {
		    	String t=m4.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
//System.out.println(t);
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);

		    	NetworkConnection n=new NetworkConnection(temp[0], Double.valueOf(temp[2]));
		    	List<Vertex>list=new ArrayList<>();
		    	for(Vertex v:ng.vertices()) {
		    		if(v.getLabel().equals(temp[3])) {
		    			list.add(v);
		    		}else if(v.getLabel().equals(temp[4])) {
		    			list.add(v);
		    		}
		    	}
		    	n.addVertices(list);
		    	ng.addEdge(n);
		    }
        }
        bReader.close();
        return ng;
	}
}
