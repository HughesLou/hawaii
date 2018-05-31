package com.hughes.zmq;

import java.util.concurrent.TimeUnit;

import org.zeromq.ZMQ;

/**
 * Created by 1466811 on 9/16/2015.
 */
public class TaskWork {

    public static void main(String[] args) throws Exception {
        ZMQ.Context context = ZMQ.context(1);

        //  Socket to receive messages on
        ZMQ.Socket receiver = context.socket(ZMQ.PULL);
        receiver.connect("tcp://localhost:5557");

        //  Socket to send messages to
        ZMQ.Socket sender = context.socket(ZMQ.PUSH);
        sender.connect("tcp://localhost:5558");

        //  Process tasks forever
        while (!Thread.currentThread().isInterrupted()) {
            String value = new String(receiver.recv(0)).trim();
            //  Simple progress indicator for the viewer
            System.out.flush();
            System.out.print(value + '.');

            //  Do the work
            TimeUnit.MILLISECONDS.sleep(Long.parseLong(value));

            //  Send results to sink
            sender.send("".getBytes(), 0);
        }
        sender.close();
        receiver.close();
        context.term();
    }
}
