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
package com.zimbra.qa.selenium.projects.ajax.tests.main;

import org.testng.annotations.*;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.*;
import com.zimbra.qa.selenium.projects.ajax.pages.*;

public class ShortcutDialog extends AjaxCore {

	public ShortcutDialog() {
		logger.info("New " + ShortcutDialog.class.getCanonicalName());
		super.startingPage = app.zPageMail;
	}


	@Test (description = "Open the 'Shortcut' dialog >> Verify contents and Close Link",
		groups = { "functional", "L3" })

	public void Shortcutdialog_Close_01() throws HarnessException {

		// Click the Account -> Shortcut menu
		DialogInformational dialog = (DialogInformational) app.zPageMain.zToolbarPressPulldown(Button.B_ACCOUNT, Button.O_SHORTCUT);

		// VERIFICATION
		ZAssert.assertTrue(dialog.zIsActive(), "Verify the Shortcut dialog opens");
		ZAssert.assertStringContains(dialog.sGetShortcutsDialogBody("css=div[class='ZmShortcutsPanel']"), "Zimbra Keyboard Shortcuts", "Verify Zimbra Keyboard Shortcuts in New window view");
		ZAssert.assertStringContains(dialog.sGetShortcutsDialogBody("css=div[class='ZmShortcutsPanel']"), "Conversation List View", "Verify Conversation List View Shortcuts in New window view");
		ZAssert.assertStringContains(dialog.sGetShortcutsDialogBody("css=div[class='ZmShortcutsPanel']"), "Previous button", "Verify Previous button Shortcut in New window view");

		dialog.zPressButton(Button.B_CLOSE);
	}


	@Test (description = "Open the 'Shortcut' dialog >> Verify in New Window link and contents",
		groups = { "functional", "L3" })

	public void Shortcutdialog_NewWindow_02() throws HarnessException {

		// Click the Account -> Shortcut menu
		DialogInformational dialog = (DialogInformational) app.zPageMain.zToolbarPressPulldown(Button.B_ACCOUNT, Button.O_SHORTCUT);

		SeparateWindow window = null;
		String windowTitle = "Zimbra";

		try {
			window = (SeparateWindow) dialog.zPressButton(Button.B_NEWWINDOW);
			window.zWaitForActive();
			window.sSelectWindow(windowTitle);

			ZAssert.assertStringContains(dialog.sGetShortcutsDialogBody("css=div[class='ZmShortcutsWindow']"), "Zimbra Keyboard Shortcuts", "Verify Zimbra Keyboard Shortcuts in New window view");
			ZAssert.assertStringContains(dialog.sGetShortcutsDialogBody("css=div[class='ZmShortcutsWindow']"), "Conversation List View", "Verify Conversation List View Shortcuts in New window view");
			ZAssert.assertStringContains(dialog.sGetShortcutsDialogBody("css=div[class='ZmShortcutsWindow']"), "Previous button", "Verify Previous button Shortcut in New window view");
			dialog.sClickAt("css=div[class='ZmShortcutsWindow'] span[class='link']:contains('Close')", "");

		} finally {

			if (window != null) {
				app.zPageMain.sSelectWindow(null);
				window.sSelectWindow(null);
				window = null;
			}
		}
	}
}