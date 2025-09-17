package com.iso8583.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iso8583.model.Iso8583Message;
import com.iso8583.service.Iso8583FieldDefinitions;
import com.iso8583.service.Iso8583MessageBuilder;

@Controller
@RequestMapping("/")
public class Iso8583Controller {

    @Autowired
    private Iso8583FieldDefinitions fieldDefinitions;

    @Autowired
    private Iso8583MessageBuilder messageBuilder;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("fieldDefinitions", fieldDefinitions.getAllFieldDefinitions());
        return "index";
    }

    @PostMapping("/build-message")
    @ResponseBody
    public Map<String, Object> buildMessage(@RequestParam String mti, @RequestParam Map<String, String> fields) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Iso8583Message message = messageBuilder.createMessage(mti, fields);
            response.put("success", true);
            response.put("message", message);
            response.put("rawMessage", message.getRawMessage());
            response.put("bitmap", message.getBitmap());
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return response;
    }

    @PostMapping("/parse-message")
    @ResponseBody
    public Map<String, Object> parseMessage(@RequestParam String rawMessage) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Iso8583Message message = messageBuilder.parseMessage(rawMessage);
            response.put("success", true);
            response.put("message", message);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return response;
    }

    @GetMapping("/sample-messages")
    @ResponseBody
    public Map<String, Object> getSampleMessages() {
        Map<String, Object> samples = new HashMap<>();
        
        // Authorization Request Sample
        Map<String, String> authFields = new HashMap<>();
        authFields.put("2", "4111111111111111");
        authFields.put("3", "000000");
        authFields.put("4", "000000010000");
        authFields.put("7", "1025120530");
        authFields.put("11", "123456");
        authFields.put("14", "2512");
        authFields.put("22", "051");
        authFields.put("25", "00");
        authFields.put("41", "TERM001");
        authFields.put("42", "MERCHANT123");
        authFields.put("49", "840");
        
        Iso8583Message authMessage = messageBuilder.createMessage("0100", authFields);
        samples.put("authorization", authMessage);
        
        return samples;
    }
}
