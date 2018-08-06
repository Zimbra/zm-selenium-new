/*
 * ***** BEGIN LICENSE BLOCK *****
 *
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
 *
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.qa.selenium.projects.ajax.tests.mail.contextmenu;

import org.testng.annotations.*;
import com.zimbra.qa.selenium.framework.core.Bugs;
import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.SetGroupMailByMessagePreference;
import com.zimbra.qa.selenium.projects.ajax.pages.contacts.FormContactNew;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.DisplayMail;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.FormMailNew;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.FormMailNew.Field;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.PageMail.Locators;

public class AddToContacts extends SetGroupMailByMessagePreference {

	public AddToContacts() {
		logger.info("New " + AddToContacts.class.getCanonicalName());
		super.startingAccountPreferences.put("zimbraPrefComposeFormat", "text");
	}


	@Bugs (ids = "102204")
	@Test (description = "Right click to from address header and verify AddToContact option",
			groups = { "smoke", "L1" })

	public void AddToContacts_01() throws HarnessException {

		// Create the message data to be sent
		String subject = "subject" + ConfigProperties.getUniqueString();
		String contactFirst = "First" + ConfigProperties.getUniqueString();

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
		ZAssert.assertEquals(actual.zGetMailProperty(com.zimbra.qa.selenium.projects.ajax.pages.mail.DisplayMail.Field.From), ZimbraAccount.AccountA().EmailAddress, "Verify the From matches");

		//	String OriginalEmailAddr = app.zPageMail.sGetText(Locators.zToAddressBubble);
		app.zPageMail.zRightClickAddressBubble(Field.From);
		app.zPageMail.zAddToContactMsgHdrContextMenu();
		SleepUtil.sleepMedium();

		app.zPageMail.sFocus("css=input[id$='_FIRST_input']");
		app.zPageMail.sType("css=input[id$='_FIRST_input']", contactFirst);
		app.zPageMail.sClickAt(FormContactNew.Toolbar.SAVE, "");
		SleepUtil.sleepMedium();

		// Verification
		app.zGetActiveAccount().soapSend(
				"<SearchRequest xmlns='urn:zimbraMail' types='contact'>"
						+ "<query>#firstname:" + contactFirst + "</query>"
						+ "</SearchRequest>");
		String contactId = app.zGetActiveAccount().soapSelectValue("//mail:cn", "id");

		ZAssert.assertNotNull(contactId,"Verify the contact is returned in the search");

		app.zGetActiveAccount().soapSend(
				"<GetContactsRequest xmlns='urn:zimbraMail'>" + "<cn id='"
						+ contactId + "'/>" + "</GetContactsRequest>");

		String firstname = app.zGetActiveAccount().soapSelectValue(
				"//mail:cn[@id='" + contactId + "']//mail:a[@n='firstName']",
				null);
		String email = app.zGetActiveAccount().soapSelectValue(
				"//mail:cn[@id='" + contactId + "']//mail:a[@n='email']", null);

		ZAssert.assertEquals(firstname, contactFirst,"Verify the first name was saved correctly");
		ZAssert.assertEquals(email, ZimbraAccount.AccountA().EmailAddress,"Verify the email was saved correctly");
	}
	
	
	@Bugs (ids = "102204")
	@Test (description = "Right click to bubble address and hit AddToContact",
			groups = { "smoke", "L1" })

	public void AddToContact_02() throws HarnessException {

		String contactFirst = "First" 	+ ConfigProperties.getUniqueString();

		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZimbraAccount.AccountB(), RecipientItem.RecipientType.To));

		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");

		// Fill out the form with the data
		mailform.zFill(mail);
		SleepUtil.sleepMedium();
		String OriginalEmailAddr = app.zPageMail.sGetText(Locators.zToAddressBubble);
		app.zPageMail.zRightClickAddressBubble(Field.To);
		app.zPageMail.zAddToContactAddressContextMenu();
		SleepUtil.sleepMedium();

		app.zPageMail.sFocus("css=input[id$='_FIRST_input']");
		app.zPageMail.sClick("css=input[id$='_FIRST_input']");
		app.zPageMail.zKeyboard.zTypeCharacters(contactFirst);
		SleepUtil.sleepMedium();
		app.zPageMail.sClickAt(FormContactNew.Toolbar.SAVE, "");
		SleepUtil.sleepMedium();

		// Verification
		app.zGetActiveAccount().soapSend(
				"<SearchRequest xmlns='urn:zimbraMail' types='contact'>"
						+ "<query>#firstname:" + contactFirst + "</query>"
						+ "</SearchRequest>");
		String contactId = app.zGetActiveAccount().soapSelectValue("//mail:cn", "id");

		ZAssert.assertNotNull(contactId, "Verify the contact is returned in the search");

		app.zGetActiveAccount().soapSend(
				"<GetContactsRequest xmlns='urn:zimbraMail'>" + "<cn id='"
						+ contactId + "'/>" + "</GetContactsRequest>");

		String firstname = app.zGetActiveAccount().soapSelectValue(
				"//mail:cn[@id='" + contactId + "']//mail:a[@n='firstName']",
				null);
		String email = app.zGetActiveAccount().soapSelectValue(
				"//mail:cn[@id='" + contactId + "']//mail:a[@n='email']", null);

		ZAssert.assertEquals(firstname, contactFirst, "Verify the first name was saved correctly");
		ZAssert.assertStringContains(email, OriginalEmailAddr, "Verify the email was saved correctly");
	}
}