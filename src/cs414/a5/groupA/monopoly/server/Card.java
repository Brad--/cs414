package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.shared.Token;

/**
 * Created by Garrett on 11/15/2016.
 */
public class Card extends Space {
    String discription;
    int type;
    int amount;
    public Card(int position){
        super(position);
        discription = "";
        type =0;
        amount =0;
    }

    public void setDiscription(String dis){ discription = dis;}

    public void setType(int t) { type = t;}

    public void setAmount(int am) { amount = am;}

    public String getDiscription(){return discription;}

    public int getType() {return type;}

    public int getAmount(){return amount;}
}
