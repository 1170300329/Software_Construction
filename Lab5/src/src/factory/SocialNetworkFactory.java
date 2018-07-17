package src.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import src.IO.NIO;
import src.IO.StreamIO;
import src.IO.WRerIO;
import src.edge.CommentTie;
import src.edge.ForwardTie;
import src.edge.FriendTie;
import src.exception.EdgeTypeException;
import src.exception.ExistedEdgeException;
import src.exception.VertexTypeException;
import src.graph.SocialNetwork;
import src.log.MyLog;
import src.vertex.Person;
import src.vertex.Vertex;

public class SocialNetworkFactory {
  private static SocialNetwork sg;
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

  public static SocialNetwork createGraph(String filePath, int kind) throws Exception {
    sg = new SocialNetwork("");
    if (kind == 2) {
      WRerIO wRerIO = new WRerIO();
      wRerIO.reader(filePath, sg);
      cnt = 0;
      id = 0;
      flag = false;
      return sg;
    } else if (kind == 1) {
      StreamIO streamIO = new StreamIO();
      streamIO.reader(filePath, sg);
      cnt = 0;
      id = 0;
      flag = false;
      return sg;
    } else if (kind == 3) {
      NIO nio = new NIO();
      nio.reader(filePath, sg);
      cnt = 0;
      id = 0;
      flag = false;
      return sg;
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
        sg.setGraphName(name);
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
    id++;
    cnt++;
    if (cnt % 50000 == 0)
      System.out.println(cnt);
    m1 = r1.matcher(s);
    m2 = r2.matcher(s);
    m3 = r3.matcher(s);
    m4 = r4.matcher(s);
    if (m1.find()) {
      String t = m1.group(1);
      t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
      String[] temp = t.split("\\#+");
      temp = rmNullEle(temp);
      for (int i = 0; i < temp.length; i++) {
        if (!temp[i].equals("Person")) {
          MyLog.logger.error("VertexTypeException:不匹配的节点类型");
          throw new VertexTypeException("不匹配的节点类型：" + temp[i]);
        }
      }
    } else if (m2.find()) {
      String t = m2.group(1);
      t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
      String[] temp = t.split("\\#+");
      temp = rmNullEle(temp);
      if (temp[1].equals("Person")) {
        Person person = new Person(temp[0]);
        String[] args = new String[temp.length - 2];
        for (int i = 2; i < temp.length; i++) {
          args[i - 2] = temp[i];
        }
        try {
          person.fillVertexInfo(args);
        } catch (Exception e) {
          throw e;
        }
        try {
          sg.addVertex(person);
        } catch (Exception e) {
          Person person2 = new Person(temp[0] + "" + id);
          person.fillVertexInfo(args);
          sg.addVertex(person2);
          MyLog.logger.warn("Label重复将" + temp[0] + "改为" + temp[0] + "" + id);
          System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
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
        if (!temp[i].equals("FriendTie") && !temp[i].equals("CommentTie")
            && !temp[i].equals("ForwardTie")) {
          MyLog.logger.error("EdgeTypeException:不匹配的边类型");
          throw new EdgeTypeException("不匹配的边类型：" + temp[i]);
        }
      }
    } else if (m4.find()) {
      String t = m4.group(1);
      t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
      String[] temp = t.split("\\#+");
      temp = rmNullEle(temp);
      if (temp[1].equals("FriendTie")) {
        FriendTie friendTie = new FriendTie(temp[0], Double.valueOf(temp[2]));
        List<Vertex> list = new ArrayList<>();
        list.add(sg.getMap().get(temp[3]));
        list.add(sg.getMap().get(temp[4]));
        friendTie.addVertices(list);
        try {
          sg.addEdgeNOC(friendTie);
        } catch (ExistedEdgeException e) {
          FriendTie friendTie1 = new FriendTie(temp[0] + "" + id, Double.valueOf(temp[2]));
          friendTie1.addVertices(list);
          sg.addEdgeNOC(friendTie1);
          System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
        } catch (Exception e) {
          MyLog.logger.warn("Label重复将" + temp[0] + "改为" + temp[0] + "" + id);
          throw e;
        }
      } else if (temp[1].equals("CommentTie")) {
        CommentTie commentTie = new CommentTie(temp[0], Double.valueOf(temp[2]));
        List<Vertex> list = new ArrayList<>();
        Person p1 = new Person(temp[3]);
        Person p2 = new Person(temp[4]);
        list.add(p1);
        list.add(p2);
        commentTie.addVertices(list);
        try {
          sg.addEdgeNOC(commentTie);
        } catch (ExistedEdgeException e) {
          CommentTie commentTie2 = new CommentTie(temp[0] + "" + id, Double.valueOf(temp[2]));
          commentTie2.addVertices(list);
          sg.addEdgeNOC(commentTie2);
          System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
        } catch (Exception e) {
          MyLog.logger.warn("Label重复将" + temp[0] + "改为" + temp[0] + "" + id);
          throw e;
        }
      } else if (temp[1].equals("ForwardTie")) {
        ForwardTie forwardtTie = new ForwardTie(temp[0], Double.valueOf(temp[2]));
        List<Vertex> list = new ArrayList<>();
        list.add(sg.getMap().get(temp[3]));
        list.add(sg.getMap().get(temp[4]));
        forwardtTie.addVertices(list);
        try {
          sg.addEdgeNOC(forwardtTie);
        } catch (ExistedEdgeException e) {
          ForwardTie forwardtTie1 = new ForwardTie(temp[0] + "" + id, Double.valueOf(temp[2]));
          forwardtTie1.addVertices(list);
          sg.addEdgeNOC(forwardtTie1);
          System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
        } catch (Exception e) {
          MyLog.logger.warn("Label重复将" + temp[0] + "改为" + temp[0] + "" + id);
          throw e;
        }
      } else {
        MyLog.logger.error("EdgeTypeException:不匹配的边类型");
        throw new EdgeTypeException("不匹配的边类型：" + temp[1]);
      }
    } else {
    }
  }
}
