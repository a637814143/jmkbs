package com.example.movie.movie.dto;

import java.util.List;

public class MovieSearchResponse {

    private List<MovieSummaryDto> items;
    private long total;
    private int page;
    private int size;

    public MovieSearchResponse() {
    }

    public MovieSearchResponse(List<MovieSummaryDto> items, long total, int page, int size) {
        this.items = items;
        this.total = total;
        this.page = page;
        this.size = size;
    }

    public List<MovieSummaryDto> getItems() {
        return items;
    }

    public void setItems(List<MovieSummaryDto> items) {
        this.items = items;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
