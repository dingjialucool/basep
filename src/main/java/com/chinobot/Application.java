package com.chinobot;

import com.github.tobato.fastdfs.FdfsClientConfig;

import java.util.TimeZone;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.util.unit.DataSize;

@Configuration
@SpringBootApplication
@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@MapperScan({"com.baomidou.mybatisplus.samples.quickstart.mapper","com.chinobot.aiuas.*.*.mapper", "com.chinobot.*.*.mapper"})
public class Application {

    public static void main(String[] args) {
    	TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(Application.class, args);
    }

    /**
     *      * 文件上传配置      * @return      
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个数据大小
        factory.setMaxFileSize(DataSize.ofMegabytes(10240)); // KB,MB
        /// 总上传数据大小

        factory.setMaxRequestSize(DataSize.ofGigabytes(10240));
        return factory.createMultipartConfig();
    }

}
