/*******************************************************************************
 * Copyright (c) 2010 Haifeng Li
 *   
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package ps.hell.ml.feature.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ps.hell.base.data.Attribute;
import ps.hell.base.data.AttributeDataset;
import ps.hell.base.data.NominalAttribute;
import ps.hell.base.data.parse.ArffParser;
import ps.hell.base.data.parse.DelimitedTextParser;
import ps.hell.ml.feature.select.NumericAttributeFeature;
import ps.hell.math.base.MathBase;
import static org.junit.Assert.*;

/**
 *
 * @author Haifeng Li
 */
public class NumericAttributeFeatureTest {
    
    public NumericAttributeFeatureTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of attributes method, of class NumericAttributeFeature.
     */
    @SuppressWarnings("unused")
    @Test
    public void testAttributes() {
        System.out.println("attributes");
        DelimitedTextParser parser = new DelimitedTextParser();
        parser.setResponseIndex(new NominalAttribute("class"), 0);
        try {
            AttributeDataset data = parser.parse("USPS Train", this.getClass().getResourceAsStream("/smile/data/usps/zip.train"));
            double[][] x = data.toArray(new double[data.size()][]);
            
            NumericAttributeFeature naf = new NumericAttributeFeature(data.attributes(), NumericAttributeFeature.Scaling.LOGARITHM);
            Attribute[] attributes = naf.attributes();
            assertEquals(256, attributes.length);
            for (int i = 0; i < attributes.length; i++) {
                System.out.println(attributes[i]);
                assertEquals(Attribute.Type.NUMERIC, attributes[i].type);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Test of f method, of class NumericAttributeFeature.
     */
    @Test
    public void testNONE() {
        System.out.println("NONE");
        DelimitedTextParser parser = new DelimitedTextParser();
        parser.setResponseIndex(new NominalAttribute("class"), 0);
        try {
            AttributeDataset data = parser.parse("USPS Train", this.getClass().getResourceAsStream("/smile/data/usps/zip.train"));
            double[][] x = data.toArray(new double[data.size()][]);
            
            NumericAttributeFeature naf = new NumericAttributeFeature(data.attributes(), NumericAttributeFeature.Scaling.NONE);
            Attribute[] attributes = naf.attributes();
            assertEquals(256, attributes.length);
            for (int i = 0; i < x.length; i++) {
                double[] y = new double[attributes.length];
                for (int j = 0; j < y.length; j++) {
                    y[j] = naf.f(x[i], j);
                    assertEquals(x[i][j], y[j], 1E-7);
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Test of f method, of class NumericAttributeFeature.
     */
    @Test
    public void testLOGARITHM() {
        System.out.println("LOGARITHM");
        DelimitedTextParser parser = new DelimitedTextParser();
        parser.setResponseIndex(new NominalAttribute("class"), 0);
        try {
            AttributeDataset data = parser.parse("USPS Train", this.getClass().getResourceAsStream("/smile/data/usps/zip.train"));
            double[][] x = data.toArray(new double[data.size()][]);
            for (int i = 0; i < x.length; i++) {
                for (int j = 0; j < x[i].length; j++) {
                    x[i][j] += 2.0;
                }
            }
            
            NumericAttributeFeature naf = new NumericAttributeFeature(data.attributes(), NumericAttributeFeature.Scaling.LOGARITHM);
            Attribute[] attributes = naf.attributes();
            assertEquals(256, attributes.length);
            for (int i = 0; i < x.length; i++) {
                double[] y = new double[attributes.length];
                for (int j = 0; j < y.length; j++) {
                    y[j] = naf.f(x[i], j);
                    assertEquals(MathBase.log(x[i][j]), y[j], 1E-7);
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Test of f method, of class NumericAttributeFeature.
     */
    @Test
    public void testNORMALIZATION() {
        System.out.println("NORMALIZATION");
        DelimitedTextParser parser = new DelimitedTextParser();
        parser.setResponseIndex(new NominalAttribute("class"), 0);
        try {
            AttributeDataset data = parser.parse("USPS Train", this.getClass().getResourceAsStream("/smile/data/usps/zip.train"));
            double[][] x = data.toArray(new double[data.size()][]);
            double[] min = MathBase.colMin(x);
            double[] max = MathBase.colMax(x);
            
            NumericAttributeFeature naf = new NumericAttributeFeature(data.attributes(), NumericAttributeFeature.Scaling.NORMALIZATION, x);
            Attribute[] attributes = naf.attributes();
            assertEquals(256, attributes.length);
            for (int i = 0; i < x.length; i++) {
                double[] y = new double[attributes.length];
                for (int j = 0; j < y.length; j++) {
                    y[j] = naf.f(x[i], j);
                    assertEquals((x[i][j]-min[j])/(max[j]-min[j]), y[j], 1E-7);
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Test of f method, of class NumericAttributeFeature.
     */
    @Test
    public void testSTANDARDIZATION() {
        System.out.println("STANDARDIZATION");
        DelimitedTextParser parser = new DelimitedTextParser();
        parser.setResponseIndex(new NominalAttribute("class"), 0);
        try {
            AttributeDataset data = parser.parse("USPS Train", this.getClass().getResourceAsStream("/smile/data/usps/zip.train"));
            double[][] x = data.toArray(new double[data.size()][]);
            double[] mean = MathBase.colMean(x);
            double[] sd = MathBase.colSd(x);
            
            NumericAttributeFeature naf = new NumericAttributeFeature(data.attributes(), NumericAttributeFeature.Scaling.STANDARDIZATION, x);
            Attribute[] attributes = naf.attributes();
            assertEquals(256, attributes.length);
            for (int i = 0; i < x.length; i++) {
                double[] y = new double[attributes.length];
                for (int j = 0; j < y.length; j++) {
                    y[j] = naf.f(x[i], j);
                    assertEquals((x[i][j]-mean[j])/sd[j], y[j], 1E-7);
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Test of f method, of class NumericAttributeFeature.
     */
    @Test
    public void testNORMALIZATIONWinsorization() {
        System.out.println("NORMALIZATION  Winsorization");
        ArffParser parser = new ArffParser();
        try {
            AttributeDataset data = parser.parse(this.getClass().getResourceAsStream("/smile/data/weka/regression/abalone.arff"));
            double[][] x = data.toArray(new double[data.size()][]);
            
            NumericAttributeFeature naf = new NumericAttributeFeature(data.attributes(), 0.05, 0.95, x);
            Attribute[] attributes = naf.attributes();
            assertEquals(data.attributes().length-1, attributes.length);
            for (int i = 0; i < x.length; i++) {
                double[] y = new double[attributes.length];
                for (int j = 0; j < y.length; j++) {
                    y[j] = naf.f(x[i], j);
                    assertTrue(y[j] <= 1.0 && y[j] >= 0.0);
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Test of f method, of class NumericAttributeFeature.
     */
    @Test
    public void testROBUSTSTANDARDIZATION() {
        System.out.println("ROBUST STANDARDIZATION");
        ArffParser parser = new ArffParser();
        try {
            AttributeDataset data = parser.parse(this.getClass().getResourceAsStream("/smile/data/weka/regression/abalone.arff"));
            double[][] x = data.toArray(new double[data.size()][]);
            
            NumericAttributeFeature naf = new NumericAttributeFeature(data.attributes(), x);
            Attribute[] attributes = naf.attributes();
            assertEquals(data.attributes().length-1, attributes.length);
            for (int i = 0; i < x.length; i++) {
                double[] y = new double[attributes.length];
                for (int j = 0; j < y.length; j++) {
                    y[j] = naf.f(x[i], j);
                }
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}
