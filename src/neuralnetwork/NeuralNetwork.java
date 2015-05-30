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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chad
 */
public class NeuralNetwork {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        NeuralNetwork brain = new NeuralNetwork();
        brain.run();
    }

    /**
     * 
     */
    private void run() {
        DataSet trainingSet = buildSet("irisdata.csv");
        DataSet testingSet = trainingSet.removePercent(30);
        
        Set<String> targetValues = trainingSet.getTargetValues();
        Set<String> attributeNames = trainingSet.getAttributeNames();
        
        ClassificationBrain brain = new ClassificationBrain(trainingSet, 10, 10);
        this.evaluate(brain, testingSet);
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
            System.out.println(output + " : " + expected);
            if(output.equals(expected)) {
                correct++;
            }
        }
        double percent = (correct / testingSet.size())*100;
        System.out.println("\n\n\t\tAccuracy: " + percent);
        return percent;
    }
    
}
