package com.lhj.project.main.controller;

import org.springframework.stereotype.Controller;

import com.lhj.project.main.model.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
public class MainController {

	private final FileService service;
}
