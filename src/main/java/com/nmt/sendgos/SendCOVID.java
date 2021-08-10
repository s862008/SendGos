/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nmt.sendgos;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author user2
 */
class SendCOVID {

    Properties properties;

    SendCOVID(Properties properties) {
        this.properties = properties;
    }

    void postorder(List<Order> orders) {

        String token = "";
        HttpPost post = new HttpPost("https://result.crie.ru/api/v2/order/get-depart-token");
        post.addHeader("Content-Type", "application/json; charset=UTF-8");
        post.addHeader("Accept", "application/json");

        String body_with_token = "{\"depart_number\":\"" + properties.getProperty("DEPART_NUMBER") + "\",\"token\":\"" + properties.getProperty("TOKEN") + "\"}";

        post.setEntity(new StringEntity(body_with_token, "UTF-8"));

        try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {

            String jsonStr = EntityUtils.toString(response.getEntity());
            //{"name":"Bad Request","message":"ЛПУ 989999 нет в базе данных! Токен не валидный","code":0,"status":400,"type":"yii\\web\\BadRequestHttpException"}
            //{"header":{"api":"2.0","dt":"2021-08-10T13:02:54+0300","latency":10,"route":"/api/v2/order/get-depart-token","status":"ok","errors":[]},"body":{"token":"5D779458-E9BA-8B18-1A80-E92469EA62E9"}}
            Logger.getLogger(SendCOVID.class.getName()).log(Level.INFO, "Ответ сервера: " + jsonStr);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject;
            try {
                jsonObject = (JSONObject) jsonParser.parse(jsonStr);
                JSONObject header = (JSONObject) jsonObject.get("header");

                if (header != null && header.get("status").equals("ok")) {
                    JSONObject arr2 = (JSONObject) jsonObject.get("body");
                    token = (String) arr2.get("token");
                } else {
                    Logger.getLogger(SendCOVID.class.getName()).log(Level.SEVERE, "Ошибка получения токена!");
                }

            } catch (ParseException ex) {
                Logger.getLogger(SendCOVID.class.getName()).log(Level.SEVERE, "Ошибка разбора json", ex);
            }

        } catch (Exception ex) {
            Logger.getLogger(SendCOVID.class.getName()).log(Level.SEVERE, "ERROR EXECUTE POST", ex);
        }

        if (!token.isEmpty()) {

            String json_orders = "";
            Map<String, Order> map = new HashMap<String, Order>();

            Iterator<Order> ordersIterator = orders.iterator();
            while (ordersIterator.hasNext()) {
                Order o = ordersIterator.next();
                if (o.getStatus() == null) {
                    map.put(o.getNumber(), o);
                    json_orders += ",{\\\"order\\\":"
                            + "{\\\"number\\\":\\\"" + o.getNumber() + "\\\",\\\"depart\\\":\\\"" + o.getDepart() + "\\\",\n"
                            + "\\\"laboratoryName\\\":\\\"" + o.getLaboratoryName() + "\\\","
                            + "\\\"laboratoryOgrn\\\":\\\"" + o.getLaboratoryOgrn() + "\\\",\n"
                            + "\\\"name\\\":\\\"" + o.getName() + "\\\",\n"
                            + "\\\"ogrn\\\":\\\"" + o.getOgrn() + "\\\",\n"
                            + "\\\"orderDate\\\":\\\"" + o.getOrderData() + "\\\",\n"
                            + "\\\"serv\\\":[{\\\"code\\\":\\\"" + o.getServ_code() + "\\\",\n"
                            + "\\\"name\\\":\\\"" + o.getServ_name() + "\\\",\n"
                            + "\\\"testSystem\\\":\\\"\\\",\n"
                            + "\\\"biomaterDate\\\":\\\"" + o.getServ_biomaterDate() + "\\\",\n"
                            + "\\\"readyDate\\\":\\\"" + o.getServ_readyDate() + "\\\",\n"
                            + "\\\"result\\\":" + o.getServ_result() + ",\\\"value\\\":" + o.getServ_value() + ",\\\"type\\\":" + o.getServ_type() + "}],"
                            + "\\\"patient\\\":{\\\"surname\\\":\\\"" + o.getPatient().surname + "\\\",\\\"name\\\":\\\"" + o.getPatient().name + "\\\",\n"
                            + "\\\"patronymic\\\":\\\"" + o.getPatient().patronymic + "\\\",\n"
                            + "\\\"gender\\\":" + o.getPatient().gender + ",\\\"birthday\\\":\\\"" + o.getPatient().birthday + "\\\",\n"
                            //   + "\\\"phone\\\":\\\"9003030857\\\",\\\"email\\\":\\\"\\\",\n"
                            + "\\\"documentType\\\":\\\"" + o.getPatient().doc_type + "\\\",\n"
                            + "\\\"documentNumber\\\":\\\"" + o.getPatient().doc_number + "\\\",\\\"documentSerNumber\\\":\\\"" + o.getPatient().doc_ser + "\\\",\n"
                            + "\\\"snils\\\":\\\"\\\",\\\"oms\\\":\\\"\\\",\n"
                            + "\\\"address\\\":{\\\"regAddress\\\":{\\\"town\\\":\\\"" + o.getPatient().town + "\\\",\n"
                            + "\\\"house\\\":\\\"" + o.getPatient().house + "\\\",\\\"state\\\":null,\\\"region\\\":\\\"" + o.getPatient().region + "\\\",\n"
                            + "\\\"building\\\":null,\\\"district\\\":null,\n"
                            + "\\\"appartament\\\":\\\"" + o.getPatient().appartament + "\\\",\n"
                            + "\\\"streetName\\\":\\\"" + o.getPatient().streetName + "\\\"},\n"
                            + "\\\"factAddress\\\":{\\\"town\\\":\\\"\\\",\\\"house\\\":\\\"\\\",\n"
                            + "\\\"state\\\":null,\\\"region\\\":\\\"" + o.getPatient().region + "\\\",\\\"building\\\":\\\"\\\",\n"
                            + "\\\"district\\\":null,\\\"appartament\\\":\\\"\\\",\n"
                            + "\\\"streetName\\\":\\\"\\\"}}}}}\n\n";
                }
            }

            Logger.getLogger(SendCOVID.class.getName()).log(Level.INFO, "Количество заказов подготовленых для отправки на сервер: " + map.size());
            if (json_orders.length() > 0) {
                String body_with_orders = String.format("{\"token\": \"%s\","
                        + "\"depart_number\": \"%s\",\n"
                        + "\"json\":\"[%s]\"}",
                        token, properties.getProperty("DEPART_NUMBER"), json_orders.substring(1));

                Logger.getLogger(SendCOVID.class.getName()).log(Level.INFO, "Сформировано тело запроса: " + body_with_orders);
                try {
                    post.setURI(new URI("https://result.crie.ru/api/v2/order/ext-orders-package"));
                } catch (URISyntaxException ex) {
                    Logger.getLogger(SendCOVID.class.getName()).log(Level.SEVERE, null, ex);
                }
                post.setEntity(new StringEntity(body_with_orders.replaceAll("\n", ""), "UTF-8"));
                try (CloseableHttpClient httpClient = HttpClients.createDefault(); CloseableHttpResponse response = httpClient.execute(post)) {

                    String jsonStr = EntityUtils.toString(response.getEntity());

                    Logger.getLogger(SendCOVID.class.getName()).log(Level.INFO, "Ответ сервера: " + jsonStr);
                    //{"header":{"api":"2.0","dt":"2021-08-09T17:31:05+0300","latency":25,"route":"/api/v2/order/ext-orders-package","status":"ok","errors":[]},"body":[{"number":"538735-2001368","status":"error","id":null,"message":"Номер документа должен содержать более 4-х символов(documentNumber)"}]} 
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject;
                    try {
                        jsonObject = (JSONObject) jsonParser.parse(jsonStr);
                        JSONObject header = (JSONObject) jsonObject.get("header");
                        if (header != null && header.get("status").equals("ok")) {
                            JSONArray arr = (JSONArray) jsonObject.get("body");
                            Iterator i = arr.iterator();
                            while (i.hasNext()) {
                                JSONObject innerObj = (JSONObject) i.next();
                                map.get((String) innerObj.get("number")).setStatus((String) innerObj.get("status"));
                                map.get((String) innerObj.get("number")).setMessage((String) innerObj.get("message"));
                                map.get((String) innerObj.get("number")).setSendDate((String) header.get("dt"));
                            }

                        } else {
                            Logger.getLogger(SendCOVID.class.getName()).log(Level.SEVERE, "Ошибка получения статуса от сервера!");
                        }

                    } catch (ParseException ex) {
                        Logger.getLogger(SendCOVID.class.getName()).log(Level.SEVERE, "Ошибка разбора json", ex);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(SendCOVID.class.getName()).log(Level.SEVERE, "ERROR EXECUTE POST", ex);
                    post.abort();
                }

            }

        }
        post.abort();
    }

}
