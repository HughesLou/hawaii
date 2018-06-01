package com.hughes.paxos.multithread;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
                    // 决策者已经通过了一个题案
                    if (prepareResult.getAcceptorStatus() == AcceptorStatus.ACCEPTED) {
                        acceptedProposals.add(prepareResult.getProposal());
                    }
                }
            }

            // 获得多数决策者的承诺
            // 可以进行第二阶段：题案提交
            if (promisedCount >= majority) {
                return true;
            }
            Proposal votedProposal = getVotedProposal(acceptedProposals);
            // 决策者已经半数通过题案
            if (votedProposal != null) {
                System.out.println(id + " @vote end with: " + votedProposal);
                return true;
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

                // 题案被决策者接受。
                if (acceptResult.isAccepted()) {
                    acceptedCount++;
                } else {
                    acceptedProposals.add(acceptResult.getProposal());
                }
            }

            // 题案被半数以上决策者接受，说明题案已经被选出来。
            if (acceptedCount >= majority) {
                System.out.println(id + " @vote accept with: " + proposal);
                return true;
            } else {
                prepareNewProposal(acceptedProposals);
                // 回退到决策准备阶段
                if (prepare()) {
                    return true;
                }
            }
        }
    }

    private void prepareNewProposal(List<Proposal> proposalList) {
        Proposal maxIdAcceptedProposal = proposalList.stream()
                .max(Comparator.comparingInt(p -> p.getId())).orElse(null);
        // 在已经被决策者通过题案中选择序列号最大的决策,重新生成递增id，改变自己的value为序列号最大的value。
        // 这是一种预测，预测此maxIdAccecptedProposal最有可能被超过半数的决策者接受。
        if (maxIdAcceptedProposal != null) {
            proposal.setId(PaxosUtil.generateId(id, numCycle, proposerNumber));
            proposal.setValue(maxIdAcceptedProposal.getValue());
        } else {
            proposal.setId(PaxosUtil.generateId(id, numCycle, proposerNumber));
        }
        numCycle++;
    }

    // 是否已经有某个提案，被大多数决策者接受
    private Proposal getVotedProposal(List<Proposal> acceptedProposals) {
        Map<Proposal, Integer> proposalCount = countAcceptedProposalCount(acceptedProposals);
        for (Entry<Proposal, Integer> entry : proposalCount.entrySet()) {
            if (entry.getValue() >= majority) {
                return entry.getKey();
            }
        }
        return null;
    }

    // 计算决策者回复的每个已经被接受的提案计数
    private Map<Proposal, Integer> countAcceptedProposalCount(List<Proposal> acceptedProposals) {
        Map<Proposal, Integer> proposalCount = new HashMap<>();
        for (Proposal pro : acceptedProposals) {
            // 决策者没有回复，或者网络异常
            if (null == pro) {
                continue;
            }
            int count = 1;
            if (proposalCount.containsKey(pro)) {
                count = proposalCount.get(pro) + 1;
            }
            proposalCount.put(pro, count);
        }

        return proposalCount;
    }

    @Override
    public void run() {
        // 用来控制Proposer同时启动
        Main.COUNT_DOWN_LATCH.countDown();
        try {
            Main.COUNT_DOWN_LATCH.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long start = System.currentTimeMillis();
        prepare();
        accept();
        System.out.println(String.format("%s with %s cost %d ms", "P." + id, proposal,
                System.currentTimeMillis() - start));
    }
}