package com.citi.training.spring.rmi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.time.LocalTime;

/**
 * Created by sxj on 2017/3/1.
 */
public class RemoteMain {
    public static void main(String[] args) throws RemoteException {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("rmi.xml");
        RemoteEmailerClient rmiClient = (RemoteEmailerClient)ctx.getBean("rmiClient");

        // register shutdown hook to ensure container shuts down singletons gracefully
        ((AbstractApplicationContext) ctx).registerShutdownHook();

        String message="message:"+ LocalTime.now();
        rmiClient.run(message);

        System.out.println("send message: [" + message + "]");
    }
}
