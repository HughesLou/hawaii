package com.hughes.zmq;

import org.zeromq.ZMQ;

/**
 * Created by 1466811 on 9/16/2015.
 */
public class TaskSink {

    public static void main(String[] args) throws Exception {

        //  Prepare our context and socket
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);
        receiver.bind("tcp://*:5558");

        //  Wait for start of batch
        String string = new String(receiver.recv(0));

        //  Start our clock now
        long tstart = System.currentTimeMillis();

        //  Process 100 confirmations
        int taskNumber;
        for (taskNumber = 0; taskNumber < 100; taskNumber++) {
            string = new String(receiver.recv(0)).trim();
            if ((taskNumber / 10) * 10 == taskNumber) {
                System.out.print(":");
            } else {
                System.out.print(".");
            }
        }
        //  Calculate and report duration of batch
        long tend = System.currentTimeMillis();

        System.out.println("\nTotal elapsed time: " + (tend - tstart) + " msec");
        receiver.close();
        context.term();
    }
}
