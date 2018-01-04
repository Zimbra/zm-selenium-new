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
package com.zimbra.qa.selenium.projects.admin.pages;

import com.zimbra.qa.selenium.framework.items.IItem;
import com.zimbra.qa.selenium.framework.ui.AbsTab;
import com.zimbra.qa.selenium.framework.ui.AbsWizard;
import com.zimbra.qa.selenium.framework.util.HarnessException;
import com.zimbra.qa.selenium.framework.util.SleepUtil;
import com.zimbra.qa.selenium.projects.admin.items.HSMItem;

public class WizardAddNewVolume extends AbsWizard {
	
	public static class Locators {
		public static final String VOL_TYPE_LOCAL = "css=[style*='z-index: 7'] input[id='zdlgv__DLG_NEW_PWRSTR_VOLUME_BObject_49']";
		public static final String VOL_TYPE_S3_BUCKET = "css=[style*='z-index: 7'] input[id='zdlgv__DLG_NEW_PWRSTR_VOLUME_BObject_50']";
		public static final String NEXT_BUTTON = "css=div[style*='z-index: 7'] td[id$='_button12_title']";
		public static final String FINISH_BUTTON = "css=div[style*='z-index: 7'] td[id$='_button13_title']";
		public static final String VOLUME_NAME_TEXT_BOX = "css=input[id='zdlgv__DLG_NEW_PWRSTR_VOLUME_BObject_10']";
		public static final String VOLUME_PATH_TEXT_BOX = "css=input[id='zdlgv__DLG_NEW_PWRSTR_VOLUME_BObject_11']";
		public static final String SET_CURRENT_CHECK_BOX = "css=input[id='zdlgv__DLG_NEW_PWRSTR_VOLUME_BObject_44']";
	}

	public WizardAddNewVolume(AbsTab page) {
		super(page);
		logger.info("New " + WizardAddNewVolume.class.getName());
	}

	@Override
	public IItem zCompleteWizard(IItem item) throws HarnessException {
		if (!(item instanceof HSMItem)) throw new HarnessException("item must be an HSMItem, was " + item.getClass().getCanonicalName());

		HSMItem hsmVol = (HSMItem) item;
		sClickAt(Locators.VOL_TYPE_LOCAL, "");
		sClickAt(Locators.NEXT_BUTTON, "");
		sType(Locators.VOLUME_NAME_TEXT_BOX, hsmVol.getVolName());
		sType(Locators.VOLUME_PATH_TEXT_BOX, hsmVol.getVolPath());
		SleepUtil.sleepVerySmall();
		sClickAt(Locators.NEXT_BUTTON, "");
		if(hsmVol.getIsCurrent()== true){
			sCheck(Locators.SET_CURRENT_CHECK_BOX);
		}
		sClickAt(Locators.FINISH_BUTTON, "");
		SleepUtil.sleepSmall();
		return (hsmVol);
	}

	@Override
	public String myPageName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		// TODO Auto-generated method stub
		return false;
	}

}
