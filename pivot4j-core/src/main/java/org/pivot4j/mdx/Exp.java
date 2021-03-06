/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 */
package org.pivot4j.mdx;

import java.io.Serializable;

/**
 * Expression node for an MDX parse tree
 */
public interface Exp extends Serializable {

    String toMdx();

    /**
     * Exp is visitable
     */
    void accept(ExpVisitor visitor);

    Exp copy();
}
