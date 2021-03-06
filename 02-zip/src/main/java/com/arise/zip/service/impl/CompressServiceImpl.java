package com.arise.zip.service.impl;

import java.io.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.arise.zip.service.ICompressService;
import com.arise.zip.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Java原生 zip 压缩实现类
 * </p>>
 *
 * @author 24arise 2021/02/03 23:01
 */
@Service
public class CompressServiceImpl implements ICompressService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompressServiceImpl.class);

	@Override
	public void compress(String srcPath) {
		compress(new File(srcPath));
	}

	@Override
	public void compress(File srcFile) {
		// 获取文件信
		String name = srcFile.getName();
		String basePath = srcFile.getParent();
		String destPath = basePath + Util.PATH + name + Util.SUFFIX;
		compress(srcFile, destPath);
	}

	@Override
	public void compress(String srcPath, String destPath) {
		compress(new File(srcPath), destPath);
	}

	@Override
	public void compress(File srcFile, String destPath) {
		compress(srcFile, new File(destPath));
	}

	/**
	 * <p>
	 * 压缩
	 * </p>
	 *
	 * @param srcFile  源路径
	 * @param destFile 目标路径
	 * @return void
	 * @author 24Arise
	 * @date 2021/2/5 00:06
	 * @version 1.0
	 **/
	private void compress(File srcFile, File destFile) {
		try {
			// 输出文件 CRC32 校验
			CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(destFile), new CRC32());

			ZipOutputStream zos = new ZipOutputStream(cos);
			compress(srcFile, zos, Util.BASE_DIR);
			zos.flush();
			zos.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * <p>
	 * 压缩
	 * </p>
	 *
	 * @param srcFile  原路径
	 * @param zos      zip输出流
	 * @param basePath 压缩包内相对路径
	 * @return void
	 * @author 24Arise
	 * @date 2021/2/4 23:51
	 * @version 1.0
	 **/
	private void compress(File srcFile, ZipOutputStream zos, String basePath) {
		if (srcFile.isDirectory()) {
			compressDir(srcFile, zos, basePath);
		} else {
			compressFile(srcFile, zos, basePath);
		}
	}

	/**
	 * <p>
	 * 压缩目录
	 * </p>
	 *
	 * @param dir      文件夹目录
	 * @param zos      zip输出流
	 * @param basePath 基础目录
	 * @return void
	 * @author 24arise 2021-02-04 18:05
	 */
	private void compressDir(File dir, ZipOutputStream zos, String basePath) {

		File[] files = dir.listFiles();
		try {
			// 构建空目录
			assert files != null;
			if (files.length < 1) {
				ZipEntry entry = new ZipEntry(basePath + dir.getName() + Util.PATH);
				zos.putNextEntry(entry);
				zos.closeEntry();
			}

			for (File file : files) {
				// 递归压缩
				compress(file, zos, basePath + dir.getName() + Util.PATH);
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
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
	 * @author 24Arise 2021-02-04 18:14
	 */
	private void compressFile(File file, ZipOutputStream zos, String dir) {
		try {
			/*
			 * 压缩包内文件名定义
			 *
			 * <pre>
			 *     可能会存在中文压缩文件乱码
			 * </pre>
			 */
			ZipEntry entry = new ZipEntry(dir + file.getName());
			zos.putNextEntry(entry);
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

			int count;
			byte[] data = new byte[Util.BUFFER];
			while ((count = bis.read(data, 0, Util.BUFFER)) != -1) {
				zos.write(data, 0, count);
			}
			bis.close();
			zos.closeEntry();
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}


}
