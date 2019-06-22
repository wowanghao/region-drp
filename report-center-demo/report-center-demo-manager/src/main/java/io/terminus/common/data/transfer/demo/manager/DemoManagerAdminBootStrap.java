package io.terminus.common.data.transfer.demo.manager;

import io.terminus.common.utils.Splitters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import java.util.Properties;

/**
 * date 2018/9/26
 *
 * @author yushuo
 */

@Slf4j
@SpringBootApplication
@ComponentScan("io.terminus.parana")
public class DemoManagerAdminBootStrap {

    public static void main(String[] args) {
        SpringApplication.run(DemoManagerAdminBootStrap.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> log.info("DemoManagerAdminBootStrap started");
    }

    @Bean
    public MessageSource messageSource(
            @Value("${admin.messages.classpath:classpath:message/messages}") String messagesClasspath) {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        for (String classpath : Splitters.COMMA.splitToList(messagesClasspath)) {
            messageSource.addBasenames(classpath);
        }
        Properties p = new Properties();

        p.setProperty("server.error", "系统异常 , 请联系管理员 .");

        messageSource.setCommonMessages(p);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.CHINA);
        return slr;
    }
}

