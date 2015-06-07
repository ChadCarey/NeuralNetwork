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
import java.util.List;
import java.util.Set;

/**
 * contains the methods needed by all brain-type classes
 * @author chad
 */
abstract class Brain {
    protected List<NeuronLayer> neuronLayers = new ArrayList<NeuronLayer>();
    protected NeuronLayer outputLayer;
    
    /**
     * handles building the brain
     * @param numLayers
     * @param numOutputs
     * @param maxAdditionalNeurons 
     */
    NeuronLayer buildBrain(BrainConfig brainConfig, DataPoint exampleData) throws Exception {
        // first inputs are the attributes of the dataPoint
        DataPoint dummyData = new DataPoint(exampleData);
        NeuronLayer lastLayer = null;
        
        // once for each layer
        int numLayers = brainConfig.size();
        int count = 0;
        Iterator<Integer> iter = brainConfig.iterator();
        while(iter.hasNext()) {
            int numNeurons = iter.next();
            count++;
           
            System.out.println("Layer " + count + " of " + numLayers + ", numNeurons = " + numNeurons);
            lastLayer = appendLayer(numNeurons, dummyData);
            dummyData.clear();
            // after creating the layer, the number of Neurons
            
            for(int j = 0; j < numNeurons; ++j) {
                /*  only the last neuronlayer's names matter as far as classification goes
                    but the names are needed to identify each nearon in the neuronLyaer
                    so I am numbering them */
                dummyData.put( j+"", j+"");
            }
        }
        return lastLayer; // this is to allow the brain to customize the output layer
    }

    private NeuronLayer appendLayer(int numNeurons, DataPoint exampleData) {
        Set<String> names = exampleData.getAttributeKeys();
        System.out.println(names);
        NeuronLayer layer = new NeuronLayer(numNeurons, names);
        this.neuronLayers.add(layer);
        return layer;
    }
    
    /**
     * displays the brain configuration
     */
    public void printBrain() {
        
    }
    
    /**
     * returns the output layer
     * @return 
     */
    protected NeuronLayer getLast() {
        int size = this.neuronLayers.size();
        if(size > 0)
            return this.neuronLayers.get(size-1);
        else
            return null;
    }
    
}
