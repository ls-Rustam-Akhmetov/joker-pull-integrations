package ru.bcs.perseus.quotes.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

@Api
@RequestMapping("/api/${spring.application.name}")
@SuppressWarnings("squid:S1610")
public abstract class BaseController {
}
