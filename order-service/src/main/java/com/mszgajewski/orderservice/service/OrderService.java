package com.mszgajewski.orderservice.service;

import com.mszgajewski.orderservice.dto.OrderLineItemsDto;
import com.mszgajewski.orderservice.dto.OrderRequest;
import com.mszgajewski.orderservice.model.Order;
import com.mszgajewski.orderservice.model.OrderLineItems;
import com.mszgajewski.orderservice.repository.OrderRepository;
import com.mszgajewski.productservice.dto.ProductRequest;
import com.mszgajewski.productservice.dto.ProductResponse;
import com.mszgajewski.productservice.model.Product;
import com.mszgajewski.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

   private final OrderRepository orderRepository;
   private final WebClient.Builder webClientBuilder;
   private final ObservationRegistry observationRegistry;
   private final ApplicationEventPublisher applicationEventPublisher;

   public String placeOrder(OrderRequest orderRequest) {
      Order order = new Order();
      order.setOrderNumber(UUID.randomUUID().toString());

      List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
              .stream()
              .map(this::mapToDto)
              .toList();

      order.getOrderLineItemsList(orderLineItems);

      List<String> skuCodes = order.getOrderLineItemsList().stream()
              .map(OrderLineItems::getSkuCode)
              .toList();

   }

   private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
      OrderLineItems orderLineItems = new OrderLineItems();
      orderLineItems.setPrice(orderLineItemsDto.getPrice());
      orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
      orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
      return orderLineItems;
   }

}
