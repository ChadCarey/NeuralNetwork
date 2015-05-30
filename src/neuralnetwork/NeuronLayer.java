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
 *
 * @author chad
 */
public class NeuronLayer {
    
    final static int MAX_LAYERS = 3;
    private List<Neuron> neurons = new ArrayList<Neuron>(); // when building don't forget the bias input
    
    /**
     * 
     * @param targetValues
     * @param attributeNames
     * @param numLayers 
     */
    private NeuronLayer(Set<String> attributeNames, int numNeurons) {
        this.buildLayer(attributeNames, numNeurons);
    }
    
    /**
     * 
     * @param neuronName 
     */
    private void addNeuron(String neuronName) {
        // create then add the neuron
        // call the next neuronLayer to do the same
    }
    
    /**
     * 
     * @param layer 
     */
    private void connectNeuronLayer(NeuronLayer layer) {
        // connects the neuronLayer to this one
    }
    
    /**
     * 
     * @param targetValues
     * @param attributeNames 
     */
    private void buildLayer(Set<String> targetValues, Set<String> attributeNames) {
        // we need one neuron for each possible target class in the set
        // create a neuron for each target value
        Iterator<String> iter = targetValues.iterator();
        while(iter.hasNext()) {
            String neuronName = iter.next();
            neurons.add(new Neuron(attributeNames, neuronName));
        }
    }
    
    public DataPoint classify(DataPoint point) {
        DataPoint outputs = new DataPoint();
        //ArrayList<Double> outputs = new ArrayList<Double>();
        Iterator<Neuron> iter = neurons.iterator();
        while(iter.hasNext()) {
            Neuron neuron = iter.next();
            outputs.put(neuron.getName(), neuron.classify(point));
        }
        return outputs;
    }
    
    /**
     * training function, NOT COMPLETE!
     * @param training 
     */
    public void train(DataSet training) {
        Iterator<DataPoint> pIter = training.iterator();
        while(pIter.hasNext()) {
            DataPoint point = pIter.next();
            ArrayList<Double> outputs = new ArrayList<Double>();
            Iterator<Neuron> nIter = neurons.iterator();
            while(nIter.hasNext()) {
                Neuron neuron = nIter.next();
     //           outputs.add(neuron.learn(point));
            }
        }
    }
    
    public String getTargetName(ArrayList<Double> result) {
        for(int i = 0; i < result.size(); ++i) {
            if(result.get(i) > 0) {
                return this.neurons.get(i).getName();
            }
        }
        return "";
    }
    
}
