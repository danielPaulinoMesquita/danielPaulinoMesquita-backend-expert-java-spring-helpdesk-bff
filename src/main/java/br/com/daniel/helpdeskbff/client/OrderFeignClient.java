package br.com.daniel.helpdeskbff.client;

import br.com.userservice.commonslib.model.requests.CreateOrderRequest;
import br.com.userservice.commonslib.model.requests.UpdateOrderRequest;
import br.com.userservice.commonslib.model.responses.OrderResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "order-service-api",
        path = "/api/orders"
)
public interface OrderFeignClient {

    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> findById(
            @NotNull(message = "The order id must be informed")
            @Parameter(description = "Order ID", example = "10", required = true)
            @PathVariable(name = "id") final Long id
    );

    @GetMapping
    ResponseEntity<List<OrderResponse>> findAll();

    @PostMapping
    ResponseEntity<Void> save(
            @Valid @RequestBody final CreateOrderRequest request
    );

    @PutMapping("/{id}")
    ResponseEntity<OrderResponse> update(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody final UpdateOrderRequest request
    );

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(
            @NotNull(message = "The order id must be informed")
            @PathVariable(name = "id") final Long id
    );

    @GetMapping("/page")
    ResponseEntity<Page<OrderResponse>> findAllPaginated(
            @RequestParam(name = "page", defaultValue = "0") final Integer page,

            @RequestParam(name = "linesPerPage", defaultValue = "12") final Integer linesPerPage,

            @RequestParam(name = "direction", defaultValue = "ASC") final String direction,

            @RequestParam(name = "orderBy", defaultValue = "id") final String orderBy
    );


}
