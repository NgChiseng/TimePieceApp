package com.idbc.ngchiseng.timepieceapp;

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
    private String announceTitle, announceOwner, announceAddress, announcePrice;

    public Announce(int idImage, String announceTitle, String announceOwner, String announceAddress, String announcePrice){
        this.idImage = idImage;
        this.announceTitle = announceTitle;
        this.announceOwner = announceOwner;
        this.announceAddress = announceAddress;
        this.announcePrice = announcePrice;
    }

    /*  This are the get's method used for return the values of each attribute of the class

     */
    public int getImage(){ return idImage; }

    public String getAnnounceTitle() { return announceTitle; }

    public String getAnnounceOwner() { return announceOwner; }

    public String getAnnounceAddress() { return announceAddress; }

    public String getAnnouncePrice() { return announcePrice; }

}
