package top.woaibocai.bczx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.service.FileUploadService;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-22 12:31
 **/
@Tag(name = "上传到minio")
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {
    @Resource
    private FileUploadService fileUploadService;
    @Operation(summary = "minio上传文件")
    @PostMapping("fileUpload")
    public Result fileUpload(@RequestParam(value = "file") MultipartFile file){
        //1. 获取上传文件

        //2.调用service的方法上传，返回minio路径
        String url = fileUploadService.upload(file);
        return Result.build(url, ResultCodeEnum.SUCCESS);
    }
}
