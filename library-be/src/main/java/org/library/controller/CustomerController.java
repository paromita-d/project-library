package org.library.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.library.controller.dto.BookDTO;
import org.library.controller.dto.StatusDTO;
import org.library.controller.dto.TransactionDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/customer")
@Api("endpoints for library customers")
public class CustomerController {

    @GetMapping(value = {"/books/{pageLimit}", "/books/{pageLimit}/{pageNum}"})
    @ApiOperation("provides a list of all books in the library in a paginated way. " +
            "PageLimit tells BE how many books are expected to be shown in a single page. " +
            "PageNum tells BE which page view data is being expected. " +
            "If no PageNum is passed, it defaults to the 1st page (pageNum = 1)")
    public List<BookDTO> getBooks(@PathVariable int pageLimit, @PathVariable(required = false) int pageNum) {
        //todo add logic
        return Collections.emptyList();
    }

    @GetMapping("/books/{bookId}")
    @ApiOperation("get more information about a specific book")
    public BookDTO getBook(@PathVariable Long bookId) {
        //todo add logic
        return new BookDTO();
    }

    @GetMapping("/cart")
    @ApiOperation("gets the cart for the current user. Valid userId should be present in the httpHeader for this to work")
    public List<BookDTO> getCart() {
        //todo add logic
        return Collections.emptyList();
    }

    @PutMapping("/cart/{bookIdList}")
    @ApiOperation("add/update/delete these books to cart for this user (one user can have only one active cart at a time - even from multiple browsers). " +
            "Valid userId should be present in the httpHeader for this to work. " +
            "This is a put operation, so overrides existing cart content")
    public StatusDTO addToCart(@PathVariable List<Long> bookIdList) {
        //todo add logic
        return StatusDTO.builder().message("books added to cart (size) - 5 books").build();
    }

    @PostMapping("/checkout")
    @ApiOperation("Checks out cart for this user. " +
            "Valid userId should be present in the httpHeader for this to work. " +
            "Returns the transaction id for this checkout")
    public StatusDTO checkOut() {
        //todo add logic
        return StatusDTO.builder().message("checkout success - transaction id - 10526").build();
    }

    @GetMapping("/history/pending")
    @ApiOperation("Shows checkouts done in the past for which at least one book is pending return. " +
            "Valid userId should be present in the httpHeader for this to work. ")
    public List<TransactionDTO> getPendingReturns() {
        //todo add logic
        return Collections.emptyList();
    }

    @GetMapping("/history/all")
    @ApiOperation("Shows all checkouts done in the past. " +
            "Valid userId should be present in the httpHeader for this to work. ")
    public List<TransactionDTO> getAllReturns() {
        //todo add logic
        return Collections.emptyList();
    }

    @PostMapping("/return")
    @ApiOperation("Returns books for a customer for a specific transaction. " +
            "Valid userId should be present in the httpHeader for this to work. ")
    public StatusDTO returnBooks(@RequestBody List<TransactionDTO> returns) {
        //todo add logic
        return StatusDTO.builder().message("return successful").build();
    }
}
