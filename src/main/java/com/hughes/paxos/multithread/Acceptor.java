package com.hughes.paxos.multithread;

/**
 * 决策者
 * @author hugheslou
 * Created on 2018/5/31.
 */
public class Acceptor {

    // 序列号
    private int id;
    // 决策者名字
    private String name;
    // 记录最新承诺的提案
    private Proposal promisedProposal = new Proposal();
    // 记录最新批准的提案
    private Proposal acceptedProposal = new Proposal();

    public Acceptor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 加锁此准备函数，不允许同时访问。模拟单个决策者串行处理一个请求。
    public synchronized PrepareResult prepare(Proposal proposal) {
        // 模拟网络不正常，发生丢包、超时现象
        if (PaxosUtil.isCrashed()) {
            System.out.println("[Down-P] " + this + proposal);
            return null;
        }

        PrepareResult prepareResult = new PrepareResult();

        if (promisedProposal.getId() > proposal.getId()) {
            prepareResult.setPromised(false);
            // 用来判断是否提前终止投票，或者设置新一轮投票的value
            prepareResult.setProposal(new Proposal(acceptedProposal));
            System.out.println("[Reject-P] " + this + proposal);
        } else {
            if (promisedProposal.getId() == proposal.getId()) {
                System.out.println("Promised id equals prepare one " + this);
            }
            prepareResult.setPromised(true);
            promisedProposal.copyFromInstance(proposal);
            System.out.println("[Promise] " + this);
        }

        return prepareResult;
    }

    // 加锁此提交函数，不允许同时访问，模拟单个决策者串行决策
    public synchronized AcceptResult accept(Proposal proposal) {
        if (PaxosUtil.isCrashed()) {
            System.out.println("[Down-A] " + this + proposal);
            return null;
        }

        AcceptResult acceptResult = new AcceptResult();

        if (promisedProposal.getId() > proposal.getId()) {
            acceptResult.setAccepted(false);
            acceptResult.setProposal(acceptedProposal);
            System.out.println("[Reject-A] " + this + proposal);
        } else {
            // 没经过prepare阶段也能accept
            acceptResult.setAccepted(true);
            promisedProposal.copyFromInstance(proposal);
            acceptedProposal.copyFromInstance(proposal);
            System.out.println("[Accept] " + this);
        }

        return acceptResult;
    }

    @Override
    public String toString() {
        return "Acceptor{" + "id=" + id + ", name='" + name + '\'' + ", promisedProposal="
                + promisedProposal + ", acceptedProposal=" + acceptedProposal + '}';
    }
}