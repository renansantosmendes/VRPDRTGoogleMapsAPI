/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgorithmsResults;

import ProblemRepresentation.Solution;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author renansantos
 */
public class ResultsGraphicsForMultiObjectiveOptimization {

    private List<Solution> population;
    private JFreeChart paretoCombined;
    private String folder;
    private String paretoCombinedFileName;

    public ResultsGraphicsForMultiObjectiveOptimization(List<Solution> population, String folder, String paretoCombinedFileName) throws IOException {
        this.population = population;
        this.folder = folder;
        this.paretoCombinedFileName = paretoCombinedFileName;
        boolean successForCreateFolder = (new File(folder)).mkdirs();
        this.buildParetoGraphic();
        this.showGraphic();
    }

    public void buildParetoGraphic() throws FileNotFoundException, IOException {
        this.paretoCombined = ChartFactory.createScatterPlot("Combined Pareto Set", "F1", "F2", createDataset(),
                PlotOrientation.VERTICAL, true, true, false);
        this.saveGraphicInFile(new FileOutputStream(folder + "/" + paretoCombinedFileName));
    }

    private XYDataset createDataset() {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries series = new XYSeries("NSGA-II");
        for (int i = 0; i < this.population.size(); i++) {
            double x = population.get(i).getAggregatedObjective1();
            double y = population.get(i).getAggregatedObjective2();
            series.add(x, y);
        }
        result.addSeries(series);
        return result;
    }

    private void saveGraphicInFile(OutputStream out) throws IOException {
        ChartUtilities.writeChartAsPNG(out, this.paretoCombined, 1024, 600);
    }

    private void showGraphic() throws FileNotFoundException, IOException {
        JFrame frame = new JFrame("Combined Pareto Set");
        frame.add(this.getPanel());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.pack();
        frame.setVisible(true);

//        byte[] b = new byte[2048];
//        int length;
//        InputStream is
//        OutputStream os = new FileOutputStream(folder + "/" + paretoCombinedFileName);
//        while ((length = is.read(b)) != -1) {
//            os.write(b, 0, length);
//        }
//        is.close();
//        os.close();
    }

    public JPanel getPanel() {
        return new ChartPanel(paretoCombined);
    }
}
