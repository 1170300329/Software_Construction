package src.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.edge.CommentTie;
import src.edge.ForwardTie;
import src.edge.FriendTie;
import src.exception.AttrNumException;
import src.exception.EdgeTypeException;
import src.exception.ExistedEdgeException;
import src.exception.HEdgeVerNumException;
import src.exception.InvalidInstructionException;
import src.exception.InvalidLabelException;
import src.exception.MismatchEdgeException;
import src.exception.VertexTypeException;
import src.exception.WeightException;
import src.graph.SocialNetwork;
import src.log.MyLog;
import src.vertex.Person;
import src.vertex.Vertex;

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
		    		if(!temp[i].equals("Person")) {
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
		    	String[] temp=t.split("\\#+");
		        temp=rmNullEle(temp);
		        if (!temp[0].matches("\\w+")) {
		    		MyLog.logger.error("InvalidLabelException:不合法的Label：顶点Label含有不合法字符");
					throw new InvalidLabelException("不合法的Label：顶点Label含有不合法字符");
				}
		    	if(temp[1].equals("Person")) {
		    		Person person=new Person(temp[0]);
		    		String[] args=new String[temp.length-2];
		    		for(int i=2;i<temp.length;i++) {
		    			args[i-2]=temp[i];
		    		}
		    		if(args.length!=2) {
		    			MyLog.logger.error("AttrNumException:属性个数有误");
						throw new AttrNumException("Person结点应该有2个属性值，但是这里只有"+args.length+"个");
					}
		    		try {
		    		person.fillVertexInfo(args);
		    		}catch (Exception e) {
						throw e;
					}
		    		try {
		    		sg.addVertex(person);
		    		}catch (Exception e) {
						Person person2=new Person(temp[0]+""+id);
						person.fillVertexInfo(args);
						sg.addVertex(person2);
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
		    	String[] temp=t.split("\\#+");
		        temp=rmNullEle(temp);
		    	for(int i=0;i<temp.length;i++) {
		    		if(!temp[i].equals("FriendTie")&&!temp[i].equals("CommentTie")&&!temp[i].equals("ForwardTie")) {
		    			MyLog.logger.error("EdgeTypeException:不匹配的边类型");
		    			throw new EdgeTypeException("不匹配的边类型："+temp[i]);
		    		}
		    	}
		    }else if(m4.find()) {
		    	String t=m4.group(1);
		    	t=t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>","#");
		    	String[] temp=t.split("\\#+");
		        temp=rmNullEle(temp);
		        if (!temp[0].matches("\\w+")) {
		    		MyLog.logger.error("InvalidLabelException:不合法的Label：边Label含有不合法字符");
		        	throw new InvalidLabelException("不合法的Label：边Label含有不合法字符");
				}
		        if(Double.valueOf(temp[2])==-1.0) {
		    		MyLog.logger.error("WeightException:带权边未给出权值");
					throw new WeightException("带权边"+temp[0]+"未给出权值");
				}
		        if(Double.valueOf(temp[2])>1.0||Double.valueOf(temp[2])<=0) {
		        	MyLog.logger.error("WeightException:权值不在规定范围内");
	    			throw new WeightException("带权边"+temp[0]+"权值不在规定范围内");
	    		}
		        if(temp[5].equals("NO")) {
		        	MyLog.logger.error("MismatchEdgeException:有向图引入了无向边！");
		        	throw new MismatchEdgeException("有向图引入了无向边！");
		        }
		    	if(temp[1].equals("FriendTie")) {
		    		FriendTie friendTie=new FriendTie(temp[0], Double.valueOf(temp[2]));
		    		List<Vertex>list=new ArrayList<>();
		    		Person p1=new Person(temp[3]);
		    		Person p2=new Person(temp[4]);
		    		list.add(p1);
		    		list.add(p2);
		    		friendTie.addVertices(list);
		    		try {
		    		sg.addEdge(friendTie);
		    		}catch (ExistedEdgeException e) {
		    			FriendTie friendTie1=new FriendTie(temp[0]+""+id, Double.valueOf(temp[2]));
		    			friendTie1.addVertices(list);
		    			sg.addEdge(friendTie1);
		    			System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
					}catch (Exception e) {
						MyLog.logger.warn("Label重复将"+temp[0]+"改为"+temp[0]+""+id);
						throw e;
					}
		    	}else if(temp[1].equals("CommentTie")) {
		    		CommentTie commentTie=new CommentTie(temp[0], Double.valueOf(temp[2]));
		    		List<Vertex>list=new ArrayList<>();
		    		Person p1=new Person(temp[3]);
		    		Person p2=new Person(temp[4]);
		    		list.add(p1);
		    		list.add(p2);
		    		commentTie.addVertices(list);
		    		try {
		    		sg.addEdge(commentTie);
		    		}catch (ExistedEdgeException e) {
						CommentTie commentTie2=new CommentTie(temp[0]+""+id, Double.valueOf(temp[2]));
						commentTie2.addVertices(list);
						sg.addEdge(commentTie2);
						System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
					}catch (Exception e) {
						MyLog.logger.warn("Label重复将"+temp[0]+"改为"+temp[0]+""+id);
						throw e;
					}
		    	}else if(temp[1].equals("ForwardTie")) {
		    		ForwardTie forwardtTie=new ForwardTie(temp[0], Double.valueOf(temp[2]));
		    		List<Vertex>list=new ArrayList<>();
		    		Person p1=new Person(temp[3]);
		    		Person p2=new Person(temp[4]);
		    		list.add(p1);
		    		list.add(p2);
		    		forwardtTie.addVertices(list);
		    		try {
		    		sg.addEdge(forwardtTie);
		    		}catch (ExistedEdgeException e) {
		    			ForwardTie forwardtTie1=new ForwardTie(temp[0]+""+id, Double.valueOf(temp[2]));
		    			forwardtTie1.addVertices(list);
		    			sg.addEdge(forwardtTie1);
		    			System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
					}catch (Exception e) {
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
				    }else {
				    	MyLog.logger.error("EdgeTypeException:不匹配的边类型");
		    		throw new EdgeTypeException("不匹配的边类型："+temp[1]);
		    	}
		    }
		    else {
		    	MyLog.logger.error("InvalidInstructionException:无效的指令");
				throw new InvalidInstructionException("无效的指令");
			}
        }
		bReader.close();
		return sg;
	}
}
