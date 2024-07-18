package model;

public class Hero extends DungeonCharacter {

    private final int myBlockChance;
    enum SpecialAction{
        ATTACK,
        HEAL,

    }
    Hero(){
        myBlockChance = 0;
    }


    public int specialSkill() {
        return 0;
    }
    public int heal() {
        return 0;
    }
    public int blockChance(){
        return 0;
    }
}
