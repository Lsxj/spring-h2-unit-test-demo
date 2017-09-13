package com.citi.training.spring.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by sxj on 2017/3/1.
 */
public interface RemoteEmailer extends Remote {
    void sendRemoteMessage(String msg) throws RemoteException;
}
