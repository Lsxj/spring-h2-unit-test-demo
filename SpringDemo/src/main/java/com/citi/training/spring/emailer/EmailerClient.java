package com.citi.training.spring.emailer;

import java.rmi.RemoteException;

/**
 * Created by sxj on 2017/3/1.
 */
public class EmailerClient {

    private Emailer emailer;


    public Emailer getEmailer() {
        return emailer;
    }

    public void setEmailer(Emailer emailer) {
        this.emailer = emailer;
    }

    public void run(String msg) throws RemoteException {
        this.emailer.sendMessage(msg);
    }

    public static void main(String[] args){

    }

}
