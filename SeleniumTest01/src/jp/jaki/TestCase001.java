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
	
		// ########## �P��ʖ� ##########
		// ������ʃ��[�h
		wd.get("http://example.selenium.jp/reserveApp/");

		// ***** �X�N���[���V���b�g���B���Ă��� *****
		TakesScreenshot tss = (TakesScreenshot)wd;
		Path filePath = FileSystems.getDefault().getPath("evidence", "ss", "001", "index01.png"); 
		try {
			Files.write(filePath, tss.getScreenshotAs(OutputType.BYTES));
		} catch (WebDriverException | IOException e) {
			e.printStackTrace();
		}
		
		// ***** HTML�t�@�C�����o�͂��Ă��� *****
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

		// **** �����O *****
		// �����O�̃G�������g���擾�iinput.text)
        WebElement element = wd.findElement(By.name("gname"));
		// �����O�̃G�������g�ɐݒ�
        element.clear();
        element.sendKeys("edy jaki");

        // ***** �h�����iinput.text x 4) *****
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

        // ***** �l���iinput.text) *****
        element = wd.findElement(By.name("hc"));
        element.clear();
        element.sendKeys("2");

        // ***** ���H�o�C�L���O�iinput.radio) *****
        element = wd.findElement(By.xpath("//input[@type='radio'][@name='bf'][@value='off']"));
        element.click();

        // ***** �v�����iinput.radio) *****
        element = wd.findElement(By.name("plan_a"));
        element.click();
        element = wd.findElement(By.name("plan_b"));
        element.click();

		// ***** ��ʑJ�ڑO�ɃX�N���[���V���b�g���B���Ă��� *****
		tss = (TakesScreenshot)wd;
		filePath = FileSystems.getDefault().getPath("evidence", "ss", "001", "index02.png"); 
		try {
			Files.write(filePath, tss.getScreenshotAs(OutputType.BYTES));
		} catch (WebDriverException | IOException e) {
			e.printStackTrace();
		}

        // ***** �v�����ibutton) *****
        element = wd.findElement(By.xpath("//button[@id='goto_next']"));
        element.click();

		// ########## �Q��ʖ� ##########
        // ***** ��ʑJ�ڏI����҂� *****
        Wait<WebDriver> wait = new WebDriverWait(wd, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='commit']")));

		// ***** ��ʑJ�ڌ�ɃX�N���[���V���b�g���B���Ă��� *****
		tss = (TakesScreenshot)wd;
		filePath = FileSystems.getDefault().getPath("evidence", "ss", "001", "confirm01.png"); 
		try {
			Files.write(filePath, tss.getScreenshotAs(OutputType.BYTES));
		} catch (WebDriverException | IOException e) {
			e.printStackTrace();
		}
		
		// ***** HTML�t�@�C�����o�͂��Ă��� *****
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

		// !!!!!!!!!! �A�T�[�V���� !!!!!!!!!!
		String result = "";
		// ***** ��ʃ^�C�g�� *****
		result = wd.getTitle();
		assertThat(result, is("�\����e�m�F"));
		
		// ***** ���v���z *****
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
