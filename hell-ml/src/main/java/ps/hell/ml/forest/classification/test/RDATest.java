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

package ps.hell.ml.forest.classification.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ps.hell.base.data.AttributeDataset;
import ps.hell.base.data.NominalAttribute;
import ps.hell.base.data.parse.ArffParser;
import ps.hell.base.data.parse.DelimitedTextParser;
import ps.hell.ml.forest.classification.RDA;
import ps.hell.math.base.MathBase;
import ps.hell.ml.statistics.validation.LOOCV;
import static org.junit.Assert.*;

/**
 *
 * @author Haifeng Li
 */
public class RDATest {

    public RDATest() {
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
     * Test of learn method, of class RDA.
     */
    @Test
    public void testLearn() {
        System.out.println("learn");
        ArffParser arffParser = new ArffParser();
        arffParser.setResponseIndex(4);
        try {
            AttributeDataset iris = arffParser.parse(this.getClass().getResourceAsStream("/smile/data/weka/iris.arff"));
            double[][] x = iris.toArray(new double[iris.size()][]);
            int[] y = iris.toArray(new int[iris.size()]);

            int n = x.length;
            LOOCV loocv = new LOOCV(n);
            int error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = MathBase.slice(x, loocv.train[i]);
                int[] trainy = MathBase.slice(y, loocv.train[i]);

                RDA rda = new RDA(trainx, trainy, 0.0);
                if (y[loocv.test[i]] != rda.predict(x[loocv.test[i]]))
                     error++;
             }
             System.out.println("RDA (0.0) error = " + error);
             assertEquals(22, error);

            error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = MathBase.slice(x, loocv.train[i]);
                int[] trainy = MathBase.slice(y, loocv.train[i]);

                RDA rda = new RDA(trainx, trainy, 0.1);
                if (y[loocv.test[i]] != rda.predict(x[loocv.test[i]]))
                     error++;
             }
             System.out.println("RDA (0.1) error = " + error);
             assertEquals(24, error);

            error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = MathBase.slice(x, loocv.train[i]);
                int[] trainy = MathBase.slice(y, loocv.train[i]);

                RDA rda = new RDA(trainx, trainy, 0.2);
                if (y[loocv.test[i]] != rda.predict(x[loocv.test[i]]))
                     error++;
             }
             System.out.println("RDA (0.2) error = " + error);
             assertEquals(20, error);

            error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = MathBase.slice(x, loocv.train[i]);
                int[] trainy = MathBase.slice(y, loocv.train[i]);

                RDA rda = new RDA(trainx, trainy, 0.3);
                if (y[loocv.test[i]] != rda.predict(x[loocv.test[i]]))
                     error++;
             }
             System.out.println("RDA (0.3) error = " + error);
             assertEquals(19, error);

            error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = MathBase.slice(x, loocv.train[i]);
                int[] trainy = MathBase.slice(y, loocv.train[i]);

                RDA rda = new RDA(trainx, trainy, 0.4);
                if (y[loocv.test[i]] != rda.predict(x[loocv.test[i]]))
                     error++;
             }
             System.out.println("RDA (0.4) error = " + error);
             assertEquals(16, error);

            error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = MathBase.slice(x, loocv.train[i]);
                int[] trainy = MathBase.slice(y, loocv.train[i]);

                RDA rda = new RDA(trainx, trainy, 0.5);
                if (y[loocv.test[i]] != rda.predict(x[loocv.test[i]]))
                     error++;
             }
             System.out.println("RDA (0.5) error = " + error);
             assertEquals(12, error);

            error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = MathBase.slice(x, loocv.train[i]);
                int[] trainy = MathBase.slice(y, loocv.train[i]);

                RDA rda = new RDA(trainx, trainy, 0.6);
                if (y[loocv.test[i]] != rda.predict(x[loocv.test[i]]))
                     error++;
             }
             System.out.println("RDA (0.6) error = " + error);
             assertEquals(11, error);

            error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = MathBase.slice(x, loocv.train[i]);
                int[] trainy = MathBase.slice(y, loocv.train[i]);

                RDA rda = new RDA(trainx, trainy, 0.7);
                if (y[loocv.test[i]] != rda.predict(x[loocv.test[i]]))
                     error++;
             }
             System.out.println("RDA (0.7) error = " + error);
             assertEquals(9, error);

            error = 0;
            double[] posteriori = new double[3];
            for (int i = 0; i < n; i++) {
                double[][] trainx = MathBase.slice(x, loocv.train[i]);
                int[] trainy = MathBase.slice(y, loocv.train[i]);

                RDA rda = new RDA(trainx, trainy, 0.8);
                if (y[loocv.test[i]] != rda.predict(x[loocv.test[i]], posteriori))
                     error++;
                
                //System.out.println(posteriori[0]+"\t"+posteriori[1]+"\t"+posteriori[2]);
             }
             System.out.println("RDA (0.8) error = " + error);
             assertEquals(6, error);

            error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = MathBase.slice(x, loocv.train[i]);
                int[] trainy = MathBase.slice(y, loocv.train[i]);

                RDA rda = new RDA(trainx, trainy, 0.9);
                if (y[loocv.test[i]] != rda.predict(x[loocv.test[i]]))
                     error++;
             }
             System.out.println("RDA (0.9) error = " + error);
             assertEquals(3, error);

            error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = MathBase.slice(x, loocv.train[i]);
                int[] trainy = MathBase.slice(y, loocv.train[i]);

                RDA rda = new RDA(trainx, trainy, 1.0);
                if (y[loocv.test[i]] != rda.predict(x[loocv.test[i]]))
                     error++;
             }
             System.out.println("RDA (1.0) error = " + error);
             assertEquals(4, error);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Test of learn method, of class RDA.
     */
    @Test
    public void testUSPS() {
        System.out.println("USPS");
        DelimitedTextParser parser = new DelimitedTextParser();
        parser.setResponseIndex(new NominalAttribute("class"), 0);
        try {
            AttributeDataset train = parser.parse("USPS Train", this.getClass().getResourceAsStream("/smile/data/usps/zip.train"));
            AttributeDataset test = parser.parse("USPS Test", this.getClass().getResourceAsStream("/smile/data/usps/zip.test"));

            double[][] x = train.toArray(new double[train.size()][]);
            int[] y = train.toArray(new int[train.size()]);
            double[][] testx = test.toArray(new double[test.size()][]);
            int[] testy = test.toArray(new int[test.size()]);
            
            RDA rda = new RDA(x, y, 0.7);
            
            int error = 0;
            for (int i = 0; i < testx.length; i++) {
                if (rda.predict(testx[i]) != testy[i]) {
                    error++;
                }
            }

            System.out.format("USPS error rate = %.2f%%\n", 100.0 * error / testx.length);
            assertEquals(235, error);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}