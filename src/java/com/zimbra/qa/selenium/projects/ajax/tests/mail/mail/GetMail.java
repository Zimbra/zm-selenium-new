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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.mail;

import java.util.List;
import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.items.MailItem;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.SetGroupMailByMessagePreference;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.DisplayMail;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.DisplayMail.Field;

public class GetMail extends SetGroupMailByMessagePreference {

	int pollIntervalSeconds = 60;

	public GetMail() {
		logger.info("New " + GetMail.class.getCanonicalName());
		super.startingAccountPreferences.put("zimbraPrefMailPollingInterval", "" + pollIntervalSeconds);
	}


	@Test (description = "Receive a mail",
			groups = { "smoke", "L1" })

	public void GetMail_01() throws HarnessException {

		// Create the message data to be sent
		String subject = "subject" + ConfigProperties.getUniqueString();

		// Send the message from AccountA to the ZCS user
		ZimbraAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zimbraMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>"+ "body" + ConfigProperties.getUniqueString() +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");

		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");

		// Refresh current view
		ZAssert.assertTrue(app.zPageMail.zVerifyMailExists(subject), "Verify message displayed in current view");

		// Get all the messages in the inbox
		List<MailItem> messages = app.zPageMail.zListGetMessages();
		ZAssert.assertNotNull(messages, "Verify the message list exists");

		// Make sure the message appears in the list
		MailItem found = null;
		for (MailItem m : messages) {
			logger.info("Subject: looking for "+ subject +" found: "+ m.gSubject);
			if ( mail.dSubject.equals(m.gSubject) ) {
				found = m;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the message is in the inbox");
	}


	@Test (description = "Receive a text mail - verify mail contents",
			groups = { "smoke", "L1" })

	public void GetMail_02() throws HarnessException {

		// Create the message data to be sent
		String subject = "subject" + ConfigProperties.getUniqueString();

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

		// Get all the SOAP data for later verification
		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");

		// Refresh current view
		ZAssert.assertTrue(app.zPageMail.zVerifyMailExists(subject), "Verify message displayed in current view");

		// Select the message so that it shows in the reading pane
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

		// Verify the To, From, Subject, Body
		ZAssert.assertEquals(actual.zGetMailProperty(Field.Subject), mail.dSubject, "Verify the subject matches");
		ZAssert.assertNotNull(actual.zGetMailProperty(Field.ReceivedDate), "Verify the date is displayed");
		ZAssert.assertNotNull(actual.zGetMailProperty(Field.ReceivedTime), "Verify the time is displayed");
		ZAssert.assertEquals(actual.zGetMailProperty(Field.From), ZimbraAccount.AccountA().EmailAddress, "Verify the From matches");
		ZAssert.assertEquals(actual.zGetMailProperty(Field.Cc), ZimbraAccount.AccountB().EmailAddress, "Verify the Cc matches");
		ZAssert.assertEquals(actual.zGetMailProperty(Field.To), app.zGetActiveAccount().EmailAddress, "Verify the To matches");
		ZAssert.assertStringContains(actual.zGetMailProperty(Field.Body), mail.dBodyText, "Verify the body matches");
	}


	@Test (description = "Receive an html mail - verify mail contents",
			groups = { "smoke", "L1" })

	public void GetMail_03() throws HarnessException {

		// Create the message data to be sent
		String subject = "subject" + ConfigProperties.getUniqueString();
		String bodyText = "text" + ConfigProperties.getUniqueString();
		String bodyHTML = "text <strong style=\"\">bold" + ConfigProperties.getUniqueString() + "</strong> text";
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
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, mail.dSubject);

		// Verify the To, From, Subject, Body
		ZAssert.assertEquals(actual.zGetMailProperty(Field.Subject), mail.dSubject, "Verify the subject matches");
		ZAssert.assertNotNull(actual.zGetMailProperty(Field.ReceivedDate), "Verify the date is displayed");
		ZAssert.assertNotNull(actual.zGetMailProperty(Field.ReceivedTime), "Verify the time is displayed");
		ZAssert.assertEquals(actual.zGetMailProperty(Field.From), ZimbraAccount.AccountA().EmailAddress, "Verify the From matches");
		ZAssert.assertEquals(actual.zGetMailProperty(Field.Cc), ZimbraAccount.AccountB().EmailAddress, "Verify the Cc matches");
		ZAssert.assertEquals(actual.zGetMailProperty(Field.To), app.zGetActiveAccount().EmailAddress, "Verify the To matches");
		ZAssert.assertStringContains(actual.zGetMailProperty(Field.Body), bodyHTML, "Verify the body content matches");
	}


	@Test (description = "Click 'Get Mail' to receive any new messages",
			groups = { "functional", "L2" })

	public void GetMail_04() throws HarnessException {

		// Create the message data to be sent
		String subject = "subject" + ConfigProperties.getUniqueString();

		ZimbraAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zimbraMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>content" + ConfigProperties.getUniqueString() +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");

		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");

		// Refresh current view
		ZAssert.assertTrue(app.zPageMail.zVerifyMailExists(subject), "Verify message displayed in current view");

		// Get the message list
		List<MailItem> messages = app.zPageMail.zListGetMessages();
		ZAssert.assertNotNull(messages, "Verify the list contains messages");

		MailItem found = null;
		for (MailItem m : messages) {
			if ( mail.dSubject.equals(m.gSubject) ) {
				found = m;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the list contains the new message");
	}


	@Test (description = "Verify new messages are polled based on the preference setting",
			groups = { "functional", "L2" })

	public void GetMail_05() throws HarnessException {

		// Create the message data to be sent
		String subject = "subject" + ConfigProperties.getUniqueString();

		ZimbraAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zimbraMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>content" + ConfigProperties.getUniqueString() +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");

		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");

		// Wait for the timeout to expire
		logger.info("waiting for the message to arrive");
		SleepUtil.sleep(1000L * (this.pollIntervalSeconds + 15));

		// Get the message list
		List<MailItem> messages = app.zPageMail.zListGetMessages();
		ZAssert.assertNotNull(messages, "Verify the list contains messages");

		MailItem found = null;
		for (MailItem m : messages) {
			if ( mail.dSubject.equals(m.gSubject) ) {
				found = m;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the list contains the new message");
	}


	@Test (description = "Type keyboard shortcut (=) for 'Get Mail' to receive any new messages",
			groups = { "functional", "L2" })

	public void GetMail_06() throws HarnessException {

		// Create the message data to be sent
		String subject = "subject" + ConfigProperties.getUniqueString();

		ZimbraAccount.AccountA().soapSend(
					"<SendMsgRequest xmlns='urn:zimbraMail'>" +
						"<m>" +
							"<e t='t' a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
							"<su>"+ subject +"</su>" +
							"<mp ct='text/plain'>" +
								"<content>content" + ConfigProperties.getUniqueString() +"</content>" +
							"</mp>" +
						"</m>" +
					"</SendMsgRequest>");

		MailItem mail = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ subject +")");

		// Type shortcut
		app.zPageMail.zKeyboardShortcut(Shortcut.S_MAIL_GETMAIL);

		// Get the message list
		List<MailItem> messages = app.zPageMail.zListGetMessages();
		ZAssert.assertNotNull(messages, "Verify the list contains messages");

		MailItem found = null;
		for (MailItem m : messages) {
			if ( mail.dSubject.equals(m.gSubject) ) {
				found = m;
				break;
			}
		}
		ZAssert.assertNotNull(found, "Verify the list contains the new message");
	}
}