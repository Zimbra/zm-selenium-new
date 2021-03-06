/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2013, 2014, 2016 Synacor, Inc.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */
/**
 *
 */
package com.zimbra.qa.selenium.projects.mobile.pages;

import com.zimbra.qa.selenium.framework.ui.AbsApplication;
import com.zimbra.qa.selenium.framework.ui.AbsPage;
import com.zimbra.qa.selenium.framework.ui.AbsTab;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;


/**
 * @author Matt Rhoades
 *
 */
public class PageMain extends AbsTab {

	public static class Locators {

		public static final String zBtnLogout = "css=[id='_logout']";

		public static final String zMainCopyright = "css=div#copyright_notice";
		public static final String zMainCopyrightText = zMainCopyright +" a";

		public static final String zAppbar = "css=div#appbar";
		public static final String zAppbarMail = zAppbar +" a#mail";
		public static final String zAppbarContact = zAppbar +" a#contact";
		public static final String zAppbarCal = zAppbar +" a#cal";
		public static final String zAppbarDocs = zAppbar +" a#docs";
		public static final String zAppbarSearch = zAppbar +" a#search";

		public static final String zBtnCompose = "//a[@href='zmain?st=newmail']";

		public static final String zPreferences = "css=a[href='?st=prefs']";

	}


	public PageMain(AbsApplication application) {
		super(application);

		logger.info("new " + PageMain.class.getCanonicalName());

	}

	/* (non-Javadoc)
	 * @see projects.admin.pages.AbsPage#isActive()
	 */
	@Override
	public boolean zIsActive() throws HarnessException {

		// Make sure the Mobile Client is loaded in the browser
		if ( !MyApplication.zIsLoaded() )
			throw new HarnessException("Admin Console application is not active!");


		// Look for the Logout button
		boolean present = sIsElementPresent(Locators.zBtnLogout);
		if ( !present ) {
			logger.debug("isActive() present = "+ present);
			return (false);
		}

		logger.debug("isActive() = "+ true);
		return (true);

	}

	/* (non-Javadoc)
	 * @see projects.admin.pages.AbsPage#myPageName()
	 */
	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	/* (non-Javadoc)
	 * @see projects.admin.pages.AbsPage#navigateTo()
	 */
	@Override
	public void zNavigateTo() throws HarnessException {


		if ( zIsActive() ) {
			// This page is already active
			return;
		}


		// 1. Logout
		// 2. Login as the default account
		if ( !((MobilePages)MyApplication).zPageLogin.zIsActive() ) {
			((MobilePages)MyApplication).zPageLogin.zNavigateTo();
		}
		((MobilePages)MyApplication).zPageLogin.zLogin();

		zWaitForActive();

	}

	/**
	 * Click the logout button
	 * @throws HarnessException
	 */
	public void zLogout() throws HarnessException {
		logger.debug("zLogout()");

		zNavigateTo();

		if ( !sIsElementPresent(Locators.zBtnLogout) ) {
			throw new HarnessException("The logoff button is not present " + Locators.zBtnLogout);
		}

		// Click on logout
		sClick(Locators.zBtnLogout);

		((MobilePages)MyApplication).zPageLogin.zWaitForActive();

		((MobilePages)MyApplication).zSetActiveAccount(null);

	}

	@Override
	public AbsPage zListItem(Action action, String item)
			throws HarnessException {
		return null;
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String item)
			throws HarnessException {
		return null;
	}

	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption ,String item)
			throws HarnessException {
		throw new HarnessException("Mobile page does not have context menu");
	}

	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		return null;
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option)
			throws HarnessException {
		return null;
	}


}
