/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nmt.sendgos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author user2
 */
public class SendGos {

    private static Logger log = Logger.getLogger(SendGos.class.getName());
    private static Properties properties;

    private static void iniLogs() {
        try {
            LogManager.getLogManager().readConfiguration(
                    new FileInputStream(new File("logging.properties")));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
    }

    private static void iniProps() {
        try {
            properties = new Properties();
            properties.load(new FileInputStream(new File("config.ini")));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SendGos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SendGos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) {

        iniLogs();
        iniProps();
  

        log.info("------ Start ------");
        if (properties != null) {

            OrderCntr ordercontroler = new OrderCntr(properties);
            List<Order> orderscovid = ordercontroler.getOrdersCovid();
             
            if (orderscovid != null) {

                SendCOVID sendCovid = new SendCOVID(properties);
              //  sendCovid.postorder(orderscovid);
                ordercontroler.updatestatus(orderscovid);
            }

        }
        log.info("------ END ------");
    }



}
