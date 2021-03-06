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

package ps.hell.ml.nlp.tool.aliasi.corpus;

/**
 * The <code>ObjectHandler</code> interface specifies a handler
 * with a single method that takes a single argument of the
 * type of the generic paramter.
 *
 * @author  Bob Carpenter
 * @version 3.3.0
 * @since   LingPipe3.3
 * @param <E> the type of object being sent to the handler
 */
public interface ObjectHandler<E> extends Handler {

    /**
     * Handle the specified object.
     *
     * @param e Object to be handled.
     */
    public void handle(E e);

}