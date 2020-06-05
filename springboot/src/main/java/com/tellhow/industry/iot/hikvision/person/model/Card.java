package com.tellhow.industry.iot.hikvision.person.model;

public class Card {
    public String cardNo;
    public String personId;
    public int cardType = 1;

    public Card(Person person) {
        this.personId = person.personId;
        if (person.phoneNo != null) {
            this.cardNo = person.phoneNo;
        } else if (person.certificateNo != null) {
            this.cardNo = person.certificateNo;
        }
    }
}
