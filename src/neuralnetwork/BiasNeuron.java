/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author chad
 */
class BiasNeuron extends Neuron {

    public BiasNeuron() {
        super(new HashSet<String>(), new String());
        this.output = 1.0;
        this.neuronName = BIAS_INPUT;
        this.regressionOutput = 1.0;
    }
    
    @Override
    public double getOutput() {
        return 1.0;
    }
}
