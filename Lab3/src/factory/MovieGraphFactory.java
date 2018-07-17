package factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.SameMovieHyperEdge;
import graph.MovieGraph;
import vertex.Actor;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

public class MovieGraphFactory {
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
	public static MovieGraph createGraph(String filePath) throws Exception {
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
//System.out.println("11");
        	if(m.find()) {
        		name=m.group(1);
//System.out.println(m.group(1));
        		break;
        	}
        }
//System.out.println(m.group(1));
        MovieGraph mg=new MovieGraph(name);
        String pattern1 = "VertexType\\=(.+)";
        Pattern r1 = Pattern.compile(pattern1);
        Matcher m1;
        
        String pattern2 = "Vertex\\=\\<(.+)\\>";
        Pattern r2 = Pattern.compile(pattern2);
        Matcher m2;
        
        String pattern3 = "EdgeType\\=(.+)";
        Pattern r3 = Pattern.compile(pattern3);
        Matcher m3;
        
        String pattern4 = "^Edge\\=\\<(.+)\\>";
        Pattern r4 = Pattern.compile(pattern4);
        Matcher m4;
        
        String pattern5 = "HyperEdge\\=\\<(.+)\\>";
        Pattern r5 = Pattern.compile(pattern5);
        Matcher m5;
		while ((s =bReader.readLine()) != null) {
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
		    		if(!temp[i].equals("Movie")&&!temp[i].equals("Actor")&&!temp[i].equals("Director")) {
		    			System.out.println("file contain illegal content:1"+" "+temp[i]);
		    			System.exit(0);
		    		}
		    	}
		    }else if(m2.find()) {
		    	String t=m2.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
//System.out.println(t);
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
		    	if(temp[1].equals("Movie")) {
		    		Movie movie=new Movie(temp[0]);
		    		String[] args=new String[temp.length-2];
		    		for(int i=2;i<temp.length;i++) {
		    			args[i-2]=temp[i];
		    		}
		    		movie.fillVertexInfo(args);
//System.out.println(movie.toString());
		    		mg.addVertex(movie);
		    	}else if(temp[1].equals("Actor")) {
		    		Actor actor=new Actor(temp[0]);
		    		String[] args=new String[temp.length-2];
		    		for(int i=2;i<temp.length;i++) {
		    			args[i-2]=temp[i];
		    		}
		    		actor.fillVertexInfo(args);
		    		mg.addVertex(actor);
		    	}else if(temp[1].equals("Director")) {
		    		Director director=new Director(temp[0]);
		    		String[] args=new String[temp.length-2];
		    		for(int i=2;i<temp.length;i++) {
		    			args[i-2]=temp[i];
		    		}
		    		director.fillVertexInfo(args);
//System.out.println(director.toString());
		    		mg.addVertex(director);
		    	}
		    }else if(m3.find()) {
		    	String t=m3.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
		    	for(int i=0;i<temp.length;i++) {
		    		if(!temp[i].equals("MovieActorRelation")&&!temp[i].equals("MovieDirectorRelation")&&!temp[i].equals("SameMovieHyperEdge")) {
		    			System.out.println("file contain illegal content:2");
		    			System.exit(0);
		    		}
		    	}
		    }else if(m4.find()) {
//System.out.println("m4find");
		    	String t=m4.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
//System.out.println(t);
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
		    	if(temp[3].equals(temp[4])) {
		    		System.out.println("there should'd be loop in the graph");
		    		System.exit(0);
		    	}
		    	if(temp[1].equals("MovieActorRelation")) {
		    		MovieActorRelation medge=new MovieActorRelation(temp[0], Double.valueOf(String.format("%.2f", Double.valueOf(temp[2]))));
		    		List<Vertex>list=new ArrayList<>();
		    		Movie movie=new Movie(temp[3]);
		    		Director director=new Director(temp[4]);
		    		list.add(movie);
		    		list.add(director);
		    		medge.addVertices(list);
		    		mg.addEdge(medge);
		    	}else if(temp[1].equals("MovieDirectorRelation")) {
		    		MovieDirectorRelation medge=new MovieDirectorRelation(temp[0], Double.valueOf(temp[2]));
		    		List<Vertex>list=new ArrayList<>();
		    		Movie movie=new Movie(temp[3]);
		    		Director director=new Director(temp[4]);
		    		list.add(movie);
		    		list.add(director);
		    		medge.addVertices(list);
		    		mg.addEdge(medge);
		    	}
		    }else if(m5.find()) {
		    	String t=m5.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>|\\{|\\}","#");
//System.out.println(t);
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
		    	SameMovieHyperEdge sedge=new SameMovieHyperEdge(temp[0],-1);
		    	List<Vertex>list=new ArrayList<>();
		    	for(int i=2;i<temp.length;i++) {
		    		Actor actor=new Actor(temp[i]);
		    		list.add(actor);
		    	}
		    	sedge.addVertices(list);
		    	mg.addEdge(sedge);
		    }
        }
        bReader.close();
        return mg;
	}
}
