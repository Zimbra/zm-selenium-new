package com.zimbra.qa.selenium.projects.ajax.tests.addressbook.contactgroups;


import java.util.List;

import org.testng.annotations.Test;

import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.items.FolderItem.SystemFolder;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.AjaxCommonTest;
import com.zimbra.qa.selenium.projects.ajax.ui.*;


public class MoveContactGroup extends AjaxCommonTest  {
	public MoveContactGroup() {
		logger.info("New "+ MoveContactGroup.class.getCanonicalName());
		
		// All tests start at the Address page
		super.startingPage = app.zPageAddressbook;

		super.startingAccountPreferences = null;		
		
	}
	
	private void MoveAndVerify(FolderItem emailedContacts, ContactGroupItem group, DialogMove dialogContactMove) throws HarnessException {
	    //enter the moved folder
        dialogContactMove.zClickTreeFolder(emailedContacts);
        dialogContactMove.zClickButton(Button.B_OK);
       
        //verify toasted message 1 contact group moved to "Emailed Contacts"
        String expectedMsg = "1 contact group moved to \"Emailed Contacts\"";
        ZAssert.assertStringContains(app.zPageMain.zGetToaster().zGetToastMessage(),
		        expectedMsg , "Verify toast message '" + expectedMsg + "'");

        //verify moved contact group not displayed
        List<ContactItem> contacts = app.zPageAddressbook.zListGetContacts(); 
 	           
		boolean isFileAsEqual=false;
		for (ContactItem ci : contacts) {
			if (ci.fileAs.equals(group.fileAs)) {
	            isFileAsEqual = true;	 
				break;
			}
		}
		
        ZAssert.assertFalse(isFileAsEqual, "Verify contact group fileAs (" + group.fileAs + ") not displayed in folder Contacts");
	
        //verify moved contact displayed in folder Emailed Contacts
        // refresh folder Emailed Contacts
        app.zTreeContacts.zTreeItem(Action.A_LEFTCLICK, emailedContacts);
   	 
        contacts = app.zPageAddressbook.zListGetContacts(); 
         
	    isFileAsEqual=false;
		for (ContactItem ci : contacts) {
			if (ci.fileAs.equals(group.fileAs)) {
	            isFileAsEqual = true;	 
				break;
			}
		}
		
        ZAssert.assertTrue(isFileAsEqual, "Verify contact group fileAs (" + group.fileAs + ") not displayed in folder Emailed Contacts");
	
	}
	
	
	@Test(	description = "Move a contact group to different folder by click Move on toolbar",
			groups = { "smoke" })
	public void ClickMoveOnToolbar() throws HarnessException {
		        
		FolderItem emailedContacts = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.EmailedContacts);
 		
	    // Create a contact group via Soap then select
		ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app, Action.A_LEFTCLICK);
	
        //click Move icon on toolbar
        DialogMove dialogContactMove = (DialogMove) app.zPageAddressbook.zToolbarPressButton(Button.B_MOVE);
     
        //move group to different folder
        MoveAndVerify(emailedContacts, group, dialogContactMove);    
 
   	}

	@Test(	description = "Move a contact group to different folder by click Move on Context menu",
			groups = { "functional" })
	public void ClickMoveOnContextmenu() throws HarnessException {
		        
		FolderItem emailedContacts = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.EmailedContacts);
 		
	    	// Create a contact group via Soap then select
		ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app, Action.A_LEFTCLICK);
	
        //click Move icon on context menu
	    DialogMove dialogContactMove = (DialogMove) app.zPageAddressbook.zListItem(Action.A_RIGHTCLICK, Button.B_MOVE, group.fileAs);
	     
        //move group to different folder
        MoveAndVerify(emailedContacts, group, dialogContactMove);    
 
   	}

	@Test(	description = "Move a contact group to different folder with shortcut m",
			groups = { "functional" })
	public void ClickShortcutm() throws HarnessException {
		        
		FolderItem emailedContacts = FolderItem.importFromSOAP(app.zGetActiveAccount(), SystemFolder.EmailedContacts);
 		
	    // Create a contact group via Soap then select
		ContactGroupItem group = app.zPageAddressbook.createUsingSOAPSelectContactGroup(app, Action.A_LEFTCLICK);
	
        //click shortcut m
	    DialogMove dialogContactMove = (DialogMove) app.zPageAddressbook.zKeyboardShortcut(Shortcut.S_MOVE);
    
	    //move group to different folder
        MoveAndVerify(emailedContacts, group, dialogContactMove);    
 
   	}

}

