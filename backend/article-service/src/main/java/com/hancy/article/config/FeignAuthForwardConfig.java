package com.hancy.article.config;

import com.hancy.blogapp.common.constants.BlogAppHeaderConstants;
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignAuthForwardConfig {

  @Bean
  public RequestInterceptor authHeaderForwardInterceptor() {
    return requestTemplate -> {
      ServletRequestAttributes attrs =
          (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

      if (attrs == null) {
        return;
      }

      HttpServletRequest request = attrs.getRequest();

      String userId = request.getHeader(BlogAppHeaderConstants.AUTH_USER_ID);
      String email = request.getHeader(BlogAppHeaderConstants.AUTH_USER_EMAIL);

      if (userId != null) {
        requestTemplate.header(BlogAppHeaderConstants.AUTH_USER_ID, userId);
      }

      if (email != null) {
        requestTemplate.header(BlogAppHeaderConstants.AUTH_USER_EMAIL, email);
      }
    };
  }
}
