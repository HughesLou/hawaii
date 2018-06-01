package com.hughes.paxos.multithread;

/**
 * 决策者记录已处理提案的状态
 * 
 * @author hugheslou
 * Created on 2018/5/31.
 */
public enum AcceptorStatus {
    ACCEPTED, // 接受
    PROMISED, // 承诺
    NONE, // 未处理过任何提案
    ;
}
