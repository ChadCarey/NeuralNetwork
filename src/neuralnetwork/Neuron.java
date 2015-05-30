/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import DataSet.DataPoint;
import com.sun.media.jfxmedia.logging.Logger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author chad
 */
public class Neuron {
    private final static String BIAS_INPUT = "BIAS_INPUT";
    private final static double BIAS_VALUE = 1.0;
    private final static double THRESHOLD = 0;
    private final static double LEARNING_RATE = 0.2;
    private HashMap<String, Double> inputWeights = new HashMap<String, Double>();
    private String neuronName;
    private double output = -1;
    private DataPoint lastPoint;
    
    /**
     * 
     * @param inputs
     * @param newNeuronName 
     */
    public Neuron(Set<String> inputs, String newNeuronName) {
        this.neuronName = newNeuronName;
        Iterator<String> iter = inputs.iterator();
        while(iter.hasNext()) {
            String inputName = iter.next();
            this.addInput(inputName);
        }
        this.addInput(BIAS_INPUT);
    }
    
    /**
     * 
     * @param point
     * @return 
     */
    public double input(DataPoint point) {
        return 0.0;
    }
    
    /**
     * classifies the dataPoint
     * @param point
     * @return 
     */
    public double classify(DataPoint point) {
        lastPoint = point;
        double inputSum = 0;
        Set<String> keys = point.getAttributeKeys();
        Iterator<String> keysIter = keys.iterator();
        while(keysIter.hasNext()) {
            //String attributeName = keysIter.next();
            String key = keysIter.next();
            String value = point.get(key);
            
            try{
                inputSum += Double.parseDouble(value) * inputWeights.get(key);
            } catch (NumberFormatException e) {
                Logger.logMsg(Logger.ERROR, "Failed to classify instance, not a numeric value: " + value);
                e.printStackTrace();
            }
        }
        // add the bias
        inputSum += this.BIAS_VALUE * this.inputWeights.get(this.BIAS_INPUT);
        output = calculateOutput(inputSum);
        
        return output;
    }
    
    
    /**
     * 
     * @param input 
     */
    private void addInput(String input) {
        // start weight at a small possitive or negative value between ~0.1 and ~0.4;
        // common practice, -1/sqrt(n) < w < 1/sqrt(n)
        Double weight = Math.random() / 3;
        weight += 0.1;
        System.err.println(input + weight);
        inputWeights.put(input, weight);
    }
    
    public double getOutput() {
        return output;
    }
    
    public String getName() {
        return this.neuronName;
    }

    private double calculateOutput(double h) {
        double e = Math.E;
        return 1.0/(1-Math.pow(e, -h));
    }
}