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
package com.zimbra.qa.selenium.projects.admin.pages;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import com.zimbra.qa.selenium.framework.ui.AbsApplication;
import com.zimbra.qa.selenium.framework.ui.AbsPage;
import com.zimbra.qa.selenium.framework.ui.AbsTab;
import com.zimbra.qa.selenium.framework.ui.Action;
import com.zimbra.qa.selenium.framework.ui.Button;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.framework.util.ZAssert;
import com.zimbra.qa.selenium.projects.admin.items.DomainItem;

public class PageManageDomains extends AbsTab {

	public static class Locators {
		public static final String CONFIGURE_ICON = "css=div.ImgAdministration";
		public static final String DOMAINS = "css=div[id^='zti__AppAdmin__CONFIGURATION__DOMAINS_'][id$='textCell']";
		public static final String HIGHLIGHTED_DOMAINS_TREEITEM = "css=div[id^='zti__AppAdmin__CONFIGURATION__DOMAINS'][aria-selected='true']";
		public static final String GEAR_ICON = "css=div.ImgConfigure";
		public static final String NEW_MENU = "css=td[id='zmi__zb_currentApp__NEW_title']:contains('New')";
		public static final String ADD_DOMAIN_ALIAS = "css=div[id='zm__zb_currentApp__MENU_POP'] div[class='ImgDomainAlias']";
		public static final String HOME = "Home";
		public static final String CONFIGURE = "Configure";
		public static final String DOMAIN = "Domains";
		public static final String DELETE_BUTTON = "css=div[id='zm__zb_currentApp__MENU_POP'] div[class='ImgDelete']";
		public static final String EDIT_BUTTON = "css=td[id='zmi__zb_currentApp__EDIT_title']:contains('Edit')";
		public static final String CONFIGURE_GAL = "div[id='zmi__zb_currentApp__GAL_WIZARD'] td[id$='_title']";
		public static final String RIGHT_CLICK_MENU_DELETE_BUTTON = "css=td[id='zmi__DMLV__DELETE_title']:contains('Delete')";
		public static final String RIGHT_CLICK_MENU_EDIT_BUTTON = "css=td[id='zmi__DMLV__EDIT_title']:contains('Edit')";
		public static final String DOMAIN_EDIT_ACL = "css=div[id^='zti__AppAdmin__CONFIGURATION__DOMAINS'][class='ZTreeItemTextCell']:contains('ACL')";
		public static final String DOMAIN_EDIT_ACL_GRANTEE_NAME = "css=div[class='DwtDialog WindowOuterContainer'] table[class='dynselect_table'] input[id^='zdlgv__EDIT_ACL'][id$='_grantee_email_display']";
		public static final String DOMAIN_ACCOUNTS_LIMITS = "css=div[id^='zti__AppAdmin__CONFIGURATION__DOMAINS'] div[class='ZTreeItemTextCell']:contains('Account Limits')";
		public static final String DOMAIN_ACCOUNTS_LIMITS_ADD = "css=div[id$='account_form_account_limits_tab'] td[class='ZWidgetTitle']:contains('Add')";
		public static final String DOMAIN_ACCOUNTS_LIMITS_COS_NAME = "css=table[id='zdlgv__UNDEFINE1_group_table']  tbody tr td[id='zdlgv__UNDEFINE1_cos___container'] input";
		public static final String DOMAIN_ACCOUNTS_LIMITS_COS_LIMIT = "css=table[id='zdlgv__UNDEFINE1_group_table'] tbody tr td[id='zdlgv__UNDEFINE1_limits___container'] input";
		public static final String MAXIMUM_ACCOUNTS_FOR_DOMAIN = "css=input[id='ztabv__DOAMIN_EDIT_zimbraDomainMaxAccounts']";
		public static final String DOMAIN_ACCOUNTS_LIMITS_AT_COS_OK = "css=td[id^='zdlg__UNDEFINE']:contains('OK')";
		public static final String DOMAIN_EDIT_ACL_ADD = "css=td[id^='ztabv__DOAMIN_EDIT_dwt_button'] td[id$='title']:contains('Add')";
		public static final String VIEW_ACCOUNTS = "css=td[id='zmi__zb_currentApp__VIEW_DOMAIN_ACCOUNTS_title']:contains('View Accounts')";
		public static final String RIGHT_CLICK_CONFIGURE_GRANTS = "css=td[id^='zmi__DMLV__UNKNOWN']:contains('Configure Grants')";
		public static final String CONFIGURE_GRANTS = "css=td[id^='zmi__zb_currentApp__UNKNOWN']:contains('Configure Grants')";
		public static final String EDIT_ACL = "css=td[id$='_dwt_button_12___container'] td:contains('Edit')";
		public static final String DELETE_ACL = "css=td[id$='_dwt_button_13___container'] td:contains('Delete')";
	}

	public static class TypeOfObject {
		public static final String DOMAIN = "Domain";
		public static final String DOMAIN_ALIAS = "Domain Alias";

	}

	public String typeOfObject = "Domain";

	public String zGetType() {
		return typeOfObject;
	}

	public void zSetType(String type) {
		this.typeOfObject = type;
	}

	public PageManageDomains(AbsApplication application) {
		super(application);
	}

	@Override
	public boolean zIsActive() throws HarnessException {

		// Make sure the Admin Console is loaded in the browser
		if (!MyApplication.zIsLoaded())
			throw new HarnessException("Admin Console application is not active!");

		boolean present = sIsElementPresent(Locators.HIGHLIGHTED_DOMAINS_TREEITEM);
		if (!present) {
			return (false);
		}
		return (true);
	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public void zNavigateTo() throws HarnessException {

		if (zIsActive()) {
			return;
		}

		// Refresh the page if page is not the main page
		if (!sIsElementPresent(Locators.CONFIGURE_ICON)) {
			zRefreshMainUI();
		}
		sClick(Locators.CONFIGURE_ICON);
		zWaitForWorkInProgressDialogInVisible();
		zWaitForElementPresent(Locators.DOMAINS);
		sClick(Locators.DOMAINS);
		zWaitForWorkInProgressDialogInVisible();
		zWaitForActive();
	}

	@Override
	public AbsPage zListItem(Action action, String item) throws HarnessException {
		logger.info(myPageName() + " zListItem(" + action + ", " + item + ")");

		tracer.trace(action + " on subject = " + item);

		AbsPage page = null;
		SleepUtil.sleepSmall();

		// How many items are in the table?
		String rowsLocator = "css=div#zl__DOMAIN_MANAGE div[id$='__rows'] div[id^='zli__']";
		int count = this.sGetCssCount(rowsLocator);
		logger.debug(myPageName() + " zListGetAccounts: number of accounts: " + count);
		int m = 50;
		if (count >= 50) {
			for (int a1 = 1; a1 <= 8; a1++) {
				String p0 = rowsLocator + ":nth-child(" + m + ")";
				if (this.sIsElementPresent(p0)) {
					sClick(p0);
					this.zKeyboard.zTypeKeyEvent(KeyEvent.VK_DOWN);
					m = m + 20;
				} else
					break;

			}

		}
		count = this.sGetCssCount(rowsLocator);

		for (int i = 1; i <= count; i++) {
			final String accountLocator = rowsLocator + ":nth-child(" + i + ")";
			String locator;

			// Email Address
			locator = accountLocator + " table tr td:nth-child(2)";

			if (this.sIsElementPresent(locator)) {
				if (this.sGetText(locator).trim().equalsIgnoreCase(item)) {
					if (action == Action.A_LEFTCLICK) {
						SleepUtil.sleepMedium();
						sClickAt(locator, "");
						break;
					} else if (action == Action.A_RIGHTCLICK) {
						sRightClick(locator);
						break;
					} else if (action == Action.A_DOUBLECLICK) {
						sDoubleClick(locator);
						break;
					}
				}
			}
		}
		return page;
	}

	@Override
	public AbsPage zListItem(Action action, Button option, String item) throws HarnessException {
		return null;
	}

	@Override
	public AbsPage zListItem(Action action, Button option, Button subOption, String item) throws HarnessException {
		return null;
	}

	@Override
	public AbsPage zToolbarPressButton(Button button) throws HarnessException {

		logger.info(myPageName() + " zToolbarPressButton(" + button + ")");

		tracer.trace("Press the " + button + " button");

		if (button == null)
			throw new HarnessException("Button cannot be null!");

		String locator = null;
		AbsPage page = null;

		if (button == Button.B_NEW) {
			locator = Locators.DOMAINS;
			page = new WizardCreateDomain(this);

		} else if (button == Button.B_TREE_DELETE) {
			locator = Locators.RIGHT_CLICK_MENU_DELETE_BUTTON;
			page = new DialogForDeleteOperationDomain(this.MyApplication, null);

		} else if (button == Button.B_TREE_EDIT) {
			locator = Locators.RIGHT_CLICK_MENU_EDIT_BUTTON;

			if (typeOfObject.equals(TypeOfObject.DOMAIN))
				page = new FormEditDomain(this.MyApplication);
			else if (typeOfObject.equals(TypeOfObject.DOMAIN_ALIAS))
				page = new WizardCreateDomainAlias(this);

		} else if (button == Button.B_HOME_DOMAIN) {
			locator = PageMain.Locators.HomeCreateDomain;
			page = new WizardCreateDomain(this);
			this.sClickAt(locator, "");
			return page;

		} else if (button == Button.B_CONFIGURE_GRANTS) {
			locator = Locators.RIGHT_CLICK_CONFIGURE_GRANTS;

		} else if (button == Button.B_EDIT_ACL) {
			locator = Locators.EDIT_ACL;
			page = new WizardEditACLAtDL(this);

		} else if (button == Button.B_DELETE_ACL) {
			locator = Locators.DELETE_ACL;
			page = new DialogForDeleteOperationACL(this.MyApplication, null);

		} else {
			throw new HarnessException("no logic defined for button " + button);
		}

		if (locator == null) {
			throw new HarnessException("locator was null for button " + button);
		}

		this.sClickAt(locator, "");

		if (page != null) {
			SleepUtil.sleepMedium();
		}

		return (page);
	}

	@Override
	public AbsPage zToolbarPressPulldown(Button pulldown, Button option) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButtonWithPulldown(" + pulldown + ", " + option + ")");

		tracer.trace("Click pulldown " + pulldown + " then " + option);

		if (pulldown == null)
			throw new HarnessException("Pulldown cannot be null!");

		if (option == null)
			throw new HarnessException("Option cannot be null!");

		String pulldownLocator = null;
		String optionLocator = null;
		AbsPage page = null;

		if (pulldown == Button.B_GEAR_BOX) {
			pulldownLocator = Locators.GEAR_ICON;

			if (option == Button.O_NEW) {
				optionLocator = Locators.NEW_MENU;
				page = new WizardCreateDomain(this);

			} else if (option == Button.O_ADD_DOMAIN_ALIAS) {
				optionLocator = Locators.ADD_DOMAIN_ALIAS;
				page = new WizardCreateDomainAlias(this);

			} else if (option == Button.O_DELETE) {
				optionLocator = Locators.DELETE_BUTTON;
				page = new DialogForDeleteOperationDomain(this.MyApplication, null);

			} else if (option == Button.O_EDIT) {
				optionLocator = Locators.EDIT_BUTTON;

				if (typeOfObject.equals(TypeOfObject.DOMAIN))
					page = new FormEditDomain(this.MyApplication);
				else if (typeOfObject.equals(TypeOfObject.DOMAIN_ALIAS))
					page = new WizardCreateDomainAlias(this);

			} else if (option == Button.O_CONFIGURE_GAL) {
				optionLocator = Locators.CONFIGURE_GAL;
				page = new WizardConfigureGAL(this);

			} else if (option == Button.O_VIEW_ACCOUNTS) {
				optionLocator = Locators.VIEW_ACCOUNTS;
				page = new DialogForDeleteOperationDomain(this.MyApplication, null);

			} else if (option == Button.O_CONFIGURE_GRANTS) {
				optionLocator = Locators.CONFIGURE_GRANTS;

			} else {
				throw new HarnessException("no logic defined for pulldown/option " + pulldown + "/" + option);
			}

		} else {
			throw new HarnessException("no logic defined for pulldown/option " + pulldown + "/" + option);
		}

		if (pulldownLocator != null) {

			if (!this.sIsElementPresent(pulldownLocator)) {
				throw new HarnessException("Button " + pulldown + " option " + option + " pulldownLocator "
						+ pulldownLocator + " not present!");
			}

			this.sClickAt(pulldownLocator, "");

			if (optionLocator != null) {

				if (!this.sIsElementPresent(optionLocator)) {
					throw new HarnessException("Button " + pulldown + " option " + option + " optionLocator "
							+ optionLocator + " not present!");
				}

				SleepUtil.sleepMedium();
				this.sClickAt(optionLocator, "");
			}

			if (option == Button.O_EDIT) {
				SleepUtil.sleepMedium();
			}
		}

		return (page);
	}

	public boolean zVerifyHeader(String header) throws HarnessException {
		if (this.sIsElementPresent("css=span:contains('" + header + "')"))
			return true;
		return false;
	}

	/**
	 * Return a list of all domain entries in the current view
	 */
	public List<DomainItem> zListGetDomainList() throws HarnessException {

		List<DomainItem> items = new ArrayList<DomainItem>();

		if (!this.sIsElementPresent("css=div[id='zl__DOMAIN_MANAGE'] div[id$='__rows']"))
			throw new HarnessException("Account Rows is not present");

		// How many items are in the table?
		String rowsLocator = "//div[@id='zl__DOMAIN_MANAGE']//div[contains(@id, '__rows')]//div[contains(@id,'zli__')]";
		int count = this.sGetXpathCount(rowsLocator);
		logger.debug(myPageName() + " zListGetdomain: number of domain: " + count);

		for (int i = 1; i <= count; i++) {
			final String domainLocator = rowsLocator + "[" + i + "]";
			String locator;

			DomainItem item = new DomainItem();

			// Type (image)
			locator = domainLocator + "//td[contains(@id,'domain_data_name')]";
			if (this.sIsElementPresent(locator)) {
				item.setName(this.sGetText(locator).trim());
			}

			// Add the new item to the list
			items.add(item);
			logger.info(item.prettyPrint());
		}

		return (items);
	}

	public AbsPage zSetAccountLimitPerCos(String cos_name, String limit) throws HarnessException {
		logger.info(myPageName() + " zSetAccountLimitPerCos(" + cos_name + ", " + limit + ")");

		tracer.trace("Enter cos name and limit " + cos_name + " then " + limit);

		AbsPage page = null;
		SleepUtil.sleepSmall();

		// Click on accounts limit tab
		this.sClickAt(Locators.DOMAIN_ACCOUNTS_LIMITS, "");

		// Click on Add button
		this.sClickAt(Locators.DOMAIN_ACCOUNTS_LIMITS_ADD, "");

		SleepUtil.sleepLong();

		// Enter COS name
		if (this.sIsElementPresent(Locators.DOMAIN_ACCOUNTS_LIMITS_COS_NAME)) {
			this.sFocus(Locators.DOMAIN_ACCOUNTS_LIMITS_COS_NAME);
			this.sType(Locators.DOMAIN_ACCOUNTS_LIMITS_COS_NAME, cos_name);
		}

		// Enter Limit
		if (this.sIsElementPresent(Locators.DOMAIN_ACCOUNTS_LIMITS_COS_LIMIT)) {
			this.sFocus(Locators.DOMAIN_ACCOUNTS_LIMITS_COS_LIMIT);
			this.sType(Locators.DOMAIN_ACCOUNTS_LIMITS_COS_LIMIT, limit);
		}

		// Click on OK button
		this.sClickAt(Locators.DOMAIN_ACCOUNTS_LIMITS_AT_COS_OK, "");

		return (page);
	}

	public AbsPage zSetAccountLimitOnDomain(String limit) throws HarnessException {
		logger.info(myPageName() + " zSetAccountLimitPerCos(" + limit + ")");

		tracer.trace("Enter cos name and limit " + limit);

		AbsPage page = null;

		// Click on accounts limit tab
		this.sClickAt(Locators.DOMAIN_ACCOUNTS_LIMITS, "");

		SleepUtil.sleepMedium();

		// Enter Limit
		if (this.sIsElementPresent(Locators.MAXIMUM_ACCOUNTS_FOR_DOMAIN)) {
			this.sFocus(Locators.MAXIMUM_ACCOUNTS_FOR_DOMAIN);
			this.sType(Locators.MAXIMUM_ACCOUNTS_FOR_DOMAIN, limit);

		}

		return (page);
	}

	public AbsPage zSetAccountLimit(String limit) throws HarnessException {
		logger.info(myPageName() + " zSetAccountLimitPerCos(" + limit + ")");

		tracer.trace("Enter cos name and limit " + limit);

		AbsPage page = null;

		// Click on accounts limit tab
		this.sClickAt(Locators.DOMAIN_ACCOUNTS_LIMITS, "");

		SleepUtil.sleepMedium();

		// Enter Limit
		if (this.sIsElementPresent(Locators.MAXIMUM_ACCOUNTS_FOR_DOMAIN)) {
			this.sFocus(Locators.MAXIMUM_ACCOUNTS_FOR_DOMAIN);
			this.sType(Locators.MAXIMUM_ACCOUNTS_FOR_DOMAIN, limit);
		}

		return (page);
	}

	/**
	 * Press the toolbar button
	 */
	public AbsPage zToolbarPressButton(Button button, String cos_name, String limit) throws HarnessException {
		logger.info(myPageName() + " zToolbarPressButton(" + button + ")");

		tracer.trace("Click button " + button);

		// Fallthrough objects
		AbsPage page = null;

		// Click on accounts limit tab
		this.sClickAt(Locators.DOMAIN_ACCOUNTS_LIMITS, "");

		if (button == Button.B_ACCOUNTS_LIMIT_PER_DOMAIN) {

			this.sFocus(Locators.MAXIMUM_ACCOUNTS_FOR_DOMAIN);
			this.sType(Locators.MAXIMUM_ACCOUNTS_FOR_DOMAIN, limit);
			page = this;

		} else if (button == Button.B_ACCOUNTS_LIMIT_PER_COS) {

			// Click on Add button
			this.sClickAt(Locators.DOMAIN_ACCOUNTS_LIMITS_ADD, "");

			SleepUtil.sleepLong();

			// Enter COS name
			if (this.sIsElementPresent(Locators.DOMAIN_ACCOUNTS_LIMITS_COS_NAME)) {
				this.sFocus(Locators.DOMAIN_ACCOUNTS_LIMITS_COS_NAME);
				this.sType(Locators.DOMAIN_ACCOUNTS_LIMITS_COS_NAME, cos_name);
			}

			// Enter Limit
			if (this.sIsElementPresent(Locators.DOMAIN_ACCOUNTS_LIMITS_COS_LIMIT)) {
				this.sFocus(Locators.DOMAIN_ACCOUNTS_LIMITS_COS_LIMIT);
				this.sType(Locators.DOMAIN_ACCOUNTS_LIMITS_COS_LIMIT, limit);

				// Click on OK button
				this.sClickAt(Locators.DOMAIN_ACCOUNTS_LIMITS_AT_COS_OK, "");
			}
			page = this;
		} else {
			throw new HarnessException("no logic defined for button " + button);
		}

		return page;
	}

	public boolean zVerifySearchResult(String item) throws HarnessException {

		logger.info(myPageName() + " zVerifyPolicyName(" + item + ")");
		boolean found = false;
		SleepUtil.sleepMedium();

		// How many items are in the table?
		String rowsLocator = "css=div[id='zl__SEARCH_MANAGE'] div[id$='__rows'] div[id^='zli__']";
		int count = this.sGetCssCount(rowsLocator);
		logger.debug(myPageName() + " zListGetPolicy: number of policys: " + count);

		for (int i = 1; i <= count; i++) {
			final String aceLocator = rowsLocator + ":nth-child(" + i + ")";
			String locator;

			// Email Address
			locator = aceLocator + " tr";

			if (this.sIsElementPresent(locator)) {
				if (this.sGetText(locator).trim().contains(item)) {

					found = true;
					break;
				} else
					logger.info("search result not displayed in current view");
			}

			if (found == true) {
				SleepUtil.sleepSmall();
				logger.info("Search result displayed in current view");
				ZAssert.assertTrue(found, "Search result displayed in current view");
				break;
			}
		}
		return found;
	}

	public boolean zVerifyACE(String item) throws HarnessException {

		logger.info(myPageName() + " zVerifyACE(" + item + ")");
		boolean found = false;
		SleepUtil.sleepMedium();

		// How many items are in the table?
		String rowsLocator = "css=div[id='zl'] div[id$='__rows'] div[id^='zli__']";
		int count = this.sGetCssCount(rowsLocator);
		logger.debug(myPageName() + " zVerifyACE: number of rows: " + count);

		for (int i = 1; i <= count; i++) {
			final String aceLocator = rowsLocator + ":nth-child(" + i + ")";
			String locator;

			// Email Address
			locator = aceLocator + " td";

			if (this.sIsElementPresent(locator)) {
				if (this.sGetText(locator).trim().equalsIgnoreCase(item)) {

					found = true;
					break;
				} else
					logger.info("search result not displayed in current view");
			}

			if (found == true) {
				SleepUtil.sleepSmall();
				logger.info("Search result displayed in current view");
				ZAssert.assertTrue(found, "Search result displayed in current view");
				break;
			}
		}
		return found;
	}
}