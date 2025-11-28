package com.multi.restproduct.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductFrontController {

    @GetMapping("/products/list")
    public void productList() {
        //예외 테스트할때
       // throw new IllegalStateException("뷰 컨트롤러에서 발생한 예외입니다.");
    }
    @GetMapping("/products/{productCode}")
    public String productDetail(@PathVariable("productCode") String productCode, Model model) {
        model.addAttribute("productCode", productCode);
        return "products/detail";
    }

    @GetMapping("/products/regist")
    public void addProduct() {

    }

    @GetMapping("/products/update/{productCode}")
    public String updateProduct(@PathVariable("productCode") String productCode, Model model) {
        model.addAttribute("productCode", productCode);
        System.out.println("Received productCode: " + productCode);


        return "products/update";
    }


}
