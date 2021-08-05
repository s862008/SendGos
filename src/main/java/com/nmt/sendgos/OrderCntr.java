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
                    order.setNamber(rs.getInt("examid"), rs.getInt("ID_ANAL"))
                            .setPatient(rs.getString("first_name"), rs.getString("last_name"), rs.getString("town"));

                    orders.add(order.build());

                }

            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                System.out.println("ошибка запроса результатов " + e);
                return null;
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderCntr.class.getName()).log(Level.SEVERE, null, ex);
        }

        return orders;

    }

    void updatestatus(List<Order> orderscovid) {

        try {
            conn = DBConnection.getDBConnection(properties);

            for (Order order : orderscovid) {
                String sqlstr = "update send_gos set status = ?,message =?  where examid = ? and id_anal = ?";
                try (java.sql.PreparedStatement updSt = conn.prepareStatement(sqlstr)) {
                    
                    updSt.setString(0, order.getStatus());   //updSt.setNull(0, 0);
                    updSt.setString(1, order.getMessage());
                    updSt.setInt(2, order.getExamid());
                    updSt.setInt(3, order.getId_anal());
                    updSt.executeUpdate();
                }
            }
            //System.out.print(orderscovid.get(0).toString());
        } catch (SQLException ex) {
            Logger.getLogger(OrderCntr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(OrderCntr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
