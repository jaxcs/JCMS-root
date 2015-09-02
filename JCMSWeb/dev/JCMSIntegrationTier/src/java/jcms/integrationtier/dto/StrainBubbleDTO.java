/****
Copyright (c) 2015 The Jackson Laboratory

This is free software: you can redistribute it and/or modify it 
under the terms of the GNU General Public License as published by  
the Free Software Foundation, either version 3 of the License, or  
(at your option) any later version.
 
This software is distributed in the hope that it will be useful,  
but WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
General Public License for more details.

You should have received a copy of the GNU General Public License 
along with this software.  If not, see <http://www.gnu.org/licenses/>.
****/

package jcms.integrationtier.dto;

import jcms.integrationtier.colonyManagement.StrainEntity;
import java.util.SortedMap;

/**
 *
 * @author mkamato
 */
public class StrainBubbleDTO {
    private int numberLiving = 0;
    private int numberCages = 0;
    private int numberMatings = 0;
    private int numberGreenFemales = 0;
    private int numberYellowFemales = 0;
    private int numberRedFemales = 0;
    private int numberGreenMales = 0;
    private int numberYellowMales = 0;
    private int numberRedMales = 0;
    private StrainEntity strain = null;
    private SortedMap[] genotypes = null;
    private int noGenotype = 0;

    /**
     * @return the numberLiving
     */
    public int getNumberLiving() {
        return numberLiving;
    }

    /**
     * @param numberLiving the numberLiving to set
     */
    public void setNumberLiving(int numberLiving) {
        this.numberLiving = numberLiving;
    }

    /**
     * @return the numberCages
     */
    public int getNumberCages() {
        return numberCages;
    }

    /**
     * @param numberCages the numberCages to set
     */
    public void setNumberCages(int numberCages) {
        this.numberCages = numberCages;
    }

 
    /**
     * @return the strain
     */
    public StrainEntity getStrain() {
        return strain;
    }

    /**
     * @param strain the strain to set
     */
    public void setStrain(StrainEntity strain) {
        this.strain = strain;
    }

    /**
     * @return the numberYellowFemales
     */
    public int getNumberYellowFemales() {
        return numberYellowFemales;
    }

    /**
     * @param numberYellowFemales the numberYellowFemales to set
     */
    public void setNumberYellowFemales(int numberYellowFemales) {
        this.numberYellowFemales = numberYellowFemales;
    }

    /**
     * @return the numberGreenFemales
     */
    public int getNumberGreenFemales() {
        return numberGreenFemales;
    }

    /**
     * @param numberGreenFemales the numberGreenFemales to set
     */
    public void setNumberGreenFemales(int numberGreenFemales) {
        this.numberGreenFemales = numberGreenFemales;
    }

    /**
     * @return the numberRedFemales
     */
    public int getNumberRedFemales() {
        return numberRedFemales;
    }

    /**
     * @param numberRedFemales the numberRedFemales to set
     */
    public void setNumberRedFemales(int numberRedFemales) {
        this.numberRedFemales = numberRedFemales;
    }

    /**
     * @return the numberYellowMales
     */
    public int getNumberYellowMales() {
        return numberYellowMales;
    }

    /**
     * @param numberYellowMales the numberYellowMales to set
     */
    public void setNumberYellowMales(int numberYellowMales) {
        this.numberYellowMales = numberYellowMales;
    }

    /**
     * @return the numberGreenMales
     */
    public int getNumberGreenMales() {
        return numberGreenMales;
    }

    /**
     * @param numberGreenMales the numberGreenMales to set
     */
    public void setNumberGreenMales(int numberGreenMales) {
        this.numberGreenMales = numberGreenMales;
    }

    /**
     * @return the numberRedMales
     */
    public int getNumberRedMales() {
        return numberRedMales;
    }

    /**
     * @param numberRedMales the numberRedMales to set
     */
    public void setNumberRedMales(int numberRedMales) {
        this.numberRedMales = numberRedMales;
    }

    /**
     * @return the numberMatings
     */
    public int getNumberMatings() {
        return numberMatings;
    }

    /**
     * @param numberMatings the numberMatings to set
     */
    public void setNumberMatings(int numberMatings) {
        this.numberMatings = numberMatings;
    }

    /**
     * @return the genotypes
     */
    public SortedMap[] getGenotypes() {
        return genotypes;
    }

    /**
     * @param genotypes the genotypes to set
     */
    public void setGenotypes(SortedMap[] genotypes) {
        this.genotypes = genotypes;
    }

    /**
     * @return the noGenotype
     */
    public int getNoGenotype() {
        return noGenotype;
    }

    /**
     * @param noGenotype the noGenotype to set
     */
    public void setNoGenotype(int noGenotype) {
        this.noGenotype = noGenotype;
    }
    
}
