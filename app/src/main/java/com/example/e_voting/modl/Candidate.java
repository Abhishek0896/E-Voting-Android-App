package com.example.e_voting.modl;

import com.example.e_voting.modl.StatusInfo;
import com.google.gson.annotations.SerializedName;

public class Candidate extends StatusInfo {

    @SerializedName("PartyName")
    private String PartyName;
    @SerializedName("CandidateName")
    private String CandidateName;
    @SerializedName("TotalVotes")
    private int totalCount;
    private int id;
    private String Url ;
    boolean isExpandable=false;

//    public Candidate(String partyName, String candidateName, int totalCount, int id) {
//        PartyName = partyName;
//        CandidateName = candidateName;
//        this.totalCount = totalCount;
//        this.id = id;
//        this.isExpandable = false;
//    }


    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public String getPartyName() {
        return PartyName;
    }

    public void setPartyName(String partyName) {
        PartyName = partyName;
    }

    public String getCandidateName() {
        return CandidateName;
    }

    public void setCandidateName(String candidateName) {
        CandidateName = candidateName;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
