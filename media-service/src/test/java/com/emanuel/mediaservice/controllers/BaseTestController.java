package com.emanuel.mediaservice.controllers;

import com.emanuel.mediaservice.proxies.NotificationServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class BaseTestController {
    protected final static String TITLE = "Test title";
    protected final static String DESCRIPTION = "Test description";
    @Autowired
    protected TestRestTemplate rest;
    @MockBean
    protected NotificationServiceProxy notificationServiceProxy;
}
