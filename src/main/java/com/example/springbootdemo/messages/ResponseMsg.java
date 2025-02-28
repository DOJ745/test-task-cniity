package com.example.springbootdemo.messages;

import com.example.springbootdemo.structs.MsgTypes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "ResponseMsg"
        , description = "Structured response message"
        , example =
        "{\n" +
        "  \"msgType\": \"MSG_SUCCESS\",\n" +
        "  \"msg\": \"User has been updated\",\n" +
        "  \"data\":" +
        "  \"id\": 1001,\n" +
        "  \"personalInfo\": {\n" +
        "    \"firstName\": \"John\",\n" +
        "    \"lastName\": \"Doe\",\n" +
        "    \"email\": \"john.doe@example.com\",\n" +
        "    \"dateOfBirth\": \"1990-03-22\",\n" +
        "    \"gender\": \"Male\"\n" +
        "  },\n" +
        "  \"education\": {\n" +
        "    \"universityName\": \"MIT\",\n" +
        "    \"degree\": \"Computer Science\",\n" +
        "    \"graduationYear\": 2015\n" +
        "  },\n" +
        "  \"employment\": {\n" +
        "    \"companyName\": \"Google\",\n" +
        "    \"position\": \"Software Engineer\",\n" +
        "    \"startDate\": \"2016-01-15\",\n" +
        "    \"endDate\": \"Present\"\n" +
        "  },\n" +
        "  \"contactInfo\": {\n" +
        "    \"phoneNumber\": \"+16502530000\",\n" +
        "    \"address\": {\n" +
        "      \"street\": \"1600 Amphitheatre Pkwy\",\n" +
        "      \"city\": \"Mountain View\",\n" +
        "      \"state\": \"CA\",\n" +
        "      \"postalCode\": 94043,\n" +
        "      \"country\": \"USA\"\n" +
        "    }\n" +
        "  },\n" +
        "  \"skills\": [\"Java\", \"Spring\", \"AWS\"]\n" +
        "}\n}"
)
public class ResponseMsg<T> implements Serializable
{
    @Schema(description = "Response message type", example = "MSG_SUCCESS")
    private MsgTypes msgType;

    @Schema(description = "Response message", example = "User has been deleted")
    private String msg;

    @Schema(description = "Response data", example = "null")
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
