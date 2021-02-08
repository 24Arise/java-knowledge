package com.arise.zip.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.arise.zip.service.IDecompressService;
import com.arise.zip.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Java原生 zip 解压缩实现类
 * </p>>
 *
 * @author 24arise 2021/02/05 12:49
 */
@Service
public class DecompressServiceImpl implements IDecompressService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DecompressServiceImpl.class);

	@Override
	public void decompress(String srcPath) {
		decompress(new File(srcPath));
	}

	@Override
	public void decompress(File srcFile) {
		String basePath = srcFile.getParent();
		decompress(srcFile, basePath);
	}

	@Override
	public void decompress(String srcPath, String destPath) {
		decompress(new File(srcPath), destPath);
	}

	@Override
	public void decompress(File srcFile, String destPath) {
		decompress(srcFile, new File(destPath));
	}

	/**
	 * <p>
	 * 解压缩
	 * </p>
	 *
	 * @param srcFile  源文件
	 * @param destFile 目标文件
	 * @return void
	 * @author Tagin 2021-02-05 16:59
	 */
	private void decompress(File srcFile, File destFile) {
		try {
			// 输入文件 CRC32 校验
			CheckedInputStream cis = new CheckedInputStream(new FileInputStream(srcFile), new CRC32());

			ZipInputStream zis = new ZipInputStream(cis);

			decompress(destFile, zis);
			zis.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>
	 * 文件探针
	 * </p>
	 * <pre>
	 *  当父目录不存在时，创建目录
	 * </pre>
	 *
	 * @param dirFile 目标文件
	 * @return void
	 * @author Tagin 2021-02-05 16:25
	 */
	private void fileProber(File dirFile) {
		File parentFile = dirFile.getParentFile();
		if (!parentFile.exists()) {
			// 递归寻找上级目录
			fileProber(parentFile);
			parentFile.mkdir();
		}
	}

	/**
	 * <p>
	 * 文件 解压缩
	 * </p>
	 *
	 * @param destFile 目标文件
	 * @param zis      zip 输入流
	 * @return void
	 * @author Tagin 2021-02-05 16:49
	 */
	private void decompress(File destFile, ZipInputStream zis) {
		try {
			ZipEntry entry = null;
			while ((entry = zis.getNextEntry()) != null) {
				// 文件
				String dir = destFile.getPath() + Util.SEPARATOR + entry.getName();
				File dirFile = new File(dir);

				// 文件检查
				fileProber(dirFile);
				if (entry.isDirectory()) {
					dirFile.mkdir();
				} else {
					decompressFile(dirFile, zis);
				}
				zis.closeEntry();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * <p>
	 * 文件解压缩
	 * </p>
	 *
	 * @param destFile 目标文件
	 * @param zis      zip 输入流
	 * @return void
	 * @author Tagin 2021-02-05 15:16
	 */
	private void decompressFile(File destFile, ZipInputStream zis) {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));

			int count;
			byte[] data = new byte[Util.BUFFER];
			while ((count = zis.read(data, 0, Util.BUFFER)) != -1) {
				bos.write(data, 0, count);
			}
			bos.flush();
			bos.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

}
