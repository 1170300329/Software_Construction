package src.helper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import src.graph.ConcreteGraph;

public class BarChart extends ApplicationFrame {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public BarChart(String applicationTitle, String chartTitle, List<Long> list) {
    super(applicationTitle);
    JFreeChart barChart = ChartFactory.createBarChart(chartTitle, "Strategy", "Time",
        createDataset(list), PlotOrientation.VERTICAL, true, true, false);

    ChartPanel chartPanel = new ChartPanel(barChart);
    chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
    setContentPane(chartPanel);
  }

  private CategoryDataset createDataset(List<Long> list) {
    final String Read = "Read";
    final String Write = "Write";

    final String kind1 = "STR";
    final String kind2 = "WRT";
    final String kind3 = "NIO";

    final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    dataset.addValue(list.get(0), Read, kind1);
    dataset.addValue(list.get(3), Write, kind1);
    dataset.addValue(list.get(1), Read, kind2);
    dataset.addValue(list.get(4), Write, kind2);
    dataset.addValue(list.get(2), Read, kind3);
    dataset.addValue(list.get(5), Write, kind3);
    return dataset;
  }

  public static void showTime(ConcreteGraph g, List<Long> list) {
    BarChart chart = new BarChart(g.getGraphName(), "", list);
    chart.pack();
    RefineryUtilities.centerFrameOnScreen(chart);
    JButton close = new JButton("close");
    close.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        chart.dispose();
      }
    });
    chart.add(close);
    chart.setVisible(true);
  }
}
