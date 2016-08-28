package com.tgimba.tgimba_android.data;

import java.util.Date;

public class BucketItemList
{
    public String Name = "";
    public Date Entered = null;
    public EnumRanking Ranking = null;
    public Boolean Achieved = false;
    public String DbId = "";

    public BucketItemList(String pName,
                          Date pEntered,
                          EnumRanking pRanking,
                          Boolean pAchieved,
                          String pDbId)
    {
        Name = pName;
        Entered = pEntered;
        Ranking = pRanking;
        Achieved = pAchieved;
        DbId = pDbId;
    }
}
