package com.prism.backendfile.service;

import com.prism.common.vo.InfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileService
{
    InfoVo saveFile(MultipartFile file, String path);

    void downloadFileById(Integer id, HttpServletResponse req, String fileNameToBrowser);

    String getFileContentById(Integer id);

    String deleteFileById(Integer id);

    InfoVo getFileById(Integer fid);
}
