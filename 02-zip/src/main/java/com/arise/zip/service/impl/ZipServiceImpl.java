package com.arise.zip.service.impl;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.arise.zip.service.IZipService;
import com.arise.zip.util.Util;

/**
 * <p>
 * Zip 压缩、解压实现类
 * </p>>
 *
 * @author 24arise 2021/02/03 23:01
 */
public class ZipServiceImpl implements IZipService {

	/**
	 * <p>
	 * 压缩目录
	 * </p>
	 *
	 * @param dir      文件夹目录
	 * @param zos      zip输出流
	 * @param basePath 基础目录
	 * @return void
	 * @author Tagin 2021-02-04 18:05
	 */
	private void compressDir(File dir, ZipOutputStream zos, String basePath) throws IOException {

		File[] files = dir.listFiles();

		// 构建空目录
		assert files != null;
		if (files.length < 1) {
			ZipEntry entry = new ZipEntry(basePath + dir.getName() + Util.PATH);

			zos.putNextEntry(entry);
			zos.closeEntry();
		}

		for (File file : files) {
			// 递归压缩
			compressDir(file, zos, dir.getName() + Util.PATH);
		}
	}


	/**
	 * <p>
	 * 压缩文件
	 * </p>
	 *
	 * @param file 待压缩文件
	 * @param zos  zip输出流
	 * @param dir  压缩文件中的当前目录
	 * @return void
	 * @author Tagin 2021-02-04 18:14
	 */
	private void compressFile(File file, ZipOutputStream zos, String dir) throws IOException {

		/**
		 * 压缩包内文件名定义
		 */
		ZipEntry entry = new ZipEntry(dir + file.getName());
		zos.putNextEntry(entry);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

		int count;
		byte data[] = new byte[Util.BUFFER];
		while ((count = bis.read(data, 0, Util.BUFFER)) != -1) {
			zos.write(data, 0, count);
		}
		bis.close();

		zos.closeEntry();
	}
	

}
