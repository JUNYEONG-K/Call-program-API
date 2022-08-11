package fis.police.fis_police_server;

import fis.police.fis_police_server.interceptor.LogInterceptor;
import fis.police.fis_police_server.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@SpringBootApplication
public class FisPoliceServerApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(FisPoliceServerApplication.class, args);
    }

	@Configuration
	public class WebConfig implements WebMvcConfigurer {
        @Value("${profileImg.path}")
        private String profileUploadFolder;
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            WebMvcConfigurer.super.addResourceHandlers(registry);
            registry.addResourceHandler("/agent/picture/**")
                    .addResourceLocations(profileUploadFolder)
                    .setCachePeriod(60 * 10 * 6)
                    .resourceChain(true)
                    .addResolver(new PathResourceResolver());
        }

		@Override
		public void addCorsMappings(CorsRegistry registry){
			registry.addMapping("/**")
					.allowedOriginPatterns("*", "http://3.35.135.214/*")
					.allowCredentials(true)
                    .allowedHeaders("*")
					.allowedMethods("*");
		}
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LogInterceptor())
                    .order(1)
                    .addPathPatterns("/**")
                    .excludePathPatterns("/css/**", "/*.ico", "/error","/messenger/*", "/app/**");

            registry.addInterceptor(new LoginCheckInterceptor())
                    .order(2)
                    .addPathPatterns("/**")
                    .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error", "/messenger/*", "/app/**");
        }
    }
}
