package com.hughes.paxos.multithread;

import static com.hughes.Utils.getIntValue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hugheslou
 * Created on 2018/5/31.
 */
public class Main {

    private static final int NUM_OF_PROPOSER = 5;
    public static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(NUM_OF_PROPOSER);
    private static final int NUM_OF_ACCEPTOR = 7;

    public static void main(String[] args) {
        int acceptorNum = NUM_OF_ACCEPTOR;
        int proposerNum = NUM_OF_PROPOSER;

        if (args.length == 2) {
            acceptorNum = getIntValue(args[0], NUM_OF_ACCEPTOR);
            proposerNum = getIntValue(args[1], NUM_OF_PROPOSER);
        }

        List<Acceptor> acceptors = new ArrayList<>();
        for (int i = 0; i < acceptorNum; i++) {
            acceptors.add(new Acceptor(i, "A." + i));
        }

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < proposerNum; i++) {
            Proposer proposer = new Proposer(i, "P." + 1, proposerNum, acceptors);
            executorService.submit(proposer);
        }
        executorService.shutdown();
    }
}