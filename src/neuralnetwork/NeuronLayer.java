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
    
    private List<Neuron> neurons = new ArrayList<Neuron>(); // when building don't forget the bias input
    
    NeuronLayer(int numNeurons, Set<String> inputNames) {
        // make a neuron for each name
        for(int i = 0; i < numNeurons; ++i) {
            String neuronName = i+"";
            neurons.add(new Neuron(inputNames, neuronName));
        }
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
    
}
