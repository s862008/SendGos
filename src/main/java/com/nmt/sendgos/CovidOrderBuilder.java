package com.nmt.sendgos;

import com.nmt.sendgos.Order.Patient;
import java.util.Optional;

public class CovidOrderBuilder implements OrderBuilder {
  private Integer examid;
  private String laboratoryName;
  private String laboratoryOgrn;
  private String short_title;
  private String ogrn;
  private String data;
  private Integer depart;
  private Integer id_anal;
  private String analiz;
  private String dates;
  private String datazanosa;
  private String result;
  private String first_name;
  private String last_name;
  private Optional<String> patronymic;
  private String sex;
  private String age;
  private Optional<String> tel_sot;
  private Optional<String> snils;
  private Optional<String> oms;
  private String doc_type;
  private String doc_number;
  private String doc_ser;
  private String region;
  private Optional<String> town;
  private Optional<String> district;
  private Optional<String> type_town;
  private Optional<String> street;
  private Optional<String> house;
  private Optional<String> appartament;
  private Optional<String> rospis;
  private Optional<String> id_partner;
  private String status;
  private String message;
  private String number;
  private String json;  

    @Override
    public Order build() {
        Order order = new Order();
        order.setExamid(examid);
        Patient patient = order.new Patient();
        patient.name = first_name;
        patient.town = type_town +" "+town;
        order.setPatient(patient);
        
        order.setName(short_title);
        order.
       
        return order;
    }

    @Override
    public String getJsonFormat() {
        json = "order:{}";
        return json;
    }

   

 
    public CovidOrderBuilder setNamber(Integer examid, Integer id_anal) {
        number = String.valueOf(examid) + "-" + String.valueOf(id_anal);
        return this;
    }
    public CovidOrderBuilder setPatient(String first_name, String last_name, String patronymic, String town) {
        this.setFirst_name(first_name);
        this.setLast_name(last_name);
        
        this.town = Optional.ofNullable(town);
        return this;
    }

    
    
}
