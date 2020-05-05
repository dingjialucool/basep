package com.chinobot.plep.app.version.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.file.util.FileClient;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.QRCodeUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.setting.entity.Setting;
import com.chinobot.plep.home.setting.service.ISettingService;
import com.google.zxing.WriterException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2019-11-04 16:23
 */
@Api(tags = "app版本管理接口")
@RestController
@RequestMapping("/api/app/version")
public class AppVersionController extends BaseController {

    @Autowired
    private ISettingService settingService;
    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private FileClient fileClient;

    /**
     * 检查app是否有新版本
     *
     * @Param: [versionName app版本号, versionCode apk版本号]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2019/11/4 17:30
     */
    @ApiOperation(value = "检查app是否有新版本", notes = "检查app是否有新版本")
    @GetMapping("/check")
    public Result checkVersion(String versionName, String versionCode) {
        Map result = null;

        // 巡查APK版本号
        QueryWrapper<Setting> versionCodeWrapper = new QueryWrapper();
        versionCodeWrapper.eq("code", "appVersionCode");
        Setting appVersionCode = settingService.getOne(versionCodeWrapper);

        // 服务器有新版本APK
        if (Integer.parseInt(appVersionCode.getVal()) > Integer.parseInt(versionCode)) {
            QueryWrapper<Setting> fileAPKWrapper = new QueryWrapper();
            fileAPKWrapper.eq("code", "appFileAPK");
            Setting appFileAPK = settingService.getOne(fileAPKWrapper);

            QueryWrapper<Setting> versionNameWrapper = new QueryWrapper();
            versionNameWrapper.eq("code", "appVersionName");
            Setting appVersionName = settingService.getOne(versionNameWrapper);

            UploadFile uploadFile = uploadFileService.getById(appFileAPK.getVal());

            result = new HashMap<>();
            result.put("fileId", appFileAPK.getVal());
            result.put("newVersion", appVersionName.getVal());
            result.put("fileSize", uploadFile.getSize());
        }

        return ResultFactory.success(result);
    }

    /**
     * 下载apk
     *
     * @Param: [response]
     * @Return: void
     * @Author: shizt
     * @Date: 2019/11/7 15:07
     */
    @ApiOperation(value = "下载app", notes = "下载app")
    @GetMapping("/downloadApk")
    public String downloadApk(HttpServletResponse resp) throws IOException {
        QueryWrapper<Setting> fileAPKWrapper = new QueryWrapper();
        fileAPKWrapper.eq("code", "appFileAPK");
        Setting appFileAPK = settingService.getOne(fileAPKWrapper);

        UploadFile uploadFile = uploadFileService.getById(appFileAPK.getVal());
        String filePath = CommonUtils.objNotEmpty(uploadFile) ? uploadFile.getPath() : null;

        if(null != filePath) {
            ServletOutputStream out = null;
            try {
                byte[] bytes = fileClient.downloadFile(filePath);
                resp.reset();// 清空输出流
                resp.setHeader("content-type", "application/vnd.android.package-archive");
                resp.setContentType("application/vnd.android.package-archive");
                resp.setHeader("Content-Disposition", "attachment;filename=" + uploadFile.getOriginName());
                resp.setContentLength(bytes.length);

                out = resp.getOutputStream();
                out.write(bytes);
                out.flush();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(out!=null) {
                    out.close();
                }
            }
        }
        return null;
    }

    /**
     * 获取apk下载二维码
     *
     * @Param: []
     * @Return: org.springframework.http.ResponseEntity<byte [ ]>
     * @Author: shizt
     * @Date: 2019/11/7 15:35
     */
    @ApiOperation(value = "获取apk下载二维码", notes = "获取apk下载二维码")
    @GetMapping("/getApkQRImg")
    public ResponseEntity<byte[]> getApkQRImg(HttpServletRequest request) throws IOException, WriterException {
        QueryWrapper<Setting> fileAPKWrapper = new QueryWrapper();
        fileAPKWrapper.eq("code", "appFileAPK");
        Setting appFileAPK = settingService.getOne(fileAPKWrapper);

        String url = request.getServerName() + ":" + request.getServerPort()
                + "/plep/api/app/version/downloadApk";
        byte[] qrcode = QRCodeUtils.getQRCodeImage(url, 300, 300);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(qrcode, headers, HttpStatus.CREATED);
    }
}
