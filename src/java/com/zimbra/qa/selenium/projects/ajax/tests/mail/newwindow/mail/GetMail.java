/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2012, 2013, 2014, 2015, 2016 Synacor, Inc.
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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.newwindow.mail;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.items.MailItem;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.XmlStringUtil;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ConfigProperties;
import com.zimbra.qa.selenium.projects.ajax.core.SetGroupMailByMessagePreference;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.SeparateWindowDisplayMail;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.DisplayMail.Field;

public class GetMail extends SetGroupMailByMessagePreference {
	protected static Logger logger = LogManager.getLogger(GetMail.class);

	final String mimeFolder = ConfigProperties.getBaseDirectory() + "/data/public/mime/email00";

	public GetMail() throws HarnessException {
		logger.info("New "+ GetMail.class.getCanonicalName());
	}


	@Test (description = "Open message in separate window",
			groups = { "smoke", "L1" })

	public void GetMail_01() throws HarnessException {

		final String subject = "subject12996136534962";

		ZimbraAccount.AccountA().soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<e t='c' a='"+ ZimbraAccount.AccountB().EmailAddress +"'/>" +
						"<su>"+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ "body" + ConfigProperties.getUniqueString() +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		// Refresh current view
		ZAssert.assertTrue(app.zPageMail.zVerifyMailExists(subject), "Verify message displayed in current view");

		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		SeparateWindowDisplayMail window = null;
		String windowTitle = "Zimbra: " + subject;

		try {

			// Choose Actions -> Launch in Window
			window = (SeparateWindowDisplayMail)app.zPageMail.zToolbarPressPulldown(Button.B_ACTIONS, Button.B_LAUNCH_IN_SEPARATE_WINDOW);

			window.zSetWindowTitle(windowTitle);
			ZAssert.assertTrue(window.zIsWindowOpen(windowTitle),"Verify the window is opened and switch to it");

		} finally {
			app.zPageMain.zCloseWindow(window, windowTitle, app);
		}
	}


	@Test (description = "Open message in separate window - verify mail contents",
			groups = { "smoke", "L1" })

	public void GetMail_02() throws HarnessException {

		final String subject = "subject1291234112962";
		final String content = "content2291234112962";

		ZimbraAccount.AccountA().soapSend(
				"<SendMsgRequest xmlns='urn:zimbraMail'>" +
					"<m>" +
						"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
						"<e t='c' a='"+ ZimbraAccount.AccountB().EmailAddress +"'/>" +
						"<su>"+ subject +"</su>" +
						"<mp ct='text/plain'>" +
							"<content>"+ content +"</content>" +
						"</mp>" +
					"</m>" +
				"</SendMsgRequest>");

		// Refresh current view
		ZAssert.assertTrue(app.zPageMail.zVerifyMailExists(subject), "Verify message displayed in current view");

		// Select the item
		app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		SeparateWindowDisplayMail window = null;
		String windowTitle = "Zimbra: " + subject;

		try {

			// Choose Actions -> Launch in Window
			window = (SeparateWindowDisplayMail)app.zPageMail.zToolbarPressPulldown(Button.B_ACTIONS, Button.B_LAUNCH_IN_SEPARATE_WINDOW);

			window.zSetWindowTitle(windowTitle);
			ZAssert.assertTrue(window.zIsWindowOpen(windowTitle),"Verify the window is opened and switch to it");

			ZAssert.assertEquals(window.zGetMailProperty(Field.Subject), subject, "Verify the subject matches");
			ZAssert.assertNotNull(window.zGetMailProperty(Field.ReceivedTime), "Verify the time is displayed");
			ZAssert.assertEquals(window.zGetMailProperty(Field.From), ZimbraAccount.AccountA().EmailAddress, "Verify the From matches");
			ZAssert.assertEquals(window.zGetMailProperty(Field.Cc),"Cc: " + ZimbraAccount.AccountB().EmailAddress, "Verify the Cc matches");
			ZAssert.assertEquals(window.zGetMailProperty(Field.To), "To: " + app.zGetActiveAccount().EmailAddress, "Verify the To matches");
			ZAssert.assertStringContains(window.zGetMailProperty(Field.Body), content, "Verify the body matches");

		} finally {
			app.zPageMain.zCloseWindow(window, windowTitle, app);
		}
	}


	@Test (description = "Open html message in separate window - verify mail contents",
			groups = { "smoke", "L1" })

	public void GetMail_03() throws HarnessException {

		// Create the message data to be sent
		String subject = "subject" + ConfigProperties.getUniqueString();
		String bodyText = "text" + ConfigProperties.getUniqueString();
		String bodyHTML = "text <strong style=\"\">bold"+ ConfigProperties.getUniqueString() +"</strong> text";
		String contentHTML = XmlStringUtil.escapeXml(
			"<html>" +
				"<head></head>" +
				"<body>"+ bodyHTML +"</body>" +
			"</html>");

		ZimbraAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zimbraMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<e t='c' a='"+ ZimbraAccount.AccountB().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='multipart/alternative'>" +
								"<mp ct='text/plain'>" +
									"<content>" + bodyText +"</content>" +
								"</mp>" +
								"<mp ct='text/html'>" +
									"<content>"+ contentHTML +"</content>" +
								"</mp>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");

		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");

		// Refresh current view
		ZAssert.assertTrue(app.zPageMail.zVerifyMailExists(subject), "Verify message displayed in current view");

		// Select the message so that it shows in the reading pane
		app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

		SeparateWindowDisplayMail window = null;
		String windowTitle = "Zimbra: " + subject;

		try {

			// Choose Actions -> Launch in Window
			window = (SeparateWindowDisplayMail)app.zPageMail.zToolbarPressPulldown(Button.B_ACTIONS, Button.B_LAUNCH_IN_SEPARATE_WINDOW);

			window.zSetWindowTitle(windowTitle);
			ZAssert.assertTrue(window.zIsWindowOpen(windowTitle),"Verify the window is opened and switch to it");

			// Verify the To, From, Subject, Body
			ZAssert.assertEquals(window.zGetMailProperty(Field.Subject), mail.dSubject, "Verify the subject matches");
			ZAssert.assertNotNull(window.zGetMailProperty(Field.ReceivedTime), "Verify the time is displayed");
			ZAssert.assertEquals(window.zGetMailProperty(Field.From), ZimbraAccount.AccountA().EmailAddress, "Verify the From matches");
			ZAssert.assertEquals(window.zGetMailProperty(Field.Cc), "Cc: " + ZimbraAccount.AccountB().EmailAddress, "Verify the Cc matches");
			ZAssert.assertEquals(window.zGetMailProperty(Field.To), "To: " + app.zGetActiveAccount().EmailAddress, "Verify the To matches");
			ZAssert.assertStringContains(window.zGetMailProperty(Field.Body), bodyHTML, "Verify the body matches");

		} finally {
			app.zPageMain.zCloseWindow(window, windowTitle, app);
		}
	}
}