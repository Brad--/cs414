package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.shared.Token;

/**
 * Created by Garrett on 11/15/2016.
 */
public class Card extends Space {
    String description;
    int type;
    int amount;
    public Card(int position){
        super(position);
        description = "";
        type =0;
        amount =0;
    }

    public void setDescription(String dis){ description = dis;}

    public void setType(int t) { type = t;}

    public void setAmount(int am) { amount = am;}

    public String getDescription(){return description;}

    public int getType() {return type;}

    public int getAmount(){return amount;}
}
