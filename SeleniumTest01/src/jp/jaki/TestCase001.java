package jp.jaki;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sahagin.runlib.external.adapter.webdriver.WebDriverAdapter;

public class TestCase001 {

	WebDriver wd = null;
	
	@Before
	public void setUp() throws Exception {
		File file = new File("D:/workspace/SeleniumTest/SeleniumTest01/driver/IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		wd = new InternetExplorerDriver();
		WebDriverAdapter.setAdapter(wd);
	}

	@Test
	public void test() {
	
		// ########## １画面目 ##########
		// 初期画面ロード
		wd.get("http://example.selenium.jp/reserveApp/");

		// ***** スクリーンショットを撮っておく *****
		TakesScreenshot tss = (TakesScreenshot)wd;
		Path filePath = FileSystems.getDefault().getPath("evidence", "ss", "001", "index01.png"); 
		try {
			Files.write(filePath, tss.getScreenshotAs(OutputType.BYTES));
		} catch (WebDriverException | IOException e) {
			e.printStackTrace();
		}
		
		// ***** HTMLファイルを出力しておく *****
		String htmlSrc = wd.getPageSource();
		Path htmlPath = FileSystems.getDefault().getPath("evidence", "html", "001", "index.html"); 
		File htmlFile = new File(htmlPath.toString());
		PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8")));
			pw.print(htmlSrc);
			pw.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}

		// **** お名前 *****
		// お名前のエレメントを取得（input.text)
        WebElement element = wd.findElement(By.name("gname"));
		// お名前のエレメントに設定
        element.clear();
        element.sendKeys("edy jaki");

        // ***** 宿泊日（input.text x 4) *****
        element = wd.findElement(By.name("reserve_y"));
        element.clear();
        element.sendKeys("2016");
        element = wd.findElement(By.name("reserve_m"));
        element.clear();
        element.sendKeys("03");
        element = wd.findElement(By.name("reserve_d"));
        element.clear();
        element.sendKeys("22");
        element = wd.findElement(By.name("reserve_t"));
        element.clear();
        element.sendKeys("2");

        // ***** 人数（input.text) *****
        element = wd.findElement(By.name("hc"));
        element.clear();
        element.sendKeys("2");

        // ***** 朝食バイキング（input.radio) *****
        element = wd.findElement(By.xpath("//input[@type='radio'][@name='bf'][@value='off']"));
        element.click();

        // ***** プラン（input.radio) *****
        element = wd.findElement(By.name("plan_a"));
        element.click();
        element = wd.findElement(By.name("plan_b"));
        element.click();

		// ***** 画面遷移前にスクリーンショットを撮っておく *****
		tss = (TakesScreenshot)wd;
		filePath = FileSystems.getDefault().getPath("evidence", "ss", "001", "index02.png"); 
		try {
			Files.write(filePath, tss.getScreenshotAs(OutputType.BYTES));
		} catch (WebDriverException | IOException e) {
			e.printStackTrace();
		}

        // ***** プラン（button) *****
        element = wd.findElement(By.xpath("//button[@id='goto_next']"));
        element.click();

		// ########## ２画面目 ##########
        // ***** 画面遷移終了を待つ *****
        Wait<WebDriver> wait = new WebDriverWait(wd, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='commit']")));

		// ***** 画面遷移後にスクリーンショットを撮っておく *****
		tss = (TakesScreenshot)wd;
		filePath = FileSystems.getDefault().getPath("evidence", "ss", "001", "confirm01.png"); 
		try {
			Files.write(filePath, tss.getScreenshotAs(OutputType.BYTES));
		} catch (WebDriverException | IOException e) {
			e.printStackTrace();
		}
		
		// ***** HTMLファイルを出力しておく *****
		htmlSrc = wd.getPageSource();
		htmlPath = FileSystems.getDefault().getPath("evidence", "html", "001", "confirm.html"); 
		htmlFile = new File(htmlPath.toString());
		try {
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8")));
			pw.print(htmlSrc);
			pw.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}

		// !!!!!!!!!! アサーション !!!!!!!!!!
		String result = "";
		// ***** 画面タイトル *****
		result = wd.getTitle();
		assertThat(result, is("予約内容確認"));
		
		// ***** 合計金額 *****
		element = wd.findElement(By.xpath("//span[@id='price']"));
		assertThat(element.getText(), is("32000"));
        
//	        (new WebDriverWait(wd, 30)).until(new ExpectedCondition<Boolean>() {
//	            public Boolean apply(WebDriver d) {
//	                return d.getTitle().startsWith("Cheese!");
//	            }
//	        });

        System.out.println("Page title is: " + wd.getTitle());
	}

	@After
	public void tearDown() throws Exception {
		wd.quit();
	}

}
