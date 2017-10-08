package com.jc;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan(basePackages = {"com.jc.mapper", "com.jc.security.mapper"})
@EnableAspectJAutoProxy
public class JcApplyApplication {

    public static void main(String[] args) {
        SpringApplication.run(JcApplyApplication.class, args);
    }


    /**
     * This config is for tomcat upload file
     * If you try to upload a file size, which exceeded the size limit(default 2mb)
     * Tomcat will crop it at the end of 2mb and reset the connection.
     * To solve it quickly, put a -1 (unlimited) for maxSwallowSize
     * As for multipart file upload, check: spring.http.multipart.max-file-size
     * in application-dev.yml
     *
     * As for stand-alone tomcat webapp, try follows in server.xml
     * <Connector port="8080" protocol="HTTP/1.1"
     * connectionTimeout="20000"
     * redirectPort="8443"
     * maxSwallowSize="10485760" />
     *
     * 10485760 = 10mb (10 x 1024 x 1024 bytes)
     *
     * @return
     */
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                //-1 means unlimited
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });

        return tomcat;

    }

}
