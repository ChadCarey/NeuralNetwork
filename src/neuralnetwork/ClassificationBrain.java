/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import DataSet.DataPoint;
import DataSet.DataSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chad
 */
public class ClassificationBrain extends Brain {

    private Set<String> outputKeys;
    
    public ClassificationBrain(BrainConfig brainConfig, DataSet exampleData) {
        super();
        outputKeys = exampleData.getTargetValues();
        try {
            // ensure that the brain config has the correct number of output nodes (one for each class)
            if(brainConfig.getOutputLayer() != outputKeys.size()) {
                brainConfig.add(outputKeys.size());
            }
            outputLayer = this.buildBrain(brainConfig, exampleData.getOne());
            outputLayer.renameNeurons(outputKeys);
        } catch (Exception ex) {
            Logger.getLogger(ClassificationBrain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param p
     * @return 
     */
    private DataPoint run(DataPoint p) {
        DataPoint point = new DataPoint(p);
        Iterator<NeuronLayer> iter = this.neuronLayers.iterator();
        while(iter.hasNext()) {
            NeuronLayer layer = iter.next();
            point = layer.classify(point);
        }
        return point;
    }
    
    public String classify(DataPoint point) {
        //DataPoint point = new DataPoint(data);
        point = this.run(point);
        
        String outputClass = null;
        double maxValue = Double.NEGATIVE_INFINITY;
        // this should now be the output of the last layer
        Set<String> keys = point.getAttributeKeys();
        //System.out.println(keys);
        Iterator<String> kIter = keys.iterator();
        while(kIter.hasNext()) {
            String key = kIter.next();
            String output = point.get(key);
            double value = Double.parseDouble(output);
            if(value > maxValue) {
                maxValue = value;
                outputClass = key;
            }
        }
        return outputClass;
    }

    public void train(DataSet trainingSet, int iterations) {
        for(int i = 0; i < iterations; ++i) {
            Iterator<DataPoint> pIter = trainingSet.iterator();
            while(pIter.hasNext()) {
                DataPoint point = pIter.next();
                // run the point through the network
                this.run(point);
                // learn from mistakes
                learn(point);
            }
            // randomize after each iteration
            //trainingSet.randomize();
        }
        
    }
    
    /**
     * teaches the network
     * @param point
     * @param results 
     */
    private void learn(DataPoint correctPoint) {
        // get last layer
        // give lastLayer the expected output values
        NeuronLayer lastLayer = this.getLast().learn(correctPoint);
        // reverse through the neuron layers (skip the one that was done already)
        for(int i = this.neuronLayers.size()-2; i >= 0; --i) {
            //give the privious layer the lastLayer (last accessed layer)
            // set the last layer to the layer that was just accessed
            lastLayer = this.neuronLayers.get(i).learn(lastLayer);
        }
        
    }
    
    /**
     * calculates the total network error
     * @param point
     * @param results
     * @return 
     */
    private DataPoint calculateOutputErrors(DataPoint point, DataPoint results) {
       String correctAnswer = point.getTargetValue();
       DataPoint errors = new DataPoint();
       // iterate through the results
       Iterator<String> iter = results.getAttributeKeys().iterator();
       while(iter.hasNext()) {
           String key = iter.next();
           double currentActivation = Double.parseDouble(results.get(key));
           double targetActivation = 0;
           // calculate the error for each result
           // the correct class should be 1
           if(correctAnswer.equals(key)) {
               targetActivation = 1;
           } // the rest should be 0
           
           // currentActivation(1-currentActivation)(currentActivation-targetActivation)
           double err = currentActivation*(1-currentActivation)*(currentActivation-targetActivation);
           errors.put(key, err);
       } 
       return errors;
    }
    
}
