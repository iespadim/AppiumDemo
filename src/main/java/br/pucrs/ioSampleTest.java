package br.pucrs;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class ioSampleTest {
        public AndroidDriver driver;
        public WebDriverWait wait;

        private AppiumBy.ByAndroidUIAutomator byLogin       = new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"Usuário de Rede\")");
        private AppiumBy.ByAndroidUIAutomator byPassword    = new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"Senha\")");
        private AppiumBy.ByAndroidUIAutomator byButton      =  new AppiumBy.ByAndroidUIAutomator("new UiSelector().resourceId(\"loginButton\")");
        private AppiumBy.ByAndroidUIAutomator byInvalidLoginMessage     = new AppiumBy.ByAndroidUIAutomator("new UiSelector().text(\"Usuário de Rede ou Senha Inválidos\")");

        @BeforeMethod
        public void setup() throws MalformedURLException {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("deviceName", "Pixel_3a_API_33_x86_64");
            caps.setCapability("platformName", "Android");
            caps.setCapability("platformVersion", "13.0");
            caps.setCapability("skipUnlock", "true");
            caps.setCapability("appPackage", "br.pucrs.staging");
            caps.setCapability("appActivity", "br.pucrs.MainActivity");
            caps.setCapability("noReset", "true");
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }


        @Test
        public void invalidLoginTest() {
            driver.context(driver.getContextHandles().toArray()[0].toString());
            WebElement loginUser = wait.until(ExpectedConditions.presenceOfElementLocated(byLogin));
            driver.navigate().back();

            WebElement loginSenha = wait.until(ExpectedConditions.presenceOfElementLocated(byPassword));
            WebElement loginButton = wait.until(ExpectedConditions.presenceOfElementLocated(byButton));

            loginUser.sendKeys("icaro.espadim@edu.pucrs.br");
            loginSenha.sendKeys("senhaerrada");

            loginButton.click();
            loginButton.click();

            WebElement invalidLoginMessage = wait.until(ExpectedConditions.presenceOfElementLocated(byInvalidLoginMessage));
            Assert.assertEquals(invalidLoginMessage.getText(), "Usuário de Rede ou Senha Inválidos");
        }
}

