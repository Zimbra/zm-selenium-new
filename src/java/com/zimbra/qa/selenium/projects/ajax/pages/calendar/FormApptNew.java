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
package com.zimbra.qa.selenium.projects.ajax.pages.calendar;

import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Keys;
import com.zimbra.qa.selenium.framework.items.*;
import com.zimbra.qa.selenium.framework.ui.*;
import com.zimbra.qa.selenium.framework.util.*;
import com.zimbra.qa.selenium.framework.util.staf.Stafpostqueue;
import com.zimbra.qa.selenium.projects.ajax.pages.AjaxPages;
import com.zimbra.qa.selenium.projects.ajax.pages.AutocompleteEntry;
import com.zimbra.qa.selenium.projects.ajax.pages.DialogWarning;
import com.zimbra.qa.selenium.projects.ajax.pages.AutocompleteEntry.Icon;

/**
 * The <code>FormApptNew<code> object defines a compose new appointment view in
 * the Zimbra Ajax client.
 * <p>
 * This class can be used to compose a new appointment.
 * <p>
 *
 * @author Matt Rhoades
 * @see http://wiki.zimbra.com/wiki/Testing:_Selenium:_ZimbraSelenium_Overview#Mail_Page
 */

public class FormApptNew extends AbsForm {

	public static String locatorValue;
	PageCalendar pageCal = new PageCalendar(MyApplication);

	public static class Locators {

		public static final String SubjectDisabled = "css=div[id^='APPT_COMPOSE_'] td[id$='_subject'] div[class='DwtInputField-focused DwtInputField-disabled DwtInputField']";
		public static final String ToDisabled = "css=div[id^='APPT_COMPOSE_'] td tr[id$='_forward_options'] div[class='ZButton ZPicker ZWidget ZHasText ZDisabled']";
		public static final String AttendeesDisabled = "css=div[id^='APPT_COMPOSE_'] td tr[id$='_attendeesContainer'] div[class='ZButton ZPicker ZWidget ZHasText ZDisabled']";
		public static final String OptionalDisabled = "css=div[id^='APPT_COMPOSE_'] td tr[id$='_optionalContainer'] div[class='ZButton ZPicker ZWidget ZHasText ZDisabled']";
		public static final String LocationDisabled = "css=div[id^='APPT_COMPOSE_'] td tr[id$='_forward_options'] div[class='ZButton ZPicker ZWidget ZHasText ZDisabled']";
		public static final String EquipmentDisabled = "css=div[id^='APPT_COMPOSE_'] td tr[id$='_resourcesContainer'] div[class='ZButton ZPicker ZWidget ZHasText ZDisabled']";
		public static final String DisplayDisabled = "css=div[id^='APPT_COMPOSE_'] td[id$='_showAsSelect'] div[class$='ZHasDropDown ZDisabled ZHasLeftIcon']";
		public static final String FolderDisabled = "css=div[id^='APPT_COMPOSE_'] td[id$='_folderSelect'] div[class$='ZHasDropDown ZDisabled ZHasLeftIcon']";
		public static final String PrivateDisabled = "css=div[id^='APPT_COMPOSE_'] td input[id$='_privateCheckbox'][type='checkbox'][disabled]";

		public static final String RepeatOptionsDisabled = "css=td[id$='_repeat_options'] div[id^='DWT'][class$='ZHasDropDown ZDisabled']";
		public static final String RepeatDescriptionDisabled = "css=div[id$='_repeatDesc'][class='DisabledText']";

		public static final String CustomizeLink = "//div[text() = 'Customize']";
		public static final String ShowOptionalLink = "css=div[id$='_show_optional']";
		public static final String ShowEquipmentLink = "css=div[id$='_show_resources']";
		public static final String ConfigureLink = "css=div[class='FakeAnchor']:contains('Configure')";
		public static final String SuggestAtimeLink = "css=div[id$='_suggest_time']:contains('Suggest a time')";
		public static final String SuggestATime10AM = "css=div[id$='_suggest_view'] td:contains(10:00 AM)";
		public static final String SuggestALocationLink = "css=div[id$='_suggest_location']:contains('Suggest a location')";
		public static String SuggestedLocations = "css=div[id='zv__CSLP'] div[class$='ZmLocationSuggestion']:contains('"
				+ locatorValue + "')";
		public static final String ShowSchedulerLink = "css=div[id$='_scheduleButton']:contains('Show')";
		public static final String HideSchedulerLink = "css=div[id$='_scheduleButton']:contains('Hide')";
		public static final String SelectFirstFreeTimeFromSuggestTimePane = "css=div[id$='_suggest_view'] table:nth-child(2) tbody tr td:nth-child(2)";
		public static final String ShowTimesAnywayLink = "css=div[id$='_suggest_view'] div[class='NoSuggestions'] span[id$='showall']";

		public static final String Button_Send = "css=div[id^='ztb__APPT-'] td[id$='_SEND_INVITE_title']";
		public static final String Button_Save = "css=div[id^='ztb__APPT-'] td[id$='_SAVE_title']";
		public static final String Button_SaveAndClose = "css=div[id^='ztb__APPT-'] td[id$='_SAVE_title']";
		public static final String Button_Close = "css=div[id^='ztb__APPT-'] td[id$='_CANCEL_title']";
		public static final String Button_Attach = "css=div[id^='ztb__APPT-'] td[id^='zb__APPT-1__ATTACHMENT_']";

		public static final String OptionsDropdown = "css=div[id$='__COMPOSE_OPTIONS'] td[id$='COMPOSE_OPTIONS_title']";
		public static final String SuggestionPreferencesMenu = "css=div[class='DwtMenu ZHasCheck ZHasIcon'] td[id$='_title']:contains('Suggestion Preferences')";
		public static final String FormatAsHTMLMenu = "css=div[class='DwtMenu ZHasCheck ZHasIcon'] td[id$='_FORMAT_HTML_title']:contains('Format As HTML')";
		public static final String FormatAsPlainTextMenu = "css=div[class='DwtMenu ZHasCheck ZHasIcon'] td[id$='_FORMAT_TEXT_title']:contains('Format As Plain Text')";
		public static final String OnlyIncludeMyWorkingHoursCheckBox = "css=input[id$='_my_working_hrs_pref']";
		public static final String OnlyIncludeOtherAttendeeCheckBox = "css=input[id$='_others_working_hrs_pref']";
		public static final String NameLocationPreferencesField = "css=div[class='ZmTimeSuggestionPrefDialog'] table[id$='_locationpref'] input[id$='_name']";
		public static final String OKButtonSuggestionPreferencesDialog = "css=div[class='ZmTimeSuggestionPrefDialog'] td[id$='_button2_title']";
		public static final String CancelButtonSuggestionPreferencesDialog = "css=div[class='ZmTimeSuggestionPrefDialog'] td[id$='_button1_title']";

		public static final String NoneMenuItem = "css=div[id*='_Menu'] div[id^='NON'] td[id$='title']:contains('None')";
		public static final String NoneButton = "css=div[id$='_repeatSelect'] td[id$='_select_container'] td[id$='_title']";
		public static final String EveryDayMenuItem = "css=div[id$='Menu_1_option_2'] td[id$='_Menu_1_option_2_title']:contains('Every Day')";
		public static final String EveryDayButton = "css=td[id$='_title']:contains('Every Day')";
		public static final String EveryWeekMenuItem = "css=[id$='Menu_1_option_3'] td[id$='_Menu_1_option_3_title']:contains('Every Week')";
		public static final String EveryWeekButton = "css=td[id$='_title']:contains('Every Week')";
		public static final String EveryMonthMenuItem = "css=div[id$='Menu_1_option_4'] td[id$='_Menu_1_option_4_title']:contains('Every Month')";
		public static final String EveryMonthButton = "css=td[id$='_title']:contains('Every Month')";
		public static final String EveryYearMenuItem = "css=div[id$='Menu_1_option_5'] td[id$='_Menu_1_option_5_title']:contains('Every Year')";
		public static final String EveryYearButton = "css=td[id$='_title']:contains('Every Year')";
		public static final String CustomMenuItem = "css=div[id$='Menu_1_option_6'] td[id$='_Menu_1_option_6_title']:contains('Custom')";
		public static final String CustomButton = "css=td[id$='_title']:contains('Custom')";

		public static final String EveryDayRadioButton = "css=div[id^='REPEAT_DAILY_DIV'] input[id^='DAILY_DEFAULT']";
		public static final String EveryWeekdayRadioButton = "css=div[id^='REPEAT_DAILY_DIV'] input[id^='DAILY_WEEKDAY']";
		public static final String EveryXdaysRadioButton = "css=div[id^='REPEAT_DAILY_DIV'] input[id^='DAILY_FIELD_RADIO']";
		public static final String EveryXdaysEditField = "css=input[id^='RECUR_DAILY_FIELD']";

		public static final String EveryXRadioButton = "css=div[id^='REPEAT_WEEKLY_DIV'] input[id^='WEEKLY_DEFAULT']";
		public static final String EveryXDropdown = "css=div[id^='REPEAT_WEEKLY_DIV'] td[id^='WEEKLY_SELECT'] td[id$='_title']";
		public static final String EveryXweeksOnRadioButton = "css=div[id^='REPEAT_WEEKLY_DIV'] input[id^='WEEKLY_FIELD_RADIO']";
		public static final String EveryXweeksOnEditField = "css=div[id^='REPEAT_WEEKLY_DIV'] input[id^='RECUR_WEEKLY_FIELD']";
		public static final String SundayCheckBox = "css=div[id^='REPEAT_WEEKLY_DIV'] input[id^='WEEKLY_CHECKBOX_NAME_1']";
		public static final String MondayCheckBox = "css=div[id^='REPEAT_WEEKLY_DIV'] input[id^='WEEKLY_CHECKBOX_NAME_2']";
		public static final String TuesdayCheckBox = "css=div[id^='REPEAT_WEEKLY_DIV'] input[id^='WEEKLY_CHECKBOX_NAME_3']";
		public static final String WednesdayCheckBox = "css=div[id^='REPEAT_WEEKLY_DIV'] input[id^='WEEKLY_CHECKBOX_NAME_4']";
		public static final String ThursdayCheckBox = "css=div[id^='REPEAT_WEEKLY_DIV'] input[id^='WEEKLY_CHECKBOX_NAME_5']";
		public static final String FridayCheckBox = "css=div[id^='REPEAT_WEEKLY_DIV'] input[id^='WEEKLY_CHECKBOX_NAME_6']";
		public static final String SaturdayCheckBox = "css=div[id^='REPEAT_WEEKLY_DIV'] input[id^='WEEKLY_CHECKBOX_NAME_7']";

		public static final String DayXofEveryYmonthsRadioButton = "css=div[id^='REPEAT_MONTHLY_DIV'] input[id^='MONTHLY_DEFAULT']";
		public static final String TheXYofEveryZmonthsRadioButton = "css=div[id^='REPEAT_MONTHLY_DIV'] input[id^='MONTHLY_FIELD_RADIO']";

		public static final String EveryYearOnXYRadioButton = "css=div[id^='REPEAT_YEARLY_DIV'] input[id^='YEALY_DEFAULT']";
		public static final String TheXYofEveryZRadioButton = "css=div[id^='REPEAT_YEARLY_DIV'] input[id^='YEARLY_FIELD_RADIO']";

		public static final String NoEndDateRadioButton = "css=div[id$='_content'] td input[id^='NO_END_DATE_RADIO']";
		public static final String EndAfterXoccurrencesRadioButton = "css=div[id$='_content'] td input[id^='END_AFTER_RADIO']";
		public static final String EndAfterXoccurrencesEditField = "css=div[id$='_content'] td input[id^='RECUR_END_INTERVAL_FIELD']";
		public static final String EndByXDateRadioButton = "css=div[id$='_content'] td input[id^='END_BY_RADIO']";
		public static final String EndByXDateEditField = "css=div[id$='_content'] td input[id^='RECUR_END_BY_FIELD']";

		public static final String DeleteZimletContextMenu = "css=div[id^='POPUP_'] td[id='DELETE_title']";
		public static final String EditZimletContextMenu = "css=div[id^='POPUP_'] td[id='EDIT_title']";
		public static final String ExpandZimletContextMenu = "css=div[id^='POPUP_'] td[id='EXPAND_title']";
		public static final String AddToContactsZimletContextMenu = "css=div[id^='POPUP_'] td[id='CONTACT_title']";

		public static final String SendUpdatesToAddedRemovedRadioButton = "css=div[id='SEND_NOTIFY_DIALOG'] div[id='SEND_NOTIFY_DIALOG_content']>table tbody tr:nth-child(1) input";
		public static final String SendUpdatesToAllRadioButton = "css=div[id='SEND_NOTIFY_DIALOG'] div[id='SEND_NOTIFY_DIALOG_content']>table tbody tr:nth-child(2) input";

		public static final String Ok_changes = "css=td[id='CHNG_DLG_ORG_1_button2_title']";
		public static final String Cancel_changes = "css=td[id='CHNG_DLG_ORG_1_button1_title']";
		public static final String AddLocation = "css=td[id$='_title']:contains('Location:')";
		public static final String addEquipment = "css=td[id$='_title']:contains('Equipment:')";

		public static final String AddAttendees = "css=td[id$='_title']:contains('Attendees:')";
		public static final String AddOptiponalAttendees = "css=td[id$='_title']:contains('Optional:')";
		public static final String EquipmentName = "css=div[class='DwtDialog'] div[id='ZmAttendeePicker_EQUIPMENT_content'] table tr td:nth-child(2) input ";
		public static final String EquipmentDescription = "css=div[class='DwtDialog'] div[id='ZmAttendeePicker_EQUIPMENT_content'] table tr:nth-child(3) td:nth-child(2) input";
		public static final String ToolbarOptions = "css=td[id='zb__APPT-1__COMPOSE_OPTIONS_title']";
		public static final String RequestResponse = "css=td[id$='_title']:contains('Request Responses')";
		public static final String ConflictResourceNote = "css= div[id$='_location_status']:contains('One or more locations are not available at the selected time')";
		public static final String BrowseAttachment = "css=tr[id$='_attachment_container'] input[name='__calAttUpload__']";

		public static final String AttendeeField = "css=input[id$='_person_input']";
		public static final String AttendeeField_Edge = "css=textarea[id$='_person_input']";
		public static final String LocationField = "css=input[id$='_location_input']";
		public static final String LocationField_Edge = "css=textarea[id$='_location_input']";
		public static final String EquipmentField = "css=input[id$='_resourcesData_input']";
		public static final String EquipmentField_Edge = "css=textarea[id$='_resourcesData_input']";
		public static final String SMSCheckBox = "css=input[id$='_reminderDeviceEmailCheckbox']";

		public static final String zBoldButton = "css=i[class='mce-ico mce-i-bold']";
		public static final String zItalicButton = "css=i[class='mce-ico mce-i-italic']";
		public static final String zTextColorDropdown = "css=div[class='mce-widget mce-btn mce-btn-small mce-colorbutton mce-first'] [class='mce-open']";
		public static final String zTextBackgroundColorDropdown = "css=div[class='mce-widget mce-btn mce-btn-small mce-colorbutton mce-last'] [class='mce-open']";
		public static final String zTextColorRed = "css=div[data-mce-color='#FF0000']";
		public static final String zTextColorTransparent = "css=div[data-mce-color='transparent']";
		public static final String zTextBackgroundColorGreen = "css=div[class='mce-container mce-panel mce-floatpanel mce-popover mce-bottom mce-start']:not([style*='display']) [data-mce-color='#008000']";
		public static final String zTextBackgroundColorTransparent = "css=div[class='mce-container mce-panel mce-floatpanel mce-popover mce-bottom mce-start']:not([style*='display']) [data-mce-color='transparent']";
		public static final String zPlainTextBodyField = "css=div[class='ZmHtmlEditor'] [class='ZmHtmlEditorTextArea']";
	}

	public static class Field {

		public static final Field Subject = new Field("Subject");
		public static final Field To = new Field("To");
		public static final Field From = new Field("From");
		public static final Field Attendees = new Field("Attendees");
		public static final Field Optional = new Field("Optional");
		public static final Field Location = new Field("Location");
		public static final Field Equipment = new Field("Equipment");
		public static final Field StartDate = new Field("StartDate");
		public static final Field StartTime = new Field("StartTime");
		public static final Field EndDate = new Field("EndDate");
		public static final Field EndTime = new Field("EndTime");
		public static final Field AllDay = new Field("AllDay");
		public static final Field Repeat = new Field("Repeat");
		public static final Field Display = new Field("Display");
		public static final Field CalendarFolder = new Field("CalendarFolder");
		public static final Field Private = new Field("Private");
		public static final Field Reminder = new Field("Reminder");
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

	public FormApptNew(AbsApplication application) {
		super(application);

		logger.info("new " + FormApptNew.class.getCanonicalName());

	}

	@Override
	public String myPageName() {
		return (this.getClass().getName());
	}

	@Override
	public void zSubmit() throws HarnessException {
		String multiLocator = "css=div[id^='ztb__APPT-1'] td[id$='_SEND_INVITE_title']";
		String locator = "css=div[id$=_SEND_INVITE]";
		if (this.sIsVisible(multiLocator) || this.sIsVisible(locator)) {
			zToolbarPressButton(Button.B_SEND);
		} else {
			zToolbarPressButton(Button.B_SAVE);
		}
	}

	public void zSubmitWithResources() throws HarnessException {
		String multiLocator = "css=div[id^='ztb__APPT-1'] td[id$='_SEND_INVITE_title']";
		String locator = "css=div[id$=_SEND_INVITE]";
		if (this.sIsVisible(multiLocator) || this.sIsVisible(locator)) {
			zToolbarPressButton(Button.B_SEND);
		} else {
			zToolbarPressButton(Button.B_SAVE);
		}
		SleepUtil.sleepVeryLong();
	}

	public String zGetSuggestedLocation(String apptLocation) throws HarnessException {
		return "css=div[id='zv__CSLP'] div[id^='zli__CSLP__']:contains('" + apptLocation + "')";
	}

	public String zGetLocationVaueFromPopUp(String apptLocation) throws HarnessException {
		return "css=div[id^='POPUP_DWT'] td[id^='" + apptLocation + "']";
	}

	public Boolean zIsLocationExistsInSuggestPane(String apptLocation) throws HarnessException {
		return sIsElementPresent("css=div[id='zv__CSLP'] div[id^='zli__CSLP__']:contains('" + apptLocation + "')");
	}

	public void zAddRequiredAttendeeFromScheduler(String attendee, int keyEvent) throws HarnessException {
		if (sIsElementPresent(Locators.ShowSchedulerLink)) {
			zToolbarPressButton(Button.B_SHOW);
			SleepUtil.sleepSmall();
			this.zType("css=td[id$='_scheduler'] td[id$='_NAME_'] input", attendee);
			this.sClickAt("css=td[id$='_scheduler'] td[id$='_NAME_'] input", "");
			SleepUtil.sleepSmall();
			pageCal.zKeyboard.zTypeKeyEvent(keyEvent);

		} else {
			this.zType("css=td[id$='_scheduler'] td[id$='_NAME_'] input", attendee);
			this.sClickAt("css=td[id$='_scheduler'] td[id$='_NAME_'] input", "");
			SleepUtil.sleepSmall();
			pageCal.zKeyboard.zTypeKeyEvent(keyEvent);
		}
		SleepUtil.sleepMedium();
	}

	public void zAddOptionalAttendeeFromScheduler(String attendee, int keyEvent) throws HarnessException {
		zToolbarPressButton(Button.B_SHOW);
		SleepUtil.sleepSmall();
		this.sClickAt("css=td[id$='_scheduler'] td[id$='_SELECT_'] td[id$='_dropdown']", "");
		this.sClickAt("css=div[class='DwtMenu ZHasIcon'] td[id$='_title']:contains('Optional Attendee')", "");
		this.zType("css=td[id$='_scheduler'] td[id$='_NAME_'] input", attendee);
		this.sClickAt("css=td[id$='_scheduler'] td[id$='_NAME_'] input", "");
		SleepUtil.sleepSmall();
		pageCal.zKeyboard.zTypeKeyEvent(keyEvent);
		SleepUtil.sleepMedium();
	}

	public void zAddLocationFromScheduler(String location, int keyEvent) throws HarnessException {
		SleepUtil.sleepMedium();
		zToolbarPressButton(Button.B_SHOW);
		SleepUtil.sleepSmall();
		this.sClickAt("css=td[id$='_scheduler'] td[id$='_SELECT_'] td[id$='_dropdown']", "");
		this.sClickAt("css=div[class='DwtMenu ZHasIcon'] td[id$='_title']:contains('Location')", "");
		this.zType("css=td[id$='_scheduler'] td[id$='_NAME_'] input", location);
		this.sClickAt("css=td[id$='_scheduler'] td[id$='_NAME_'] input", "");
		SleepUtil.sleepSmall();
		pageCal.zKeyboard.zTypeKeyEvent(keyEvent);
		SleepUtil.sleepMedium();
	}

	public void zAddEquipmentFromScheduler(String equipment, int keyEvent) throws HarnessException {
		zToolbarPressButton(Button.B_SHOW);
		SleepUtil.sleepSmall();
		this.sClickAt("css=td[id$='_scheduler'] td[id$='_SELECT_'] td[id$='_dropdown']", "");
		this.sClickAt("css=div[class='DwtMenu ZHasIcon'] td[id$='_title']:contains('Equipment')", "");
		this.zType("css=td[id$='_scheduler'] td[id$='_NAME_'] input", equipment);
		this.sClickAt("css=td[id$='_scheduler'] td[id$='_NAME_'] input", "");
		SleepUtil.sleepSmall();
		pageCal.zKeyboard.zTypeKeyEvent(keyEvent);
		SleepUtil.sleepMedium();
	}

	public String zGetApptSubject() throws HarnessException {
		return sGetValue("css=td[id$='_subject'] input[id$='_subject_input']");
	}

	public String zGetAttendeeFromScheduler() throws HarnessException {
		return sGetValue("css=td[id$='_scheduler'] td[id$='_NAME_'] input");
	}

	public String zGetStartDate() throws HarnessException {
		return sGetValue("css=input[id$='startDateField']");
	}

	public String zGetEndDate() throws HarnessException {
		return sGetValue("css=input[id$='endDateField']");
	}

	public Boolean zVerifyRequiredAttendee(String attendee) throws HarnessException {
		return sIsElementPresent("css=td[id$='_person'] span:contains('" + attendee + "')");
	}

	public Boolean zVerifyOptionalAttendee(String attendee) throws HarnessException {
		return sIsElementPresent("css=td[id$='_optional'] span:contains('" + attendee + "')");
	}

	public Boolean zVerifyLocation(String location) throws HarnessException {
		return sIsElementPresent("css=td[id$='_location'] span:contains('" + location + "')");
	}

	public Boolean zVerifyEquipment(String equipment) throws HarnessException {
		return sIsElementPresent("css=td[id$='_resourcesData'] span:contains('" + equipment + "')");
	}

	public String zGetApptBodyValue() throws HarnessException {
		return sGetValue("css=div[class='ZmHtmlEditor'] textarea[class='ZmHtmlEditorTextArea']");
	}

	public Boolean zVerifyMeetingInPastWarning() throws HarnessException {
		return sIsElementPresent("css=div[id='zcs1_meetingInPastWarningMessageText']");
	}

	public String zGetApptBodyHtml() throws HarnessException {
		String bodyText;
		this.sSelectFrame("css=iframe[id$='ZmHtmlEditor1_body_ifr']");
		bodyText = sGetText("css=body[id='tinymce']");
		this.sSelectFrame("relative=top");
		return bodyText;
	}

	public void zRemoveAttendee(String attendee) throws HarnessException {
		SleepUtil.sleepSmall();
		this.sRightClickAt("css=td[id$='_person'] span:contains('" + attendee + "')", "");
		SleepUtil.sleepSmall();
		this.sClickAt(Locators.DeleteZimletContextMenu, "");
	}

	public void zRemoveLocation(String location) throws HarnessException {
		SleepUtil.sleepSmall();
		this.sRightClickAt("css=td[id$='_location'] span:contains('" + location + "')", "");
		this.sClickAt(Locators.DeleteZimletContextMenu, "");
		if (ConfigProperties.getStringProperty("browser").contains("edge")) {
			this.sClickAt("css=textarea[id$='_location_input']", "");
		} else {
			this.sClickAt("css=input[id$='_location_input']", "");
		}
		SleepUtil.sleepMedium();
	}

	public void zRemoveEquipment(String equipment) throws HarnessException {
		SleepUtil.sleepSmall();
		this.sRightClickAt("css=td[id$='resourcesData'] span:contains('" + equipment + "')", "");
		this.sClickAt(Locators.DeleteZimletContextMenu, "");
		this.sClickAt("css=td[id$='resourcesData']", "");
		SleepUtil.sleepMedium();
	}

	public void zVerifyDisabledControlInProposeNewTimeUI() throws HarnessException {
		SleepUtil.sleepMedium();
		ZAssert.assertTrue(this.sIsElementPresent(Locators.ToDisabled),
				"Verify to field is disabled when attendee propose new time");
		ZAssert.assertTrue(this.sIsElementPresent(Locators.SubjectDisabled),
				"Verify subject field is disabled when attendee propose new time");
		ZAssert.assertTrue(this.sIsElementPresent(Locators.AttendeesDisabled),
				"Verify attendees field is disabled when attendee propose new time");
		ZAssert.assertTrue(this.sIsElementPresent(Locators.OptionalDisabled),
				"Verify optional field is disabled when attendee propose new time");
		ZAssert.assertTrue(this.sIsElementPresent(Locators.LocationDisabled),
				"Verify location field is disabled when attendee propose new time");
		ZAssert.assertTrue(this.sIsElementPresent(Locators.EquipmentDisabled),
				"Verify equipment field is disabled while attendee propose new time");
		ZAssert.assertTrue(this.sIsElementPresent(Locators.DisplayDisabled),
				"Verify display field is disabled when attendee propose new time");
		ZAssert.assertTrue(this.sIsElementPresent(Locators.FolderDisabled),
				"Verify folder field is disabled when attendee propose new time");
		ZAssert.assertTrue(this.sIsElementPresent(Locators.PrivateDisabled),
				"Verify private field is disabled when attendee propose new time");
	}

	public void zVerifyDisabledControlInOpenInstance() throws HarnessException {
		ZAssert.assertTrue(this.sIsElementPresent(Locators.RepeatOptionsDisabled),
				"Verify repeat dropdown remains disabled");
		ZAssert.assertTrue(this.sIsElementPresent(Locators.RepeatDescriptionDisabled),
				"Verify repeat description remains disabled");
	}

	public AbsPage zToolbarPressButton(Button button) throws HarnessException {

		logger.info(myPageName() + " zToolbarPressButton(" + button + ")");

		tracer.trace("Click button " + button);

		if (button == null)
			throw new HarnessException("Button cannot be null!");

		AbsPage page = null;
		String locator = null;

		SleepUtil.sleepSmall();

		if (button == Button.B_SEND) {

			if (sIsElementPresent("css=div[id^='ztb__APPT-2'] td[id$='_SEND_INVITE_title']")) {
				locator = "css=div[id^='ztb__APPT-2'] td[id$='_SEND_INVITE_title']";
			} else {
				locator = Locators.Button_Send;
			}
			page = new DialogConfirmRemoveAllExceptions(this.MyApplication, pageCal);

		} else if (button == Button.B_SAVE) {

			locator = Locators.Button_Save;
			page = null;

		} else if (button == Button.B_SAVEANDCLOSE) {

			locator = Locators.Button_SaveAndClose;
			page = null;

		} else if (button == Button.B_ATTACH) {

			locator = Locators.Button_Attach;
			page = null;

		} else if (button == Button.B_BROWSE) {

			locator = Locators.BrowseAttachment;

			this.sClickAt(locator, "0,0");
			SleepUtil.sleepSmall();

			page = null;

		} else if (button == Button.B_CLOSE) {

			locator = Locators.Button_Close;
			page = null;

		} else if (button == Button.B_SUGGESTATIME) {

			locator = Locators.SuggestAtimeLink;
			page = null;

		} else if (button == Button.B_FIRST_TIME_SUGGESTION) {

			if (this.sIsElementPresent(Locators.SelectFirstFreeTimeFromSuggestTimePane) == true) {
				this.sClickAt(Locators.SelectFirstFreeTimeFromSuggestTimePane, "");

			} else if (this.sIsElementPresent(Locators.ShowTimesAnywayLink) == true) {
				this.sClickAt(Locators.ShowTimesAnywayLink, "");
				this.sClickAt(Locators.SelectFirstFreeTimeFromSuggestTimePane, "");
			}

			return null;

		} else if (button == Button.B_SUGGESTALOCATION) {

			locator = Locators.SuggestALocationLink;
			page = null;

		} else if (button == Button.B_10AM) {

			locator = Locators.SuggestATime10AM;
			page = null;

		} else if (button == Button.B_SHOW) {

			locator = Locators.ShowSchedulerLink;
			this.sClickJavaScript(locator);
			SleepUtil.sleepMedium();
			return (page);

		} else if (button == Button.B_SHOW_TIMES_ANYWAY) {

			locator = Locators.ShowTimesAnywayLink;
			page = null;

			if (this.sIsElementPresent(locator) == true) {
				this.sClickAt(Locators.ShowTimesAnywayLink, "");
				SleepUtil.sleepMedium();
				return null;
			}

		} else if (button == Button.B_LOCATION) {

			locator = Locators.AddLocation;
			page = new DialogFindLocation(this.MyApplication, pageCal);

		} else if (button == Button.B_EQUIPMENT) {

			locator = Locators.addEquipment;
			page = new DialogFindLocation(this.MyApplication, pageCal);

		} else if (button == Button.B_TO) {

			locator = Locators.AddAttendees;
			page = new DialogFindLocation(this.MyApplication, pageCal);

		} else if (button == Button.B_OPTIONAL) {

			locator = Locators.AddOptiponalAttendees;
			page = new DialogFindLocation(this.MyApplication, pageCal);

		} else if (button == Button.B_SHOW_OPTIONAL) {

			locator = Locators.ShowOptionalLink;
			this.sClickJavaScript(locator);
			SleepUtil.sleepMedium();
			return (page);

		} else if (button == Button.B_SHOW_EQUIPMENT) {

			locator = Locators.ShowEquipmentLink;
			this.sClickJavaScript(locator);
			SleepUtil.sleepMedium();
			return (page);

		} else {
			throw new HarnessException("no logic defined for button " + button);
		}

		if (locator == null)
			throw new HarnessException("locator was null for button " + button);

		this.sClickAt(locator, "");
		this.zWaitForBusyOverlay();

		if (button == Button.B_SEND || button == Button.B_SAVE || button == Button.B_SAVEANDCLOSE) {
			Stafpostqueue sp = new Stafpostqueue();
			sp.waitForPostqueue();
		}

		SleepUtil.sleepMedium();

		return (page);

	}

	public AbsPage zPressButton(Button button, String value) throws HarnessException {

		logger.info(myPageName() + " zPressButton(" + button + ")");
		SleepUtil.sleepMedium();

		tracer.trace("Click button " + button);

		if (button == null)
			throw new HarnessException("Button cannot be null!");

		AbsPage page = null;
		String locator = null;

		if (button == Button.B_SUGGESTEDLOCATION) {

			locator = zGetSuggestedLocation(value);
			page = null;

		} else if (button == Button.B_LOCATIONMENU) {

			locator = zGetLocationVaueFromPopUp(value);

			this.sClickAt("css=div[id$='_suggest_view'] table:nth-child(2) tbody tr td:nth-child(3) span", "0,0");
			SleepUtil.sleepSmall();

			page = null;

		} else {
			throw new HarnessException("no logic defined for button " + button);
		}

		if (locator == null)
			throw new HarnessException("locator was null for button " + button);

		this.sClickAt(locator, "");
		SleepUtil.sleepMedium();

		this.zWaitForBusyOverlay();

		return (page);
	}

	public void zVerifySpecificTimeNotExists(String time) throws HarnessException {
		PageCalendar pageCal = new PageCalendar(MyApplication);
		String[] timeArray = time.split(",");
		for (int i = 0; i <= timeArray.length - 1; i++) {
			ZAssert.assertEquals(false,
					pageCal.sIsElementPresent("css=div[id$='_suggest_view'] td:contains('" + timeArray[i] + "')"),
					"Verify busy timeslots are not showing while suggesting a time");
		}
	}

	public void zVerifySpecificTimeExists(String time) throws HarnessException {
		PageCalendar pageCal = new PageCalendar(MyApplication);
		String[] timeArray = time.split(",");
		for (int i = 0; i <= timeArray.length - 1; i++) {
			ZAssert.assertEquals(true,
					pageCal.sIsElementPresent("css=div[id$='_suggest_view'] td:contains('" + timeArray[i] + "')"),
					"Verify free timeslots are showing while suggesting a time");
		}
	}

	public void zVerifySpecificLocationNotExists(String location) throws HarnessException {
		PageCalendar pageCal = new PageCalendar(MyApplication);
		String[] locationArray = location.split(",");
		for (int i = 0; i <= locationArray.length - 1; i++) {
			ZAssert.assertEquals(false,
					pageCal.sIsElementPresent("css=div[id$='_suggest_view'] td:contains('" + locationArray[i] + "')"),
					"Verify busy timeslot are not showing while suggesting a time");
		}
	}

	public void zSetTomorrowDate() throws HarnessException {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 2);
		this.zKeyboard.zSelectAll("css=input[id$='_startDateField']");
		SleepUtil.sleepVerySmall();
		this.zKeyboard.zTypeKeyEvent(KeyEvent.VK_DELETE);
		SleepUtil.sleepVerySmall();
		this.zTypeKeys("css=input[id$='_startDateField']", new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
	}

	public String zGetTomorrowDate() throws HarnessException {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 2);
		return new SimpleDateFormat("yyyyMMdd").format(c.getTime());
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

		if (pulldown == Button.B_OPTIONS) {
			pulldownLocator = Locators.OptionsDropdown;

		} else {
			throw new HarnessException("no logic defined for pulldown " + pulldown);
		}

		if (option == Button.O_SUGGESTION_PREFERENCES) {
			optionLocator = Locators.SuggestionPreferencesMenu;
		}

		else if (option == Button.O_FORMAT_AS_HTML) {
			optionLocator = Locators.FormatAsHTMLMenu;
		}

		else if (option == Button.O_FORMAT_AS_PLAIN_TEXT) {
			optionLocator = Locators.FormatAsPlainTextMenu;
		}

		else {
			throw new HarnessException("no logic defined for option " + pulldown);
		}

		if (pulldownLocator != null) {

			if (!this.sIsElementPresent(pulldownLocator)) {
				throw new HarnessException("Button " + pulldown + " option " + option + " pulldownLocator "
						+ pulldownLocator + " not present!");
			}

			this.sClickAt(pulldownLocator, "");

			this.zWaitForBusyOverlay();

			if (optionLocator != null) {

				if (option == Button.O_SUGGESTION_PREFERENCES) {
					optionLocator = Locators.SuggestionPreferencesMenu;
				}

				else if (option == Button.O_FORMAT_AS_HTML) {
					optionLocator = Locators.FormatAsHTMLMenu;
				}

				else if (option == Button.O_FORMAT_AS_PLAIN_TEXT) {
					optionLocator = Locators.FormatAsPlainTextMenu;
				}

				else {
					throw new HarnessException("no logic defined for pulldown " + pulldown);
				}

				if (!this.sIsElementPresent(optionLocator)) {
					throw new HarnessException("Button " + pulldown + " option " + option + " optionLocator "
							+ optionLocator + " not present!");
				}

				this.sClickAt(optionLocator, "");

				this.zWaitForBusyOverlay();
				page = new DialogWarning(DialogWarning.DialogWarningID.SwitchToTextComposeAppointment,
						this.MyApplication, ((AjaxPages) this.MyApplication).zPageCalendar);

			}

		}
		return (page);
	}

	public void zSetFromIdentity(String value) throws HarnessException {
		logger.info(myPageName() + " zSetFrom(" + value + ")");

		String pulldownLocator = "css=td[id$='_identity'] td[id$='_dropdown']";
		String optionLocator = "css=td[id$='_title']:contains(" + value + ")";

		if (pulldownLocator != null) {

			if (!this.sIsElementPresent(pulldownLocator)) {
				throw new HarnessException("pulldownLocator not present! " + pulldownLocator);
			}

			this.sClick(pulldownLocator);

			this.zWaitForBusyOverlay();

			if (optionLocator != null) {

				if (!this.sIsElementPresent(optionLocator)) {
					throw new HarnessException("optionLocator not present! " + optionLocator);
				}

				this.sClick(optionLocator);

				this.zWaitForBusyOverlay();

			}
		}
	}

	public void zFillField(Field field, ZDate value) throws HarnessException {

		String stringFormat;
		if (field == Field.StartDate || field == Field.EndDate) {
			stringFormat = value.toMM_DD_YYYY();
		} else if (field == Field.StartTime || field == Field.EndTime) {
			stringFormat = value.tohh_mm_aa();
		} else {
			throw new HarnessException("zFillField() not implemented for field: " + field);
		}

		zFillField(field, stringFormat);
	}

	public void zFillField(Field field) throws HarnessException {

		tracer.trace("Set " + field);

		String locator = null;

		if (field == Field.AllDay) {
			locator = "css=input[id$='_allDayCheckbox']";

		} else if (field == Field.Private) {
			locator = "css=input[id$='_privateCheckbox']";

		} else {
			throw new HarnessException("not implemented for field " + field);
		}

		if (!this.sIsElementPresent(locator))
			throw new HarnessException("Field is not present field=" + field + " locator=" + locator);

		this.sClick(locator);
		this.zWaitForBusyOverlay();

	}

	public void zFillField(Field field, String value) throws HarnessException {

		tracer.trace("Set " + field + " to " + value);

		String locator = null;
		String isRepeat = null;

		// subject
		if (field == Field.Subject) {

			locator = "css=div[id^='APPT_COMPOSE_'] td[id$='_subject'] input";

		} else if (field == Field.From) {

			zSetFromIdentity(value);
			return;

		} else if (field == Field.To) {

			if (ConfigProperties.getStringProperty("browser").contains("edge")) {
				locator = "css=textarea[id$='_to_control_input']";
			} else {
				locator = "css=input[id$='_to_control_input']";
			}

			// attendees
		} else if (field == Field.Attendees) {

			if (ConfigProperties.getStringProperty("browser").contains("edge")) {
				locator = "css=textarea[id$='_person_input']";
			} else {
				locator = "css=input[id$='_person_input']";
			}

			// optional
		} else if (field == Field.Optional) {

			if (ConfigProperties.getStringProperty("browser").contains("edge")) {
				locator = "css=textarea[id$='_optional_input']";
			} else {
				locator = "css=input[id$='_optional_input']";
			}

			// location
		} else if (field == Field.Location) {

			if (ConfigProperties.getStringProperty("browser").contains("edge")) {
				locator = "css=textarea[id$='_location_input']";
			} else {
				locator = "css=input[id$='_location_input']";
			}

			// equipment
		} else if (field == Field.Equipment) {

			if (ConfigProperties.getStringProperty("browser").contains("edge")) {
				locator = "css=textarea[id$='_resourcesData_input']";
			} else {
				locator = "css=input[id$='_resourcesData_input']";
			}

			// start date
		} else if (field == Field.StartDate) {

			locator = "css=input[id$='_startDateField']";

			// start time
		} else if (field == Field.StartTime) {

			locator = "css=td[id$='_startTimeSelect'] td[id$='_timeSelectInput'] input";

			// end date
		} else if (field == Field.EndDate) {

			locator = "css=input[id$='_endDateField']";

			// end time
		} else if (field == Field.EndTime) {

			locator = "css=td[id$='_endTimeSelect'] td[id$='_timeSelectInput'] input";

			// display
		} else if (field == Field.Display) {

			locator = "css=td[id$='_showAsSelect'] input";

			// calendar folder
		} else if (field == Field.CalendarFolder) {

			locator = "css=td[id$='_folderSelect'] td[id$='_select_container']";
			this.sClickAt(locator, "");

			value = "css=div[id*='_Menu_'] td[id$='_title']:contains('" + value + "')";
			this.sClickAt(value, "");

			return;

			// repeat
		} else if (field == Field.Repeat) {

			isRepeat = value;
			locator = "css=div[id$='_repeatSelect'] td[id$='_dropdown'] div";

			// body
		} else if (field == Field.Body) {

			SleepUtil.sleepMedium();

			int frames = this.sGetCssCount("css=iframe");
			logger.info("Body: # of frames: " + frames);

			// Plain text
			locator = "css=textarea[class='ZmHtmlEditorTextArea']";
			if (this.sIsVisible(locator)) {
				this.sFocus(locator);
				this.sClick(locator);
				this.zWaitForBusyOverlay();
				this.sType(locator, value);
				return;

			// HTML compose
			} else if (frames >= 1) {

				try {

					if (this.sIsElementPresent("css=iframe[id$='ZmHtmlEditor1_body_ifr']")) {
						locator = "css=body[id='tinymce']";
						this.sSelectFrame("css=div[class='ZmApptComposeView'] div[id$='_notes'] iframe[id$='_body_ifr']");
						this.sClick(locator);
						this.sFocus(locator);
						this.sType(locator, value);

					} else {
						throw new HarnessException("Unable to locate compose body");
					}

				} finally {
					this.sSelectFrame("relative=top");
				}

				this.zWaitForBusyOverlay();

				return;

			} else {
				throw new HarnessException("Compose //iframe count was " + frames);
			}
		}

		if (locator == null) {
			throw new HarnessException("locator was null for field " + field);
		}

		if (!this.sIsElementPresent(locator))
			throw new HarnessException("Field is not present field=" + field + " locator=" + locator);

		if (isRepeat != null) {
			this.sClickAt(locator, "");
			zRecurringOptions(locator, value, isRepeat);

		} else {

			this.sFocus(locator);
			this.sClickAt(locator, "");

			if (field == Field.StartDate || field == Field.EndDate || field == Field.StartTime
					|| field == Field.EndTime) {
				this.zKeyboard.zSelectAll(locator);
				this.sTypeDateTime(locator, value);

			} else if (field == Field.Subject) {
				this.sType(locator, value);
				SleepUtil.sleepVerySmall();
				if (sIsElementPresent("css=td[id='zb__App__tab_COMPOSE-1_right_icon']")) {
					sClickAt("css=td[id='zb__App__tab_COMPOSE-1_right_icon']", "0,0");
				}

			} else {
				if (sIsElementPresent("css=td[id='zb__App__tab_COMPOSE-1_right_icon']")) {
					sClickAt("css=td[id='zb__App__tab_COMPOSE-1_right_icon']", "0,0");
				}
				SleepUtil.sleepSmall();
				this.sType(locator, value);
				SleepUtil.sleepSmall();
				this.zKeyboard.zTypeKeyEvent(locator,Keys.ENTER);
				SleepUtil.sleepSmall();
			}
		}
		SleepUtil.sleepSmall();
	}

	@Override
	public void zFill(IItem item) throws HarnessException {
		logger.info(myPageName() + ".zFill(ZimbraItem)");
		logger.info(item.prettyPrint());

		// Make sure the item is a MailItem
		if (!(item instanceof AppointmentItem)) {
			throw new HarnessException("Invalid item type - must be AppointmentItem");
		}

		AppointmentItem appt = (AppointmentItem) item;

		// Subject
		if (appt.getSubject() != null) {
			zFillField(Field.Subject, appt.getSubject());
		}

		// Attendees
		if (appt.getAttendees() != null) {
			zFillField(Field.Attendees, appt.getAttendees());
		}
		// Body
		if (appt.getContent() != null) {
			zFillField(Field.Body, appt.getContent());
		}

		// Forward To Attendees
		if (appt.getToAttendees() != null) {
			zFillField(Field.To, appt.getToAttendees());
		}

		// Optional
		if (appt.getOptional() != null) {
			sClickJavaScript(Locators.ShowOptionalLink);
			zFillField(Field.Optional, appt.getOptional());
		}

		// Location
		if (appt.getLocation() != null) {
			zFillField(Field.Location, appt.getLocation());
		}

		// Equipment
		if (appt.getEquipment() != null) {
			sClickJavaScript(Locators.ShowEquipmentLink);
			zFillField(Field.Equipment, appt.getEquipment());
		}

		// Start date-time
		if (appt.getStartTime() != null) {
			zFillField(Field.StartDate, appt.getStartTime());
			zFillField(Field.StartTime, appt.getStartTime());
		}

		// End date-time
		if (appt.getEndTime() != null) {
			zFillField(Field.EndDate, appt.getEndTime());
			zFillField(Field.EndTime, appt.getEndTime());
		}

		// Calendar folder
		if (appt.getFolder() != null) {
			zFillField(Field.CalendarFolder, appt.getFolder());
		}

		// Is recurring
		if (appt.getRecurring() != null) {
			zFillField(Field.Repeat, appt.getRecurring());
		}

		// Is all day
		if (appt.getIsAllDay() == true) {
			zFillField(Field.AllDay);
		}

		// Is private
		if (appt.getIsPrivate() == true) {
			SleepUtil.sleepMedium();
			String locator = "css=input[id$='_privateCheckbox']";
			this.sCheck(locator);
			this.zWaitForBusyOverlay();
		}

		if (appt.getIsPrivate() == false) {
			SleepUtil.sleepMedium();
			String locator = "css=input[id$='_privateCheckbox']";
			this.sUncheck(locator);
			this.zWaitForBusyOverlay();
		}
	}

	private void zRepeatCore(Button recurringType, Button repeat, String repeatOption1, Button end, String endOption1)
			throws HarnessException {

		logger.info(myPageName() + " zRepeat(recurringType, " + repeat + ", " + repeatOption1 + ", " + end + ", "
				+ endOption1 + ")");

		if (recurringType.equals(null) || repeat.equals(null) || end.equals(null)) {
			throw new HarnessException("Repeat options can't be null!");
		}

		this.sClickAt(Locators.NoneButton, "");

		if (!recurringType.equals(Button.O_CUSTOM_MENU)) {

			if (recurringType.equals(Button.O_EVERY_DAY_MENU)) {
				this.sClickAt(Locators.EveryDayMenuItem, "");
				this.sClickJavaScript(Locators.CustomizeLink);

				if (repeat.equals(Button.B_EVERY_DAY_RADIO_BUTTON)) {
					this.sClickAt(Locators.EveryDayRadioButton, "");

				} else if (repeat.equals(Button.B_EVERY_WEEKDAY_RADIO_BUTTON)) {
					this.sClickAt(Locators.EveryWeekdayRadioButton, "");

				} else if (repeat.equals(Button.B_EVERY_X_DAYS_RADIO_BUTTON)) {
					this.sClickAt(Locators.EveryXdaysRadioButton, "");
					this.sType(Locators.EveryXdaysEditField, repeatOption1);
				}

			} else if (recurringType.equals(Button.O_EVERY_WEEK_MENU)) {
				this.sClickAt(Locators.EveryWeekMenuItem, "");
				this.sClickJavaScript(Locators.CustomizeLink);

				if (repeat.equals(Button.B_EVERY_X_RADIO_BUTTON)) {
					this.sClickAt(Locators.EveryXRadioButton, "");
					if (!repeatOption1.equals("")) {
						this.sClickAt("css=td[id^='WEEKLY_SELECT'] td[id$='_title']", "");
						this.sClickAt("css=div[id^='POPUP_DWT'] td[id$='_title']:contains('" + repeatOption1 + "')",
								"");
					}

				} else if (repeat.equals(Button.B_EVERY_X_WEEKS_ON_RADIO_BUTTON)) {
					this.sClickAt(Locators.EveryXweeksOnRadioButton, "");

				} else if (repeat.equals(Button.E_EVERY_X_WEEKS_ON_EDIT_FIELD)) {
					this.sType(Locators.EveryXweeksOnEditField, repeatOption1);

				} else if (repeat.equals(Button.B_SUNDAY_CHECK_BOX)) {
					this.sClickAt(Locators.SundayCheckBox, repeatOption1);

				} else if (repeat.equals(Button.B_MONDAY_CHECK_BOX)) {
					this.sClickAt(Locators.MondayCheckBox, repeatOption1);

				} else if (repeat.equals(Button.B_TUESDAY_CHECK_BOX)) {
					this.sClickAt(Locators.TuesdayCheckBox, repeatOption1);

				} else if (repeat.equals(Button.B_WEDNESDAY_CHECK_BOX)) {
					this.sClickAt(Locators.WednesdayCheckBox, repeatOption1);

				} else if (repeat.equals(Button.B_THURSDAY_CHECK_BOX)) {
					this.sClickAt(Locators.ThursdayCheckBox, repeatOption1);

				} else if (repeat.equals(Button.B_FRIDAY_CHECK_BOX)) {
					this.sClickAt(Locators.FridayCheckBox, repeatOption1);

				} else if (repeat.equals(Button.B_SATURDAY_CHECK_BOX)) {
					this.sClickAt(Locators.SaturdayCheckBox, repeatOption1);

				}

			} else if (recurringType.equals(Button.O_EVERY_MONTH_MENU)) {
				this.sClickAt(Locators.EveryMonthMenuItem, "");
				this.sClickJavaScript(Locators.CustomizeLink);

				if (repeat.equals(Button.B_DAY_X_OF_EVERY_Y_MONTHS_RADIO_BUTTON)) {
					this.sClickAt(Locators.DayXofEveryYmonthsRadioButton, "");

				} else if (repeat.equals(Button.B_THE_X_Y_OF_EVERY_Z_MONTHS_RADIO_BUTTON)) {
					this.sClickAt(Locators.TheXYofEveryZmonthsRadioButton, "");
					this.sClickAt("css=td[id^='MONTHLY_WEEKDAY_SELECT'] td[id$='_title']", "");
					this.sClickAt("css=div[id*='Menu_'] td[id$='_title']:contains('" + StringUtils
							.capitalize(LocalDate.now().plusDays(1).getDayOfWeek().toString().toLowerCase()) + "')",
							"");
				}

			} else if (recurringType.equals(Button.O_EVERY_YEAR_MENU)) {
				this.sClickAt(Locators.EveryYearMenuItem, "");
				this.sClickJavaScript(Locators.CustomizeLink);

				if (repeat.equals(Button.B_EVERY_YEAR_ON_X_Y_RADIO_BUTTON)) {
					this.sClickAt(Locators.EveryYearOnXYRadioButton, "");

				} else if (repeat.equals(Button.B_THE_X_Y_OF_EVERY_Z_RADIO_BUTTON)) {
					this.sClickAt(Locators.TheXYofEveryZRadioButton, "");
				}

			}

			if (end.equals(Button.B_NO_END_DATE_RADIO_BUTTON)) {
				this.sClickAt(Locators.NoEndDateRadioButton, "");

			} else if (end.equals(Button.B_END_AFTER_X_OCCURRENCES_RADIO_BUTTON)) {
				this.sClickAt(Locators.EndAfterXoccurrencesRadioButton, "");
				this.sType(Locators.EndAfterXoccurrencesEditField, endOption1);

			} else if (end.equals(Button.B_END_BY_DATE_RADIO_BUTTON)) {
				this.sClickAt(Locators.EndByXDateRadioButton, "");
				this.sType(Locators.EndByXDateEditField, endOption1);
			}

		}

		DialogCustomRepeat dlgCustomRepeat = (DialogCustomRepeat) new DialogCustomRepeat(
				DialogCustomRepeat.DialogWarningID.DialogCustomRepeat, MyApplication,
				((AjaxPages) MyApplication).zPageCalendar);
		dlgCustomRepeat.zPressButton(Button.B_OK);
	}

	public void zRepeat(Button recurringType, Button repeat, Button end) throws HarnessException {
		zRepeatCore(recurringType, repeat, "", end, "");
	}

	public void zRepeat(Button recurringType, Button repeat, String repeatOption1, Button end, String endOption1)
			throws HarnessException {
		zRepeatCore(recurringType, repeat, repeatOption1, end, endOption1);
	}

	@Override
	public boolean zIsActive() throws HarnessException {
		logger.info(myPageName() + " zIsActive()");

		String locator = "css=div[id^='zb__App__tab_APPT-']";

		if (!this.sIsElementPresent(locator)) {
			return (false);
		}

		if (!this.zIsVisiblePerPosition(locator, 150, 75)) {
			return (false);
		}
		SleepUtil.sleepMedium();

		logger.info(myPageName() + " zIsActive() = true");
		return (true);
	}

	public String zGetApptSubject(String subject) throws HarnessException {
		return this.sGetText("css=td[id*='_subject']:contains('" + subject + "')");
	}

	public String zGetApptAttendees(String attendee) throws HarnessException {
		return this.sGetText("css=td[id*='_person']:contains('" + attendee + "')");
	}

	public String zGetApptOptional(String optional) throws HarnessException {
		return this.sGetText("css=td[id*='_optional']:contains('" + optional + "')");
	}

	public String zGetApptLocation(String location) throws HarnessException {
		return this.sGetText("css=td[id*='_location']:contains('" + location + "')");
	}

	public String zGetApptLocationFloating(String location) throws HarnessException {
		return this.sGetValue("css=input[id$='_location_input']");
	}

	public String zGetApptEquipment(String equipment) throws HarnessException {
		return this.sGetText("css=td[id*='_resourcesData']:contains('" + equipment + "')");
	}

	public void zRecurringOptions(String locator, String recurringType, String endBy) throws HarnessException {

		if (recurringType.split(",")[0].toUpperCase().equals("NONE")) {
			this.sClickAt(Locators.NoneMenuItem, "");

		} else if (recurringType.split(",")[0].toUpperCase().equals("EVERYDAY")) {
			this.sClickAt(Locators.EveryDayMenuItem, "");

		} else if (recurringType.split(",")[0].toUpperCase().equals("EVERYWEEK")) {
			this.sClickAt(Locators.EveryWeekMenuItem, "");

		} else if (recurringType.split(",")[0].toUpperCase().equals("EVERYMONTH")) {
			this.sClickAt(Locators.EveryMonthMenuItem, "");

		} else if (recurringType.split(",")[0].toUpperCase().equals("EVERYYEAR")) {
			this.sClickAt(Locators.EveryYearMenuItem, "");

		} else if (recurringType.split(",")[0].toUpperCase().equals("CUSTOM")) {
			this.sClickAt(Locators.CustomMenuItem, "");
		} else {
			this.sType(locator, recurringType);
		}
	}

	public boolean zVerifyNewApptTabRemainsOpened() throws HarnessException {
		String locator = "css=div[id^='zb__App__tab']";
		if (sIsElementPresent(locator)) {
			return true;
		} else {
			return false;
		}
	}

	public void zRequestResponseOFF() throws HarnessException {
		this.sClickAt(Locators.ToolbarOptions, "");
		this.sClickAt(Locators.RequestResponse, "");
	}

	public void zRequestResponseON() throws HarnessException {
		this.sClickAt(Locators.ToolbarOptions, "");
		this.sClickAt(Locators.RequestResponse, "");
		SleepUtil.sleepSmall();
		this.sClickAt(Locators.ToolbarOptions, "");
		this.sClickAt(Locators.RequestResponse, "");
	}

	public void zCloseModifiedApptTab() throws HarnessException {
		this.zToolbarPressButton(Button.B_CLOSE);
		DialogConfirmModification confirmClose = (DialogConfirmModification) new DialogConfirmModification(
				this.MyApplication, pageCal);
		confirmClose.zPressButton(Button.B_CANCEL);
	}

	public String zVerifyComposeFormatHTML() throws HarnessException {
		logger.info("return window.document.getElementsByClassName('ZmHtmlEditorTextArea')[0].style.display");
		return sGetEval("return window.document.getElementsByClassName('ZmHtmlEditorTextArea')[0].style.display");
	}

	public List<AutocompleteEntry> zAutocompleteFillField(Field field, String value) throws HarnessException {
		logger.info(myPageName() + " zAutocompleteFillField(" + field + ", " + value + ")");

		tracer.trace("Set " + field + " to " + value);

		String locator = null;

		if (field == Field.Attendees) {
			locator = Locators.AttendeeField;

		} else if (field == Field.Location) {
			if (ConfigProperties.getStringProperty("browser").contains("edge")) {
				locator = Locators.LocationField_Edge;
			} else {
				locator = Locators.LocationField;
			}

		} else if (field == Field.Equipment) {
			if (ConfigProperties.getStringProperty("browser").contains("edge")) {
				locator = Locators.EquipmentField_Edge;
			} else {
				locator = Locators.EquipmentField;
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

		String containerLocator = "css=div[id^='zac__APPT-'][style*='display: block;']";

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

	public void zSelectStartDateFromDatePicker(String Day) throws HarnessException {
		this.sClick("css=td[id$='_startMiniCalBtn'] div[class='ImgSelectPullDownArrow']");
		this.sClick("//div[@class='DwtMenu' and contains(@style,'display: block;')]/descendant::td[text()='" + Day
				+ "' and not(contains(@class,'DwtCalendarDay-grey'))]");

	}

	public void zSelectEndDateFromDatePicker(String Day) throws HarnessException {
		this.sClick("td[id$='_endMiniCalBtn'] div[class='ImgSelectPullDownArrow']");
		this.sClick("//div[@class='DwtMenu' and contains(@style,'display: block;')]/descendant::td[text()='" + Day
				+ "' and not(contains(@class,'DwtCalendarDay-grey'))]");

	}

	public void zSelectStartTimeFromDropdown(String Hour, String Minutes, String Meridiem) throws HarnessException {

		if (Hour.equals("0")) {
			Hour = "12";
		}
		String loc = "" + Hour + ":00 " + Meridiem + "";
		if (Hour.equals("12")) {
			if (Meridiem.equals("AM")) {
				loc = "Midnight";
			} else {
				loc = "Noon";
			}
		}

		this.sClick("css=td[id$='_startTimeSelect'] div[class='ImgSelectPullDownArrow']");

		if (!sIsVisible("td[id$='_title']:contains(" + loc + ")")) {
			for (int i = 0; i < 16; i++) {
				sClick("div[class*='ZHasSubMenu'][style*='display: block'] div[class='DwtMenuScrollTop']>div[class='ImgUpArrowSmall']");
			}
			while (!sIsVisible("td[id$='_title']:contains(" + loc + ")")) {
				sClick("div[class*='ZHasSubMenu'][style*='display: block'] div[class='DwtMenuScrollBottom']>div[class='ImgDownArrowSmall']");
			}
		}
		sMouseOver("td[id$='_title']:contains(" + loc + ")");
		if (Minutes.equals("00") && (loc.equals("Midnight") || loc.equals("Noon"))) {
			sClick("td[id$='_title']:contains(" + loc + ")");
		} else {
			sClick("td[id$='_title']:contains(" + Hour + ":" + Minutes + " " + Meridiem + ")");
		}

	}

	public void zSelectEndTimeFromDropdown(String Hour, String Minutes, String Meridiem) throws HarnessException {

		if (Hour.equals("0")) {
			Hour = "12";
		}
		String loc = "" + Hour + ":00 " + Meridiem + "";
		if (Hour.equals("12")) {
			if (Meridiem.equals("AM")) {
				loc = "Midnight";
			} else {
				loc = "Noon";
			}
		}

		this.sClick("td[id$='_endTimeSelect'] div[class='ImgSelectPullDownArrow']");

		if (!sIsVisible("td[id$='_title']:contains(" + loc + ")")) {
			for (int i = 0; i < 16; i++) {
				sClick("div[class*='ZHasSubMenu'][style*='display: block'] div[class='DwtMenuScrollTop']>div[class='ImgUpArrowSmall']");
			}
			while (!sIsVisible("td[id$='_title']:contains(" + loc + ")")) {
				sClick("div[class*='ZHasSubMenu'][style*='display: block'] div[class='DwtMenuScrollBottom']>div[class='ImgDownArrowSmall']");
			}
		}
		sMouseOver("td[id$='_title']:contains(" + loc + ")");
		if (Minutes.equals("00") && (loc.equals("Midnight") || loc.equals("Noon"))) {
			sClick("td[id$='_title']:contains(" + loc + ")");
		} else {
			sClick("td[id$='_title']:contains(" + Hour + ":" + Minutes + " " + Meridiem + ")");
		}

	}
}