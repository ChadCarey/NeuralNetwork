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
import java.util.List;
import java.util.Set;

/**
 *
 * @author chad
 */
public class Neuron {
    
    protected final static double LEARNING_RATE = 0.2;
    
    private HashMap<String, Double> inputWeights = new HashMap<String, Double>();
    protected String neuronName;
    private DataPoint lastInput;
    protected double regressionOutput;
    protected double output;
    protected double error;
        
    public final static String BIAS_INPUT = "BIAS_INPUT";
    protected final static double BIAS_VALUE = 1.0;
    
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
        lastInput = point;
        double inputSum = 0;
        Set<String> keys = point.getAttributeKeys();
        Iterator<String> keysIter = keys.iterator();
        while(keysIter.hasNext()) {
            //String attributeName = keysIter.next();
            String key = keysIter.next();
            String value = point.get(key);
            
            try{
                Double weight = inputWeights.get(key);
                if(value != null && weight != null)
                    inputSum += Double.parseDouble(value) * weight;
            } catch (NumberFormatException e) {
                Logger.logMsg(Logger.ERROR, "Failed to classify instance, not a numeric value: " + value);
                e.printStackTrace();
            }
        }
        // add the bias
        inputSum += this.BIAS_VALUE * this.inputWeights.get(this.BIAS_INPUT);
        
        this.regressionOutput = inputSum;
        //System.out.print("old output: " + this.output);
        this.output = calculateOutput(inputSum);
        //System.out.println(" new ouput: " + this.output);
        
        return this.output;
    }
    
    
    /**
     * 
     * @param input 
     */
    protected void addInput(String input) {
        // start weight at a small possitive or negative value between ~0.1 and ~0.4;
        double randomNum = Math.random();
        int sign = 1;
        if((int)(randomNum * 100 % 2) == 0) {
            sign = -1;
        }
        Double weight = randomNum / 3;
        weight += 0.1;
        weight *= sign;
        //System.err.println("Input name: " + input + " Weight: " + weight);
        inputWeights.put(input, weight);
    }
    
    public double getOutput() {
        return output;
    }
    
    public String getName() {
        return this.neuronName;
    }
    
    void rename(String name) {
        this.neuronName = name;
    }

    protected double calculateOutput(double h) {
        double e = Math.E;
        double output = 1.0/(1+Math.pow(e, -h));
        //System.err.println("neuron output: " + output);
        return output;
    }
    

    /**
     * calculates the error of this neuron
     * @param targetValue 
     */
    void calculateError(double targetValue) {
        this.error = this.output*(1-this.output)*(this.output-targetValue);
    }

    /**
     * calculates the error of this node based on the connection weights it has
     * with the given neurons
     * @param connectedNeurons 
     */
    void calculateError(List<Neuron> connectedNeurons) {
        double weightedErrorSum = 0.0;
        Iterator<Neuron> iter = connectedNeurons.iterator();
        while(iter.hasNext()) {
            Neuron neuron = iter.next();
            weightedErrorSum += neuron.error * neuron.output;
        }
        this.error = this.output*(1-this.output)*weightedErrorSum;
    }

    /**
     * calculates the new weights
     * @param connectedNeurons 
     */
    void calculateWeights(List<Neuron> connectedNeurons) {
        // neuron_k:weight = neuron_k:weight - this:LearningRate * neuron_k:error * this:output
        Iterator<Neuron> iter = connectedNeurons.iterator();
        while(iter.hasNext()) {
            Neuron neuron = iter.next();
            double currentWeight = neuron.inputWeights.get(this.neuronName);
            double neuronError = neuron.error;
            double newWeight = currentWeight - LEARNING_RATE*neuronError*this.output;
            if(Math.random()*1000 < 2.0) {
                System.out.println("this neuronName: " + this.neuronName + " that neuron keys: " + neuron.inputWeights);
                System.out.println("Old weight: " + currentWeight + " new weight: " + newWeight);
            }
            neuron.inputWeights.put(this.neuronName, newWeight);
        }
    }
}
