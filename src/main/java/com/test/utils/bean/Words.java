package com.test.utils.bean;

public class Words {

    private String word; //词的内容
    private String off ; //该词在未分词文本中的偏移位置
    private Double idf ; //该词的 IDF 值
    private String attr; //词性 (http://bbs.xunsearch.com/showthread.php?tid=1235)
    private Integer len; // 词的长度

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }

    public Double getIdf() {
        return idf;
    }

    public void setIdf(Double idf) {
        this.idf = idf;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    @Override
    public String toString() {
        return "Words{" +
                "word='" + word + '\'' +
                ", off='" + off + '\'' +
                ", idf=" + idf +
                ", attr='" + attr + '\'' +
                ", len=" + len +
                '}';
    }
}
