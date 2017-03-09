package com.safecharge.retail.test;

import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.safecharge.retail.biz.SafechargeConfiguration;
import com.safecharge.retail.biz.SafechargeHttpClient;
import com.safecharge.retail.model.Item;
import com.safecharge.retail.model.MerchantInfo;
import com.safecharge.retail.request.GetOrderDetailsRequest;
import com.safecharge.retail.request.GetSessionTokenRequest;
import com.safecharge.retail.request.OpenOrderRequest;
import com.safecharge.retail.request.PaymentAPMRequest;
import com.safecharge.retail.request.PaymentCCRequest;
import com.safecharge.retail.request.SafechargeRequest;
import com.safecharge.retail.request.UpdateOrderRequest;
import com.safecharge.retail.util.Constants;

/**
 * Copyright (C) 2007-2017 SafeCharge International Group Limited.
 *
 * @author <a mailto:nikolad@safecharge.com>Nikola Dichev</a>
 * @since 2/24/2017
 */
public class ValidationsTest {

    private static final MerchantInfo validMerchantInfo = new MerchantInfo("dummy", "1234", "1234", Constants.HashAlgorithm.MD5);
    private static final MerchantInfo invalidMerchantInfo = new MerchantInfo("dummy", null, null, Constants.HashAlgorithm.MD5);
    private static final String dummySessionToken = "dummySessionToken";
    private static final Item dummyValidItem = new Item();
    private static final Item dummyInvalidItem = new Item();
    private static final String dummyOrderId = "1234";

    @Before public void initialization() {
        dummyValidItem.setQuantity("1");
        dummyValidItem.setName("dummyValidItem");
        dummyValidItem.setPrice("1");
        dummyInvalidItem.setName("dummyInvalidItem");
    }

    @Test public void testSuccessfulValidation_GetSessionToken() {
        SafechargeConfiguration.init(validMerchantInfo, "http://dummy:1234/ppp/", SafechargeHttpClient.createDefault());

        SafechargeRequest safechargeRequest = new GetSessionTokenRequest.Builder().build();
        Assert.assertTrue(safechargeRequest != null);
    }

    @Test public void testFailedValidation_GetSessionToken() {

        SafechargeConfiguration.init(invalidMerchantInfo, "http://dummy:1234/ppp/", SafechargeHttpClient.createDefault());

        try {
            new GetSessionTokenRequest.Builder().build();
            Assert.fail("ConstraintViolationException expected, object creation passed successfully.");
        } catch (ConstraintViolationException e) {
            Assert.assertEquals(2, e.getConstraintViolations()
                                    .size());
        }
    }

    @Test public void testSuccessfulValidation_GetOrderDetails() {
        SafechargeConfiguration.init(validMerchantInfo, "http://dummy:1234/ppp/", SafechargeHttpClient.createDefault());
        SafechargeRequest safechargeRequest = new GetOrderDetailsRequest.Builder().setOrderId("1234")
                                                                                  .addSessionToken(dummySessionToken)
                                                                                  .build();
        Assert.assertTrue(safechargeRequest != null);
    }

    @Test public void testFailedValidation_GetOrderDetails() {

        SafechargeConfiguration.init(validMerchantInfo, "http://dummy:1234/ppp/", SafechargeHttpClient.createDefault());

        try {
            new GetOrderDetailsRequest.Builder().build();
            Assert.fail("ConstraintViolationException expected, object creation passed successfully.");
        } catch (ConstraintViolationException e) {
            Assert.assertEquals(2, e.getConstraintViolations()
                                    .size());
        }
    }

    @Test public void testSuccessfulValidation_OpenOrder() {
        SafechargeConfiguration.init(validMerchantInfo, "http://dummy:1234/ppp/", SafechargeHttpClient.createDefault());
        SafechargeRequest safechargeRequest = new OpenOrderRequest.Builder().addSessionToken(dummySessionToken)
                                                                            .addCurrency("EUR")
                                                                            .addAmount("1")
                                                                            .addItem(dummyValidItem)
                                                                            .build();
        Assert.assertTrue(safechargeRequest != null);
    }

    @Test public void testFailedValidation_OpenOrder() {

        SafechargeConfiguration.init(validMerchantInfo, "http://dummy:1234/ppp/", SafechargeHttpClient.createDefault());

        try {
            SafechargeRequest safechargeRequest = new OpenOrderRequest.Builder().addSessionToken(dummySessionToken)
                                                                                .addAmount("1")
                                                                                .addItem(dummyInvalidItem)
                                                                                .build();
            Assert.fail("ConstraintViolationException expected, object creation passed successfully.");
        } catch (ConstraintViolationException e) {
            Assert.assertEquals(3, e.getConstraintViolations()
                                    .size());
        }
    }

    @Test public void testSuccessfulValidation_UpdateOrder() {
        SafechargeConfiguration.init(validMerchantInfo, "http://dummy:1234/ppp/", SafechargeHttpClient.createDefault());
        SafechargeRequest safechargeRequest = new UpdateOrderRequest.Builder().addSessionToken(dummySessionToken)
                                                                              .addOrderId(dummyOrderId)
                                                                              .addCurrency("EUR")
                                                                              .addAmount("1")
                                                                              .addItem(dummyValidItem)
                                                                              .build();
        Assert.assertTrue(safechargeRequest != null);
    }

    @Test public void testFailedValidation_UpdateOrder() {

        SafechargeConfiguration.init(validMerchantInfo, "http://dummy:1234/ppp/", SafechargeHttpClient.createDefault());

        try {
            SafechargeRequest safechargeRequest = new UpdateOrderRequest.Builder().addSessionToken(dummySessionToken)
                                                                                  .addOrderId(dummyOrderId)
                                                                                  .addAmount("1")
                                                                                  .addItem(dummyInvalidItem)
                                                                                  .build();
            Assert.fail("ConstraintViolationException expected, object creation passed successfully.");
        } catch (ConstraintViolationException e) {
            Assert.assertEquals(3, e.getConstraintViolations()
                                    .size());
        }
    }

    @Test public void testSuccessfulValidation_PaymentAPM() {
        SafechargeConfiguration.init(validMerchantInfo, "http://dummy:1234/ppp/", SafechargeHttpClient.createDefault());
        SafechargeRequest safechargeRequest = new PaymentAPMRequest.Builder().addCurrency("EUR")
                                                                             .addAmount("2")
                                                                             .addSessionToken(dummySessionToken)
                                                                             .addItem("test_item_1", "1", "1")
                                                                             .addItem("test_item_2", "1", "1")
                                                                             .addUserDetails("Test street 1", "Sofia", "BG", "test@test.com", "Test",
                                                                                     "Testov", "0884123456", null, "1000")
                                                                             .addBillingDetails("Test", "Testov", "test@test.com", "0884123456",
                                                                                     "Test street 1", "Sofia", "BG", null, "1000", "0884123456")
                                                                             .addShippingDetails("Test", "Testov", "test@test.com", "0884123456",
                                                                                     "Test street 1", "Sofia", "BG", null, "1000", "0884123456")
                                                                             .addOrderId(dummyOrderId)
                                                                             .addURLDetails(
                                                                                     "https://apmtest.gate2shop.com/nikolappp/cashier/cancel.do",
                                                                                     "https://apmtest.gate2shop.com/nikolappp/defaultPending.do",
                                                                                     "https://apmtest.gate2shop.com/nikolappp/defaultSuccess.do")
                                                                             .addPaymentMethod("apmgw_expresscheckout")
                                                                             .build();
        Assert.assertTrue(safechargeRequest != null);
    }

    @Test public void testFailedValidation_PaymentAPM() {

        SafechargeConfiguration.init(invalidMerchantInfo, "http://dummy:1234/ppp/", SafechargeHttpClient.createDefault());

        try {
            SafechargeRequest safechargeRequest = new PaymentAPMRequest.Builder().addSessionToken(dummySessionToken)
                                                                                 .addItem(null, "1", "1")
                                                                                 .addUserDetails("Test street 1 ", "Sofia", "BG", "test@test.com",
                                                                                         "Test street 1 Test street 1 Test street 1 Test street 1 ",
                                                                                         "Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 ",
                                                                                         "0884123456", null, "1000")
                                                                                 .addBillingDetails("Test", "Testov", "test@test.com", "0884123456",
                                                                                         "Test street 1", "Sofia", "BG", null, "1000", "0884123456")
                                                                                 .addShippingDetails("Test", "Testov", "test@test.com", "0884123456",
                                                                                         "Test street 1", "Sofia", "BG", null, "1000", "0884123456")
                                                                                 .addOrderId(dummyOrderId)
                                                                                 .addURLDetails(
                                                                                         "https://apmtest.gate2shop.com/nikolappp/cashier/cancel.do",
                                                                                         "https://apmtest.gate2shop.com/nikolappp/defaultPending.do",
                                                                                         "https://apmtest.gate2shop.com/nikolappp/defaultSuccess.do")
                                                                                 .addPaymentMethod("apmgw_expresscheckout")
                                                                                 .build();
            Assert.fail("ConstraintViolationException expected, object creation passed successfully.");
        } catch (ConstraintViolationException e) {
            Assert.assertEquals(7, e.getConstraintViolations()
                                    .size());
        }
    }

    @Test public void testSuccessfulValidation_PaymentCCRequest() {
        SafechargeConfiguration.init(validMerchantInfo, "http://dummy:1234/ppp/", SafechargeHttpClient.createDefault());
        SafechargeRequest safechargeRequest = new PaymentCCRequest.Builder().addCurrency("EUR")
                                                                            .addAmount("2")
                                                                            .addSessionToken(dummySessionToken)
                                                                            .addItem("test_item_1", "1", "1")
                                                                            .addItem("test_item_2", "1", "1")
                                                                            .addUserDetails("Test street 1", "Sofia", "BG", "test@test.com", "Test",
                                                                                    "Testov", "0884123456", null, "1000")
                                                                            .addBillingDetails("Test", "Testov", "test@test.com", "0884123456",
                                                                                    "Test street 1", "Sofia", "BG", null, "1000", "0884123456")
                                                                            .addShippingDetails("Test", "Testov", "test@test.com", "0884123456",
                                                                                    "Test street 1", "Sofia", "BG", null, "1000", "0884123456")
                                                                            .addOrderId(dummyOrderId)
                                                                            .addTransactionType(Constants.TransactionType.Sale)
                                                                            .addCardData("4111111111111111", "Test Test", "11", "2011", null, "123")
                                                                            .build();
        Assert.assertTrue(safechargeRequest != null);
    }

    @Test public void testFailedValidation_PaymentCCRequest() {

        SafechargeConfiguration.init(invalidMerchantInfo, "http://dummy:1234/ppp/", SafechargeHttpClient.createDefault());

        try {
            SafechargeRequest safechargeRequest = new PaymentCCRequest.Builder().addSessionToken(dummySessionToken)
                                                                                .addItem(null, "1", "1")
                                                                                .addUserDetails("Test street 1 ", "Sofia", "BG", "test@test.com",
                                                                                        "Test street 1 Test street 1 Test street 1 Test street 1 ",
                                                                                        "Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 Test street 1 ",
                                                                                        "0884123456", null, "1000")
                                                                                .addBillingDetails("Test", "Testov", "test@test.com", "0884123456",
                                                                                        "Test street 1", "Sofia", "BG", null, "1000", "0884123456")
                                                                                .addShippingDetails("Test", "Testov", "test@test.com", "0884123456",
                                                                                        "Test street 1", "Sofia", "BG", null, "1000", "0884123456")
                                                                                .addOrderId(dummyOrderId)
                                                                                .addCardData(null, null, "11", "2011", null, "123")
                                                                                .addUserPaymentOption(null, "12")
                                                                                .build();
            Assert.fail("ConstraintViolationException expected, object creation passed successfully.");
        } catch (ConstraintViolationException e) {
            Assert.assertEquals(9, e.getConstraintViolations()
                                    .size());
        }
    }
}