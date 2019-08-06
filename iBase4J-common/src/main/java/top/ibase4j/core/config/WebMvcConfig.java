package top.ibase4j.core.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.util.List;
import javax.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.JstlView;
import top.ibase4j.core.filter.CsrfFilter;
import top.ibase4j.core.filter.LocaleFilter;
import top.ibase4j.core.interceptor.BaseInterceptor;
import top.ibase4j.core.interceptor.MaliciousRequestInterceptor;
import top.ibase4j.core.support.http.ReturnValueHandlerFactory;
import top.ibase4j.core.util.DataUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.PropertiesUtil;









@EnableWebMvc
public abstract class WebMvcConfig
  implements WebMvcConfigurer
{
  @Bean
  public ReturnValueHandlerFactory returnValueHandlerFactory() { return new ReturnValueHandlerFactory(); }

  
  @Bean
  public FilterRegistrationBean<CharacterEncodingFilter> encodingFilterRegistration() {
    CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
    encodingFilter.setEncoding("UTF-8");
    encodingFilter.setForceEncoding(true);
    FilterRegistrationBean<CharacterEncodingFilter> registration = new FilterRegistrationBean<CharacterEncodingFilter>(encodingFilter, new org.springframework.boot.web.servlet.ServletRegistrationBean[0]);
    
    registration.setName("encodingFilter");
    registration.addUrlPatterns(new String[] { "/*" });
    registration.setAsyncSupported(true);
    registration.setOrder(1);
    return registration;
  }
  
  @Bean
  public FilterRegistrationBean<LocaleFilter> localeFilterRegistration() {
    FilterRegistrationBean<LocaleFilter> registration = new FilterRegistrationBean<LocaleFilter>(new LocaleFilter(), new org.springframework.boot.web.servlet.ServletRegistrationBean[0]);
    
    registration.setName("localeFilter");
    registration.addUrlPatterns(new String[] { "/*" });
    registration.setOrder(2);
    return registration;
  }
  
  @Bean
  public FilterRegistrationBean<CsrfFilter> csrfFilterRegistration() {
    FilterRegistrationBean<CsrfFilter> registration = new FilterRegistrationBean<CsrfFilter>(new CsrfFilter(), new org.springframework.boot.web.servlet.ServletRegistrationBean[0]);
    registration.setName("csrfFilter");
    registration.addUrlPatterns(new String[] { "/*" });
    registration.setOrder(3);
    return registration;
  }

  
  public abstract BaseInterceptor eventInterceptor();
  
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.jsp("/WEB-INF/jsp/", ".jsp");
    registry.enableContentNegotiation(new View[] { new JstlView() });
  }

  
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("redirect:/index.html");
    registry.setOrder(-2147483648);
  }


  
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) { configurer.enable(); }


  
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
    List<MediaType> mediaTypes = InstanceUtil.newArrayList();
    mediaTypes.add(MediaType.valueOf("application/json;charset=UTF-8"));
    mediaTypes.add(MediaType.valueOf("text/html"));
    converter.setSupportedMediaTypes(mediaTypes);
    converter.setFeatures(new SerializerFeature[] { SerializerFeature.QuoteFieldNames, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNonStringValueAsString });
    
    converters.add(converter);
  }

  
  public void addInterceptors(InterceptorRegistry registry) {
    MaliciousRequestInterceptor requestInterceptor = new MaliciousRequestInterceptor();
    if (DataUtil.isNotEmpty(PropertiesUtil.getInt("request.minInterval"))) {
      requestInterceptor.setMinRequestIntervalTime(PropertiesUtil.getInt("request.minInterval").intValue());
    }
    requestInterceptor.setNextInterceptor(new BaseInterceptor[] { eventInterceptor() });
    registry.addInterceptor(requestInterceptor).addPathPatterns(new String[] { "/**" }).excludePathPatterns(new String[] { "/*.ico", "/*/api-docs", "/swagger**", "/swagger-resources/**", "/webjars/**", "/configuration/**" });
  }


  
  @Bean
  public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    
    factory.setMaxFileSize("1024MB");
    
    factory.setMaxRequestSize("1024MB");
    return factory.createMultipartConfig();
  }


  
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(new String[] { "upload/**" }).addResourceLocations(new String[] { "/WEB-INF/upload/" });
    registry.addResourceHandler(new String[] { "swagger-ui.html" }).addResourceLocations(new String[] { "classpath:/META-INF/resources/" });
    registry.addResourceHandler(new String[] { "webjars/**" }).addResourceLocations(new String[] { "classpath:/META-INF/resources/webjars/" });
    registry.addResourceHandler(new String[] { "/**" }).addResourceLocations(new String[] { "/WEB-INF/", "classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/" });
  }




  
  public void addCorsMappings(CorsRegistry registry) { registry.addMapping("/api/*").allowedOrigins(new String[] { "*" }).allowCredentials(false)
      .allowedMethods(new String[] { "GET", "POST", "DELETE", "PUT"
        }).allowedHeaders(new String[] { "Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Access-Control-Allow-Methods", "Access-Control-Max-Age"
        
        }).exposedHeaders(new String[] { "Access-Control-Allow-Origin" }).maxAge(3600L); }
}
