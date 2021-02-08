### Java 原生压缩、解压缩

- 背景

> 本文将介绍 使用 `java.util.zip` 类，完成 java 原生压缩、解压缩
>
> `java.util.zip` 提供用于读取和写入标准ZIP和GZIP文件格式的类。 还包括使用DEFLATE压缩算法压缩和解压缩数据的类，该算法由ZIP和GZIP文件格式使用。 此外，还有用于计算任意输入流的CRC-32，CRC-32C和Adler-32校验和的实用程序类

---

> 接口摘要

接口 | 描述
----|----|
`Checksum` | 表示数据校验和的接口。

> 类摘要

类|描述|
----|----|
`Adler32` |一个类，可用于计算数据流的Adler-32校验和。
`CheckedInputStream` |一种输入流，它还维护正在读取的数据的校验和。
`CheckedOutputStream` | 输出流，它还维护正在写入的数据的校验和。
`CRC32` | 可用于计算数据流的CRC-32的类。
`CRC32C` | 可用于计算数据流的CRC-32C的类。
`Deflater` | 此类使用流行的ZLIB压缩库为通用压缩提供支持。
`DeflaterInputStream` | 实现输入流过滤器，以“压缩”压缩格式压缩数据。
`DeflaterOutputStream` | 此类实现了一个输出流过滤器，用于以“deflate”压缩格式压缩数据。
`GZIPInputStream` | 此类实现了一个流过滤器，用于读取GZIP文件格式的压缩数据。
`GZIPOutputStream` | 此类实现了一个流过滤器，用于以GZIP文件格式写入压缩数据。
`Inflater` | 此类使用流行的ZLIB压缩库为通用解压缩提供支持。
`InflaterInputStream` | 此类实现了一个流过滤器，用于以“deflate”压缩格式解压缩数据。
`InflaterOutputStream` | 实现输出流过滤器，用于解压缩以“deflate”压缩格式存储的数据。
`ZipEntry` | 此类用于表示ZIP文件条目。
`ZipFile` | 该类用于从zip文件中读取条目。
`ZipInputStream` | 此类实现了一个输入流过滤器，用于读取ZIP文件格式的文件。
`ZipOutputStream` | 此类实现了一个输出流过滤器，用于以ZIP文件格式写入文件。

> 异常摘要

异常|描述|
----|----|
`DataFormatException` | 表示发生了数据格式错误。
`ZipException` | 表示发生某种Zip异常的信号。

> 错误汇总

Error|描述|
----|----|
`ZipError` | 表示发生了不可恢复的错误。

- 压缩

> 使用 `ZipOutputStream` 的 `write` 方法将压缩数据写入到 `Zip` 输入流中，然后将待压缩的文件以 `ZipEntry` 的方式追加到压缩文件中
>
> *Tips：压缩前先使用 `CheckedOutputStream` 指定文件校验算法 （通常使用 `CRC32` 算法），另，可见参考文献 `[6]` 中对于 `Checksum` 的详细解释；即：在将网路中传输压缩对象时候可将 `Checksum` 作为压缩数据是否异常的校验参数*
>

> `CRC32` 校验

```java
// 输出文件 CRC32 校验
CheckedOutputStream cos=new CheckedOutputStream(new FileOutputStream(destFile),new CRC32());
ZipOutputStream zos=new ZipOutputStream(cos);
```

> `ZipEntry` 追加文件条目

```java
ZipEntry entry=new ZipEntry(dir+file.getName());
zos.putNextEntry(entry);
```

> 压缩文件

```java
private void compressFile(File file,ZipOutputStream zos,String dir){
	try{
		ZipEntry entry=new ZipEntry(dir+file.getName());
		zos.putNextEntry(entry);
		BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file));
	
		int count;
		byte[]data=new byte[Util.BUFFER];
		while((count=bis.read(data,0,Util.BUFFER))!=-1){
			zos.write(data,0,count);
		}
		bis.close();
		zos.closeEntry();
	}catch(IOException e){
		LOGGER.error(e.getMessage(),e);
	}
}
```

- 解压缩

> 使用 `ZipInputStream` 的 `read` 方法将压缩包解压，然后使用 `ZipEntry` 将压缩项从压缩包中提取出来，并使用 `BufferedOutputStream` 的 `write` 方法将对应的数据写入到 `Buffer` 输出流中
>
> *Tips：解压缩前先使用 `CheckedInputStream` 指定文件校验算法 （通常使用 `CRC32` 算法），另，在构建压缩文件时，需要考虑目录自动创建，可使用递归方式逐层创建父目录*
>

> `CRC32` 校验

```java
// 输入文件 CRC32 校验
CheckedInputStream cis=new CheckedInputStream(new FileInputStream(srcFile),new CRC32());
ZipInputStream zis=new ZipInputStream(cis);
```

> 创建父目录

```java
private void fileProber(File dirFile){
	File parentFile=dirFile.getParentFile();
	if(!parentFile.exists()){
		// 递归寻找上级目录
		fileProber(parentFile);
		parentFile.mkdir();
	}
}
```

> `ZipEntry` 提取压缩项

```java
private void decompress(File destFile,ZipInputStream zis){
	try{
		ZipEntry entry=null;
		while((entry=zis.getNextEntry())!=null){
			// 文件
			String dir=destFile.getPath()+Util.SEPARATOR+entry.getName();
			File dirFile=new File(dir);
			
			// 文件检查
			fileProber(dirFile);
			if(entry.isDirectory()){
			dirFile.mkdir();
			}else{
			decompressFile(dirFile,zis);
			}
			zis.closeEntry();
		}
	}catch(Exception e){
		LOGGER.error(e.getMessage(),e);
	}
}
```

> 解压缩文件

```java
private void decompressFile(File destFile,ZipInputStream zis){
	try{
		BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(destFile));
	
		int count;
		byte[]data=new byte[Util.BUFFER];
		while((count=zis.read(data,0,Util.BUFFER))!=-1){
			bos.write(data,0,count);
		}
		bos.flush();
		bos.close();
	}catch(Exception e){
		LOGGER.error(e.getMessage(),e);
	}
}
```

- 参考文献

`[ 1 ]` [`Package java.util.zip`](https://www.apiref.com/java11-zh/java.base/java/util/zip/package-summary.html)

`[ 2 ]` [`Class ZipEntry`](https://www.apiref.com/java11-zh/java.base/java/util/zip/ZipEntry.html)

`[ 3 ]` [`Class ZipOutputStream`](https://www.apiref.com/java11-zh/java.base/java/util/zip/ZipOutputStream.html)

`[ 4 ]` [`Java不同压缩算法的性能比较`](http://it.deepinmind.com/java/2015/01/04/performance-general-compression.html)

`[ 5 ]` [`Java Performance Tuning Guide`](http://java-performance.info/performance-general-compression/)

`[ 6 ]` [`使用 GZip 和 Zip 压缩Java数据流`](https://blog.csdn.net/yangdayin/article/details/7240838)