package com.arise.zip;

import java.io.File;

import com.arise.zip.service.ICompressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZipApplicationTests {

	@Autowired
	ICompressService compressService;

	@Test
	void contextLoads() {
		System.out.println("Java 中 zip 压缩学习");
	}


	@Test
	void compressTest() {
		String srcPath = "/Users/24arise/Pictures/Anbiao/20200123";
		String destPath = "/Users/24arise/Pictures/20200123.zip";
		// 1.0 源文件目录地址
		compressService.compress(srcPath);
		// 2.0 原文件
		// compressService.compress(new File(srcPath));
		// 3.0 源文件目录地址、目标文件地址
		// compressService.compress(srcPath, destPath);
		// 4.0 源文件、目标文件地址
		// compressService.compress(new File(srcPath), destPath);

	}


	@Test
	void decompressTest() {

	}

}
