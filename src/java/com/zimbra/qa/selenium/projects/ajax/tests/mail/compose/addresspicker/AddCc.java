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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.compose.addresspicker;

import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.items.MailItem;
import com.zimbra.qa.selenium.framework.items.RecipientItem;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ConfigProperties;
import com.zimbra.qa.selenium.projects.ajax.core.SetGroupMailByMessagePreference;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.FormAddressPicker;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.FormMailNew;

public class AddCc extends SetGroupMailByMessagePreference {

	public AddCc() {
		logger.info("New "+ AddCc.class.getCanonicalName());
		super.startingAccountPreferences.put("zimbraPrefComposeFormat", "text");
	}


	@Test (description = "Select a 'Cc' address in the addresspicker",
			groups = { "sanity" })

	public void AddCc_01() throws HarnessException {

		// Create the message data to be sent
		MailItem mail = new MailItem();
		mail.dToRecipients.add(new RecipientItem(ZimbraAccount.AccountA()));
		mail.dSubject = "subject" + ConfigProperties.getUniqueString();
		mail.dBodyText = "body" + ConfigProperties.getUniqueString();

		// Open the new mail form
		FormMailNew mailform = (FormMailNew) app.zPageMail.zToolbarPressButton(Button.B_NEW);
		ZAssert.assertNotNull(mailform, "Verify the new form opened");

		mailform.zFill(mail);

		FormAddressPicker pickerform = (FormAddressPicker)mailform.zToolbarPressButton(Button.B_CC);
		ZAssert.assertTrue(pickerform.zIsActive(), "Verify the address picker opened corectly");

		pickerform.zFillField(FormAddressPicker.Field.Search, ZimbraAccount.AccountB().EmailAddress);
		pickerform.zToolbarPressButton(Button.B_SEARCH);
		pickerform.zToolbarPressButton(Button.B_CC);
		pickerform.zSubmit();

		// Send the message
		mailform.zSubmit();

		MailItem sent = MailItem.importFromSOAP(app.zGetActiveAccount(), "subject:("+ mail.dSubject +")");
		ZAssert.assertNotNull(sent, "Verify the message appears in the sent folder");
		ZAssert.assertEquals(sent.dCcRecipients.get(0).dEmailAddress, ZimbraAccount.AccountB().EmailAddress, "Verify the 'cc' field is correct");
	}
}