package cs414.a5.groupA.monopoly.server;

import static cs414.a5.groupA.monopoly.server.PropertyGroup.UTILITY;

/**
 * Created by bradley on 10/27/16.
 */
public class UtilitySpace extends Deed{
    public UtilitySpace(int position) {
        super(position);
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
