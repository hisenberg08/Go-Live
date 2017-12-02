package com.golive.launch.cont;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages="com.golive.launch.view")
public class RestfulController {

}
