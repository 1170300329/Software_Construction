package simulation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ladder.Ladder;
import log.MyLog;
import monkey.Monkey;

public class Simulator {
  private int n;
  private int h;
  private int t;
  private static int N;
  private int k;
  private int MV;
  private static int num = 0;
  private Set<Ladder> set = Collections.synchronizedSet(new HashSet<>());
  private static List<List<Double>> chart = new ArrayList<>();

  public void readFile(String filePath) throws IOException {
    BufferedReader bReader = null;
    File f = new File(filePath);
    FileReader reader = new FileReader(f);
    bReader = new BufferedReader(reader);
    String s = "";
    s = bReader.readLine();
    MyLog.logger.info(s);
    String pattern =
        "n\\s*=\\s*(\\d+)\\s*,\\s*h\\s*=\\s*(\\d+)\\s*,\\s*t\\s*=\\s*(\\d+)\\s*,\\s*N\\s*=\\s*(\\d+)\\s*,\\s*k\\s*=\\s*(\\d+)\\s*,\\s*MV\\s*=\\s*(\\d+)";
    Pattern r = Pattern.compile(pattern);
    Matcher m;
    m = r.matcher(s);
    if (m.find()) {
      n = Integer.valueOf(m.group(1));
      h = Integer.valueOf(m.group(2));
      t = Integer.valueOf(m.group(3));
      N = Integer.valueOf(m.group(4));
      k = Integer.valueOf(m.group(5));
      MV = Integer.valueOf(m.group(6));
    } else {
      System.out.println("信息格式有误");
    }
    bReader.close();
  }

  public void Initial() {
    for (int i = 0; i < n; i++) {
      set.add(new Ladder(i + 1));
    }
  }

  public void MonkeyGenerator() throws InterruptedException {
    long time1 = System.currentTimeMillis();
    List<Integer> list = Collections.synchronizedList(new ArrayList<>());
    ExecutorService exe = Executors.newCachedThreadPool();
    int times = N / k;
    int rest = N % k;
    for (int i = 0; i < times; i++) {
      for (int j = 0; j < k; j++) {
        String d = Math.random() > 0.5 ? "R->L" : "L->R";
        exe.execute(new Thread(new Monkey(++num, d, (int) ((Math.random() * 5) + 1), set, list)));// 换成注释里面的语句即可产生差别较大的速度
        // exe.execute(new Thread(new Monkey(++num, d, (Math.random() > 0.5 ? 5 : 1), set, list)));
      }
      Thread.sleep(t * 1000);
    }
    for (int m = 0; m < rest; m++) {
      String d = Math.random() > 0.5 ? "R->L" : "L->R";
      exe.execute(new Thread(new Monkey(++num, d, (int) ((Math.random() * 5) + 1), set, list)));// 换成注释里面的语句即可产生差别较大的速度
      // exe.execute(new Thread(new Monkey(++num, d, (Math.random() > 0.5 ? 5 : 1), set, list)));
    }
    long time2 = 0;
    exe.shutdown();
    while (true) {
      if (exe.isTerminated()) {
        time2 = System.currentTimeMillis();
        break;
      }
    }

    List<Double> temp = new ArrayList<>();
    temp.add((N * 1.0) / ((time2 - time1) / 1000));
    MyLog.logger.info("吞吐率为：" + (N * 1.0) / ((time2 - time1) / 1000));
    int c = 0, d = 0;
    for (int i = 0; i < list.size() - 1; i++) {
      for (int j = i + 1; j < list.size(); j++) {
        d++;
        if (list.get(j) < list.get(i)) {
          c++;
        }
      }
    }
    MyLog.logger.info("公平性为：" + ((d - c - c) * 1.0 / d));
    temp.add(((d - c - c) * 1.0 / d));
    chart.add(temp);
  }

  public void writeto(String filePath) throws IOException {
    BufferedReader bReader = null;
    File f = new File("src/log/event.log");
    FileReader reader = new FileReader(f);
    bReader = new BufferedReader(reader);
    String s = "";
    File file = new File(filePath);
    FileWriter writer = new FileWriter(file);
    BufferedWriter bwriter = new BufferedWriter(writer);
    while ((s = bReader.readLine()) != null) {
      bwriter.write(s + "\n");
    }
    FileOutputStream testfile = new FileOutputStream("src/log/event.log");
    testfile.write(new String("").getBytes());
    testfile.close();
    bReader.close();
    bwriter.close();
    writer.close();
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    Simulator simulation = new Simulator();
    simulation.readFile("src/readfile/test.txt");
    simulation.Initial();
    simulation.MonkeyGenerator();
    simulation.writeto("src/simu1/exp7.txt");
    num = 0;

    simulation.readFile("src/readfile/test1.txt");
    simulation.Initial();
    simulation.MonkeyGenerator();
    simulation.writeto("src/simu1/exp8.txt");
    num = 0;

    simulation.readFile("src/readfile/test2.txt");
    simulation.Initial();
    simulation.MonkeyGenerator();
    simulation.writeto("src/simu1/exp9.txt");
    num = 0;

    // simulation.readFile("src/readfile/test3.txt");
    // simulation.Initial();
    // simulation.MonkeyGenerator();
    // simulation.writeto("src/simu4/exp4.txt");
    // num = 0;
    //
    // simulation.readFile("src/readfile/test4.txt");
    // simulation.Initial();
    // simulation.MonkeyGenerator();
    // simulation.writeto("src/simu4/exp5.txt");
    // BarChart.showTime(chart);

  }
}
