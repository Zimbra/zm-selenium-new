/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2016 Synacor, Inc.
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
package com.zimbra.qa.selenium.projects.admin.tests.login;

import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ZimbraAdminAccount;
import com.zimbra.qa.selenium.framework.util.CommandLineUtility;
import com.zimbra.qa.selenium.framework.util.ConfigProperties;
import com.zimbra.qa.selenium.projects.admin.core.AdminCore;

public class LoginWithCsrfTokenCheckDisabled extends AdminCore {

	public LoginWithCsrfTokenCheckDisabled() {
		logger.info("New "+ LoginWithCsrfTokenCheckDisabled.class.getCanonicalName());
		super.startingPage = app.zPageLogin;
	}


	@Test (description = "Login to the admin console after disabling csrf check", priority=5,
			groups = { "smoke", "L1", "non-aws" })

	public void LoginWithCsrfTokenCheckDisabled_01() throws HarnessException {
		try {

			String zimbraCsrfTokenCheckEnabledValue = "FALSE";

			// Change zimbraCsrfTokenCheckEnabled value to false
			ZimbraAdminAccount.GlobalAdmin().soapSend(
					"<ModifyConfigRequest xmlns='urn:zimbraAdmin'>"
							+		"<a n='zimbraCsrfTokenCheckEnabled'>"+ zimbraCsrfTokenCheckEnabledValue + "</a>"
							+	"</ModifyConfigRequest>");

			// Restart zimbra services
			CommandLineUtility.runCommandOnZimbraServer(ZimbraAccount.AccountZCS().zGetAccountStoreHost(),
					"zmmailboxdctl restart");

			// Wait for the service to come up
			SleepUtil.sleep(60000);

			CommandLineUtility.runCommandOnZimbraServer(ZimbraAccount.AccountZCS().zGetAccountStoreHost(),
					"zmcontrol status");

			SleepUtil.sleepMedium();

			// Login
			app.zPageLogin.zLogin(gAdmin);

			// Verify main page becomes active
			ZAssert.assertTrue(app.zPageMain.zIsActive(), "Verify that the account is logged in with zimbraCsrfTokenCheckEnabledValue as FALSE");
			app.zPageMain.zLogout();
		}

		finally {

			String zimbraCsrfTokenCheckEnabledValue = "TRUE";

			// Change zimbraCsrfTokenCheckEnabled value to false
			ZimbraAdminAccount.GlobalAdmin().soapSend(
					"<ModifyConfigRequest xmlns='urn:zimbraAdmin'>"
							+		"<a n='zimbraCsrfTokenCheckEnabled'>"+ zimbraCsrfTokenCheckEnabledValue + "</a>"
							+	"</ModifyConfigRequest>");

			CommandLineUtility.runCommandOnZimbraServer(ZimbraAccount.AccountZCS().zGetAccountStoreHost(),
					"zmmailboxdctl restart");
			SleepUtil.sleep(60000);
			app.zPageMain.zRefreshMainUI();

			// Open the base URL
			app.zPageLogin.sOpen(ConfigProperties.getBaseURL());
			app.zPageLogin.zLogin(gAdmin);
			ZAssert.assertTrue(app.zPageMain.zIsActive(), "Verify that the account is logged in after zimbraCsrfTokenCheckEnabledValue as TRUE");
			app.zPageMain.zLogout();
		}
	}
}