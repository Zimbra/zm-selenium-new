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
package com.zimbra.qa.selenium.projects.ajax.tests.mail.mail;

import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.SetGroupMailByMessagePreference;
import com.zimbra.qa.selenium.projects.ajax.tests.mail.compose.drafts.ReplyingMessageDoesntCreateDraft;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.DisplayMail;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.DisplayMail.Field;
import com.zimbra.qa.selenium.projects.ajax.pages.preferences.PagePreferences;
import com.zimbra.qa.selenium.projects.ajax.pages.preferences.TreePreferences.TreeItem;

public class DisplayMailContent extends SetGroupMailByMessagePreference {

	public DisplayMailContent() {
		logger.info("New "+ ReplyingMessageDoesntCreateDraft.class.getCanonicalName());
	}


	@Test (description = "Verify multiline body mail(HTML content) displayed properly when display mail pref set to HTML",
			groups = { "sanity", "L1" })

	public void DisplayHTMLMailWithHTMLPref_01() throws HarnessException {

		String multilineHtmlData = null;
		if (ConfigProperties.getStringProperty("browser").contains("firefox")
				|| ConfigProperties.getStringProperty("browser").contains("edge")) {
			multilineHtmlData = "<div><span style=\"font-size: 14pt;\"><span style=\"font-family: &quot;comic sans ms&quot;,&quot;comic sans&quot;,sans-serif;\">Number list below</span>:</span></div>";
		} else {
			multilineHtmlData = "<div style=\"\"><span style=\"font-size: 14pt;\"><span style=\"font-family: &quot;comic sans ms&quot;, &quot;comic sans&quot;, sans-serif;\">Number list below</span>:</span></div>";
		}

		final String mimeFile = ConfigProperties.getBaseDirectory() + "/data/public/mime/email19/multilineHTMLcontent.txt";
		final String subject = "subject13214016725788";
		final String boldContent = "<div style=\"\"><strong style=\"\">BoldString</strong></div>";
		final String italicContent = "<div style=\"\"><em style=\"\">ItalicString</em></div>";
		final String underlineContent ="<span style=\"text-decoration: underline;\">Underline text</span>";
		final String colorFontContent ="<span style=\"color: rgb(255, 0, 0);\">Red color text</span></div>";
		final String colorBackgroundContent ="<span style=\"background-color: rgb(51, 153, 102);\">Green background</span></div>";
		final String numberedListContent ="<ol style=\"\"><li style=\"\">point one</li><li style=\"\">point two</li><li style=\"\">point three</li></ol>";

		// Inject the sample mime
		injectMessage(app.zGetActiveAccount(), mimeFile);

		// Refresh current view
		ZAssert.assertTrue(app.zPageMail.zVerifyMailExists(subject), "Verify message displayed in current view");

		// Select the message so that it shows in the reading pane
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);

		// Verify body content
		ZAssert.assertStringContains(actual.zGetMailProperty(Field.Body), boldContent, "Verify bold text content");
		ZAssert.assertStringContains(actual.zGetMailProperty(Field.Body), italicContent, "Verify italic text content");
		ZAssert.assertStringContains(actual.zGetMailProperty(Field.Body), underlineContent, "Verify underline text content");
		ZAssert.assertStringContains(actual.zGetMailProperty(Field.Body), colorFontContent, "Verify colored body text content");
		ZAssert.assertStringContains(actual.zGetMailProperty(Field.Body), colorBackgroundContent, "Verify color background text content");
		ZAssert.assertStringContains(actual.zGetMailProperty(Field.Body), multilineHtmlData, "Verify font family and size of text content");
		ZAssert.assertStringContains(actual.zGetMailProperty(Field.Body), numberedListContent, "Verify numbered list text content");
	}


	@Test (description = "Verify multiline body mail(HTML content) displayed properly when display mail pref set to PlainText",
			groups = { "sanity", "L1" })

	public void DisplayHTMLMailWithTextPref_02() throws HarnessException {

		String multilineTextData = null;
		if (ConfigProperties.getStringProperty("browser").contains("firefox")) {
			multilineTextData = "BoldString <br>ItalicString <br>Underline text <br>Red color text <br>Green background <br><br>Number list below : <br><br><br>&nbsp;&nbsp; &nbsp;1. point one <br>&nbsp;&nbsp; &nbsp;2. point two <br>&nbsp;&nbsp; &nbsp;3. point three";
		} else {
			multilineTextData = "BoldString <br style=\"\" />ItalicString <br style=\"\" />Underline text <br style=\"\" />Red color text <br style=\"\" />Green background <br style=\"\" /><br style=\"\" />Number list below : <br style=\"\" /><br style=\"\" /><br style=\"\" />    1. point one <br style=\"\" />    2. point two <br style=\"\" />    3. point three";
		}

		// Navigate to preference -> Mail and set display mail pref to Text and verify
		app.zPagePreferences.zNavigateTo();
		app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Mail);

		ZAssert.assertTrue(app.zPagePreferences.sIsElementPresent(PagePreferences.Locators.zDisplayMailAsText), "Verify Display Mail as Text radio button is present");

		app.zPagePreferences.sClick(PagePreferences.Locators.zDisplayMailAsText);
		app.zPagePreferences.zWaitForBusyOverlay();

		app.zPagePreferences.zToolbarPressButton(Button.B_SAVE);

		app.zGetActiveAccount().soapSend(
				"<GetPrefsRequest xmlns='urn:zimbraAccount'>"
		+			"<pref name='zimbraPrefMessageViewHtmlPreferred'/>"
		+		"</GetPrefsRequest>");

		String value = app.zGetActiveAccount().soapSelectValue("//acct:pref[@name='zimbraPrefMessageViewHtmlPreferred']", null);
		ZAssert.assertEquals(value, "FALSE", "Verify zimbraPrefMessageViewHtmlPreferred preference changed to Text");

		final String mimeFile = ConfigProperties.getBaseDirectory() + "/data/public/mime/email19/multilineHTMLcontent.txt";
		final String subject = "subject13214016725788";

		if (!app.zPageMail.zVerifyMailExists(subject)) {
			injectMessage(app.zGetActiveAccount(), mimeFile);
			ZAssert.assertTrue(app.zPageMail.zVerifyMailExists(subject), "Verify message displayed in current view");
		}

		// Verify body content
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		ZAssert.assertStringContains(actual.zGetMailProperty(Field.Body), multilineTextData, "Verify plain text content");
	}


	@Test (description = "Verify multiline body mail(plain text content) displayed properly when display mail pref set to HTML",
			groups = { "sanity", "L1" })

	public void DisplayTextMailWithHTMLPref_03() throws HarnessException {

		String multilineTextData = null;
		if (ConfigProperties.getStringProperty("browser").contains("firefox")) {
			multilineTextData = "line 1<br>line 2<br>line 3<br><br>line 4";
		} else {
			multilineTextData = "line 1<br style=\"\" />line 2<br style=\"\" />line 3<br style=\"\" /><br style=\"\" />line 4";
		}
		app.zGetActiveAccount().soapSend(
				"<GetPrefsRequest xmlns='urn:zimbraAccount'>"
		+			"<pref name='zimbraPrefMessageViewHtmlPreferred'/>"
		+		"</GetPrefsRequest>");

		String value = app.zGetActiveAccount().soapSelectValue("//acct:pref[@name='zimbraPrefMessageViewHtmlPreferred']", null);

		if (value.equalsIgnoreCase("FALSE")) {

			// Navigate to preference -> Mail and set display mail pref to Text and verify
			app.zPagePreferences.zNavigateTo();
			app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Mail);

			ZAssert.assertTrue(app.zPagePreferences.sIsElementPresent(PagePreferences.Locators.zDisplayMailAsHTML), "Verify Display Mail as HTML radio button is present");

			app.zPagePreferences.sClick(PagePreferences.Locators.zDisplayMailAsHTML);
			app.zPagePreferences.zWaitForBusyOverlay();

			app.zPagePreferences.zToolbarPressButton(Button.B_SAVE);

			app.zGetActiveAccount().soapSend(
					"<GetPrefsRequest xmlns='urn:zimbraAccount'>"
			+			"<pref name='zimbraPrefMessageViewHtmlPreferred'/>"
			+		"</GetPrefsRequest>");

			String value1 = app.zGetActiveAccount().soapSelectValue("//acct:pref[@name='zimbraPrefMessageViewHtmlPreferred']", null);
			ZAssert.assertEquals(value1, "TRUE", "Verify zimbraPrefMessageViewHtmlPreferred preference changed to HTML");
		}

		final String mimeFile = ConfigProperties.getBaseDirectory() + "/data/public/mime/email19/multilineTextcontent.txt";
		final String subject = "subject13214016777777";

		// Inject the sample mime
		injectMessage(app.zGetActiveAccount(), mimeFile);

		// Refresh current view
		ZAssert.assertTrue(app.zPageMail.zVerifyMailExists(subject), "Verify message displayed in current view");

		// Verify body content
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		ZAssert.assertStringContains(actual.zGetMailProperty(Field.Body), multilineTextData, "Verify plain text content");
	}


	@Test (description = "Verify multiline body mail(plain text content) displayed properly when display mail pref set to PlainText",
			groups = { "sanity", "L1" })

	public void DisplayTextMailWithTextPref_04() throws HarnessException {

		String multilineTextData = null;
		if (ConfigProperties.getStringProperty("browser").contains("firefox")) {
			multilineTextData = "line 1<br>line 2<br>line 3<br><br>line 4";
		} else {
			multilineTextData = "line 1<br style=\"\" />line 2<br style=\"\" />line 3<br style=\"\" /><br style=\"\" />line 4";
		}

		app.zGetActiveAccount().soapSend(
				"<GetPrefsRequest xmlns='urn:zimbraAccount'>"
		+			"<pref name='zimbraPrefMessageViewHtmlPreferred'/>"
		+		"</GetPrefsRequest>");

		String value = app.zGetActiveAccount().soapSelectValue("//acct:pref[@name='zimbraPrefMessageViewHtmlPreferred']", null);

		if (value.equals("TRUE")) {

			// Navigate to preference -> Mail and set display mail pref to Text and verify
			app.zPagePreferences.zNavigateTo();
			app.zTreePreferences.zTreeItem(Action.A_LEFTCLICK, TreeItem.Mail);

			ZAssert.assertTrue(app.zPagePreferences.sIsElementPresent(PagePreferences.Locators.zDisplayMailAsText), "Verify Display Mail as Text radio button is present");

			app.zPagePreferences.sClick(PagePreferences.Locators.zDisplayMailAsText);
			app.zPagePreferences.zWaitForBusyOverlay();

			app.zPagePreferences.zToolbarPressButton(Button.B_SAVE);

			app.zGetActiveAccount().soapSend(
					"<GetPrefsRequest xmlns='urn:zimbraAccount'>"
			+			"<pref name='zimbraPrefMessageViewHtmlPreferred'/>"
			+		"</GetPrefsRequest>");

			String value1 = app.zGetActiveAccount().soapSelectValue("//acct:pref[@name='zimbraPrefMessageViewHtmlPreferred']", null);
			ZAssert.assertEquals(value1, "FALSE", "Verify zimbraPrefMessageViewHtmlPreferred preference changed to Text");
		}

		final String mimeFile = ConfigProperties.getBaseDirectory() + "/data/public/mime/email19/multilineTextcontent.txt";
		final String subject = "subject13214016777777";

		// If mail already exist from previous testcases then don't inject
		if (!app.zPageMail.zVerifyMailExists(subject)) {
			injectMessage(app.zGetActiveAccount(), mimeFile);
			ZAssert.assertTrue(app.zPageMail.zVerifyMailExists(subject), "Verify message displayed in current view");
		}

		// Verify body content
		DisplayMail actual = (DisplayMail) app.zPageMail.zListItem(Action.A_LEFTCLICK, subject);
		ZAssert.assertStringContains(actual.zGetMailProperty(Field.Body), multilineTextData, "Verify plain text content");
	}
}