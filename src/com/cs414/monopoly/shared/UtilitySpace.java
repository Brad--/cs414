package com.cs414.monopoly.shared;

import static com.cs414.monopoly.shared.PropertyGroup.UTILITY;

/**
 * Created by bradley on 10/27/16.
 */
public class UtilitySpace extends Deed{
    public UtilitySpace(Board b, int position) {
        super(b, position);
    }

    @Override
    public void calcPriceAndPropertyGroup() {
        this.price = 150;// Times the roll
        this.propertyGroup = UTILITY;
    }

    @Override
    protected void calcRent() {
        this.houseRent = 10; // Times roll
    }
}
