import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.webui.driver.DriverFactory
import java.util.List
import java.time.Duration
import java.util.Random

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

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait




public class HouseholdIncome {
	private static final int TIMEOUT = 15
	private static int timeoutSeconds = 10


	@Keyword
	static void addHouseHoldIncome() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)


		VerifyUtils.verifyTextByXPath(
				"//h3[normalize-space()='Tell us about your household Income']",
				"Tell us about your household Income")

		VerifyUtils.verifyTextByXPath(
				"//span[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath(
				"//span[normalize-space()='Child support income from non-custodial parent(s)']",
				"Child support income from non-custodial parent(s)")

		addJobEarnedIncome()
	}

	static void addJobEarnedIncome() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		String householdJobIncomeQuestion = "//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Do you or anyone in your household have income from one or more jobs?']]"

		String householdJobIncomeYesXpath = householdJobIncomeQuestion + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String householdJobIncomeSelectedYesXpath = householdJobIncomeQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdJobIncomeYesXpath,
				householdJobIncomeSelectedYesXpath,
				"Yes")


		By addJobIncomeBtn = By.xpath("//button[@type='button' and .//span[normalize-space()='Add Job Income']]")
		ClickUtils.waitAndClick(addJobIncomeBtn, timeoutSeconds)

		VerifyUtils.verifyTextByXPath(
				"//div[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* Choose the household member for whom you wish to enter employment information.']",
				"* Choose the household member for whom you wish to enter employment information.")

		By employmentInformationDropdown = By.xpath("//div[@id='mui-component-select-memberName']")
		ClickUtils.waitAndClick(employmentInformationDropdown, TIMEOUT)

		By firstMemberOption = By.xpath("//ul[@role='listbox']//li[@role='option'][1]")
		ClickUtils.waitAndClick(firstMemberOption, TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* Employer Name']",
				"* Employer Name")

		String employerName = generateEmployerName()
		By employerNameField = By.xpath("//input[@id='employerName']")
		FormFieldUtils.populateOrAssertTextField(driver, employerNameField, ".*", employerName)


		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* Employer Phone Number']",
				"* Employer Phone Number")

		String employerPhoneNumber = generateRandomMobile()
		By employerPhoneNumberField = By.xpath("//input[@id='workPhone']")
		FormFieldUtils.populateOrAssertTextField(driver, employerPhoneNumberField, ".*", employerPhoneNumber)


		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='Employer Email Address']",
				"Employer Email Address")

		String employerEmailAddress = generateRandomEmail()
		By employerEmailAddressField = By.xpath("//input[@id='emailAddress1']")
		FormFieldUtils.populateOrAssertTextField(driver, employerEmailAddressField, ".*", employerEmailAddress)
		clickEnabledSaveNext(driver, wait)

		//Employer Address
		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* Address Line 1']",
				"* Address Line 1")

		By addressLine1Field = By.xpath("//input[@name='addressLine1' or @id='addressLine1']")
		WebElement address1el = wait.until(ExpectedConditions.elementToBeClickable(addressLine1Field))
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", address1el)

		address1el.click()
		address1el.sendKeys(Keys.chord(Keys.CONTROL, "a"))
		address1el.sendKeys(Keys.DELETE)
		address1el.sendKeys("2345")

		By firstAddressAutoFillOption = By.xpath("(//div[contains(@class,'pac-item') or @role='option' or self::li][normalize-space()!=''])[1]")
		WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(firstAddressAutoFillOption))
		firstOption.click()

		By cityField = By.xpath("//input[@id='city']")
		By stateField = By.xpath("//input[@id='state']")
		By zipCodeField = By.xpath("//input[@id='zipCode']")

		wait.until {
			driver.findElement(cityField).getAttribute("value")?.trim() &&
					driver.findElement(stateField).getAttribute("value")?.trim() &&
					driver.findElement(zipCodeField).getAttribute("value")?.trim()
		}

		assert driver.findElement(cityField).getAttribute("value").trim() != ""
		assert driver.findElement(stateField).getAttribute("value").trim() != ""
		assert driver.findElement(zipCodeField).getAttribute("value").trim() != ""

		By saveAndNextButton = By.xpath("(//button[.//span[normalize-space()='Save & Next'] and not(@disabled)])[last()]")
		ClickUtils.waitAndClick(saveAndNextButton, TIMEOUT)

		//Employment
		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* Employment Type']",
				"* Employment Type")

		By employmentTypeDropdown = By.xpath("//div[@id='mui-component-select-employementType']")
		DropdownUtils.selectDropDownOption(employmentTypeDropdown, "Full-Time", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		//VerifyUtils.verifyTextByXPath("//label[normalize-space()='* Approximately when did the household member begin working at this job? (You must provide a date if the start date is in the future.)']",
		//"* Approximately when did the household member begin working at this job? (You must provide a date if the start date is in the future.)")

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* Approximately when did the household member begin working at this job? (You must provide a date if the start date is in the future.)']",
				"* Approximately when did the household member begin working at this job? (You must provide a date if the start date is in the future.)")

		By jobStartDateField = By.xpath("//input[@id='date-picker-dialog']")
		DateOfBirthUtils.enterDateDirectly(jobStartDateField, "07/10/2020", TIMEOUT)

		/*BirthDateUtils.selectDateFromCalendar(
		 driver,
		 wait,
		 "//label[contains(normalize-space(.), 'Approximately when did the household member begin working at this job?')]",
		 By.xpath("//button[@type='button' and contains(@aria-label,'Approximately when did the household member begin working at this job?')]"),
		 By.xpath("//input[@id='date-picker-dialog']"),
		 "* Approximately when did the household member begin working at this job? (You must provide a date if the start date is in the future.)",
		 "2020",
		 "July 2020",
		 "10",
		 TIMEOUT)*/


		VerifyUtils.verifyTextByXPath(
				"//p[normalize-space()='* Is the household member still working at this job?']",
				"* Is the household member still working at this job?")

		String householdStillWorkingQuestion = "//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Is the household member still working at this job?']]"

		String householdStillWorkingYesXpath = householdStillWorkingQuestion + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String householdStillWorkingSelectedYesXpath = householdStillWorkingQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"


		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdStillWorkingYesXpath,
				householdStillWorkingSelectedYesXpath,
				"Yes")

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* How many hours does the household member work at this job each week?']",
				"* How many hours does the household member work at this job each week?")

		By weeklyWorkHours = By.xpath("//input[@id='hours-input']")

		FormFieldUtils.populateOrAssertTextField(driver, weeklyWorkHours, '.*', "40")

		VerifyUtils.verifyTextByXPath("//label[.//span[normalize-space()='*'] and .//span[normalize-space()='How often is the household member paid?']]", "* How often is the household member paid?")

		By payingScheduleDropdown = By.xpath("//div[@id='mui-component-select-paySchedule']")
		DropdownUtils.selectDropDownOption(payingScheduleDropdown, "Monthly", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath("//label[.//span[normalize-space()='*'] and .//span[normalize-space()='Gross Income Amount Received Before Taxes']]", '* Gross Income Amount Received Before Taxes')

		By grossIncomeBeforeTaxes = By.xpath("//input[@id='grossIncome']")

		FormFieldUtils.populateOrAssertTextField(driver, grossIncomeBeforeTaxes, '.*', "4798.00")

		VerifyUtils.verifyTextByXPath("//p[@class='doc-head' and .//span[normalize-space()='*'] and .//span[normalize-space()='Upload Proof of Earned Income']]",
				"* Upload Proof of Earned Income")

		String proofOfEmploymentEarnedIncomeFile = RunConfiguration.getProjectDir() + "/Data Files/ProofofEarnedIncome.pdf"

		DataFileUploadUtils.uploadDocumentBySectionLabel(
				driver,
				"Upload Proof of Earned Income",
				proofOfEmploymentEarnedIncomeFile,
				TIMEOUT)

		By employmentEarnedIncomeSaveBtn = By.xpath("(//button[@type='button' and .//span[normalize-space()='Save'] and not(@disabled)])[last()]")
		ClickUtils.waitAndClick(employmentEarnedIncomeSaveBtn, TIMEOUT)

		By ssaSectionQuestion = By.xpath("//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Is anyone in your household receiving SSA Survivor Benefits or Social Security Benefits from a deceased or disabled spouse/parent?']]")
		wait.until(ExpectedConditions.visibilityOfElementLocated(ssaSectionQuestion))

		addSsaBenefitUnearnedIncome()
	}


	static void addSsaBenefitUnearnedIncome() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		VerifyUtils.verifyTextByXPath(
				"//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Is anyone in your household receiving SSA Survivor Benefits or Social Security Benefits from a deceased or disabled spouse/parent?']]",
				"* Is anyone in your household receiving SSA Survivor Benefits or Social Security Benefits from a deceased or disabled spouse/parent?")

		String ssaSurvivorBenefitsQuestion = "//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Is anyone in your household receiving SSA Survivor Benefits or Social Security Benefits from a deceased or disabled spouse/parent?']]"


		String householdSsaSurvivorBenefitsYesXpath = ssaSurvivorBenefitsQuestion +  "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String householdSsaSurvivorBenefitsSelectedYesXpath  = ssaSurvivorBenefitsQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdSsaSurvivorBenefitsYesXpath,
				householdSsaSurvivorBenefitsSelectedYesXpath,
				"Yes")



		By addSsaBenefitBtn = By.xpath("//button[contains(@class,'add-btn') and .//span[contains(normalize-space(), 'Add SSA Benefit')]]")
		ClickUtils.waitAndClick(addSsaBenefitBtn, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//div[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* Which household member is receiving the other income?']",
				"* Which household member is receiving the other income?")

		By otherSsnIncomeHouseholdMemberDropdown = By.xpath("//div[@id='mui-component-select-memberType']")
		ClickUtils.waitAndClick(otherSsnIncomeHouseholdMemberDropdown, TIMEOUT)

		By firstOtherSsnIncomeHouseholdMemberOption = By.xpath("(//ul[@role='listbox']//li[@role='option'])[1]")
		ClickUtils.waitAndClick(firstOtherSsnIncomeHouseholdMemberOption, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* Income Type']",
				"* Income Type")

		By otherSsnIncomeTypeDropdown = By.xpath("//div[@id='mui-component-select-incomeSource']")
		ClickUtils.waitAndClick(otherSsnIncomeTypeDropdown, TIMEOUT)

		By firstOtherSsnIncomeTypeOption = By.xpath("(//ul[@role='listbox']//li[@role='option'])[1]")
		ClickUtils.waitAndClick(firstOtherSsnIncomeTypeOption, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* How often does the household member receive this income?']",
				"* How often does the household member receive this income?")

		By otherSourceIncomePayScheduleDropdown = By.xpath("//div[@id='mui-component-select-paySchedule']")
		ClickUtils.waitAndClick(otherSourceIncomePayScheduleDropdown, TIMEOUT)

		By otherSourceIncomePayScheduleOption = By.xpath("(//ul[@role='listbox']//li[@role='option'])[1]")
		ClickUtils.waitAndClick(otherSourceIncomePayScheduleOption, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* How much does the household member receive?']",
				"* How much does the household member receive?")
		By otherSourceIncomeAmount = By.xpath("//input[@name='amount']")
		FormFieldUtils.populateOrAssertTextField(driver, otherSourceIncomeAmount, '.*', "6161.00")

		VerifyUtils.verifyTextByXPath(
				"//label[contains(@class,'clsFormDateLabel') and .//span[contains(@class,'labelStar')] and contains(normalize-space(.), 'When did the household member start receiving this income?')]",
				"* When did the household member start receiving this income?")

		/*BirthDateUtils.selectDateFromCalendar(
		 driver,
		 wait,
		 "//label[contains(@class,'clsFormDateLabel') and .//span[contains(@class,'labelStar')] and contains(normalize-space(.), 'When did the household member start receiving this income?')]",
		 By.xpath("//button[@type='button' and contains(@aria-label,'When did the household member start receiving this income?')]"),
		 By.xpath("//input[@id='date-picker-dialog']"),
		 "* When did the household member start receiving this income?",
		 "2022",
		 "May 2022",
		 "17",
		 TIMEOUT)*/

		By jobStartDateField = By.xpath("//input[@id='date-picker-dialog']")
		DateOfBirthUtils.enterDateDirectly(jobStartDateField, "05/17/2022", TIMEOUT)

		/*By ssnBenefitsCalendarYearHeader = By.xpath("//div[contains(@class,'MuiPickersCalendarHeader') or contains(@class,'MuiPickersToolbar')]//*[normalize-space()='2023']")
		 WebElement ssnBenefitsCalendarYearHeaderEl = wait.until(ExpectedConditions.elementToBeClickable(ssnBenefitsCalendarYearHeader))
		 js.executeScript("arguments[0].scrollIntoView({block:'center'});", ssnBenefitsCalendarYearHeaderEl)
		 ssnBenefitsCalendarYearHeaderEl.click()
		 By ssnBenefitsBeginWorkingYear = By.xpath("//*[contains(@class,'MuiPickersYear') or @role='button'][normalize-space()='2021']")
		 WebElement ssnBenefitsBeginWorkingYearEl = wait.until(ExpectedConditions.elementToBeClickable(ssnBenefitsBeginWorkingYear))
		 js.executeScript("arguments[0].scrollIntoView({block:'center'});", ssnBenefitsBeginWorkingYearEl)
		 ssnBenefitsBeginWorkingYearEl.click()
		 By ssnBenefitsCalendarMonthYearHeader = By.xpath("//*[contains(text(),'March 2021')]")
		 wait.until(ExpectedConditions.visibilityOfElementLocated(ssnBenefitsCalendarMonthYearHeader))
		 By ssnBenefitsBeginWorkingDay = By.xpath(
		 "//button[normalize-space()='23' and not(@disabled)] | " +
		 "//*[contains(@class,'MuiPickersDay') and normalize-space()='23' and not(contains(@class,'Mui-disabled'))]"
		 )
		 WebElement ssnBenefitsBeginWorkingDayEl = wait.until(ExpectedConditions.elementToBeClickable(ssnBenefitsBeginWorkingDay))
		 js.executeScript("arguments[0].scrollIntoView({block:'center'});", ssnBenefitsBeginWorkingDayEl)
		 ssnBenefitsBeginWorkingDayEl.click()
		 By ssnBenefitsCalendarOkButton = By.xpath("//button[normalize-space()='OK']")
		 WebElement ssnBenefitsCalendarOkBtn = wait.until(ExpectedConditions.elementToBeClickable(ssnBenefitsCalendarOkButton))
		 ssnBenefitsCalendarOkBtn.click()*/


		VerifyUtils.verifyTextByXPath(
				"//p[.//span[normalize-space()='Upload Proof of Unearned Income']]",
				"* Upload Proof of Unearned Income")

		String ssaUnearnedIncomeFile = RunConfiguration.getProjectDir() + "/Data Files/SsaBenefitsIncome.pdf"

		DataFileUploadUtils.uploadDocumentBySectionLabel(
				driver,
				"Upload Proof of Unearned Income",
				ssaUnearnedIncomeFile,
				TIMEOUT)

		//uploadProofOfSsaBenefitUnearnedIncome(driver, wait, TIMEOUT)

		By ssaUnearnedIncomeSaveBtn = By.xpath("//button[@type='button' and .//span[normalize-space()='Save']]")
		ClickUtils.waitAndClick(ssaUnearnedIncomeSaveBtn, timeoutSeconds)

		By householdTcaQuestion = By.xpath("//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Do you or anyone in the household get TCA (Temporary Cash Assistance)?']]")
		wait.until(ExpectedConditions.visibilityOfElementLocated(householdTcaQuestion))

		addTemporaryCashAssistanceUnearnedIncome()
	}

	/*static void uploadProofOfSsaBenefitUnearnedIncome(WebDriver driver, WebDriverWait wait, int timeoutSeconds = 15) {
	 By uploadBtn = By.xpath("//button[@type='button' and .//img[@alt='add']]")
	 new core.ClickUtils().waitAndClick(uploadBtn, timeoutSeconds)
	 String identityFile = RunConfiguration.getProjectDir() + "/Data Files/ProofofUnearnedIncome.pdf"
	 By fileInputBy = By.id("contained-button-file")
	 wait.until(ExpectedConditions.presenceOfElementLocated(fileInputBy))
	 driver.findElement(fileInputBy).sendKeys(identityFile)
	 By uploadConfirm = By.xpath("//button[contains(@class,'btn-primary') and @title='Upload']")
	 new core.ClickUtils().waitAndClick(uploadConfirm, timeoutSeconds)
	 WebUI.comment("File uploaded successfully")
	 }*/


	static void addTemporaryCashAssistanceUnearnedIncome() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		String householdTcaQuestion = "//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Do you or anyone in the household get TCA (Temporary Cash Assistance)?']]"

		String householdTcaYesXpath = householdTcaQuestion + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String householdTcaSelectedYesXpath = householdTcaQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdTcaYesXpath,
				householdTcaSelectedYesXpath,
				"Yes")

		By addTcaBenefitBtn = By.xpath("//button[@type='button' and contains(@class,'add-btn') and .//span[contains(normalize-space(.), 'Add TCA Benefit')]]")

		ClickUtils.waitAndClick(addTcaBenefitBtn, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//div[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath(
				"//label[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Which household member is receiving the other income?']]",
				"* Which household member is receiving the other income?")

		By otherTemporaryCashHouseholdMemberDropdown = By.xpath("//div[@id='mui-component-select-memberType']")
		ClickUtils.waitAndClick(otherTemporaryCashHouseholdMemberDropdown, TIMEOUT)

		By otherTemporaryCashHouseholdMemberOption = By.xpath("(//ul[@role='listbox' and not(@aria-hidden='true')]//li[@role='option'])[1]")
		ClickUtils.waitAndClick(otherTemporaryCashHouseholdMemberOption, TIMEOUT)


		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* Income Type']",
				"* Income Type")

		By temporaryCashAssistanceIncomeTypeDropdown = By.xpath("//div[@id='mui-component-select-incomeSource']")
		ClickUtils.waitAndClick(temporaryCashAssistanceIncomeTypeDropdown, TIMEOUT)

		By temporaryCashAssistanceIncomeTypeOption = By.xpath("(//ul[@role='listbox' and not(@aria-hidden='true')]//li[@role='option'])[1]")
		ClickUtils.waitAndClick(temporaryCashAssistanceIncomeTypeOption, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space(.)='* How often does the household member receive this income?']",
				"* How often does the household member receive this income?")

		By temporaryCashAssistanceIncomeFrequencyDropdown = By.xpath("//div[@id='mui-component-select-paySchedule']")
		ClickUtils.waitAndClick(temporaryCashAssistanceIncomeFrequencyDropdown, TIMEOUT)

		By temporaryCashAssistanceIncomeFrequencyOption = By.xpath("(//ul[@role='listbox' and not(@aria-hidden='true')]//li[@role='option'])[1]")
		ClickUtils.waitAndClick(temporaryCashAssistanceIncomeFrequencyOption, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//label[.//span[contains(@class,'labelStar') and normalize-space()='*'] and .//span[normalize-space()='How much does the household member receive?']]",
				"* How much does the household member receive?")
		By othertemporaryCashAssistanceAmount = By.xpath("//input[@name='amount' and @aria-label='amount']")
		FormFieldUtils.populateOrAssertTextField(driver, othertemporaryCashAssistanceAmount, '.*', "1313.00")

		VerifyUtils.verifyTextByXPath(
				"//label[contains(@class,'clsFormDateLabel') and .//span[contains(@class,'labelStar')] and contains(normalize-space(.), 'When did the household member start receiving this income?')]",
				"* When did the household member start receiving this income?")

		/*By tcaCalendarYearHeader = By.xpath("//div[contains(@class,'MuiPickersCalendarHeader') or contains(@class,'MuiPickersToolbar')]//*[normalize-space()='2022']")
		 WebElement tcaCalendarYearHeaderEl = wait.until(ExpectedConditions.elementToBeClickable(tcaCalendarYearHeader))
		 js.executeScript("arguments[0].scrollIntoView({block:'center'});", tcaCalendarYearHeaderEl)
		 tcaCalendarYearHeaderEl.click()
		 By tcaBeginWorkingYear = By.xpath("//*[contains(@class,'MuiPickersYear') or @role='button'][normalize-space()='2022']")
		 WebElement tcaBeginWorkingYearEl = wait.until(ExpectedConditions.elementToBeClickable(tcaBeginWorkingYear))
		 js.executeScript("arguments[0].scrollIntoView({block:'center'});", tcaBeginWorkingYearEl)
		 tcaBeginWorkingYearEl.click()
		 By tcaCalendarMonthYearHeader = By.xpath("//*[contains(text(),'March 2022')]")
		 wait.until(ExpectedConditions.visibilityOfElementLocated(tcaCalendarMonthYearHeader))
		 By tcaBeginWorkingDay = By.xpath(
		 "//button[normalize-space()='23' and not(@disabled)] | " +
		 "//*[contains(@class,'MuiPickersDay') and normalize-space()='23' and not(contains(@class,'Mui-disabled'))]")
		 WebElement tcaBeginWorkingDayEl = wait.until(ExpectedConditions.elementToBeClickable(tcaBeginWorkingDay))
		 js.executeScript("arguments[0].scrollIntoView({block:'center'});", tcaBeginWorkingDayEl)
		 tcaBeginWorkingDayEl.click()
		 By tcaCalendarOkButton = By.xpath("//button[normalize-space()='OK']")
		 WebElement tcaCalendarOkBtn = wait.until(ExpectedConditions.elementToBeClickable(tcaCalendarOkButton))
		 tcaCalendarOkBtn.click()*/

		/*BirthDateUtils.selectDateFromCalendar(
		 driver,
		 wait,
		 "//label[contains(@class,'clsFormDateLabel') and .//span[contains(@class,'labelStar')] and contains(normalize-space(.), 'When did the household member start receiving this income?')]",
		 By.xpath("//button[@type='button' and contains(@aria-label,'When did the household member start receiving this income?')]"),
		 By.xpath("//input[@id='date-picker-dialog']"),
		 "* When did the household member start receiving this income?",
		 "2020",
		 "August 2020",
		 "19",
		 TIMEOUT)*/

		By jobStartDateField = By.xpath("//input[@id='date-picker-dialog']")
		DateOfBirthUtils.enterDateDirectly(jobStartDateField, "08/19/2020", TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//p[contains(@class,'doc-head') and contains(@class,'inc_head') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Upload Proof of Unearned Income'] and .//button[@type='button'] and .//img[@alt='add']]",
				"* Upload Proof of Unearned Income")

		//uploadProofOfUnearnedTcaIncome(driver, wait, TIMEOUT)

		String proofOfTcaUnearnedIncomeFile = RunConfiguration.getProjectDir() + "/Data Files/ProofOfTcaIncome.pdf"

		DataFileUploadUtils.uploadDocumentBySectionLabel(
				driver,
				"Upload Proof of Unearned Income",
				proofOfTcaUnearnedIncomeFile,
				TIMEOUT)

		By tcaSaveBtn = By.xpath("//button[@type='button' and contains(@class,'create-accnt') and .//span[normalize-space()='Save']]")

		ClickUtils.waitAndClick(tcaSaveBtn, TIMEOUT)

		By householdSnapQuestion = By.xpath(
				"//p[contains(@class,'input-label') and " +
				".//span[contains(@class,'labelStar')] and " +
				".//span[normalize-space()='Do you or anyone in the household get SNAP (Supplemental Nutrition Assistance Program)?']]")
		wait.until(ExpectedConditions.visibilityOfElementLocated(householdSnapQuestion))

		addSupplementalNutritionAssistanceProgramUnearnedIncome()
	}


	/*static void uploadProofOfUnearnedTcaIncome(WebDriver driver, WebDriverWait wait, int timeoutSeconds = 15) {
	 By uploadTcaBtn = By.xpath("//button[@type='button' and .//img[@alt='add']]")
	 new core.ClickUtils().waitAndClick(uploadTcaBtn, timeoutSeconds)
	 String identityTcaFile = RunConfiguration.getProjectDir() + "/Data Files/ProofofTcaIncome.pdf"
	 By fileTcaInputBy = By.id("contained-button-file")
	 wait.until(ExpectedConditions.presenceOfElementLocated(fileTcaInputBy))
	 driver.findElement(fileTcaInputBy).sendKeys(identityTcaFile)
	 By uploadTcaConfirm = By.xpath("//button[contains(@class,'btn-primary') and @title='Upload']")
	 new core.ClickUtils().waitAndClick(uploadTcaConfirm, timeoutSeconds)
	 WebUI.comment("File uploaded successfully")
	 }*/

	static void addSupplementalNutritionAssistanceProgramUnearnedIncome() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		String householdSnapQuestion = "//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Do you or anyone in the household get SNAP (Supplemental Nutrition Assistance Program)?']]"

		String householdSnapYesXpath = householdSnapQuestion + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String householdSnapSelectedYesXpath = householdSnapQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				householdSnapYesXpath,
				householdSnapSelectedYesXpath,
				"Yes")


		By addSnapButton = By.xpath("//button[.//span[normalize-space()='Add SNAP Benefit']]")
		ClickUtils.waitAndClick(addSnapButton, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//div[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath(
				"//label[.//span[contains(@class,'labelStar') and normalize-space()='*'] and .//span[normalize-space()='Which household member is receiving the other income?']]",
				"* Which household member is receiving the other income?")

		By snapHouseholdMemberDropdown = By.xpath("//div[@id='mui-component-select-memberType']")
		ClickUtils.waitAndClick(snapHouseholdMemberDropdown, TIMEOUT)

		By snapHouseholdMemberOption = By.xpath("(//ul[@role='listbox']//li[@role='option'])[1]")
		ClickUtils.waitAndClick(snapHouseholdMemberOption, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* Income Type']",
				"* Income Type")

		By snapHouseholdIncomeTypeDropdown = By.xpath("//div[@id='mui-component-select-incomeSource']")
		ClickUtils.waitAndClick(snapHouseholdIncomeTypeDropdown, TIMEOUT)

		By snapHouseholdIncomeTypeOption = By.xpath("(//ul[@role='listbox']//li[@role='option'])[1]")
		ClickUtils.waitAndClick(snapHouseholdIncomeTypeOption, TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		By snapSaveBtn=By.xpath("//button[@type='button' and contains(@class,'create-accnt') and .//span[normalize-space()='Save']]")
		ClickUtils.waitAndClick(snapSaveBtn, TIMEOUT)

		By notJobOtherIncomeSourcesQuestion = By.xpath(
				"//p[contains(@class,'input-label') and " +
				".//span[contains(@class,'labelStar')] and " +
				".//span[normalize-space()='Do you or anyone in your household have income from one or more sources other than a job?']]")
		wait.until(ExpectedConditions.visibilityOfElementLocated(notJobOtherIncomeSourcesQuestion))

		addOtherIncomeSourceUnearnedIncome()
	}

	static void addOtherIncomeSourceUnearnedIncome() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		String notJobOtherIncomeSourcesQuestion = "//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Do you or anyone in your household have income from one or more sources other than a job?']]"

		String notJobOtherIncomeSourcesYesXpath = notJobOtherIncomeSourcesQuestion + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String notJobOtherIncomeSourcesSelectedYesXpath = notJobOtherIncomeSourcesQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				notJobOtherIncomeSourcesYesXpath,
				notJobOtherIncomeSourcesSelectedYesXpath,
				"Yes")


		By addOtherIncomeSourceBtn = By.xpath("//button[contains(@class,'add-btn') and .//span[contains(normalize-space(), 'Add Other Income Source')]]")
		ClickUtils.waitAndClick(addOtherIncomeSourceBtn, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//div[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath(
				"//label[.//span[normalize-space()='Which household member is receiving the other income?']]",
				"* Which household member is receiving the other income?")

		By otherSourceIncomeHouseholdMemberDropdown = By.xpath("//div[@id='mui-component-select-memberType']")
		ClickUtils.waitAndClick(otherSourceIncomeHouseholdMemberDropdown, TIMEOUT)

		By otherSourceIncomeHouseholdMemberOption = By.xpath("(//ul[@role='listbox' and not(@aria-hidden='true')]//li[@role='option'])[1]")
		ClickUtils.waitAndClick(otherSourceIncomeHouseholdMemberOption, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space()='* Income Type']",
				"* Income Type")

		By otherSourceIncomeTypeDropdown = By.xpath("//div[@id='mui-component-select-incomeSource']")
		ClickUtils.waitAndClick(otherSourceIncomeTypeDropdown, TIMEOUT)

		By otherSourceIncomeTypeOption = By.xpath("(//ul[@role='listbox' and not(@aria-hidden='true')]//li[@role='option'])[6]")
		ClickUtils.waitAndClick(otherSourceIncomeTypeOption, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//label[normalize-space(.)='* How often does the household member receive this income?']",
				"* How often does the household member receive this income?")

		By otherSourceIncomeFrequencyDropdown = By.xpath("//div[@id='mui-component-select-paySchedule']")
		ClickUtils.waitAndClick(otherSourceIncomeFrequencyDropdown, TIMEOUT)

		By otherSourceIncomeFrequencyOption = By.xpath("(//ul[@role='listbox' and not(@aria-hidden='true')]//li[@role='option'])[5]")
		ClickUtils.waitAndClick(otherSourceIncomeFrequencyOption, TIMEOUT)


		VerifyUtils.verifyTextByXPath(
				"//label[@for='childSupportAmount' and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='How much does the household member receive?']]",
				"* How much does the household member receive?")
		By otherSourceIncomeAmount = By.xpath("//input[@name='amount' and @type='text' and @aria-label='amount']")
		FormFieldUtils.populateOrAssertTextField(driver, otherSourceIncomeAmount, '.*', "250.00")


		VerifyUtils.verifyTextByXPath(
				"//label[contains(@class,'clsFormDateLabel') and .//span[contains(@class,'labelStar')] and contains(normalize-space(.), 'When did the household member start receiving this income?')]",
				"* When did the household member start receiving this income?")

		/*By otherSourceIncomeCalendarYearHeader = By.xpath("//div[contains(@class,'MuiPickersCalendarHeader') or contains(@class,'MuiPickersToolbar')]//*[normalize-space()='2025']")
		 WebElement otherSourceIncomeCalendarYearHeaderEl = wait.until(ExpectedConditions.elementToBeClickable(otherSourceIncomeCalendarYearHeader))
		 js.executeScript("arguments[0].scrollIntoView({block:'center'});", otherSourceIncomeCalendarYearHeaderEl)
		 otherSourceIncomeCalendarYearHeaderEl.click()
		 By otherSourceIncomeBeginWorkingYear = By.xpath("//*[contains(@class,'MuiPickersYear') or @role='button'][normalize-space()='2025']")
		 WebElement otherSourceIncomeBeginWorkingYearEl = wait.until(ExpectedConditions.elementToBeClickable(otherSourceIncomeBeginWorkingYear))
		 js.executeScript("arguments[0].scrollIntoView({block:'center'});", otherSourceIncomeBeginWorkingYearEl)
		 otherSourceIncomeBeginWorkingYearEl.click()
		 By otherSourceIncomeCalendarMonthYearHeader = By.xpath("//*[contains(text(),'March 2025')]")
		 wait.until(ExpectedConditions.visibilityOfElementLocated(otherSourceIncomeCalendarMonthYearHeader))
		 By otherSourceIncomeBeginWorkingDay = By.xpath(
		 "//button[normalize-space()='23' and not(@disabled)] | " +
		 "//*[contains(@class,'MuiPickersDay') and normalize-space()='23' and not(contains(@class,'Mui-disabled'))]")
		 WebElement otherSourceIncomeBeginWorkingDayEl = wait.until(ExpectedConditions.elementToBeClickable(otherSourceIncomeBeginWorkingDay))
		 js.executeScript("arguments[0].scrollIntoView({block:'center'});", otherSourceIncomeBeginWorkingDayEl)
		 otherSourceIncomeBeginWorkingDayEl.click()
		 By otherSourceIncomeCalendarOkButton = By.xpath("//button[normalize-space()='OK']")
		 WebElement otherSourceIncomeCalendarOkBtn = wait.until(ExpectedConditions.elementToBeClickable(otherSourceIncomeCalendarOkButton))
		 otherSourceIncomeCalendarOkBtn.click()*/

		/*BirthDateUtils.selectDateFromCalendar(
		 driver,
		 wait,
		 "//label[contains(@class,'clsFormDateLabel') and .//span[contains(@class,'labelStar')] and contains(normalize-space(.), 'When did the household member start receiving this income?')]",
		 By.xpath("//button[@type='button' and contains(@aria-label,'When did the household member start receiving this income?')]"),
		 By.xpath("//input[@id='date-picker-dialog']"),
		 "* When did the household member start receiving this income?",
		 "2019",
		 "June 2019",
		 "13",
		 TIMEOUT)*/

		By jobStartDateField = By.xpath("//input[@id='date-picker-dialog']")
		DateOfBirthUtils.enterDateDirectly(jobStartDateField, "06/13/2019", TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//p[contains(@class,'doc-head') and contains(@class,'inc_head') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Upload Proof of Unearned Income']]",
				"* Upload Proof of Unearned Income")

		//uploadProofOfUnearnedOtherSourceIncome(driver, wait, TIMEOUT)

		String proofOfOtherUnearnedIncomeFile = RunConfiguration.getProjectDir() + "/Data Files/OtherSourceIncome.pdf"

		DataFileUploadUtils.uploadDocumentBySectionLabel(
				driver,
				"Upload Proof of Unearned Income",
				proofOfOtherUnearnedIncomeFile,
				TIMEOUT)


		By unearnedOtherSourceIncomeSaveBtn = By.xpath("//button[@type='button' and contains(@class,'create-accnt') and .//span[normalize-space()='Save']]")

		ClickUtils.waitAndClick(unearnedOtherSourceIncomeSaveBtn, TIMEOUT)

		By statesChildCareScholarshipQuestion = By.xpath(
				"//p[contains(@class,'input-label') and " +
				".//span[contains(@class,'labelStar')] and " +
				".//span[normalize-space()='Are you currently receiving child care subsidy from the State’s Child Care Scholarship Program (CCS)?']]")
		wait.until(ExpectedConditions.visibilityOfElementLocated(statesChildCareScholarshipQuestion))

		statesChildCareScholarshipProgram()
	}

	/*static void uploadProofOfUnearnedOtherSourceIncome(WebDriver driver, WebDriverWait wait, int timeoutSeconds = 15) {
	 By uploadUnearnedOtherIncomeBtn = By.xpath("//button[@type='button' and .//img[@alt='add']]")
	 new core.ClickUtils().waitAndClick(uploadUnearnedOtherIncomeBtn, timeoutSeconds)
	 String identityUnearnedOtherIncomeFile = RunConfiguration.getProjectDir() + "/Data Files/OtherSourceIncome.pdf"
	 By fileUnearnedOtherIncomeInputBy = By.id("contained-button-file")
	 wait.until(ExpectedConditions.presenceOfElementLocated(fileUnearnedOtherIncomeInputBy))
	 driver.findElement(fileUnearnedOtherIncomeInputBy).sendKeys(identityUnearnedOtherIncomeFile)
	 By uploadUnearnedOtherIncomeConfirm = By.xpath("//button[contains(@class,'btn-primary') and @title='Upload']")
	 new core.ClickUtils().waitAndClick(uploadUnearnedOtherIncomeConfirm, timeoutSeconds)
	 WebUI.comment("File uploaded successfully")
	 }*/

	static void statesChildCareScholarshipProgram() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		String statesChildCareScholarshipQuestion = "//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Are you currently receiving child care subsidy from the State’s Child Care Scholarship Program (CCS)?']]"

		String statesChildCareScholarshipYesXpath = statesChildCareScholarshipQuestion + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String statesChildCareScholarshipSelectedYesXpath = statesChildCareScholarshipQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				statesChildCareScholarshipYesXpath,
				statesChildCareScholarshipSelectedYesXpath,
				"Yes")


		WebUI.delay(5)
		clickEnabledSaveNext(driver, wait)
	}

	// -------------------------
	// Helper Reusable Methods
	// -------------------------
	private static void pressEsc(WebDriver driver) {
		driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE)
	}

	private static void clickWithScroll(WebDriver driver, WebDriverWait wait, By locator) {
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator))
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el)
		el.click()
	}

	private static void clickEnabledSaveNext(WebDriver driver, WebDriverWait wait) {
		By saveNext = By.xpath("//button[.//span[normalize-space()='Save & Next'] and not(contains(@class,'btn-disabled'))]")
		WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(saveNext))
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn)
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", btn)
	}

	private static void selectYesIfNotSelected(WebDriver driver, WebDriverWait wait, String yesXpath, String selectedXpath) {

		By yesOption = By.xpath(yesXpath)
		By yesSelected = By.xpath(selectedXpath)

		JavascriptExecutor js = (JavascriptExecutor) driver

		// If already selected → just assert
		if (!driver.findElements(yesSelected).isEmpty()) {
			println("'Yes' is already selected.")
		} else {

			println("'Yes' is not selected. Clicking it now.")

			WebElement yesEl = wait.until(ExpectedConditions.elementToBeClickable(yesOption))
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", yesEl)

			try {
				yesEl.click()
			} catch (Exception e) {
				// JS fallback (helps with MUI toggle issues)
				js.executeScript("arguments[0].click();", yesEl)
			}

			// Wait until it becomes selected
			wait.until(ExpectedConditions.presenceOfElementLocated(yesSelected))
		}

		// Final assertion
		if (driver.findElements(yesSelected).isEmpty()) {
			throw new AssertionError("ASSERTION FAILED — 'Yes' not selected for: " + yesXpath)
		}

		println("ASSERTION PASSED — 'Yes' is selected.")
	}

	private static void selectFirstVisibleListboxOption(WebDriver driver, WebDriverWait wait) {
		By options = By.xpath("//ul[@role='listbox']//li[@role='option' and not(contains(@style,'display: none'))]")
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@role='listbox']")))

		List<WebElement> opts = driver.findElements(options)
		if (opts == null || opts.isEmpty()) {
			throw new AssertionError("No visible listbox options found.")
		}
		WebElement first = opts.get(0)
		first.click()
	}

	private static String generateRandomName() {
		String[] names = [
			"John",
			"Amy",
			"Chris",
			"Sophia",
			"Mark",
			"Lisa",
			"David",
			"Emma"
		]
		return names[new Random().nextInt(names.length)]
	}

	private static String generateEmployerName() {
		String[] employerNames = [
			"ABC Company",
			"MSG Company",
			"LMN Company",
			"MAR Company",
			"LIS Company",
			"DAV Company",
			"SOH Company"
		]
		return employerNames[new Random().nextInt(employerNames.length)]
	}

	private static final Random RANDOM = new Random()

	private static final String[] EMPLOYER_EMAILS = [
		"abcemployer@gmail.com",
		"msgemployer@gmail.com",
		"lmnemployer@gmail.com",
		"maremployer@gmail.com",
		"lisemployer@gmail.com",
		"davemployer@gmail.com",
		"sohemployer@gmail.com"
	]

	private static String generateRandomEmail() {
		return EMPLOYER_EMAILS[RANDOM.nextInt(EMPLOYER_EMAILS.length)]
	}

	private static String generateRandomSSN() {
		List<String> ssns = ["615-67-8765", "987-76-3456"]
		return ssns[new Random().nextInt(ssns.size())]
	}

	private static String generateRandomMobile() {
		List<String> phones = [
			"301-709-8765",
			"202-760-3456",
			"610-772-3456"
		]
		return phones[new Random().nextInt(phones.size())]
	}

	private static String generateRandomIdNumber() {
		List<String> ids = [
			"MD-8797676",
			"MD-76432123456"
		]
		return ids[new Random().nextInt(ids.size())]
	}

	private static String generateRandomIssuer() {
		List<String> issuers = [
			"State of Maryland",
			"United States"
		]
		return issuers[new Random().nextInt(issuers.size())]
	}

	private static void closeMuiOverlays(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver
		try {
			new Actions(driver)
					.sendKeys(Keys.ESCAPE).pause(Duration.ofMillis(150))
					.sendKeys(Keys.ESCAPE).perform()
		} catch (Exception ignore) {}

		try {
			List<WebElement> backdrops = driver.findElements(By.cssSelector(".MuiBackdrop-root"))
			if (backdrops != null && !backdrops.isEmpty()) {
				js.executeScript("arguments[0].click();", backdrops.get(0))
			}
		} catch (Exception ignore) {}

		try {
			js.executeScript("document.body.click();")
		} catch (Exception ignore) {}
	}

	private static void closeMuiDropdown(WebDriver driver, WebDriverWait wait) {
		By listbox = By.xpath("//ul[@role='listbox']")

		try {
			WebElement active = driver.switchTo().activeElement()
			active.sendKeys(Keys.ESCAPE)
			wait.until(ExpectedConditions.invisibilityOfElementLocated(listbox))
			return
		} catch (Exception ignore) {
		}

		try {
			WebElement body = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")))
			body.click()
			wait.until(ExpectedConditions.invisibilityOfElementLocated(listbox))
			return
		} catch (Exception ignore) {
		}

		try {
			((JavascriptExecutor) driver).executeScript("document.body.click();")
			wait.until(ExpectedConditions.invisibilityOfElementLocated(listbox))
		} catch (Exception ignore) {
		}
	}

	private static void safeClick(JavascriptExecutor js, WebDriverWait wait, By by) {
		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(by))
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", el)
		try {
			el.click()
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", el)
		}
	}
}
