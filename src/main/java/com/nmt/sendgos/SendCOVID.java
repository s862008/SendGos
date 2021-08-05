/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nmt.sendgos;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author user2
 */
class SendCOVID {

    Properties properties;

    SendCOVID(Properties properties) {
        this.properties = properties;
    }

    void postorder(List<Order> orderscovid) {

        HttpPost post = new HttpPost("https://result.crie.ru/api/v2/order/get-depart-token");
        post.addHeader("Content-Type", "application/json; charset=UTF-8");
        post.addHeader("Accept", "application/json");
  
        String body_with_token = "{\"depart_number\":\"" + properties.getProperty("DEPART_NUMBER") + "\",\"token\":\""+properties.getProperty("TOKEN")+"\"}";
      
        post.setEntity(new StringEntity(body_with_token, "UTF-8"));
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(post)) {

            String jsonStr = EntityUtils.toString(response.getEntity());
            System.out.println(jsonStr);
           

        } catch (Exception ex) {
            Logger.getLogger(SendCOVID.class.getName()).log(Level.SEVERE, "ERROR EXECUTE POST", ex);
        }
        
        
        try {  
          post.setURI(new URI("https://result.crie.ru/api/v2/order/ext-orders-package"));
          
          
        } catch (URISyntaxException ex) {
            Logger.getLogger(SendCOVID.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

}
