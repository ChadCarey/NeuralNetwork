/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import DataSet.DataPoint;
import DataSet.DataSet;
import java.util.ArrayList;
import java.util.List;

/**
 * contains the methods needed by all brain-type classes
 * @author chad
 */
abstract class Brain {
    List<NeuronLayer> neuronLayers = new ArrayList<NeuronLayer>();
    
    /**
     * handles building the brain
     * @param numLayers
     * @param numOutputs
     * @param maxAdditionalNeurons 
     */
    NeuronLayer buildBrain(int numLayers, int numOutputs, int maxAdditionalNeurons, DataPoint exampleData) throws Exception {
        
        if(maxAdditionalNeurons < numOutputs) {
            Exception e = new Exception("maxNeurons must be greater or equal to numOutputs");
            throw new Exception();
        }
        // first inputs are the attributes of the dataPoint
        DataPoint dummyData = new DataPoint(exampleData);
        NeuronLayer lastLayer = null;
        
        // once for each layer
        for(int i = 0; i < numLayers; ++i) {
            
            int numNeurons = numOutputs;
            
            // if this is not the last layer
            // we choose a reandom number of neurons between numOutputs and maxNeurons
            if(i+1 < numLayers) {
                numNeurons += (Math.random()*maxAdditionalNeurons);
                assert(numNeurons > 0);
                assert(numNeurons < maxAdditionalNeurons);
                assert(numNeurons >= numOutputs);
            }
           
            System.out.println("Layer " + (i+1) + " of " + numLayers + ", numNeurons = " + numNeurons);
            lastLayer = appendLayer(numNeurons, dummyData);
            dummyData.clear();
            // after creating the layer, the number of Neurons
            
            for(int j = 0; j < numNeurons; ++j) {
                // only the last neuronlayer's names matter as far as classification goes
                // but the names are needed to identify each nearon in the neuronLyaer
                dummyData.put(j+"", j+"");
            }
        }
        return lastLayer; // this is to allow the brain to customize the output layer
    }

    private NeuronLayer appendLayer(int numNeurons, DataPoint exampleData) {
        NeuronLayer layer = new NeuronLayer(numNeurons, exampleData.getAttributeKeys());
        this.neuronLayers.add(layer);
        return layer;
    }
    
    /**
     * displays the brain configuration
     */
    public void printBraint() {
        
    }
    
    public abstract void train(DataSet trainingSet);
    
}
