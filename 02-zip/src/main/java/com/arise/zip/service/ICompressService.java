package com.arise.zip.service;

import java.io.File;

/**
 * <p>
 * Java原生 zip 压缩接口类
 * </p>
 *
 * @author 24arise 2021/02/04 18:08
 */
public interface ICompressService {

	/**
	 * <p>
	 * 压缩
	 * </p>
	 *
	 * @param srcPath 源文件路径
	 * @return void
	 * @author 24Arise
	 * @date 2021/2/5 00:22
	 * @version 1.0
	 **/
	void compress(String srcPath);

	/**
	 * <p>
	 * 压缩
	 * </p>
	 *
	 * @param srcFile 源文件
	 * @return void
	 * @author 24Arise
	 * @date 2021/2/5 00:23
	 * @version 1.0
	 **/
	void compress(File srcFile);

	/**
	 * <p>
	 * 文件压缩
	 * </p>
	 *
	 * @param srcPath  源文件路径
	 * @param destPath 目标文件路径
	 * @return void
	 * @author 24Arise
	 * @date 2021/2/5 00:24
	 * @version 1.0
	 **/
	void compress(String srcPath, String destPath);

	/**
	 * <p>
	 * 压缩
	 * </p>
	 *
	 * @param srcFile  源文件
	 * @param destPath 目标文件路径
	 * @return void
	 * @author 24Arise
	 * @date 2021/2/5 00:25
	 * @version 1.0
	 **/
	void compress(File srcFile, String destPath);

}
