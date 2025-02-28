package com.example.springbootdemo.messages;

import com.example.springbootdemo.structs.MsgTypes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonSerialize
public class ResponseMsg<T> implements Serializable
{
    private MsgTypes msgType;
    private String msg;
    private T data;

    public static <T> String createMsg(MsgTypes type, String msg, T data)
    {
        ResponseMsg<T> responseMsg = new ResponseMsg<T>(type, msg, data);
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
