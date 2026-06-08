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

import java.time.Duration
import java.util.Random
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait


public class ChildCareProvider {
	private static final int TIMEOUT = 15
	private static int timeoutSeconds = 10


	@Keyword
	static void addChildCareProvider() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)


		VerifyUtils.verifyTextByXPath("//h3[normalize-space()='Tell us about your child care provider']", "Tell us about your child care provider")

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Fields marked with * are mandatory']", "Fields marked with * are mandatory")



		String childCareProvidersMainQuestion = "//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Do you wish to add any child care providers?']]"

		String childCareProviderMainYesXpath = childCareProvidersMainQuestion + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String childCareProviderMainSelectedYesXpath = childCareProvidersMainQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				childCareProviderMainYesXpath,
				childCareProviderMainSelectedYesXpath,
				"Yes")

		By addChildCareProviderBtn = By.xpath("//button[@type='button' and .//span[normalize-space()='Add Child Care Provider']]")

		ClickUtils.waitAndClick(addChildCareProviderBtn, TIMEOUT)

		VerifyUtils.verifyTextByXPath("//span[normalize-space()='Fields marked with * are mandatory']", "Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath("//label[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Select provider']]", "* Select provider")

		By selectProviderDropdown = By.xpath("//input[@id='provider-combo-box' and @role='combobox']")

		ClickUtils.waitAndClick(selectProviderDropdown, TIMEOUT)
		DropdownUtils.selectDropDownOption(selectProviderDropdown, "ABC Learning Center", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)


		VerifyUtils.verifyTextByXPath("//label[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Which child is receiving child care?']]",
				"* Which child is receiving child care?")

		By whichChildReceivingCareDropDown = By.xpath("//div[@role='button' and @id='mui-component-select-appHouseHoldChildID']")
		ClickUtils.waitAndClick(whichChildReceivingCareDropDown, TIMEOUT)

		By firstChildOption = By.xpath("//ul[@role='listbox']//li[@role='option'][1]")
		ClickUtils.waitAndClick(firstChildOption, TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath("//label[@id='address-label' and normalize-space()='Address:']", "Address:")

		By addressField = By.xpath("//input[@id='address']")

		FormFieldUtils.populateOrAssertTextField(driver, addressField, "regex:.+", null)

		VerifyUtils.verifyTextByXPath("//label[@id='phone-label' and normalize-space()='Provider Phone Number']", "Provider Phone Number")

		By providerPhoneNumber = By.xpath("//input[@id='phone']")

		FormFieldUtils.populateOrAssertTextField(driver, providerPhoneNumber, 'regex:^\\d{3}-\\d{3}-\\d{4}$', null)

		VerifyUtils.verifyTextByXPath("//label[@id='email-label' and normalize-space()='Provider Email Address']", "Provider Email Address")

		By providerEmailAddress = By.xpath("//input[@id='email']")

		FormFieldUtils.populateOrAssertTextField(driver, providerEmailAddress, 'regex:^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$', null)


		VerifyUtils.verifyTextByXPath("//label[@id='srvC_LOC_TYP-label' and normalize-space()='Provider Type']", "Provider Type")

		By providerType = By.xpath("//input[@id='srvC_LOC_TYP']")

		FormFieldUtils.populateOrAssertTextField(driver, providerType, 'regex:.+', null)


		String childFullTimeCareQuestion = "//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Is the child in full time care?']]"



		String childFullTimeCareYesXpath = childFullTimeCareQuestion  + "/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']"

		String childFullTimeCareSelectedYesXpath = childFullTimeCareQuestion + "/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']"

		YesNoSelectionUtils.selectOptionIfNotSelected(
				driver,
				wait,
				childFullTimeCareYesXpath,
				childFullTimeCareSelectedYesXpath,
				"Yes")

		VerifyUtils.verifyTextByXPath("//label[normalize-space()='How frequently are you paying?']", "How frequently are you paying?")

		By payingFrequencyDropdown = By.xpath("//div[@id='mui-component-select-paymentFrequency']")
		DropdownUtils.selectDropDownOption(payingFrequencyDropdown, "Monthly", TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath("//label[normalize-space()='Fee']", "Fee")

		By childCareProviderFee = By.xpath("//input[@name='weeklyFee' and @type='text' and @aria-label='weeklyFee']")

		WebElement feeField = wait.until(ExpectedConditions.elementToBeClickable(childCareProviderFee))
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", feeField)
		feeField.click()
		feeField.sendKeys(Keys.chord(Keys.CONTROL, "a"))
		feeField.sendKeys(Keys.DELETE)
		feeField.sendKeys("231.00")
		feeField.sendKeys(Keys.TAB)
		
		wait.until(ExpectedConditions.attributeToBe(childCareProviderFee, "value", "231.00"))
		
		By childCareProviderSaveBtn = By.xpath(
		    "//div[contains(@class,'profile-modal')]//div[contains(@class,'nav-modal-btns')]//button[.//span[normalize-space()='Save']]"
		)
		ClickUtils.waitAndClick(childCareProviderSaveBtn, TIMEOUT)

		WebUI.delay(5)

		clickEnabledSaveButton(driver, wait)

		WebUI.delay(5)

		clickChildProviderMainYesBtn(driver, wait)

		WebUI.delay(5)
	}


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

	private static void clickChildProviderMainYesBtn(WebDriver driver, WebDriverWait wait) {
		By childProviderMainYesButton = By.xpath("//button[contains(@class,'ajs-ok') and normalize-space()='Yes']")
		WebElement childProviderMainYesBtn = wait.until(ExpectedConditions.elementToBeClickable(childProviderMainYesButton))
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", childProviderMainYesBtn)
		try {
			childProviderMainYesBtn.click()
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", childProviderMainYesBtn)
		}
	}
}
