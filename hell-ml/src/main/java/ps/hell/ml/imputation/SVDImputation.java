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
package ps.hell.ml.imputation;

import ps.hell.math.base.MathBase;
import ps.hell.math.base.matrix.decomposition.SingularValueDecomposition2;


/**
 * 缺失值与奇异值分解归责。鉴于圣言
* = Uσ;V <一口> T < / >一同晚餐,我们使用最重要的特征向量
* V <一口> T < /一口>线性估计缺失值。虽然它已经
*显示几个重要的特征向量是充分的描述
*数据与小错误,特征向量的具体分数最好
*估计需要经验决定的。一旦k最重要
*特征向量从V <一口> T < /一口>被选中,我们估计缺失值j
*在行我先回归这一行对k特征向量,然后使用
*系数回归重建j的一个线性组合
* k的特征向量。行我和j的j th价值的k值
*特征向量不习惯在决定这些回归系数。
*值得注意的是,圣言只能进行完整的矩阵;
*因此我们最初由其他方法填补所有缺失值
*矩阵A,获得”。然后,我们利用期望最大化方法
*到达最终的估计,如下所示。每一个缺失值估计
*使用上面的算法,然后在新重复该过程
*获得矩阵,直到矩阵低于总变化
*经验确定阈值(比如0.01)
 * Missing value imputation with singular value decomposition. Given SVD
 * A = U &Sigma; V<sup>T</sup>, we use the most significant eigenvectors of
 * V<sup>T</sup> to linearly estimate missing values. Although it has been
 * shown that several significant eigenvectors are sufficient to describe
 * the data with small errors, the exact fraction of eigenvectors best for
 * estimation needs to be determined empirically. Once k most significant
 * eigenvectors from V<sup>T</sup> are selected, we estimate a missing value j
 * in row i by first regressing this row against the k eigenvectors and then use
 * the coefficients of the regression to reconstruct j from a linear combination
 * of the k eigenvectors. The j th value of row i and the j th values of the k
 * eigenvectors are not used in determining these regression coefficients.
 * It should be noted that SVD can only be performed on complete matrices;
 * therefore we originally fill all missing values by other methods in
 * matrix A, obtaining A'. We then utilize an expectation maximization method to
 * arrive at the final estimate, as follows. Each missing value in A is estimated
 * using the above algorithm, and then the procedure is repeated on the newly
 * obtained matrix, until the total change in the matrix falls below the
 * empirically determined threshold (say 0.01).
 *
 * @author Haifeng Li
 */
public class SVDImputation implements MissingValueImputation {

    /**
     * The number of eigenvectors used for imputation.
     */
    private int k;

    /**
     * Constructor.
     * @param k the number of eigenvectors used for imputation.
     */
    public SVDImputation(int k) {
        if (k < 1) {
            throw new IllegalArgumentException("Invalid number of eigenvectors for imputation: " + k);
        }

        this.k = k;
    }

    @Override
    public void impute(double[][] data) throws MissingValueImputationException {
        impute(data, 10);
    }
    
    /**
     * Impute missing values in the dataset.
     * @param data a data set with missing values (represented as Double.NaN).
     * On output, missing values are filled with estimated values.
     * @param maxIter the maximum number of iterations.
     */
    public void impute(double[][] data, int maxIter) throws MissingValueImputationException {
        if (maxIter < 1) {
            throw new IllegalArgumentException("Invalid maximum number of iterations: " + maxIter);
        }

        int[] count = new int[data[0].length];
        for (int i = 0; i < data.length; i++) {
            int n = 0;
            for (int j = 0; j < data[i].length; j++) {
                if (Double.isNaN(data[i][j])) {
                    n++;
                    count[j]++;
                }
            }

            if (n == data[i].length) {
                throw new MissingValueImputationException("The whole row " + i + " is missing");
            }
        }

        for (int i = 0; i < data[0].length; i++) {
            if (count[i] == data.length) {
                throw new MissingValueImputationException("The whole column " + i + " is missing");
            }
        }

        double[][] full = new double[data.length][];
        for (int i = 0; i < full.length; i++) {
            full[i] = data[i].clone();
        }

        KMeansImputation.columnAverageImpute(full);

        for (int iter = 0; iter < maxIter; iter++) {
            svdImpute(data, full);
        }

        for (int i = 0; i < data.length; i++) {
            System.arraycopy(full[i], 0, data[i], 0, data[i].length);
        }
    }

    /**
     * Impute the missing values by SVD.
     * @param rawData the raw data with missing values.
     * @param data the data with current imputations.
     */
    private void svdImpute(double[][] raw, double[][] data) {
        SingularValueDecomposition2 svd = SingularValueDecomposition2.decompose(data);

        int d = data[0].length;

        for (int i = 0; i < raw.length; i++) {
            int missing = 0;
            for (int j = 0; j < d; j++) {
                if (Double.isNaN(raw[i][j])) {
                    missing++;
                } else {
                    data[i][j] = raw[i][j];
                }
            }

            if (missing == 0) {
                continue;
            }

            double[][] A = new double[d - missing][k];
            double[] B = new double[d - missing];

            for (int j = 0, m = 0; j < d; j++) {
                if (!Double.isNaN(raw[i][j])) {
                    System.arraycopy(svd.getV()[j], 0, A[m], 0, k);
                    B[m++] = raw[i][j];
                }
            }

            double[] s = MathBase.solve(A, B);

            for (int j = 0; j < d; j++) {
                if (Double.isNaN(raw[i][j])) {
                    data[i][j] = 0;
                    for (int l = 0; l < k; l++) {
                        data[i][j] += s[l] * svd.getV()[j][l];
                    }
                }
            }
        }
    }
}