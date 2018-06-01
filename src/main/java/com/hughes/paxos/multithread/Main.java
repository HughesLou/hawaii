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

    private static final int NUM_OF_PROPOSER = 7;
    public static final CountDownLatch START_LATCH = new CountDownLatch(NUM_OF_PROPOSER);
    public static final CountDownLatch STOP_LATCH = new CountDownLatch(NUM_OF_PROPOSER);
    private static final int NUM_OF_ACCEPTOR = 9;

    public static void main(String[] args) {
        try {
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
                Proposer proposer = new Proposer(i, "P." + i, proposerNum, acceptors);
                executorService.submit(proposer);
            }
            Main.STOP_LATCH.await();
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}