package src.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import src.IO.IO;
import src.IO.NIO;
import src.IO.StreamIO;
import src.IO.WRerIO;
import src.factory.GraphFactory;
import src.graph.ConcreteGraph;
import src.helper.BarChart;
import src.helper.ParseCommandHelper;

public class MovieGraphApp {
  /**
   * App for clients.
   * 
   * @param args main args.
   * @throws Exception throw exception.
   */
  public static void main(String[] args) throws Exception {
    List<Long> list = new ArrayList<>();
    System.out.println(">------输入\"exit\"退出,\"cmd --help\"可以查看帮助。(字符串之间用空格隔开，请不要用引号！)-----");
    Scanner sb = new Scanner(System.in);
    String filepath = null;
    while (true) {
      System.out.println(">请输入读入文件路径：");
      System.out.print(">");
      filepath = sb.nextLine();
      ConcreteGraph g = null;
      try {
        final long t1 = System.currentTimeMillis();
        g = (ConcreteGraph) GraphFactory.createGraph(filepath, 1);
        final long t2 = System.currentTimeMillis();
        g = (ConcreteGraph) GraphFactory.createGraph(filepath, 2);
        final long t3 = System.currentTimeMillis();
        g = (ConcreteGraph) GraphFactory.createGraph(filepath, 3);
        final long t4 = System.currentTimeMillis();
        long g1 = t2 - t1;
        long g2 = t3 - t2;
        long g3 = t4 - t3;
        list.add(g1);
        list.add(g2);
        list.add(g3);
        System.out.println("STR->g1:" + g1);
        System.out.println("WRT->g2:" + g2);
        System.out.println("NIO->g3:" + g3);
        // MyLog.logger.info("读取文件：" + filepath);
      } catch (Exception e) {
        System.out.println(e.getMessage());
        continue;
      }
      while (true) {
        String temp = null;
        System.out.print(">");
        temp = sb.nextLine();
        if (!temp.equals("exit")) {
          try {
            ParseCommandHelper.parseAndExecuteCommand(temp, g, sb);
          } catch (Exception e) {
            System.out.println(e.getMessage());
            continue;
          }
        } else {
          final long time1 = System.currentTimeMillis();

          IO io = new StreamIO();
          io.writer("src/src/graph.txt", g);
          final long time2 = System.currentTimeMillis();

          io = new WRerIO();
          io.writer("src/src/graph1.txt", g);
          final long time3 = System.currentTimeMillis();

          io = new NIO();
          io.writer("src/src/graph2.txt", g);
          final long time4 = System.currentTimeMillis();
          long gap1 = time2 - time1;
          long gap2 = time3 - time2;
          long gap3 = time4 - time3;
          list.add(gap1);
          list.add(gap2);
          list.add(gap3);
          System.out.println("STRgap1:" + gap1);
          System.out.println("WRTgap2:" + gap2);
          System.out.println("NIOgap3:" + gap3);
          System.out.println("Exit");
          BarChart.showTime(g, list);
          sb.close();
          return;
        }
      }
    }
  }
}
