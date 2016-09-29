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
import ps.hell.ml.forest.classification.LogisticRegression;
import ps.hell.math.base.MathBase;
import ps.hell.ml.statistics.validation.LOOCV;
import static org.junit.Assert.*;

/**
 *
 * @author Haifeng
 */
public class LogisticRegressionTest {

    public LogisticRegressionTest() {
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
     * Test of learn method, of class LogisticRegression.
     */
    @Test
    public void testIris2() {
        System.out.println("Iris binary");
        ArffParser arffParser = new ArffParser();
        arffParser.setResponseIndex(4);
        try {
            AttributeDataset iris = arffParser.parse(this.getClass().getResourceAsStream("/smile/data/weka/iris.arff"));
            double[][] x = iris.toArray(new double[iris.size()][]);
            int[] y = iris.toArray(new int[iris.size()]);

            for (int i = 0; i < y.length; i++) {
                if (y[i] == 2) {
                    y[i] = 1;
                } else {
                    y[i] = 0;
                }
            }

            int n = x.length;
            LOOCV loocv = new LOOCV(n);
            int error = 0;
            for (int i = 0; i < n; i++) {
                double[][] trainx = MathBase.slice(x, loocv.train[i]);
                int[] trainy = MathBase.slice(y, loocv.train[i]);
                LogisticRegression logit = new LogisticRegression(trainx, trainy);

                if (y[loocv.test[i]] != logit.predict(x[loocv.test[i]]))
                    error++;
            }

            System.out.println("Logistic Regression error = " + error);
            assertEquals(3, error);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Test of learn method, of class LogisticRegression.
     */
    @Test
    public void testIris() {
        System.out.println("Iris");
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
                LogisticRegression logit = new LogisticRegression(trainx, trainy);

                if (y[loocv.test[i]] != logit.predict(x[loocv.test[i]]))
                    error++;
            }

            System.out.println("Logistic Regression error = " + error);
            assertEquals(3, error);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Test of learn method, of class LogisticRegression.
     */
    @Test
    public void testSegment() {
        System.out.println("Segment");
        ArffParser arffParser = new ArffParser();
        arffParser.setResponseIndex(19);
        try {
            AttributeDataset train = arffParser.parse(this.getClass().getResourceAsStream("/smile/data/weka/segment-challenge.arff"));
            AttributeDataset test = arffParser.parse(this.getClass().getResourceAsStream("/smile/data/weka/segment-test.arff"));

            double[][] x = train.toArray(new double[train.size()][]);
            int[] y = train.toArray(new int[train.size()]);
            double[][] testx = test.toArray(new double[test.size()][]);
            int[] testy = test.toArray(new int[test.size()]);

            LogisticRegression logit = new LogisticRegression(x, y, 0.05, 1E-3, 1000);
            
            int error = 0;
            for (int i = 0; i < testx.length; i++) {
                if (logit.predict(testx[i]) != testy[i]) {
                    error++;
                }
            }

            System.out.format("Segment error rate = %.2f%%\n", 100.0 * error / testx.length);
            assertEquals(48, error);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    /**
     * Test of learn method, of class LogisticRegression.
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
            
            LogisticRegression logit = new LogisticRegression(x, y, 0.3, 1E-3, 1000);
            
            int error = 0;
            for (int i = 0; i < testx.length; i++) {
                if (logit.predict(testx[i]) != testy[i]) {
                    error++;
                }
            }

            System.out.format("USPS error rate = %.2f%%\n", 100.0 * error / testx.length);
            assertEquals(188, error);
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
}