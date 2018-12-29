package com.tts.starsky.apperceive.controller.send;

import org.junit.Test;

import static org.junit.Assert.*;

public class SendBaseTest {

    @Test
    public void init() {
        SendBase.init();
        String hostAddress = SendBase.getHostAddress();
        System.out.println(hostAddress);
        SendBase sendBase = SendBase.getSendBase();
        sendBase.sendMessage();
    }
}