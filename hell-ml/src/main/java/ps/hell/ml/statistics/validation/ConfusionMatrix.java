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

import java.util.HashSet;
import java.util.Set;

/**
 * Generates the confusion matrix based on truth and prediction vectors
 * @author owlmsj
 */
public class ConfusionMatrix {
	
	private int[][] matrix;
	
	public ConfusionMatrix(int[] truth, int[] prediction) {
		if(truth.length != prediction.length){
			 throw new IllegalArgumentException(String.format("The vector sizes don't match: %d != %d.", truth.length, prediction.length));
		}
		
		Set<Integer> ySet = new HashSet<Integer>();
		
		for(int i = 0; i < truth.length; i++){
			ySet.add(truth[i]);
		}
		
		matrix = new int[ySet.size()][ySet.size()];
		
		for(int i = 0; i < truth.length; i++){
			matrix[truth[i]][prediction[i]] += 1;
		}
		
		ySet.clear();
	}
	
    public int[][] getMatrix() {
        return matrix;
    }
    
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("ROW=truth and COL=predicted\n\n");
		
		for(int i = 0; i < matrix.length; i++){
			sb.append("class "+i+"\t: ");
			for(int j = 0; j < matrix.length; j++){
				sb.append(matrix[i][j] +"\t| ");
			}
			sb.append("\n");
 		}
		
		return sb.toString().trim();
	}
}
