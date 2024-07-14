package com.monou.domain.activity.service;

import com.monou.domain.activity.respository.IActivityRepository;
import org.springframework.stereotype.Service;

/**
 * @author Serendipity
 * @description
 * @date 2024-07-15 00:59
 **/
@Service
public class RaffleActivityService extends AbstractRaffleActivity{
    public RaffleActivityService(IActivityRepository activityRepository) {
        super(activityRepository);
    }

}
