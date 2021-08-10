/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nmt.sendgos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user2
 */
public class OrderCntr {

    Connection conn;
    Properties properties;

    public OrderCntr(Properties properties) {
        this.properties = properties;
    }

    List<Order> getOrdersCovid() {
        List<Order> orders = new ArrayList<Order>();
        try {
            conn = DBConnection.getDBConnection(properties);
            String sqlstr = "select * from order_sendgos";

            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlstr);) {

                while (rs.next()) {
                    CovidOrderBuilder order = new CovidOrderBuilder();
                    order.setDepart(Integer.parseInt((String) properties.get("DEPART_NUMBER")))
                            .setLaboratory(rs.getString("laboratoryName"), rs.getString("laboratoryOgrn"))
                            .setNamber(rs.getInt("examid"), rs.getInt("ID_ANAL"))
                            .setOrderDate(rs.getString("data"))
                            .setServ(rs.getInt("ID_ANAL"), rs.getString("analiz"), rs.getString("result"), rs.getInt("analtp"), rs.getString("dates"), rs.getString("datazanosa"))
                            .setMarker(rs.getString("marker"))
                            .setPatient(rs.getString("first_name"), rs.getString("last_name"), rs.getString("patronymic"), rs.getString("sex"), rs.getString("age"), rs.getString("tel_sot"))
                            .setPatientDocs(rs.getString("snils"), rs.getString("oms"), rs.getString("doc_type"), rs.getString("doc_number"), rs.getString("doc_ser"))
                            .setPatientAddress(rs.getString("region"), rs.getString("town"), rs.getString("district"), rs.getString("type_town"), rs.getString("street"), rs.getString("house"), rs.getString("appartament"))
                            .setRospis(rs.getString("rospis"))
                            .setOrderDate(rs.getString("data"));
                    if (rs.getString("short_title") != null) {
                        order.setCompanyZakazchic(rs.getString("short_title"), rs.getString("ogrn"));
                    } else {
                        order.setCompanyZakazchic(rs.getString("partner_short_title"), rs.getString("partner_ogrn"));
                    }
                    order.setCheck(rs.getString("checkclient"));

                    orders.add(order.build());

                }

            } catch (Exception e) {
                Logger.getLogger(OrderCntr.class.getName()).log(Level.SEVERE, "Ошибка запроса результатов ", e);
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderCntr.class.getName()).log(Level.SEVERE, "Ошибка SQL", ex);
        }

        return orders;

    }

    void updatestatus(List<Order> orderscovid) {

        try {
            conn = DBConnection.getDBConnection(properties);

            for (Order order : orderscovid) {
                String sqlstr = "update send_gos set status = ?,message =?,senddate=?  where examid = ? and anal_id = ?";
                try (java.sql.PreparedStatement updSt = conn.prepareStatement(sqlstr)) {
           
                    updSt.setString(1, order.getStatus());
                    updSt.setString(2, order.getMessage());
                    updSt.setTimestamp(3, (order.getSendDate() == null ? null : Timestamp.valueOf(order.getSendDate().replace("T", " ").substring(0,19))));
                    updSt.setInt(4, order.getExamid());
                    updSt.setInt(5, order.getServ_code());
                    updSt.executeUpdate();
                } catch (Exception ex) {
                    Logger.getLogger(OrderCntr.class.getName()).log(Level.SEVERE, "Ошибка обновления статуса", ex);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(OrderCntr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderCntr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
