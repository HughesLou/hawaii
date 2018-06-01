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
    // 记录已处理提案的状态
    private AcceptorStatus status = AcceptorStatus.NONE;
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
            return null;
        }

        PrepareResult prepareResult = new PrepareResult();

        // TODO: 感觉这样比较复杂，继续看后续代码，以确定是否需要这样设计
        switch (status) {
            // NONE表示之前没有承诺过任何提议者；此时，接受提案
            case NONE:
                prepareResult.setAcceptorStatus(AcceptorStatus.NONE);
                prepareResult.setPromised(true);
                prepareResult.setProposal(null);
                // 转换自身的状态，已经承诺了提议者，并记录承诺的提案。
                status = AcceptorStatus.PROMISED;
                promisedProposal = proposal;
                return prepareResult;
            // 已经承诺过任意提议者
            case PROMISED:
                // 判断提案的先后顺序，只承诺相对较新的提案
                if (promisedProposal.getId() > proposal.getId()) {
                    prepareResult.setPromised(false);
                } else {
                    // 提议ID应该不会重复，除非节点重复收到同一个提议
                    if (promisedProposal.getId() == proposal.getId()) {
                        System.out.println("Promised id equals prepare one " + proposal.getId());
                    }
                    promisedProposal = proposal;
                    prepareResult.setPromised(true);
                }
                prepareResult.setAcceptorStatus(status);
                prepareResult.setProposal(promisedProposal);
                return prepareResult;
            // 已经批准过提案
            case ACCEPTED:
                if (promisedProposal.getId() == proposal.getId()) {
                    System.out.println("Accepted id equals prepare one " + proposal.getId());
                }
                if (promisedProposal.getId() > proposal.getId()) {
                    prepareResult.setPromised(false);
                    prepareResult.setProposal(acceptedProposal);
                } else {
                    promisedProposal = proposal;
                    prepareResult.setPromised(true);
                    prepareResult.setProposal(promisedProposal);
                }
                prepareResult.setAcceptorStatus(status);
                return prepareResult;
            default:
                return null;
        }
    }

    // 加锁此提交函数，不允许同时访问，模拟单个决策者串行决策
    public synchronized AcceptResult accept(Proposal proposal) {
        AcceptResult acceptResult = new AcceptResult();
        if (PaxosUtil.isCrashed()) {
            return null;
        }
        switch (status) {
            // 此状态表示之前没有收到过其他任何消息，当前的accept请求是第一次收到
            case NONE:
                promisedProposal = proposal;
                acceptedProposal = proposal;
                status = AcceptorStatus.ACCEPTED;
                acceptResult.setAccepted(true);
                acceptResult.setAcceptorStatus(status);
                acceptResult.setProposal(acceptedProposal);
                return acceptResult;
            // 已经承诺过提案
            case PROMISED:
                // 判断commit提案和承诺提案的序列号大小；大于等于，接受提案。
                if (proposal.getId() >= promisedProposal.getId()) {
                    promisedProposal = proposal;
                    acceptedProposal = proposal;
                    status = AcceptorStatus.ACCEPTED;
                    acceptResult.setAccepted(true);
                } else { // 小于，回绝提案
                    acceptResult.setAccepted(false);
                }
                acceptResult.setAcceptorStatus(status);
                acceptResult.setProposal(promisedProposal);
                return acceptResult;
            // 已接受过提案
            case ACCEPTED:
                if (acceptedProposal.getId() == proposal.getId()) {
                    System.out.println("Accepted id equals accept one " + proposal.getId());
                }
                if (proposal.getId() >= promisedProposal.getId()) {
                    promisedProposal = proposal;
                    acceptedProposal = proposal;
                    acceptResult.setAccepted(true);
                } else { // 否则，回绝提案
                    acceptResult.setAccepted(false);
                }
                acceptResult.setAcceptorStatus(status);
                acceptResult.setProposal(promisedProposal);
                return acceptResult;
            default:
        }

        return null;
    }

    @Override
    public String toString() {
        return "Acceptor{" + "id=" + id + ", name='" + name + '\'' + ", status=" + status
                + ", promisedProposal=" + promisedProposal + ", acceptedProposal="
                + acceptedProposal + '}';
    }
}
