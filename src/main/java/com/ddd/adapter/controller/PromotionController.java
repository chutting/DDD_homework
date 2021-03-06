package com.ddd.adapter.controller;

import com.ddd.adapter.request.PromotionRequest;
import com.ddd.adapter.response.PageablePromotionResponse;
import com.ddd.adapter.response.PromotionDetailResponse;
import com.ddd.application.PromotionApplication;
import com.ddd.infra.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PromotionController {
    private final PromotionApplication promotionApplication;

    @PostMapping("/promotion" )
    @ResponseStatus(HttpStatus.CREATED)
    public Long createPromotion(@RequestBody PromotionRequest promotionRequest) {
        return promotionApplication.createPromotion(promotionRequest.toPromotion());
    }

    @PutMapping("/promotion/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long editPromotion(@PathVariable("id") Long id, @RequestBody PromotionRequest promotionRequest) {
        return promotionApplication.editPromotion(promotionRequest.toPromotion(id));
    }

    @GetMapping("/promotions")
    @ResponseStatus(HttpStatus.OK)
    public Page<PageablePromotionResponse> getPromotions(@RequestParam(name = "page") int page,
                                                         @RequestParam(name = "size") int size) {
        return promotionApplication.getPromotions(page, size).map(PageablePromotionResponse::fromPromotion);
    }

    @GetMapping(value="/promotions/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PromotionDetailResponse getPromotionById(@PathVariable("id") Long id) {
        return promotionApplication.getPromotionById(id).map(PromotionDetailResponse::fromPromotion)
                .orElseThrow(() -> new NotFoundException("not found"));
    }
}
