package com.example.springmodulith.order;

import com.example.springmodulith.order.exposed.CompleteOrderDto;
import com.example.springmodulith.order.dto.CompleteOrderResponseDto;
import com.example.springmodulith.order.dto.OrderDto;
import com.example.springmodulith.order.dto.OrderResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 30 May, 2024
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderDto orderDto) {
        return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.OK);
    }

    @PostMapping(path = "complete")
    public ResponseEntity<CompleteOrderResponseDto> completeOrder(@RequestBody @Valid CompleteOrderDto completeOrderDto) {
        return new ResponseEntity<>(orderService.completePayment(completeOrderDto), HttpStatus.OK);
    }
}
