package com.prism.backendfile.service;

import com.prism.backendfile.domain.SysFiles;
import com.prism.backendfile.repository.FilesRepository;
import com.prism.backendfile.util.FileUtil;
import com.prism.common.vo.InfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.Line;
import java.io.*;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService
{
    private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Autowired
    FilesRepository filesRepository;
    @Value("${file_path}")
    private String defaultPath;

    @Override
    public InfoVo saveFile(MultipartFile file, String dir)
    {
        if (dir == null)
        {
            dir = defaultPath;
        }

        String fileName = System.currentTimeMillis() + "." + file.getOriginalFilename().split("\\.")[1];
        String path = dir + File.separator + fileName;
        InfoVo infoVo = uploadFileToDisk(file, path);
        if (infoVo.getStatus() != InfoVo.HttpStatus.Ok)
        {
            return infoVo;
        }

        SysFiles sysFiles = new SysFiles();
        sysFiles.setFilename(file.getOriginalFilename());
        sysFiles.setPath(path);
        sysFiles.setVolume(file.getSize());
        sysFiles.setMd5(FileUtil.getMD5(new File(path)));

        filesRepository.save(sysFiles);
        infoVo.setData(sysFiles);

        return infoVo;
    }

    public InfoVo uploadFileToDisk(MultipartFile file, String path)
    {
        File dest = new File(path);
        logger.info(path);
        if (!dest.getParentFile().exists())
        {
            logger.info("no dir making dir");
            dest.getParentFile().mkdir();
            logger.info("make dir success");
        }
        try
        {
            file.transferTo(dest);
            logger.info("file {} uploaded", file.getName());
        } catch (IllegalStateException e)
        {
            logger.error("upload {} failed, error: {}", file.getName(), e.toString());
            return new InfoVo("failed!" + e.toString(), 0);
        } catch (IOException e)
        {
            logger.error("upload {} failed, error: {}", file.getName(), e.toString());
            return new InfoVo("failed!" + e.toString(), 0);
        }
        return new InfoVo("success", InfoVo.HttpStatus.Ok, dest.getPath());
    }

    @Override
    public void downloadFileById(Integer id, HttpServletResponse req, String fileNameToBrowser)
    {
        Optional<SysFiles> optionalFiles = filesRepository.findById(id);
        if (!optionalFiles.isPresent())
        {
            logger.debug("can not find file id: {}", id);
            return;
        }
        SysFiles file = optionalFiles.get();
        String path = file.getPath();

        req.setHeader("content-type", "application/octet-stream;charset=UTF-8");
        try
        {
            fileNameToBrowser = new String((fileNameToBrowser).getBytes(), "iso-8859-1");
            req.setHeader("Content-Disposition", "attachment;filename=" + fileNameToBrowser);
        } catch (UnsupportedEncodingException e)
        {
            logger.error("download file {} error", path);
            logger.error(e.toString());
            return;
        }

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        FileInputStream f = null;
        OutputStream os = null;
        try
        {
            os = req.getOutputStream();

            f = new FileInputStream(new File(path));
            bis = new BufferedInputStream(f);
            int i = bis.read(buff);
            while (i != -1)
            {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        } finally
        {
            if (bis != null)
            {
                try
                {
                    bis.close();
                    os.close();
                    f.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getFileContentById(Integer id)
    {
        return null;
    }

    @Override
    public String deleteFileById(Integer id)
    {
        return null;
    }

    @Override
    public InfoVo getFileById(Integer fid)
    {
        Optional<SysFiles> optional = filesRepository.findById(fid);
        if(optional.isPresent())
        {
            return new InfoVo("success",InfoVo.HttpStatus.Ok,  optional.get());
        }
        return new InfoVo("can not find fid",InfoVo.HttpStatus.Failed,  null);

    }
}
