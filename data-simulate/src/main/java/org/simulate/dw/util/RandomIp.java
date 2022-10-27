package org.simulate.dw.util;

import java.util.Random;

/**
 *
 * @desc
 *
 */
public class RandomIp {

    private static final String[] ips = {"123.23.4.12", "12.23.54.12", "12.34.123.44", "192.168.10.1"};

    public static String getIp() {
        Random random = new Random();
        return ips[random.nextInt(ips.length)];
    }

}
