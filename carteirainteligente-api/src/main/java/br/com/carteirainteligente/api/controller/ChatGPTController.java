package br.com.carteirainteligente.api.controller;

import com.lilittlecat.chatgpt.offical.ChatGPT;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatgpt")
public class ChatGPTController {

    @GetMapping
    public void listEntries() {


        ChatGPT chatGPT = new ChatGPT("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");
        String hello = chatGPT.ask("hello");
        System.out.println(hello); // will be "\n\nHello! How may I assist you today?"


    }
}
