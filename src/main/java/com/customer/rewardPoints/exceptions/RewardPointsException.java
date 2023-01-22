package com.customer.rewardPoints.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RewardPointsException extends RuntimeException{

    private String message;
}
