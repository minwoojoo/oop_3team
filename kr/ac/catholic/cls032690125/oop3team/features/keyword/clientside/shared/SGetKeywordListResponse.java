package kr.ac.catholic.cls032690125.oop3team.features.keyword.clientside.shared;

import kr.ac.catholic.cls032690125.oop3team.models.Keyword;
import kr.ac.catholic.cls032690125.oop3team.shared.ServerResponseBasePacket;

import java.util.List;

public class SGetKeywordListResponse extends ServerResponseBasePacket {
    private List<Keyword> keywords;

    public SGetKeywordListResponse(long requets,List<Keyword> keywords) {
        super(requets);
        this.keywords = keywords;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }
}
