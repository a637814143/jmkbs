package com.example.movie.advertiser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.example.movie.domain.ad.AdAccount;
import com.example.movie.domain.ad.AdAccountRepository;
import com.example.movie.domain.ad.AdAccountStatus;
import com.example.movie.domain.ad.AdCampaign;
import com.example.movie.domain.ad.AdCampaignRepository;
import com.example.movie.domain.ad.AdCampaignStatus;
import com.example.movie.domain.ad.AdCreative;
import com.example.movie.domain.ad.AdCreativeRepository;
import com.example.movie.domain.ad.AdCreativeStatus;
import com.example.movie.domain.ad.AdEventRepository;
import com.example.movie.domain.ad.AdEventType;
import com.example.movie.domain.user.User;
import com.example.movie.domain.user.UserRepository;
import com.example.movie.domain.user.UserRole;

@Service
@Transactional
public class AdvertiserService {

    private final AdAccountRepository adAccountRepository;
    private final AdCampaignRepository adCampaignRepository;
    private final AdCreativeRepository adCreativeRepository;
    private final AdEventRepository adEventRepository;
    private final UserRepository userRepository;

    public AdvertiserService(AdAccountRepository adAccountRepository, AdCampaignRepository adCampaignRepository,
            AdCreativeRepository adCreativeRepository, AdEventRepository adEventRepository,
            UserRepository userRepository) {
        this.adAccountRepository = adAccountRepository;
        this.adCampaignRepository = adCampaignRepository;
        this.adCreativeRepository = adCreativeRepository;
        this.adEventRepository = adEventRepository;
        this.userRepository = userRepository;
    }

    public AdAccountView applyAccount(AdAccountRequest request) {
        User user = requireAdvertiser(request.getUserId());
        return adAccountRepository.findByUserId(user.getId())
                .map(this::toAccountView)
                .orElseGet(() -> {
                    AdAccount account = new AdAccount();
                    account.setUser(user);
                    account.setCompanyName(request.getCompanyName());
                    account.setStatus(AdAccountStatus.PENDING);
                    account.setBalanceCents(0L);
                    AdAccount saved = adAccountRepository.save(account);
                    return toAccountView(saved);
                });
    }

    @Transactional(readOnly = true)
    public AdAccountView getAccountByUser(Long userId) {
        return adAccountRepository.findByUserId(userId).map(this::toAccountView).orElse(null);
    }

    public AdAccountView recharge(Long accountId, RechargeRequest request) {
        AdAccount account = adAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("广告账户不存在"));
        account.setBalanceCents(account.getBalanceCents() + request.getAmountCents());
        return toAccountView(account);
    }

    public AdCampaignView createCampaign(AdCampaignRequest request) {
        AdAccount account = adAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("广告账户不存在"));
        AdCampaign campaign = new AdCampaign();
        campaign.setAccount(account);
        campaign.setName(request.getName());
        campaign.setObjective(request.getObjective());
        campaign.setBudgetTotalCents(request.getBudgetTotalCents());
        campaign.setBudgetDailyCents(request.getBudgetDailyCents());
        campaign.setBidType(request.getBidType());
        campaign.setStartTime(request.getStartTime());
        LocalDateTime endTime = request.getEndTime();
        campaign.setEndTime(endTime);
        campaign.setPacing(request.getPacing());
        campaign.setStatus(AdCampaignStatus.DRAFT);
        AdCampaign saved = adCampaignRepository.save(campaign);
        return toCampaignView(saved);
    }

    @Transactional(readOnly = true)
    public List<AdCampaignView> listCampaigns(Long accountId) {
        return adCampaignRepository.findByAccountId(accountId).stream().map(this::toCampaignView)
                .collect(Collectors.toList());
    }

    public AdCreativeView createCreative(AdCreativeRequest request) {
        AdCampaign campaign = adCampaignRepository.findById(request.getCampaignId())
                .orElseThrow(() -> new IllegalArgumentException("广告计划不存在"));
        AdCreative creative = new AdCreative();
        creative.setCampaign(campaign);
        creative.setType(request.getType());
        creative.setAssetUrl(request.getAssetUrl());
        creative.setClickUrl(request.getClickUrl());
        creative.setStatus(AdCreativeStatus.PENDING);
        creative.setAuditNotes(null);
        AdCreative saved = adCreativeRepository.save(creative);
        return toCreativeView(saved);
    }

    @Transactional(readOnly = true)
    public List<AdCreativeView> listCreativesByAccount(Long accountId) {
        return adCreativeRepository.findByCampaignAccountId(accountId).stream().map(this::toCreativeView)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdCreativeView> listCreativesByCampaign(Long campaignId) {
        return adCreativeRepository.findByCampaignId(campaignId).stream().map(this::toCreativeView)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdCreativeView> listCreativesByStatus(AdCreativeStatus status) {
        return adCreativeRepository.findByStatus(status).stream().map(this::toCreativeView)
                .collect(Collectors.toList());
    }

    public AdCreativeView submitForReview(Long creativeId) {
        AdCreative creative = adCreativeRepository.findById(creativeId)
                .orElseThrow(() -> new IllegalArgumentException("广告素材不存在"));
        creative.setStatus(AdCreativeStatus.PENDING);
        return toCreativeView(creative);
    }

    public AdCreativeView activateCreative(Long creativeId) {
        AdCreative creative = adCreativeRepository.findById(creativeId)
                .orElseThrow(() -> new IllegalArgumentException("广告素材不存在"));
        if (creative.getStatus() != AdCreativeStatus.APPROVED) {
            throw new IllegalStateException("素材尚未通过审核");
        }
        creative.setStatus(AdCreativeStatus.ACTIVE);
        creative.setAuditNotes(null);
        return toCreativeView(creative);
    }

    public AdDashboardView dashboard(Long accountId) {
        AdAccount account = adAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("广告账户不存在"));
        long impressions = adEventRepository.countByCreativeCampaignAccountIdAndEventType(accountId, AdEventType.IMPRESSION);
        long clicks = adEventRepository.countByCreativeCampaignAccountIdAndEventType(accountId, AdEventType.CLICK);
        Map<String, Long> campaignStatus = adCampaignRepository.findByAccountId(accountId).stream()
                .collect(Collectors.groupingBy(campaign -> campaign.getStatus().name(), Collectors.counting()));
        Map<String, Long> creativeStatus = adCreativeRepository.findByCampaignAccountId(accountId).stream()
                .collect(Collectors.groupingBy(creative -> creative.getStatus().name(), Collectors.counting()));
        return new AdDashboardView(account.getId(), account.getBalanceCents(), impressions, clicks, campaignStatus,
                creativeStatus);
    }

    public AdAccountView settle(Long accountId, SettlementRequest request) {
        AdAccount account = adAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("广告账户不存在"));
        long amount = request.getAmountCents() != null ? request.getAmountCents() : 0L;
        if (amount > account.getBalanceCents()) {
            throw new IllegalArgumentException("账户余额不足");
        }
        account.setBalanceCents(account.getBalanceCents() - amount);
        return toAccountView(account);
    }

    public AdCreativeView reviewCreative(Long creativeId, AdCreativeDecisionRequest request) {
        AdCreative creative = adCreativeRepository.findById(creativeId)
                .orElseThrow(() -> new IllegalArgumentException("广告素材不存在"));
        creative.setStatus(request.getStatus());
        creative.setAuditNotes(request.getAuditNotes());
        return toCreativeView(creative);
    }

    public AdAccountView updateAccountStatus(Long accountId, AdAccountStatusRequest request) {
        AdAccount account = adAccountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("广告账户不存在"));
        account.setStatus(request.getStatus());
        return toAccountView(account);
    }

    @Transactional(readOnly = true)
    public List<AdAccountView> listAccounts() {
        return adAccountRepository.findAll().stream().map(this::toAccountView).collect(Collectors.toList());
    }

    private User requireAdvertiser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        if (user.getRole() != UserRole.ADVERTISER) {
            throw new IllegalStateException("仅广告主可申请广告账户");
        }
        return user;
    }

    private AdAccountView toAccountView(AdAccount account) {
        return new AdAccountView(account.getId(), account.getUser().getId(), account.getCompanyName(),
                account.getStatus(), account.getBalanceCents());
    }

    private AdCampaignView toCampaignView(AdCampaign campaign) {
        return new AdCampaignView(campaign.getId(), campaign.getAccount().getId(), campaign.getName(),
                campaign.getObjective(), campaign.getBudgetTotalCents(), campaign.getBudgetDailyCents(),
                campaign.getBidType(), campaign.getStartTime(), campaign.getEndTime(), campaign.getPacing(),
                campaign.getStatus());
    }

    private AdCreativeView toCreativeView(AdCreative creative) {
        return new AdCreativeView(creative.getId(), creative.getCampaign().getId(), creative.getType(),
                creative.getAssetUrl(), creative.getClickUrl(), creative.getStatus(), creative.getAuditNotes());
    }
}
