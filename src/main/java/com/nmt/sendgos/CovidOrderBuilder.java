package com.nmt.sendgos;

import com.nmt.sendgos.Order.Patient;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private Integer analtp;
    private String datazabora;
    private String datazanosa;
    private String result;
    private Optional<String> marker;
    private String first_name;
    private String last_name;
    private Optional<String> patronymic;
    private String sex;
    private String age;
    private Optional<String> tel_sot;
    private Optional<String> snils;
    private Optional<String> oms;
    private Optional<String> doc_type;
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
    private String check;
    private String status;
    private String message;
    private String number;
    private String json;

    @Override
    public Order build() {
        Order order = new Order();
        order.setNumber(number);
        order.setExamid(examid);
        order.setName(short_title);
        order.setOgrn(ogrn);
        order.setLaboratoryName(laboratoryName);
        order.setLaboratoryOgrn(laboratoryOgrn);
        order.setDepart(depart);
        order.setOrderData(data.substring(0, 10).trim());
        order.setServ_type(analtp);
        order.setServ_code(id_anal);
        order.setServ_name(analiz);
        order.setServ_biomaterDate(datazabora.substring(0, 10).trim());
        order.setServ_readyDate(datazanosa.substring(0, 10).trim());

        if (analtp == 4) {
            order.setServ_value(result);
            order.setServ_result("null");
        } else {
            order.setServ_result(marker.orElse(""));
            order.setServ_value("null");
        }
        if (!marker.isPresent()) {
            System.out.println("ЕСТЬ marker равный null в заказе " + examid);
            order.setStatus("error");
            order.setMessage("marker = null; ");
        }

        Patient patient = order.new Patient();
        patient.name = this.first_name;
        patient.surname = this.last_name;
        patient.patronymic = this.patronymic.orElse("");
        patient.gender = this.sex.equals("м") ? "1" : "2";
        patient.birthday = this.age.substring(0, 10).trim();
        patient.phone = this.tel_sot.orElse("");
        patient.snils = this.snils.orElse("");
        patient.oms = this.oms.orElse("");
        patient.doc_type = this.doc_type.orElse("").equals("Паспорт") ? "Паспорт гражданина РФ" : this.doc_type.orElse("");
        patient.doc_number = this.doc_number;
        patient.doc_ser = this.doc_ser;
        patient.region = this.region;
        patient.district = this.district.orElse("");
        patient.streetName = this.street.orElse("");
        patient.town = (type_town.orElse("") + " " + town.orElse("")).trim();
        patient.appartament = this.appartament.orElse("");
        patient.house = this.house.orElse("");

        order.setPatient(patient);

        if (!(rospis.orElse("")).equals("1")) {
            order.setStatus("error");
            order.setMessage((Optional.ofNullable(order.getMessage()).orElse("")) + "нет росписи; ");
        }
        if (check == null) {
           // order.setStatus("error");
           // order.setMessage((Optional.ofNullable(order.getMessage()).orElse("")) + "недостаточно данных для отправки заказа;");
        }

        Logger.getLogger(CovidOrderBuilder.class.getName()).log(Level.INFO, "Собран заказ: " + order.toString());

        return order;
    }

    @Override
    public String getJsonFormat() {
        return json;
    }

    public CovidOrderBuilder setNamber(Integer examid, Integer id_anal) {
        this.examid = examid;
        this.id_anal = id_anal;
        this.number = String.valueOf(examid) + "-" + String.valueOf(id_anal);
        return this;
    }

    public CovidOrderBuilder setCompanyZakazchic(String short_title, String ogrn) {
        this.short_title = short_title;
        this.ogrn = ogrn;
        return this;
    }

    public CovidOrderBuilder setPatient(String first_name, String last_name, String patronymic, String sex, String age,
            String tel_sot) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.patronymic = Optional.ofNullable(patronymic);
        this.sex = sex;
        this.age = age;
        this.tel_sot = Optional.ofNullable(tel_sot);

        return this;
    }

    public CovidOrderBuilder setPatientDocs(String snils, String oms, String doc_type, String doc_number, String doc_ser) {
        this.snils = Optional.ofNullable(snils);
        this.oms = Optional.ofNullable(oms);
        this.doc_type = Optional.ofNullable(doc_type);
        this.doc_number = doc_number;
        this.doc_ser = doc_ser;
        return this;
    }

    public CovidOrderBuilder setPatientAddress(String region, String town, String district, String type_town, String street, String house, String appartament) {

        this.region = region;
        this.town = Optional.ofNullable(town);
        this.district = Optional.ofNullable(district);
        this.type_town = Optional.ofNullable(type_town);
        this.street = Optional.ofNullable(street);
        this.house = Optional.ofNullable(house);
        this.appartament = Optional.ofNullable(appartament);

        return this;
    }

    public CovidOrderBuilder setLaboratory(String laboratoryName, String laboratoryOgrn) {
        this.laboratoryName = laboratoryName;
        this.laboratoryOgrn = laboratoryOgrn;
        return this;
    }

    public CovidOrderBuilder setRospis(String rospis) {
        this.rospis = Optional.ofNullable(rospis);
        return this;
    }

    public CovidOrderBuilder setOrderDate(String dt) {
        this.data = dt;
        return this;
    }

    public CovidOrderBuilder setDepart(Integer d) {
        this.depart = d;
        return this;
    }

    public CovidOrderBuilder setServ(Integer idanal, String analiz, String result, Integer analtp, String dt_zabor, String dt_zanos) {
        this.analiz = analiz;
        this.id_anal = idanal;
        this.datazabora = dt_zabor;
        this.datazanosa = dt_zanos;
        this.result = result;
        this.analtp = analtp;
        return this;
    }

    public CovidOrderBuilder setMarker(String aInt) {
        this.marker = Optional.ofNullable(aInt);
        return this;
    }

    void setCheck(String chk) {
        check = chk;
    }

}
