package com.nmt.sendgos;

class Order {

    private String number; // уникальный номер ex+id anal
    private Integer examid; 
    private Integer depart; // login
    private String laboratoryName; 
    private String laboratoryOgrn; 
    private String name; // short title  партнера
    private String ogrn;// 13  символов 
    private String orderData; // data examines
    private String biomaterDate; // data zabor  probirka
    private Integer serv_code; // id anal
    private String serv_name; // analize
    private String serv_result; //result 0 - отрицательный - 1 полжительные
    private Integer serv_type; // тип анализа
    private String readyDate; // data zanos  from results
    private Patient patient; 
    private String status;
    private String message;
    private String json;

    public Order() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getExamid() {
        return examid;
    }

    public void setExamid(Integer examid) {
        this.examid = examid;
    }

    public Integer getDepart() {
        return depart;
    }

    public void setDepart(Integer depart) {
        this.depart = depart;
    }

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }

    public String getLaboratoryOgrn() {
        return laboratoryOgrn;
    }

    public void setLaboratoryOgrn(String laboratoryOgrn) {
        this.laboratoryOgrn = laboratoryOgrn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getOrderData() {
        return orderData;
    }

    public void setOrderData(String orderData) {
        this.orderData = orderData;
    }

    public String getBiomaterDate() {
        return biomaterDate;
    }

    public void setBiomaterDate(String biomaterDate) {
        this.biomaterDate = biomaterDate;
    }

    public Integer getServ_code() {
        return serv_code;
    }

    public void setServ_code(Integer serv_code) {
        this.serv_code = serv_code;
    }

    public String getServ_name() {
        return serv_name;
    }

    public void setServ_name(String serv_name) {
        this.serv_name = serv_name;
    }

    public String getServ_result() {
        return serv_result;
    }

    public void setServ_result(String serv_result) {
        this.serv_result = serv_result;
    }

    public Integer getServ_type() {
        return serv_type;
    }

    public void setServ_type(Integer serv_type) {
        this.serv_type = serv_type;
    }

    public String getReadyDate() {
        return readyDate;
    }

    public void setReadyDate(String readyDate) {
        this.readyDate = readyDate;
    }

  

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
      

    public  class Patient {

        protected String name; 
        protected String surname;
        protected String patronymic;
        protected String gender; // 1 муж 2 ж
        protected String birthday; 
        protected String phone;
        protected String email;
        protected String snils;
        protected String oms;
        protected String doc_type;
        protected String doc_number;
        protected String doc_ser;
        protected String region;
        protected String town;
        protected String district;
        protected String streetName;
        protected String house;
        protected String appartament;

        public Patient() {

        }
    }

}
