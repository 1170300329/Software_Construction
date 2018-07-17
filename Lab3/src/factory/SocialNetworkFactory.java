package factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.CommentTie;
import edge.ForwardTie;
import edge.FriendTie;
import graph.SocialNetwork;
import vertex.Person;
import vertex.Vertex;

public class SocialNetworkFactory {
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
	public static SocialNetwork createGraph(String filePath) throws Exception {
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
		SocialNetwork sg=new SocialNetwork(name);
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
		    		if(!temp[i].equals("Person")) {
		    			System.out.println("file contain illegal content");
		    			System.exit(0);
		    		}
		    	}
		    }else if(m2.find()) {
		    	String t=m2.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
		    	String[] temp=t.split("\\#+");
		        temp=rmNullEle(temp);
		    	if(temp[1].equals("Person")) {
		    		Person person=new Person(temp[0]);
		    		String[] args=new String[temp.length-2];
		    		for(int i=2;i<temp.length;i++) {
		    			args[i-2]=temp[i];
		    		}
		    		person.fillVertexInfo(args);
		    		sg.addVertex(person);
		    	}
		    }else if(m3.find()) {
		    	String t=m3.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
		    	String[] temp=t.split("\\#+");
		        temp=rmNullEle(temp);
		    	for(int i=0;i<temp.length;i++) {
		    		if(!temp[i].equals("FriendTie")&&!temp[i].equals("CommentTie")&&!temp[i].equals("ForwardTie")) {
		    			System.out.println("file contain illegal content");
		    			System.exit(0);
		    		}
		    	}
		    }else if(m4.find()) {
		    	String t=m4.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
		    	String[] temp=t.split("\\#+");
		        temp=rmNullEle(temp);
		        
		        if(Double.valueOf(temp[2])>1||Double.valueOf(temp[2])<=0) {
		        	System.out.println("weight range error");
		        	System.exit(0);
		        }
		        if(temp[3].equals(temp[4])) {
		        	System.out.println("there should't be loop in the graph");
		        	System.exit(0);
		        }
		    	if(temp[1].equals("FriendTie")) {
		    		FriendTie friendTie=new FriendTie(temp[0], Double.valueOf(temp[2]));
		    		List<Vertex>list=new ArrayList<>();
		    		Person p1=new Person(temp[3]);
		    		Person p2=new Person(temp[4]);
		    		list.add(p1);
		    		list.add(p2);
		    		friendTie.addVertices(list);
		    		sg.addEdge(friendTie);
		    	}else if(temp[1].equals("CommentTie")) {
		    		CommentTie commentTie=new CommentTie(temp[0], Double.valueOf(temp[2]));
		    		List<Vertex>list=new ArrayList<>();
		    		Person p1=new Person(temp[3]);
		    		Person p2=new Person(temp[4]);
		    		list.add(p1);
		    		list.add(p2);
		    		commentTie.addVertices(list);
		    		sg.addEdge(commentTie);
		    	}else if(temp[1].equals("ForwardTie")) {
		    		ForwardTie forwardtTie=new ForwardTie(temp[0], Double.valueOf(temp[2]));
		    		List<Vertex>list=new ArrayList<>();
		    		Person p1=new Person(temp[3]);
		    		Person p2=new Person(temp[4]);
		    		list.add(p1);
		    		list.add(p2);
		    		forwardtTie.addVertices(list);
		    		sg.addEdge(forwardtTie);
		    	}
		    }
        }
		bReader.close();
		return sg;
	}
}
