package com.hughes.paxos.multithread;

/**
 * 决策者针对提议者的准备提案的回复
 * 
 * @author hugheslou
 * Created on 2018/5/31.
 */
public class PrepareResult {

    // 是否承诺
    private boolean promised;
    // 决策者的状态
    private AcceptorStatus acceptorStatus = AcceptorStatus.NONE;
    // 决策者返回的提案
    private Proposal proposal;

    public boolean isPromised() {
        return promised;
    }

    public void setPromised(boolean promised) {
        this.promised = promised;
    }

    public AcceptorStatus getAcceptorStatus() {
        return acceptorStatus;
    }

    public void setAcceptorStatus(AcceptorStatus acceptorStatus) {
        this.acceptorStatus = acceptorStatus;
    }

    public Proposal getProposal() {
        return proposal;
    }

    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    @Override
    public String toString() {
        return "PrepareResult [promised=" + promised + ", acceptorStatus=" + acceptorStatus
                + ", proposal=" + proposal + "]";
    }
}
