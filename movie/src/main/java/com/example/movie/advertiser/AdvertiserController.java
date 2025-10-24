package com.example.movie.advertiser;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movie.advertiser.dto.AdAccountRequest;
import com.example.movie.advertiser.dto.AdAccountStatusRequest;
import com.example.movie.advertiser.dto.AdAccountView;
import com.example.movie.advertiser.dto.AdCampaignRequest;
import com.example.movie.advertiser.dto.AdCampaignView;
import com.example.movie.advertiser.dto.AdCreativeDecisionRequest;
import com.example.movie.advertiser.dto.AdCreativeRequest;
import com.example.movie.advertiser.dto.AdCreativeView;
import com.example.movie.advertiser.dto.AdDashboardView;
import com.example.movie.advertiser.dto.RechargeRequest;
import com.example.movie.advertiser.dto.SettlementRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ads")
@Validated
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"})
public class AdvertiserController {

    private final AdvertiserService advertiserService;

    public AdvertiserController(AdvertiserService advertiserService) {
        this.advertiserService = advertiserService;
    }

    @PostMapping("/accounts")
    public AdAccountView applyAccount(@Valid @RequestBody AdAccountRequest request) {
        return advertiserService.applyAccount(request);
    }

    @GetMapping("/accounts/by-user/{userId}")
    public AdAccountView getAccount(@PathVariable Long userId) {
        return advertiserService.getAccountByUser(userId);
    }

    @PostMapping("/accounts/{accountId}/recharge")
    public AdAccountView recharge(@PathVariable Long accountId, @Valid @RequestBody RechargeRequest request) {
        return advertiserService.recharge(accountId, request);
    }

    @PostMapping("/campaigns")
    public AdCampaignView createCampaign(@Valid @RequestBody AdCampaignRequest request) {
        return advertiserService.createCampaign(request);
    }

    @GetMapping("/accounts/{accountId}/campaigns")
    public List<AdCampaignView> listCampaigns(@PathVariable Long accountId) {
        return advertiserService.listCampaigns(accountId);
    }

    @PostMapping("/creatives")
    public AdCreativeView createCreative(@Valid @RequestBody AdCreativeRequest request) {
        return advertiserService.createCreative(request);
    }

    @GetMapping("/accounts/{accountId}/creatives")
    public List<AdCreativeView> listCreatives(@PathVariable Long accountId) {
        return advertiserService.listCreativesByAccount(accountId);
    }

    @PostMapping("/creatives/{creativeId}/submit")
    public AdCreativeView submitCreative(@PathVariable Long creativeId) {
        return advertiserService.submitForReview(creativeId);
    }

    @PostMapping("/creatives/{creativeId}/activate")
    public AdCreativeView activateCreative(@PathVariable Long creativeId) {
        return advertiserService.activateCreative(creativeId);
    }

    @GetMapping("/accounts/{accountId}/dashboard")
    public AdDashboardView dashboard(@PathVariable Long accountId) {
        return advertiserService.dashboard(accountId);
    }

    @PostMapping("/accounts/{accountId}/settle")
    public AdAccountView settle(@PathVariable Long accountId, @Valid @RequestBody SettlementRequest request) {
        return advertiserService.settle(accountId, request);
    }

    @PostMapping("/admin/creatives/{creativeId}/decision")
    public AdCreativeView reviewCreative(@PathVariable Long creativeId,
            @Valid @RequestBody AdCreativeDecisionRequest request) {
        return advertiserService.reviewCreative(creativeId, request);
    }

    @PostMapping("/admin/accounts/{accountId}/status")
    public AdAccountView updateAccountStatus(@PathVariable Long accountId,
            @Valid @RequestBody AdAccountStatusRequest request) {
        return advertiserService.updateAccountStatus(accountId, request);
    }

    @GetMapping("/admin/accounts")
    public List<AdAccountView> listAccounts() {
        return advertiserService.listAccounts();
    }
}
