package factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edge.WordNeighborhood;
import graph.GraphPoet;
import vertex.Vertex;
import vertex.Word;

public class GraphPoetFactory {
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
	public static GraphPoet createGraph(String filePath) throws Exception {
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
        GraphPoet gP=new GraphPoet(name);
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
		    		if(!temp[i].equals("Word")) {
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
		    	if(temp[1].equals("Word")) {
		    		Word word=new Word(temp[0]);
		    		gP.addVertex(word);
		    	}
		    }else if(m3.find()) {
		    	String t=m3.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
//System.out.println(t);
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
		    	for(int i=0;i<temp.length;i++) {
		    		if(!temp[i].equals("WordNeighborhood")) {
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
		    		List<Vertex>list=new ArrayList<>();
		    		Word w1=new Word(temp[3]);
		    		Word w2=new Word(temp[4]);
		    		list.add(w1);
		    		list.add(w2);
		    		if(Double.valueOf(temp[2])<=0) {
		    			System.out.println("weight<0,please revise the file");
		    			System.exit(0);
		    		}
		    		if(!(((int)((double)Double.valueOf(temp[2])))/Double.valueOf(temp[2])==1)) {
		    			System.out.println("weight should be an integer");
		    			System.exit(0);
		    		}
		    		WordNeighborhood w=new WordNeighborhood(temp[0],Double.valueOf(temp[2]));
		    		w.addVertices(list);
		    		gP.addEdge(w);
		    }
		}
        bReader.close();
        return gP;
	}
}
