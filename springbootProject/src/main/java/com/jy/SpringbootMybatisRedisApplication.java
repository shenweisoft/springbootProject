package com.jy;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.jy.mapper")
@ConfigurationProperties(prefix = "socketConfig")
public class SpringbootMybatisRedisApplication {

    @Value("${socketConfig.macAddress}")
    private String macAddress;

    @Value("${socketConfig.realAddress}")
    private String realAddress;

    @Value("${socketConfig.port}")
    private Integer port;



    //日志级别由低到高 trace<debug<info<warn<error
    //SpringBoot默认给我们使用的是info级别的，没有指定级别的就用SpringBoot默认规定的级别；
    private static Logger logger = LoggerFactory.getLogger(SpringbootMybatisRedisApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(SpringbootMybatisRedisApplication.class, args);
    }


    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();

        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){
            //在本地window环境测试时用localhost
            config.setHostname(macAddress);
        } else if(os.toLowerCase().startsWith("mac")){
            config.setHostname(macAddress);
        }else {
            //部署到你的远程服务器正式发布环境时用服务器公网ip
            config.setHostname(realAddress);
        }
        config.setPort(port);

		/*config.setAuthorizationListener(new AuthorizationListener() {//类似过滤器
			@Override
			public boolean isAuthorized(HandshakeData data) {
				//http://localhost:8081?username=test&password=test
				//例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息，本文不做身份验证
				// String username = data.getSingleUrlParam("username");
				// String password = data.getSingleUrlParam("password");
				return true;
			}
		});*/


        final SocketIOServer server = new SocketIOServer(config);
        return server;
    }

    @Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);

    }
}
