import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.configuration.RunConfiguration
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

public class NonCustodialParent {
	private static final int TIMEOUT = 15
	private static int timeoutSeconds = 10

	@Keyword
	static void addNonCustodialParent() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		VerifyUtils.verifyTextByXPath(
				'//h3[normalize-space()="Tell us about your children\'s non-custodial parent"]',
				"Tell us about your children's non-custodial parent")

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		String nonCustodialParentMainQuestion = "//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Do any of the children in your household have a parent that does not live in the household with you?']]"

		String nonCustodialParentMainYesXpath = nonCustodialParentMainQuestion +
				"/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String nonCustodialParentMainSelectedYesXpath = nonCustodialParentMainQuestion +
				"/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				nonCustodialParentMainYesXpath,
				nonCustodialParentMainSelectedYesXpath,
				"Yes")

		By addNonCustodialParentBtn = By.xpath("//button[normalize-space()='Add Non-Custodial Parent']")
		ClickUtils.waitAndClick(addNonCustodialParentBtn, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//div[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath(
				"//label[.//span[contains(@class,'labelStar')] and .//span[normalize-space()='Which child(ren) is related to this non-custodial parent?']]",
				"* Which child(ren) is related to this non-custodial parent?")

		By childForNonCustodialParent = By.xpath("//div[@id='mui-component-select-appHouseHoldChildID']")
		ClickUtils.waitAndClick(childForNonCustodialParent, timeoutSeconds)

		// Wait for dropdown/listbox
		By childForNonCustodialParentListbox = By.xpath("//ul[@role='listbox']")
		wait.until(ExpectedConditions.visibilityOfElementLocated(childForNonCustodialParentListbox))

		// Click first visible option
		By firstChildOption = By.xpath("(//ul[@role='listbox']//li[@role='option'])[1]")
		WebElement firstChildOptionEl = wait.until(ExpectedConditions.elementToBeClickable(firstChildOption))
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", firstChildOptionEl)
		js.executeScript("arguments[0].click();", firstChildOptionEl)

		// Close dropdown by clicking the select field again
		WebElement dropdownEl = wait.until(ExpectedConditions.elementToBeClickable(childForNonCustodialParent))
		js.executeScript("arguments[0].click();", dropdownEl)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath("//label[normalize-space()='Prefix']", "Prefix")
		By childForNonCustodialParentPrefixDropdown = By.xpath("//div[@id='mui-component-select-prefix']")
		ClickUtils.waitAndClick(childForNonCustodialParentPrefixDropdown, TIMEOUT)
		DropdownUtils.selectDropDownOption(childForNonCustodialParentPrefixDropdown, "Dr.", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		String nonCustodialParentFirstName = generateRandomFirstName()
		String nonCustodialParentLastName = generateRandomLastName()

		VerifyUtils.verifyTextByXPath("//label[@id='firstName-label']", "* First name of non-custodial parent")
		By nonCustodialParentFirstNameField = By.xpath("//input[@id='firstName']")
		FormFieldUtils.populateOrAssertTextField(driver, nonCustodialParentFirstNameField, ".*", nonCustodialParentFirstName)


		VerifyUtils.verifyTextByXPath("//label[@id='middleName-label']", "Middle Name")

		VerifyUtils.verifyTextByXPath("//label[@id='lastName-label']", "* Last name of non-custodial parent")
		By nonCustodialParentLastNameField = By.xpath("//input[@id='lastName']")
		FormFieldUtils.populateOrAssertTextField(driver, nonCustodialParentLastNameField, ".*", nonCustodialParentLastName)


		VerifyUtils.verifyTextByXPath("//label[normalize-space()='Suffix']", "Suffix")
		By nonCustodialParentSuffixDropdown = By.xpath("//div[@id='mui-component-select-suffix']")
		ClickUtils.waitAndClick(nonCustodialParentSuffixDropdown, TIMEOUT)
		DropdownUtils.selectDropDownOption(nonCustodialParentSuffixDropdown, "Sr.", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath(
				"//label[contains(@class,'input-label') and normalize-space()=\"What is this non-custodial parent's race?\"]",
				"What is this non-custodial parent's race?")
		By nonCustodialParentRaceOption = By.xpath("//div[@id='mui-component-select-race']")
		RaceUtils.clearAndSelectTwoOrThreeRaceOptions(nonCustodialParentRaceOption, TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)


		VerifyUtils.verifyTextByXPath(
				"//label[contains(normalize-space(.), \"What is this non-custodial parent's ethnicity?\")]",
				"What is this non-custodial parent's ethnicity?")
		By nonCustodialParentEthnicityDropdown = By.xpath("//div[@role='button' and @id='mui-component-select-ethinicity']")
		DropdownUtils.selectDropDownOption(nonCustodialParentEthnicityDropdown, "Not Hispanic or Latino", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath(
				"//label[contains(@class,'clsFormDateLabel') and normalize-space()='Date of Birth']",
				"Date of Birth")

		// Date of Birth
		BirthDateUtils.selectDateFromCalendar(
				driver,
				wait,
				"//label[contains(@class,'clsFormDateLabel') and normalize-space()='Date of Birth']",
				By.xpath("//button[@aria-label='Date of Birth']"),
				By.xpath("//input[@id='date-picker-dialog']"),
				"Date of Birth",
				"1979",
				"March 1979",
				"19",
				TIMEOUT)
		
		/*By nonCustodialParentDob = By.xpath("//input[@id='date-picker-dialog']")
		DateOfBirthUtils.enterDateDirectly(nonCustodialParentDob, "03/19/1979", TIMEOUT)*/

		//Gender
		VerifyUtils.verifyTextByXPath("//label[contains(normalize-space(.), '* Gender')]", "* Gender")
		By nonCustodialParentGenderDropdown = By.xpath("//div[@id='mui-component-select-gender']")
		DropdownUtils.selectDropDownOption(nonCustodialParentGenderDropdown, "Female", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath("//label[normalize-space()='SSN']", "SSN")

		By nonCustodialParentSsnInput = By.xpath("//input[@id='socialSecurityNumber']")

		FormFieldUtils.populateOrAssertTextField(
				driver,
				nonCustodialParentSsnInput,
				"regex:\\d{3}-\\d{2}-\\d{4}",
				generateRandomSocialSecurityNumber())
		OverlayUtils.closeMuiOverlays(driver, TIMEOUT)

		String nonCustodialParentAddressQuestion = "//p[contains(@class,'input-label') and normalize-space()='Do you know the non-custodial parent’s address?']"

		String nonCustodialParentAddressYesXpath = nonCustodialParentAddressQuestion + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='No']"

		String nonCustodialParentAddressSelectedYesXpath = nonCustodialParentAddressQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='No']"


		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				nonCustodialParentAddressYesXpath,
				nonCustodialParentAddressSelectedYesXpath,
				"No")


		String nonCustodialParentChildSupportQuestion = "//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Does this parent pay child support?']]"

		String nonCustodialParentChildSupportYesXpath = nonCustodialParentChildSupportQuestion + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='No']"

		String nonCustodialParentChildSupportSelectedYesXpath = nonCustodialParentChildSupportQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='No']"


		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				nonCustodialParentChildSupportYesXpath,
				nonCustodialParentChildSupportSelectedYesXpath,
				"No")

		/*VerifyUtils.verifyTextByXPath(
		 "//label[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='How often do they pay?']]",
		 "* How often do they pay?")
		 By payScheduleDropdown = By.xpath("//div[@id='mui-component-select-paySchedule']")
		 DropdownUtils.selectDropDownOption(payScheduleDropdown, "Monthly", TIMEOUT)
		 CloseDropdownUtils.closeMuiDropdown(driver, wait)
		 VerifyUtils.verifyTextByXPath(
		 "//label[@for='childSupportAmount' and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Child Support Amount']]",
		 "* Child Support Amount")
		 By nonCustodialParentChildSupportAmountInput = By.xpath("//input[@name='childSupportAmount' and @aria-label='childSupportAmount']")
		 WebElement amountEl = wait.until(ExpectedConditions.visibilityOfElementLocated(nonCustodialParentChildSupportAmountInput))
		 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", amountEl)
		 amountEl.click()
		 amountEl.sendKeys(Keys.chord(Keys.CONTROL, "a"))
		 amountEl.sendKeys("900.00")
		 amountEl.sendKeys(Keys.TAB)
		 String enteredAmount = amountEl.getAttribute("value")
		 assert enteredAmount == "900.00" : "Child Support Amount was not populated. Actual value: '${enteredAmount}'"
		 VerifyUtils.verifyTextByXPath("//p[contains(@class,'doc-head') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Upload Proof of Child Support Income']]",
		 "* Upload Proof of Child Support Income")
		 String nonCustParentChildSupportIncomeFile = RunConfiguration.getProjectDir() + "/Data Files/NonCustodialSupportIncome.pdf"
		 DataFileUploadUtils.uploadDocumentBySectionLabel(
		 driver,
		 "Upload Proof of Child Support Income",
		 nonCustParentChildSupportIncomeFile,
		 TIMEOUT)*/

		ButtonClickUtils.clickEnabledSaveNext(driver, wait)

		//uploadProofOfNonCustodialParentChildSupportIncome(driver, wait, TIMEOUT)
		//clickEnabledSaveNext(driver, wait)

		//clickEnabledSaveButton(driver, wait)

		//WebUI.delay(5)

		//clickEnabledSaveButton(driver, wait)

		//WebUI.delay(5)

		//clickNoneCustodialParentMainYesBtn(driver, wait)

		By nonCustodialParentSaveBtn = By.xpath("//button[@type='button' and contains(@class,'btn-primary') and .//span[normalize-space()='Save']]")
		ClickUtils.waitAndClick(nonCustodialParentSaveBtn, 20)

		By nonCustodialParentSaveMainBtn = By.xpath("//button[@type='button' and contains(@class,'btn-primary') and .//span[normalize-space()='Save']]")

		ClickUtils.waitAndClick(nonCustodialParentSaveMainBtn, 20)

		WebUI.delay(5)
		By nonCustodialParentMainYesBtn = By.xpath("//button[contains(@class,'ajs-ok') and normalize-space()='Yes']")
		ClickUtils.waitAndClick(nonCustodialParentMainYesBtn, 20)
	}

	/*static void uploadProofOfNonCustodialParentChildSupportIncome(WebDriver driver, WebDriverWait wait, int timeoutSeconds = 15) {
	 By uploadBtn = By.xpath("//button[@type='button' and .//img[@alt='add']]")
	 CustomKeywords.'core.ClickUtils.waitAndClick'(uploadBtn, timeoutSeconds)
	 String identityFile = RunConfiguration.getProjectDir() + "/Data Files/NonCustodialParentSupportIncome.pdf"
	 By fileInputBy = By.id("contained-button-file")
	 wait.until(ExpectedConditions.presenceOfElementLocated(fileInputBy))
	 driver.findElement(fileInputBy).sendKeys(identityFile)
	 By uploadConfirm = By.xpath("//button[contains(@class,'btn-primary') and @title='Upload']")
	 CustomKeywords.'core.ClickUtils.waitAndClick'(uploadConfirm, timeoutSeconds)
	 WebUI.comment("File uploaded successfully")
	 }*/

	// -------------------------
	// Helper Methods
	// -------------------------

	private static void clickEnabledSaveNext(WebDriver driver, WebDriverWait wait) {
		By saveNext = By.xpath("//button[.//span[normalize-space()='Save & Next'] and not(contains(@class,'btn-disabled'))]")
		WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(saveNext))
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn)
		try {
			btn.click()
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn)
		}
	}

	private static void clickEnabledSaveButton(WebDriver driver, WebDriverWait wait) {
		By saveButton = By.xpath("//button[contains(@class,'btn-primary') and .//span[normalize-space()='Save']]")
		WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveButton))
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", saveBtn)
		try {
			saveBtn.click()
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn)
		}
	}

	private static void clickNoneCustodialParentMainYesBtn(WebDriver driver, WebDriverWait wait) {
		By noneCustodialParentMainYesButton = By.xpath("//button[contains(@class,'ajs-ok') and normalize-space()='Yes']")
		WebElement noneCustodialParentMainYesBtn = wait.until(ExpectedConditions.elementToBeClickable(noneCustodialParentMainYesButton))
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", noneCustodialParentMainYesBtn)
		try {
			noneCustodialParentMainYesBtn.click()
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", noneCustodialParentMainYesBtn)
		}
	}

	By nonCustodialParentMainYesBtn = By.xpath("//button[contains(@class,'ajs-ok') and normalize-space()='Yes']")

	private static void clickWithScroll(WebDriver driver, WebDriverWait wait, By locator) {
		WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator))
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el)
		try {
			el.click()
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", el)
		}
	}

	private static void selectFirstVisibleListboxOption(WebDriver driver, WebDriverWait wait) {
		By listbox = By.xpath("//ul[@role='listbox']")
		By options = By.xpath("//ul[@role='listbox']//li[@role='option']")

		wait.until(ExpectedConditions.visibilityOfElementLocated(listbox))
		List<WebElement> opts = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(options))

		WebElement firstVisibleEnabled = opts.find { WebElement el ->
			el.isDisplayed() && el.isEnabled() && !isMuiDisabled(el) && (el.getText()?.trim())
		}

		if (firstVisibleEnabled == null) {
			throw new AssertionError("No visible enabled listbox options found.")
		}

		try {
			firstVisibleEnabled.click()
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstVisibleEnabled)
		}
	}

	private static String generateRandomFirstName() {
		String[] firstNames = [
			"ccssNonCustodialFirst",
			"headNonCustodialFirst",
			"adultNonCustodialFirst",
			"theNonCustodialFirst",
			"NonCustodialParentFirst",
			"meNonCustodialFirst",
			"nonCustodialFirst"
		]
		return firstNames[new Random().nextInt(firstNames.length)]
	}

	private static String generateRandomLastName() {
		String[] lastNames = [
			"ccssNonCustodialLast",
			"headNonCustodialLast",
			"adultNonCustodialLast",
			"theNonCustodialLast",
			"NonCustodialParentLast",
			"meNonCustodialLast",
			"nonCustodialLast"
		]
		return lastNames[new Random().nextInt(lastNames.length)]
	}

	private static String generateRandomSocialSecurityNumber() {
		List<String> childSsns = ["615-67-8765", "987-76-3456"]
		return childSsns[new Random().nextInt(childSsns.size())]
	}
}