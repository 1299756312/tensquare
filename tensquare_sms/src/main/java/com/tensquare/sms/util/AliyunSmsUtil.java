package com.tensquare.sms.util;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Data
@Scope(value = "singleton")
@Component
public class AliyunSmsUtil {
    @Value("${aliyun.accesskey}")
    private  String accesskey;
    @Value("${aliyun.accessSecret}")
    private String accessSecret;


    // HttpClient Configuration
        /*HttpClient httpClient = new ApacheAsyncHttpClientBuilder()
                .connectionTimeout(Duration.ofSeconds(10)) // Set the connection timeout time, the default is 10 seconds
                .responseTimeout(Duration.ofSeconds(10)) // Set the response timeout time, the default is 20 seconds
                .maxConnections(128) // Set the connection pool size
                .maxIdleTimeOut(Duration.ofSeconds(50)) // Set the connection pool timeout, the default is 30 seconds
                // Configure the proxy
                .proxy(new ProxyOptions(ProxyOptions.Type.HTTP, new InetSocketAddress("<your-proxy-hostname>", 9001))
                        .setCredentials("<your-proxy-username>", "<your-proxy-password>"))
                // If it is an https connection, you need to configure the certificate, or ignore the certificate(.ignoreSSL(true))
                .x509TrustManagers(new X509TrustManager[]{})
                .keyManagers(new KeyManager[]{})
                .ignoreSSL(false)
                .build();*/

    // Configure Credentials authentication information, including ak, secret, token
   public void   sendSms(Map<String,String> map,String templateCode,String signName) throws ExecutionException, InterruptedException {
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(this.accesskey)
                .accessKeySecret(this.accessSecret)
                //.securityToken("<your-token>") // use STS token
                .build());
        // Configure the Client
        AsyncClient client = AsyncClient.builder()
                .region("cn-hangzhou") // Region ID
                //.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
                .credentialsProvider(provider)
                //.serviceConfiguration(Configuration.create()) // Service-level configuration
                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                        //.setReadTimeout(Duration.ofSeconds(30))
                )
                .build();
        // Parameter settings for API request

       SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
               .signName(signName)
               .templateCode(templateCode)
               .phoneNumbers(map.get("mobile"))
               .templateParam(" {\"code\":\""+ map.get("code") +"\"}")
               // Request-level configuration rewrite, can set Http request parameters, etc.
               // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
               .build();

       // Asynchronously get the return value of the API request
       CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
       // Synchronously get the return value of the API request
       SendSmsResponse resp = response.get();
       System.out.println(new Gson().toJson(resp));
       // Asynchronous processing of return values
        /*response.thenAccept(resp -> {
            System.out.println(new Gson().toJson(resp));
        }).exceptionally(throwable -> { // Handling exceptions
            System.out.println(throwable.getMessage());
            return null;
        });*/

 client.close();
    }


}
