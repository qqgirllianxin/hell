/*
 * LingPipe v. 4.1.0
 * Copyright (C) 2003-2011 Alias-i
 *
 * This program is licensed under the Alias-i Royalty Free License
 * Version 1 WITHOUT ANY WARRANTY, without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Alias-i
 * Royalty Free License Version 1 for more details.
 *
 * You should have received a copy of the Alias-i Royalty Free License
 * Version 1 along with this program; if not, visit
 * http://alias-i.com/lingpipe/licenses/lingpipe-license-1.txt or contact
 * Alias-i, Inc. at 181 North 11th Street, Suite 401, Brooklyn, NY 11211,
 * +1 (718) 290-9170.
 */

package ps.hell.ml.nlp.tool.aliasi.classify;

/**
 * The {@code JointClassifier} interface specifies a single method for
 * n-best classification with joint input and category probabilities.
 * 
 * @author  Bob Carpenter
 * @version 3.9.1
 * @since   LingPipe3.9.1
 * @param <E> the type of objects being classified
 */
public interface JointClassifier<E> extends ConditionalClassifier<E> {

    /**
     * Returns the n-best joint probability classification for
     * the specified input.
     *
     * @param input Object to classify.
     * @return Classification of object.
     */
    public JointClassification classify(E input);

}