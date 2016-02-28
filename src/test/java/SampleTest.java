import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Kanitkorn S. <k.sujautra@gmail.com> on 2/28/2016 AD.
 */
@FixMethodOrder(MethodSorters.JVM)
public class SampleTest {

    static AndroidDriver driver;

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
                "AndroidDeviceTest");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),
                capabilities);

        driver.startActivity("com.geekybase.appfortraining", "com.geekybase.appfortraining.activity.MainActivity");
    }

    @Test
    public void when_click_button_should_see_dialog() throws Exception {
        WebElement btn = driver.findElement(By.id("com.geekybase.appfortraining:id/button"));
        btn.click();
        WebElement dialogMessage = driver.findElement(By.id("android:id/message"));
        assertEquals("Hello click", dialogMessage.getText());
        (driver.findElement(By.id("android:id/button1"))).click();
    }

    @Test
    public void sample_text_should_has_text_as_sample_text() throws Exception {
        WebElement textView = driver.findElement(By.id("com.geekybase.appfortraining:id/textView"));
        assertEquals("Sample Text", textView.getText());
    }

    @Test
    public void when_click_toggle_button_text_should_change() throws Exception {
        WebElement btn = driver.findElement(By.id("com.geekybase.appfortraining:id/toggleButton"));
        String text = btn.getText();
        btn.click();
        String newText = btn.getText();
        if (text.equals("ปิด")) {
            assertEquals("เปิด", newText);
        }
        else {
            assertEquals("ปิด", newText);
        }
    }

    @Test
    public void when_click_switch_status_should_change() throws Exception {
        WebElement btn = driver.findElement(By.id("com.geekybase.appfortraining:id/switchButton"));
        String status = btn.getAttribute("checked");
        btn.click();
        String newStatus = btn.getAttribute("checked");
        if (status.equals("true")) {
            assertEquals("false", newStatus);
        }
        else {
            assertEquals("true", newStatus);
        }
    }

    @Test
    public void when_move_from_0_to_something() throws Exception {
        WebElement seekBar = driver.findElement(By.id("com.geekybase.appfortraining:id/seekBar"));
        int originalX = seekBar.getLocation().getX();
        int originalY = seekBar.getLocation().getY();
        TouchAction tAction = new TouchAction(driver);
        tAction.press(originalX, originalY).moveTo(originalX + 500, originalY).release().perform();
        assertEquals(100, originalX + 100);
    }

    @Test
    public void result_number_should_be_correct() throws Exception {
        driver.startActivity("com.geekybase.appfortraining", "com.geekybase.appfortraining.activity.SecondActivity");

        WebElement firstNumber = driver.findElement(By.id("com.geekybase.appfortraining:id/tvNumber1"));
        WebElement secondNumber = driver.findElement(By.id("com.geekybase.appfortraining:id/edtNumber2"));

        firstNumber.sendKeys("10");
        secondNumber.sendKeys("10");

        (driver.findElement(By.id("com.geekybase.appfortraining:id/btnCal"))).click();

        WebElement result = driver.findElement(By.id("com.geekybase.appfortraining:id/tvResult"));
        assertEquals("20", result.getText());
    }

    @Test
    public void github_result_should_be_correct() throws Exception {
        driver.startActivity("com.geekybase.appfortraining", "com.geekybase.appfortraining.activity.ThreeActivity");

        (driver.findElement(By.id("com.geekybase.appfortraining:id/edtUser"))).sendKeys("lukyth");
        (driver.findElement(By.id("com.geekybase.appfortraining:id/btnSearch"))).click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.geekybase.appfortraining:id/tvFullName")));
        String name = (driver.findElement(By.id("com.geekybase.appfortraining:id/tvFullName"))).getText();
        assertEquals("Kanitkorn Sujautra", name);
    }

    @Test
    public void swipe_right_should_change_tab() throws Exception {
        driver.startActivity("com.geekybase.appfortraining", "com.geekybase.appfortraining.activity.FourActivity");
        WebElement pager = driver.findElement(By.id("com.geekybase.appfortraining:id/viewPager"));
        driver.swipe(400, 400, 50, 400, 300);
        String page = pager.getAttribute("name");
        assertEquals("Two", page);
    }

    @Test
    public void added_music_name_should_be_the_same() throws Exception {
        driver.startActivity("com.geekybase.appfortraining", "com.geekybase.appfortraining.activity.FiveActivity");
        (driver.findElement(By.id("com.geekybase.appfortraining:id/edtMusicName"))).sendKeys("test1");
        (driver.findElement(By.id("com.geekybase.appfortraining:id/edtArtist"))).sendKeys("test2");
        (driver.findElement(By.id("com.geekybase.appfortraining:id/btnAdd"))).click();
        List<WebElement> musics = driver.findElements(By.id("com.geekybase.appfortraining:id/tvMusicName"));
        assertEquals("test1", musics.get(musics.size() - 1).getText());
    }

    @Test
    public void should_find_overflow_list_item() throws Exception {
        driver.startActivity("com.geekybase.appfortraining", "com.geekybase.appfortraining.activity.FiveActivity");
        WebElement musicName = driver.findElement(By.id("com.geekybase.appfortraining:id/edtMusicName"));
        WebElement artist = driver.findElement(By.id("com.geekybase.appfortraining:id/edtArtist"));
        WebElement btn = driver.findElement(By.id("com.geekybase.appfortraining:id/btnAdd"));
        for (Integer i = 0; i < 4; i++) {
            musicName.sendKeys(i.toString());
            artist.sendKeys(i.toString());
            btn.click();
            musicName.clear();
            artist.clear();
        }
        driver.scrollTo("3");
        List<WebElement> musics = driver.findElements(By.id("com.geekybase.appfortraining:id/tvMusicName"));
        assertEquals("3", musics.get(musics.size() - 1).getText());
    }

    @AfterClass
    public static void tearDown() {
        driver.startActivity("com.geekybase.appfortraining", "com.geekybase.appfortraining.activity.MainActivity");
        driver.quit();
    }
}
