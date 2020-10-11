package com.sda.onlinestore.service;

import com.sda.onlinestore.dto.OrderDTO;
import com.sda.onlinestore.dto.OrderLineDTO;
import com.sda.onlinestore.dto.ProductDto;
import com.sda.onlinestore.model.*;
import com.sda.onlinestore.repository.OrderRepository;
import com.sda.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public void save(String username, Long productID){
        Optional<OrderModel> orderModelOptional = orderRepository.findOrderModelByUserName(username);
        OrderModel order = new OrderModel();
        ProductModel productModel = new ProductModel();

        if(orderModelOptional.isPresent()) {
            order  = orderModelOptional.get();
            List<OrderLineModel> orderLineModels = order.getOrderLines();
            for (OrderLineModel olm: orderLineModels) {
                if(olm.getProductModel().getId() == productID){
                   olm.setQuantity(olm.getQuantity() + 1);
                }
            }
            orderRepository.save(order);
        }
        else{
            OrderLineModel orderLineModel = new OrderLineModel();
            orderLineModel.setQuantity(1);
            orderLineModel.setProductModel(productRepository.findById(productID).orElse(null));
            orderLineModel.setPrice(orderLineModel.getQuantity() * orderLineModel.getProductModel().getPrice());
            order.getOrderLines().add(orderLineModel);
            orderRepository.save(order);

        }
    }

    public void deleteById(Long id){
        orderRepository.deleteById(id);
    }

    public OrderDTO findById(Long id) {
        Optional<OrderModel> order = orderRepository.findById(id);
        OrderDTO orderDTO = new OrderDTO();
        if (order != null) {
            orderDTO.setId(order.get().getId());
            orderDTO.setTotal(order.get().getTotal());

            List<OrderLineDTO> orderLinesDTO = new ArrayList<>();
            for (OrderLineModel ol : order.get().getOrderLines()) {
                OrderLineDTO old = new OrderLineDTO();
                old.setId(ol.getId());
                old.setPrice(ol.getPrice());
                old.setQuantity(ol.getQuantity());

                ProductDto productDto = new ProductDto();
                productDto.setId(ol.getProductModel().getId());
                productDto.setName(ol.getProductModel().getName());
                productDto.setPrice(ol.getProductModel().getPrice());
                old.setProductDTO(productDto);
                orderLinesDTO.add(old);
            }
            orderDTO.setOrderLines(orderLinesDTO);
        }
        return orderDTO;

    }


    public List<OrderDTO> findAll(){
        List<OrderDTO> orderDTOS = new ArrayList<>();
        List<OrderModel> orderModels = orderRepository.findAll();
        for (OrderModel om: orderModels) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(om.getId());
            orderDTO.setTotal(om.getTotal());

            List<OrderLineDTO> orderLinesDTO = new ArrayList<>();

            for (OrderLineModel ol : om.getOrderLines()) {
                OrderLineDTO old = new OrderLineDTO();
                old.setId(ol.getId());
                old.setPrice(ol.getPrice());
                old.setQuantity(ol.getQuantity());

                ProductDto productDto = new ProductDto();
                productDto.setId(ol.getProductModel().getId());
                productDto.setName(ol.getProductModel().getName());
                productDto.setPrice(ol.getProductModel().getPrice());
                old.setProductDTO(productDto);
                orderLinesDTO.add(old);
            }
            orderDTO.setOrderLines(orderLinesDTO);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    public void update(OrderDTO orderDTO){
        Optional<OrderModel> orderModelOptional = orderRepository.findById(orderDTO.getId());
        if(orderModelOptional.isPresent()){
            OrderModel orderModel = orderModelOptional.get();
            orderModel.setTotal(orderDTO.getTotal());
            List<OrderLineModel> orderlines = new ArrayList<>();
            for (OrderLineDTO orderLineDTO: orderDTO.getOrderLines()) {
                OrderLineModel orderLineModel = new OrderLineModel();
                orderLineModel.setQuantity(orderLineDTO.getQuantity());

                ProductModel productModel = new ProductModel();
                productModel.setName(orderLineDTO.getProductDTO().getName());
                productModel.setPrice(orderLineDTO.getProductDTO().getPrice());
                orderLineModel.setProductModel(productModel);
                orderlines.add(orderLineModel);
            }
            orderModel.setOrderLines(orderlines);
            orderRepository.save(orderModel);
        }
    }

    public Double totalPrice(List<OrderLineModel> orderLineModels){
        Double total = 0.0;
        for (OrderLineModel olm: orderLineModels) {
            total = total + olm.getProductModel().getPrice() * olm.getQuantity();
        }
        return total;
    }
}
