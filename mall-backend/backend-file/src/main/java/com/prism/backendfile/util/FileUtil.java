package com.prism.backendfile.util;


import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FileUtil
{
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 通过zip路径和文件路径获取文件内容
     *
     * @param zipPath
     * @param filePath
     * @return
     */
    public static String getZipFileContent(String zipPath, String filePath)
    {
        BufferedReader br = null;
        InputStreamReader isr = null;
        ZipInputStream zi = null;
        try
        {
            ZipFile zip_file = new ZipFile(zipPath);

            FileHeader fh = zip_file.getFileHeader(filePath);
            zi = zip_file.getInputStream(fh);
            char[] b = new char[(int) fh.getUncompressedSize()];
            isr = new InputStreamReader(zi, "utf-8");
            br = new BufferedReader(isr);
            br.read(b);
            zi.close();
            isr.close();
            return String.valueOf(b);
        } catch (Exception e)
        {
            logger.error("get zip file content error! zip path:{}, file " + "path:{}", zipPath, filePath);
        } finally
        {
            try
            {
                if (zi != null)
                {
                    zi.close();
                }
                if (isr != null)
                {
                    isr.close();
                }
                if (br != null)
                {
                    br.close();
                }
            } catch (Exception ex)
            {
            }
        }
        return null;
    }

    /**
     * 获取一个文件的md5值(可处理大文件)
     *
     * @return md5 value
     */
    public static String getMD5(File file)
    {
        FileInputStream fileInputStream = null;
        try
        {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1)
            {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                if (fileInputStream != null)
                {
                    fileInputStream.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 求一个字符串的md5值
     *
     * @param target 字符串
     * @return md5 value
     */
    public static String MD5(String target)
    {
        return DigestUtils.md5Hex(target);
    }
}
