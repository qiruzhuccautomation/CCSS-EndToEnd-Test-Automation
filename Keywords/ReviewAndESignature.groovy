import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static org.assertj.core.api.InstanceOfAssertFactories.BYTE

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

import groovy.console.ui.BytecodeCollector
import internal.GlobalVariable
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import static org.testng.Assert.assertTrue

import java.time.Duration
import java.util.Random


public class ReviewAndESignature {
	private static final int TIMEOUT = 15
	private static int timeoutSeconds = 10

	@Keyword
	static void addESignature() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)


		VerifyUtils.verifyTextByXPath("//h3[normalize-space()='Application Summary']", "Application Summary")

		VerifyUtils.verifyTextByXPath("//p[normalize-space()='The information you provided is below. Please use the edit icon to change the information. You will be directed to the section of the application to make the necessary changes. Any changes you make will be saved. DHHS will use the income you provided to determine your eligibility.']",
				"The information you provided is below. Please use the edit icon to change the information. You will be directed to the section of the application to make the necessary changes. Any changes you make will be saved. DHHS will use the income you provided to determine your eligibility.")

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Head of Household (HOH)']", "Head of Household (HOH)")

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Other Household Members (Adult)']", "Other Household Members (Adult)")

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Other Household Members (Child)']", "Other Household Members (Child)")

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Non-Custodial Parent']", "Non-Custodial Parent")

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Child Care Provider Info']", "Child Care Provider Info")

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Authorized Representatives']", "Authorized Representatives")

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Earned Income']", "Earned Income")

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Unearned Income']", "Unearned Income")

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Earned Income']", "Child Support Expense")

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Unearned Income']", "Child Support Income")

		By applicationSummarySaveAndNextBtn = By.xpath("//button[.//span[normalize-space()='Save & Next']]")

		ClickUtils.waitAndClick(applicationSummarySaveAndNextBtn, TIMEOUT)

		VerifyUtils.verifyTextByXPath("//h3[normalize-space()='Agreements']", "Agreements")

		VerifyUtils.verifyTextByXPath("//div[normalize-space()='Fields marked with * are mandatory']", "Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath("//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='To submit your application you must agree to all of the following statements [Yes/No].']]",
				"* To submit your application you must agree to all of the following statements [Yes/No].")

		selectYesIfNotSelected(driver, wait,
				"//p[normalize-space()='* To submit your application you must agree to all of the following statements [Yes/No].']/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']",
				"//p[normalize-space()='* To submit your application you must agree to all of the following statements [Yes/No].']/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']")

		By agreementsBtn = By.xpath("//button[.//span[normalize-space()='Save & Next']]")
		ClickUtils.waitAndClick(agreementsBtn, TIMEOUT)

		VerifyUtils.verifyTextByXPath("//h3[normalize-space()='Electronic Signature']", "Electronic Signature")

		VerifyUtils.verifyTextByXPath("//p[normalize-space()='I confirm with my signature below that all the details provided in the application are true.']", "I confirm with my signature below that all the details provided in the application are true.")

		VerifyUtils.verifyTextByXPath(
				"//div[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath("//label[@id='applicantSignature-label' and contains(@class,'Mui-error') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Signature of Applicant']]", "* Signature of Applicant")
		By signatureOfApplicant = By.xpath("//input[@id='applicantSignature']")
		FormFieldUtils.populateOrAssertTextField(driver, signatureOfApplicant, ".*", 'Adult Auto')

		WebElement ccssApplicationSubmitBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[.//span[normalize-space()='Submit']]")))

		assertTrue(ccssApplicationSubmitBtn.isEnabled(), "Submit button should be enabled")

		WebElement ccssApplicationSaveAndExitBtn = driver.findElement(By.xpath("//button[.//span[normalize-space()='Save & Exit']]"))

		assertTrue(ccssApplicationSaveAndExitBtn.isEnabled(), "Save & Exit button should be enabled")

		WebElement ccssApplicationDeleteBtn = driver.findElement(By.xpath("//button[.//span[normalize-space()='Delete']]"))

		assertTrue(ccssApplicationDeleteBtn.isEnabled(), "Delete button should be enabled")
	}

	// -------------------------
	// Helper Methods
	// -------------------------
	private static void clickIfNotSelected(WebDriver driver, WebDriverWait wait, JavascriptExecutor js, By option, By selected, String label) {
		if (driver.findElements(selected).isEmpty()) {
			WebElement el = wait.until(ExpectedConditions.elementToBeClickable(option))
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", el)
			try {
				el.click()
			} catch (Exception e) {
				js.executeScript("arguments[0].click();", el)
			}
			wait.until(ExpectedConditions.presenceOfElementLocated(selected))
		}

		if (driver.findElements(selected).isEmpty()) {
			throw new AssertionError("ASSERTION FAILED - '${label}' not selected.")
		}
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
	private static boolean isMuiDisabled(WebElement el) {
		String classValue = el.getAttribute("class") ?: ""
		return el.getAttribute("disabled") != null || classValue.contains("Mui-disabled")
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

	private static void selectYesIfNotSelected(WebDriver driver, WebDriverWait wait, String yesXpath, String selectedXpath) {
		By yesOption = By.xpath(yesXpath)
		By yesSelected = By.xpath(selectedXpath)
		JavascriptExecutor js = (JavascriptExecutor) driver

		if (driver.findElements(yesSelected).isEmpty()) {
			WebElement yesEl = wait.until(ExpectedConditions.elementToBeClickable(yesOption))
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", yesEl)
			try {
				yesEl.click()
			} catch (Exception e) {
				js.executeScript("arguments[0].click();", yesEl)
			}
			wait.until(ExpectedConditions.presenceOfElementLocated(yesSelected))
		}

		if (driver.findElements(yesSelected).isEmpty()) {
			throw new AssertionError("ASSERTION FAILED - 'Yes' not selected for: ${yesXpath}")
		}
	}

	private static void selectNoIfNotSelected(WebDriver driver, WebDriverWait wait, String noXpath, String selectedXpath) {
		By noOption = By.xpath(noXpath)
		By noSelected = By.xpath(selectedXpath)
		JavascriptExecutor js = (JavascriptExecutor) driver

		if (driver.findElements(noSelected).isEmpty()) {
			WebElement noEl = wait.until(ExpectedConditions.elementToBeClickable(noOption))
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", noEl)
			try {
				noEl.click()
			} catch (Exception e) {
				js.executeScript("arguments[0].click();", noEl)
			}
			wait.until(ExpectedConditions.presenceOfElementLocated(noSelected))
		}

		if (driver.findElements(noSelected).isEmpty()) {
			throw new AssertionError("ASSERTION FAILED - 'No' not selected for: ${noXpath}")
		}
	}
}
