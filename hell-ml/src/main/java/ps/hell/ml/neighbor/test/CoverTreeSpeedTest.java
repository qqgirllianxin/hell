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

package ps.hell.ml.neighbor.test;

import org.junit.*;
import ps.hell.base.data.AttributeDataset;
import ps.hell.base.data.NominalAttribute;
import ps.hell.base.data.parse.DelimitedTextParser;
import ps.hell.math.base.distance.EuclideanDistance;
import ps.hell.ml.neighbor.CoverTree;
import ps.hell.ml.neighbor.Neighbor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Haifeng Li
 */
public class CoverTreeSpeedTest {
    double[][] x = null;
    double[][] testx = null;
    CoverTree<double[]> coverTree = null;

    public CoverTreeSpeedTest() {
        long start = System.currentTimeMillis();
        DelimitedTextParser parser = new DelimitedTextParser();
        parser.setResponseIndex(new NominalAttribute("class"), 0);
        try {
            AttributeDataset train = parser.parse("USPS Train", this.getClass().getResourceAsStream("/smile/data/usps/zip.train"));
            AttributeDataset test = parser.parse("USPS Test", this.getClass().getResourceAsStream("/smile/data/usps/zip.test"));

            x = train.toArray(new double[train.size()][]);
            testx = test.toArray(new double[test.size()][]);
        } catch (Exception ex) {
            System.err.println(ex);
        }

        double time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("Loading data: %.2fs\n", time);

        start = System.currentTimeMillis();
        coverTree = new CoverTree<double[]>(x, new EuclideanDistance());
        time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("Building cover tree: %.2fs\n", time);
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
     * Test of nearest method, of class CoverTree.
     */
    @Test
    public void testCoverTree() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < testx.length; i++) {
            coverTree.nearest(testx[i]);
        }
        double time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("NN: %.2fs\n", time);

        start = System.currentTimeMillis();
        for (int i = 0; i < testx.length; i++) {
            coverTree.knn(testx[i], 10);
        }
        time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("10-NN: %.2fs\n", time);

        start = System.currentTimeMillis();
        List<Neighbor<double[], double[]>> n = new ArrayList<Neighbor<double[], double[]>>();
        for (int i = 0; i < testx.length; i++) {
            coverTree.range(testx[i], 8.0, n);
            n.clear();
        }
        time = (System.currentTimeMillis() - start) / 1000.0;
        System.out.format("Range: %.2fs\n", time);
    }
}