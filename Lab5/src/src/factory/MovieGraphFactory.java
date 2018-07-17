package src.factory;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import src.IO.NIO;
import src.IO.StreamIO;
import src.IO.WRerIO;
import src.edge.MovieActorRelation;
import src.edge.MovieDirectorRelation;
import src.edge.SameMovieHyperEdge;
import src.exception.EdgeTypeException;
import src.exception.ExistedEdgeException;
import src.exception.VertexTypeException;
import src.graph.MovieGraph;
import src.log.MyLog;
import src.vertex.Actor;
import src.vertex.Director;
import src.vertex.Movie;
import src.vertex.Vertex;

public class MovieGraphFactory {
  private static MovieGraph mg;
  private static boolean flag = false;
  private static int id = 0;
  private static int cnt = 0;

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

  public static MovieGraph createGraph(String filePath, int kind) throws Exception {
    mg = new MovieGraph("");
    if (kind == 2) {
      WRerIO wRerIO = new WRerIO();
      wRerIO.reader(filePath, mg);
      cnt = 0;
      id = 0;
      flag = false;
      return mg;
    } else if (kind == 1) {
      StreamIO streamIO = new StreamIO();
      streamIO.reader(filePath, mg);
      cnt = 0;
      id = 0;
      flag = false;
      return mg;
    } else if (kind == 3) {
      NIO nio = new NIO();
      nio.reader(filePath, mg);
      cnt = 0;
      id = 0;
      flag = false;
      return mg;
    } else {
      return null;
    }
  }

  public static void parse(String s) throws Exception {
    String name = "";
    String pattern = "GraphName=“(.+)”";
    if (flag == false) {
      Pattern r = Pattern.compile(pattern);
      Matcher m = r.matcher(s);
      if (m.find()) {
        name = m.group(1);
        mg.setGraphName(name);
        flag = true;
        return;
      }
    }
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

    cnt++;
    if (cnt % 50000 == 0)
      System.out.println(cnt);
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
          throw new VertexTypeException("不匹配的节点类型：" + temp[i]);
        }
      }
    } else if (m2.find()) {
      String t = m2.group(1);
      t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
      String[] temp = t.split("\\#+");
      temp = rmNullEle(temp);
      if (temp[1].equals("Movie")) {
        Movie movie = new Movie(temp[0]);
        String[] args = new String[temp.length - 2];
        for (int i = 2; i < temp.length; i++) {
          args[i - 2] = temp[i];
        }
        try {
          movie.fillVertexInfo(args);
        } catch (Exception e) {
          throw e;
        }
        try {
          mg.addVertex(movie);
        } catch (Exception e) {
          Movie movie1 = new Movie(temp[0] + "" + id);
          movie1.fillVertexInfo(args);
          MyLog.logger.warn("Label重复将" + temp[0] + "改为" + temp[0] + "" + id);
          System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
          mg.addVertex(movie1);
        }
      } else if (temp[1].equals("Actor")) {
        Actor actor = new Actor(temp[0]);
        String[] args = new String[temp.length - 2];
        for (int i = 2; i < temp.length; i++) {
          args[i - 2] = temp[i];
        }
        try {
          actor.fillVertexInfo(args);
        } catch (Exception e) {
          throw e;
        }
        try {
          mg.addVertex(actor);
        } catch (Exception e) {
          Actor actor1 = new Actor(temp[0] + "" + id);
          actor1.fillVertexInfo(args);
          MyLog.logger.warn("Label重复将" + temp[0] + "改为" + temp[0] + "" + id);
          System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
          mg.addVertex(actor1);
        }
      } else if (temp[1].equals("Director")) {
        Director director = new Director(temp[0]);
        String[] args = new String[temp.length - 2];
        for (int i = 2; i < temp.length; i++) {
          args[i - 2] = temp[i];
        }
        try {
          director.fillVertexInfo(args);
        } catch (Exception e) {
          throw e;
        }
        try {
          mg.addVertex(director);
        } catch (Exception e) {
          Director director1 = new Director(temp[0] + "" + id);
          director1.fillVertexInfo(args);
          MyLog.logger.warn("Label重复将" + temp[0] + "改为" + temp[0] + "" + id);
          System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
          mg.addVertex(director1);
        }
      } else {
        MyLog.logger.error("VertexTypeException:不匹配的节点类型");
        throw new VertexTypeException("不匹配的节点类型：" + temp[1]);
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
          throw new EdgeTypeException("不匹配的边类型：" + temp[i]);
        }
      }
    } else if (m4.find()) {
      String t = m4.group(1);
      t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
      String[] temp = t.split("\\#+");
      temp = rmNullEle(temp);
      if (temp[1].equals("MovieActorRelation")) {
        MovieActorRelation medge = new MovieActorRelation(temp[0],
            Double.valueOf(String.format("%.2f", Double.valueOf(temp[2]))));
        List<Vertex> list = new ArrayList<>();
        list.add(mg.getMap().get(temp[3]));
        list.add(mg.getMap().get(temp[4]));
        medge.addVertices(list);
        try {
          mg.addEdge(medge);
        } catch (ExistedEdgeException e) {
          MovieActorRelation medge1 = new MovieActorRelation(temp[0] + "" + id,
              Double.valueOf(String.format("%.2f", Double.valueOf(temp[2]))));
          medge1.addVertices(list);
          mg.addEdge(medge1);
          MyLog.logger.warn("Label重复将" + temp[0] + "改为" + temp[0] + "" + id);
          System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
        } catch (Exception e) {
          throw e;
        }
      } else if (temp[1].equals("MovieDirectorRelation")) {
        MovieDirectorRelation medge = new MovieDirectorRelation(temp[0], Double.valueOf(temp[2]));
        List<Vertex> list = new ArrayList<>();
        list.add(mg.getMap().get(temp[3]));
        list.add(mg.getMap().get(temp[4]));
        medge.addVertices(list);
        try {
          mg.addEdge(medge);
        } catch (ExistedEdgeException e) {
          MovieDirectorRelation medge1 =
              new MovieDirectorRelation(temp[0] + "" + id, Double.valueOf(temp[2]));
          medge1.addVertices(list);
          mg.addEdge(medge1);
          MyLog.logger.warn("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
          System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
        }
      } else {
        MyLog.logger.error("EdgeTypeException:不匹配的边类型");
        throw new EdgeTypeException("不匹配的边类型：" + temp[1]);
      }
    } else if (m5.find()) {
      String t = m5.group(1);
      t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>|\\{|\\}", "#");
      String[] temp = t.split("\\#+");
      temp = rmNullEle(temp);
      SameMovieHyperEdge sedge = new SameMovieHyperEdge(temp[0], -1);
      List<Vertex> list = new ArrayList<>();
      for (int i = 2; i < temp.length; i++) {
        list.add(mg.getMap().get(temp[i]));
      }
      sedge.addVertices(list);
      try {
        mg.addEdge(sedge);
      } catch (ExistedEdgeException e) {
        SameMovieHyperEdge sedge1 = new SameMovieHyperEdge(temp[0] + "" + id, -1);
        sedge1.addVertices(list);
        mg.addEdge(sedge1);
        MyLog.logger.warn("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
        System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
      }
    } else {
    }
  }
}
