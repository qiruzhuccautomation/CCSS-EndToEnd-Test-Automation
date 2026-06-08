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


public class AuthorizedRepresentatives {
	private static final int TIMEOUT = 15
	private static int timeoutSeconds = 10

	@Keyword
	static void addAuthorizedRepresentatives() {
		WebDriver driver = DriverFactory.getWebDriver()
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
		JavascriptExecutor js = (JavascriptExecutor) driver
		Actions actions = new Actions(driver)

		VerifyUtils.verifyTextByXPath("//h3[normalize-space()='Tell us about your authorized representative(s) (If Relevant)']",
				"Tell us about your authorized representative(s) (If Relevant))")


		VerifyUtils.verifyTextByXPath("//p[span[contains(@class,'labelStar')] and span[normalize-space()='Do you wish to add any authorized representatives to your application?']]",
				"* Do you wish to add any authorized representatives to your application?")

		selectOptionIfNotSelected(driver, wait,
				"//p[normalize-space()='* Do you wish to add any authorized representatives to your application?']/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']",
				"//p[normalize-space()='* Do you wish to add any authorized representatives to your application?']/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']")


		By addAuthorizedRepresentativeBtn=By.xpath("//button[.//span[normalize-space()='Add Authorized Representative']]")
		ClickUtils.waitAndClick(addAuthorizedRepresentativeBtn, TIMEOUT)

		VerifyUtils.verifyTextByXPath("//h4[normalize-space()='Authorized Representatives']", "Authorized Representatives")

		VerifyUtils.verifyTextByXPath("//h5[normalize-space()=\"Enter the authorized representative's information.\"]", "Enter the authorized representative's information.")


		VerifyUtils.verifyTextByXPath(
				"//div[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		VerifyUtils.verifyTextByXPath("//label[.//span[contains(@class,'labelStar')] and .//span[normalize-space()='I want my authorized representative to do the following:']]",
				"* I want my authorized representative to do the following:")

		VerifyUtils.verifyTextByXPath("//label[.//span[normalize-space()='Assist with the application process and complete forms.']]", "Assist with the application process and complete forms.")

		VerifyUtils.verifyTextByXPath("//label[.//span[normalize-space()='Speak to the county about your application.']]", "Speak to the county about your application.")

		VerifyUtils.verifyTextByXPath("//label[.//span[normalize-space()='Report changes on your behalf']]", "Report changes on your behalf")

		//CustomKeywords.'components.RaceUtils.handleChildRaceSelection'(10, 3)


		VerifyUtils.verifyTextByXPath("//label[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='What is your relationship with the authorized representative?']]",
				"What is your relationship with the authorized representative?")

		By authorizedRepresentativeRelationDropdown = By.xpath("//div[@id='mui-component-select-relationshipToHHH']")
		ClickUtils.waitAndClick(authorizedRepresentativeRelationDropdown, TIMEOUT)
		By authorizedRepresentativeRelationOption = By.xpath("//ul[@role='listbox']//li[@role='option'][1]")
		ClickUtils.waitAndClick(authorizedRepresentativeRelationOption, TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath("//label[@id='firstName-label' and contains(@class,'Mui-error') and .//span[contains(@class,'labelStar')] and contains(normalize-space(.), 'First Name') and contains(normalize-space(.), 'Legally as it appears on their ID')]",
				'* First Name Legally as it appears on their ID')

		By representativeFirstNameField = By.xpath("//input[@id='firstName']")
		String representativeFirstName = generateRandomFirstAuthRepName()
		FormFieldUtils.populateOrAssertTextField(driver, representativeFirstNameField, '.*', representativeFirstName)

		VerifyUtils.verifyTextByXPath("//label[@id='firstName-label' and contains(@class,'Mui-error') and .//span[contains(@class,'labelStar')] and contains(normalize-space(.), 'First Name') and contains(normalize-space(.), 'Legally as it appears on their ID')]",
				'* Last Name Legally as it appears on their ID')
		By representativeLastNameField = By.xpath("//input[@id='lastName']")
		String representativeLastName = generateRandomLastAuthRepName()
		FormFieldUtils.populateOrAssertTextField(driver, representativeLastNameField, '.*', representativeLastName)

		By authRepSuffixDropdown = By.xpath("//div[@id='mui-component-select-suffix' and @role='button']")
		ClickUtils.waitAndClick(authRepSuffixDropdown, TIMEOUT)
		By authRepSuffixOption = By.xpath("//ul[@role='listbox']//li[@role='option'][5]")
		ClickUtils.waitAndClick(authRepSuffixOption, TIMEOUT)
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath("//p[contains(@class,'input-label') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Is this person at least 18 years of age?']]",
				"* Is this person at least 18 years of age?")
		selectYesIfNotSelected(driver, wait,
				"//p[normalize-space()='* Is this person at least 18 years of age?']/following::div[contains(@class,'gender')][1]//li[normalize-space()='Yes']",
				"//p[normalize-space()='* Is this person at least 18 years of age?']/following::div[contains(@class,'gender')][1]//li[contains(@class,'selected') and normalize-space()='Yes']")

		VerifyUtils.verifyTextByXPath(
				"//label[.//span[normalize-space()='Gender'] and .//span[contains(@class,'labelStar')]]",
				"* Gender")

		By authRepGenderDropdown = By.xpath("//div[@id='mui-component-select-gender']")
		ClickUtils.waitAndClick(authRepGenderDropdown, 10)
		DropdownUtils.selectDropDownOption(authRepGenderDropdown, "Male")
		CloseDropdownUtils.closeMuiDropdown(driver, wait)

		VerifyUtils.verifyTextByXPath("//p[contains(@class,'doc-head') and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Upload Proof of Identity']]",
				"* Upload Proof of Identity")

		uploadProofOfAuthRepIdentity(driver, wait, TIMEOUT)

		By authRepSaveNextBtn = By.xpath("//button[.//span[normalize-space()='Save & Next']]")
		ClickUtils.waitAndClick(authRepSaveNextBtn, 10)
		WebUI.delay(5)

		CustomKeywords.'core.VerifyUtils.verifyTextByXPath'(
				"//label[normalize-space()='* Mobile Number']",
				"* Mobile Number")
		String authRepPhoneNumber = generateRandomMobileNumber()
		By authRepPhoneNumberField = By.xpath("//input[@id='workPhone']")
		FormFieldUtils.populateOrAssertTextField(driver, authRepPhoneNumberField, ".*", authRepPhoneNumber)

		CustomKeywords.'core.VerifyUtils.verifyTextByXPath'(
				"//label[@id='emailAddress1-label' and .//span[contains(@class,'labelStar')] and .//span[normalize-space()='Email Address']]",
				"* Email Address")

		String authRepEmailAddress = generateRandomEmailAddress()
		By authRepEmailAddressField = By.xpath("//input[@id='emailAddress1']")
		FormFieldUtils.populateOrAssertTextField(driver, authRepEmailAddressField, ".*", authRepEmailAddress)


		By authorizedRepNextBtn = By.xpath("//button[.//span[normalize-space()='Next']]")
		ClickUtils.waitAndClick(authorizedRepNextBtn, TIMEOUT)


		CustomKeywords.'core.VerifyUtils.verifyTextByXPath'(
				"//div[normalize-space()='Fields marked with * are mandatory']",
				"Fields marked with * are mandatory")

		//Employer Address
		CustomKeywords.'core.VerifyUtils.verifyTextByXPath'(
				"//label[normalize-space()='* Address Line 1']",
				"* Address Line 1")

		By addressLine1Field = By.xpath("//input[@name='addressLine1' or @id='addressLine1']")
		WebElement address1el = wait.until(ExpectedConditions.elementToBeClickable(addressLine1Field))
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", address1el)

		address1el.click()
		address1el.sendKeys(Keys.chord(Keys.CONTROL, "a"))
		address1el.sendKeys(Keys.DELETE)
		address1el.sendKeys("606")

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

		By authRepAddressPageSaveButton = By.xpath("//button[.//span[normalize-space()='Save']]")
		ClickUtils.waitAndClick(authRepAddressPageSaveButton, TIMEOUT)

		By authRepMainSaveButton = By.xpath("//button[.//span[normalize-space()='Save']]")
		ClickUtils.waitAndClick(authRepMainSaveButton, TIMEOUT)

		WebUI.delay(5)

		By authRepToNextStepYesButton = By.xpath("//button[normalize-space()='Yes']")
		ClickUtils.waitAndClick(authRepToNextStepYesButton, TIMEOUT)
	}

	static void uploadProofOfAuthRepIdentity(WebDriver driver, WebDriverWait wait, int timeoutSeconds = 15) {
		By uploadAuthorizedRepresentativesBtn = By.xpath("//button[@type='button' and .//img[@alt='add']]")
		ClickUtils.waitAndClick(uploadAuthorizedRepresentativesBtn, timeoutSeconds)
		String authorizedRepresentativesIdentityFile = RunConfiguration.getProjectDir() + "/Data Files/ProofofRepresentativesIdentity.pdf"
		By authorizedRepresentativesIdentityInputBy = By.id("contained-button-file")
		wait.until(ExpectedConditions.presenceOfElementLocated(authorizedRepresentativesIdentityInputBy))
		driver.findElement(authorizedRepresentativesIdentityInputBy).sendKeys(authorizedRepresentativesIdentityFile)
		By authorizedRepresentativesIdentityConfirm = By.xpath("//button[contains(@class,'btn-primary') and @title='Upload']")
		ClickUtils.waitAndClick(authorizedRepresentativesIdentityConfirm, timeoutSeconds)
		WebUI.comment("File uploaded successfully")
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

	private static String generateRandomFirstAuthRepName() {
		String[] firstRepNames = [
			"RepFirst",
			"RepresentativeFirst",
			"CaseWorkerFirst",
			"LawyerFirst",
			"RulerFirst",
			"LawFirst"
		]
		return firstRepNames[new Random().nextInt(firstRepNames.length)]
	}

	private static String generateRandomLastAuthRepName() {
		String[] lastRepNames = [
			"RepLast",
			"RepresentativeLast",
			"CaseWorkerLast",
			"LawyerLast",
			"RulerLast",
			"LawLast"
		]
		return lastRepNames[new Random().nextInt(lastRepNames.length)]
	}

	private static String generateRandomSocialSecurityNumber() {
		List<String> childSsns = ["615-67-8765", "987-76-3456"]
		return childSsns[new Random().nextInt(childSsns.size())]
	}

	private static String generateRandomMobileNumber() {
		List<String> authRepMobileNumber = [
			"301-906-8765",
			"202-769-3456"
		]
		return authRepMobileNumber[new Random().nextInt(authRepMobileNumber.size())]
	}

	private static String generateRandomEmailAddress() {
		List<String> authRepEmailAddress = [
			"legalaid123@gmail.com",
			"casehelper123@gmail.com"
		]
		return authRepEmailAddress[new Random().nextInt(authRepEmailAddress.size())]
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
