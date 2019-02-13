package com.company.Models;

import murilo.libs.relational.ObjectRelational;

public class Client extends ObjectRelational {

    private static final long serialVersionUID = 1L;

    public Client() {
    }

    public Client(String cpf, String name, String adress, String phone, String cellphone, String email) {
        this.cpf = cpf;
        this.name = name;
        this.adress = adress;
        this.phone = phone;
        this.cellphone = cellphone;
        this.email = email;
    }

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setCpf(Integer cpf) {
        this.cpf = cpf.toString();
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String adress;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String cellphone;

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}