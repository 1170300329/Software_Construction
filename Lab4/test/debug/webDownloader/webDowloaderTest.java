package debug.webDownloader;

import static org.junit.Assert.*;

import org.junit.Test;

public class webDowloaderTest {

	@Test
	public void testPDF() {
		WebDownloader downloader = null;
		String fileURLSource = "https://people.ok.ubc.ca/rlawrenc/teaching/304/Notes/index.html";
		String fileURLRoot = "https://people.ok.ubc.ca/rlawrenc/teaching/304/Notes/";
		
		String destination=null;
		downloader = new PdfDownloader(fileURLSource, destination, fileURLRoot);
		downloader.init();
		assertTrue(downloader.files.size()==47);
	}
	
	@Test
	public void testMP3() {
		WebDownloader downloader = null;
		String fileURLSource = "https://people.ok.ubc.ca/rlawrenc/teaching/304/Notes/index.html";
		String fileURLRoot = "https://people.ok.ubc.ca/rlawrenc/teaching/304/Notes/";
		String destination=null;
		downloader = new Mp3Downloader(fileURLSource, destination, fileURLRoot);
		downloader.init();
		assertTrue(downloader.files.size()==0);
	}
	@Test
	public void testTXT() {
		WebDownloader downloader = null;
		String fileURLSource = "https://people.ok.ubc.ca/rlawrenc/teaching/304/Notes/index.html";
		String fileURLRoot = "https://people.ok.ubc.ca/rlawrenc/teaching/304/Notes/";
		String destination=null;
		downloader = new TextDownloader(fileURLSource, destination, fileURLRoot);
		downloader.init();
		assertTrue(downloader.files.size()==21);
	}

}
