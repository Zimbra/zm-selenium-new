/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2011, 2013, 2014, 2015, 2016 Synacor, Inc.
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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.compose;

import java.awt.event.KeyEvent;
import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.SetGroupMailByMessagePreference;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.FormMailNew;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.FormMailNew.Field;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.FormMailNew.Locators;

public class CreateMailHtml extends SetGroupMailByMessagePreference {

	public CreateMailHtml() {
		logger.info("New "+ CreateMailHtml.class.getCanonicalName());
		super.startingAccountPreferences.put("zimbraPrefComposeFormat", "html");
	}


	@Test (description = "Send a mail using HTML editor",
			groups = { "sanity", "L1" })

	public void CreateMailHtml_01() throws HarnessException {

		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZimbraAccount.AccountA()));
		mail.dSubject = "subject" + ConfigProperties.getUniqueString();
		mail.dBodyHtml = "body" + ConfigProperties.getUniqueString();

		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");

		// Fill out the form with the data
		mailform.zFill(mail);

		// Send the message
		mailform.zSubmit();

		ZimbraAccount.AccountA().soapSend(
					"<SearchRequest types='message' xmlns='urn:zimbraMail'>"
			+			"<query>subject:("+ mail.dSubject +")</query>"
			+		"</SearchRequest>");
		String id = ZimbraAccount.AccountA().soapSelectValue("//mail:m", "id");

		ZimbraAccount.AccountA().soapSend(
					"<GetMsgRequest xmlns='urn:zimbraMail'>"
			+			"<m id='"+ id +"' html='1'/>"
			+		"</GetMsgRequest>");

		String from = ZimbraAccount.AccountA().soapSelectValue("//mail:e[@t='f']", "a");
		String to = ZimbraAccount.AccountA().soapSelectValue("//mail:e[@t='t']", "a");
		String subject = ZimbraAccount.AccountA().soapSelectValue("//mail:su", null);
		String html = ZimbraAccount.AccountA().soapSelectValue("//mail:mp[@ct='text/html']//mail:content", null);

		ZAssert.assertEquals(from, app.zGetActiveAccount().EmailAddress, "Verify the from field is correct");
		ZAssert.assertEquals(to, ZimbraAccount.AccountA().EmailAddress, "Verify the to field is correct");
		ZAssert.assertEquals(subject, mail.dSubject, "Verify the subject field is correct");
		ZAssert.assertStringContains(html, mail.dBodyHtml, "Verify the html content");
	}


	@Test (description = "Send a mail multiline body content using HTML editor",
			groups = { "sanity", "L0" })

	public void CreateMultilineContentMailHtml_02() throws HarnessException {

		// Create the message data to be sent
		final String dToRecipients = ZimbraAccount.AccountA().EmailAddress;
		final String dSubject = "subject" + ConfigProperties.getUniqueString();
		final String dBodyHtmlBold = "<strong>BoldString</strong>";
		final String dBodyHtmlItalic = "<br></div><div><span id=\"_mce_caret\"><em>ItalicString</em><span id=\"_mce_caret\"></span>";
		String dBodyHtmlRedColorText = "<br></div><div><span id=\"_mce_caret\"><span style=\"color: rgb(255, 0, 0);\">RedColorText</span><span id=\"_mce_caret\"></span>";
		String dBodyHtmlGreenBackgroundText = "<br></div><div><span id=\"_mce_caret\"><span style=\"background-color: rgb(0, 128, 0);\">GreenBackgroundText</span><span id=\"_mce_caret\"></span>";

		if (ConfigProperties.getStringProperty("browser").equals("edge")) {
			dBodyHtmlRedColorText = "</span></span><br></div><div><span id=\"_mce_caret\"><span style=\"color: #ff0000;\">RedColorText</span><span id=\"_mce_caret\"></span></span>";
			dBodyHtmlGreenBackgroundText = "<br></div><div><span id=\"_mce_caret\"><span style=\"background-color: #008000;\">GreenBackgroundText</span><span id=\"_mce_caret\"></span></span>";
		}

		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");

		// Fill out the form with the data
		mailform.zFillField(Field.To, dToRecipients);
		mailform.zFillField(Field.Subject, dSubject);

		// Enter multiline body HTML text
		mailform.sClick(Locators.zBoldButton);
		mailform.zKeyboard.zTypeCharacters("BoldString");
		mailform.sClick(Locators.zBoldButton);
		mailform.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ENTER);
		mailform.sClick(Locators.zItalicButton);
		mailform.zKeyboard.zTypeCharacters("ItalicString");
		mailform.sClick(Locators.zItalicButton);
		mailform.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ENTER);
		mailform.sClick(Locators.zTextColorDropdown);
		mailform.sClick(Locators.zTextColorRed);
		mailform.zKeyboard.zTypeCharacters("RedColorText");
		mailform.sClick(Locators.zTextColorDropdown);
		mailform.sClick(Locators.zTextColorTransparent);
		mailform.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ENTER);
		mailform.sClick(Locators.zTextBackgroundColorDropdown);
		mailform.sClick(Locators.zTextBackgroundColorGreen);
		mailform.zKeyboard.zTypeCharacters("GreenBackgroundText");
		mailform.sClick(Locators.zTextBackgroundColorDropdown);
		mailform.sClick(Locators.zTextBackgroundColorTransparent);
		mailform.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ENTER);

		// Send the message
		mailform.zSubmit();

		ZimbraAccount.AccountA().soapSend(
					"<SearchRequest types='message' xmlns='urn:zimbraMail'>"
			+			"<query>subject:("+ dSubject +")</query>"
			+		"</SearchRequest>");
		String id = ZimbraAccount.AccountA().soapSelectValue("//mail:m", "id");

		ZimbraAccount.AccountA().soapSend(
					"<GetMsgRequest xmlns='urn:zimbraMail'>"
			+			"<m id='"+ id +"' html='1'/>"
			+		"</GetMsgRequest>");

		String from = ZimbraAccount.AccountA().soapSelectValue("//mail:e[@t='f']", "a");
		String to = ZimbraAccount.AccountA().soapSelectValue("//mail:e[@t='t']", "a");
		String subject = ZimbraAccount.AccountA().soapSelectValue("//mail:su", null);
		String html = ZimbraAccount.AccountA().soapSelectValue("//mail:mp[@ct='text/html']//mail:content", null).replace("\uFEFF", "");

		ZAssert.assertEquals(from, app.zGetActiveAccount().EmailAddress, "Verify the from field is correct");
		ZAssert.assertEquals(to, ZimbraAccount.AccountA().EmailAddress, "Verify the to field is correct");
		ZAssert.assertEquals(subject, dSubject, "Verify the subject field is correct");
		ZAssert.assertStringContains(html, dBodyHtmlBold, "Verify the html content");
		ZAssert.assertStringContains(html, dBodyHtmlItalic, "Verify the html content");
		ZAssert.assertStringContains(html, dBodyHtmlRedColorText, "Verify the html content");
		ZAssert.assertStringContains(html, dBodyHtmlGreenBackgroundText, "Verify the html content");
	}
}