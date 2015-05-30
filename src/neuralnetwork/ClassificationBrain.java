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
    
    public ClassificationBrain(DataSet exampleData, int numLayers, int maxExtraNeurons) {
        super();
//        Set<String> inputKeys = exampleData.getOne().getAttributeKeys();
        outputKeys = exampleData.getTargetValues();
        try {
            int baseNeurons = outputKeys.size();
            NeuronLayer outputLayer = this.buildBrain(numLayers, baseNeurons, maxExtraNeurons+baseNeurons, exampleData.getOne());
            outputLayer.renameNeurons(outputKeys);
        } catch (Exception ex) {
            Logger.getLogger(ClassificationBrain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String classify(DataPoint point) {
        //DataPoint point = new DataPoint(data);
        Iterator<NeuronLayer> iter = this.neuronLayers.iterator();
        while(iter.hasNext()) {
            NeuronLayer layer = iter.next();
            point = layer.classify(point);
        }
        
        String outputClass = null;
        double maxValue = Double.NEGATIVE_INFINITY;
        // this should now be the output of the last layer
        Set<String> keys = point.getAttributeKeys();
        System.out.println(keys);
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

    @Override
    public void train(DataSet trainingSet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
