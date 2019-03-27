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
package com.zimbra.qa.selenium.projects.ajax.tests.main.login;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.core.Bugs;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ConfigProperties;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCore;
import com.zimbra.qa.selenium.projects.ajax.pages.PageLogin;

public class LoginScreen extends AjaxCore {

	public LoginScreen() {
		logger.info("New "+ LoginScreen.class.getCanonicalName());
		super.startingPage = app.zPageLogin;
	}


	@Test (description = "Verify the label text on the ajax client login screen",
			groups = { "smoke", "L1" })

	public void LoginScreen_01() throws HarnessException {

		String username = app.zPageLogin.sGetText(PageLogin.Locators.zDisplayedusername);
		ZAssert.assertEquals(username, app.zGetLocaleString("usernameLabel"), "Verify the displayed label 'username'");
	}


	@Test (description = "Verify the copyright on the login screen contains the current year",
			groups = { "functional","L2" })

	public void LoginScreen_02() throws HarnessException {

		Calendar calendar = new GregorianCalendar();
		String thisYear = "" + calendar.get(Calendar.YEAR);

		String copyright = app.zPageLogin.sGetText(PageLogin.Locators.zDisplayedcopyright);

		String message = String.format("Verify the copyright (%s) on the login screen contains the current year (%s)", copyright, thisYear);
		ZAssert.assertStringContains(copyright, thisYear, message);
	}


	@Test (description = "Verify initial focus on the login screen should be in username",
			groups = { "functional","L2" })

	public void LoginScreen_03() throws HarnessException {

		app.zPageLogin.zNavigateTo();

		String value = "foo" + ConfigProperties.getUniqueString();
		app.zPageLogin.zKeyboardTypeString(value);

		// Get the value of the username field
		String actual = app.zPageLogin.sGetValue(PageLogin.Locators.zInputUsername);

		// Verify typed text and the actual text match
		ZAssert.assertEquals(actual, value, "Verify the username has initial focus");
	}


	@Bugs (ids = "50457")
	@Test (description = "Verify 'web client' rather than 'collaboration suite'",
			groups = { "functional", "L2" })

	public void LoginScreen_04() throws HarnessException {

		String title = app.zPageLogin.sGetTitle();

		// TODO: Need to I18N
		ZAssert.assertStringContains(title, "Web Client", "Verify 'web client' rather than 'collaboration suite'");
	}
}