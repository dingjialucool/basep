package com.chinobot.plep.home.algorithm.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.chinobot.common.utils.SFTPHelper;
import com.chinobot.plep.home.algorithm.entity.Algorithm;

public class SftpThread extends Thread {

	private Algorithm algorithm;
	private byte[] fileByte;
	private String algorithmIP;
	private String algorithmUser;
	private String algorithmPwd;
	private String algorithmRoot;
	
	public SftpThread(Algorithm algorithm, byte[] fileByte, String algorithmIP, String algorithmUser, String algorithmPwd, String algorithmRoot) {
		// TODO Auto-generated constructor stub
		this.algorithm = algorithm;
		this.fileByte = fileByte;
		this.algorithmIP = algorithmIP;
		this.algorithmUser = algorithmUser;
		this.algorithmPwd = algorithmPwd;
		this.algorithmRoot = algorithmRoot;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		FileOutputStream fos = null;
		try {
			String fileName = algorithm.getAlgorithmVersion() + "_" + algorithm.getDomainType() + "_" + algorithm.getSceneType() + "_" + algorithm.getPlatformType();
			InputStream in = new ByteArrayInputStream(fileByte);
			File file = new File(fileName);
			file.createNewFile();
			fos = new FileOutputStream(file);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = in.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			fos.flush();

			SFTPHelper sftpHelper = new SFTPHelper(algorithmUser, algorithmPwd, algorithmIP);
			if (sftpHelper.connection()) {
				System.out.println("start upload..." + file.getAbsolutePath() + " to..." + algorithmRoot + fileName + File.separator);
				boolean boo = sftpHelper.put(file.getAbsolutePath(), algorithmRoot + fileName + File.separator);
				if (boo) {
					sftpHelper.execCommand(new String[] {"cd " + algorithmRoot + fileName + File.separator, "unzip -o " + file.getName() + ".zip", "chmod -R a+x *", "./auto_install.sh"});
				}
				file.delete();
				sftpHelper.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
