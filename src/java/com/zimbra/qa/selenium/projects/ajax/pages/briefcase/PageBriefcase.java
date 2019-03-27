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
package com.zimbra.qa.selenium.projects.ajax.pages.briefcase;

import java.util.EnumMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import com.zimbra.qa.selenium.framework.items.DocumentItem;
import com.zimbra.qa.selenium.framework.items.FileItem;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.IItem;
import com.zimbra.qa.selenium.framework.ui.AbsApplication;
import com.zimbra.qa.selenium.framework.ui.AbsPage;
import com.zimbra.qa.selenium.framework.ui.AbsTab;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.ui.Shortcut;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.RestUtil;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.ZimbraAccount;
import com.zimbra.qa.selenium.framework.util.ConfigProperties;
import com.zimbra.qa.selenium.projects.ajax.pages.AjaxPages;
import com.zimbra.qa.selenium.projects.ajax.pages.DialogMove;
import com.zimbra.qa.selenium.projects.ajax.pages.DialogTag;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.DialogCreateFolder;
import com.zimbra.qa.selenium.projects.ajax.pages.mail.FormMailNew;

public class PageBriefcase extends AbsTab {

	WebElement we = null;

	
	public static final String pageTitle = "Email: Briefcase";

	public static class Locators {

		public static final String zBriefcaseZimletsPane = "ztih__main_Briefcase__ZIMLET_textCell";
		public static final String zBriefcaseTagsPane = "ztih__main_Briefcase__TAG_textCell";
		public static final String zBriefcaseFolder = "zti__main_Briefcase__16_textCell";

		public static final Locators zNewBriefcaseOverviewPaneIcon = new Locators(
				"id=ztih__main_Briefcase__BRIEFCASE_textCell");
		public static final Locators briefcaseListView = new Locators(
				"css=div[id^='zl__BDLV'][class^='DwtListView-Rows']");
		public static final Locators zBriefcaseFolderIcon = new Locators("css=div[id='zti__main_Briefcase__16']");
		public static final Locators zTrashFolder = new Locators("id=zti__main_Briefcase__3_textCell");
		public static final Locators zBriefcaseAppIconBtn = new Locators("id=zb__App__Briefcase_left_icon");
		public static final Locators zNewMenuIconBtn = new Locators("id=zb__BCD__NEW_FILE_left_icon");
		public static final Locators zNewMenuLeftIconBtn = new Locators("css=div[id=zb__NEW_MENU]");
		public static final Locators zNewMenuArrowBtn = new Locators(
				"css=div[id=zb__NEW_MENU] div[class^=ImgSelectPullDownArrow]");
		public static final Locators zUploadFileIconBtn = new Locators("id=zb__BDLV-main__NEW_FILE_left_icon");
		public static final Locators zUploadFileTitleBtn = new Locators(
				"css=div[id=zb__BDLV-main__NEW_FILE]>table>tbody>tr>td[id=zb__BDLV-main__NEW_FILE_title]");
		public static final Locators zEditFileIconBtn = new Locators("id=zb__BDLV-main__EDIT_FILE_left_icon");
		public static final Locators zEditFileBtn = new Locators("css=div[id=zb__BDLV-main__EDIT_FILE]");
		public static final Locators zEditButtonDisabled = new Locators(
				"css=div[id='ztb__BDLV-main'] div[id='zb__BDLV-main__EDIT_FILE']");
		public static final Locators zOpenFileInSeparateWindowIconBtn = new Locators(
				"id=zb__BDLV-main__NEW_BRIEFCASE_WIN_left_icon");
		public static final Locators zDeleteIconBtn = new Locators("id=zb__BDLV-main__DELETE_left_icon");
		public static final Locators zDeleteBtn = new Locators("css=div[id=zb__BDLV-main__DELETE]");
		public static final Locators zMoveIconBtn = new Locators("id=zb__BDLV-main__MOVE_left_icon");
		public static final Locators zMoveBtn = new Locators("id=zb__BDLV-main__MOVE");
		public static final Locators zTagItemIconBtn = new Locators("id=zb__BCD__TAG_MENU_left_icon");
		public static final Locators zViewIconBtn = new Locators("id=zb__BCD__VIEW_MENU_left_icon");
		public static final Locators zSendBtnIconBtn = new Locators("id=zb__BCD__SEND_FILE_left_icon");
		public static final Locators zNewDocumentIconBtn = new Locators("id=zb__BCD__NEW_DOC_left_icon");
		public static final Locators zNewSpreadsheetIconBtn = new Locators("id=zb__BCD__NEW_SPREADSHEET_left_icon");
		public static final Locators zNewPresentationIconBtn = new Locators("id=zb__BCD__NEW_PRESENTATION_left_icon");
		public static final Locators zRenameInput = new Locators("css=div[class^=RenameInput]>input");
		public static final Locators zFileBodyField = new Locators("css=html>body");
		public static final Locators zHeaderCheckBox = new Locators("css=div[id=zlhi__BDLV-main__se]");
		public static final Locators zListItemLockIcon = new Locators(
				"css=div[id^=zlif__BDLV-main__][id$=__loid][class=ImgPadLock]");
		public static final Locators zCloseIconBtn = new Locators("css=td[id^=zb__MSG][id$=CLOSE_title]");
		public static final Locators zAttachmentText = new Locators("css=div[class=attBubbleHolder] span");

		// Menus
		public static final String zOpenMenu = "css=div[id='zm__Briefcase'] td[id='zmi__Briefcase__OPEN_FILE_title']";
		public static final String zEditMenu = "css=div[id='zm__Briefcase'] td[id='zmi__Briefcase__EDIT_FILE_title']";
		public static final Locators zEditMenuDisabled = new Locators(
				"css=div[id='zm__Briefcase'] div[id='zmi__Briefcase__EDIT_FILE']");
		public static final String zSendLinkMenu = "css=div[id='zm__Briefcase'] td[id='zmi__Briefcase__SEND_FILE_title']";
		public static final String zSendAsAttachmentsMenu = "css=div[id='zm__Briefcase'] td[id='zmi__Briefcase__SEND_FILE_AS_ATT_title']";
		public static final String zRestoreAsCurrentVersion = "css=div[id='zm__Briefcase'] td[id='zmi__Briefcase__RESTORE_VERSION_title']";
		public static final String zCheckInFileMenu = "css=div[id='zm__Briefcase'] td[id='zmi__Briefcase__CHECKIN_title']";
		public static final String zDiscardCheckoutMenu = "css=div[id='zm__Briefcase'] td[id='zmi__Briefcase__DISCARD_CHECKOUT_title']";
		public static final String zMoveMenu = "css=div[id='zm__Briefcase'] td[id='zmi__Briefcase__MOVE_title']";
		public static final String zRenameMenu = "css=div[id='zm__Briefcase'] td[id='zmi__Briefcase__RENAME_FILE_title']";
		public static final String zDeleteMenu = "css=div[id='zm__Briefcase'] td[id='zmi__Briefcase__DELETE_title']";
		public static final String zTagMenu = "css=div[id='zm__Briefcase'] td[id='zmi__Briefcase__TAG_MENU_title']";

		public final String locator;

		public Locators(String locator) {
			this.locator = locator;
		}
	}

	public static class Response {
		public static enum ResponsePart {
			BODY, HEADERS
		}

		public static enum Format {
			HTML("html"), TEXT("text"), TGZ("tgz"), XML("xml"), JSON("json"), NATIVE("native"), RAW("raw");

			private String fmt;

			private Format(String fmt) {
				this.fmt = fmt;
			}

			public String getFormat() {
				return fmt;
			}
		}
	}

	public PageBriefcase(AbsApplication application) {
		super(application);
		logger.info("new " + PageBriefcase.class.getCanonicalName());
	}

	@Override
	public boolean zIsActive() throws HarnessException {

		String locator = Locators.zBriefcaseFolderIcon.locator;

		boolean loaded = zWaitForElementPresent(locator, "1000");

		if (!loaded)
			return (loaded);
		boolean active = this.zIsVisiblePerPosition(locator, 0, 0);
		return (active);

	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public void zNavigateTo() throws HarnessException {

		if (zIsActive()) {
			logger.info(myPageName() + " is already loaded");
			return;
		}

		((AjaxPages) MyApplication).zPageMain.zCheckAppLoaded(Locators.zBriefcaseZimletsPane);
	}

	public AbsPage zToolbarPressButton(Button button, IItem item) throws HarnessException {

		logger.info(myPageName() + " zToolbarPressButton(" + button + ")");

		tracer.trace("Press the " + button + " button");

		if (button == null)
			throw new HarnessException("Button cannot be null!");

		String locator = null;
		AbsPage page = null;

		if (button == Button.B_NEW) {
			// Check if the button is disabled
			locator = Locators.zNewMenuLeftIconBtn.locator;

			// Click on New Document icon
			this.sClickAt(locator, "0,0");
			zWaitForBusyOverlay();
			SleepUtil.sleepLong();

			page = new DocumentBriefcaseNew(this.MyApplication);
			page.zIsActive();
			return page;

		} else if (button == Button.B_UPLOAD_FILE) {
			// Check if the button is disabled
			locator = Locators.zUploadFileTitleBtn.locator;

			page = new DialogUploadFile(MyApplication, this);
		} else if (button == Button.B_EDIT_FILE) {

			locator = Locators.zEditFileBtn.locator;

			if (item instanceof DocumentItem) {
				page = new DocumentBriefcaseEdit(MyApplication, (DocumentItem) item);
			} else {
				page = null;
			}
		} else if (button == Button.B_DELETE) {

			locator = Locators.zDeleteBtn.locator;

			// Check if the button is disabled
			String attrs = sGetAttribute(locator + "@class");

			if (attrs.contains("ZDisabled")) {
				throw new HarnessException(button + " is disabled " + attrs);
			}

			page = new DialogConfirm(DialogConfirm.Confirmation.DELETE, MyApplication, this);
		} else if (button == Button.B_OPEN_IN_SEPARATE_WINDOW) {
			// Check if the button is disabled
			locator = Locators.zOpenFileInSeparateWindowIconBtn.locator;

			String attrs = sGetAttribute("css=td[" + locator + "]>div@class");

			if (attrs.contains("ZDisabledImage")) {
				throw new HarnessException(button + " is disabled " + attrs);
			}

			page = new DocumentBriefcaseOpen(this.MyApplication);

		} else if (button == Button.B_MOVE) {
			// Check if the button is disabled
			locator = Locators.zMoveIconBtn.locator;

			String attrs = sGetAttribute("css=td[" + locator + "]>div@class");

			if (attrs.contains("ZDisabledImage")) {
				throw new HarnessException(button + " is disabled " + attrs);
			}

			page = new DialogMove(MyApplication, this);

		} else if (button == Button.B_PRINT) {
			throw new HarnessException("implement Print dialog");

		} else if (button == Button.B_TAG) {
			throw new HarnessException("implement Print dialog");

		} else if (button == Button.B_LISTVIEW) {
			throw new HarnessException("implement Print dialog");

		} else {
			throw new HarnessException("no logic defined for button " + button);
		}

		if (locator == null) {
			throw new HarnessException("locator was null for button " + button);
		}

		if (!this.sIsElementPresent(locator))
			throw new HarnessException("Button is not present locator=" + locator + " button=" + button);

		this.sClickAt(locator, "0,0");
		SleepUtil.sleepMedium();

		zWaitForBusyOverlay();

		return (page);
	}

	public AbsPage zToolbarPressPulldown(Button pulldown, Button option, IItem item) throws HarnessException {

		logger.info(myPageName() + " zToolbarPressPulldown(" + pulldown + ", " + option + ")");

		tracer.trace("Click pulldown " + pulldown + " then " + option);

		if (pulldown == null)
			throw new HarnessException("Pulldown cannot be null!");

		if (option == null)
			throw new HarnessException("Option cannot be null!");

		String pulldownLocator = null;
		String optionLocator = null;
		AbsPage page = null;

		if (pulldown == Button.B_NEW) {
			pulldownLocator = Locators.zNewMenuArrowBtn.locator;
			if (option == Button.O_NEW_BRIEFCASE) {

				optionLocator = "css=div#zb__NEW_MENU_NEW_BRIEFCASE";
				page = new DialogCreateBriefcaseFolder(this.MyApplication,
						((AjaxPages) MyApplication).zPageBriefcase);

			} else if (option == Button.O_NEW_DOCUMENT) {
				if (ConfigProperties.zimbraGetVersionString().contains("7.1."))
					optionLocator = "css=tr[id=POPUP_NEW_DOC]";
				else
					optionLocator = "css=div#zb__NEW_MENU_NEW_DOC";

				page = new DocumentBriefcaseNew(this.MyApplication);

			} else if (option == Button.O_NEW_FOLDER) {
				throw new HarnessException("implement me!");

			} else if (option == Button.O_NEW_TAG) {
				if (ConfigProperties.zimbraGetVersionString().contains("7.1."))
					optionLocator = "css=tr[id=POPUP_NEW_TAG]>td[id$=_title]:contains(Tag)";
				else
					optionLocator = "css=div#zb__NEW_MENU_NEW_TAG";

				page = new DialogTag(this.MyApplication, this);

			} else {
				throw new HarnessException("no logic defined for pulldown/option " + pulldown + "/" + option);
			}

		} else if (pulldown == Button.B_TAG) {
			if (option == Button.O_TAG_NEWTAG) {

				pulldownLocator = "css=td[id=zb__BDLV-main__TAG_MENU_dropdown]>div[class='ImgSelectPullDownArrow']";
				optionLocator = "css=div[id='zb__BDLV-main__TAG_MENU|MENU'] div[id=briefcase_newtag]";

				page = new DialogTag(this.MyApplication, this);

			} else if (option == Button.O_TAG_REMOVETAG) {
				// Using General shortcuts: Type "u" shortcut
				// zKeyboard.zTypeCharacters(Shortcut.S_MAIL_REMOVETAG.getKeys());

				pulldownLocator = "css=td[id$='__TAG_MENU_dropdown']>div[class='ImgSelectPullDownArrow']";

				optionLocator = "css=div[id^=briefcase_removetag__]";

				page = null;

			} else {
				throw new HarnessException("no logic defined for pulldown/option " + pulldown + "/" + option);
			}

		} else if (pulldown == Button.B_SEND) {
			if (option == Button.O_SEND_AS_ATTACHMENT) {

				pulldownLocator = "css=td[id$='__SEND_FILE_MENU_dropdown']>div[class='ImgSelectPullDownArrow']";

				optionLocator = "css=td[id$='_title']:contains('Send as attachment')";

				page = new FormMailNew(this.MyApplication);

			} else if (option == Button.O_SEND_LINK) {

				pulldownLocator = "css=td[id$='__SEND_FILE_MENU_dropdown']>div[class='ImgSelectPullDownArrow']";

				optionLocator = "css=td[id$='_title']:contains('Send link')";

				page = new DialogConfirm(DialogConfirm.Confirmation.SENDLINK, this.MyApplication, this);

			} else {
				throw new HarnessException("no logic defined for pulldown/option " + pulldown + "/" + option);
			}

		} else if (pulldown == Button.B_ACTIONS) {

			pulldownLocator = "css=td[id=zb__BDLV-main__ACTIONS_MENU_dropdown]>div[class='ImgSelectPullDownArrow']";

			if (option == Button.B_LAUNCH_IN_SEPARATE_WINDOW) {

				optionLocator = "css=div[id='zmi__BDLV-main__DETACH_WIN']";

				page = new DocumentBriefcaseOpen(this.MyApplication, item);

			} else if (option == Button.O_SEND_AS_ATTACHMENT) {

				optionLocator = "css=div[id*=SEND_FILE_AS_ATT] td[id*=SEND_FILE_AS_ATT_title]:contains('Send as attachment')";

				page = new FormMailNew(this.MyApplication);

			} else if (option == Button.O_SEND_LINK) {

				optionLocator = "css=div[id*=SEND_FILE] td[id*=SEND_FILE_title]:contains('Send link')";

				page = new DialogConfirm(DialogConfirm.Confirmation.SENDLINK, this.MyApplication, this);

			} else {
				throw new HarnessException("no logic defined for pulldown/option " + pulldown + "/" + option);
			}

		} else if (pulldown == Button.B_MOVE) {

			if (option == Button.O_NEW_FOLDER) {

				// Check if we are CLV or MV
				if (this.zIsVisiblePerPosition("css=div#ztb__CLV-main", 0, 0)) {
					pulldownLocator = "css=td#zb__CLV-main__MOVE_MENU_dropdown>div";
				} else {
					pulldownLocator = "css=td#zb__TV-main__MOVE_MENU_dropdown>div";
				}
				optionLocator = "css=div[class='DwtFolderChooser'] div[id$='_newButtonDivId'] td[id$='_title']";
				page = new DialogCreateFolder(this.MyApplication, this);

			} else {
				throw new HarnessException("no logic defined for B_MOVE and " + option);
			}

			if (!this.sIsElementPresent(pulldownLocator)) {
				throw new HarnessException(pulldownLocator + " not present!");
			}

			// 8.0 change ... need sClickAt()
			// this.sClick(pulldownLocator);
			this.sClickAt(pulldownLocator, "0,0");

			zWaitForBusyOverlay();

			if (!this.sIsElementPresent(optionLocator)) {
				throw new HarnessException(optionLocator + " not present!");
			}

			this.sClick(optionLocator);
			zWaitForBusyOverlay();
			page.zWaitForActive();
			SleepUtil.sleepSmall();

			return (page);

		} else {
			throw new HarnessException("no logic defined for pulldown/option " + pulldown + "/" + option);
		}

		if (pulldownLocator != null) {

			if (!this.zWaitForElementPresent(pulldownLocator, "3000")) {
				throw new HarnessException("Button " + pulldown + " option " + option + " pulldownLocator "
						+ pulldownLocator + " not present!");
			}

			if (zIsBrowserMatch(BrowserMasks.BrowserMaskIE)) {
				if (pulldown == Button.B_NEW) {
					pulldownLocator = "css=td[id=zb__NEW_MENU_dropdown]>div[class^=ImgSelectPullDownArrow]";
					sClick(pulldownLocator);
				} else {
					sClick(pulldownLocator);
				}
			} else {
				sClickAt(pulldownLocator, "0,0");
			}

			zWaitForBusyOverlay();

			if (optionLocator != null) {

				if (!this.zWaitForElementVisible(optionLocator, true, "3000")) {
					throw new HarnessException("Button " + pulldown + " option " + option + " optionLocator "
							+ optionLocator + " not present!");
				}

				// work around for bug 59722
				if (optionLocator.contains("Dc")) {
					for (int i = 0; i < 6; i++) {
						zKeyEvent(optionLocator, "40", "keydown");
					}
					zKeyEvent(optionLocator, "13", "keydown");
				} else if (optionLocator.contains("Tg")) {
					for (int i = 0; i < 8; i++) {
						zKeyEvent(optionLocator, "40", "keydown");
					}
					zKeyEvent(optionLocator, "13", "keydown");
				} else
					this.sClickAt(optionLocator, "0,0");

				zWaitForBusyOverlay();
			}

			if (page != null) {
				if (option == Button.O_SEND_AS_ATTACHMENT) {
					String locator = "css=div[class='attBubbleHolder']";
					if (!this.zWaitForElementPresent(locator, "3000")) {
						throw new HarnessException(locator + " not present");
					}
				} else if (option == Button.O_TAG_NEWTAG) {
					String locator = "id=CreateTagDialog";
					if (!this.zWaitForElementPresent(locator, "3000")) {
						throw new HarnessException(locator + " not present");
					}
				} else
					page.zWaitForActive();
			}
		}

		SleepUtil.sleepSmall();
		return (page);
	}

	public AbsPage zToolbarPressPulldown(Button pulldown, String option) throws HarnessException {

		logger.info(myPageName() + " zToolbarPressButtonWithPulldown(" + pulldown + ", " + option + ")");

		tracer.trace("Click pulldown " + pulldown + " then " + option);

		if (pulldown == null)
			throw new HarnessException("Pulldown cannot be null!");

		if (option == null)
			throw new HarnessException("Option cannot be null!");

		//
		String pulldownLocator = null;
		String optionLocator = null;
		AbsPage page = null;

		if (pulldown == Button.B_TAG) {
			if (option.length() > 0) {

				pulldownLocator = "css=td[id$='__TAG_MENU_dropdown']>div[class='ImgSelectPullDownArrow']";

				optionLocator = "//td[contains(@id,'_title') and contains(text(),'" + option + "')]";

				page = null;

			} else {
				throw new HarnessException("no logic defined for pulldown/option " + pulldown + "/" + option);
			}
		} else {
			throw new HarnessException("no logic defined for pulldown " + pulldown);
		}

		if (pulldownLocator != null) {

			if (!this.sIsElementPresent(pulldownLocator)) {
				throw new HarnessException("Button " + pulldown + " option " + option + " pulldownLocator "
						+ pulldownLocator + " not present!");
			}

			this.sClickAt(pulldownLocator, "0,0");

			zWaitForBusyOverlay();

			if (optionLocator != null) {

				if (!this.sIsElementPresent(optionLocator)) {
					throw new HarnessException("Button " + pulldown + " option " + option + " optionLocator "
							+ optionLocator + " not present!");
				}

				this.sClickAt(optionLocator, "0,0");

				zWaitForBusyOverlay();
			}
		}

		return (page);
	}

	public AbsPage zToolbarPressPulldown(Button pulldown, Object dynamic) throws HarnessException {

		logger.info(myPageName() + " zToolbarPressButtonWithPulldown(" + pulldown + ", " + dynamic + ")");

		tracer.trace("Click pulldown " + pulldown + " then " + dynamic);

		if (pulldown == null)
			throw new HarnessException("Pulldown cannot be null!");

		if (dynamic == null)
			throw new HarnessException("Option cannot be null!");

		String pulldownLocator = null;
		String optionLocator = null;
		AbsPage page = null;

		if (pulldown == Button.B_MOVE) {

			if (!(dynamic instanceof FolderItem))
				throw new HarnessException("if pulldown = " + Button.B_MOVE + ", then dynamic must be FolderItem");

			FolderItem folder = (FolderItem) dynamic;

			pulldownLocator = "css=td#zb__BDLV-main__MOVE_MENU_dropdown>div";
			optionLocator = "css=td[id=zti__ZmFolderChooser_BriefcaseBDLV-main__" + folder.getId() + "_textCell]";

			page = null;
		} else {
			throw new HarnessException("no logic defined for pulldown/dynamic " + pulldown + "/" + dynamic);
		}

		if (pulldownLocator != null) {

			if (!this.sIsElementPresent(pulldownLocator)) {
				throw new HarnessException(
						"Button " + pulldown + " pulldownLocator " + pulldownLocator + " not present!");
			}
			this.sClickAt(pulldownLocator, "");

			zWaitForBusyOverlay();

			SleepUtil.sleepVerySmall();

			if (optionLocator != null) {

				if (!this.sIsElementPresent(optionLocator)) {
					throw new HarnessException(
							" dynamic " + dynamic + " optionLocator " + optionLocator + " not present!");
				}
				this.sClickAt(optionLocator, "");

				zWaitForBusyOverlay();
			}
		}

		return (page);
	}

	public void zHeader(Action action) throws HarnessException {
		logger.info(myPageName() + " zHeader(" + action + ")");

		tracer.trace(action + " on briefcase header");

		// Validate the arguments
		if (action == null)
			throw new HarnessException("action cannot be null!");

		String locator = null;

		// Based on the action specified, take the appropriate action(s)
		if (action == Action.A_BRIEFCASE_HEADER_CHECKBOX) {

			locator = Locators.zHeaderCheckBox.locator;

			// Left-Click on the header
			this.sClickAt(locator, "0,0");
		} else {
			throw new HarnessException("implement me!  action = " + action);
		}
		zWaitForBusyOverlay();
	}

	public AbsPage zListItem(Action action, IItem item) throws HarnessException {

		SleepUtil.sleepMedium();

		// Validate the arguments
		if ((action == null) || (item == null)) {
			throw new HarnessException("Must define an action and item");
		}

		if (!((item instanceof FolderItem) || (item instanceof FileItem) || (item instanceof DocumentItem))) {
			throw new HarnessException("Not supported item: " + item.getClass());
		}

		String itemName = item.getName();

		tracer.trace(action + " on briefcase item = " + itemName);

		logger.info(myPageName() + " zListItem(" + action + ", " + itemName + ")");

		AbsPage page = null;
		String listLocator = "//div[contains(@id,'zl__BDLV')]";
		String itemLocator = listLocator + "//div[contains(@id,'zli__BDLV') and contains(@class,'Row')]";
		String itemNameLocator = listLocator + "//*[contains(@id,'zlif__BDLV') and contains(text(),'" + itemName
				+ "')]";

		if (!this.sIsElementPresent(itemLocator))
			throw new HarnessException("List View Rows is not present " + itemLocator);

		if (action == Action.A_LEFTCLICK) {
			zWaitForElementPresent(itemNameLocator);

			this.sClickAt(itemNameLocator, "0,0");

			// page = new DocumentPreview(MyApplication);

		} else if (action == Action.A_DOUBLECLICK) {
			zWaitForElementPresent(itemNameLocator);

			// double-click on the item
			this.sDoubleClick(itemNameLocator);

			if (item instanceof DocumentItem) {
				page = new DocumentBriefcaseOpen(MyApplication, (DocumentItem) item);
			} else
				page = null;

		} else if (action == Action.A_BRIEFCASE_CHECKBOX) {
			zWaitForElementPresent(itemNameLocator);

			String checkBoxLocator = "";
			String itemIndexLocator = "";
			int count = sGetXpathCount(itemLocator);

			for (int i = 1; i <= count; i++) {
				itemIndexLocator = itemLocator + "[position()=" + i + "]";
				if (sIsElementPresent(itemIndexLocator + "//*[contains(text(),'" + itemName + "')]")) {
					checkBoxLocator = itemIndexLocator
							+ "//div[contains(@id,'zlif__BDLV')]/div[contains(@class,'ImgCheckbox')]";
					break;
				}
			}

			if (!this.sIsElementPresent(checkBoxLocator)) {
				throw new HarnessException("Checkbox locator is not present " + checkBoxLocator);
			}

			String image = this.sGetAttribute(checkBoxLocator + "@class");

			if (!image.equals("ImgCheckboxChecked")) {
				// Left-Click on the Check box field
				this.sClickAt(checkBoxLocator, "0,0");
			} else {
				logger.error("Trying to mark check box, but it was already enabled");
			}
			page = null;

		} else if (action == Action.A_RIGHTCLICK) {
			zWaitForElementPresent(itemNameLocator);

			// Right-Click on the item
			this.sRightClickAt(itemNameLocator, "0,0");

		} else {
			throw new HarnessException("implement me!  action = " + action);
		}

		zWaitForBusyOverlay();
		SleepUtil.sleepSmall();

		if (page != null) {
			page.zWaitForActive();
		}
		return page;
	}

	public AbsPage zListItem(Action action, Button option, IItem item) throws HarnessException {

		if (action == null)
			throw new HarnessException("action cannot be null");
		if (option == null)
			throw new HarnessException("button cannot be null");
		if (item == null)
			throw new HarnessException("Item cannot be null or blank");

		String subject = item.getName();

		tracer.trace(action + " then " + option + " on briefcase item = " + subject);

		logger.info(myPageName() + " zListItem(" + action + ", " + option + ", " + subject + ")");

		AbsPage page = null;
		String listLocator = Locators.briefcaseListView.locator;
		// String rowLocator;
		String itemlocator = null;

		if (!this.sIsElementPresent(listLocator))
			throw new HarnessException("List View Rows is not present " + listLocator);

		itemlocator = "//div[contains(@id,'zl__BDLV')]" + "//*[contains(@id,'zlif__BDLV') and contains(text(),'"
				+ subject + "')]";

		if (action == Action.A_RIGHTCLICK) {

			zWaitForElementPresent(itemlocator);

			// Right-Click on the item
			this.sRightClickAt(itemlocator, "0,0");
			SleepUtil.sleepSmall();

			String optionLocator = null;

			if (option == Button.B_RENAME) {

				optionLocator = Locators.zRenameMenu;

				page = null;

			} else if (option == Button.O_EDIT) {

				optionLocator = Locators.zEditMenu;

				if (item instanceof DocumentItem) {
					page = new DocumentBriefcaseEdit(MyApplication, (DocumentItem) item);
				} else
					page = null;
			} else if (option == Button.O_RESTORE_AS_CURRENT_VERSION) {

				optionLocator = Locators.zRestoreAsCurrentVersion;

				page = null;

			} else if (option == Button.O_OPEN) {

				optionLocator = Locators.zOpenMenu;

				if (item instanceof DocumentItem) {
					page = new DocumentBriefcaseOpen(MyApplication, (DocumentItem) item);
				} else
					page = null;

			} else if (option == Button.O_SEND_LINK) {

				optionLocator = Locators.zSendLinkMenu;

				page = new DialogConfirm(DialogConfirm.Confirmation.SENDLINK, this.MyApplication, this);

			} else if (option == Button.O_SEND_AS_ATTACHMENT) {

				optionLocator = Locators.zSendAsAttachmentsMenu;

				page = new FormMailNew(this.MyApplication);

			} else if (option == Button.O_DELETE) {

				optionLocator = Locators.zDeleteMenu;

				page = new DialogConfirm(DialogConfirm.Confirmation.DELETE, MyApplication, this);

			} else if (option == Button.O_MOVE) {

				optionLocator = Locators.zMoveMenu;

				page = new DialogMove(MyApplication, this);
			} else if (option == Button.O_TAG_FILE) {

				optionLocator = Locators.zTagMenu;

				page = new DialogMove(MyApplication, this);
			} else if (option == Button.O_CHECK_IN_FILE) {

				optionLocator = Locators.zCheckInFileMenu;

				page = new DialogCheckInFile(MyApplication, this);
			} else if (option == Button.O_DISCARD_CHECK_OUT) {

				optionLocator = Locators.zDiscardCheckoutMenu;

				page = null;
			} else {
				throw new HarnessException("implement action: " + action + " option:" + option);
			}

			if (!this.sIsElementPresent(optionLocator)) {
				throw new HarnessException(optionLocator + " not present!");
			}

			// click on the option
			this.sClickAt(optionLocator, "0,0");
			SleepUtil.sleepSmall();

			this.zWaitForBusyOverlay();

		} else {
			throw new HarnessException("implement me!  action = " + action);
		}

		if (page != null) {
			if (option == Button.O_SEND_AS_ATTACHMENT) {
				String locator = "css=div[class='attBubbleHolder']";
				if (!this.zWaitForElementPresent(locator, "3000")) {
					throw new HarnessException(locator + " not present");
				}
				return page;
			} else
				page.zWaitForActive();
		}

		return (page);
	}

	public AbsPage zListItem(Action action, Button option, String subOption, IItem item) throws HarnessException {

		if (action == null)
			throw new HarnessException("action cannot be null");
		if (option == null)
			throw new HarnessException("button cannot be null");
		if (subOption == null)
			throw new HarnessException("subOption button cannot be null");
		if (item == null)
			throw new HarnessException("Item cannot be null or blank");

		String rowItem = item.getName();

		tracer.trace(action + " then " + option + " then " + subOption + " on briefcase item = " + rowItem);

		logger.info(myPageName() + " zListItem(" + action + ", " + option + ", " + subOption + ", " + rowItem + ")");

		AbsPage page = null;

		String listLocator = Locators.briefcaseListView.locator;

		String itemlocator = null;

		if (!this.sIsElementPresent(listLocator))
			throw new HarnessException("List View Rows is not present " + listLocator);

		// itemlocator = listLocator + " div:contains(" + rowItem + ")";
		itemlocator = "//div[contains(@id,'zl__BDLV')]" + "//*[contains(@id,'zlif__BDLV') and contains(text(),'"
				+ rowItem + "')]";

		if (action == Action.A_RIGHTCLICK) {

			zWaitForElementPresent(itemlocator);

			// Right-Click on the item
			this.sRightClickAt(itemlocator, "0,0");

			// Now the ContextMenu is opened
			// Click on the specified option

			String optionLocator = null;

			if (option == Button.O_TAG_FILE) {

				optionLocator = "css=div[id=zm__Briefcase] tr[id*=Briefcase__TAG_MENU]>td[id*=TAG_MENU_title]";

			} else {
				throw new HarnessException("implement action: " + action + " option:" + option);
			}

			this.sMouseOver(optionLocator);

			String subOptionLocator = "//div[@id='zmi__Briefcase__TAG_MENU|MENU']"
					+ "//*[contains(@class,'ZWidgetTitle') and contains(text(),'" + subOption + "')]";

			this.zWaitForBusyOverlay();
			zWaitForElementVisible(subOptionLocator, true, "3000");

			// click on the sub option
			this.sClickAt(subOptionLocator, "0,0");

			this.zWaitForBusyOverlay();

			page = null;

		} else {
			throw new HarnessException("implement me!  action = " + action);
		}

		return (page);
	}

	@Override
	public AbsPage zKeyboardShortcut(Shortcut shortcut) throws HarnessException {

		if (shortcut == null)
			throw new HarnessException("Shortcut cannot be null");

		tracer.trace("Using the keyboard, press the " + shortcut.getKeys() + " keyboard shortcut");

		AbsPage page = null;

		String keyCode = "";

		if ((shortcut == Shortcut.S_NEWITEM) || (shortcut == Shortcut.S_NEWDOCUMENT)) {

			// "New Document" shortcut result in a new document page opening
			page = new DocumentBriefcaseNew(this.MyApplication);

			keyCode = "78,68";
		} else if (shortcut == Shortcut.S_DELETE) {

			// "Delete Document" shortcut leads to Confirmation Dialog opening
			page = new DialogConfirm(DialogConfirm.Confirmation.DELETE, MyApplication, this);

			keyCode = "46";
		} else if (shortcut == Shortcut.S_BACKSPACE) {

			// "Delete Document" shortcut leads to Confirmation Dialog opening
			page = new DialogConfirm(DialogConfirm.Confirmation.DELETE, MyApplication, this);

			keyCode = "8";
		} else if (shortcut == Shortcut.S_NEWTAG) {

			// "NEW TAG" shortcut opens "Create New Tag" dialog
			page = new DialogTag(MyApplication, this);

			keyCode = "78,84";
		} else if (shortcut == Shortcut.S_NEWFOLDER) {

			// "NEW Folder" shortcut opens "Create New Folder" dialog
			// due to the bug #63029 it opens dialog with Mail tree view
			page = new DialogCreateBriefcaseFolder(MyApplication, this);

			keyCode = "78,70";
		} else if (shortcut == Shortcut.S_MOVE) {

			// "Move" shortcut opens "Choose Folder" dialog
			page = new DialogMove(MyApplication, this);

			keyCode = "77";
		} else {
			throw new HarnessException("implement shortcut: " + shortcut);
		}

		// zKeyboard.zTypeCharacters(shortcut.getKeys());

		zKeyDown(keyCode);

		this.zWaitForBusyOverlay();

		// If a page is specified, wait for it to become active
		if (page != null) {
			page.zWaitForActive(); // throws a HarnessException if never active
		}
		return (page);
	}

	public void rename(String text) throws HarnessException {

		logger.info("renaming to: " + text);

		String locator = Locators.zRenameInput.locator;
		WebElement we = getElement(locator);
		clearField(locator);
		SleepUtil.sleepVerySmall();
		we.sendKeys(text);
		we.sendKeys(Keys.RETURN);
		SleepUtil.sleepMedium();
	}

	public void typeKey(String locator, String keycode, String event) throws HarnessException {
		sFocus(locator);

		sGetEval("if (document.createEventObject) {var x=selenium.browserbot.findElementOrNull('" + locator
				+ "');var evObj = x.document.createEventObject();" + "evObj.keyCode=" + keycode
				+ "; evObj.repeat = false; x.focus(); x.fireEvent(\"on" + event + "\",evObj);}"
				+ "else {if (window.KeyEvent) {var evObj = document.createEvent('KeyEvents');" + "evObj.initKeyEvent( '"
				+ event + "', true, true, window, false, false, false, false," + keycode + ", 0 );} "
				+ "else {var evObj = document.createEvent('HTMLEvents');" + "evObj.initEvent( '" + event
				+ "', true, true, window, 1 ); evObj.keyCode=" + keycode
				+ ";} var x = selenium.browserbot.findElementOrNull('" + locator
				+ "'); x.blur(); x.focus(); x.dispatchEvent(evObj);}");
	}

	public void fireEvent(String locator, String eventName) throws HarnessException {
		logger.info("firing Event: " + eventName + " on " + locator);
		super.sFireEvent(locator, eventName);
	}

	public void isOpenDocLoaded(DocumentItem docItem) throws HarnessException {
		zWaitForWindow(docItem.getName());

		zSelectWindow(docItem.getName());

		zWaitForElementPresent("css=td[class='ZhAppContent'] div:contains('" + docItem.getDocText() + "')");
	}

	public void isOpenFileLoaded(String windowName, String text) throws HarnessException {
		zWaitForWindow(windowName);

		zSelectWindow(windowName);

		zWaitForElementPresent(Locators.zFileBodyField.locator + ":contains('" + text + "')");
	}

	public boolean isPresentInListView(String itemName) throws HarnessException {
		String itemLocator = "//div[contains(@id,'zl__BDLV')]" + "//*[contains(@id,'zlif__BDLV') and contains(text(),'"
				+ itemName + "')]";

		return sIsElementPresent(itemLocator);
	}

	public boolean waitForPresentInListView(String itemName) throws HarnessException {

		String itemLocator = "//div[contains(@id,'zl__BDLV')]" + "//*[contains(@id,'zlif__BDLV') and contains(text(),'"
				+ itemName + "')]";

		zWaitForElementPresent(itemLocator);
		return true;
	}

	public boolean isOptionDisabled(Locators name) throws HarnessException {
		return sIsElementPresent(name.locator + "[class='ZMenuItem ZWidget   ZHasText   ZDisabled ZHasLeftIcon']");
	}

	public boolean isToolbarButtonDisabled(Locators name) throws HarnessException {
		return sIsElementPresent(name.locator + "[class='ZToolbarButton ZWidget ZHasText ZDisabled']");
	}

	public boolean isLockIconPresent(IItem item) throws HarnessException {
		boolean present = false;
		String itemName = item.getName();

		String listLocator = Locators.briefcaseListView.locator;
		String itemLocator = listLocator + " div[id^='zli__BDLV'][class^='Row']";
		String itemNameLocator = "//div[contains(@id,'zl__BDLV')]"
				+ "//*[contains(@id,'zlif__BDLV') and contains(text(),'" + itemName + "')]";

		zWaitForElementPresent(itemNameLocator);

		String lockIconLocator = "";

		int count = sGetCssCount(itemLocator);

		for (int i = 1; i <= count; i++) {
			if (sIsElementPresent(itemLocator + ":nth-child(" + i + "):contains(" + itemName + ")")) {
				lockIconLocator = itemLocator + ":nth-child(" + i
						+ ")>table>tbody>tr>td>div[id^=zlif__BDLV][id$=__loid]>[class^=Img]";
				break;
			}
		}

		if (!this.sIsElementPresent(lockIconLocator)) {
			logger.info("Lock icon locator is not present " + lockIconLocator);
		} else {
			String image = this.sGetAttribute(lockIconLocator + "@class");

			if (image.equalsIgnoreCase("ImgPadLock"))
				present = true;
		}

		return present;
	}

	public boolean waitForDeletedFromListView(String itemName) throws HarnessException {

		String itemLocator = "//div[contains(@id,'zl__BDLV')]" + "//*[contains(@id,'zlif__BDLV') and contains(text(),'"
				+ itemName + "')]";

		zWaitForElementDeleted(itemLocator);
		return true;
	}

	public String getItemNameFromListView(String itemName) throws HarnessException {

		String itemLocator = "//div[contains(@id,'zl__BDLV')]" + "//*[contains(@id,'zlif__BDLV') and contains(text(),'"
				+ itemName + "')]";

		return sGetText(itemLocator);
	}

	public boolean isEditDocLoaded(DocumentItem docItem) throws HarnessException {
		zWaitForWindow(docItem.getName());
		zSelectWindow(docItem.getName());
		zWaitForElementPresent("css=iframe[id$='_body_ifr']");
		zWaitForIframeText("css=iframe[id$='_body_ifr']", docItem.getDocText());

		return true;
	}

	public void deleteFileByName(String docName) throws HarnessException {
		ZimbraAccount account = MyApplication.zGetActiveAccount();
		account.soapSend("<SearchRequest xmlns='urn:zimbraMail' types='document'>" + "<query>" + docName + "</query>"
				+ "</SearchRequest>");
		String id = account.soapSelectValue("//mail:doc", "id");
		deleteFileById(id);
	}

	public void deleteFileById(String docId) throws HarnessException {
		ZimbraAccount account = MyApplication.zGetActiveAccount();
		account.soapSend("<ItemActionRequest xmlns='urn:zimbraMail'>" + "<action id='" + docId + "' op='trash'/>"
				+ "</ItemActionRequest>");
	}

	public EnumMap<Response.ResponsePart, String> displayFile(String filename, Map<String, String> params)
			throws HarnessException {
		ZimbraAccount account = MyApplication.zGetActiveAccount();

		RestUtil util = new RestUtil();

		util.setAuthentication(account);

		util.setPath("/home/~/Briefcase/" + filename);

		for (Map.Entry<String, String> query : params.entrySet()) {
			util.setQueryParameter(query.getKey(), query.getValue());
		}

		if (util.doGet() != HttpStatus.SC_OK)
			throw new HarnessException("Unable to open " + filename + " in " + util.getLastURI());

		final String responseHeaders = util.getLastResponseHeaders();
		final String responseBody = util.getLastResponseBody();

		return new EnumMap<Response.ResponsePart, String>(Response.ResponsePart.class) {
			private static final long serialVersionUID = 1L;
			{
				put(Response.ResponsePart.HEADERS, responseHeaders);
				put(Response.ResponsePart.BODY, responseBody);
			}
		};
	}

	public String openUrl(String url) throws HarnessException {

		this.sOpen(url);

		return url;
	}

	public String getLocation() {
		String url = null;
		try {
			url = sGetLocation();
		} catch (Exception ex) {
			logger.info(ex);
		}
		return url;
	}

	public String openUrl(String path, Map<String, String> params) throws HarnessException {
		ZimbraAccount account = MyApplication.zGetActiveAccount();

		RestUtil util = new RestUtil();

		util.setAuthentication(account);

		if (null != path && !path.isEmpty())
			util.setPath("/" + path + "/");
		else
			util.setPath("/");

		if (null != params && !params.isEmpty()) {
			for (Map.Entry<String, String> query : params.entrySet()) {
				util.setQueryParameter(query.getKey(), query.getValue());
			}
		}

		if (util.doGet() != HttpStatus.SC_OK)
			throw new HarnessException("Unable to open " + util.getLastURI());

		String url = util.getLastURI().toString();

		if (url.endsWith("?"))
			url = url.substring(0, url.length() - 1);

		this.sOpen(url);

		return url;
	}

	public void closeWindow() throws HarnessException {
		tracer.trace("Close the separate window");

		this.sClose();
	}

	@Override
	public void zSelectWindow(String windowID) throws HarnessException {
		logger.info("zSelectWindow(" + windowID + ")");
		super.zSelectWindow(windowID);
	}

	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButton(" + button + ")");

		tracer.trace("Press the " + button + " button");
		
		String locator = null;
		AbsPage page = null;

		if (button == null)
			throw new HarnessException("Button cannot be null!");

		if (button == Button.B_REFRESH) {

			return (((AjaxPages) this.MyApplication).zPageMain.zToolbarPressButton(Button.B_REFRESH));

		} else if (button == Button.B_UPLOAD_FILE) {
			
			locator = Locators.zUploadFileTitleBtn.locator;

			page = new DialogUploadFile(MyApplication, this);
		}
		
		this.sClickAt(locator, "0,0");
		SleepUtil.sleepMedium();

		zWaitForBusyOverlay();

		return (page);

	}

	@Override
	public AbsPage zListItem(Action action, String name) throws HarnessException {

		if ((action == null) || (name == null)) {
			throw new HarnessException("Must define an action and item");
		}

		tracer.trace(action + " on briefcase item = " + name);

		logger.info(myPageName() + " zListItem(" + action + ", " + name + ")");

		AbsPage page = null;
		String listLocator = "//div[contains(@id,'zl__BDLV')]";
		String itemLocator = listLocator + "//div[contains(@id,'zli__BDLV') and contains(@class,'Row')]";
		String itemNameLocator = listLocator + "//*[contains(@id,'zlif__BDLV') and contains(text(),'" + name + "')]";

		if (!this.sIsElementPresent(itemLocator))
			throw new HarnessException("List View Rows is not present " + itemLocator);

		if (action == Action.A_LEFTCLICK) {
			zWaitForElementPresent(itemNameLocator);
			this.sClickAt(itemNameLocator, "0,0");

		} else if (action == Action.A_BRIEFCASE_CHECKBOX) {
			zWaitForElementPresent(itemNameLocator);

			String checkBoxLocator = "";
			String itemIndexLocator = "";
			int count = sGetXpathCount(itemLocator);

			for (int i = 1; i <= count; i++) {
				itemIndexLocator = itemLocator + "[position()=" + i + "]";
				if (sIsElementPresent(itemIndexLocator + "//*[contains(text(),'" + name + "')]")) {
					checkBoxLocator = itemIndexLocator
							+ "//div[contains(@id,'zlif__BDLV')]/div[contains(@class,'ImgCheckbox')]";
					break;
				}
			}

			if (!this.sIsElementPresent(checkBoxLocator)) {
				throw new HarnessException("Checkbox locator is not present " + checkBoxLocator);
			}

			String image = this.sGetAttribute(checkBoxLocator + "@class");

			if (!image.equals("ImgCheckboxChecked")) {
				this.sClickAt(checkBoxLocator, "0,0");
			} else {
				logger.error("Trying to mark check box, but it was already enabled");
			}
			page = null;

		}
		return (page);
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String subject) throws HarnessException {
		if (action == null)
			throw new HarnessException("action cannot be null");
		if (option == null)
			throw new HarnessException("button cannot be null");
		if (subject == null)
			throw new HarnessException("subject cannot be null or blank");

		tracer.trace(action + " then " + option + " on briefcase item = " + subject);

		logger.info(myPageName() + " zListItem(" + action + ", " + option + ", " + subject + ")");

		AbsPage page = null;

		if (action == Action.A_RIGHTCLICK) {

			zWaitForElementPresent(subject);

			this.sRightClickAt(subject, "0,0");
			String optionLocator = null;

			if (option == Button.B_RENAME) {

				optionLocator = Locators.zRenameMenu;

				page = null;
			}

			else if (option == Button.O_DELETE) {

				optionLocator = Locators.zDeleteMenu;

				page = new DialogConfirm(DialogConfirm.Confirmation.DELETE, MyApplication, this);

			} else if (option == Button.O_RESTORE_AS_CURRENT_VERSION) {

				optionLocator = Locators.zRestoreAsCurrentVersion;

				page = null;

			}
			this.sClick(optionLocator);
		}

		zWaitForBusyOverlay();
		SleepUtil.sleepSmall();

		return (page);

	}

	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption, String item) throws HarnessException {
		throw new HarnessException("implement me! : action=" + action + " subOption=" + subOption + "item=" + item);
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option) throws HarnessException {
		throw new HarnessException("implement me! : pulldown=" + pulldown + " option=" + option);
	}

	public Boolean zVerifyImageFilePreviewContents(String locator) throws HarnessException {

		try {
			webDriver().switchTo().frame(0);
			we = webDriver().findElement(By.cssSelector(locator.replace("css=", "")));
			if (we.isDisplayed()) {
				return true;
			} else {
				return false;
			}

		} finally {
			webDriver().switchTo().defaultContent();
		}

	}

	public Boolean zVerifyTextFilePreviewContents(String fileContent) throws HarnessException {

		try {
			webDriver().switchTo().frame(0);
			String bodyContent = webDriver().findElement(By.tagName("body")).getText();
			boolean result = false;
			if (!ConfigProperties.getStringProperty("browser").contains("edge")) {
				if (fileContent.equals(bodyContent)) {
					result = true;
				} else {
					result = false;
				}
			} else {
				if (bodyContent.contains(fileContent)) {
					result = true;
				} else {
					result = false;
				}
			}
			return result;

		} finally {
			webDriver().switchTo().defaultContent();
		}
	}

	public Boolean zVerifyPdfFilePreviewContents(String fileContent) throws HarnessException {

		if (ConfigProperties.getStringProperty("browser").contains("firefox")) {

			String[] pdfElements = { "//body//div[contains(text(), '" + fileContent + "')]", "//button[@id='zoomIn']",
					"//button[@id='previous']", "//a[@id='viewBookmark']" };

			try {
				webDriver().switchTo().frame(0);

				for (int i = 0; i <= pdfElements.length - 1; i++) {
					System.out.println("Verify " + pdfElements[i] + " element present in file preview");
					we = webDriver().findElement(By.xpath(pdfElements[i]));
					if (!we.isDisplayed()) {
						throw new HarnessException("Could't find " + pdfElements[i] + " element in file preview");
					}
				}

			} finally {
				webDriver().switchTo().defaultContent();
			}

		}
		return true;
	}
}