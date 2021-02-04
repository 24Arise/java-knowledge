package com.arise.zip;

import com.arise.zip.service.ICompressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ZipApplicationTests {

    @Autowired
    ICompressService compressService;

    @Test
    void contextLoads() {
        System.out.println("Java 中 zip 压缩学习");
    }


    @Test
    void zipTest() {
        try {
            String srcPath = "/Users/24Arise/Pictures/Anbiao/20210122/唐晶晶";
            compressService.compress(srcPath);
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
