package com.customer.rewardPoints.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardsSummary {
    String orderMonth;
    BigDecimal totalMonthlyAmount;
    BigDecimal totalMonthlyRewardPoints;
}
