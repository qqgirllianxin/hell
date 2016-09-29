// Copyright (C) 2014 Guibing Guo
//
// This file is part of LibRec.
//
// LibRec is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// LibRec is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with LibRec. If not, see <http://www.gnu.org/licenses/>.
//

package ps.hell.ml.rs.librec.ext;

import ps.hell.ml.rs.librec.data.Configuration;
import ps.hell.ml.rs.librec.data.DenseMatrix;
import ps.hell.ml.rs.librec.data.DenseVector;
import ps.hell.ml.rs.librec.data.MatrixEntry;
import ps.hell.ml.rs.librec.data.SparseMatrix;
import ps.hell.ml.rs.librec.data.SparseVector;
import ps.hell.ml.rs.librec.intf.IterativeRecommender;
import ps.hell.ml.rs.librec.util.Strings;

/**
 * 非负矩阵分解算法
 * v更新u 更新v svd解
 * Daniel D. Lee and H. Sebastian Seung, <strong>Algorithms for Non-negative Matrix Factorization</strong>, NIPS 2001.
 * 
 * @author guoguibing
 * 
 */
@Configuration("factors, numIters")
public class NMF extends IterativeRecommender {

	
	protected DenseMatrix W, H;
	/**
	 * V = W * H
	 */
	protected SparseMatrix V;

	public NMF(SparseMatrix trainMatrix, SparseMatrix testMatrix, int fold) {
		super(trainMatrix, testMatrix, fold);

		// no need to update learning rate
		lRate = -1;
	}

	@Override
	protected void initModel() throws Exception {
		W = new DenseMatrix(numUsers, numFactors);
		H = new DenseMatrix(numFactors, numItems);

		W.init(0.01);
		H.init(0.01);

		V = trainMatrix;
	}

	@Override
	protected void buildModel() throws Exception {
		for (int iter = 1; iter <= numIters; iter++) {

			// update W by fixing H
			for (int u = 0; u < W.numRows(); u++) {
				SparseVector uv = V.row(u);

				if (uv.getCount() > 0) {
					SparseVector euv = new SparseVector(V.numColumns());

					//matrix u*j dot(row,col)
					for (int j : uv.getIndex())
						euv.set(j, predict(u, j));

					for (int f = 0; f < W.numColumns(); f++) {
						DenseVector fv = H.row(f, false);
						double real = fv.inner(uv);
						double estm = fv.inner(euv) + 1e-9;

						W.set(u, f, W.get(u, f) * (real / estm));
					}
				}
			}

			// update H by fixing W
			DenseMatrix trW = W.transpose();
			for (int j = 0; j < H.numColumns(); j++) {
				SparseVector jv = V.column(j);

				if (jv.getCount() > 0) {
					SparseVector ejv = new SparseVector(V.numRows());

					for (int u : jv.getIndex())
						ejv.set(u, predict(u, j));

					for (int f = 0; f < H.numRows(); f++) {
						DenseVector fv = trW.row(f, false);
						double real = fv.inner(jv);
						double estm = fv.inner(ejv) + 1e-9;

						H.set(f, j, H.get(f, j) * (real / estm));
					}
				}
			}

			// compute errors
			loss = 0;
			for (MatrixEntry me : V) {
				int u = me.row();
				int j = me.column();
				double ruj = me.get();

				if (ruj > 0) {
					double euj = predict(u, j) - ruj;

					loss += euj * euj;
				}
			}

			loss *= 0.5;

			if (isConverged(iter))
				break;
		}
	}

	@Override
	protected double predict(int u, int j) {
		return DenseMatrix.product(W, u, H, j);
	}

	@Override
	public String toString() {
		return Strings.toString(new Object[] { numFactors, numIters });
	}
}
