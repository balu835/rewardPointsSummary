package com.customer.rewardPoints.controller;

import com.customer.rewardPoints.RewardPointsApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RewardPointsApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardPointsControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testFetchRewardWithValidCustomerIdReturnRewardPoints() throws Exception {
        ResponseEntity<?> responseEntity = this.testRestTemplate
                .getForEntity("http://localhost:" + port + "/fetchRewardPoints/1001", String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
    @Test
    public void testFetchRewardWithInValidCustomerIdReturnBadRequest() throws Exception {
        ResponseEntity<?> responseEntity = this.testRestTemplate
                .getForEntity("http://localhost:" + port + "/fetchRewardPoints/-1", String.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }
    @Test
    public void testFetchRewardWithCustomerIdDoesNotExistsReturnsBadRequest() throws Exception {
        ResponseEntity<?> responseEntity = this.testRestTemplate
                .getForEntity("http://localhost:" + port + "/fetchRewardPoints/1009", String.class);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }
    @Test
    public void testFetchRewardWithValidCustomerIdNoRecordsReturnsSuccess() throws Exception {
        ResponseEntity<?> responseEntity = this.testRestTemplate
                .getForEntity("http://localhost:" + port + "/fetchRewardPoints/1005", String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testFetchRewardWithInValidUrlReturns404Error() throws Exception {
        ResponseEntity<?> responseEntity = this.testRestTemplate
                .getForEntity("http://localhost:" + port + "/fetchRewardPoint", String.class);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }


}
