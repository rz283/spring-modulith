package com.example.springmodulith.order;

import com.example.springmodulith.exception.ModulithException;
import com.example.springmodulith.inventory.exposed.InventoryDto;
import com.example.springmodulith.inventory.exposed.InventoryService;
import com.example.springmodulith.order.dto.*;
import com.example.springmodulith.order.exposed.CompleteOrderDto;
import com.example.springmodulith.order.exposed.EmailDto;
import com.example.springmodulith.order.exposed.OrderPaymentDto;
import com.example.springmodulith.order.type.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final InventoryService inventoryService;
    private final OrderRepository orderRepository;
    private final OrderInventoryRepository orderInventoryRepository;
    private final OrderEventService orderEventService;

    public OrderResponseDto createOrder(OrderDto orderDto) {
        //get Inventories by names
        List<String> inventoryNames = orderDto.inventoryRequestDtoList().stream().map(InventoryRequestDto::inventoryName).toList();

        List<InventoryDto> inventories = inventoryService.fetchAllInName(inventoryNames);

        final AtomicLong amount = new AtomicLong();
        //persist the order
        Order order = buildAndPersistOrder(orderDto);
        log.info("Order created: {}", order);

        //build and persist the orderInventory
        buildAndPersistOrderInventories(orderDto, inventories, order.getId(), amount);

        OrderPaymentDto orderPaymentDto = new OrderPaymentDto(order.getOrderIdentifier(), amount.get());
        EmailDto emailDto = new EmailDto(orderDto.customerEmail(), orderDto.customername(), order.getOrderIdentifier(), orderPaymentDto.amount(), false);
        orderEventService.completeOrder(orderPaymentDto, emailDto);

        return new OrderResponseDto("Order Currently processed", 102);
    }

    public CompleteOrderResponseDto completePayment(CompleteOrderDto completeOrderDto) {
        Optional<Order> optionalOrder = orderRepository.getOrderByOrderIdentifier(completeOrderDto.orderIdentifier());
        if(optionalOrder.isEmpty()) throw new ModulithException("Order with id: " + completeOrderDto.orderIdentifier() + " not found");

        Order order = optionalOrder.get();
        final long amount = orderInventoryRepository.orderIdAmount(order.getId());
        EmailDto emailDto = new EmailDto(order.getCustomerEmail(), order.getCustomerName(), order.getOrderIdentifier(), amount, true);

        orderEventService.completePayment(completeOrderDto, emailDto);

        return new CompleteOrderResponseDto(true);
    }

    private void buildAndPersistOrderInventories(OrderDto orderDto, List<InventoryDto> inventories, Long orderId, AtomicLong amount) {
        List<OrderInventory> orderInventories = new ArrayList<>(inventories.size());

        inventories.forEach(inventoryDto -> {
            OrderInventory orderInventory = new OrderInventory();

            InventoryRequestDto inventoryRequestDto = getInventoryRequestDtoByName(inventoryDto.name(), orderDto.inventoryRequestDtoList());
            orderInventory.setOrderId(orderId);
            orderInventory.setInventoryId(inventoryDto.id());
            orderInventory.setQty(inventoryRequestDto.qty());

            long totalPrice = inventoryDto.price() * inventoryRequestDto.qty();
            orderInventory.setTotalQtyPrice(totalPrice);

            orderInventories.add(orderInventory);
            amount.addAndGet(totalPrice);
        });

        log.info("Order Inventories persisted: {}", orderInventories);
        orderInventoryRepository.saveAll(orderInventories);
    }

    private static InventoryRequestDto getInventoryRequestDtoByName(String inventoryName, List<InventoryRequestDto> inventoryRequestDtoList) {
        return inventoryRequestDtoList.stream().filter(inv -> inv.inventoryName().equals(inventoryName)).findFirst().orElse(null);
    }

    private Order buildAndPersistOrder(OrderDto orderDto) {
        Order order = new Order();

        order.setOrderIdentifier(UUID.randomUUID().toString());
        order.setCustomerName(orderDto.customername());
        order.setCustomerEmail(orderDto.customerEmail());
        order.setStatus(Status.COMPLETED);

        return orderRepository.save(order);
    }
}
