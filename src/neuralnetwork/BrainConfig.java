/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

import Utilities.UtilityBelt;
import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chad
 */
public class BrainConfig extends ArrayList<Integer> {
    
    
    /**
     * 
     * @param config 
     */
    public BrainConfig(String config) {
        String[] layers = config.split(",");
        for(int i = 0; i < layers.length; ++i) {
            try {
                String layer = layers[i];
                System.out.println("Layer: " + layer);
                this.add(Integer.parseInt(layer));
            } catch (NumberFormatException e) {
                System.err.println("invalid file format, must be all integer values");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
    
    public static ArrayList<BrainConfig> loadBrainList(String filename) {
        ArrayList<BrainConfig> list = new ArrayList<BrainConfig>();
        BufferedReader reader = UtilityBelt.getFileReader(filename);
        String config = null;
        try {
            while((config = reader.readLine()) != null) {
                list.add(new BrainConfig(config));
            }
        } catch (IOException ex) {
            Logger.getLogger(BrainConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }

    /**
     * returns the number of neurons in the outputLayer
     * @return 
     */
    public int getOutputLayer() {
        int outputSize = 0;
        if(this.size() > 0) {
            return this.get(this.size()-1);
        }
        return outputSize;
    }
}
