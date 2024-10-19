package com.example.springmodulith.order;

import com.example.springmodulith.order.dto.InventoryRequestDto;
import com.example.springmodulith.order.dto.OrderDto;
import com.example.springmodulith.order.dto.OrderResponseDto;
import com.example.springmodulith.order.exposed.OrderPaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ApplicationModuleTest(mode = ApplicationModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
class OrderIntegrationTest {

    @Autowired
    OrderService orderService;

    @Test
    void verifyModule() {

    }

    @Test
    void createOrder() {
        List<InventoryRequestDto> inventoryRequestDtoList =
                List.of(
                        new InventoryRequestDto("ruler",3),
                        new InventoryRequestDto("pencil",4)
                );

        OrderDto orderDto = new OrderDto("Test Man", "test@test.com",inventoryRequestDtoList);

        OrderResponseDto orderResponseDto = orderService.createOrder(orderDto);

        assertThat(orderResponseDto.message()).isEqualTo("Order Currently processed");
        assertThat(orderResponseDto.statusCode()).isEqualTo(102);
    }

    @Test
    void publishOrderPaymentDto(Scenario scenario) {
        scenario.publish(new OrderPaymentDto(UUID.randomUUID().toString(), 5000L))
                .andWaitForEventOfType(OrderPaymentDto.class)
                .matching(event -> event.amount() == 5000L)
                .toArriveAndVerify(ev -> System.out.println(ev.amount()));
    }
}