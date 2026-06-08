import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI


import core.RaceUtils
import core.DateOfBirthUtils
import core.DropdownUtils
import core.VerifyUtils
import core.YesNoSelectionUtils
import core.DataFileUploadUtils
import core.ButtonClickUtils
import core.ClickUtils
import core.OverlayUtils
import core.CloseDropdownUtils
import core.BirthDateUtils
import core.DatePickerUtils
import utilities.FormFieldUtils

import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS

import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable
import com.kms.katalon.core.configuration.RunConfiguration
import java.util.List

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.Random



class HouseHoldHeadGeneralInformation {

	private static final int TIMEOUT = 15

	@Keyword
	static void addHouseHoldHeadGeneralInfo() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)


		VerifyUtils.verifyTextByXPath("//h3[normalize-space()='Step 1 - My Info']", "Step 1 - My Info")

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.clsheading_step")))

		List<String> expectedSteps = [
			"General Info (Required)",
			"Contact (Required)",
			"Address (Required)",
			"Identification (Required)",
			"Education (Required)",
			"Disability (Required)",
			"More Info (Required)"
		]

		List<WebElement> stepLabels = driver.findElements(By.cssSelector("span.clsheading_step"))

		WebUI.verifyEqual(stepLabels.size(), expectedSteps.size())

		for (int i = 0; i < expectedSteps.size(); i++) {
			String actual = stepLabels.get(i).getText().trim()
			String expected = expectedSteps.get(i)
			WebUI.verifyEqual(actual, expected)
			println("Verified step: " + actual)
		}

		VerifyUtils.verifyTextByXPath(
				"//h3[normalize-space()='Tell us about yourself']",
				"Tell us about yourself")

		VerifyUtils.verifyTextByXPath(
				"//span[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='Prefix']",
				"Prefix")

		By householdHeadPrefixDropdown = By.xpath("//div[@id='mui-component-select-prefix']")
		DropdownUtils.selectDropDownOption(householdHeadPrefixDropdown, "Mr.", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath(
				"//label[@for='firstName' and .//span[normalize-space()='*'] and .//span[normalize-space()='First Name Legally as it appears on your ID']]",
				"* First Name Legally as it appears on your ID")

		By householdHeadFirstName = By.xpath("//input[@id='firstName']")
		FormFieldUtils.populateOrAssertTextField(
				driver, householdHeadFirstName, ".*", "Adult")

		VerifyUtils.verifyTextByXPath(
				"//label[@for='middleName' and normalize-space()='Middle Name']",
				"Middle Name")

		VerifyUtils.verifyTextByXPath(
				"//label[@for='lastName' and .//span[normalize-space()='*'] and .//span[normalize-space()='Last Name Legally as it appears on your ID']]",
				"* Last Name Legally as it appears on your ID")

		By householdHeadLastName = By.xpath("//input[@id='lastName']")
		FormFieldUtils.populateOrAssertTextField(
				driver, householdHeadLastName, ".*", "Auto")

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='Suffix']",
				"Suffix")

		By suffixDropdown = By.xpath("//div[@id='mui-component-select-suffix']")
		DropdownUtils.selectDropDownOption(suffixDropdown, "Sr.", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		BirthDateUtils.selectDateFromCalendar(
				driver,
				wait,
				"//label[@class='clsFormDateLabel' and .//span[normalize-space()='*'] and contains(normalize-space(.), 'Date of Birth')]",
				By.xpath("//button[@aria-label='Date of Birth']"),
				By.xpath("//input[@id='date-picker-dialog']"),
				"* Date of Birth",
				"1976",
				"January 1976",
				"1",
				TIMEOUT)

		//By householdHeadProfileDob = By.xpath("//input[@id='date-picker-dialog']")

		/*By householdHeadProfileDob = By.xpath("//input[@id='date-picker-dialog' and @placeholder='MM/DD/YYYY']")
		 DateOfBirthUtils.enterDateDirectly(householdHeadProfileDob, "01/01/1976", TIMEOUT)*/

		WebUI.delay(1)

		VerifyUtils.verifyTextByXPath(
				"//label[.//span[normalize-space()='*'] and .//span[normalize-space()='What is your country of birth?']]",
				"* What is your country of birth?")

		By houseHoldHeadCountryDropdown = By.xpath("//input[@id='country-select-demo']")
		DropdownUtils.selectDropDownOption(houseHoldHeadCountryDropdown, "USA", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath(
				"//label[.//span[normalize-space()='*'] and .//span[normalize-space()='What is your ethnicity?']]",
				"* What is your ethnicity?")

		By houseHoldHeadEthnicityDropdown = By.xpath("//div[@id='mui-component-select-ethinicity']")
		DropdownUtils.selectDropDownOption(houseHoldHeadEthnicityDropdown, "Not Hispanic or Latino", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)


		VerifyUtils.verifyTextByXPath(
				"//label[.//span[normalize-space()='*'] and .//span[normalize-space()='What is your race?']]",
				"* What is your race?")

		By houseHoldHeadRaceOption = By.xpath("//div[@id='mui-component-select-race']")
		RaceUtils.clearAndSelectTwoOrThreeRaceOptions(houseHoldHeadRaceOption, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//label[.//span[normalize-space()='*'] and .//span[normalize-space()='Gender']]",
				"* Gender")

		By houseHoldHeadGenderDropdown = By.xpath("//div[@id='mui-component-select-gender']")
		DropdownUtils.selectDropDownOption(houseHoldHeadGenderDropdown, "Male", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		String householdHeadSsnQuestion = "//p[contains(@class,'input-label') and normalize-space()='Do you have a SSN?']"


		String householdHeadSsnYesXpath = householdHeadSsnQuestion + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String householdHeadSsnSelectedYesXpath = householdHeadSsnQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"


		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdHeadSsnYesXpath,
				householdHeadSsnSelectedYesXpath,
				"Yes")
		VerifyUtils.verifyTextByXPath("//label[normalize-space()='SSN']", "SSN")
		By householdHeadSocialSecurityNumber = By.xpath("//input[@id='ssn']")

		FormFieldUtils.populateOrAssertMaskedTextField(
				driver,
				householdHeadSocialSecurityNumber,
				"regex:\\d{3}-\\d{2}-\\d{4}",
				generateRandomSSN())
		ButtonClickUtils.clickEnabledSaveNext(driver, wait)
		addHouseHoldHeadContactInfo()
	}




	@Keyword
	static void addHouseHoldHeadContactInfo() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)


		VerifyUtils.verifyTextByXPath("//h3[normalize-space()='Tell us about your contact information']",
				"Tell us about your contact information")

		VerifyUtils.verifyTextByXPath(
				"//span[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath("//label[@for='mobilePhone' and .//span[normalize-space()='*'] and .//span[normalize-space()='Mobile Number']]",
				"* Mobile Number")

		By headHouseholdMobileNumber = By.xpath("//input[@id='mobilePhone']")

		FormFieldUtils.populateOrAssertTextField(driver, headHouseholdMobileNumber, "regex:\\d{3}-\\d{3}-\\d{4}", generateRandomMobile())

		VerifyUtils.verifyTextByXPath("//label[@for='emailAddress1' and .//span[normalize-space()='*'] and .//span[normalize-space()='Email Address']]",
				"* Email Address")
		String houseHoldHeadEmailAddress = generateRandomEmail()
		By headHouseholdEmailAddressField = By.xpath("//input[@id='emailAddress1']")

		FormFieldUtils.populateOrAssertTextField(driver, headHouseholdEmailAddressField, ".*", houseHoldHeadEmailAddress)

		VerifyUtils.verifyTextByXPath(
				"//label[contains(@class,'input-label') and .//span[normalize-space()='*'] and .//span[normalize-space()='Preferred Language']]",
				"* Preferred Language")

		By houseHoldHeadPreferredLanguageDropdown =
				By.xpath("//div[@id='mui-component-select-userLanguageID' and @role='button']")

		DropdownUtils.selectDropDownOption(houseHoldHeadPreferredLanguageDropdown, "French", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		String houseHoldHeadInterpreterQuestion = "//p[.//span[normalize-space()='*'] and .//span[normalize-space()='Do you need an interpreter?']]"

		String houseHoldHeadInterpreterNoXpath =
				houseHoldHeadInterpreterQuestion +
				"/following::div[contains(@class,'gender')][1]//li[normalize-space()='No']"

		String houseHoldHeadInterpreterNoSelectedXpath =
				houseHoldHeadInterpreterQuestion +
				"/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='No']"


		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				houseHoldHeadInterpreterNoXpath,
				houseHoldHeadInterpreterNoSelectedXpath,
				"No")

		String houseHoldHeadCommunicationTimeQuestion = "//p[.//span[normalize-space()='*'] and .//span[normalize-space()='How do you want us to communicate with you during business hours?']]"


		String houseHoldHeadcommunicationPhoneXpath =
				houseHoldHeadCommunicationTimeQuestion +
				"/following::div[contains(@class,'gender')][1]//li[normalize-space()='Phone']"

		String houseHoldHeadcommunicationPhoneSelectedXpath =
				houseHoldHeadCommunicationTimeQuestion +
				"/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Phone']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				houseHoldHeadcommunicationPhoneXpath,
				houseHoldHeadcommunicationPhoneSelectedXpath,
				"Phone")
		ButtonClickUtils.clickEnabledSaveNext(driver, wait)

		addHouseHoldHeadAddressInfo()
	}

	@Keyword
	static void addHouseHoldHeadAddressInfo() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		VerifyUtils.verifyTextByXPath("//h3[normalize-space()='Tell us about where you live']",
				"Tell us about where you live")


		VerifyUtils.verifyTextByXPath(
				"//span[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath(
				"//label[@for='addressLine1' and .//span[normalize-space()='*'] and .//span[normalize-space()='Address Line 1']]",
				"* Address Line 1"
				)

		By addressLine1Field = By.xpath("//input[@id='addressLine1']")

		FormFieldUtils.populateOrAssertTextField(
				driver,
				addressLine1Field,
				"regex:.+",
				"66 Research Court"
				)
		VerifyUtils.verifyTextByXPath(
				"//label[@for='addressLine2']",
				"Address Line 2 (Suite/Apt#/Building#)")
		VerifyUtils.verifyTextByXPath("//label[@for='city' and .//span[normalize-space()='*'] and .//span[normalize-space()='City']]", "* City")

		By houseHoldHeadCity = By.xpath("//input[@id='city']")

		FormFieldUtils.populateOrAssertTextField(driver, houseHoldHeadCity, "regex:.+", "Rockville")
		VerifyUtils.verifyTextByXPath("//label[@for='state' and .//span[normalize-space()='*'] and .//span[normalize-space()='State']]", "* State")

		By houseHoldHeadState = By.xpath("//input[@id='state']")
		FormFieldUtils.populateOrAssertTextField(
				driver,
				houseHoldHeadCity,
				"regex:.+",
				"MD")
		VerifyUtils.verifyTextByXPath("//label[@for='zipCode' and .//span[normalize-space()='*'] and .//span[normalize-space()='Zip Code']]", "* Zip Code")

		By houseHoldHeadZipCode = By.xpath("//input[@id='zipCode']")
		FormFieldUtils.populateOrAssertTextField(
				driver,
				houseHoldHeadZipCode,
				"regex:.+",
				"20850")

		VerifyUtils.verifyTextByXPath(
				"//p[@class='doc-head' and .//span[normalize-space()='*'] and .//span[normalize-space()='Upload Proof of Address']]",
				"* Upload Proof of Address")

		String hhProofAddressFile = RunConfiguration.getProjectDir() + "/Data Files/HouseholdHeadProofOfAddress.pdf"

		DataFileUploadUtils.uploadDocumentBySectionLabel(
				driver,
				"Upload Proof of Address",
				hhProofAddressFile,
				TIMEOUT)

		String householdHeadSeparateMailingAddressQuestion = "//p[contains(@class,'input-label') and normalize-space()='Do you have a separate mailing address?']"

		String householdHeadSeparateMailingAddressNoXpath =
				householdHeadSeparateMailingAddressQuestion +
				"/following::div[contains(@class,'gender')][1]//li[normalize-space()='No']"

		String householdHeadSeparateMailingAddressNoSelectedXpath =
				householdHeadSeparateMailingAddressQuestion +
				"/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='No']"


		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdHeadSeparateMailingAddressNoXpath,
				householdHeadSeparateMailingAddressNoSelectedXpath,
				"No")

		ButtonClickUtils.clickEnabledSaveNext(driver, wait)
		addHouseHoldHeadIdentityInfo()
	}
	static void addHouseHoldHeadIdentityInfo() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		VerifyUtils.verifyTextByXPath("//h3[normalize-space()='Provide proof of identity']",
				"Provide proof of identity")

		VerifyUtils.verifyTextByXPath(
				"//span[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")


		VerifyUtils.verifyTextByXPath(
				"//label[.//span[normalize-space()='*'] and .//span[normalize-space()='Photo Identification']]",
				"* Photo Identification")

		By householdHeadPhotoIdentificationDropdown = By.xpath("//div[@id='mui-component-select-idCardType']")

		DropdownUtils.selectDropDownOption(
				householdHeadPhotoIdentificationDropdown,
				"Driver’s license",
				TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath(
				"//label[@for='idCardNumber' and contains(normalize-space(.), '*') and contains(normalize-space(.), 'Identification Number')]",
				"* Identification Number")
		By householdHeadIdNumber = By .xpath("//input[@id='idCardNumber']")

		FormFieldUtils.populateOrAssertTextField(
				driver,
				householdHeadIdNumber,
				"regex:.+",
				"MD-123456")
		closeMuiOverlays(driver)

		VerifyUtils.verifyTextByXPath("//label[@for='idCardIssueAuthority' and normalize-space()='Who issued this identification? (example: State of Maryland, United States, etc.)']",
				"Who issued this identification? (example: State of Maryland, United States, etc.)")
		By householdHeadIdIssueAuthority = By.xpath("//input[@id='idCardIssueAuthority']")
		FormFieldUtils.populateOrAssertTextField(
				driver,
				householdHeadIdIssueAuthority,
				"regex:.+",
				"State of Maryland")
		closeMuiOverlays(driver)

		String householdHeadIdDateQuestion = "//p[.//span[normalize-space()='*'] and .//span[normalize-space()='Does this proof of ID have an issue date and expiration date?']]"

		String householdHeadIdDateNoXpath = householdHeadIdDateQuestion + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='No']"

		String householdHeadIdDateNoSelectedXpath = householdHeadIdDateQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='No']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdHeadIdDateNoXpath,
				householdHeadIdDateNoSelectedXpath,
				"No")

		/*VerifyUtils.verifyTextByXPath(
		 "//label[@class='clsFormDateLabel' and normalize-space(.)='* Issue Date']",
		 "* Issue Date")
		 DatePickerUtils.selectNextDayByFieldLabel(driver, wait, "* Issue Date")
		 VerifyUtils.verifyTextByXPath(
		 "//label[@class='clsFormDateLabel' and normalize-space(.)='* Expiration Date']",
		 "* Expiration Date")
		 DatePickerUtils.selectNextDayByFieldLabel(driver, wait, "* Expiration Date")*/

		VerifyUtils.verifyTextByXPath(
				"//p[@class='doc-head' and .//span[normalize-space()='*'] and .//span[normalize-space()='Upload Proof of Identity']]",
				"* Upload Proof of Identity ")

		String hhProofIdentityFile = RunConfiguration.getProjectDir() + "/Data Files/HouseholdHeadProofOfIdentity.pdf"

		DataFileUploadUtils.uploadDocumentBySectionLabel(
				driver,
				"Upload Proof of Identity",
				hhProofIdentityFile,
				TIMEOUT)
		ButtonClickUtils.clickEnabledSaveNext(driver, wait)
		addHouseHoldHeadEducationDetails()
	}

	static void addHouseHoldHeadEducationDetails() {

		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		VerifyUtils.verifyTextByXPath(
				"//h3[normalize-space()='Provide education details']",
				"Provide education details")

		VerifyUtils.verifyTextByXPath(
				"//span[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		String householdHeadSchoolEnrollmentQuestion =  "//p[.//span[normalize-space()='*'] and .//span[normalize-space()='Are you currently enrolled in school?']]"

		String householdHeadSchoolEnrollmentYesXpath = householdHeadSchoolEnrollmentQuestion  + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String householdHeadSchoolEnrollmentSelectedYesXpath = householdHeadSchoolEnrollmentQuestion  +
				"/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdHeadSchoolEnrollmentYesXpath,
				householdHeadSchoolEnrollmentSelectedYesXpath,
				"Yes")

		VerifyUtils.verifyTextByXPath(
				"//label[.//span[normalize-space()='*'] and .//span[normalize-space()='What is the highest level of education that you have completed?']]",
				"* What is the highest level of education that you have completed?")

		By householdHeadEducationLevelDropdown = By.xpath("//div[@id='mui-component-select-graduationLevel']")
		DropdownUtils.selectDropDownOption(householdHeadEducationLevelDropdown, "Some College/Associates Degree", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		String householdHeadCurrentStudentStatusQuestion = "//label[contains(@class,'input-label') and .//span[normalize-space()='What is your current student status?']]"

		String householdHeadCurrentStudentStatusXpath =
				householdHeadCurrentStudentStatusQuestion +
				"/following-sibling::div[contains(@class,'gender')]//li[normalize-space()='Part Time']"

		String householdHeadCurrentStudentStatusSelectedXpath =
				householdHeadCurrentStudentStatusQuestion +
				"/following-sibling::div[contains(@class,'gender')]//li[contains(@class,'selected') and normalize-space()='Part Time']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdHeadCurrentStudentStatusXpath,
				householdHeadCurrentStudentStatusSelectedXpath,
				"Part Time")

		VerifyUtils.verifyTextByXPath(
				"//p[@class='doc-head' and .//span[normalize-space()='*'] and .//span[normalize-space()='Upload Proof of enrollment']]",
				"* Upload Proof of enrollment")

		String hhProofEnrollmentFile = RunConfiguration.getProjectDir() + "/Data Files/HouseholdHeadProofOfEnrollment.pdf"

		DataFileUploadUtils.uploadDocumentBySectionLabel(
				driver,
				"Upload Proof of enrollment",
				hhProofEnrollmentFile,
				TIMEOUT)

		ButtonClickUtils.clickEnabledSaveNext(driver, wait)
		addHouseHoldHeadDisabilityInfo()
	}

	static void addHouseHoldHeadDisabilityInfo() {

		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		VerifyUtils.verifyTextByXPath("//h3[normalize-space()='Disability']", "Disability")

		VerifyUtils.verifyTextByXPath(
				"//span[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		String householdHeadDisabilityQuestionXpath =
				"//p[.//span[normalize-space()='*'] and .//span[normalize-space()='Are you disabled?']]"

		String householdHeadDisabilityYesXpath = householdHeadDisabilityQuestionXpath + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String householdHeadDisabilitySelectedYesXpath = householdHeadDisabilityQuestionXpath + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"


		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdHeadDisabilityYesXpath,
				householdHeadDisabilitySelectedYesXpath,
				"Yes")


		String householdHeadBlindQuestionXpath =
				"//p[.//span[normalize-space()='*'] and .//span[normalize-space()='Are you blind?']]"

		String householdHeadBlindNoXpath = householdHeadBlindQuestionXpath + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='No']"

		String householdHeadBlindNoSelectedXpath = householdHeadBlindQuestionXpath + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='No']"


		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdHeadBlindNoXpath,
				householdHeadBlindNoSelectedXpath,
				"No")


		String householdHeadAccommodationsQuestionXpath =
				"//p[.//span[normalize-space()='*'] and .//span[normalize-space()='Do you need any accommodations?']]"

		String householdHeadAccommodationsYesXpath = householdHeadAccommodationsQuestionXpath + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String householdHeadAccommodationsSelectedYesXpath = householdHeadAccommodationsQuestionXpath + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"



		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdHeadAccommodationsYesXpath,
				householdHeadAccommodationsSelectedYesXpath,
				"Yes")

		By householdHeadAccommodationOption = By.xpath("//div[@id='mui-component-select-accommodationType']")
		RaceUtils.clearAndSelectTwoOrThreeRaceOptions(householdHeadAccommodationOption, TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		ButtonClickUtils.clickEnabledSaveNext(driver, wait)
		addHouseHoldHeadMoreInfo()
	}

	static void addHouseHoldHeadMoreInfo() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		VerifyUtils.verifyTextByXPath("//h3[normalize-space()='Tell us more about you']", "Tell us more about you")

		VerifyUtils.verifyTextByXPath(
				"//span[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")


		VerifyUtils.verifyTextByXPath("//label[.//span[normalize-space()='*'] and .//span[normalize-space()='What is your marital status?']]",
				"* What is your marital status?")

		By householdHeadMarriageStatusDropdown = By.xpath("//div[@id='mui-component-select-familyStatusCode']")

		DropdownUtils.selectDropDownOption(householdHeadMarriageStatusDropdown, "Married", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath("//label[normalize-space()='What is your United States military status?']",
				"What is your United States military status?")
		By householdHeadVeteranStatusDropdown = By.xpath("//div[@id='mui-component-select-veteranStatusCode']")

		DropdownUtils.selectDropDownOption(householdHeadVeteranStatusDropdown, "Reserve", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		String householdHeadPregnantQuestion =
				"//p[.//span[normalize-space()='*'] and .//span[normalize-space()='Are you or your spouse or partner pregnant?']]"

		String householdHeadSpousePregnantNoXpath =
				householdHeadPregnantQuestion +
				"/following::div[contains(@class,'gender')][1]//li[normalize-space()='No']"

		String householdHeadSpousePregnantNoSelectedXpath =
				householdHeadPregnantQuestion +
				"/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='No']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdHeadSpousePregnantNoXpath,
				householdHeadSpousePregnantNoSelectedXpath,
				"No")


		String householdHeadIncomeTaxesQuestion = "//p[.//span[normalize-space()='*'] and .//span[normalize-space()='Have you filed your income taxes?']]"

		String householdHeadIncomeTaxesYesXpath =
				householdHeadIncomeTaxesQuestion +
				"/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String householdHeadIncomeTaxesSelectedYesXpath =
				householdHeadIncomeTaxesQuestion +
				"/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdHeadIncomeTaxesYesXpath,
				householdHeadIncomeTaxesSelectedYesXpath,
				"Yes")

		VerifyUtils.verifyTextByXPath("//p[@class='doc-head' and .//span[normalize-space()='*'] and .//span[normalize-space()='Upload Income taxes']]",
				"* Upload Income taxes")
		String hhProofIncomeTaxFile = RunConfiguration.getProjectDir() + "/Data Files/HouseholdHeadFederalIncomeTax.pdf"

		DataFileUploadUtils.uploadDocumentBySectionLabel(
				driver,
				"Upload Income taxes",
				hhProofIncomeTaxFile,
				TIMEOUT)

		By householdHeadMoreInfoSaveBtn = By.xpath("//button[@type='button' and contains(@class,'btn-primary') and .//span[normalize-space()='Save']]")
		ClickUtils.waitAndClick(householdHeadMoreInfoSaveBtn, TIMEOUT)
		WebUI.delay(5)

		By householdHeadMoreInfoYesBtn = By.xpath("//button[contains(@class,'ajs-ok') and normalize-space()='Yes']")
		ClickUtils.waitAndClick(householdHeadMoreInfoYesBtn, TIMEOUT)
	}
	//}

	// -------------------------
	// Helper Reusable Methods
	// -------------------------

	private static void closeMuiOverlays(WebDriver driver) {
		new Actions(driver).sendKeys(Keys.ESCAPE).perform()
	}

	private static String generateRandomSSN() {
		Random random = new Random()
		int ssnFirstPart = 100 + random.nextInt(900)
		int ssnSecondPart = 10 + random.nextInt(90)
		int ssnThirdPart = 1000 + random.nextInt(9000)
		return "${ssnFirstPart}-${ssnSecondPart}-${ssnThirdPart}"
	}
	private static final Random randomPhoneNum = new Random()
	private static final List<String> MOBILE_NUMBERS = [
		"301-709-8765",
		"202-760-3456",
		"610-772-3456"
	]

	private static String generateRandomMobile() {
		return MOBILE_NUMBERS[randomPhoneNum.nextInt(MOBILE_NUMBERS.size())]
	}

	private static final Random randomEmailAddress = new Random()

	private static final String[] EMPLOYER_EMAILS = [
		"qiru.zhu+seleniumautoversion6@hotmail.com",
		"qiru.zhu+seleniumautoversion6@hotmail.com",
		"qiru.zhu+seleniumautoversion6@hotmail.com",
		"qiru.zhu+seleniumautoversion6@hotmail.com",
		"qiru.zhu+seleniumautoversion6@hotmail.com",
		"qiru.zhu+seleniumautoversion6@hotmail.com"
	]

	private static String generateRandomEmail() {
		return EMPLOYER_EMAILS[randomEmailAddress.nextInt(EMPLOYER_EMAILS.length)]
	}
}