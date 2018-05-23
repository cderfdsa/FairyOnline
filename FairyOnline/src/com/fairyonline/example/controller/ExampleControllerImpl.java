package com.fairyonline.example.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fairyonline.example.entity.Example;
import com.fairyonline.example.service.ExampleServiceImpl;

@Controller
@Repository
@RequestMapping("/example")/*���ĳɶ�Ӧ�����֣����Ӧ��ַ*/
public class ExampleControllerImpl {/*�շ�ʽ����*/
	@Resource
	private ExampleServiceImpl exampleServiceImple;

//	@RequestMapping(value="/Add")/*������Ӧ��ַ*/
	@PostMapping("/Add")
	public String Add() {/*�շ�ʽ����*/
		System.out.println("get add");
		Example example = new Example("����exampleName");
		exampleServiceImple.Add(example);
		return "Example/Example";
	}
}
