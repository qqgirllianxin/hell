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

package ps.hell.ml.forest.classification;

import java.util.Arrays;

import ps.landerbuluse.ml.forest.model.projection.Projection;
import ps.landerbuluse.ml.math.MathBase;
import ps.landerbuluse.ml.math.matrix.decomposition.EigenValueDecomposition2;


/**
 * Fisher线性判别。费舍尔定义两个之间的分离
*分布的类间方差的比值
*类内方差,在某种意义上,一个措施
*信噪比的类标签。盛名发现一个线性
*结合最大化投影后的分离的特性。
*可以用于降维结果组合
*之前后分类
 * Fisher's linear discriminant. Fisher defined the separation between two
 * distributions to be the ratio of the variance between the classes to
 * the variance within the classes, which is, in some sense, a measure
 * of the signal-to-noise ratio for the class labeling. FLD finds a linear
 * combination of features which maximizes the separation after the projection.
 * The resulting combination may be used for dimensionality reduction
 * before later classification.
 * <p>
 * The terms Fisher's linear discriminant and LDA are often used
 * interchangeably, although FLD actually describes a slightly different
 * discriminant, which does not make some of the assumptions of LDA such
 * as normally distributed classes or equal class covariances.
 * When the assumptions of LDA are satisfied, FLD is equivalent to LDA.
 * <p>
 * FLD is also closely related to principal component analysis (PCA), which also
 * looks for linear combinations of variables which best explain the data.
 * As a supervised method, FLD explicitly attempts to model the
 * difference between the classes of data. On the other hand, PCA is a
 * unsupervised method and does not take into account any difference in class.
 * <p>
 * One complication in applying FLD (and LDA) to real data
 * occurs when the number of variables/features does not exceed
 * the number of samples. In this case, the covariance estimates do not have
 * full rank, and so cannot be inverted. This is known as small sample size
 * problem.
 * 
 * @see LDA
 * @see smile.projection.PCA
 * 
 * @author Haifeng Li
 */
public class FLD implements Classifier<double[]>, Projection<double[]> {
    /**
     * The dimensionality of data.
     */
    private final int p;
    /**
     * The number of classes.
     */
    private final int k;
    /**
     * Original common mean vector.
     */
    private final double[] mean;
    /**
     * Original class mean vectors.
     */
    private final double[][] mu;
    /**
     * Project matrix.
     */
    private final double[][] scaling;
    /**
     * Projected common mean vector.
     */
    private final double[] smean;
    /**
     * Projected class mean vectors.
     */
    private final double[][] smu;

    /**
     * Trainer for Fisher's linear discriminant.
     */
    public static class Trainer extends ClassifierTrainer<double[]> {
        /**
         * The dimensionality of mapped space.
         */
        private int L = -1;
        /**
         * A tolerance to decide if a covariance matrix is singular. The trainer
         * will reject variables whose variance is less than tol<sup>2</sup>.
         */
        private double tol = 1E-4;

        /**
         * Constructor. The dimensionality of mapped space will be k - 1,
         * where k is the number of classes of data. The default tolerance
         * to covariance matrix singularity is 1E-4.
         */
        public Trainer() {
        }
        
        /**
         * Sets the dimensionality of mapped space.
         * 
         * @param L the dimensionality of mapped space.
         */
        public void setDimension(int L) {
            if (L < 1) {
                throw new IllegalArgumentException("Invalid mapping space dimension: " + L);
            }

            this.L = L;
        }
        
        /**
         * Sets covariance matrix singular tolerance.
         * 
         * @param tol a tolerance to decide if a covariance matrix is singular.
         * The trainer will reject variables whose variance is less than tol<sup>2</sup>.
         */
        public void setTolerance(double tol) {
            if (tol < 0.0) {
                throw new IllegalArgumentException("Invalid tol: " + tol);
            }

            this.tol = tol;
        }
        
        @Override
        public FLD train(double[][] x, int[] y) {
            return new FLD(x, y, L, tol);
        }
    }
    
    /**
     * Constructor. Learn Fisher's linear discriminant.
     * @param x training instances.
     * @param y training labels in [0, k), where k is the number of classes.
     */
    public FLD(double[][] x, int[] y) {
        this(x, y, -1);
    }

    /**
     * Constructor. Learn Fisher's linear discriminant.
     * @param x training instances.
     * @param y training labels in [0, k), where k is the number of classes.
     * @param L the dimensionality of mapped space.
     */
    public FLD(double[][] x, int[] y, int L) {
        this(x, y, L, 1E-4);
    }

    /**
     * Constructor. Learn Fisher's linear discriminant.
     * @param x training instances.
     * @param y training labels in [0, k), where k is the number of classes.
     * @param L the dimensionality of mapped space.
     * @param tol a tolerance to decide if a covariance matrix is singular; it
     * will reject variables whose variance is less than tol<sup>2</sup>.
     */
    public FLD(double[][] x, int[] y, int L, double tol) {
        if (x.length != y.length) {
            throw new IllegalArgumentException(String.format("The sizes of X and Y don't match: %d != %d", x.length, y.length));
        }

        // class label set.
        int[] labels = MathBase.unique(y);
        Arrays.sort(labels);
        
        for (int i = 0; i < labels.length; i++) {
            if (labels[i] < 0) {
                throw new IllegalArgumentException("Negative class label: " + labels[i]); 
            }
            
            if (i > 0 && labels[i] - labels[i-1] > 1) {
                throw new IllegalArgumentException("Missing class: " + labels[i]+1);                 
            }
        }

        k = labels.length;
        if (k < 2) {
            throw new IllegalArgumentException("Only one class.");            
        }
        
        if (tol < 0.0) {
            throw new IllegalArgumentException("Invalid tol: " + tol);
        }
        
        if (x.length <= k) {
            throw new IllegalArgumentException(String.format("Sample size is too small: %d <= %d", x.length, k));
        }

        if (L >= k) {
            throw new IllegalArgumentException(String.format("The dimensionality of mapped space is too high: %d >= %d", L, k));
        }

        if (L <= 0) {
            L = k - 1;
        }

        final int n = x.length;
        p = x[0].length;

        // The number of instances in each class.
        int[] ni = new int[k];
        // Common mean vector.
        mean = MathBase.colMean(x);
        // Common covariance.
        double[][] T = new double[p][p];
        // Class mean vectors.
        mu = new double[k][p];

        for (int i = 0; i < n; i++) {
            int c = y[i];
            ni[c]++;
            for (int j = 0; j < p; j++) {
                mu[c][j] += x[i][j];
            }
        }

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < p; j++) {
                mu[i][j] = mu[i][j] / ni[i] - mean[j];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < p; j++) {
                for (int l = 0; l <= j; l++) {
                    T[j][l] += (x[i][j] - mean[j]) * (x[i][l] - mean[l]);
                }
            }
        }

        for (int j = 0; j < p; j++) {
            for (int l = 0; l <= j; l++) {
                T[j][l] /= n;
                T[l][j] = T[j][l];
            }
        }

        // Between class scatter
        double[][] B = new double[p][p];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < p; j++) {
                for (int l = 0; l <= j; l++) {
                    B[j][l] += mu[i][j] * mu[i][l];
                }
            }
        }

        for (int j = 0; j < p; j++) {
            for (int l = 0; l <= j; l++) {
                B[j][l] /= k;
                B[l][j] = B[j][l];
            }
        }

        EigenValueDecomposition2 eigen = EigenValueDecomposition2.decompose(T, true);
        
        tol = tol * tol;
        double[] s = eigen.getEigenValues();
        for (int i = 0; i < s.length; i++) {
            if (s[i] < tol) {
                throw new IllegalArgumentException("The covariance matrix is close to singular.");
            }

            s[i] = 1.0 / s[i];
        }

        double[][] U = eigen.getEigenVectors();
        double[][] UB = MathBase.atbmm(U, B);

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < p; j++) {
                UB[i][j] *= s[j];
            }
        }

        MathBase.abmm(U, UB, B);

        eigen = EigenValueDecomposition2.decompose(B, true);

        U = eigen.getEigenVectors();
        scaling = new double[p][L];
        for (int i = 0; i < p; i++) {
            System.arraycopy(U[i], 0, scaling[i], 0, L);
        }
        
        smean = new double[L];
        MathBase.atx(scaling, mean, smean);
        smu = MathBase.abmm(mu, scaling);
    }

    @Override
    public int predict(double[] x) {
        if (x.length != p) {
            throw new IllegalArgumentException(String.format("Invalid input vector size: %d, expected: %d", x.length, p));
        }

        double[] wx = project(x);

        int y = 0;
        double nearest = Double.POSITIVE_INFINITY;
        for (int i = 0; i < k; i++) {
            double d = MathBase.distance(wx, smu[i]);
            if (d < nearest) {
                nearest = d;
                y = i;
            }
        }

        return y;
    }

    /**
     * Predicts the class label of an instance and also calculate a posteriori
     * probabilities. Not supported.
     */
    @Override
    public int predict(double[] x, double[] posteriori) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public double[] project(double[] x) {
        if (x.length != p) {
            throw new IllegalArgumentException(String.format("Invalid input vector size: %d, expected: %d", x.length, p));
        }

        double[] y = new double[scaling[0].length];
        MathBase.atx(scaling, x, y);
        y=MathBase.minus(y, smean);
        return y;
    }

    @Override
    public double[][] project(double[][] x) {
        double[][] y = new double[x.length][scaling[0].length];
        
        for (int i = 0; i < x.length; i++) {
            if (x[i].length != p) {
                throw new IllegalArgumentException(String.format("Invalid input vector size: %d, expected: %d", x[i].length, p));
            }

            MathBase.atx(scaling, x[i], y[i]);
           y[i]= MathBase.minus(y[i], smean);
        }
        
        return y;
    }

    /**
     * Returns the projection matrix W. The dimension reduced data can be obtained
     * by y = W' * x.
     */
    public double[][] getProjection() {
        return scaling;
    }
}
