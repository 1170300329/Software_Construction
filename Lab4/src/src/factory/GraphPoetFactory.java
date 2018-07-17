package src.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.edge.Edge;
import src.edge.WordNeighborhood;
import src.exception.EdgeTypeException;
import src.exception.ExistedEdgeException;
import src.exception.HEdgeVerNumException;
import src.exception.InvalidInstructionException;
import src.exception.InvalidLabelException;
import src.exception.MismatchEdgeException;
import src.exception.MultiEdgeException;
import src.exception.VertexTypeException;
import src.exception.WeightException;
import src.graph.GraphPoet;
import src.log.MyLog;
import src.vertex.Vertex;
import src.vertex.Word;

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
		String name="";
        @SuppressWarnings("resource")
		BufferedReader bReader = new BufferedReader(reader);
        String pattern = "GraphName=“(.+)”";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        int id=0;
        while ((s =bReader.readLine()) != null) {
        	m = r.matcher(s);
        	if(m.find()) {
        			//System.out.println(m.group(1).matches("\\w+"));
    				if(!m.group(1).matches("\\w+")) {
    					MyLog.logger.error("InvalidLabelException:不合法的Label：图的Label含有不合法字符");
    					throw new InvalidLabelException("不合法的Label：图的Label含有不合法字符");
    				}
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
        
        String pattern4 = "^Edge\\=\\<(.+)\\>";
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
		    		if(!temp[i].equals("Word")) {
		    			MyLog.logger.error("VertexTypeException:不匹配的节点类型");
		    			throw new VertexTypeException("不匹配的节点类型："+temp[i]);
		    		}
		    	}
		    }else if(m2.find()) {
		    	String t=m2.group(1);
		    	
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
		    	//System.out.println("@"+temp[0]);
		    	if (!temp[0].matches("\\w+")) {
		    		MyLog.logger.error("InvalidLabelException:不合法的Label：顶点Label含有不合法字符");
					throw new InvalidLabelException("不合法的Label：顶点Label含有不合法字符");
				}
		    	if(temp[1].equals("Word")) {
		    		Word word=new Word(temp[0]);
		    		try {
		    		gP.addVertex(word);
		    		}catch (Exception e) {
						Word word2=new Word(temp[0]+""+id);
						gP.addVertex(word2);
						MyLog.logger.warn("createGraph:因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
						System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
						
					}
		    	}else {
		    		MyLog.logger.error("VertexTypeException:不匹配的节点类型");
					throw new VertexTypeException("不匹配的节点类型："+temp[1]);
				}
		    }else if(m3.find()) {
		    	String t=m3.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
		    	for(int i=0;i<temp.length;i++) {
		    		if(!temp[i].equals("WordNeighborhood")) {
		    			MyLog.logger.error("EdgeTypeException:不匹配的边类型");
		    			throw new EdgeTypeException("不匹配的边类型："+temp[i]);
		    		}
		    	}
		    }else if(m4.find()) {
		    	String t=m4.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
		    	String[] temp=t.split("\\#+");
		    	temp=rmNullEle(temp);
//		    	System.out.println("@"+temp[0]);
		    	if (!temp[0].matches("\\w+")) {
		    		MyLog.logger.error("InvalidLabelException:不合法的Label：边Label含有不合法字符");
					throw new InvalidLabelException("不合法的Label：边Label含有不合法字符");
				}
		    	if(!temp[1].equals("WordNeighborhood")) {
		    		MyLog.logger.error("EdgeTypeException:不匹配的边类型");
		    		throw new EdgeTypeException("不匹配的边类型："+temp[1]);
		    	}
		    	if(Double.valueOf(temp[2])==-1.0) {
		    		MyLog.logger.error("WeightException:带权边未给出权值");
					throw new WeightException("带权边"+temp[0]+"未给出权值");
				}
		    	if((Double.valueOf(temp[2])-((int)((double)Double.valueOf(temp[2]))))!=0
		    			||Double.valueOf(temp[2])<0) {
		    		MyLog.logger.error("WeightException:带权边权值不是正整数");
		    		throw new WeightException("带权边"+temp[0]+"权值不是正整数");
		    	}
		    	if(temp[5].equals("NO")) {
		    		MyLog.logger.error("MismatchEdgeException:有向图引入了无向边！");
		        	throw new MismatchEdgeException("有向图引入了无向边！");
		        }
		    	try {
			    	for(Edge ed:gP.edges()) {
			    		if(ed.getList().get(0).getLabel().equals(temp[3])&&ed.getList().get(1).getLabel().equals(temp[4])) {
			    			MyLog.logger.warn("GraphPoetFactory#createGraph:存在多重边");
			    			throw new MultiEdgeException(temp[3]+"和"+temp[4]+"之间不能存在多重边："+temp[0]);
			    		}
			    	}}catch (MultiEdgeException e) {
						continue;
					}
		    		List<Vertex>list=new ArrayList<>();
		    		Word w1=new Word(temp[3]);
		    		Word w2=new Word(temp[4]);
		    		list.add(w1);
		    		list.add(w2);
		    		WordNeighborhood w=new WordNeighborhood(temp[0],Double.valueOf(temp[2]));
		    		w.addVertices(list);
		    		try {
		    		gP.addEdge(w);
		    		}catch (ExistedEdgeException e) {
		    			WordNeighborhood wm=new WordNeighborhood(temp[0]+""+id,Double.valueOf(temp[2]));
		    			wm.addVertices(list);
		    			gP.addEdge(wm);
		    			MyLog.logger.warn("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
		    			System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
					}
		    		catch (Exception e) {
						throw e;
					}
		    }else if(m5.find()){
		    	try {
		    		throw new HEdgeVerNumException("在此图中不应该有超边");
		    	}catch (Exception e) {
		    		MyLog.logger.warn("在此图中不应该有超边");
					continue;
				}
		    }else {
		    	MyLog.logger.error("GraphPoetFactory#createGraph#InvalidInstructionException:无效的指令");
				throw new InvalidInstructionException("无效的指令");
			}
		}
        bReader.close();
        return gP;
	}
}
