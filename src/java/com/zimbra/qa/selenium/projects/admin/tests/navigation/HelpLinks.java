/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2015, 2016 Synacor, Inc.
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
package com.zimbra.qa.selenium.projects.admin.tests.navigation;

import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.projects.admin.core.AdminCore;

public class HelpLinks extends AdminCore {

	public HelpLinks() {
		logger.info("New "+ HelpLinks.class.getCanonicalName());
		super.startingPage = app.zPageMain;
	}


	/**
	 * Testcase : Navigate to Help > Help links
	 * Steps :
	 * 1. Verify navigation path - Navigate to Help > Help links
	 * 2. Verify links Zimbra Online Help, Administrators Guide, End Users Guide displayed by clicking on Help Center
	 * @throws HarnessException
	 */

	@Test (description = "Verify Help Center links",
			groups = { "sanity", "L0" })

	public void HelpLinks_01() throws HarnessException {

		// Navigate to Help > Help links
		app.zPageManageHelp.zToolbarPressPulldown(Button.B_GEAR_BOX, Button.O_HELP_CENTRAL_ONLINE);
		SleepUtil.sleepMedium();

		// Verify "Zimbra Online Help" link is present
		boolean isZimbraOnlineHelpLinkPresent = app.zPageManageHelp.zVerifyHelpCenterLink("Zimbra Online Help");
		ZAssert.assertTrue(isZimbraOnlineHelpLinkPresent, "Verify Zimbra Online Help link is present");
		SleepUtil.sleepSmall();

		// Verify "Administrators Guide" link is present
		boolean isAdministratorGuideLinkPresent = app.zPageManageHelp.zVerifyHelpCenterLink("Administrators' Guide");
		ZAssert.assertTrue(isAdministratorGuideLinkPresent, "Verify Zimbra Administrators' Guide link is present");
		SleepUtil.sleepSmall();

		// Verify "End Users' Guide" link is present
		boolean isEndUsersGuideLinkPresent = app.zPageManageHelp.zVerifyHelpCenterLink("End Users' Guide");
		ZAssert.assertTrue(isEndUsersGuideLinkPresent, "Verify End Users' Guide link is present");

		app.zPageMain.zLogout();
	}
}