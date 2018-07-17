package src.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import src.IO.NIO;
import src.IO.StreamIO;
import src.IO.WRerIO;
import src.edge.WordNeighborhood;
import src.exception.EdgeTypeException;
import src.exception.ExistedEdgeException;
import src.exception.VertexTypeException;
import src.graph.GraphPoet;
import src.log.MyLog;
import src.vertex.Vertex;
import src.vertex.Word;

public class GraphPoetFactory {
  private static GraphPoet gP;
  private static boolean flag = false;
  private static int id = 0;
  private static int cnt = 0;

  public static String[] rmNullEle(String[] temp) {
    StringBuffer sb = new StringBuffer();
    int len = temp.length;
    for (int i = 0; i < len; i++) {
      if ("".equals(temp[i])) {
        continue;
      }
      sb.append(temp[i]);
      if (i != len - 1) {
        sb.append(";");
      }
    }
    temp = sb.toString().split(";");
    return temp;
  }

  public static GraphPoet createGraph(String filePath, int kind) throws Exception {
    gP = new GraphPoet("");
    if (kind == 2) {
      WRerIO wRerIO = new WRerIO();
      wRerIO.reader(filePath, gP);
      cnt = 0;
      id = 0;
      flag = false;
      return gP;
    } else if (kind == 1) {
      StreamIO streamIO = new StreamIO();
      streamIO.reader(filePath, gP);
      cnt = 0;
      id = 0;
      flag = false;
      return gP;
    } else if (kind == 3) {
      NIO nio = new NIO();
      nio.reader(filePath, gP);
      cnt = 0;
      id = 0;
      flag = false;
      return gP;
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
        gP.setGraphName(name);
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
        if (!temp[i].equals("Word")) {
          MyLog.logger.error("VertexTypeException:不匹配的节点类型");
          throw new VertexTypeException("不匹配的节点类型：" + temp[i]);
        }
      }
    } else if (m2.find()) {
      String t = m2.group(1);
      t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
      String[] temp = t.split("\\#+");
      temp = rmNullEle(temp);
      // if (!temp[0].matches("\\w+")) {
      // MyLog.logger.error("InvalidLabelException:不合法的Label：顶点Label含有不合法字符");
      // throw new InvalidLabelException("不合法的Label：顶点Label含有不合法字符");
      // }
      if (temp[1].equals("Word")) {
        try {
          gP.addVertex(new Word(temp[0]));
        } catch (Exception e) {
          Word word2 = new Word(temp[0] + "" + id);
          gP.addVertex(word2);
          MyLog.logger.warn("createGraph:因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
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
        if (!temp[i].equals("WordNeighborhood")) {
          MyLog.logger.error("EdgeTypeException:不匹配的边类型");
          throw new EdgeTypeException("不匹配的边类型：" + temp[i]);
        }
      }
    } else if (m4.find()) {
      String t = m4.group(1);
      t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
      String[] temp = t.split("\\#+");
      temp = rmNullEle(temp);
      List<Vertex> list = new ArrayList<>();
      list.add(new Word(temp[3]));
      list.add(new Word(temp[4]));
      WordNeighborhood w = new WordNeighborhood(temp[0], Double.valueOf(temp[2]));
      w.addVertices(list);
      try {
        gP.addEdge(w);
      } catch (ExistedEdgeException e) {
        WordNeighborhood wm = new WordNeighborhood(temp[0] + "" + id, Double.valueOf(temp[2]));
        wm.addVertices(list);
        gP.addEdge(wm);
        MyLog.logger.warn("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
        System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
      } catch (Exception e) {
        throw e;
      }
    } else {

    }
  }
}
