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

package ps.hell.ml.statistics.validation;

import ps.hell.math.base.MathBase;


/**
 * 根均方误差。
 * Root mean squared error.
 * 
 * @author Haifeng Li
 */
public class RMSE implements RegressionMeasure {

    @Override
    public double measure(double[] truth, double[] prediction) {
        if (truth.length != prediction.length) {
            throw new IllegalArgumentException(String.format("The vector sizes don't match: %d != %d.", truth.length, prediction.length));
        }

        int n = truth.length;
        double rss = 0.0;
        for (int i = 0; i < n; i++) {
            rss += MathBase.sqr(truth[i] - prediction[i]);
        }

        return MathBase.sqrt(rss/n);
    }

    @Override
    public String toString() {
        return "RMSE";
    }
}