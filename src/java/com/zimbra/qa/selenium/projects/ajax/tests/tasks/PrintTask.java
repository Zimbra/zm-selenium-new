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
package com.zimbra.qa.selenium.projects.ajax.tests.tasks;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.items.TaskItem;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.ui.Shortcut;
import com.zimbra.qa.selenium.framework.util.ConfigProperties;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCore;
import com.zimbra.qa.selenium.projects.ajax.pages.SeparateWindowPrintPreview;

public class PrintTask extends AjaxCore {

	@SuppressWarnings("serial")
	public PrintTask() {
		logger.info("New "+ PrintTask.class.getCanonicalName());

		super.startingPage = app.zPageTasks;

		super.startingAccountPreferences = new HashMap<String , String>() {{
			put("zimbraPrefShowSelectionCheckbox", "TRUE");
			put("zimbraPrefTasksReadingPaneLocation", "bottom");
		}};
	}


	@Test (description = "Print Task using RightClick -> Print and Verify Contents in Print view",
			groups = { "functional", "L3" })

	public void PrintTask_01() throws HarnessException {

		FolderItem taskFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);

		// Create a basic task
		String subject = "task"+ ConfigProperties.getUniqueString();
		String bodyText = "text" + ConfigProperties.getUniqueString();

		app.zGetActiveAccount().soapSend(
				"<CreateTaskRequest xmlns='urn:zimbraMail'>"
			+		"<m >"
			+			"<inv>"
			+				"<comp name='"+ subject +"'>"
			+					"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>"
			+				"</comp>"
			+			"</inv>"
			+			"<su>"+ subject +"</su>"
			+			"<mp ct='text/plain'>"
			+				"<content>"+ bodyText +"</content>"
			+			"</mp>"
			+		"</m>"
			+	"</CreateTaskRequest>");

		// Refresh the tasks view
		app.zPageTasks.zToolbarPressButton(Button.B_REFRESH);
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);

		// Select the item
		app.zPageTasks.zListItem(Action.A_MAIL_CHECKBOX, subject);

		SeparateWindowPrintPreview window = null;
		String windowTitle = "Zimbra";

		try {

			// Right click the item, select print
			window = (SeparateWindowPrintPreview)app.zPageTasks.zListItem(Action.A_RIGHTCLICK, Button.O_PRINT_MENU, subject);
			SleepUtil.sleepLong();

			// Press esc from keyboard to discard the print preview dialog.
			window.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ESCAPE);

			window.zSetWindowTitle(windowTitle);
			ZAssert.assertTrue(window.zIsWindowOpen(windowTitle),"Verify the window is opened and switch to it");

			// Verify content in Print view.
			String printContent = window.sGetBodyContent(windowTitle, "css=div[class='ZhCallListPrintView']");
			ZAssert.assertStringContains(printContent, subject, "Verify subject in Print view");
			ZAssert.assertStringContains(printContent, bodyText, "Verify content in Print view");

		} finally {
			window.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ESCAPE);		// to close the print preview dialog in case it is still open
			app.zPageMain.zCloseWindow(window, windowTitle, app);
		}
	}


	@Test (description = "Print Task using shortcut 'p' and verify its content from GUI",
			groups = { "functional", "L3" })

	public void PrintTask_02() throws HarnessException {

		FolderItem taskFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);

		// Create a basic task
		String subject = "task"+ ConfigProperties.getUniqueString();
		String bodyText = "text" + ConfigProperties.getUniqueString();

		app.zGetActiveAccount().soapSend(
				"<CreateTaskRequest xmlns='urn:zimbraMail'>" +
				"<m >" +
				"<inv>" +
				"<comp name='"+ subject +"'>" +
				"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
				"</comp>" +
				"</inv>" +
				"<su>"+ subject +"</su>" +
				"<mp ct='text/plain'>" +
				"<content>"+ bodyText +"</content>" +
				"</mp>" +
				"</m>" +
		"</CreateTaskRequest>");

		TaskItem task = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject);
		ZAssert.assertNotNull(task, "Verify the task is created");

		// Refresh the tasks view
		app.zPageTasks.zToolbarPressButton(Button.B_REFRESH);
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);

		// Select the item
		app.zPageTasks.zListItem(Action.A_MAIL_CHECKBOX, subject);

		SeparateWindowPrintPreview window = null;
		String windowTitle = "Zimbra";

		try {

			// Press keyboard shortcut p
			window = (SeparateWindowPrintPreview)app.zPageTasks.zKeyboardShortcut(Shortcut.S_PRINTTASK);
			SleepUtil.sleepLong();

			// Press esc from keyboard to discard the print preview dialog.
			window.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ESCAPE);

			window.zSetWindowTitle(windowTitle);
			ZAssert.assertTrue(window.zIsWindowOpen(windowTitle),"Verify the window is opened and switch to it");

			// Verify content in Print view.
			String printContent = window.sGetBodyContent(windowTitle, "css=div[class='ZhCallListPrintView']");
			ZAssert.assertStringContains(printContent, subject, "Verify subject in Print view");
			ZAssert.assertStringContains(printContent, bodyText, "Verify content in Print view");

		} finally {
			window.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ESCAPE);
			app.zPageMain.zCloseWindow(window, windowTitle, app);
		}
	}


	@Test (description = "Print multiple tasks using Print-> Print TaskFolder and  and verify its content from GUI",
			groups = { "functional", "L3"}	)

	public void PrintTask_03() throws HarnessException {

		FolderItem taskFolder = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.Tasks);
		// Create a basic task to delete
		String subject1 = "task1"+ ConfigProperties.getUniqueString();
		String subject2 = "task2"+ ConfigProperties.getUniqueString();
		String subject3 = "task3"+ ConfigProperties.getUniqueString();

		app.zGetActiveAccount().soapSend(
				"<CreateTaskRequest xmlns='urn:zimbraMail'>" +
					"<m >" +
			        	"<inv>" +
			        		"<comp name='"+ subject1 +"'>" +
			        			"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
			        		"</comp>" +
			        	"</inv>" +
			        	"<su>"+ subject1 +"</su>" +
			        	"<mp ct='text/plain'>" +
			        		"<content>content"+ ConfigProperties.getUniqueString() +"</content>" +
			        	"</mp>" +
					"</m>" +
				"</CreateTaskRequest>");

		app.zGetActiveAccount().soapSend(
				"<CreateTaskRequest xmlns='urn:zimbraMail'>" +
					"<m >" +
			        	"<inv>" +
			        		"<comp name='"+ subject2 +"'>" +
			        			"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
			        		"</comp>" +
			        	"</inv>" +
			        	"<su>"+ subject2 +"</su>" +
			        	"<mp ct='text/plain'>" +
			        		"<content>content"+ ConfigProperties.getUniqueString() +"</content>" +
			        	"</mp>" +
					"</m>" +
				"</CreateTaskRequest>");

		app.zGetActiveAccount().soapSend(
				"<CreateTaskRequest xmlns='urn:zimbraMail'>" +
					"<m >" +
			        	"<inv>" +
			        		"<comp name='"+ subject3 +"'>" +
			        			"<or a='"+ app.zGetActiveAccount().EmailAddress +"'/>" +
			        		"</comp>" +
			        	"</inv>" +
			        	"<su>"+ subject3 +"</su>" +
			        	"<mp ct='text/plain'>" +
			        		"<content>content"+ ConfigProperties.getUniqueString() +"</content>" +
			        	"</mp>" +
					"</m>" +
				"</CreateTaskRequest>");

		TaskItem task1 = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject1);
		TaskItem task2 = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject2);
		TaskItem task3 = TaskItem.importFromSOAP(app.zGetActiveAccount(), subject3);

		ZAssert.assertNotNull(task1, "Verify the task1 is created");
		ZAssert.assertNotNull(task2, "Verify the task2 is created");
		ZAssert.assertNotNull(task3, "Verify the task3 is created");

		// Refresh the tasks view
		app.zPageTasks.zToolbarPressButton(Button.B_REFRESH);
		app.zTreeTasks.zTreeItem(Action.A_LEFTCLICK, taskFolder);

		SeparateWindowPrintPreview window = null;
		String windowTitle = "Zimbra";

		try {

			// Pull down Print button and select Print Task folder.
			window = (SeparateWindowPrintPreview)app.zPageTasks.zToolbarPressPulldown(Button.B_PRINT, Button.O_PRINT_TASKFOLDER);
			SleepUtil.sleepLong();

			// Press esc from keyboard to discard the print preview dialog.
			window.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ESCAPE);

			window.zSetWindowTitle(windowTitle);
			ZAssert.assertTrue(window.zIsWindowOpen(windowTitle),"Verify the window is opened and switch to it");

			// Verify subjects in Print view.
			String printContent = window.sGetBodyContent(windowTitle, "css=div[class='ZhCallListPrintView']");
			ZAssert.assertStringContains(printContent, subject1, "Verify subject in Print view");
			ZAssert.assertStringContains(printContent, subject2, "Verify subject2 in Print view");
			ZAssert.assertStringContains(printContent, subject3, "Verify subject2 in Print view");

		} finally {
			window.zKeyboard.zTypeKeyEvent(KeyEvent.VK_ESCAPE);
			app.zPageMain.zCloseWindow(window, windowTitle, app);
		}
	}
}