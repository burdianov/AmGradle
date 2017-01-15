package com.testography.amgradle.data.storage.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserAddressRealm extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String street;
    private String house;
    private String apartment;
    private int floor;
    private String comment;
    private boolean favorite;

    public UserAddressRealm() {
    }

    public UserAddressRealm(int id, String name, String street,
                            String house, String apartment, int floor,
                            String comment, boolean favorite) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
        this.floor = floor;
        this.comment = comment;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public String getApartment() {
        return apartment;
    }

    public int getFloor() {
        return floor;
    }

    public String getComment() {
        return comment;
    }

    public boolean getFavorite() {
        return favorite;
    }
}
