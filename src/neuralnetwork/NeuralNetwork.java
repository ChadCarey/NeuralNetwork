/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import DataSet.DataPoint;
import DataSet.DataSet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chad
 */
public class NeuralNetwork {

    final private static int TRAINING_ITERATIONS = 500;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NeuralNetwork brain = new NeuralNetwork();
        System.out.println("iris");
        brain.run("irisdata.csv");
        System.out.println("diabetes");
        brain.run("diabetes.csv");
    }

    /**
     * 
     */
    private void run(String filename) {
        DataSet trainingSet = buildSet(filename);
        trainingSet.randomize();
        DataSet testingSet = trainingSet.removePercent(30);
        
        Set<String> targetValues = trainingSet.getTargetValues();
        Set<String> attributeNames = trainingSet.getAttributeNames();
        ArrayList<BrainConfig> configList = BrainConfig.loadBrainList("configList.csv");
        Iterator<BrainConfig> iter = configList.iterator();
        while(iter.hasNext()) {
            
            BrainConfig config = iter.next();
            ClassificationBrain brain = new ClassificationBrain(config, trainingSet);
            double oldAccuracy = this.evaluate(brain, testingSet);
            brain.train(trainingSet, TRAINING_ITERATIONS);
            double accuracy = this.evaluate(brain, testingSet);
            System.out.println("\t\told:      " + oldAccuracy);
            
            // write to csv
            writeResults(brain, accuracy);
        }
        
        System.out.print("\n\nDone!\n\n");
    }

    /**
     * 
     * @param filename
     * @return 
     */
    private DataSet buildSet(String filename) {
        DataSet set = null;
        try {
            set = new DataSet(filename);
        } catch (IOException ex) {
            //Logger.getLogger(.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        set.randomize();
        return set;
    }

    private double evaluate(ClassificationBrain brain, DataSet testingSet) {
        double correct = 0.0;
        Iterator <DataPoint> pIter = testingSet.iterator();
        while(pIter.hasNext()){
            DataPoint point = pIter.next();
            String expected = point.getTargetValue();
            String output = brain.classify(point);
        //    System.out.println(output + " : " + expected);
            if(output.equals(expected)) {
                correct++;
            }
        }
        double percent = (correct / testingSet.size())*100;
        System.out.println("\n\n\t\tAccuracy: " + percent);
        return percent;
    }

    private void writeResults(ClassificationBrain brain, double accuracy) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
