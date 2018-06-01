package com.hughes.paxos.multithread;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * @author hugheslou
 * Created on 2018/5/31.
 */
public class Proposer implements Runnable {

    // 序列号
    private int id;
    // 提议者的名字
    private String name;
    // 提议者的提案
    private Proposal proposal;
    // 大多数决策者的最小个数
    private int majority;
    private int proposerNumber;
    private int numCycle;
    // 决策者们
    private List<Acceptor> acceptors;

    public Proposer(int id, String name, int proposerNumber, List<Acceptor> acceptors) {
        this.id = id;
        this.name = name;
        this.proposerNumber = proposerNumber;
        this.acceptors = acceptors;
        majority = acceptors.size() / 2 + 1;
        this.proposal = new Proposal(PaxosUtil.generateId(id, numCycle, proposerNumber),
                "XYZ-" + id);
        numCycle++;
    }

    /**
     * 准备提案过程，获得大多数决策者支持后进入确认提案阶段
     * @return true: 表示已经有提案通过 false: 表示prepare阶段获得了半数及以上的promise
     */
    public synchronized boolean prepare() {
        while (true) {
            List<Proposal> acceptedProposals = new ArrayList<>();
            // 已获得承诺个数
            int promisedCount = 0;
            for (Acceptor acceptor : acceptors) {

                PrepareResult prepareResult = acceptor.prepare(proposal);
                // 随机休眠一段时间，模拟网络延迟。
                PaxosUtil.sleepRandom();

                // 模拟网络异常
                if (null == prepareResult) {
                    continue;
                }

                // 获得承诺
                if (prepareResult.isPromised()) {
                    promisedCount++;
                } else {
                    // 决策者通过的提案
                    acceptedProposals.add(prepareResult.getProposal());
                }
            }

            // 获得多数决策者的承诺
            // 可以进行第二阶段：提案提交
            if (promisedCount >= majority) {
                return true;
            }
            Proposal votedProposal = getVotedProposal(acceptedProposals);
            // 决策者已经半数通过提案
            if (votedProposal != null) {
                // 小概率会出现，prepare阶段已经发现；后续的accept过程中因为网络原因超过半数节点无法accept
                // 导致重新生成提案再进行prepare
                // 所以当prepare返回false时，accept阶段将跳过
                proposal = votedProposal;
                System.out.println("======== " + name + " vote end with: " + votedProposal);
                return false;
            }
            prepareNewProposal(acceptedProposals);
        }
    }

    // 获得大多数决策者承诺后，开始进行提案确认
    public synchronized boolean accept() {
        while (true) {
            List<Proposal> acceptedProposals = new ArrayList<>();
            // 已获得接受该提案的决策者个数
            int acceptedCount = 0;
            for (Acceptor acceptor : acceptors) {

                AcceptResult acceptResult = acceptor.accept(proposal);
                // 模拟网络延迟
                PaxosUtil.sleepRandom();

                // 模拟网络异常
                if (null == acceptResult) {
                    continue;
                }

                // 提案被决策者接受。
                if (acceptResult.isAccepted()) {
                    acceptedCount++;
                } else {
                    acceptedProposals.add(acceptResult.getProposal());
                }
            }

            // 提案被半数以上决策者接受，说明提案已经被选出来。
            if (acceptedCount >= majority) {
                System.out.println("======== " + name + " vote accept with: " + proposal);
                return true;
            } else {
                prepareNewProposal(acceptedProposals);
                // 回退到决策准备阶段
                if (!prepare()) {
                    return true;
                }
            }
        }
    }

    private void prepareNewProposal(List<Proposal> proposalList) {
        Proposal maxIdAcceptedProposal = proposalList.stream().filter(Objects::nonNull)
                .filter(pro -> pro.getId() >= 0).max(Comparator.comparingInt(p -> p.getId()))
                .orElse(null);
        // 在已经被决策者通过提案中选择序列号最大的决策,重新生成递增id，改变自己的value为序列号最大的value。
        // 这是一种预测，预测此maxIdAcceptedProposal最有可能被超过半数的决策者接受。
        if (maxIdAcceptedProposal != null) {
            proposal.setValue(maxIdAcceptedProposal.getValue());
        }
        proposal.setId(PaxosUtil.generateId(id, numCycle, proposerNumber));
        System.out.println(name + " with new proposal: " + proposal);
        numCycle++;
    }

    // 是否已经有某个提案，被大多数决策者接受
    private Proposal getVotedProposal(List<Proposal> proposalList) {
        Map<Proposal, Integer> proposalCount = new HashMap<>();
        for (Proposal pro : proposalList) {
            if (null == pro || pro.getId() < 0) {
                continue;
            }
            int count = 1;
            if (proposalCount.containsKey(pro)) {
                count = proposalCount.get(pro) + 1;
            }
            proposalCount.put(pro, count);
        }

        for (Entry<Proposal, Integer> entry : proposalCount.entrySet()) {
            if (entry.getValue() >= majority) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void run() {
        try {
            // 用来控制Proposer同时启动
            Main.START_LATCH.countDown();
            Main.START_LATCH.await();
            if (prepare()) {
                accept();
            }
            Main.STOP_LATCH.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}