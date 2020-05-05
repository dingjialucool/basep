//package com.chinobot;
//
//import com.chinobot.common.file.util.FastDFSClient;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ApplicationTests {
//
//	@Test
//	public void Upload() throws FileNotFoundException {
//		//String fileUrl = this.getClass().getResource("/test.jpg").getPath();
//		String fileUrl = "D:\\test\\Chrysanthemum.jpg";
//		//String fileUrl = "D:\\test\\1.doc";
//		File file = new File (fileUrl);
//		String str = FastDFSClient.uploadFile(file);
//		String fullStr = FastDFSClient.getResAccessUrl(str);
//		System.out.println ("str: " + str);
//		System.out.println ("fullStr: " + fullStr);
//	}
//
//	@Test
//	public void Delete() {
//		FastDFSClient.deleteFile("group1/M00/00/00/rBEAClu8OiSAFbN_AAbhXQnXzvw031.jpg");
//	}
//
//	@Test
//	public void Path(){
//		String path = "http://192.168.0.11:9001/group1/M00/00/00/wKgAC1ytxLWAJPJhAAC4AF-cFV8309.doc";
//		String ss = FastDFSClient.getResAccessUrl (path);
//		System.out.println (ss);
//	}
//
//	@Test
//	public void DownLoad(){
//		String path = "group1/M00/00/00/wKgAC1yt06uActdaAA1rIoHwd3k212.jpg";
//		Boolean ss = FastDFSClient.downloadFile (path, new File ("D:\\test\\1111.jpg"));
//		System.out.println (ss);
//	}
//
//}
