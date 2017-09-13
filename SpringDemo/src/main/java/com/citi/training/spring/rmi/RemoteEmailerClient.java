package com.citi.training.spring.rmi;

import java.rmi.RemoteException;

/**
 * Created by sxj on 2017/3/1.
 */
public class RemoteEmailerClient {

    private RemoteEmailer emailer;

    public RemoteEmailer getEmailer() {
        return emailer;
    }

    public void setEmailer(RemoteEmailer emailer) {
        this.emailer = emailer;
    }

    public void run(String msg) throws RemoteException {
        this.emailer.sendRemoteMessage(msg);
    }

}
