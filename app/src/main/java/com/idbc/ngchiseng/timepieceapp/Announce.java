package com.idbc.ngchiseng.timepieceapp;

import android.content.res.Resources;

/**
 * Created by ChiSeng Ng Yu on 22/6/2017.
 * Class that represent the announce objects, that will use in the adapter to generate the elements
 * of the announce' list in the fragment screen corresponding.
 */

public class Announce {

    /*  This will declare the global and basic variables that will use like Announce objects attribute.
    Note: In this case, i make each variable like public to put aside the get's method for now, because
    it is more efficient, but maybe less secure. The other form is make each variable like private
    and implements each get's method corresponding.
     */
    private int idImage;
    private String title, name, address, currency, price, unit, descriptionOrQuantity, quantity, type, firstDate, secondDate;

    /* Complete object with 12 params, in this case used in the PurchaseDone and SalesDone fragments */
    public Announce(int idImage, String title, String name, String address, String currency, String price, String unit, String descriptionOrQuantity, String quantity, String type, String firstDate, String secondDate){
        this.idImage = idImage;
        this.title = title;
        this.name = name;
        this.address = address;
        this.currency = currency;
        this.price = price;
        this.unit = unit;
        this.descriptionOrQuantity = descriptionOrQuantity;
        this.quantity = quantity;
        this.type = type;
        this.firstDate = firstDate;
        this.secondDate = secondDate;
    }

    /* SemiComplete object with 11 params, in this case used in the PurchaseInProcess and SalesInProcess fragments */
    public Announce(int idImage, String title, String name, String address, String currency, String price, String unit, String description, String quantity, String type, String firstDate){
        this.idImage = idImage;
        this.title = title;
        this.name = name;
        this.address = address;
        this.currency = currency;
        this.price = price;
        this.unit = unit;
        this.descriptionOrQuantity = description;
        this.quantity = quantity;
        this.type = type;
        this.firstDate = firstDate;
    }

    /* SemiComplete object with 9 params, in this case used in the PublicationProducts and PublicationsServices fragments, or in Main Donation */
    public Announce(int idImage, String title, String address, String currency, String price, String unit, String description, String firstDate, String secondDate){
        this.idImage = idImage;
        this.title = title;
        this.address = address;
        this.currency = currency;
        this.price = price;
        this.unit = unit;
        this.descriptionOrQuantity = description;
        this.firstDate = firstDate;
        this.secondDate = secondDate;
    }

    /* SemiComplete object with 8 params, in this case used in the publication donations fragment, main Products and Services published in the app. */
    public Announce(int idImage, String title, String name, String address, String currency, String price, String unit, String description){
        this.idImage = idImage;
        this.title = title;
        this.name = name;
        this.address = address;
        this.currency = currency;
        this.price = price;
        this.unit = unit;
        this.descriptionOrQuantity = description;
    }

    /* SemiComplete object with 7 params, in this case used in the BagShopping of the Products and Services collected by the user to pay. */
    public Announce(int idImage, String title, String name, String address, String currency, String price, String unit){
        this.idImage = idImage;
        this.title = title;
        this.name = name;
        this.address = address;
        this.currency = currency;
        this.price = price;
        this.unit = unit;
    }

    /*  This are the get's method used for return the values of each attribute of the class

     */
    public int getImage(){ return idImage; }

    public String getTitle() { return title; }

    public String getName() { return name; }

    public String getAddress() { return address; }

    public String getCurrency() { return currency; }

    public String getPoint() { return ("+" + address); }

    public String getPrice() { return price; }

    public int getPriceInt() { return Integer.parseInt(price); }

    public String getUnit() { return unit; }

    public String getDescription() { return descriptionOrQuantity; }

    public String getQuantity() { return quantity; }

    public String getType() { return type; }

    public String getFirstDate() { return firstDate; }

    public String getSecondDate() { return secondDate; }

    public int getOrQuantity() { return Integer.parseInt(descriptionOrQuantity); }

    public String getPriceComplete() { return (currency + price + "/" + unit); }

    public String getCurrencyPrice() { return (currency + price); }

    public String getCurrencyUnit() { return (currency + unit); }

    public String getPriceByQuantity() { return Integer.toString(Integer.parseInt(price) * Integer.parseInt(quantity)); }

    public String getCollectedDonation() { return (currency + price + " " + unit);}

    public String getRequiredDonation() { return (currency + firstDate + " " + secondDate);}

    public void setSumOne() { this.descriptionOrQuantity = Integer.toString(1 + Integer.parseInt(descriptionOrQuantity)); }

    public void setSubtractOne() { this.descriptionOrQuantity = Integer.toString(Integer.parseInt(descriptionOrQuantity) - 1); }

}
