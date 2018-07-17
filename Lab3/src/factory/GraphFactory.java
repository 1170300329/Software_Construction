package factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.Edge;
import graph.Graph;
import vertex.Vertex;

public class GraphFactory {
	public static Graph<Vertex, Edge> createGraph(String filePath) throws Exception {
		Graph<Vertex, Edge> graph=null;
		File f=new File(filePath);
		FileReader reader = new FileReader(f);
		String s="";
        BufferedReader bReader = new BufferedReader(reader);
        s =bReader.readLine();
        bReader.close();
        String pattern = "GraphType\\=\\“(.+)\\”";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        if(m.find()) {
        	System.out.println(m.group(1));
        	if(m.group(1).equals("MovieGraph")) {
        		graph=MovieGraphFactory.createGraph(filePath);
        	}else if(m.group(1).equals("GraphPoet")) {
        		graph=GraphPoetFactory.createGraph(filePath);
        	}else if(m.group(1).equals("NetworkTopology")) {
        		graph=NetworkTopologyFactory.createGraph(filePath);
        	}else if(m.group(1).equals("SocialNetwork")) {
        		graph=SocialNetworkFactory.createGraph(filePath);
        	}
        }
		return graph;
	}
}
//public static void main(String[] args) {
//String t="“Movie”,“Actor”，\"Director\"";
//t=t.replaceAll("\\“|\\”|\"|\\,|\\，","#");
//t=t.replaceAll("\\,", "#");
////String pattern ="GraphName\\=\\“(.+)\\”";
////Pattern r = Pattern.compile(pattern);
////Matcher m = r.matcher(s);
////if(m.find()) {
////	System.out.println(m.group(1));
////}
//System.out.println(t);
//String[] strings=t.split("\\#+");
//System.out.println(strings.length);
//for(int i=0;i<strings.length;i++) {
//	System.out.println(strings[i]);
//}
//}