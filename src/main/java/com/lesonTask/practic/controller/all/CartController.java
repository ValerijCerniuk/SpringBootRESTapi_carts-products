package com.lesonTask.practic.controller.all;

import com.lesonTask.practic.model.Cart;
import com.lesonTask.practic.service.CartService;
import com.lesonTask.practic.service.email.EmailServiceImpl;
import com.lesonTask.practic.service.jsonService.JsonService;
import com.lesonTask.practic.service.pdf.CartPdfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials = "true")
@Controller
@RequestMapping("/api")
public class CartController {

    private final CartService cartService;
    private final CartPdfService cartPdfService;
    private final JsonService jsonService;
    private final EmailServiceImpl emailService;

    public CartController(CartService cartService, CartPdfService cartPdfService, JsonService jsonService, EmailServiceImpl emailService) {
        this.cartService = cartService;
        this.cartPdfService = cartPdfService;
        this.jsonService = jsonService;
        this.emailService = emailService;
    }
// TODO suderint roliu logika, kas ka ir kam gali dadet, pakeist, salint

/**
 * Admin endpoint
 */
    @DeleteMapping("/admin/carts/{cartId}")
    public ResponseEntity<HttpStatus> deleteCartById(@PathVariable Long cartId){
        return cartService.deleteCartById(cartId);
    }

    /**
     * Moderator endpoint
     */
    @GetMapping("/mod/carts")
    public ResponseEntity<List<Cart>> getAllCarts() {
        return cartService.getAllCart();
    }

    /**
     * User endpoints
     * suppose to by open only for user carts
     */
    @PutMapping("/carts/{cartId}")
    public ResponseEntity<HttpStatus> updateCart(@PathVariable("cartId") Long cartId, @Valid @RequestBody Cart cart) {
        return cartService.updateCart(cartId, cart);

    }

    @PostMapping("/carts")
    public ResponseEntity<HttpStatus> addCart(@Valid @RequestBody Cart cart) {
        return cartService.save(cart);
    }

    @GetMapping("/carts/{cartId}/export/pdf")
    public ResponseEntity<HttpStatus> exportToPDF(@PathVariable("cartId") Long cartId) {
        ResponseEntity<Cart> cartData = cartService.findById(cartId);
        if (cartData.hasBody() && cartData.getBody() != null) {
            cartPdfService.exportCartToPdf(cartData.getBody());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/carts/{cartId}/export/json")
    public ResponseEntity<HttpStatus> exportToJson (@PathVariable("cartId") Long cartId) {
        ResponseEntity<Cart> cartData = cartService.findById(cartId);
        if (cartData.hasBody() && cartData.getBody() != null) {
            jsonService.writeCartToJsonFile(cartData.getBody());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/carts/{cartId}/send/cartByMail")
    public ResponseEntity<HttpStatus> sendCartViaEmail(@PathVariable("cartId") Long cartId){
        ResponseEntity<Cart> cartData = cartService.findById(cartId);
        if (cartData.hasBody() && cartData.getBody() != null) {
            emailService.sendCartViaEmail(cartData.getBody());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/carts/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable("cartId") Long cartId) {
        return cartService.findById(cartId);
    }
}
