package com.example.simplemvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = ISimpleMVCController.PATH)
public interface ISimpleMVCController {

	String PATH = "simpleMVC";

}
