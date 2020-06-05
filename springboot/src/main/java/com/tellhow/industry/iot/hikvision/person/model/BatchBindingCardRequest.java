package com.tellhow.industry.iot.hikvision.person.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class BatchBindingCardRequest {
    public String startDate;
    public String endDate = "2037-12-31";
    public List<Card> cardList;

    public BatchBindingCardRequest(List<Person> personList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.startDate = sdf.format(new Date());
        cardList = personList.stream().map(Card::new).collect(toList());
    }
}
