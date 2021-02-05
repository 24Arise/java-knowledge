package com.arise.zip.service;

import java.io.File;

/**
 * <p>
 * Java原生 zip 解压缩接口类
 * </p>
 *
 * @author 24arise 2021/02/05 12:50
 */
public interface IDecompressService {


	/**
	 * <p>
	 * 文件压缩
	 * </p>
	 *
	 * @param srcPath 源文件路径
	 * @return void
	 * @author Tagin 2021-02-05 17:08
	 */
	void decompress(String srcPath);

	/**
	 * <p>
	 * 文件压缩
	 * </p>
	 *
	 * @param srcFile 源文件
	 * @return void
	 * @author Tagin 2021-02-05 17:08
	 */
	void decompress(File srcFile);

	/**
	 * <p>
	 * 文件压缩
	 * </p>
	 *
	 * @param srcPath  源文件路径
	 * @param destPath 目标文件路径
	 * @return void
	 * @author Tagin 2021-02-05 17:08
	 */
	void decompress(String srcPath, String destPath);

	/**
	 * <p>
	 * 文件压缩
	 * </p>
	 *
	 * @param srcFile  源文件
	 * @param destPath 目标文件路径
	 * @return void
	 * @author Tagin 2021-02-05 17:08
	 */
	void decompress(File srcFile, String destPath);


}
