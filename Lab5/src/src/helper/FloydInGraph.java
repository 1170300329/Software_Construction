package src.helper;

import java.util.ArrayList;
import java.util.List;

public class FloydInGraph {
  private static int INF = Integer.MAX_VALUE;
  private double[][] dist;
  private int[][] path;
  private static List<List<Integer>> result = new ArrayList<>();
  private List<int[][]> list = new ArrayList<>();

  public static List<List<Integer>> getPath() {
    return result;
  }

  public static double findAllPath(double[][] g, int begin, int end) {
    result = new ArrayList<>();
    FloydInGraph graph = new FloydInGraph(g.length);
    graph.findCheapestPath(begin, end, g);
    for (int i = 0; i < result.size() - 1; i++) {
      for (int j = i + 1; j < result.size(); j++) {
        if (result.get(i).containsAll(result.get(j)) && result.get(j).containsAll(result.get(i))) {
          result.remove(j);
          j--;
        }
      }
    }
    return graph.dist[begin][end];
  }

  public void findCheapestPath(int begin, int end, double[][] matrix) {
    floyd(matrix);
    for (int[][] pth : list) {
      List<Integer> temp = new ArrayList<>();
      temp.add(begin);
      findPath(begin, end, pth, temp);
      temp.add(end);
      result.add(temp);
    }
  }

  public void findPath(int i, int j, int[][] pth, List<Integer> temp) {
    int k = pth[i][j];
    if (k == -1)
      return;
    findPath(i, k, pth, temp);
    temp.add(k);
    findPath(k, j, pth, temp);
  }

  public void floyd(double[][] matrix) {
    list.add(path);
    int size = matrix.length;
    // System.out.println(size);
    // initialize dist and path
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        path[i][j] = -1;
        dist[i][j] = matrix[i][j];
      }
    }
    // System.out.println("path"+path.length);
    for (int k = 0; k < size; k++) {
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          if (dist[i][k] != INF && dist[k][j] != INF && dist[i][k] + dist[k][j] < dist[i][j]) {
            dist[i][j] = dist[i][k] + dist[k][j];
            for (int[][] t : list) {
              t[i][j] = k;
            }
          }
          if (dist[i][k] != INF && dist[k][j] != INF
              && Math.abs(dist[i][k] + dist[k][j] - dist[i][j]) < 0.0000001) {
            List<int[][]> list1 = null;
            for (int[][] t : list) {
              list1 = new ArrayList<>();
              int[][] temp = new int[size][size];
              // System.out.println(size);
              for (int x = 0; x < t.length; x++) {
                // System.out.println("tleng"+t.length);
                for (int y = 0; y < t[x].length; y++) {
                  // System.out.println("txlength"+t[x].length);
                  temp[x][y] = t[x][y];
                }
              }
              temp[i][j] = k;
              list1.add(temp);
            }
            for (int[][] s : list1) {
              list.add(s);
            }
          }
        }
      }
    }

  }

  public FloydInGraph(int size) {
    this.path = new int[size][size];
    this.dist = new double[size][size];
  }
}
