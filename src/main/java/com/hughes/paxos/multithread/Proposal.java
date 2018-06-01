package com.hughes.paxos.multithread;

import java.util.Objects;

/**
 * @author hugheslou
 * Created on 2018/5/31.
 */
public class Proposal {

    // 提案的序列号
    private int id;
    // 提案的值
    private String value;

    public Proposal() {
    }

    public Proposal(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proposal)) return false;
        Proposal proposal = (Proposal) o;
        return id == proposal.getId() && Objects.equals(value, proposal.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public String toString() {
        return "Proposal{" + "id=" + id + ", value='" + value + '\'' + '}';
    }
}