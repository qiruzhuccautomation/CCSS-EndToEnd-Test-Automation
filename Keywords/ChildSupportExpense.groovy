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
import com.kms.katalon.core.webui.driver.DriverFactory

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



public class ChildSupportExpense {

	private static final int TIMEOUT = 15
	private static int timeoutSeconds = 10

	@Keyword
	static void addChildSupportExpense() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		VerifyUtils.verifyTextByXPath(
				"//h3[normalize-space()='Tell us about your household Expense']",
				"Tell us about your household Expense")

		VerifyUtils.verifyTextByXPath(
				"//p[.//span[contains(@class,'labelStar') and normalize-space()='*']]",
				"* Do you or your spouse pay court ordered child support to a child outside your home?")

		String courtOrderedChildSupportQuestion = "//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Do you or your spouse pay court ordered child support to a child outside your home?']]"

		String courtOrderedChildSupportYesXpath = courtOrderedChildSupportQuestion + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String courtOrderedChildSupportSelectedYesXpath = courtOrderedChildSupportQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				courtOrderedChildSupportYesXpath,
				courtOrderedChildSupportSelectedYesXpath,
				"Yes")

		By addChildSupportExpenseBtn = By.xpath("//button[contains(@class,'add-btn') and .//img[@alt='add'] and contains(normalize-space(.), 'Add Child Support Expense')]")
		ClickUtils.waitAndClick(addChildSupportExpenseBtn, timeoutSeconds)

		WebUI.delay(5)

		VerifyUtils.verifyTextByXPath(
				"//h4[normalize-space()='Child Support Expense']",
				"Child Support Expense")

		VerifyUtils.verifyTextByXPath(
				"//div[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath("//label[.//span[contains(@class,'labelStar') and normalize-space()='*'] and .//span[normalize-space()='Name']]", "* Name")

		By householdMemberNameDropdown = By.xpath("//div[@id='mui-component-select-memberType']")
		ClickUtils.waitAndClick(householdMemberNameDropdown, TIMEOUT)
		By firstHouseholdMemberNameOption = By.xpath("//ul[@role='listbox']//li[@role='option'][1]")
		ClickUtils.waitAndClick(firstHouseholdMemberNameOption, TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath(
				"//label[.//span[contains(@class,'labelStar') and normalize-space()='*'] and .//span[normalize-space()='Expense Type']]",
				"* Expense Type")

		By childSupportExpenseTypeDropdown = By.xpath("//div[@id='mui-component-select-expenseName']")
		ClickUtils.waitAndClick(childSupportExpenseTypeDropdown, TIMEOUT)

		By childSupportExpenseTypeOption = By.xpath("(//ul[@role='listbox' and not(@aria-hidden='true')]//li[@role='option'])[1]")
		ClickUtils.waitAndClick(childSupportExpenseTypeOption, TIMEOUT)

		VerifyUtils.verifyTextByXPath(
				"//label[@for='childSupportAmount' and .//span[contains(@class,'labelStar') and normalize-space()='*'] and .//span[normalize-space()='What is the amount?']]",
				"* What is the amount?")

		By childSupportExpenseAmount = By.xpath("//input[@name='amount' and @type='text' and @aria-label='amount']")
		FormFieldUtils.populateOrAssertTextField(driver, childSupportExpenseAmount, '.*', "700.00")

		VerifyUtils.verifyTextByXPath(
				"//label[.//span[contains(@class,'labelStar') and normalize-space()='*'] and .//span[normalize-space()='What is the frequency?']]",
				"* What is the frequency?")

		By childSupportExpenseFrequencyDropdown = By.xpath("//div[@id='mui-component-select-expenseFrequency']")
		ClickUtils.waitAndClick(childSupportExpenseFrequencyDropdown, TIMEOUT)

		By childSupportExpenseFrequencyOption = By.xpath("(//ul[@role='listbox' and not(@aria-hidden='true')]//li[@role='option'])[5]")
		ClickUtils.waitAndClick(childSupportExpenseFrequencyOption, TIMEOUT)



		VerifyUtils.verifyTextByXPath(
				"//p[contains(@class,'doc-head') and contains(@class,'inc_head') and .//span[contains(@class,'labelStar') and normalize-space()='*'] and .//span[normalize-space()='Upload Proof of Child Support Expense']]",
				"* Upload Proof of Child Support Expense")


		By disabledSaveButton = By.xpath("//button[@type='button' and contains(@class,'btn-disabled') and .//span[normalize-space()='Save']]")

		WebElement disabledSaveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(disabledSaveButton))
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", disabledSaveBtn)
		js.executeScript("arguments[0].click();", disabledSaveBtn)


		By okButton = By.xpath("//button[@type='button' and .//span[normalize-space()='OK']]")
		WebElement okBtn = wait.until(ExpectedConditions.presenceOfElementLocated(okButton))
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", okBtn)
		js.executeScript("arguments[0].click();", okBtn)

		String childSupportExpenseFile = RunConfiguration.getProjectDir() + "/Data Files/ChildSupportExpense.pdf"

		DataFileUploadUtils.uploadDocumentBySectionLabel(
				driver,
				"Upload Proof of Child Support Expense",
				childSupportExpenseFile,
				TIMEOUT)

		By childSupportExpenseSaveBtn = By.xpath("//button[contains(@class,'btn-primary') and .//span[normalize-space()='Save']]")
		ClickUtils.waitAndClick(childSupportExpenseSaveBtn, timeoutSeconds)

		By childSupportExpenseMainSaveBtn = By.xpath("//button[contains(@class,'btn-primary') and .//span[normalize-space()='Save']]")
		ClickUtils.waitAndClick(childSupportExpenseMainSaveBtn, timeoutSeconds)

		By childSupportExpenseMainYesBtn = By.xpath("//button[contains(@class,'ajs-ok') and normalize-space()='Yes']")
		ClickUtils.waitAndClick(childSupportExpenseMainYesBtn, timeoutSeconds)
	}


	/*static void uploadProofOfChildSupportExpense(WebDriver driver, WebDriverWait wait, int timeoutSeconds = 15) {
	 By uploadChildSupportExpenseBtn = By.xpath("//button[@type='button' and .//img[@alt='add']]")
	 ClickUtils.waitAndClick(uploadChildSupportExpenseBtn, timeoutSeconds)
	 String identityChildSupportExpenseFile = RunConfiguration.getProjectDir() + "/Data Files/ChildSupportExpense.pdf"
	 By fileChildSupportExpenseInputBy = By.id("contained-button-file")
	 wait.until(ExpectedConditions.presenceOfElementLocated(fileChildSupportExpenseInputBy))
	 driver.findElement(fileChildSupportExpenseInputBy).sendKeys(identityChildSupportExpenseFile)
	 By uploadChildSupportExpenseConfirm = By.xpath("//button[contains(@class,'btn-primary') and @title='Upload']")
	 new core.ClickUtils().waitAndClick(uploadChildSupportExpenseConfirm, timeoutSeconds)
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

	private static String generateRandomSocialSecurityNumber() {
		List<String> childSsns = ["615-67-8765", "987-76-3456"]
		return childSsns[new Random().nextInt(childSsns.size())]
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
}
