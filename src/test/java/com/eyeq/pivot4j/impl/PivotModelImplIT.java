/*
 * ====================================================================
 * This software is subject to the terms of the Common Public License
 * Agreement, available at the following URL:
 *   http://www.opensource.org/licenses/cpl.html .
 * You must accept the terms of that agreement to use this software.
 * ====================================================================
 */
package com.eyeq.pivot4j.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.olap4j.CellSet;
import org.olap4j.CellSetAxis;

import com.eyeq.pivot4j.AbstractIntegrationTestCase;
import com.eyeq.pivot4j.NotInitializedException;
import com.eyeq.pivot4j.PivotException;
import com.eyeq.pivot4j.PivotModel;

public class PivotModelImplIT extends AbstractIntegrationTestCase {

	private String testQuery = "SELECT {[Measures].[Unit Sales], [Measures].[Store Cost], [Measures].[Store Sales]} ON COLUMNS, "
			+ "{([Promotion Media].[All Media], [Product].[All Products])} ON ROWS FROM [Sales] WHERE [Time].[1997]";

	/**
	 * @return the testQuery
	 */
	public String getTestQuery() {
		return testQuery;
	}

	/**
	 * @param testQuery
	 *            the testQuery to set
	 */
	public void setTestQuery(String testQuery) {
		this.testQuery = testQuery;
	}

	@Test
	public void testInitialize() {
		PivotModel model = getPivotModel();
		model.setMdx(getTestQuery());

		assertFalse("Model is already initialized.", model.isInitialized());
		model.initialize();

		assertTrue("Model is not initialized.", model.isInitialized());
	}

	@Test(expected = PivotException.class)
	public void testInitializeWithNoInitialMdx() {
		PivotModel model = getPivotModel();
		model.initialize();
	}

	@Test
	public void testDestroy() {
		PivotModel model = getPivotModel();
		model.setMdx(getTestQuery());
		model.initialize();

		assertTrue("Model is not initialized.", model.isInitialized());

		model.destroy();
		assertFalse("Model is not destroyed.", model.isInitialized());
	}

	@Test
	public void testGetCellSet() {
		PivotModel model = getPivotModel();
		model.setMdx(getTestQuery());
		model.initialize();

		CellSet cellSet = model.getCellSet();
		assertNotNull("CellSet is null.", cellSet);

		List<CellSetAxis> axes = cellSet.getAxes();
		assertNotNull("Cell axes list is null.", axes);
		assertEquals("Invalid cell axes size.", 2, axes.size());
	}

	@Test
	public void testGetMdx() {
		PivotModel model = getPivotModel();
		model.setMdx(getTestQuery());
		model.initialize();

		assertEquals("MDX has been modified unexpectedly.", getTestQuery(),
				model.getMdx());
	}

	@Test
	public void testGetCurrentMdx() {
		PivotModel model = getPivotModel();
		model.setMdx(getTestQuery());
		model.initialize();

		String currentMdx = model.getCurrentMdx();
		assertEquals("MDX has been modified unexpectedly.", getTestQuery(),
				currentMdx);
	}

	@Test(expected = NotInitializedException.class)
	public void testGetCellSetBeforeInitialize() {
		PivotModel model = getPivotModel();
		assertFalse("Model is already initialized.", model.isInitialized());

		model.getCellSet();
	}
}
