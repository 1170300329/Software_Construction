package src.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import src.edge.Edge;
import src.edge.MovieActorRelation;
import src.edge.MovieDirectorRelation;
import src.edge.SameMovieHyperEdge;
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
import src.graph.MovieGraph;
import src.log.MyLog;
import src.vertex.Actor;
import src.vertex.Director;
import src.vertex.Movie;
import src.vertex.Vertex;

public class MovieGraphFactory {
	public static String[] rmNullEle(String[] temp) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < temp.length; i++) {
			if ("".equals(temp[i])) {
				continue;
			}
			sb.append(temp[i]);
			if (i != temp.length - 1) {
				sb.append(";");
			}
		}
		temp = sb.toString().split(";");
		return temp;
	}

	public static MovieGraph createGraph(String filePath) throws Exception {
		File f = new File(filePath);
		FileReader reader = new FileReader(f);
		String s = "";
		String name ="";
		@SuppressWarnings("resource")
		BufferedReader bRd = new BufferedReader(reader);
		String pattern = "GraphName\\=\\“(.+)\\”";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(s);
		int id=0;
		while ((s = bRd.readLine()) != null) {
			m = r.matcher(s);
			if (m.find()) {
				if(!m.group(1).matches("\\w+")) {
					MyLog.logger.error("InvalidLabelException:不合法的Label：图的Label含有不合法字符");
					throw new InvalidLabelException("不合法的Label：图的Label含有不合法字符");
				}
				name = m.group(1);
				break;
			}
		}
		MovieGraph mg = new MovieGraph(name);
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
		while ((s =bRd.readLine()) != null) {
			m1 = r1.matcher(s);
			m2 = r2.matcher(s);
			m3 = r3.matcher(s);
			m4 = r4.matcher(s);
			m5 = r5.matcher(s);
			id++;
			if (m1.find()) {
				String t = m1.group(1);
				t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
				String[] temp = t.split("\\#+");
				temp = rmNullEle(temp);
				for (int i = 0; i < temp.length; i++) {
					if (!temp[i].equals("Movie") && !temp[i].equals("Actor") && !temp[i].equals("Director")) {
						MyLog.logger.error("VertexTypeException:不匹配的节点类型");
						throw new VertexTypeException("不匹配的节点类型："+temp[i]);
					}
				}
			} else if (m2.find()) {
				String t = m2.group(1);
				
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
				t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
				String[] temp = t.split("\\#+");
				temp = rmNullEle(temp);
				if (!temp[0].matches("\\w+")) {
					MyLog.logger.error("InvalidLabelException:不合法的Label：顶点Label含有不合法字符");
					throw new InvalidLabelException("不合法的Label：顶点Label含有不合法字符");
				}
				if (temp[1].equals("Movie")) {
					Movie movie = new Movie(temp[0]);
					String[] args = new String[temp.length - 2];
					for (int i = 2; i < temp.length; i++) {
						args[i - 2] = temp[i];
					}
				if(args.length!=3) {
					MyLog.logger.error("AttrNumException:属性个数有误");
					throw new AttrNumException("Movie结点应该有3个属性值，但是这里只有"+args.length+"个");
				}
					try {
					movie.fillVertexInfo(args);
					}catch (Exception e) {
						throw e;
					}
					try {
					mg.addVertex(movie);
					}catch (Exception e) {
						Movie movie1=new Movie(temp[0]+""+id);
						movie1.fillVertexInfo(args);
						MyLog.logger.warn("Label重复将"+temp[0]+"改为"+temp[0]+""+id);
						System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
						mg.addVertex(movie1);
					}
				} else if (temp[1].equals("Actor")) {
					Actor actor = new Actor(temp[0]);
					String[] args = new String[temp.length - 2];
					for (int i = 2; i < temp.length; i++) {
						args[i - 2] = temp[i];
					}
					if(args.length!=2) {
						MyLog.logger.error("AttrNumException:属性个数有误");
						throw new AttrNumException("Actor结点应该有2个属性值，但是这里只有"+args.length+"个");
					}
					try {
					actor.fillVertexInfo(args);
					}catch (Exception e) {
						throw e;
					}
					try {
						mg.addVertex(actor);
						}catch (Exception e) {
							Actor actor1=new Actor(temp[0]+""+id);
							actor1.fillVertexInfo(args);
							MyLog.logger.warn("Label重复将"+temp[0]+"改为"+temp[0]+""+id);
							System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
							mg.addVertex(actor1);
						}
				} else if (temp[1].equals("Director")) {
					Director director = new Director(temp[0]);
					String[] args = new String[temp.length - 2];
					for (int i = 2; i < temp.length; i++) {
						args[i - 2] = temp[i];
					}
					if(args.length!=2) {
						MyLog.logger.error("AttrNumException:属性个数有误");
						throw new AttrNumException("Director结点应该有2个属性值，但是这里只有"+args.length+"个");
					}
					try {
					director.fillVertexInfo(args);
					}catch (Exception e) {
						throw e;
					}
					try {
						mg.addVertex(director);
						}catch (Exception e) {
							Director director1=new Director(temp[0]+""+id);
							director1.fillVertexInfo(args);
							MyLog.logger.warn("Label重复将"+temp[0]+"改为"+temp[0]+""+id);
							System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
							mg.addVertex(director1);
						}
				}else {
					MyLog.logger.error("VertexTypeException:不匹配的节点类型");
					throw new VertexTypeException("不匹配的节点类型："+temp[1]);
				}
			} else if (m3.find()) {
				String t = m3.group(1);
				t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
				String[] temp = t.split("\\#+");
				temp = rmNullEle(temp);
				for (int i = 0; i < temp.length; i++) {
					if (!temp[i].equals("MovieActorRelation") && !temp[i].equals("MovieDirectorRelation")
							&& !temp[i].equals("SameMovieHyperEdge")) {
						MyLog.logger.error("EdgeTypeException:不匹配的边类型");
						throw new EdgeTypeException("不匹配的边类型："+temp[i]);
					}
				}
			} else if (m4.find()) {
				String t = m4.group(1);
				t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
				String[] temp = t.split("\\#+");
				temp = rmNullEle(temp);
				if (!temp[0].matches("\\w+")) {
					MyLog.logger.error("InvalidLabelException:不合法的Label：边Label含有不合法字符");
					throw new InvalidLabelException("不合法的Label：边Label含有不合法字符");
				}
				try {
				if(!temp[5].equals("YES")) {
					MyLog.logger.error("MismatchEdgeException:无向图引入了有向边！");
					throw new MismatchEdgeException("无向图引入了有向边！");
				}}catch (Exception e) {
					temp[5]="NO";
				}
				if (temp[1].equals("MovieActorRelation")) {
					if(Double.valueOf(String.format("%.2f", Double.valueOf(temp[2])))==-1.0) {
						MyLog.logger.error("WeightException:带权边未给出权值");
						throw new WeightException("带权边"+temp[0]+"未给出权值");
					}
					try {
				    	for(Edge ed:mg.edges()) {
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
					MovieActorRelation medge = new MovieActorRelation(temp[0],
							Double.valueOf(String.format("%.2f", Double.valueOf(temp[2]))));
					List<Vertex> list = new ArrayList<>();
					Movie movie = new Movie(temp[3]);
					Director director = new Director(temp[4]);
					list.add(movie);
					list.add(director);
					try {
					medge.addVertices(list);
					}catch (Exception e) {
						continue;
					}
					try {
					mg.addEdge(medge);
					}catch (ExistedEdgeException e) {
						MovieActorRelation medge1 = new MovieActorRelation(temp[0]+""+id,
								Double.valueOf(String.format("%.2f", Double.valueOf(temp[2]))));
						medge1.addVertices(list);
						mg.addEdge(medge1);
						MyLog.logger.warn("Label重复将"+temp[0]+"改为"+temp[0]+""+id);
						System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
					}catch (Exception e) {
						throw e;
					}
				} else if (temp[1].equals("MovieDirectorRelation")) {
					try {
				    	for(Edge ed:mg.edges()) {
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
					MovieDirectorRelation medge = new MovieDirectorRelation(temp[0], Double.valueOf(temp[2]));
					List<Vertex> list = new ArrayList<>();
					Movie movie = new Movie(temp[3]);
					Director director = new Director(temp[4]);
					list.add(movie);
					list.add(director);
					medge.addVertices(list);
					try {
					mg.addEdge(medge);
					}catch (ExistedEdgeException e) {
						MovieDirectorRelation medge1=new MovieDirectorRelation(temp[0]+""+id,
								Double.valueOf(temp[2]));
						medge1.addVertices(list);
						mg.addEdge(medge1);
						MyLog.logger.warn("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
						System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
					}
				}else {
					MyLog.logger.error("EdgeTypeException:不匹配的边类型");
					throw new EdgeTypeException("不匹配的边类型："+temp[1]);
				}
			} else if (m5.find()) {
				String t = m5.group(1);
				t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>|\\{|\\}", "#");
				// System.out.println(t);
				String[] temp = t.split("\\#+");
				temp = rmNullEle(temp);
				if (!temp[0].matches("\\w+")) {
					MyLog.logger.error("InvalidLabelException:不合法的Label：超边Label含有不合法字符");
					throw new InvalidLabelException("不合法的Label：超边Label含有不合法字符");
				}
				if(!temp[1].equals("SameMovieHyperEdge")) {
					MyLog.logger.error("EdgeTypeException:不匹配的边类型");
					throw new EdgeTypeException("不匹配的边类型："+temp[1]);
				}
				if(temp.length-2<=1) {
					MyLog.logger.error("HEdgeVerNumException:超边内节点数目少于2个");
					throw new HEdgeVerNumException("超边内节点数目少于2个");
				}
				SameMovieHyperEdge sedge = new SameMovieHyperEdge(temp[0], -1);
				List<Vertex> list = new ArrayList<>();
				for (int i = 2; i < temp.length; i++) {
					Actor actor = new Actor(temp[i]);
					list.add(actor);
				}
				sedge.addVertices(list);
				try {
				mg.addEdge(sedge);
				}catch (ExistedEdgeException e) {
					SameMovieHyperEdge sedge1 = new SameMovieHyperEdge(temp[0]+""+id, -1);
					sedge1.addVertices(list);
					mg.addEdge(sedge1);
					MyLog.logger.warn("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
					System.out.println("因为Label重复，将"+temp[0]+"改为"+temp[0]+""+id);
				}
			}else {
				MyLog.logger.error("InvalidInstructionException:无效的指令");
				throw new InvalidInstructionException("无效的指令");
			}
		}
		bRd.close();
		return mg;
	}
}
