package com.fastproject;

import com.fastproject.utils.sm.SM2KeyGenerator;
import com.fastproject.utils.sm.SM2Utils;
import oshi.SystemInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@EnableScheduling
public class RunServerWork {

    @Bean
    public SystemInfo systemInfo() {
        return new SystemInfo();
    }

    public static void main(String[] args) {
        SpringApplication.run(RunServerWork.class, args);
    }


    public static void key(){
        try {
            // 授权 秘钥
            // 检查 ./data 目录下是否有公钥与私钥文件，没有则生成
            Path dataDir = Paths.get("./data");
            Path publicKeyFile = dataDir.resolve("sm2_public.key");
            Path privateKeyFile = dataDir.resolve("sm2_private.key");

            if (!Files.exists(publicKeyFile) || !Files.exists(privateKeyFile)) {
                // 确保目录存在
                if (!Files.exists(dataDir)) {
                    Files.createDirectories(dataDir);
                }
                // 生成密钥对
                String[] keyPair = SM2KeyGenerator.generateKeyPairHex();
                String publicKey = keyPair[0];
                String privateKey = keyPair[1];

                // 写入文件
                Files.writeString(publicKeyFile, publicKey);
                Files.writeString(privateKeyFile, privateKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
