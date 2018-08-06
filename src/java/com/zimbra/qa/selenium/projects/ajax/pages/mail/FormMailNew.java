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
package com.zimbra.qa.selenium.projects.ajax.pages.mail;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.zimbra.qa.selenium.framework.core.SeleniumService;
import com.zimbra.qa.selenium.framework.items.IItem;
import com.zimbra.qa.selenium.framework.items.MailItem;
import com.zimbra.qa.selenium.framework.items.RecipientItem;
import com.zimbra.qa.selenium.framework.items.RecipientItem.RecipientType;
import com.zimbra.qa.selenium.framework.ui.AbsApplication;
import com.zimbra.qa.selenium.framework.ui.AbsForm;
import com.zimbra.qa.selenium.framework.ui.AbsPage;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.ConfigProperties;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.staf.Stafpostqueue;
import com.zimbra.qa.selenium.projects.ajax.pages.AjaxPages;
import com.zimbra.qa.selenium.projects.ajax.pages.AutocompleteEntry;
import com.zimbra.qa.selenium.projects.ajax.pages.AutocompleteEntry.Icon;
import com.zimbra.qa.selenium.projects.ajax.pages.DialogWarning;

/**
 * The <code>FormMailNew<code> object defines a compose new message view in the
 * Zimbra Ajax client.
 * <p>
 * This class can be used to compose a new message.
 * <p>
 *
 * @author Matt Rhoades
 * @see http://wiki.zimbra.com/wiki/Testing:_Selenium:_ZimbraSelenium_Overview#Mail_Page
 */
public class FormMailNew extends AbsForm {

	WebElement we = null;

	public static class Locators {

		public static final String zSendIconBtn = "css=div[id^='ztb__COMPOSE-1'] td[id$='zb__COMPOSE-1__SEND_title']";
		public static final String zCancelIconBtn = "css=[id^=zb__COMPOSE][id$=__CANCEL_title]";
		public static final String zEditIconBtn = "css=[id$='main__EDIT_title']";
		public static final String zCloseIconBtn = "css=[id^=zb__COMPOSE][id$=__CANCEL_title]";
		public static final String zSaveDraftIconBtn = "css=[id^=zb__COMPOSE][id$=__SAVE_DRAFT_title]";
		public static final String zSpellCheckIconBtn = "css=[id^=zb__COMPOSE][id$=__SPELL_CHECK_left_icon]>div.ImgSpellCheck";

		public static final String zOptionsButton = "css=div[id^='ztb__COMPOSE'] td[id$='__COMPOSE_OPTIONS_title']";
		public static final String zDontIncludeOriginalMessageReply = "css=div[id$='_REPLY__INC_NONE'] td[id$='_REPLY__INC_NONE_title']";
		public static final String zDontIncludeOriginalMessageForward = "css=div[id$='_FORWARD__INC_NONE'] td[id$='_FORWARD__INC_NONE_title']";
		public static final String zIncludeOriginalMessageReply = "css=div[id$='_REPLY__INC_BODY'] td[id$='_REPLY__INC_BODY_title']";
		public static final String zIncludeOriginalMessageForward = "css=div[id$='_FORWARD_ATT__INC_BODY'] td[id$='_FORWARD_ATT__INC_BODY_title']";
		public static final String zIncludeLastMessageOnlyReply = "css=div[id$='_REPLY__INC_SMART'] td[id$='_REPLY__INC_SMART_title']";
		public static final String zIncludeLastMessageOnlyForward = "css=div[id$='_FORWARD__INC_SMART'] td[id$='_FORWARD__INC_SMART_title']";
		public static final String zIncludeOriginalAsAttachmentReply = "css=div[id$='_REPLY__INC_ATTACHMENT'] td[id$='_REPLY__INC_ATTACHMENT_title']:contains('Include Original As Attachment')";
		public static final String zIncludeOriginalAsAttachmentForward = "css=div[id$='_FORWARD_ATT__INC_ATTACHMENT'] td[id$='_FORWARD_ATT__INC_ATTACHMENT_title']";
		public static final String zUsePrefixReply = "css=div[id$='_REPLY__USE_PREFIX'] td[id$='_REPLY__USE_PREFIX_title']";
		public static final String zUsePrefixForward = "css=div[id$='_FORWARD_ATT__USE_PREFIX'] td[id$='_FORWARD_ATT__USE_PREFIX_title']";
		public static final String zIncludeHeadersReply = "css=div[id$='_REPLY__INCLUDE_HEADERS'] td[id$='_REPLY__INCLUDE_HEADERS_title']";
		public static final String zIncludeHeadersForward = "css=div[id$='_FORWARD_ATT__INCLUDE_HEADERS'] td[id$='_FORWARD_ATT__INCLUDE_HEADERS_title']";
		public static final String zPrefixInHeaderReplyCompose = "css=div[data-marker='__HEADERS__'] blockquote[style*='border-left:2px solid #1010FF;']";
		public static final String zPrefixInQuotedTextReplyCompose = "css=div[data-marker='__QUOTED_TEXT__'] blockquote[style*='border-left:2px solid #1010FF;']";
		
		public static final String zToField = "css=input[id^='zv__COMPOSE'][id$='_to_control']";
		public static final String zToField_Edge = "css=textarea[id^='zv__COMPOSE'][id$='_to_control']";
		public static final String zCcField = "css=input[id^=zv__COMPOSE][id$=_cc_control]";
		public static final String zCcField_Edge = "css=textarea[id^=zv__COMPOSE][id$=_cc_control]";
		public static final String zBccField = "css=input[id^=zv__COMPOSE][id$=_bcc_control]";
		public static final String zBccField_Edge = "css=textarea[id^=zv__COMPOSE][id$=_bcc_control]";
		public static final String zSubjectField = "css=div[id^=zv__COMPOSE] input[id$=_subject_control]";
		public static final String zAttachmentField = "css=div[id$=_attachments_div]";
		public static final String zAttachmentImage = "css=div[id$=_attachments_div] div[class='ImgAttachment']";
		public static final String zAttachmentCheckbox = "css=div[id$=_attachments_div] input[name='ZmComposeView_forAttName1']";
		public static final String zAttachmentText = "css=div[id$=_attachments_div] a[class='AttLink']:contains(";
		public static final String zLinkText = "css=iframe[id*='DWT'][class*='Editor']";
		public static final String zPlainTextBodyField = "css=div[class='ZmHtmlEditor'] [class='ZmHtmlEditorTextArea']";

		public static final String zBodyFrameHTML = "//div[contains(id,'zv__COMPOSE')]//iframe";
		public static final String zBodyFrameCss = "css=[id$='_body_ifr']";

		public static final String zPriorityPulldown = "css=[id*='__COMPOSE'][id$='___priority_dropdown']";
		public static final String zPriorityOptionHigh = "css=[id*='__COMPOSE'][id$='___priority_dropdown']";
		public static final String zPriorityOptionNormal = "css=[id*='__COMPOSE'][id$='___priority_dropdown']";
		public static final String zPriorityOptionLow = "css=[id*='__COMPOSE'][id$='___priority_dropdown']";

		public static final String zBubbleToField = "css=[id^=zv__COMPOSE][id$=_to_cell]";
		public static final String zBubbleCcField = "css=[id^=zv__COMPOSE][id$=_cc_cell]";
		public static final String zBubbleBccField = "css=[id^=zv__COMPOSE][id$=_bcc_cell]";
		public static final String CcField = "css= td[id='zv__COMPOSE-1_cc_cell'] div[class='addrBubbleContainer']";
		public static final String ToField = "css= td[id='zv__COMPOSE-1_to_cell'] div[class='addrBubbleContainer']";
		public static final String zBubbleExpand = "css=div.ImgBubbleExpand";
		
		public static final String FormatAsHTMLMenu = "css=div[id^='zm__COMPOSE'][style*='z-index'] div[id$='__FORMAT_HTML'] tr[id^='POPUP_zmi__COMPOSE']";
		public static final String FormatAsPlainTextMenu = "css=div[id^='zm__COMPOSE'][style*='z-index'] div[id$='__FORMAT_TEXT'] tr[id^='POPUP_zmi__COMPOSE']";
		public static final String zOptionsdropdown = "css=div[id^='ztb__COMPOSE']:not([aria-hidden='true']) [id^=zb__COMPOSE][id$=__COMPOSE_OPTIONS_dropdown]";

		// Spell check locators
		public static final String zMisspelledWordCss = "css=body[id='tinymce'] span[class='ZM-SPELLCHECK-MISSPELLED']";
		public static final String zAddMisspelledWord = "css=div[id^='POPUP_'] div[id='add'] td[id$='_title']";
		public static final String zIgnoreMisspelledWord = "css=div[id^='POPUP_'] div[id='ignore'] td[id$='_title']";

		// Spell check warning dialog locators when Mandatory spell check is enabled
		public static final String zSpellCheckWarningDialog = "css=div[id^='SpellCheckConfirm'][class='DwtDialog']";
		public static final String zSpellCheckWarningDialogContent = "css=div[id^='SpellCheckConfirm'] [class='DwtMsgArea']";
		public static final String zCorrectSpellingBtn = "css=div[id^='SpellCheckConfirm'][id$='_button5']";
		public static final String zSendAnywayBtn = "css=div[id^='SpellCheckConfirm'][id$='_button4']";

		// TinyMCE Editor buttons
		public static final String zDirectionLeftButton = "css=i[class='mce-ico mce-i-ltr']";
		public static final String zDirectionRightButton = "css=i[class='mce-ico mce-i-rtl']";
		public static final String zBoldButton = "css=i[class='mce-ico mce-i-bold']";
		public static final String zItalicButton = "css=i[class='mce-ico mce-i-italic']";
		public static final String zTextColorDropdown = "css=div[class='mce-widget mce-btn mce-btn-small mce-colorbutton mce-first'] [class='mce-open']";
		public static final String zTextBackgroundColorDropdown = "css=div[class='mce-widget mce-btn mce-btn-small mce-colorbutton mce-last'] [class='mce-open']";
		public static final String zTextColorRed = "css=div[data-mce-color='#FF0000']";
		public static final String zTextColorTransparent = "css=div[data-mce-color='transparent']";
		public static final String zTextBackgroundColorGreen = "css=div[class='mce-container mce-panel mce-floatpanel mce-popover mce-bottom mce-start']:not([style*='display']) [data-mce-color='#008000']";
		public static final String zTextBackgroundColorTransparent = "css=div[class='mce-container mce-panel mce-floatpanel mce-popover mce-bottom mce-start']:not([style*='display']) [data-mce-color='transparent']";

		public static final String zAddAttachmentFromOriginalMsgLink = "css=tr[id$='_reply_attachments_link'] a";

		public static final String zOkCancelContinueComposeWarningDialog = "css=div#OkCancel.DwtDialog";
		public static final String zOkBtnOnContinueComposeWarningDialog = "css=div#OkCancel.DwtDialog td[id^='OK'] td[id$='_title']";
		public static final String zCancelBtnOnContinueComposeWarningDialog = "css=div#OkCancel.DwtDialog td[id^='Cancel'] td[id$='_title']";
		public static final String zSendLaterDisabled = "css=div[id='SEND_LATER'][class*='ZDisabled']";

	}

	public static class Field {

		public static final Field To = new Field("To");
		public static final Field Cc = new Field("Cc");
		public static final Field Bcc = new Field("Bcc");
		public static final Field From = new Field("From");
		public static final Field Subject = new Field("Subject");
		public static final Field Body = new Field("Body");

		private String field;

		private Field(String name) {
			field = name;
		}

		@Override
		public String toString() {
			return (field);
		}

	}

	public FormMailNew(AbsApplication application) {
		super(application);

		logger.info("new " + FormMailNew.class.getCanonicalName());

	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public void zSubmit() throws HarnessException {
		zToolbarPressButton(Button.B_SEND);
	}

	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButton(" + button + ")");

		tracer.trace("Click button " + button);

		if (button == null)
			throw new HarnessException("Button cannot be null!");

		AbsPage page = null;
		String locator = null;

		if (button == Button.B_SEND) {

			if (sIsElementPresent("css=div[id^='ztb__COMPOSE-2'] td[id='zb__COMPOSE-2__SEND_title']")) {
				locator = "css=div[id^='ztb__COMPOSE-2'] td[id='zb__COMPOSE-2__SEND_title']";
			} else {
				locator = Locators.zSendIconBtn;
			}

			// Click on send
			this.sClick(locator);
			this.zWaitForBusyOverlay();

			// Wait for the message to be delivered
			Stafpostqueue sp = new Stafpostqueue();
			sp.waitForPostqueue();

			return (page);

		} else if (button == Button.B_CANCEL) {

			locator = Locators.zCancelIconBtn;
			page = new DialogWarning(DialogWarning.DialogWarningID.SaveCurrentMessageAsDraft, this.MyApplication,
					((AjaxPages) this.MyApplication).zPageMail);
			this.sClickAt(locator, "0,0");
			this.zWaitForBusyOverlay();

			return (page);

		} else if (button == Button.B_EDIT) {

			locator = Locators.zEditIconBtn;
			page = null;
			this.sClickAt(locator, "0,0");

			this.zWaitForBusyOverlay();
			return (page);

		} else if (button == Button.B_CLOSE) {

			locator = Locators.zCloseIconBtn;
			page = null;
			this.sClickAt(locator, "0,0");

			this.zWaitForBusyOverlay();
			return (page);

		} else if (button == Button.B_SAVE_DRAFT) {

			locator = Locators.zSaveDraftIconBtn;
			page = this;

		} else if (button == Button.B_ADD_ATTACHMENT) {

			throw new HarnessException("implement me (?)");

		} else if (button == Button.B_SPELL_CHECK) {

			locator = Locators.zSpellCheckIconBtn;
			page = this;

		} else if (button == Button.B_SIGNATURE) {

			throw new HarnessException("use zToolbarPressPulldown to attach signature");

		} else if (button == Button.B_OPTIONS) {

			locator = Locators.zOptionsButton;
			page = this;

		} else if (button == Button.B_TO) {

			locator = "css=div[id$='__TO'] td[id$='__TO_title']";
			page = new FormAddressPicker(this.MyApplication);

		} else if (button == Button.B_CC) {

			locator = "css=div[id$='__CC'] td[id$='__CC_title']";
			page = new FormAddressPicker(this.MyApplication);

		} else if (button == Button.B_BCC) {

			locator = "css=div[id$='__BCC'] td[id$='__BCC_title']";
			page = new FormAddressPicker(this.MyApplication);

		} else if (button == Button.B_SHOWBCC) {

			page = this;

			// Show Bcc is under option drop-down menu
			String optionLocator = "css=[id$=__COMPOSE_OPTIONS_title]";
			this.sClick(optionLocator);
			locator = "css=div[id$=SHOW_BCC]";

			if (zBccIsActive())
				return (this);

			this.sClick(locator);
			this.zWaitForBusyOverlay();

			return (page);
		} else {
			throw new HarnessException("no logic defined for button " + button);
		}

		if (locator == null)
			throw new HarnessException("locator was null for button " + button);

		this.sClick(locator);
		this.zWaitForBusyOverlay();
		SleepUtil.sleepMedium();

		if (page != null) {
			page.zWaitForActive();
		}

		return (page);
	}

	public AbsPage zToolbarPressPulldown(Button pulldown, Button option) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressPulldown(" + pulldown + ", " + option + ")");

		tracer.trace("Click pulldown " + pulldown + " then " + option);

		if (pulldown == null)
			throw new HarnessException("Pulldown cannot be null!");

		if (option == null)
			throw new HarnessException("Option cannot be null!");

		String pulldownLocator = null;
		String optionLocator = null;
		AbsPage page = null;

		// Get subject
		SleepUtil.sleepSmall();
		we = webDriver().findElement(By.cssSelector("input[class='subjectField']"));
		String subject = we.getAttribute("value");

		if (pulldown == Button.B_OPTIONS) {

			if (option == Button.O_OPTION_REQUEST_READ_RECEIPT) {

				pulldownLocator = "css=div[id^='ztb__COMPOSE'] div[id$='__COMPOSE_OPTIONS'] td[id$='_dropdown']";
				optionLocator = "css=div[id^='zm__COMPOSE'] div[id*='REQUEST_READ_RECEIPT'] td[id$='_title']";
				page = null;

			} else if (option == Button.O_FORMAT_AS_HTML) {

				pulldownLocator = Locators.zOptionsdropdown;
				optionLocator = Locators.FormatAsHTMLMenu;

				page = null;

			} else if (option == Button.O_FORMAT_AS_PLAIN_TEXT) {

				pulldownLocator = Locators.zOptionsdropdown;
				optionLocator = Locators.FormatAsPlainTextMenu;

				page = new DialogWarning(DialogWarning.DialogWarningID.ComposeOptionsChangeWarning, this.MyApplication,
						((AjaxPages) this.MyApplication).zPageMail);

			} else if (option == Button.O_DONT_INCLUDE_ORIGINAL_MESSAGE) {

				pulldownLocator = Locators.zOptionsdropdown;

				if (subject.startsWith("Re:")) {
					optionLocator = Locators.zDontIncludeOriginalMessageReply;
				} else {
					optionLocator = Locators.zDontIncludeOriginalMessageForward;
				}

				page = null;

			} else if (option == Button.O_INCLUDE_ORIGINAL_MESSAGE) {

				pulldownLocator = Locators.zOptionsdropdown;

				if (subject.startsWith("Re:")) {
					optionLocator = Locators.zIncludeOriginalMessageReply;
				} else {
					optionLocator = Locators.zIncludeOriginalMessageForward;
				}

				page = null;

			} else if (option == Button.O_INCLUDE_LAST_MESSAGE_ONLY) {

				pulldownLocator = Locators.zOptionsdropdown;

				if (subject.startsWith("Re:")) {
					optionLocator = Locators.zIncludeLastMessageOnlyReply;
				} else {
					optionLocator = Locators.zIncludeLastMessageOnlyForward;
				}

				page = null;

			} else if (option == Button.O_INCLUDE_ORIGINAL_AS_ATTACHMENT) {

				pulldownLocator = Locators.zOptionsdropdown;

				if (subject.startsWith("Re:")) {
					optionLocator = Locators.zIncludeOriginalAsAttachmentReply;
				} else {
					optionLocator = Locators.zIncludeOriginalAsAttachmentForward;
				}

				page = null;

			} else if (option == Button.O_USE_PRIFIX) {

				pulldownLocator = Locators.zOptionsdropdown;

				if (subject.startsWith("Re:")) {
					optionLocator = Locators.zUsePrefixReply;
				} else {
					optionLocator = Locators.zUsePrefixForward;
				}

				page = null;

			} else if (option == Button.O_INCLUDE_HEADERS) {

				pulldownLocator = Locators.zOptionsdropdown;

				if (subject.startsWith("Re:")) {
					optionLocator = Locators.zIncludeHeadersReply;
				} else {
					optionLocator = Locators.zIncludeHeadersForward;
				}

				page = null;

			} else {
				throw new HarnessException("implement " + pulldown + " and " + option);
			}

		} else if (pulldown == Button.B_PRIORITY) {

			pulldownLocator = "css=[id$=__COMPOSE_OPTIONS_title]";

			if (option == Button.O_PRIORITY_HIGH) {
				optionLocator = "css=div[id$=PRIORITY_HIGH]";
				page = this;

			} else if (option == Button.O_PRIORITY_NORMAL) {
				optionLocator = "css=div[id$=PRIORITY_NORMAL]";
				page = this;

			} else if (option == Button.O_PRIORITY_LOW) {
				optionLocator = "css=div[id$=PRIORITY_LOW]";
				page = this;

			} else {
				throw new HarnessException("unsupported priority option " + option);
			}

		} else if (pulldown == Button.B_SECURE_EMAIL) {

			pulldownLocator = "css=td[id$='_com_zimbra_securemail_checkbox_title']";

			if (option == Button.O_DONT_SIGN) {
				optionLocator = "css=div[id$='_com_zimbra_securemail_menu'] table tbody tr:nth-child(1)";
				page = this;

			} else if (option == Button.O_SIGN) {
				optionLocator = "css=div[id$='_com_zimbra_securemail_menu'] table tbody tr:nth-child(2)";
				page = this;

			} else if (option == Button.O_SIGN_AND_ENCRYPT) {
				optionLocator = "css=div[id$='_com_zimbra_securemail_menu'] table tbody tr:nth-child(3)";
				page = this;

			} else {
				throw new HarnessException("unsupported priority option " + option);
			}

		} else if (pulldown == Button.B_SEND) {

			pulldownLocator = "css=div[id$='__SEND_MENU'] td[id$='__SEND_MENU_dropdown']>div";

			if (option == Button.O_SEND_SEND) {

				// This action requires a 'wait for postfix queue'
				optionLocator = "css=tr#POPUP_SEND td#SEND_title";
				page = null;

				if (!this.sIsElementPresent(pulldownLocator)) {
					throw new HarnessException("Button " + pulldown + " option " + option + " pulldownLocator "
							+ pulldownLocator + " not present!");
				}

				this.sRightClick(pulldownLocator);
				this.zWaitForBusyOverlay();

				if (!this.sIsElementPresent(optionLocator)) {
					throw new HarnessException("Button " + pulldown + " option " + option + " optionLocator "
							+ optionLocator + " not present!");
				}

				this.sClickAt(optionLocator, "");
				this.zWaitForBusyOverlay();

				Stafpostqueue sp = new Stafpostqueue();
				sp.waitForPostqueue();

				return (page);

			} else if (option == Button.O_SEND_SEND_LATER) {

				optionLocator = "css=tr#POPUP_SEND_LATER td#SEND_LATER_title";
				page = new DialogSendLater(this.MyApplication, ((AjaxPages) MyApplication).zPageMail);

				if (!this.sIsElementPresent(pulldownLocator)) {
					throw new HarnessException("Button " + pulldown + " option " + option + " pulldownLocator "
							+ pulldownLocator + " not present!");
				}

				this.sRightClick(pulldownLocator);
				this.zWaitForBusyOverlay();

				if (!this.sIsElementPresent(optionLocator)) {
					throw new HarnessException("Button " + pulldown + " option " + option + " optionLocator "
							+ optionLocator + " not present!");
				}

				this.sClickAt(optionLocator, "");
				SleepUtil.sleepSmall();
				this.zWaitForBusyOverlay();
				page.zWaitForActive();

				return (page);

			} else {
				throw new HarnessException("unsupported pulldown/option " + pulldown + "/" + option);
			}

		} else {
			throw new HarnessException("no logic defined for pulldown " + pulldown);
		}

		if (pulldownLocator != null) {

			if (!this.sIsElementPresent(pulldownLocator)) {
				throw new HarnessException("Button " + pulldown + " option " + option + " pulldownLocator "
						+ pulldownLocator + " not present!");
			}

			this.sClickAt(pulldownLocator, "");
			this.zWaitForBusyOverlay();

			if (optionLocator != null) {

				if (!this.sIsElementPresent(optionLocator)) {
					throw new HarnessException("Button " + pulldown + " option " + option + " optionLocator "
							+ optionLocator + " not present!");
				}

				this.sClickAt(optionLocator, "");
				this.zWaitForBusyOverlay();
				SleepUtil.sleepMedium();
			}

			if (page != null) {
				page.zWaitForActive();
			}
		}

		return (page);
	}

	public void zSetFromIdentity(String value) throws HarnessException {
		logger.info(myPageName() + " zSetFrom(" + value + ")");

		String pulldownLocator = "css=div[id^='zv__COMPOSE'] tr[id$='_identity_row'] td[id$='_dropdown']";
		String optionLocator = "css=td[id$='_title']:contains(" + value + ")";

		if (pulldownLocator != null) {

			if (!this.sIsElementPresent(pulldownLocator)) {
				throw new HarnessException("pulldownLocator not present! " + pulldownLocator);
			}

			this.sClickAt(pulldownLocator, "");
			this.zWaitForBusyOverlay();

			if (optionLocator != null) {
				if (!this.sIsElementPresent(optionLocator)) {
					throw new HarnessException("optionLocator not present! " + optionLocator);
				}

				this.sClickAt(optionLocator, "");
				SleepUtil.sleepSmall();
				this.zWaitForBusyOverlay();
			}
		}

		DialogWarning dialog = new DialogWarning(DialogWarning.DialogWarningID.ComposeOptionsChangeWarning,
				MyApplication, ((AjaxPages) MyApplication).zPageMail);
		if (dialog.zIsActive()) {
			dialog.zPressButton(Button.B_OK);
		}
	}

	public void zFillField(Field field, String value) throws HarnessException {

		tracer.trace("Set " + field + " to " + value);

		String locator = null;

		if (field == Field.From) {

			zSetFromIdentity(value);

			return;

		} else if (field == Field.To) {

			if (ConfigProperties.getStringProperty("browser").contains("edge")) {
				locator = "css=textarea[id$='_to_control']";
			} else {
				locator = Locators.zToField;
			}

			if (!this.sIsElementPresent(locator))
				throw new HarnessException("Field is not present field=" + field + " locator=" + locator);

			this.sFocus(locator);
			this.sClick(locator);
			this.zWaitForBusyOverlay();

			this.sType(locator, value);
			// this.zKeyboard.zTypeCharacters(value);
			SleepUtil.sleepMedium();
			this.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ENTER);
			SleepUtil.sleepSmall();
			this.zWaitForBusyOverlay();

			return;

		} else if (field == Field.Cc) {

			if (ConfigProperties.getStringProperty("browser").contains("edge")) {
				locator = "css=textarea[id$='_cc_control']";
			} else {
				locator = Locators.zCcField;
			}

			if (!this.sIsElementPresent(locator))
				throw new HarnessException("Field is not present field=" + field + " locator=" + locator);

			this.sFocus(locator);
			this.sClick(locator);
			this.zWaitForBusyOverlay();

			this.sType(locator, value);
			SleepUtil.sleepSmall();
			this.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ENTER);
			SleepUtil.sleepSmall();
			this.zWaitForBusyOverlay();

			return;

		} else if (field == Field.Bcc) {

			if (ConfigProperties.getStringProperty("browser").contains("edge")) {
				locator = "css=textarea[id$='_bcc_control']";
			} else {
				locator = Locators.zBccField;
			}

			if (!zBccIsActive()) {
				this.zToolbarPressButton(Button.B_SHOWBCC);
			}

			if (!this.sIsElementPresent(locator))
				throw new HarnessException("Field is not present field=" + field + " locator=" + locator);

			this.sFocus(locator);
			this.sClick(locator);
			this.zWaitForBusyOverlay();

			this.sType(locator, value);
			SleepUtil.sleepSmall();
			this.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ENTER);
			SleepUtil.sleepSmall();
			this.zWaitForBusyOverlay();

			return;

		} else if (field == Field.Subject) {

			locator = Locators.zSubjectField;

		} else if (field == Field.Body) {

			SleepUtil.sleepLong();

			locator = "css=div[id^='zv__COMPOSE'] iframe[id$='_body_ifr']";
			if (this.sIsElementPresent(locator) && this.zIsVisiblePerPosition(locator, 0, 0)) {

				logger.info("FormMailNew.zFillField: Html Compose");

				try {

					this.sSelectFrame("css=iframe[id$='_body_ifr']");
					locator = "css=html body";

					if (!this.sIsElementPresent(locator))
						throw new HarnessException("Unable to locate compose body");

					this.sFocus(locator);
					this.sClick(locator);
					this.sType(locator, value);
					// this.zKeyboard.zTypeCharacters(value);

				} finally {
					this.sSelectFrame("relative=top");
				}

				this.zWaitForBusyOverlay();

				return;

			}

			locator = "css=div[id^='zv__COMPOSE'] textarea.ZmHtmlEditorTextArea";
			if (this.sIsElementPresent(locator) && this.zIsVisiblePerPosition(locator, 0, 0)) {

				logger.info("FormMailNew.zFillField: Text Compose");

				this.sFocus(locator);
				this.sClick(locator);
				this.zWaitForBusyOverlay();
				this.sType(locator, value);

				SleepUtil.sleepSmall();

				return;
			}

			int frames = this.sGetCssCount("css=iframe");
			logger.debug("Body: # of frames: " + frames);
			String browser = SeleniumService.getInstance().getSeleniumBrowser();

			if (browser.equalsIgnoreCase("edge")) {
				if (frames == 1) {
					// //
					// Text compose
					// //

					locator = "css=textarea[id*='textarea_']";

					if (!this.sIsElementPresent(locator))
						throw new HarnessException("Unable to locate compose body");

					this.sFocus(locator);
					this.sClick(locator);
					this.zWaitForBusyOverlay();
					this.sType(locator, value);

					return;

				} else if (frames == 2) {

					// locator = "css=iframe[id^='iframe_DWT']";
					locator = "css=iframe[id$='_content_ifr']";
					if (!this.sIsElementPresent(locator))
						throw new HarnessException("Unable to locate compose body");

					zTypeFormattedText(locator, value);

					this.zWaitForBusyOverlay();

					return;

				}

			} else {
				if (frames == 0) {
					// //
					// Text compose
					// //

					locator = "css=textarea[class='DwtHtmlEditorTextArea']";

					if (!this.sIsElementPresent(locator))
						throw new HarnessException("Unable to locate compose body");

					this.sFocus(locator);
					this.sClick(locator);
					this.zWaitForBusyOverlay();
					this.sType(locator, value);

					SleepUtil.sleepSmall();

					return;

				} else if (frames == 1) {
					// //
					// HTML compose
					// //

					try {

						locator = "css=iframe[id$='_content_ifr']";

						if (!this.sIsElementPresent(locator))
							throw new HarnessException("Unable to locate compose body");

						this.sFocus(locator);
						this.sClickAt(locator, "");

						zTypeFormattedText(locator, value);

					} finally {
						// Make sure to go back to the original iframe
						this.sSelectFrame("relative=top");

					}

					this.zWaitForBusyOverlay();

					SleepUtil.sleepSmall();

					return;

				} else if (frames >= 2) {
					// //
					// HTML compose
					// //

					try {

						this.sSelectFrame("css=body[id=tinymce]");
						locator = "css=html body";

						if (!this.sIsElementPresent(locator))
							throw new HarnessException("Unable to locate compose body");

						this.sFocus(locator);
						this.sClick(locator);

						// this.sType(locator, value);
						this.zKeyboard.zTypeCharacters(value);

					} finally {
						// Make sure to go back to the original iframe
						this.sSelectFrame("relative=top");

					}

					SleepUtil.sleepSmall();

					this.zWaitForBusyOverlay();

					return;

				} else {
					throw new HarnessException("Compose //iframe count was " + frames);
				}
			}

		} else {
			throw new HarnessException("not implemented for field " + field);
		}

		if (locator == null) {
			throw new HarnessException("locator was null for field " + field);
		}

		if (!this.sIsElementPresent(locator))
			throw new HarnessException("Field is not present field=" + field + " locator=" + locator);

		this.sFocus(locator);
		this.sClickAt(locator, "0,0");
		this.zWaitForBusyOverlay();

		if (field == Field.Subject) {
			for (int i=1; i<=3; i++) {
				this.sType(locator, value);
				if (this.sGetValue(locator).contains(value)) {
					logger.info("Message subject value typed properly in " + i + " attempt.");
					break;
				} else {
					logger.info("Couldn't type message subject value in " + i + " attempt.");
				}
			}
		} else {
			this.sType(locator, value);
		}

		this.zWaitForBusyOverlay();
		SleepUtil.sleepSmall();
	}

	private boolean zBccIsActive() throws HarnessException {
		logger.info(myPageName() + ".zBccIsActive()");

		String locator;

		locator = "css=div[id^='zv__COMPOSE'] tr[id$='_bcc_row']";
		if (!sIsElementPresent(locator))
			throw new HarnessException("Unable to locate the BCC field " + locator);

		locator = locator + "[style*=none]";
		return (!sIsElementPresent(locator));
	}

	@Override
	public void zFill(IItem item) throws HarnessException {
		logger.info(myPageName() + ".zFill(ZimbraItem)");
		logger.info(item.prettyPrint());

		// Make sure the item is a MailItem
		if (!(item instanceof MailItem)) {
			throw new HarnessException("Invalid item type - must be MailItem");
		}

		// Convert object to MailItem
		MailItem mail = (MailItem) item;

		// Handle the subject
		if (mail.dSubject != null) {
			zFillField(Field.Subject, mail.dSubject);

		}

		if (mail.dBodyText != null) {
			String textBody = "css=textarea[id*=ZmHtmlEditor]";
			sType(textBody, mail.dBodyText);
			sFireEvent(textBody, "keyup");

		}
		if (mail.dBodyHtml != null) {
			String bodyLocator = "css=iframe[id*='ifr']";
			zWaitForElementPresent(bodyLocator, "10000");
			sClickAt(bodyLocator, "");
			zTypeFormattedText(bodyLocator, mail.dBodyHtml);
		}

		// Handle the Recipient list, which can be a combination of To, Cc, Bcc, and
		// From
		StringBuilder to = null;
		StringBuilder cc = null;
		StringBuilder bcc = null;
		StringBuilder from = null;

		// Convert the list of recipients to a semicolon separated string
		List<RecipientItem> recipients = mail.dAllRecipients();
		if (recipients != null) {
			if (!recipients.isEmpty()) {

				for (RecipientItem r : recipients) {
					if (r.dType == RecipientType.To) {
						if (to == null) {
							to = new StringBuilder();
							to.append(r.dEmailAddress);
						} else {
							to.append(";").append(r.dEmailAddress);
						}
					}
					if (r.dType == RecipientType.Cc) {
						if (cc == null) {
							cc = new StringBuilder();
							cc.append(r.dEmailAddress);
						} else {
							cc.append(";").append(r.dEmailAddress);
						}
					}
					if (r.dType == RecipientType.Bcc) {
						if (bcc == null) {
							bcc = new StringBuilder();
							bcc.append(r.dEmailAddress);
						} else {
							bcc.append(";").append(r.dEmailAddress);
						}
					}
					if (r.dType == RecipientType.From) {
						if (from == null) {
							from = new StringBuilder();
							from.append(r.dEmailAddress);
						} else {
							from.append(";").append(r.dEmailAddress);
						}
					}
				}

			}
		}

		// Fill out the To field
		if (to != null) {
			this.zFillField(Field.To, to.toString());
		}

		if (cc != null) {
			this.zFillField(Field.Cc, cc.toString());
		}

		if (bcc != null) {
			this.zFillField(Field.Bcc, bcc.toString());
		}

	}

	@Override
	public boolean zIsActive() throws HarnessException {
		logger.info(myPageName() + " zIsActive()");

		// Look for the div
		String locator = "css=div[id^='ztb__COMPOSE']";

		if (!this.sIsElementPresent(locator)) {
			return (false);
		}

		if (!this.zIsVisiblePerPosition(locator, 0, 0)) {
			return (false);
		}

		logger.info(myPageName() + " zIsActive() = true");
		return (true);
	}

	public boolean zHasAttachment() throws HarnessException {

		String locator = "css=div[id$='_attachments_div'] div.attBubbleContainer";

		boolean hasBubble = this.sIsElementPresent(locator);

		return (hasBubble);

	}

	public boolean zHasAttachment(String attachmentName) throws HarnessException {
		// Is the bubble there?
		if (!zHasAttachment()) {
			return (false);
		}

		// Attachment bubble locator
		String locator = "css=div[id$='_attachments_div'] div.attBubbleContainer a.AttLink";

		// Get all attachments
		List<WebElement> attachments = getElements(locator);
		
		// Check attachment name 
		for(WebElement a : attachments) {
			if(a.getText().contains(attachmentName)) {
				logger.info("Found folder item: "+ a.getText());
				return true;
			}
		}
		return false;
	}
	
	public void zRemoveAttachment(String attachmentName) throws HarnessException {
		// Is the bubble there?
		if (!zHasAttachment()) {
			throw new HarnessException("Attchement bubble is not present!");
		} 
		
		// Check for the presence of attachment
		String attachmentLocator = "//div[@class='attBubbleContainer']//a[@class='AttLink'][.='" + attachmentName + "']";
		if(!sIsElementPresent(attachmentLocator)) {
			throw new HarnessException("Attchement '" + attachmentName + "' bubble is not present!");
		}
		
		// Remove attachment
		sClick(attachmentLocator + "/following-sibling::span");	
	}

	public List<AutocompleteEntry> zAutocompleteFillField(Field field, String value) throws HarnessException {
		logger.info(myPageName() + " zAutocompleteFillField(" + field + ", " + value + ")");

		tracer.trace("Set " + field + " to " + value);

		String locator = null;

		if (field == Field.To) {

			if (ConfigProperties.getStringProperty("browser").equals("edge")) {
				locator = Locators.zToField_Edge;
			} else {
				locator = Locators.zToField;
			}

		} else if (field == Field.Cc) {

			if (ConfigProperties.getStringProperty("browser").equals("edge")) {
				locator = Locators.zCcField_Edge;
			} else {
				locator = Locators.zCcField;
			}

		} else if (field == Field.Bcc) {

			if (ConfigProperties.getStringProperty("browser").equals("edge")) {
				locator = Locators.zBccField_Edge;
			} else {
				locator = Locators.zBccField;
			}

			// Make sure the BCC field is showing
			if (!zBccIsActive()) {
				this.zToolbarPressButton(Button.B_SHOWBCC);
			}

		} else {
			throw new HarnessException("Unsupported field: " + field);
		}

		if (locator == null) {
			throw new HarnessException("locator was null for field " + field);
		}

		if (!this.sIsElementPresent(locator))
			throw new HarnessException("Field is not present field=" + field + " locator=" + locator);

		this.sFocus(locator);
		this.sClickAt(locator, "");
		this.zWaitForBusyOverlay();
		clearField(locator);
		sType(locator, value);

		this.zWaitForBusyOverlay();
		waitForAutocomplete();

		return (zAutocompleteListGetEntries());

	}

	protected void waitForAutocomplete() throws HarnessException {
		String locator = "css=div[class='acWaiting'][style*='display: none;']";
		for (int i = 0; i < 30; i++) {
			if (this.sIsElementPresent(locator))
				return;
			SleepUtil.sleep(1000);
		}
		throw new HarnessException("autocomplete never completed");
	}

	protected AutocompleteEntry parseAutocompleteEntry(String itemLocator) throws HarnessException {
		logger.info(myPageName() + " parseAutocompleteEntry()");

		String locator = null;

		// Get the icon
		locator = itemLocator + " td.AutocompleteMatchIcon div@class";
		String image = this.sGetAttribute(locator);

		// Get the address
		locator = itemLocator + " td + td";
		String address = this.sGetText(locator);

		AutocompleteEntry entry = new AutocompleteEntry(Icon.getIconFromImage(image), address, false, itemLocator);

		return (entry);
	}

	public List<AutocompleteEntry> zAutocompleteListGetEntries() throws HarnessException {
		logger.info(myPageName() + " zAutocompleteListGetEntries()");

		List<AutocompleteEntry> items = new ArrayList<AutocompleteEntry>();

		String containerLocator = "css=div[id^='zac__COMPOSE-'][style*='display: block;']";

		if (!this.zWaitForElementPresent(containerLocator, "5000")) {
			// Autocomplete is not visible, return an empty list.
			return (items);
		}

		String rowsLocator = containerLocator + " tr[id*='_acRow_']";
		int count = this.sGetCssCount(rowsLocator);
		for (int i = 0; i < count; i++) {

			items.add(parseAutocompleteEntry(containerLocator + " tr[id$='_acRow_" + i + "']"));

		}

		return (items);
	}

	public void zAutocompleteSelectItem(AutocompleteEntry entry) throws HarnessException {
		logger.info(myPageName() + " zAutocompleteSelectItem(" + entry + ")");

		// Click on the address
		this.sClick(entry.getLocator() + " td + td");
		this.zWaitForBusyOverlay();

	}

	public void zAutocompleteForgetItem(AutocompleteEntry entry) throws HarnessException {
		logger.info(myPageName() + " zAutocompleteForgetItem(" + entry + ")");

		// Mouse over the entry
		zAutocompleteMouseOverItem(entry);

		// Click on the address
		this.sClick(entry.getLocator() + " div[id*='_acForgetText_']");
		this.zWaitForBusyOverlay();

	}

	public void zAutocompleteMouseOverItem(AutocompleteEntry entry) throws HarnessException {
		logger.info(myPageName() + " zAutocompleteMouseOverItem(" + entry + ")");

		// Click on the address
		this.sMouseOver(entry.getLocator() + " td + td");
		this.zWaitForBusyOverlay();

	}

	public String zGetFieldValue(Field field) throws HarnessException {
		String locator = null;
		String fieldValue = null;

		if (field == Field.Cc) {
			locator = Locators.CcField;

		} else if (field == Field.To) {
			locator = Locators.ToField;

		} else if (field == Field.Body) {

			SleepUtil.sleepMedium();

			locator = "css=div[id^='zv__COMPOSE'] iframe[id$='_body_ifr']";
			if (this.sIsElementPresent(locator) && this.zIsVisiblePerPosition(locator, 0, 0)) {

				logger.info("FormMailNew.zFillField: Html Compose");

				this.sSelectFrame("css=iframe[id$='_body_ifr']");
				locator = "css=html body";

				if (!this.sIsElementPresent(locator))
					throw new HarnessException("Unable to locate compose body");
			}

		} else {
			throw new HarnessException("no logic defined for field " + field);
		}

		// Make sure something was set
		if (locator == null)
			throw new HarnessException("locator was null for field = " + field);

		// Make sure the field is present
		if (!this.sIsElementPresent(locator))
			throw new HarnessException("Unable to find the field = " + field + " using locator = " + locator);

		// Get the field value
		fieldValue = this.sGetText(locator);
		logger.info("DisplayMail.ZGetFieldValue(" + field + ") = " + fieldValue);

		if (field.equals(Field.Body)) {
			this.sSelectFrame("relative=top");
		}

		return (fieldValue);
	}

	public String zGetHtmltBodyText() throws HarnessException {
		String BodyHtmlText;
		try {

			this.sSelectFrame("css=iframe[id$='ZmHtmlEditor1_body_ifr']");
			BodyHtmlText = sGetText("css=body[id='tinymce']");

		} finally {
			this.sSelectFrame("relative=top");
		}

		return BodyHtmlText;

	}

	public String zGetPlainBodyText() throws HarnessException {
		String locator = "css=textarea[id*='ZmHtmlEditor'][class='ZmHtmlEditorTextArea']";
		String BodyPlainText = sGetValue(locator);
		return BodyPlainText;

	}

	public boolean zVerifyDisabledSendLater() throws HarnessException {

		String pulldownLocator = "css=div[id$='__SEND_MENU'] td[id$='__SEND_MENU_dropdown']>div";
		this.sRightClick(pulldownLocator);
		this.zWaitForBusyOverlay();

		if (this.sIsElementPresent(Locators.zSendLaterDisabled))
			return true;

		return false;
	}

	public String zGetSignatueImageSrc() throws HarnessException {
		SleepUtil.sleepMedium();
		String imageSrc;
		try {

			this.sSelectFrame("css=iframe[id$='ZmHtmlEditor1_body_ifr']");
			imageSrc = sGetAttribute("css=div[data-marker='__QUOTED_TEXT__'] img@src");

		} finally {

			this.sSelectFrame("relative=top");
		}
		return imageSrc;
	}
	
	// Expand the DL and select the member
	public void zSelectMemberFromDL(String dl, String member) throws HarnessException {
		String dlLocator = Locators.zBubbleExpand + "[onclick*='" + dl + "']";
		String dlMemberLcoator = "css=div[id^='ZmAutocompleteListView'] table tr[id^='ZmAutocompleteListView'] td:contains('" + member + "')";
		this.sClick(dlLocator);
		this.sMouseMoveAt(Locators.zSendIconBtn, "0,0");
		SleepUtil.sleepLong();
		this.sClick(dlMemberLcoator);
	}
	
	public boolean zVerifyPrefixInHtmlMailBody() throws HarnessException {
		boolean status = false;
		SleepUtil.sleepMedium();
		try {
			this.sSelectFrame("css=iframe[id='ZmHtmlEditor1_body_ifr']");
			status = this.sIsElementPresent(Locators.zPrefixInHeaderReplyCompose) && this.sIsElementPresent(Locators.zPrefixInQuotedTextReplyCompose);
		} finally {
			this.sSelectFrame("relative=top");
		}		
		return status;
	}
}