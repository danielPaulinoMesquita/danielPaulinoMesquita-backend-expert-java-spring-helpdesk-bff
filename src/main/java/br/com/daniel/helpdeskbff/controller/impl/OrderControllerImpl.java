package br.com.daniel.helpdeskbff.controller.impl;


import br.com.daniel.helpdeskbff.controller.OrderController;
import br.com.daniel.helpdeskbff.service.OrderService;
import br.com.userservice.commonslib.model.requests.CreateOrderRequest;
import br.com.userservice.commonslib.model.requests.UpdateOrderRequest;
import br.com.userservice.commonslib.model.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Override
    public ResponseEntity<OrderResponse> findById(Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @Override
    public ResponseEntity<List<OrderResponse>> findAll() {
        return ResponseEntity.ok().body(orderService.findAll());
    }

    @Override
    public ResponseEntity<Void> save(CreateOrderRequest request) {
        orderService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<OrderResponse> update(Long id, UpdateOrderRequest request) {
        return ResponseEntity.ok().body(orderService.update(id, request));
    }

    @Override
    public ResponseEntity<Void> deleteById(Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<OrderResponse>> findAllPaginated(Integer page,
                                                                Integer linesPerPage,
                                                                String direction,
                                                                String orderBy) {
        return ResponseEntity.ok().body(orderService.findAllPaginated(page, linesPerPage, direction, orderBy));
    }
}
