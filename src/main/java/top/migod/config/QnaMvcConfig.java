package top.migod.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class QnaMvcConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/user/*.html", "/user/*");
    }


    @Value("${qna.avatar_folder_path}")
    private String avatarFolderPath;

    @Value("${qna.avatar_folder_path.relative}")
    private String avatorFolderRelativePath;

    //映射头像地址
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(avatorFolderRelativePath).
                addResourceLocations("file:" + avatarFolderPath);
    }
}
