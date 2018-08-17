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
package com.zimbra.qa.selenium.projects.ajax.pages.tasks;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriverException;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.items.TaskItem;
import com.zimbra.qa.selenium.framework.ui.AbsApplication;
import com.zimbra.qa.selenium.framework.ui.AbsPage;
import com.zimbra.qa.selenium.framework.ui.AbsTab;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.ui.Shortcut;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.projects.ajax.pages.AjaxPages;
import com.zimbra.qa.selenium.projects.ajax.pages.ContextMenu;
import com.zimbra.qa.selenium.projects.ajax.pages.DialogAssistant;
import com.zimbra.qa.selenium.projects.ajax.pages.DialogMove;
import com.zimbra.qa.selenium.projects.ajax.pages.DialogTag;
import com.zimbra.qa.selenium.projects.ajax.pages.DialogWarning;
import com.zimbra.qa.selenium.projects.ajax.pages.SeparateWindow;
import com.zimbra.qa.selenium.projects.ajax.pages.SeparateWindowPrintPreview;
import com.zimbra.qa.selenium.projects.ajax.pages.ZimbraDOM;

public class PageTasks extends AbsTab {

	public static class Locators {

		public static final String zTasksZimletsPane = "ztih__main_Tasks__ZIMLET_textCell";
		public static final String zTasksTagsPane = "ztih__main_Tasks__TAG_textCell";
		public static final String zTasksFolder = "zti__main_Tasks__15_textCell";

		public static final String zl__TKL__rowsID = "zl__TKL-main__rows";
		public static final String _upComingTaskListHdrID = "_upComingTaskListHdr";
		public static final String zli__TKL__ = "zli__TKL-";

		public static final String zb__TKE1__SAVE_left_icon = "zb__TKE1__SAVE_left_icon";
		public static final String taskListView = "css=div[id='zl__TKL-main__rows'][class='DwtListView-Rows']";
		public static final String zTasksTab = "zb__App__Tasks";
		public static final String zNewTask = "css=div[id='zb__NEW_MENU'] td[id='zb__NEW_MENU_title']:contains('New Task')";
		public static final String zNewTaskDropDown = "css=div[id='zb__NEW_MENU'] td[id='zb__NEW_MENU_dropdown']>div";
		public static final String zNewTagMenuItem = "css=div[id='zb__NEW_MENU_NEW_TAG'] tr[id^='POPUP_'] td[id$='_title']";
		public static final String zMarkAsCompleted = "css=div[id^='ztb__TKL'] tr[id^='ztb__TKL'] td[id$='_title']:contains('Mark as Completed')";
		public static String zMarkAsCompletedId = null;
		public static final String zNewTaskMenuItem = "css=div[id='zb__NEW_MENU_NEW_TASK'] td[id$='_title']";
		public static final String zNewTaskFolderMenuItem = "css=div[id='zb__NEW_MENU_NEW_TASK_FOLDER'] tr[id^='POPUP_'] td[id$='_title']";
		public static final String zDeleteTaskMenuItem = "css=div[id='zm__Tasks'] tr[id='POPUP_zmi__Tasks__DELETE']";
		public static final String zMoveTaskMenuItem = "css=div[id='zm__Tasks'] tr[id='POPUP_zmi__Tasks__MOVE']";
		public static final String zNewTaskListMenuItem = "css=div[id$='NEWFOLDER']";
		public static String zMoveTaskDropDownId = null;
		public static final String zEditTaskMenuItem = "css=div[id='zm__Tasks'] tr[id='POPUP_zmi__Tasks__EDIT']";
		public static String zFilterByTaskDropDownId = null;
		public static final String zFilterByTaskDropDown = "css=tr[id='ztb__TKL-main_items'] div[id='zb__TKL-main__VIEW_MENU'] td[id='zb__TKL-main__VIEW_MENU_dropdown']>div";
		public static final String zToDoListTaskMenuItem = "css=div[id='TKVT'] tr[id='POPUP_TKVT']";
		public static final String zShowOrigTaskMenuItem = "css=div[id='zm__Tasks'] tr[id='POPUP_zmi__Tasks__SHOW_ORIG'] td[id$='_title']";
		public static final String zPrintTaskMenuItem = "css=div[id='zm__Tasks'] tr[id='POPUP_zmi__Tasks__PRINT_TASK'] td[id$='_title']";
		public static String zPrintTaskDropDownId = null;
		public static final String zTaskBodyField = "css=div[class='ZmTaskEditView'] div[id$='_notes'] textarea[id$='_body']";
		public static final String zPrintTaskFolder = "css=tr[id='POPUP_PRINT_TASKFOLDER'] td[id$='_title']";
		public static final String zCloseButton = "css=div[id^='ztb__TKV']  tr[id^='ztb__TKV'] td[id$='_title']:contains('Close')";
		public static final String zAttachmentInputBox = "css=input[name='__calAttUpload__']";
		public static final String zAttachmentsLabel = "css=div[id='zv__TKL-main'] div[class='ZmMailMsgView'] td[class='LabelColName']:contains('Attachments')";
		public static final String zEditButton = "css=div[id^='ztb__TKL'] tr[id^='ztb__TKL'] td[id$='_title']:contains('Edit')";
		public static final String zEditAttachmentCheckbox = "css=div[class='ZmTaskEditView'] tr[id$='_attachment_container'] input[type='checkbox']";
		public static final String zCheckboxenable = "css=div[class='ImgCheckboxChecked']";
		public static final String zUntagTaskBubble = "css=div[id='zv__zv__TKL-main__TKV'] tr[id='zv__TKL-main_MSG_tagRow'] span[class='addrBubble TagBubble'] span[class='ImgBubbleDelete']";
		public static final String zTaskSubject = "css=div.MsgHeaderTable div.SubjectCol";
		public static final String zTaskStartDate = "css=div.MsgHeaderTable table[id$='_hdrTable'] tr[id$='_sd']";
		public static final String zTaskDueDate = "css=div.MsgHeaderTable table[id$='_hdrTable'] tr[id$='_ed']";
		public static final String zNewTaskBanner = "css=div[id$='_newTaskBannerId'] div.newTaskBanner";
	}

	public PageTasks(AbsApplication application) {
		super(application);

		logger.info("new " + PageTasks.class.getCanonicalName());

	}

	@Override
	public boolean zIsActive() throws HarnessException {

		if (!((AjaxPages) MyApplication).zPageMain.zIsActive()) {
			((AjaxPages) MyApplication).zPageMain.zNavigateTo();
		}

		String id = "zb__App__Tasks";
		String rowLocator = "css=div[id='" + Locators.zl__TKL__rowsID + "']>div";

		boolean loaded = this.sIsElementPresent(rowLocator);
		if (!loaded)
			return (false);

		return (this.sIsElementPresent("css=div[id='" + id + "'][class*=ZSelected]"));

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

		((AjaxPages) MyApplication).zPageMain.zCheckAppLoaded(Locators.zTasksZimletsPane);
	}

	public boolean isPresent(String itemName) throws HarnessException {
		String itemLocator = Locators.taskListView + " td[width*='auto']:contains(" + itemName + ")";

		zWaitForElementPresent(itemLocator);
		return true;
	}

	@Override
	public AbsPage zListItem(Action action, String subject) throws HarnessException {
		logger.info(myPageName() + " zListItem(" + action + ", " + subject + ")");

		tracer.trace(action + " on subject = " + subject);

		if (action == null)
			throw new HarnessException("action cannot be null");

		if ((subject == null) || (subject.trim().length() == 0))
			throw new HarnessException("subject cannot be null or empty");

		AbsPage page = null;
		String itemLocator = null;

		String rowLocator = "css=div[id='" + Locators.zl__TKL__rowsID + "']>div";
		int count = this.sGetCssCount(rowLocator);
		logger.debug(myPageName() + " zListItem: number of rows: " + count);

		if (count < 1)
			throw new HarnessException("No tasks in the list!");

		itemLocator = rowLocator + ":first-child";
		for (int i = 1; i < count; i++) {

			itemLocator = itemLocator + " + div ";
			if (!this.sIsElementPresent(itemLocator))
				throw new HarnessException("Item Locator not present: " + itemLocator);

			String id;
			try {
				id = this.sGetAttribute(itemLocator + "@id");
				if (id == null)
					throw new HarnessException("id was null: " + itemLocator + "@id");
				if (!id.startsWith(Locators.zli__TKL__))
					continue;

			} catch (WebDriverException e) {
				logger.warn("No ID on item: " + itemLocator);
				continue;
			}

			String subjectLocator = "css=div[id='" + id + "'] td[id$='_su']";
			if (!this.sIsElementPresent(subjectLocator))
				throw new HarnessException("Subject Locator not present: " + subjectLocator);

			String itemSubject = this.sGetText(subjectLocator);
			if ((itemSubject == null) || (itemSubject.trim().length() == 0)) {
				logger.debug("found empty task subject");
				continue;
			}
			if (!itemSubject.equals(subject)) {
				continue;
			}
			if (itemSubject.equals(subject)) {
				// Found it
				break;
			}

			itemLocator = null;
		}

		if (itemLocator == null) {
			throw new HarnessException("Unable to locate item with subject(" + subject + ")");
		}

		if (action == Action.A_LEFTCLICK) {

			this.sClick(itemLocator);

			this.zWaitForBusyOverlay();

			// Return the displayed mail page object
			page = new DisplayTask(MyApplication);

		} else if (action == Action.A_CTRLSELECT) {

			throw new HarnessException("implement me!  action = " + action);

		} else if (action == Action.A_SHIFTSELECT) {

			throw new HarnessException("implement me!  action = " + action);

		} else if (action == Action.A_RIGHTCLICK) {

			// Right-Click on the item
			this.sRightClick(itemLocator);

			// Return the displayed mail page object
			page = new ContextMenu(MyApplication);

		} else if (action == Action.A_MAIL_CHECKBOX) {

			String selectlocator = itemLocator + " div[id$='__se']";
			if (!this.sIsElementPresent(selectlocator))
				throw new HarnessException("Checkbox locator is not present " + selectlocator);

			if (this.sIsElementPresent(selectlocator + "[class*=ImgCheckboxChecked]"))
				throw new HarnessException("Trying to check box, but it was already enabled");

			this.sClick(selectlocator);
			this.zWaitForBusyOverlay();

			page = null;

		} else if (action == Action.A_DOUBLECLICK) {

			// double-click on the item
			this.sDoubleClick(itemLocator);
			page = new DisplayTask(this.MyApplication);

		} else if (action == Action.A_MAIL_UNCHECKBOX) {

			String selectlocator = itemLocator + " div[id$='__se']";
			if (!this.sIsElementPresent(selectlocator))
				throw new HarnessException("Checkbox locator is not present " + selectlocator);

			if (this.sIsElementPresent(selectlocator + "[class*=ImgCheckboxChecked]"))
				throw new HarnessException("Trying to uncheck box, but it was already disabled");

			this.sClick(selectlocator);
			this.zWaitForBusyOverlay();

			page = null;

		} else {
			throw new HarnessException("implement me!  action = " + action);
		}

		if (page != null) {
			page.zWaitForActive();
		}

		return (page);

	}

	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption, String item) throws HarnessException {
		tracer.trace(action + " then " + option + "," + subOption + " on item = " + item);

		throw new HarnessException("implement me!");
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String subject) throws HarnessException {
		logger.info(myPageName() + " zListItem(" + action + ", " + option + ", " + subject + ")");

		tracer.trace(action + " then " + option + " on subject = " + subject);

		if (action == null)
			throw new HarnessException("action cannot be null");
		if (option == null)
			throw new HarnessException("button cannot be null");
		if (subject == null || subject.trim().length() == 0)
			throw new HarnessException("subject cannot be null or blank");

		String itemLocator = null;
		AbsPage page = null;

		String rowLocator = "css=div[id='" + Locators.zl__TKL__rowsID + "']>div";
		int count = this.sGetCssCount(rowLocator);
		logger.debug(myPageName() + " zListItem: number of rows: " + count);

		if (count < 1)
			throw new HarnessException("No tasks in the list!");

		for (int i = 1; i <= count; i++) {

			itemLocator = rowLocator + ":nth-of-type(" + i + ")";
			if (!this.sIsElementPresent(itemLocator))
				throw new HarnessException("Item Locator not present: " + itemLocator);

			String id;
			try {
				id = this.sGetAttribute(itemLocator + "@id");
				if (id == null)
					throw new HarnessException("id was null: " + itemLocator + "@id");
				if (!id.startsWith(Locators.zli__TKL__))
					continue;

			} catch (WebDriverException e) {
				logger.warn("No ID on item: " + itemLocator);
				continue;
			}

			String subjectLocator = "css=div[id='" + id + "'] td[id$='_su']";
			if (!this.sIsElementPresent(subjectLocator))
				throw new HarnessException("Subject Locator not present: " + subjectLocator);

			String itemSubject = this.sGetText(subjectLocator);
			if ((itemSubject == null) || (itemSubject.trim().length() == 0)) {
				logger.debug("found empty task subject");
				continue;
			}

			if (itemSubject.equals(subject)) {
				break;
			}

			itemLocator = null;
		}

		if (itemLocator == null) {
			throw new HarnessException("Unable to locate item with subject(" + subject + ")");
		}

		if (action == Action.A_RIGHTCLICK) {

			// Right-Click on the item
			this.sRightClickAt(itemLocator, "");
			SleepUtil.sleepMedium();

			String optionLocator = null;

			if (option == Button.B_DELETE) {
				optionLocator = Locators.zDeleteTaskMenuItem;
				page = null;

			} else if (option == Button.O_MOVE_MENU) {
				optionLocator = Locators.zMoveTaskMenuItem;
				page = new DialogMove(MyApplication, this);

			} else if (option == Button.O_EDIT) {
				optionLocator = Locators.zEditTaskMenuItem;
				page = new FormTaskNew(this.MyApplication);

			} else if (option == Button.O_SHOW_ORIGINAL) {
				optionLocator = Locators.zShowOrigTaskMenuItem;

				page = new SeparateWindow(this.MyApplication);
				this.sClickAt(optionLocator, "");
				this.zWaitForBusyOverlay();
				SleepUtil.sleepLong();
				this.zWaitForBusyOverlay();

				return (page);

			} else if (option == Button.O_PRINT_MENU) {
				optionLocator = Locators.zPrintTaskMenuItem;

				page = new SeparateWindowPrintPreview(this.MyApplication);
				this.sClickAt(optionLocator, "");
				this.zWaitForBusyOverlay();
				SleepUtil.sleepLong();

				return (page);

			} else {
				throw new HarnessException("implement action:" + action + " option:" + option);
			}

			// click on the option
			this.sClickAt(optionLocator, "");
			SleepUtil.sleepMedium();

			this.zWaitForBusyOverlay();

		} else {
			throw new HarnessException("implement me!  action = " + action);
		}

		if (page != null) {
			page.zWaitForActive();
		}

		return (page);

	}

	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButton(" + button + ")");

		tracer.trace("Press the " + button + " button");

		if (button == null)
			throw new HarnessException("Button cannot be null!");

		String locator = null;
		AbsPage page = null;

		if (button == Button.B_REFRESH) {

			return (((AjaxPages) this.MyApplication).zPageMain.zToolbarPressButton(Button.B_REFRESH));

		} else if (button == Button.B_NEW) {

			locator = Locators.zNewTask;
			page = new FormTaskNew(this.MyApplication);

		} else if (button == Button.B_EDIT) {

			String id = ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS, ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON,
					ZimbraDOM.COMPONENT_NAME.OP_EDIT);

			locator = "css=div#" + id + " td[id$='_title']";
			page = new FormTaskNew(this.MyApplication);

		} else if (button == Button.B_DELETE) {

			String id = ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS, ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON,
					ZimbraDOM.COMPONENT_NAME.OP_DELETE);

			locator = "css=div#" + id + " td[id$='_title']";
			page = null;

		} else if (button == Button.B_Attachment) {

			String id = ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS, ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON,
					ZimbraDOM.COMPONENT_NAME.OP_ATTACHMENT);

			locator = "css=div#" + id + " td[id$='_left_icon']>div";
			page = null;

		} else if (button == Button.B_ATTACH) {

			locator = "css=tr[id$='_attachment_container'] input[name='__calAttUpload__']";
			page = null;

		} else if (button == Button.B_MOVE) {
			locator = "zb__TKL-main__MOVE_left_icon";
			page = new DialogMove(this.MyApplication, this);

		} else if (button == Button.B_PRINT) {

			locator = "zb__TKL-main__PRINT_left_icon";
			page = null;

		} else if (button == Button.B_SAVE) {
			locator = "css=div[id^='ztb__TKE']  tr[id^='ztb__TKE'] td[id$='_title']:contains('Save')";
			page = null;

		} else if (button == Button.B_CLOSE) {
			locator = Locators.zCloseButton;
			page = null;

		} else if (button == Button.B_TAG) {

			String id = ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS, ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON,
					ZimbraDOM.COMPONENT_NAME.OP_TAG_MENU, ZimbraDOM.CONTAINING_VIEW.VIEW_TASKLIST,
					ZimbraDOM.SKIN_COMPONENT.SKIN_APP_TOP_TOOLBAR);

			locator = "css=div#" + id + " td[id$='_dropdown']";

		} else if (button == Button.B_TASK_FILTERBY) {
			throw new HarnessException("implement me");

		} else if (button == Button.B_TASK_MARKCOMPLETED) {
			if (Locators.zMarkAsCompletedId == null) {
				String MarkAsCompletedId = ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS,
						ZimbraDOM.COMPONENT_NAME.OP_MARK_AS_COMPLETED, ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON,
						ZimbraDOM.CONTAINING_VIEW.VIEW_TASKLIST, ZimbraDOM.SKIN_COMPONENT.SKIN_APP_TOP_TOOLBAR);
				locator = "css=div#" + MarkAsCompletedId + " td[id$='_title']";
			}
			page = null;

		} else {
			throw new HarnessException("no logic defined for button " + button);
		}

		if (locator == null) {
			throw new HarnessException("locator was null for button " + button);
		}

		this.sClickAt(locator, "");
		this.zWaitForBusyOverlay();

		if (button == Button.B_EDIT) {
			SleepUtil.sleepLongMedium();
		} else {
			SleepUtil.sleepMedium();
		}

		if (page != null) {
			page.zWaitForActive();
		}

		return (page);
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option) throws HarnessException {

		tracer.trace("Click pulldown " + pulldown + " then " + option);
		if (pulldown == null)
			throw new HarnessException("Pulldown cannot be null!");

		if (option == null)
			throw new HarnessException("Option cannot be null!");

		String pulldownLocator = null;
		String optionLocator = null;
		AbsPage page = null;

		if (pulldown == Button.B_TAG) {
			if (option == Button.O_TAG_NEWTAG) {

				String id = ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS, ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON,
						ZimbraDOM.COMPONENT_NAME.OP_TAG_MENU, ZimbraDOM.CONTAINING_VIEW.VIEW_TASKLIST,
						ZimbraDOM.SKIN_COMPONENT.SKIN_APP_TOP_TOOLBAR);

				pulldownLocator = "css=div#" + id + " td[id$='_dropdown']";
				optionLocator = "css=div[id='" + id + "|MENU'] div[id='tasks_newtag']";

				page = new DialogTag(this.MyApplication, this);

			} else if (option == Button.O_TAG_REMOVETAG) {

				String id = ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS, ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON,
						ZimbraDOM.COMPONENT_NAME.OP_TAG_MENU, ZimbraDOM.CONTAINING_VIEW.VIEW_TASKLIST,
						ZimbraDOM.SKIN_COMPONENT.SKIN_APP_TOP_TOOLBAR);

				pulldownLocator = "css=div#" + id + " td[id$='_dropdown']";

				optionLocator = "css=div[id='" + id + "|MENU'] div[id='tasks_removetag']";

				page = null;

			} else {
				throw new HarnessException("no logic defined for pulldown/option " + pulldown + "/" + option);
			}
		} else if (pulldown == Button.B_NEW) {

			if (option == Button.O_NEW_TAG) {

				pulldownLocator = Locators.zNewTaskDropDown;
				optionLocator = Locators.zNewTagMenuItem;

				page = new DialogTag(this.MyApplication, this);
			} else if (option == Button.O_NEW_TASK) {

				pulldownLocator = Locators.zNewTaskDropDown;
				optionLocator = Locators.zNewTaskMenuItem;

				page = new FormTaskNew(this.MyApplication);

			} else if (option == Button.O_NEW_TASK_FOLDER) {

				pulldownLocator = Locators.zNewTaskDropDown;
				optionLocator = Locators.zNewTaskFolderMenuItem;

				page = new DialogCreateTaskFolder(this.MyApplication, this);

			} else {
				throw new HarnessException("no logic defined for pulldown/option " + pulldown + "/" + option);
			}

		} else if (pulldown == Button.B_MOVE) {

			if (Locators.zMoveTaskDropDownId == null) {
				String id = ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS, ZimbraDOM.COMPONENT_NAME.OP_MOVE_MENU,
						ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON, ZimbraDOM.CONTAINING_VIEW.VIEW_TASKLIST,
						ZimbraDOM.SKIN_COMPONENT.SKIN_APP_TOP_TOOLBAR);
				pulldownLocator = "css=div#" + id + " td[id$='_dropdown']";

			}

			optionLocator = Locators.zNewTaskListMenuItem;

			page = new DialogCreateTaskFolder(this.MyApplication, this);

		} else if (pulldown == Button.B_PRINT) {

			page = new SeparateWindowPrintPreview(this.MyApplication);
			((SeparateWindowPrintPreview) page).zInitializeWindowNames();

			// Click the pulldown
			if (Locators.zPrintTaskDropDownId == null) {
				String PrintTaskDropDown = ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS, ZimbraDOM.COMPONENT_NAME.OP_PRINT,
						ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON, ZimbraDOM.CONTAINING_VIEW.VIEW_TASKLIST,
						ZimbraDOM.SKIN_COMPONENT.SKIN_APP_TOP_TOOLBAR);
				pulldownLocator = "css=div#" + PrintTaskDropDown + " td[id$='_dropdown']";
			}
			this.sClickAt(pulldownLocator, "");
			zWaitForBusyOverlay();

			if (option == Button.O_PRINT_TASKFOLDER) {
				// Click the pulldown option, if specified
				optionLocator = Locators.zPrintTaskFolder;
				this.sClickAt(optionLocator, "");
				zWaitForBusyOverlay();
			}

			return (page);

		} else if (pulldown == Button.B_TASK_FILTERBY) {

			if (option == Button.O_TASK_TODOLIST) {
				if (Locators.zFilterByTaskDropDownId == null) {
					String FilterByTaskDropDownId = ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS,
							ZimbraDOM.COMPONENT_NAME.OP_VIEW_MENU, ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON,
							ZimbraDOM.CONTAINING_VIEW.VIEW_TASKLIST, ZimbraDOM.SKIN_COMPONENT.SKIN_APP_TOP_TOOLBAR);
					pulldownLocator = "css=div#" + FilterByTaskDropDownId + " td[id$='_dropdown']";
				}
				optionLocator = Locators.zToDoListTaskMenuItem;

				page = null;
			}

		}

		if (pulldownLocator != null) {

			if (!this.sIsElementPresent(pulldownLocator)) {
				throw new HarnessException("Button " + pulldown + " option " + option + " pulldownLocator "
						+ pulldownLocator + " not present!");
			}

			this.sClickAt(pulldownLocator, "");
			SleepUtil.sleepMedium();

			zWaitForBusyOverlay();

			if (optionLocator != null) {

				if (!this.sIsElementPresent(optionLocator)) {
					throw new HarnessException("Button " + pulldown + " option " + option + " optionLocator "
							+ optionLocator + " not present!");
				}

				this.sClickAt(optionLocator, "");

				zWaitForBusyOverlay();
			}

			if (page != null) {
				page.zWaitForActive();
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
			// pulldownLocator = Locators.zMoveTaskDropDown;
			if (Locators.zMoveTaskDropDownId == null) {
				String id = ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS, ZimbraDOM.COMPONENT_NAME.OP_MOVE_MENU,
						ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON, ZimbraDOM.CONTAINING_VIEW.VIEW_TASKLIST,
						ZimbraDOM.SKIN_COMPONENT.SKIN_APP_TOP_TOOLBAR);
				pulldownLocator = "css=div#" + id + " td[id$='_dropdown']";

			}

			optionLocator = "css=td#zti__ZmFolderChooser_TasksTKL-main__" + folder.getId() + "_textCell";

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

			SleepUtil.sleepSmall();

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

	public enum TaskStatus {
		PastDue, Upcoming, NoDueDate
	}

	private TaskItem parseTaskRow(String css) throws HarnessException {
		logger.info("TASK: " + css);
		// See http://bugzilla.zimbra.com/show_bug.cgi?id=56452

		if (!this.sIsElementPresent(css))
			throw new HarnessException("Unable to locate task: " + css);

		TaskItem item = new TaskItem();

		// Is it checked?
		item.gIsChecked = this.sIsElementPresent(css + " div[id$='__se'][class='ImgCheckboxChecked']");
		this.sIsElementPresent(css + " div[id$='__tg'][class='ImgBlank_16']");

		// What's the priority?
		item.gPriority = "normal";
		if (this.sIsElementPresent(css + " td[id$='__pr'] div.ImgPriorityHigh_list")) {
			item.gPriority = "high";
		} else if (this.sIsElementPresent(css + " td[id$='__pr'] div.ImgPriorityLow_list")) {
			item.gPriority = "low";
		}

		// Is there an attachment?
		item.gHasAttachments = this.sIsElementPresent(css + " div[id$='__at'][class*='ImgAttachment']");

		// Get the subject
		item.gSubject = this.sGetText(css + " td[id$='__su']").trim();

		// Get the status
		if (this.sIsElementPresent(css + " td[id$='__st']")) {
			item.gStatus = this.sGetText(css + " td[id$='__st']").trim();
		}

		// Get the % complete
		if (this.sIsElementPresent(css + " td[id$='__pc']")) {
			item.gPercentComplete = this.sGetText(css + " td[id$='__pc']").trim();
		}

		// Get the due date
		if (this.sIsElementPresent(css + " td[id$='__dt']")) {
			item.gDueDate = this.sGetText(css + " td[id$='__dt']").trim();
		}

		return (item);
	}

	public List<TaskItem> zGetTasks() throws HarnessException {

		List<TaskItem> items = new ArrayList<TaskItem>();

		String rowLocator = "css=div[id='" + Locators.zl__TKL__rowsID + "']>div";
		int count = this.sGetCssCount(rowLocator);
		logger.debug(myPageName() + " zGetTasks: number of rows: " + count);

		if (count < 1)
			throw new HarnessException("No tasks in the list!");

		String itemLocator = rowLocator + ":first-child";

		for (int i = 1; i < count; i++) {
			itemLocator = itemLocator + " + div ";
			// String itemLocator = rowLocator + ":nth-of-type("+ i +")";
			if (!this.sIsElementPresent(itemLocator))
				throw new HarnessException("Item Locator not present: " + itemLocator);

			String id;
			try {
				id = this.sGetAttribute(itemLocator + "@id");
				if (id == null)
					throw new HarnessException("id was null: " + itemLocator + "@id");
				if (!id.startsWith(Locators.zli__TKL__))
					continue;

			} catch (WebDriverException e) {
				logger.warn("No ID on item: " + itemLocator);
				continue;
			}

			// Found a task

			TaskItem item = parseTaskRow("css=div[id='" + id + "']");
			items.add(item);
			logger.info(item.prettyPrint());

		}

		// Return the list of items
		return (items);

	}

	@Override
	public AbsPage zKeyboardShortcut(Shortcut shortcut) throws HarnessException {
		String keyCode = "";
		if (shortcut == null)
			throw new HarnessException("Shortcut cannot be null");

		tracer.trace("Using the keyboard, press the " + shortcut.getKeys() + " keyboard shortcut");

		AbsPage page = null;

		SleepUtil.sleepMedium();

		if ((shortcut == Shortcut.S_NEWTAG)) {

			// "New Message" shortcuts result in a compose form opening
			// page = new FormMailNew(this.MyApplication);
			page = new DialogTag(MyApplication, ((AjaxPages) MyApplication).zPageTasks);
			keyCode = "78,84";

		} else if (shortcut == Shortcut.S_ESCAPE) {
			page = new DialogWarning(DialogWarning.DialogWarningID.SaveTaskChangeMessage, this.MyApplication,
					((AjaxPages) this.MyApplication).zPageTasks);

			keyCode = "27";

		} else if (shortcut == Shortcut.S_ASSISTANT) {
			page = new DialogAssistant(MyApplication, ((AjaxPages) MyApplication).zPageTasks);
			keyCode = "192";

		} else if (shortcut == Shortcut.S_NEWTASK) {
			// page = new DialogAssistant(MyApplication, ((AppAjaxClient)
			// MyApplication).zPageTasks);
			page = new FormTaskNew(this.MyApplication);
			keyCode = "78,75";

		} else if (shortcut == Shortcut.S_TASK_HARDELETE) {
			// Hard Delete shows the Warning Dialog : Are you sure you want to
			// permanently delete it?
			page = new DialogWarning(DialogWarning.DialogWarningID.PermanentlyDeleteTheItem, MyApplication,
					((AjaxPages) MyApplication).zPageTasks);

			zKeyboard.zTypeCharacters(shortcut.getKeys());
			return (page);

		} else if (shortcut == Shortcut.S_MOVE) {

			// "Move" shortcut opens "Choose Folder" dialog
			page = new DialogMove(MyApplication, this);

			keyCode = "77";
		} else if (shortcut == Shortcut.S_BACKSPACE) {
			page = null;
			keyCode = "8";

		} else if (shortcut == Shortcut.S_MAIL_MOVETOTRASH) {

			zKeyboard.zTypeCharacters(shortcut.getKeys());
			page = null;
			return page;
		} else if (shortcut == Shortcut.S_PRINTTASK) {

			page = new SeparateWindowPrintPreview(this.MyApplication);
			((SeparateWindowPrintPreview) page).zInitializeWindowNames();

			keyCode = "80";
			zKeyDown(keyCode);
			this.zWaitForBusyOverlay();

			return (page);

		}

		else {

			throw new HarnessException("implement shortcut: " + shortcut);
		}

		zKeyDown(keyCode);
		// zKeyboard.zTypeCharacters(shortcut.getKeys());

		this.zWaitForBusyOverlay();

		// If a page is specified, wait for it to become active
		if (page != null) {
			page.zWaitForActive(); // This method throws a HarnessException if never active
		}
		return (page);
	}

	public TaskItem browseTask(String subject) throws HarnessException {
		// Get the list of tasks in the view
		List<TaskItem> tasks = zGetTasks();
		ZAssert.assertNotNull(tasks, "Verify the list of tasks exists");

		// Iterate over the task list, looking for the new task
		TaskItem found = null;
		for (TaskItem t : tasks) {
			logger.info("Task: looking for " + subject + " found: " + t.gSubject);
			if (subject.equals(t.gSubject)) {
				// Found it!
				found = t;
			}
		}
		return found;
	}

	public String zGetHtmlBodyText() throws HarnessException {
		try {
			sSelectFrame("css=div[id='zv__TKL-main'] iframe[id$='__body__iframe']");
			String bodyhtml = this.sGetHtmlSource();
			return bodyhtml;
		} finally {
			this.sSelectFrame("relative=top");
		}
	}

	public boolean zVerifyDisabled(String buttonID) throws HarnessException {
		if (buttonID == "DeleteButton") {
			return this.sIsElementPresent("css=div[id='" + ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS,
					ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON, ZimbraDOM.COMPONENT_NAME.OP_DELETE) + "'].ZDisabled");
		} else if (buttonID == "EditButton") {
			return this.sIsElementPresent("css=div[id='" + ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS,
					ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON, ZimbraDOM.COMPONENT_NAME.OP_EDIT) + "'].ZDisabled");
		} else if (buttonID == "MoveButton") {
			return this.sIsElementPresent("css=div[id='" + ZimbraDOM.getID(ZimbraDOM.APP.APP_TASKS,
					ZimbraDOM.COMPONENT_TYPE.WIDGET_BUTTON, ZimbraDOM.COMPONENT_NAME.OP_MOVE_MENU) + "'].ZDisabled");
		} else {
			return false;
		}
	}
	
	public boolean zVerifyTaskExists(String subject) throws HarnessException {
		String taskLocator = "css=div[id^='zli__TKL-main'] td[id^='zlif__TKL-main'][id$='__su']"; 
		return sIsElementPresent(taskLocator + ":contains('" +  subject + "')");
	}
	
	public boolean zVerifyTaskBody(String content) throws HarnessException {
		try {
			webDriver().switchTo().defaultContent();
			webDriver().switchTo().frame(0);
			if (this.getElement("css=html body").getText().equals(content)) {
				return true;
			} else {
				return false;
			}

		} finally {
			webDriver().switchTo().defaultContent();
		}
	}
}