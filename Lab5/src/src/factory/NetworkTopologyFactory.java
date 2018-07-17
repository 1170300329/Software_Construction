package src.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import src.IO.NIO;
import src.IO.StreamIO;
import src.IO.WRerIO;
import src.edge.NetworkConnection;
import src.exception.EdgeTypeException;
import src.exception.ExistedEdgeException;
import src.exception.VertexTypeException;
import src.graph.NetworkTopology;
import src.log.MyLog;
import src.vertex.Computer;
import src.vertex.Router;
import src.vertex.Server;
import src.vertex.Vertex;

public class NetworkTopologyFactory {
  private static NetworkTopology ng;
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

  public static NetworkTopology createGraph(String filePath, int kind) throws Exception {
    ng = new NetworkTopology("");
    if (kind == 2) {
      WRerIO wRerIO = new WRerIO();
      wRerIO.reader(filePath, ng);
      cnt = 0;
      id = 0;
      flag = false;
      return ng;
    } else if (kind == 1) {
      StreamIO streamIO = new StreamIO();
      streamIO.reader(filePath, ng);
      cnt = 0;
      id = 0;
      flag = false;
      return ng;
    } else if (kind == 3) {
      NIO nio = new NIO();
      nio.reader(filePath, ng);
      cnt = 0;
      id = 0;
      flag = false;
      return ng;
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
        ng.setGraphName(name);
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

    String pattern3 = "^EdgeType\\=(.+)";
    Pattern r3 = Pattern.compile(pattern3);
    Matcher m3;

    String pattern4 = "Edge\\=\\<(.+)\\>";
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
        if (!temp[i].equals("Computer") && !temp[i].equals("Router") && !temp[i].equals("Server")) {
          MyLog.logger.error("VertexTypeException:不匹配的节点类型");
          throw new VertexTypeException("不匹配的节点类型：" + temp[i]);
        }
      }
    } else if (m2.find()) {
      String t = m2.group(1);
      t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
      String[] temp = t.split("\\#+");
      temp = rmNullEle(temp);
      if (temp[1].equals("Computer")) {
        Computer computer = new Computer(temp[0]);
        String[] args = new String[temp.length - 2];
        for (int i = 2; i < temp.length; i++) {
          args[i - 2] = temp[i];
        }
        try {
          computer.fillVertexInfo(args);
        } catch (Exception e) {
          throw e;
        }
        try {
          ng.addVertex(computer);
        } catch (Exception e) {
          Computer computer2 = new Computer(temp[0] + "" + id);
          computer2.fillVertexInfo(args);
          ng.addVertex(computer2);
          MyLog.logger.warn("Label重复将" + temp[0] + "改为" + temp[0] + "" + id);
          System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
        }
      } else if (temp[1].equals("Router")) {
        Router router = new Router(temp[0]);
        String[] args = new String[temp.length - 2];
        for (int i = 2; i < temp.length; i++) {
          args[i - 2] = temp[i];
        }
        try {
          router.fillVertexInfo(args);
        } catch (Exception e) {
          throw e;
        }
        try {
          ng.addVertex(router);
        } catch (Exception e) {
          Router router2 = new Router(temp[0] + "" + id);
          router2.fillVertexInfo(args);
          ng.addVertex(router2);
          MyLog.logger.warn("Label重复将" + temp[0] + "改为" + temp[0] + "" + id);
          System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
        }
      } else if (temp[1].equals("Server")) {
        Server server = new Server(temp[0]);
        String[] args = new String[temp.length - 2];
        for (int i = 2; i < temp.length; i++) {
          args[i - 2] = temp[i];
        }
        try {
          server.fillVertexInfo(args);
        } catch (Exception e) {
          throw e;
        }
        try {
          ng.addVertex(server);
        } catch (Exception e) {
          Server server2 = new Server(temp[0] + "" + id);
          server2.fillVertexInfo(args);
          ng.addVertex(server2);
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
        if (!temp[i].equals("NetworkConnection")) {
          MyLog.logger.error("EdgeTypeException:不匹配的边类型");
          throw new EdgeTypeException("不匹配的边类型：" + temp[i]);
        }
      }
    } else if (m4.find()) {
      String t = m4.group(1);
      t = t.replaceAll("\\“|\\”|\"|\\,|\\，|\\<|\\>", "#");
      String[] temp = t.split("\\#+");
      temp = rmNullEle(temp);

      NetworkConnection n = new NetworkConnection(temp[0], Double.valueOf(temp[2]));
      List<Vertex> list = new ArrayList<>();
      list.add(ng.getMap().get(temp[3]));
      list.add(ng.getMap().get(temp[4]));
      n.addVertices(list);
      try {
        ng.addEdge(n);
      } catch (ExistedEdgeException e) {
        NetworkConnection n1 = new NetworkConnection(temp[0] + "" + id, Double.valueOf(temp[2]));
        n1.addVertices(list);
        ng.addEdge(n1);
        System.out.println("因为Label重复，将" + temp[0] + "改为" + temp[0] + "" + id);
      } catch (Exception e) {
        MyLog.logger.warn("Label重复将" + temp[0] + "改为" + temp[0] + "" + id);
        throw e;
      }
    } else {
    }
  }
}
