package com.testing.investment.entity;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

//TODO Single entity class with required fields, validation and Lombok anotations

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Reward {

    @NonNull
    @Min(value = 1, message = "Invest period should be 1 month or greater")
    private int numberOfMonths;

    @FutureOrPresent(message = "Can not be past date")
    @NonNull
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate startDate;

    @NonNull
    @DecimalMin(value = "0.001", message = "Invest amount should be 0.001 ETH or greater")
    private double investedAmount;

    @NonNull
    private double rewardAmount;

    @NonNull
    @DecimalMin(value = "0.01", message = "Yearly staking reward should be 0.01% or greater")
    private double interestRate;

    @NonNull
    private double totalRewardAmount;
    
    private String reinvestStakingReward;


}
