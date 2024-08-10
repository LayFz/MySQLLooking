package com.staraid.mysql;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.math.BigInteger;
import org.bouncycastle.util.io.pem.PemReader;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CertiTest {
    @Test
    void contextLoads() {
        try {
            // 读取PEM文件
            PemReader pemReader = new PemReader(new FileReader("V:\\炽游\\炽游信息\\微信支付证书\\apiclient_cert.p12"));
            byte[] pemContent = pemReader.readPemObject().getContent();
            pemReader.close();

            // 创建CertificateFactory
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

            // 解析证书
            X509Certificate certificate = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(pemContent));

            // 获取序列号
            BigInteger serialNumber = certificate.getSerialNumber();
            System.out.println("序列号: " + serialNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
