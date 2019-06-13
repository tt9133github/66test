package com.prism.backendfile.controller;

import com.prism.backendfile.service.FileService;
import com.prism.common.vo.InfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController
{
    @Autowired
    FileService fileService;

    @RequestMapping(value = "/{path}", method = RequestMethod.POST)
    @ResponseBody
    public InfoVo uploadFileToSpecificPath(MultipartFile file, @PathVariable("path") String path)
    {
        return fileService.saveFile(file, path);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public InfoVo uploadFileToDefaultPath(@RequestBody MultipartFile file)
    {
        return fileService.saveFile(file, null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public InfoVo getFileById(@PathVariable("id") Integer id)
    {
        return fileService.getFileById(id);
    }

}
