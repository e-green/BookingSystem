package org.egreen.opensms.server.controller;

import java.util.Date;
import java.sql.Timestamp;

/**
 * Created by pramoda-nf on 1/22/16.
 */
public class Main {

    public static void main(String[] args) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        System.out.println(timestamp.getTime());
    }
}
