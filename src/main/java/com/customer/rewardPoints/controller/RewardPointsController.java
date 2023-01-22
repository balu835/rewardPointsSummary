package com.customer.rewardPoints.controller;

import com.customer.rewardPoints.entity.OrderEntity;
import com.customer.rewardPoints.exceptions.RewardPointsException;
import com.customer.rewardPoints.repository.OrderRepository;
import com.customer.rewardPoints.service.business.RewardPointsBusinessService;
import com.customer.rewardPoints.validator.CustomerValidator;
import com.customer.rewardPoints.web.view.RewardPointsView;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@Controller
@Slf4j
public class RewardPointsController {

    public static final String ERROR_PROCESSING_REQUEST_FOR_INVALID_DATA = "Error Processing request for Invalid Data.";
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private RewardPointsBusinessService rewardPointsBusinessService;

    @Autowired
    private CustomerValidator customerValidator;

    /**
     * Returns all the list of orders in the system.
     *
     * @param model
     * @return
     */
    @GetMapping("/orders")
    public String fetchAllOrders(Model model) {
        log.info("fetchAllOrders ::  fetching all orders");
        List<OrderEntity> orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        log.info("fetchAllOrders ::  completed fetching all orders");
        return "orders";
    }

    /**
     * Returns all orders for the given customerId.
     *
     * @param customerId
     * @param model
     * @return
     */
    @GetMapping("/orders/{customerId}")
    public String fetchOrdersForCustomer(@PathVariable("customerId") long customerId, Model model) {
        log.info("fetchOrdersForCustomer :: Begin fetching all orders for the given customerId {}", customerId);
        List<OrderEntity> orders = orderRepository.findByCustomerCustomerId(customerId);
        model.addAttribute("orders", orders);
        log.info("Total number of orders for the given customerId {} are {}", customerId, orders.size());
        return "orders";
    }

    /**
     * Builds rewards point summary view for the given customerId with sample ui display.
     *
     *
     * @param customerId
     * @param model
     * @param response
     * @return
     */
    @GetMapping("/rewardPointsView/{customerId}")
    public String fetchRewardPointsView(@PathVariable("customerId") long customerId, Model model, HttpServletResponse response) {
        log.info("fetchRewardPointsView:: Fetching reward points for customerId {} ", customerId);
        try {
            if(customerValidator.isValidCustomer(customerId)) {
                RewardPointsView rewardPointsView = rewardPointsBusinessService.getRewardPointsSummaryView(customerId);
                model.addAttribute("rewardPointsView", rewardPointsView);
                log.info("fetchRewardPoints:: Reward points for given customerId {} are {}", customerId, rewardPointsView.getTotalRewardPoints());
            }
        } catch (RewardPointsException exception) {
            response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
            model.addAttribute("message", exception.getMessage());
            log.error("fetchRewardPointsView:: Invalid request identified for customerId {}", customerId);
            return "error";
        }
        return "reward-points-view";
    }

    /**
     * Fetch rewards points for the given customerId
     * A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every
     * dollar spent over $50 in each transaction
     * (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
     *
     * @param customerId
     * @return
     */
    @GetMapping("/fetchRewardPoints/{customerId}")
    @ResponseBody
    public ResponseEntity<?> fetchRewardPoints(@PathVariable("customerId") long customerId) {
        log.info("fetchRewardPoints:: Fetching reward points for customerId {} ", customerId);
        try {
            if(customerValidator.isValidCustomer(customerId)) {
                RewardPointsView rewardPointsView = rewardPointsBusinessService.getRewardPointsSummaryView(customerId);
                log.info("fetchRewardPoints:: Reward points for given customerId {} are {}", customerId, rewardPointsView.getTotalRewardPoints());
                return new ResponseEntity<>(rewardPointsView, HttpStatus.OK);
            }
        } catch (RewardPointsException exception) {
            log.error("fetchRewardPoints:: Invalid request identified for customerId {}", customerId);
            return ResponseEntity.badRequest().body(exception.getMessage());
        } catch (Exception ex){
            log.error("fetchRewardPoints:: Error Processing the request");
            return ResponseEntity.internalServerError().body(ERROR_PROCESSING_REQUEST_FOR_INVALID_DATA);
        }
        return null;
    }

}
