package com.customer.rewardPoints.web.view;

import java.math.BigDecimal;
import java.util.List;

import com.customer.rewardPoints.domain.RewardsSummary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardPointsView {

    private List<RewardsSummary> rewardsSummaries;
    private BigDecimal totalRewardPoints;

}
