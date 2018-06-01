package com.hughes.paxos;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * 实现简单的Basic Paxos，参考了 https://my.oschina.net/u/2541538/blog/807185
 * @author hugheslou
 * Created on 2018/5/31.
 */
public final class SimpleBasicPaxos {

    private static final HashFunction HASH_FUNCTION = Hashing.murmur3_32();
    private static final Random RANDOM = new Random();
    private static final String[] PROPOSALS = { "X", "Y", "Z" };
    private static final float FAILURE_PROBABILITY = 0.5f;

    public static void main(String[] args) {
        List<Acceptor> acceptors = new ArrayList<>();
        Arrays.asList("A.A", "A.B", "A.C", "A.D", "A.E")
                .forEach(name -> acceptors.add(new Acceptor(name)));
        Proposer.vote(new Proposal(1L, null), acceptors);
    }

    private static void printInfo(String subject, String operation, String result) {
        System.out.println(subject + " : " + operation + " <" + result + ">");
    }

    /**
     * 对于提案的约束，第三条约束要求：
     * 如果maxVote不存在，那么没有限制，下一次表决可以使用任意提案；
     * 否则，下一次表决要沿用maxVote的提案
     *
     * @param currentVoteNumber
     * @param proposals
     * @return
     */
    private static Proposal nextProposal(long currentVoteNumber, List<Proposal> proposals) {
        long voteNumber = currentVoteNumber + 1;
        if (proposals.isEmpty()) {
            return new Proposal(voteNumber, PROPOSALS[RANDOM.nextInt(PROPOSALS.length)]);
        }
        Collections.sort(proposals);
        Proposal maxProposal = proposals.get(proposals.size() - 1);
        long maxVoteNumber = maxProposal.getVoteNumber();
        String content = maxProposal.getContent();
        if (maxVoteNumber >= currentVoteNumber) {
            voteNumber = maxVoteNumber + 1;
            System.out.println("WARN: reset vote number as " + voteNumber);
        }
        if (content != null) {
            return new Proposal(voteNumber, content);
        } else {
            return new Proposal(voteNumber, PROPOSALS[RANDOM.nextInt(PROPOSALS.length)]);
        }
    }

    private static class Proposer {

        /**
         * @param proposal
         * @param acceptors
         */
        public static void vote(Proposal proposal, Collection<Acceptor> acceptors) {
            int majorNumber = Math.floorDiv(acceptors.size(), 2) + 1;
            int count = 0;
            while (true) {
                printInfo("V.R", "START", ++count + "@[" + proposal + "]");
                List<Proposal> proposals = new ArrayList<>();
                for (Acceptor acceptor : acceptors) {
                    Promise promise = acceptor.onPrepare(proposal);
                    if (promise != null && promise.isAck()) {
                        proposals.add(promise.getProposal());
                    }
                }
                if (proposals.size() < majorNumber) {
                    printInfo("[" + proposal + "]", "VOTE", "NOT PREPARED@" + proposals.size());
                    proposal = nextProposal(proposal.getVoteNumber(), proposals);
                    continue;
                }
                int acceptCount = 0;
                for (Acceptor acceptor : acceptors) {
                    if (acceptor.onAccept(proposal)) {
                        acceptCount++;
                    }
                }
                if (acceptCount < majorNumber) {
                    printInfo("[" + proposal + "]", "VOTE", "NOT ACCEPTED@" + acceptCount);
                    proposal = nextProposal(proposal.getVoteNumber(), proposals);
                    continue;
                }
                break;
            }
            printInfo("[" + proposal + "]", "VOTE", "SUCCESS");
        }
    }

    private static class Acceptor {

        // 上次表决结果
        private Proposal last = new Proposal();
        // prepared 或者 promised 的最大的表决ID
        private long maxVoteNumber = last.getVoteNumber();
        private String name;

        public Acceptor(String name) {
            this.name = name;
        }

        public Promise onPrepare(Proposal proposal) {
            // 假设这个过程有50%的几率失败
            if (Math.random() > FAILURE_PROBABILITY || proposal == null) {
                printInfo(name, "PREPARE", "NO-RESPONSE");
                return null;
            }
            if (proposal.getVoteNumber() > maxVoteNumber) {
                Promise response = new Promise(true, last);
                maxVoteNumber = proposal.getVoteNumber();
                printInfo(name, "PREPARE", "OK");
                return response;
            } else {
                printInfo(name, "PREPARE", "REJECTED");
                return new Promise(false, null);
            }
        }

        public boolean onAccept(Proposal proposal) {
            // 假设这个过程有50%的几率成功
            if (Math.random() > FAILURE_PROBABILITY && proposal != null
                    && proposal.getVoteNumber() >= maxVoteNumber) {
                printInfo(name, "ACCEPT", "OK");
                maxVoteNumber = proposal.getVoteNumber();
                last = proposal;
                return true;
            }
            printInfo(name, "ACCEPT", "NO-RESPONSE");
            return false;
        }
    }

    private static class Promise {

        private final boolean ack;
        private final Proposal proposal;

        public Promise(boolean ack, Proposal proposal) {
            this.ack = ack;
            this.proposal = proposal;
        }

        public boolean isAck() {
            return ack;
        }

        public Proposal getProposal() {
            return proposal;
        }
    }

    private static class Proposal implements Comparable<Proposal> {

        private final long voteNumber;
        private final String content;

        public Proposal(long voteNumber, String content) {
            this.voteNumber = voteNumber;
            this.content = content;
        }

        public Proposal() {
            this(0, null);
        }

        public long getVoteNumber() {
            return voteNumber;
        }

        public String getContent() {
            return content;
        }

        @Override
        public int compareTo(Proposal o) {
            return Long.compare(voteNumber, o.voteNumber);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Proposal proposal = (Proposal) o;
            return voteNumber == proposal.voteNumber && Objects.equals(content, proposal.content);
        }

        @Override
        public int hashCode() {
            return HASH_FUNCTION.newHasher().putLong(voteNumber)
                    .putString(content, StandardCharsets.UTF_8).hash().asInt();
        }

        @Override
        public String toString() {
            return new StringBuilder().append(voteNumber).append('_').append(content).toString();
        }
    }
}