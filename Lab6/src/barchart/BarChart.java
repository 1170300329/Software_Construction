package barchart;

import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class BarChart extends ApplicationFrame {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public BarChart(String applicationTitle, String chartTitle, List<List<Double>> chart2) {
    super(applicationTitle);
    JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Strategy", "Time",
        createDataset(chart2), PlotOrientation.VERTICAL, true, true, false);

    ChartPanel chartPanel = new ChartPanel(barChart);
    chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
    setContentPane(chartPanel);
  }

  private CategoryDataset createDataset(List<List<Double>> chart2) {
    final String Th = "Th";
    final String F = "F";

    final String kind1 = "k=1";
    final String kind2 = "k=2";
    final String kind3 = "k=3";
    // final String kind4 = "t=4";
    // final String kind5 = "t=5";

    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    dataset.addValue(chart2.get(0).get(0), Th, kind1);
    dataset.addValue(chart2.get(0).get(1), F, kind1);
    dataset.addValue(chart2.get(1).get(0), Th, kind2);
    dataset.addValue(chart2.get(1).get(1), F, kind2);
    dataset.addValue(chart2.get(2).get(0), Th, kind3);
    dataset.addValue(chart2.get(2).get(1), F, kind3);
    // dataset.addValue(chart2.get(3).get(0), Th, kind4);
    // dataset.addValue(chart2.get(3).get(1), F, kind4);
    // dataset.addValue(chart2.get(4).get(0), Th, kind5);
    // dataset.addValue(chart2.get(4).get(1), F, kind5);
    return dataset;
  }

  public static void showTime(List<List<Double>> chart2) {
    BarChart chart = new BarChart("", "Change k", chart2);
    chart.pack();
    RefineryUtilities.centerFrameOnScreen(chart);
    chart.setVisible(true);
  }
}
