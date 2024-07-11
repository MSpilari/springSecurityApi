package com.example.springsecurity.DTO;

import java.util.List;

public record FeedDTO(List<FeedItemDTO> feedItem,
        int page,
        int pageSize,
        int totalPages,
        long totalElements) {

}
