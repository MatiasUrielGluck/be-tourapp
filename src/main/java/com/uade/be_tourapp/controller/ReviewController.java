package com.uade.be_tourapp.controller;

import com.uade.be_tourapp.dto.GenericResponseDTO;
import com.uade.be_tourapp.dto.review.ReviewRequestDTO;
import com.uade.be_tourapp.dto.review.ReviewResponseDTO;
import com.uade.be_tourapp.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/review")
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping()
    public ResponseEntity<ReviewResponseDTO> crearReview(@RequestBody @Validated ReviewRequestDTO reviewRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.crearReview(reviewRequestDTO));
    }

    @DeleteMapping("{viajeId}")
    public ResponseEntity<GenericResponseDTO> eliminarReview(@PathVariable Integer viajeId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.eliminarReview(viajeId));
    }
}
