package com.zxw.zcloud.demo.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class SecretKeyUtil {

    @Value("${zcloud.debug}")
    private boolean isDebug;

    private String resourcePath = "classpath:key/";

    private String lyfPrivateKey = "";

    private String lyfPublicKey = "";

    private String platformPrivateKey = "";

    private String platformPublicKey = "";

    @Autowired
    ResourceLoader resourceLoader;

    @PostConstruct
    public void postConstruct() {
        if (isDebug){
            resourcePath = resourcePath + "dev/";
        } else {
            resourcePath = resourcePath + "prod/";
        }
        lyfPrivateKey = this.readFile(resourcePath + "lyfPrivateKey.txt");
        lyfPublicKey = this.readFile(resourcePath + "lyfPublicKey.txt");
        platformPrivateKey = this.readFile(resourcePath + "platformPrivateKey.txt");
        platformPublicKey = this.readFile(resourcePath + "platformPublicKey.txt");
    }

    private String readFile(String filePathName) {
        try {
            String str = "";
            Resource resource = resourceLoader.getResource(filePathName);
            InputStream is = resource.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String data = "";
            while ((data = br.readLine()) != null) {
                str += data;
            }

            br.close();
            isr.close();
            is.close();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getLyfPrivateKey() {
        return lyfPrivateKey;
    }

    public String getLyfPublicKey() {
        return lyfPublicKey;
    }

    public String getPlatformPrivateKey() {
        return platformPrivateKey;
    }

    public String getPlatformPublicKey() {
        return platformPublicKey;
    }
}
