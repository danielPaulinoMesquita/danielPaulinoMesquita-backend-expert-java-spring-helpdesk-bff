package br.com.daniel.helpdeskbff.service;

import br.com.daniel.helpdeskbff.client.OrderFeignClient;
import br.com.userservice.commonslib.model.requests.CreateOrderRequest;
import br.com.userservice.commonslib.model.requests.UpdateOrderRequest;
import br.com.userservice.commonslib.model.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderFeignClient orderFeignClient;


    public OrderResponse findById(final Long id) {
        return orderFeignClient.findById(id).getBody();
    }

    public void save(CreateOrderRequest createOrderRequest) {
        orderFeignClient.save(createOrderRequest);
    }


    public OrderResponse update(Long id, UpdateOrderRequest request) {
        return orderFeignClient.update(id, request).getBody();
    }

    public void deleteById(Long id) {
        orderFeignClient.deleteById(id);
    }

    public List<OrderResponse> findAll() {
        return orderFeignClient.findAll().getBody();
    }

    public Page<OrderResponse> findAllPaginated(Integer page, Integer linesPerPage, String direction, String orderBy) {
        return orderFeignClient.findAllPaginated(page, linesPerPage, direction, orderBy).getBody();
    }

}
