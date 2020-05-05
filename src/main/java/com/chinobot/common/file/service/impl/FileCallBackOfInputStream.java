package com.chinobot.common.file.service.impl;

import java.io.IOException;
import java.io.InputStream;

import com.github.tobato.fastdfs.proto.storage.DownloadCallback;

public class FileCallBackOfInputStream implements DownloadCallback<InputStream>{

	@Override
	public InputStream recv(InputStream ins) throws IOException {
		return ins;
	}

}
