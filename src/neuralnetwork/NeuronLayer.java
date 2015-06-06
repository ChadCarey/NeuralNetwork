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
    
    private List<Neuron> neurons = new ArrayList<Neuron>();
    private BiasNeuron biasNeuron;
    
    NeuronLayer(int numNeurons, Set<String> inputNames) {
        // make a neuron for each name
        for(int i = 0; i < numNeurons; ++i) {
            String neuronName = i+"";
            neurons.add(new Neuron(inputNames, neuronName));
        }
        biasNeuron = new BiasNeuron();
    }
    
    /**
     * 
     * @param targetValues
     * @param inputNames
     */
    NeuronLayer(Set<String> inputNames, ArrayList<String> neuronNames) {
        // make a neuron for each name
        Iterator<String> iter = neuronNames.iterator();
        while(iter.hasNext()) {
            String neuronName = iter.next();
            neurons.add(new Neuron(inputNames, neuronName));
        }
    }
    
    public DataPoint classify(DataPoint point) {
        DataPoint outputs = new DataPoint();
        //ArrayList<Double> outputs = new ArrayList<Double>();
        Iterator<Neuron> iter = neurons.iterator();
        while(iter.hasNext()) {
            Neuron neuron = iter.next();
            double value = neuron.classify(point);
            outputs.put(neuron.getName(), value);
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
    
    public void learn() {
        
    }
    
    public int getNeuronCount() {
        return this.neurons.size();
    }
    
    public void renameNeurons(Set<String> names) {
        Iterator<String> sIter = names.iterator();
        Iterator<Neuron> nIter = neurons.iterator();
        for(int i = 0; nIter.hasNext() && sIter.hasNext(); ++i) {
            String name = sIter.next();
            Neuron neuron = nIter.next();
            System.err.print("Neuron " + neuron.getName() + " renamed to ");
            neuron.rename(name);
            System.err.println(neuron.getName());
        }
    }
    
    public Neuron getNeuron(String name) {
        Iterator<Neuron> iter = neurons.iterator();
        while(iter.hasNext()) {
            Neuron neuron = iter.next();
            if(neuron.getName().equals(name))
                return neuron;
        }
        return null;
    }

    /**
     * learns from the given data point
     * @param correctPoint
     * @return 
     */
    NeuronLayer learn(DataPoint correctPoint) {
        // loop through the neurons
        Iterator<Neuron> iter = this.neurons.iterator();
        String targetName = correctPoint.getTargetValue();
        while(iter.hasNext()) {
            // calculate() the error for each neuron and set it in the neuron
            // if this is the correct point, pass it a 1 as the target value
            // else it is a zero
            Neuron neuron = iter.next();
            double targetValue = 0.0;
            if(neuron.getName().equals(targetName)) {
                targetValue = 1.0;
            }
            neuron.calculateError(targetValue);
        }
        
        return this; // returns itself, this is just for use ease and uniformity
    }

    /**
     * learns from the given neuron layer. Note: this is expected to be the next
     * layer in a list of neuronLayers. (ie the one that used the outputs from this layer)
     * @param lastLayer
     * @return 
     */
    NeuronLayer learn(NeuronLayer followingLayer) {
        // loop through the neurons
        Iterator<Neuron> iter = this.neurons.iterator();
        while(iter.hasNext()) {
            // calculate() the error for each neuron and set it in the neuron
            // if this is the correct point, pass it a 1 as the target value
            // else it is a zero
            Neuron neuron = iter.next();
            neuron.calculateError(followingLayer.neurons);
            neuron.calculateWeights(followingLayer.neurons);
        }
        this.biasNeuron.calculateWeights(followingLayer.neurons);
        return this; // returns itself, this is just for use ease and uniformity
    }
    
}
