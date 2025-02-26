package com.example.springbootdemo.messages;

import com.example.springbootdemo.structs.MsgTypes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonSerialize
public class ResponseMsg
{
    private MsgTypes msgType;
    private String msg;

    public static String createMsg(MsgTypes type, String msg)
    {
        ResponseMsg responseMsg = new ResponseMsg(type, msg);
        ObjectMapper objectMapper = new ObjectMapper();

        try
        {
            return objectMapper.writeValueAsString(responseMsg);
        }
        catch (JsonProcessingException e)
        {
            return "{\"error\": \"Failed to create response message\"}";
        }
    }
}
