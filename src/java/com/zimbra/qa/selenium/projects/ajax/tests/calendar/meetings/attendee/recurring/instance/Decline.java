/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2013, 2014, 2015, 2016 Synacor, Inc.
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
package com.zimbra.qa.selenium.projects.ajax.tests.calendar.meetings.attendee.recurring.instance;

import java.util.*;
import org.testng.annotations.Test;
import com.zimbra.qa.selenium.framework.core.Bugs;
import com.zimbra.qa.selenium.framework.items.FolderItem;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.projects.ajax.core.*;
import com.zimbra.qa.selenium.projects.ajax.pages.calendar.DialogConfirmationDeclineAppointment;

public class Decline extends AjaxCore {

	public Decline() {
		logger.info("New "+ Decline.class.getCanonicalName());
		super.startingPage =  app.zPageCalendar;
	}

	
	@Bugs (ids = "83240")
	@Test (description = "Rt-click to appointment from the calendar app and Decline the meeting invite (Instance)",
			groups = { "smoke", "L1" })

	public void DeclineMeeting_01() throws HarnessException {

		// ------------------------ Test data ------------------------------------

		Calendar now = Calendar.getInstance();
		String tz = ZTimeZone.getLocalTimeZone().getID();
		ZDate startUTC = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 10, 0, 0);
		ZDate endUTC   = new ZDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH), 11, 0, 0);

		String apptSubject = ConfigProperties.getUniqueString();
		String apptBody = "body" + ConfigProperties.getUniqueString();

		// --------------- Creating invitation (organizer) ----------------------------

		ZimbraAccount.AccountA().soapSend(
				"<CreateAppointmentRequest xmlns='urn:zimbraMail'>" +
					"<m>"+
						"<inv method='REQUEST' type='event' fb='B' transp='O' allDay='0' name='"+ apptSubject +"'>"+
							"<s d='"+ startUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>" +
							"<e d='"+ endUTC.toTimeZone(tz).toYYYYMMDDTHHMMSS() +"' tz='"+ tz +"'/>" +
							"<or a='"+ ZimbraAccount.AccountA().EmailAddress +"'/>" +
							"<at role='REQ' ptst='NE' rsvp='1' a='" + app.zGetActiveAccount().EmailAddress + "'/>" +
							"<recur>" +
								"<add>" +
									"<rule freq='DAI'>" +
										"<interval ival='1'/>" +
									"</rule>" +
								"</add>" +
							"</recur>" +
						"</inv>" +
						"<e a='"+ app.zGetActiveAccount().EmailAddress +"' t='t'/>" +
						"<mp content-type='text/plain'>" +
							"<content>"+ apptBody +"</content>" +
						"</mp>" +
						"<su>"+ apptSubject +"</su>" +
					"</m>" +
				"</CreateAppointmentRequest>");

		// Verify appointment exists in current view
        ZAssert.assertTrue(app.zPageCalendar.zVerifyAppointmentExists(apptSubject), "Verify appointment displayed in current view");


		// --------------- Login to attendee & decline invitation ----------------------------------------------------

		app.zPageCalendar.zListItem(Action.A_RIGHTCLICK, Button.O_INSTANCE_MENU, Button.O_DECLINE_MENU, apptSubject);
		DialogConfirmationDeclineAppointment declineAppt = (DialogConfirmationDeclineAppointment) new DialogConfirmationDeclineAppointment(app, app.zPageCalendar);
		declineAppt.zPressButton(Button.B_NOTIFY_ORGANIZER);
		declineAppt.zPressButton(Button.B_YES);

		// ---------------- Verification at organizer & invitee side both -------------------------------------


		// Check that the organizer shows the attendee as "NEEDS ACTION" for series ---

		// Organizer: Search for the appointment (InvId)
		ZimbraAccount.AccountA().soapSend(
					"<SearchRequest xmlns='urn:zimbraMail' types='appointment' calExpandInstStart='"+ startUTC.addDays(-10).toMillis() +"' calExpandInstEnd='"+ endUTC.addDays(10).toMillis() +"'>"
				+		"<query>"+ apptSubject +"</query>"
				+	"</SearchRequest>");

		String organizerInvId = ZimbraAccount.AccountA().soapSelectValue("//mail:appt", "invId");

		// Get the appointment details
		ZimbraAccount.AccountA().soapSend(
					"<GetAppointmentRequest  xmlns='urn:zimbraMail' id='"+ organizerInvId +"'/>");

		String attendeeStatus = ZimbraAccount.AccountA().soapSelectValue("//mail:at[@a='"+ app.zGetActiveAccount().EmailAddress +"']", "ptst");

		// Verify attendee status shows as ptst=NE
		ZAssert.assertEquals(attendeeStatus, "NE", "Verify that the attendee shows as 'NEEDS ACTION'");


		// Check that the attendee showing status as "NEEDS ACTION" for series ---

		// Attendee: Search for the appointment (InvId)
		app.zGetActiveAccount().soapSend(
					"<SearchRequest xmlns='urn:zimbraMail' types='appointment' calExpandInstStart='"+ startUTC.addDays(-10).toMillis() +"' calExpandInstEnd='"+ endUTC.addDays(10).toMillis() +"'>"
				+		"<query>"+ apptSubject +"</query>"
				+	"</SearchRequest>");

		String attendeeInvId = app.zGetActiveAccount().soapSelectValue("//mail:appt", "invId");

		// Get the appointment details
		app.zGetActiveAccount().soapSend(
					"<GetAppointmentRequest  xmlns='urn:zimbraMail' id='"+ attendeeInvId +"'/>");

		String myStatus = app.zGetActiveAccount().soapSelectValue("//mail:at[@a='"+ app.zGetActiveAccount().EmailAddress +"']", "ptst");
		String exceptId = app.zGetActiveAccount().soapSelectValue("//mail:appt//mail:exceptId", "d");

		// Verify attendee status shows as ptst=NE
		ZAssert.assertEquals(myStatus, "NE", "Verify that the attendee shows as 'NEEDS ACTION'");
		ZAssert.assertEquals(exceptId, startUTC.toyyyyMMddTHHmmss(), "Verify that particular instance is declined properly");

		// Organizer: Search for the appointment response
		String inboxId = FolderItem.importFromSOAP(ZimbraAccount.AccountA(), FolderItem.SystemFolder.Inbox).getId();

		ZimbraAccount.AccountA().soapSend(
					"<SearchRequest xmlns='urn:zimbraMail' types='message'>"
				+		"<query>inid:"+ inboxId +" subject:("+ apptSubject +") content:" + "I won't attend on " + "</query>"
				+	"</SearchRequest>");

		String messageId = ZimbraAccount.AccountA().soapSelectValue("//mail:m", "id");
		ZAssert.assertNotNull(messageId, "Verify organizer gets email notification");
	}
}